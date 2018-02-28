package com.jszheng.searchtree.avltree;

import com.jszheng.base.BinaryTree;
import com.jszheng.node.TreeNode;
import com.jszheng.searchtree.BstInsertion;

import java.util.Stack;

/*
 * O(Log n)
 */
public class AvlInsertion<E extends Comparable<? super E>> extends BstInsertion<E> {

    @Override
    protected AvlTree<E> getBt() {
        BinaryTree<E> bt = super.getBt();
        return (AvlTree<E>) bt;
    }

    @Override
    protected void fixAfterInsertion(TreeNode<E> newNode) {
        AvlTree<E> avlTree = getBt();
        Stack<Boolean> stack = new Stack<>();
        TreeNode<E> lastNode;
        TreeNode<E> currentNode = newNode;

        while (currentNode != null) {
            lastNode = currentNode;
            currentNode = currentNode.getParent();

            if (currentNode == null)
                break;

            int bf = avlTree.getBalanceFactor(currentNode);
            if (bf < -1 || bf > 1) {
                boolean isGrandChildLeft = stack.pop();
                avlTree.handleUnbalancedNode(currentNode, bf, isGrandChildLeft);
                break; // Important!
            }

            stack.push(lastNode.isLeftChild());
        }
    }
}
