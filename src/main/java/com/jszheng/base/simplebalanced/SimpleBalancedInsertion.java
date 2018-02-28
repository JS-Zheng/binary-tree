package com.jszheng.base.simplebalanced;

import com.jszheng.base.BinaryTree;
import com.jszheng.insertion.AbstractInsertionAlgo;
import com.jszheng.insertion.InsertionLocator;
import com.jszheng.node.TreeNode;

/*
 * O(Log n)
 */
public class SimpleBalancedInsertion<E> extends AbstractInsertionAlgo<E> {

    @Override
    protected InsertionLocator<E> insertData(E data) {
        BinaryTree<E> bt = getBt();
        boolean isLeft = false;
        TreeNode<E> lastNode = null;
        TreeNode<E> currentNode = bt.getRoot();
        int searchCount = 0;

        // Normal Insertion.
        while (currentNode != null) {
            searchCount++;
            lastNode = currentNode;
            TreeNode<E> lChild = currentNode.getLeftChild();
            TreeNode<E> rChild = currentNode.getRightChild();

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
                    int cL = bt.count(lChild);
                    int cR = bt.count(rChild);

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
