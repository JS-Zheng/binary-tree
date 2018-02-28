package com.jszheng.searchtree.rbtree;

import com.jszheng.Env;
import com.jszheng.node.TreeNode;
import com.jszheng.searchtree.BinarySearchTree;
import com.jszheng.searchtree.BstDeletion;
import com.jszheng.searchtree.rotation.LlRotation;
import com.jszheng.searchtree.rotation.RotationState;
import com.jszheng.searchtree.rotation.RrRotation;

import static com.jszheng.searchtree.rbtree.RedBlackTree.Color.BLACK;
import static com.jszheng.searchtree.rbtree.RedBlackTree.Color.RED;


public class RedBlackDeletion<E extends Comparable<? super E>> extends BstDeletion<E> {

    @Override
    protected RedBlackTree<E> getBt() {
        BinarySearchTree<E> bst = super.getBt();
        return (RedBlackTree<E>) bst;
    }

    @Override
    protected void fixAfterDeletion(TreeNode<E> parent, TreeNode<E> sibling, TreeNode<E> targetNode, boolean isTargetLeft, int degree) {
        RedBlackTree<E> rbt = getBt();

        RedBlackTree.Color targetColor = rbt.colorOf(targetNode);

        if (Env.debug) {
            System.out.println("===== Delete Node: " + targetNode.getData() + " =====");
            System.out.println("Color: " + rbt.colorOf(targetNode));
            System.out.println("Degree: " + degree);
            if (parent != null)
                System.out.println("Parent: " + parent.getData());
            if (sibling != null)
                System.out.println("Sibling: " + sibling.getData());
            System.out.println("=========================");
        }

        if (targetColor == BLACK) {
            if (degree == 1) {
                handleDegreeOne(parent, isTargetLeft);
            } else {
                handleLeaf(sibling);
            }
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

        if (Env.debug) {
            System.out.println("handle Degree One");
            System.out.println("CurrentNode: " + currentNode.getData());
            System.out.println();
        }

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
                if (Env.debug) System.out.println("Case1: Sibling is RED");

                rbt.putColor(parent, RED); // 父親變紅
                rbt.putColor(sibling, BLACK); // 兄弟變黑

                RotationState state = isSiblingLeft ? new LlRotation() : new RrRotation();
                state.rotate(rbt, parent); // 父親旋轉 -> 提升 sibling

                sibling = isSiblingLeft ? parent.getLeftChild() : parent.getRightChild();
                continue;
            }

            if (Env.debug) System.out.println("Sibling is Black");

            TreeNode<E> siblingLChild = sibling.getLeftChild();
            TreeNode<E> siblingRChild = sibling.getRightChild();
            RedBlackTree.Color siblingLColor = rbt.colorOf(siblingLChild);
            RedBlackTree.Color siblingRColor = rbt.colorOf(siblingRChild);

            // Case 2 -- sibling and both child of it are BLACK
            if (siblingLColor == BLACK && siblingRColor == BLACK) {
                if (Env.debug) System.out.println("Case2: Both child of sibling are BLACK");

                rbt.putColor(sibling, RED); // 兄弟變紅

                if (rbt.colorOf(parent) == RED || rbt.getRoot() == parent) {
                    rbt.putColor(parent, BLACK); // 父親變黑
                    return;
                } else {
                    sibling = parent.getSibling();
                    continue;
                }
            }

            TreeNode<E> outsideChild = isSiblingLeft ? siblingLChild : siblingRChild;
            TreeNode<E> interiorChild = isSiblingLeft ? siblingRChild : siblingLChild;
            RedBlackTree.Color outsideChildColor = rbt.colorOf(outsideChild);
            RedBlackTree.Color interiorChildColor = rbt.colorOf(interiorChild);

            // Case 3 -- Outside BLACK, Interior RED
            if (outsideChildColor == BLACK && interiorChildColor == RED) {
                rbt.putColor(sibling, RED); // 兄弟變紅
                rbt.putColor(interiorChild, BLACK); // 內子變黑
                RotationState state = isSiblingLeft ? new RrRotation() : new LlRotation();
                state.rotate(rbt, sibling); // 兄弟轉轉
                sibling = isSiblingLeft ? parent.getLeftChild() : parent.getRightChild();
                continue;
            }

            // Case 4 -- Outside RED
            if (outsideChildColor == RED) {
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
