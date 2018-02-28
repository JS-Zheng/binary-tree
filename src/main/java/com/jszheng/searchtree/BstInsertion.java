package com.jszheng.searchtree;

import com.jszheng.base.BinaryTree;
import com.jszheng.insertion.AbstractInsertionAlgo;
import com.jszheng.insertion.InsertionLocator;
import com.jszheng.node.TreeNode;

/*
 * O(Log n)
 */
public class BstInsertion<E extends Comparable<? super E>> extends AbstractInsertionAlgo<E> {

    @Override
    public boolean insert(BinaryTree<E> bt, E data) {
        return data != null && super.insert(bt, data);
    }

    @Override
    protected BinarySearchTree<E> getBt() {
        BinaryTree<E> bt = super.getBt();
        return (BinarySearchTree<E>) bt;
    }

    @Override
    public InsertionLocator<E> insertData(E data) {
        int searchCount = 0;
        boolean isLeft = false;
        TreeNode<E> lastNode = null;
        TreeNode<E> currentNode = getBt().getRoot();

        while (currentNode != null) {
            searchCount++;

            if (!handleNode(currentNode))
                return null;

            int compareResult = data.compareTo(currentNode.getData());
            lastNode = currentNode;

            if (compareResult < 0) {
                currentNode = currentNode.getLeftChild();
                isLeft = true;
            } else if (compareResult == 0)
                return null;
            else {
                currentNode = currentNode.getRightChild();
                isLeft = false;
            }
        }

        return new InsertionLocator<>(isLeft, lastNode, searchCount);
    }

    @Override
    protected boolean validateBt(E data) {
        boolean defaultResult = super.validateBt(data);
        return defaultResult && data != null;
    }

    // Handles each node in process of BST Comparing
    protected boolean handleNode(TreeNode<E> node) {
        // Default do nothing.
        return true;
    }
}
