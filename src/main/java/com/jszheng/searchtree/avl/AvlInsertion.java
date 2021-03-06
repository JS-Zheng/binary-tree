package com.jszheng.searchtree.avl;

import com.jszheng.Env;
import com.jszheng.base.BinaryTree;
import com.jszheng.node.BinTreeNode;
import com.jszheng.searchtree.BstInsertion;

import java.util.Stack;

/*
 * O(Log n)
 */
class AvlInsertion<E extends Comparable<? super E>> extends BstInsertion<E> {

    @Override
    protected AvlTree<E> getBt() {
        BinaryTree<E> bt = super.getBt();
        return (AvlTree<E>) bt;
    }

    @Override
    protected void fixAfterInsertion(BinTreeNode<E> newNode) {
        AvlTree<E> avlTree = getBt();
        Stack<Boolean> stack = new Stack<>();
        BinTreeNode<E> lastNode;
        BinTreeNode<E> currentNode = newNode;

        while (currentNode != null) {
            lastNode = currentNode;
            currentNode = currentNode.getParent();

            if (currentNode == null)
                break;

            int bf = avlTree.getBalanceFactor(currentNode);
            if (bf < -1 || bf > 1) {
                boolean isGrandChildLeft = stack.pop();
                // Unbalanced
                if (Env.debug)
                    System.out.printf("[insert] node: %s is unbalanced -- BF: %d\n"
                            , currentNode.getData(), bf);
                avlTree.handleUnbalancedNode(currentNode, bf, isGrandChildLeft);
                break; // Important!
            }

            stack.push(lastNode.isLeftChild());
        }
    }
}
