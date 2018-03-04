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

            if (bf < -1 || bf > 1) {
                BinTreeNode<E> child = avlTree.getHigherChild(currentNode);
                boolean isGrandChildLeft = avlTree.getHigherChild(child).isLeftChild();
                // Unbalanced
                if (Env.debug)
                    System.out.printf("[delete] node: %s is unbalanced -- BF: %d\n"
                            , currentNode.getData(), bf);
                avlTree.handleUnbalancedNode(currentNode, bf, isGrandChildLeft);
                break;
            }

            currentNode = currentNode.getParent();
        }
    }
}
