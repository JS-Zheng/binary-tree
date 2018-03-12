package com.jszheng.searchtree.redblack;

import com.jszheng.Env;
import com.jszheng.node.BinTreeNode;
import com.jszheng.searchtree.BinarySearchTree;
import com.jszheng.searchtree.BstDeletion;
import com.jszheng.searchtree.rotation.LlRotation;
import com.jszheng.searchtree.rotation.RotationState;
import com.jszheng.searchtree.rotation.RrRotation;

import static com.jszheng.searchtree.redblack.RedBlackTree.Color.BLACK;
import static com.jszheng.searchtree.redblack.RedBlackTree.Color.RED;

class RedBlackDeletion<E extends Comparable<? super E>> extends BstDeletion<E> {

    @Override
    protected RedBlackTree<E> getBt() {
        BinarySearchTree<E> bst = super.getBt();
        return (RedBlackTree<E>) bst;
    }

    @Override
    protected void fixAfterDeletion(BinTreeNode<E> parent, BinTreeNode<E> sibling, BinTreeNode<E> targetNode, boolean isTargetLeft, int degree) {
        RedBlackTree<E> rbt = getBt();

        RedBlackTree.Color targetColor = rbt.colorOf(targetNode);

        if (Env.debug)
            System.out.println("[delete] actually delete " + rbt.getNodeString(targetNode));

        if (targetColor == BLACK) {
            if (degree == 1)
                handleDegreeOne(parent, isTargetLeft);
            else
                handleLeaf(sibling);
        }

        rbt.removeColor(targetNode);
    }

    private void handleDegreeOne(BinTreeNode<E> parent, boolean isTargetLeft) {
        RedBlackTree<E> rbt = getBt();

        BinTreeNode<E> currentNode;
        if (parent == null)
            currentNode = rbt.getRoot();
        else
            currentNode = isTargetLeft ? parent.getLeftChild() : parent.getRightChild();

        rbt.putColor(currentNode, BLACK);
    }

    private void handleLeaf(BinTreeNode<E> sibling) {
        if (sibling == null) return;
        RedBlackTree<E> rbt = getBt();

        while (true) {
            RedBlackTree.Color siblingColor = rbt.colorOf(sibling);
            BinTreeNode<E> parent = sibling.getParent();
            boolean isSiblingLeft = sibling.isLeftChild();

            // Case 1: x’s sibling w is red
            if (siblingColor == RED) {
                if (Env.debug) {
                    System.out.printf("[delete] case1: sibling %s is RED\n",
                            rbt.getNodeString(sibling));
                    System.out.printf("[delete] make sibling %s => BLACK,  parent %s => RED\n",
                            rbt.getNodeString(parent), rbt.getNodeString(sibling));
                }

                rbt.putColor(sibling, BLACK); // 兄弟變黑
                rbt.putColor(parent, RED); // 父親變紅

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

            BinTreeNode<E> siblingLChild = sibling.getLeftChild();
            BinTreeNode<E> siblingRChild = sibling.getRightChild();
            RedBlackTree.Color siblingLColor = rbt.colorOf(siblingLChild);
            RedBlackTree.Color siblingRColor = rbt.colorOf(siblingRChild);

            // Case 2: x’s sibling w is black, and both of w’s children are black
            if (siblingLColor == BLACK && siblingRColor == BLACK) {
                if (Env.debug) {
                    System.out.printf("[delete] case2: both child of sibling %s are BLACK\n",
                            rbt.getNodeString(sibling));
                    System.out.printf("[delete] make sibling %s => RED\n",
                            rbt.getNodeString(sibling));
                }

                rbt.putColor(sibling, RED); // 兄弟變紅

                if (rbt.colorOf(parent) == RED || rbt.getRoot() == parent) {
                    if (Env.debug)
                        System.out.printf("[delete] make parent %s => BLACK and terminate while-loop\n",
                                rbt.getNodeString(parent));

                    rbt.putColor(parent, BLACK); // 父親變黑
                    return;
                } else {
                    // CLRS do this "x = x.p", rather than "w = x.p.p.right"
                    sibling = parent.getSibling();
                    if (Env.debug)
                        System.out.println("[delete] move sibling pointer to " + rbt.getNodeString(sibling));
                    continue;
                }
            }

            BinTreeNode<E> outsideChild = isSiblingLeft ? siblingLChild : siblingRChild;
            BinTreeNode<E> interiorChild = isSiblingLeft ? siblingRChild : siblingLChild;
            RedBlackTree.Color outsideChildColor = rbt.colorOf(outsideChild);
            RedBlackTree.Color interiorChildColor = rbt.colorOf(interiorChild);

            // Case 3: x’s sibling w is black, w’s interior child is red, and w’s outside child is black
            if (outsideChildColor == BLACK && interiorChildColor == RED) {
                if (Env.debug) {
                    System.out.printf("[delete] case3: sibling %s is black, its outsideChild %s is BLACK, interiorChild %s is RED\n",
                            rbt.getNodeString(sibling), rbt.getNodeString(outsideChild), rbt.getNodeString(interiorChild));
                    System.out.printf("[delete] make interiorChild %s => BLACK, sibling %s => RED\n",
                            rbt.getNodeString(interiorChild), rbt.getNodeString(sibling));
                }
                rbt.putColor(interiorChild, BLACK); // 內子變黑
                rbt.putColor(sibling, RED); // 兄弟變紅

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
                rbt.putColor(sibling, rbt.colorOf(parent)); // 兄弟變為父親的顏色
                rbt.putColor(parent, BLACK); // 父親變黑
                rbt.putColor(outsideChild, BLACK); // 外子變黑

                RotationState state = isSiblingLeft ? new LlRotation() : new RrRotation();
                state.rotate(rbt, parent); // 父親旋轉
            }

            return;
        }
    }
}
