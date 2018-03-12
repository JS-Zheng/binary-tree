package com.jszheng.base.simplebalanced;

import com.jszheng.base.BinaryTree;
import com.jszheng.insertion.AbstractInsertionAlgo;
import com.jszheng.insertion.InsertionLocator;
import com.jszheng.node.BinTreeNode;

/*
 * O(Log n)
 */
class SimpleBalancedInsertion<E> extends AbstractInsertionAlgo<E> {

    @Override
    protected InsertionLocator<E> insertData(E data) {
        BinaryTree<E> bt = getBt();
        boolean isLeft = false;
        BinTreeNode<E> lastNode = null;
        BinTreeNode<E> currentNode = bt.getRoot();
        int searchCount = 0;

        // Normal Insertion.
        while (currentNode != null) {
            searchCount++;
            lastNode = currentNode;
            BinTreeNode<E> lChild = currentNode.getLeftChild();
            BinTreeNode<E> rChild = currentNode.getRightChild();

            int sL = bt.size(lChild);
            int sR = bt.size(rChild);

            if (sL <= sR) {
                currentNode = lChild;
                isLeft = true;
            } else {
                currentNode = rChild;
                isLeft = false;
            }
        }

        return new InsertionLocator<>(isLeft, lastNode, searchCount);
    }
}
