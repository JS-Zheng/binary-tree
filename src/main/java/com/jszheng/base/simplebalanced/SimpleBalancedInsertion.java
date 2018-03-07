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

            if (lChild == null) {
                currentNode = null;
                isLeft = true;
            } else if (rChild == null) {
                currentNode = null;
                isLeft = false;
            } else { // lChild != null && rChild != null
                int hL = bt.height(lChild);
                int hR = bt.height(rChild);

                if (hL < hR) {
                    currentNode = lChild;
                    isLeft = true;
                } else if (hL == hR) {
                    int cL = bt.size(lChild);
                    int cR = bt.size(rChild);

                    if (cL <= cR) {
                        currentNode = lChild;
                        isLeft = true;
                    } else {
                        currentNode = rChild;
                        isLeft = false;
                    }
                } else {
                    currentNode = rChild;
                    isLeft = false;
                }
            }
        }

        return new InsertionLocator<>(isLeft, lastNode, searchCount);
    }
}
