package com.jszheng.searchtree.redblack;

import com.jszheng.Env;
import com.jszheng.node.TreeNode;
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
    protected void fixAfterDeletion(TreeNode<E> parent, TreeNode<E> sibling, TreeNode<E> targetNode, boolean isTargetLeft, int degree) {
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

    private void handleDegreeOne(TreeNode<E> parent, boolean isTargetLeft) {
        RedBlackTree<E> rbt = getBt();

        TreeNode<E> currentNode;
        if (parent == null)
            currentNode = rbt.getRoot();
        else
            currentNode = isTargetLeft ? parent.getLeftChild() : parent.getRightChild();

        rbt.putColor(currentNode, BLACK);
    }

    private void handleLeaf(TreeNode<E> sibling) {
        RedBlackTree<E> rbt = getBt();

        while (true) {
            RedBlackTree.Color siblingColor = rbt.colorOf(sibling);
            TreeNode<E> parent = sibling.getParent();
            boolean isSiblingLeft = sibling.isLeftChild();

            // Case 1 -- sibling is RED (parent 必為 BLACK)
            if (siblingColor == RED) {
                if (Env.debug) {
                    System.out.printf("[delete] case1: sibling %s is RED\n",
                            rbt.getNodeString(sibling));
                    System.out.printf("[delete] make parent %s => RED, sibling %s => BLACK\n",
                            rbt.getNodeString(parent), rbt.getNodeString(sibling));
                }

                rbt.putColor(parent, RED); // 父親變紅
                rbt.putColor(sibling, BLACK); // 兄弟變黑

                RotationState state = isSiblingLeft ? new LlRotation() : new RrRotation();
                state.rotate(rbt, parent); // 父親旋轉 -> 提升 sibling

                sibling = isSiblingLeft ? parent.getLeftChild() : parent.getRightChild();
                if (Env.debug)
                    System.out.println("[delete] move sibling pointer to " + rbt.getNodeString(sibling));
                continue;
            }

            TreeNode<E> siblingLChild = sibling.getLeftChild();
            TreeNode<E> siblingRChild = sibling.getRightChild();
            RedBlackTree.Color siblingLColor = rbt.colorOf(siblingLChild);
            RedBlackTree.Color siblingRColor = rbt.colorOf(siblingRChild);

            // Case 2 -- sibling and both child of it are BLACK
            if (siblingLColor == BLACK && siblingRColor == BLACK) {
                if (Env.debug)
                    System.out.printf("[delete] case2: both child of sibling %s are BLACK\n",
                            rbt.getNodeString(sibling));

                rbt.putColor(sibling, RED); // 兄弟變紅

                if (rbt.colorOf(parent) == RED || rbt.getRoot() == parent) {
                    if (Env.debug)
                        System.out.printf("[delete] make parent %s => BLACK, sibling %s => RED\n",
                                rbt.getNodeString(parent), rbt.getNodeString(sibling));

                    rbt.putColor(parent, BLACK); // 父親變黑
                    return;
                } else {
                    sibling = parent.getSibling();
                    if (Env.debug)
                        System.out.println("[delete] move sibling pointer to " + rbt.getNodeString(sibling));
                    continue;
                }
            }

            TreeNode<E> outsideChild = isSiblingLeft ? siblingLChild : siblingRChild;
            TreeNode<E> interiorChild = isSiblingLeft ? siblingRChild : siblingLChild;
            RedBlackTree.Color outsideChildColor = rbt.colorOf(outsideChild);
            RedBlackTree.Color interiorChildColor = rbt.colorOf(interiorChild);

            // Case 3 -- Outside BLACK, Interior RED
            if (outsideChildColor == BLACK && interiorChildColor == RED) {
                if (Env.debug) {
                    System.out.printf("[delete] case3: outsideChild %s is BLACK, interiorChild %s is RED\n",
                            rbt.getNodeString(outsideChild), rbt.getNodeString(interiorChild));
                    System.out.printf("[delete] make sibling %s => RED, interiorChild %s => BLACK\n",
                            rbt.getNodeString(sibling), rbt.getNodeString(interiorChild));
                }
                rbt.putColor(sibling, RED); // 兄弟變紅
                rbt.putColor(interiorChild, BLACK); // 內子變黑
                RotationState state = isSiblingLeft ? new RrRotation() : new LlRotation();
                state.rotate(rbt, sibling); // 兄弟轉轉
                sibling = isSiblingLeft ? parent.getLeftChild() : parent.getRightChild();
                if (Env.debug)
                    System.out.println("[delete] move sibling pointer to " + rbt.getNodeString(sibling));
                continue;
            }

            // Case 4 -- Outside RED
            if (outsideChildColor == RED) {
                if (Env.debug) {
                    System.out.printf("[delete] case3: outsideChild %s is RED\n",
                            rbt.getNodeString(outsideChild));
                    System.out.printf("[delete] make sibling color %s same as parent => %s, and outsideChild %s => BLACK\n",
                            rbt.getNodeString(sibling), rbt.getNodeString(parent), rbt.getNodeString(outsideChild));
                }
                rbt.putColor(sibling, rbt.colorOf(parent));
                rbt.putColor(parent, BLACK); // 父親變黑
                rbt.putColor(outsideChild, BLACK); // 外子變黑
                RotationState state = isSiblingLeft ? new LlRotation() : new RrRotation();
                state.rotate(rbt, parent); // 父親旋轉
            }

            return;
        }
    }
}
