package com.jszheng.searchtree.redblack;

import com.jszheng.Env;
import com.jszheng.node.BinTreeNode;
import com.jszheng.searchtree.BinarySearchTree;
import com.jszheng.searchtree.BstDeletion;
import com.jszheng.searchtree.rotation.LlRotation;
import com.jszheng.searchtree.rotation.RotationState;
import com.jszheng.searchtree.rotation.RrRotation;

import static com.jszheng.searchtree.redblack.RedBlackTree.BLACK;
import static com.jszheng.searchtree.redblack.RedBlackTree.RED;


class RedBlackDeletion<E extends Comparable<? super E>> extends BstDeletion<E> {

    @Override
    protected RedBlackTree<E> getBt() {
        BinarySearchTree<E> bst = super.getBt();
        return (RedBlackTree<E>) bst;
    }

    @Override
    protected void fixAfterDeletion(BinTreeNode<E> parent, BinTreeNode<E> sibling, BinTreeNode<E> targetNode, boolean isTargetLeft, int degree) {
        RedBlackTree<E> rbt = getBt();

        boolean targetColor = rbt.colorOf((RedBlackTreeNode<E>) targetNode);

        if (Env.debug)
            System.out.println("[delete] actually delete " + rbt.getNodeString(targetNode));

        if (targetColor == BLACK) {
            if (degree == 1)
                handleDegreeOne((RedBlackTreeNode<E>) parent, isTargetLeft);
            else
                handleLeaf((RedBlackTreeNode<E>) sibling);
        }
    }

    private void handleDegreeOne(RedBlackTreeNode<E> parent, boolean isTargetLeft) {
        RedBlackTree<E> rbt = getBt();

        RedBlackTreeNode<E> currentNode;
        if (parent == null)
            currentNode = (RedBlackTreeNode<E>) rbt.getRoot();
        else
            currentNode = isTargetLeft ? parent.getLeftChild() : parent.getRightChild();

        rbt.setColor(currentNode, BLACK);
    }

    private void handleLeaf(RedBlackTreeNode<E> sibling) {
        if (sibling == null) return;
        RedBlackTree<E> rbt = getBt();

        while (true) {
            boolean siblingColor = rbt.colorOf(sibling);
            RedBlackTreeNode<E> parent = sibling.getParent();
            boolean isSiblingLeft = sibling.isLeftChild();

            // Case 1: x’s sibling w is red
            if (siblingColor == RED) {
                if (Env.debug) {
                    System.out.printf("[delete] case1: sibling %s is RED\n",
                            rbt.getNodeString(sibling));
                    System.out.printf("[delete] make sibling %s => BLACK,  parent %s => RED\n",
                            rbt.getNodeString(parent), rbt.getNodeString(sibling));
                }

                rbt.setColor(sibling, BLACK); // 兄弟變黑
                rbt.setColor(parent, RED); // 父親變紅

                RotationState state = isSiblingLeft ? new LlRotation() : new RrRotation();
                state.rotate(rbt, parent); // 父親旋轉 -> 提升 sibling

                sibling = isSiblingLeft ? parent.getLeftChild() : parent.getRightChild();
                if (Env.debug) {
                    String childSide = isSiblingLeft ? "left child" : "right child";
                    System.out.println("[delete] move sibling pointer to " +
                            rbt.getNodeString(sibling) + " (" + childSide + " of parent)");
                }
                continue;
            }

            RedBlackTreeNode<E> siblingLChild = sibling.getLeftChild();
            RedBlackTreeNode<E> siblingRChild = sibling.getRightChild();
            boolean siblingLColor = rbt.colorOf(siblingLChild);
            boolean siblingRColor = rbt.colorOf(siblingRChild);

            // Case 2: x’s sibling w is black, and both of w’s children are black
            if (siblingLColor == BLACK && siblingRColor == BLACK) {
                if (Env.debug) {
                    System.out.printf("[delete] case2: both child of sibling %s are BLACK\n",
                            rbt.getNodeString(sibling));
                    System.out.printf("[delete] make sibling %s => RED\n",
                            rbt.getNodeString(sibling));
                }

                rbt.setColor(sibling, RED); // 兄弟變紅

                if (rbt.colorOf(parent) == RED || rbt.getRoot() == parent) {
                    if (Env.debug)
                        System.out.printf("[delete] make parent %s => BLACK and terminate while-loop\n",
                                rbt.getNodeString(parent));

                    rbt.setColor(parent, BLACK); // 父親變黑
                    return;
                } else {
                    // CLRS do this "x = x.p", rather than "w = x.p.p.right"
                    sibling = parent.getSibling();
                    if (Env.debug)
                        System.out.println("[delete] move sibling pointer to " + rbt.getNodeString(sibling));
                    continue;
                }
            }

            RedBlackTreeNode<E> outsideChild = isSiblingLeft ? siblingLChild : siblingRChild;
            RedBlackTreeNode<E> interiorChild = isSiblingLeft ? siblingRChild : siblingLChild;
            boolean outsideChildColor = rbt.colorOf(outsideChild);
            boolean interiorChildColor = rbt.colorOf(interiorChild);

            // Case 3: x’s sibling w is black, w’s interior child is red, and w’s outside child is black
            if (outsideChildColor == BLACK && interiorChildColor == RED) {
                if (Env.debug) {
                    System.out.printf("[delete] case3: sibling %s is black, its outsideChild %s is BLACK, interiorChild %s is RED\n",
                            rbt.getNodeString(sibling), rbt.getNodeString(outsideChild), rbt.getNodeString(interiorChild));
                    System.out.printf("[delete] make interiorChild %s => BLACK, sibling %s => RED\n",
                            rbt.getNodeString(interiorChild), rbt.getNodeString(sibling));
                }
                rbt.setColor(interiorChild, BLACK); // 內子變黑
                rbt.setColor(sibling, RED); // 兄弟變紅

                RotationState state = isSiblingLeft ? new RrRotation() : new LlRotation();
                state.rotate(rbt, sibling); // 兄弟旋轉

                // CLRS: w = isSiblingLeft ? x.p.left : x.p.right;
                sibling = isSiblingLeft ? parent.getLeftChild() : parent.getRightChild();

                if (Env.debug)
                    System.out.println("[delete] move sibling pointer to " + rbt.getNodeString(sibling));
                continue;
            }

            // Case 4: x’s sibling w is black, and w’s outside child is red
            if (outsideChildColor == RED) {
                if (Env.debug) {
                    System.out.printf("[delete] case3: sibling w is black, and its outsideChild %s is RED\n",
                            rbt.getNodeString(outsideChild));
                    System.out.printf("[delete] make sibling color %s same as parent => %s, and outsideChild %s => BLACK\n",
                            rbt.getNodeString(sibling), rbt.getNodeString(parent), rbt.getNodeString(outsideChild));
                }
                rbt.setColor(sibling, rbt.colorOf(parent)); // 兄弟變為父親的顏色
                rbt.setColor(parent, BLACK); // 父親變黑
                rbt.setColor(outsideChild, BLACK); // 外子變黑

                RotationState state = isSiblingLeft ? new LlRotation() : new RrRotation();
                state.rotate(rbt, parent); // 父親旋轉
            }

            return;
        }
    }
}
