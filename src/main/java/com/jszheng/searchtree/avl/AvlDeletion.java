package com.jszheng.searchtree.avl;

import com.jszheng.Env;
import com.jszheng.node.BinTreeNode;
import com.jszheng.searchtree.BinarySearchTree;
import com.jszheng.searchtree.BstDeletion;

class AvlDeletion<E extends Comparable<? super E>> extends BstDeletion<E> {

    @Override
    protected AvlTree<E> getBt() {
        BinarySearchTree<E> bst = super.getBt();
        return (AvlTree<E>) bst;
    }

    @Override
    protected void fixAfterDeletion(BinTreeNode<E> parent, BinTreeNode<E> sibling, BinTreeNode<E> targetNode, boolean isTargetLeft, int degree) {
        AvlTree<E> avlTree = getBt();
        BinTreeNode<E> currentNode = parent;

        while (currentNode != null) {
            int bf = avlTree.getBalanceFactor(currentNode);

            // parent 高度不變
            if (bf == 1 || bf == -1) return;

            if (bf < -1 || bf > 1) {
                // Unbalanced
                if (Env.debug)
                    System.out.printf("[delete] node: %s is unbalanced -- BF: %d\n"
                            , currentNode.getData(), bf);

                BinTreeNode<E> child = bf == 2 ? currentNode.getLeftChild() : currentNode.getRightChild();
                int childBf = avlTree.getBalanceFactor(child);
                boolean isGrandChildLeft;

                if (bf == 2)
                    isGrandChildLeft = childBf >= 0;
                else
                    isGrandChildLeft = childBf > 0;

                avlTree.handleUnbalancedNode(currentNode, bf, isGrandChildLeft);

                // child 原先已平衡
                if (childBf == 0) return;

            }

            currentNode = currentNode.getParent();
        }
    }
}
