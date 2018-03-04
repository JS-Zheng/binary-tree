package com.jszheng.searchtree;

import com.jszheng.base.BinaryTree;
import com.jszheng.node.BinTreeNode;
import com.jszheng.search.SearchAlgo;
import com.jszheng.search.SearchResult;

/*
 * O(Log n)
 */
public class BstSearch<E extends Comparable<? super E>> implements SearchAlgo<E> {

    private BinaryTree<E> bt;

    @Override
    public SearchResult<E> search(BinaryTree<E> bt, E data) {
        BinTreeNode<E> currentNode;

        if (bt == null || (currentNode = bt.getRoot()) == null)
            return null;

        this.bt = bt;

        SearchResult<E> result = new SearchResult<>();
        int searchCount = 0;


        while (currentNode != null) {
            searchCount++;

            @SuppressWarnings({"rawtypes", "unchecked"})
            int compareResult = data.compareTo(currentNode.getData());

            if (compareResult < 0) {
                currentNode = currentNode.getLeftChild();
            } else if (compareResult == 0) {
                result.setNode(currentNode);
                result.setSearchCount(searchCount);

                fixAfterSearch(currentNode);
                return result;
            } else {
                currentNode = currentNode.getRightChild();
            }
        }

        result.setSearchCount(searchCount);

        return result;
    }

    public SearchResult<E> searchMax(BinaryTree<E> bt) {
        return searchExtrema(bt, true);
    }

    public SearchResult<E> searchMin(BinaryTree<E> bt) {
        return searchExtrema(bt, false);
    }

    protected BinaryTree<E> getBt() {
        return bt;
    }

    protected void fixAfterSearch(BinTreeNode<E> node) {
        // default do nothing.
    }

    private SearchResult<E> searchExtrema(BinaryTree<E> bt, boolean searchMax) {
        if (bt == null)
            return null;

        this.bt = bt;

        SearchResult<E> result = new SearchResult<>();
        int searchCount = 0;

        BinTreeNode<E> lastNode = null;
        BinTreeNode<E> currentNode = bt.getRoot();

        while (currentNode != null) {
            searchCount++;

            lastNode = currentNode;
            currentNode = searchMax ? currentNode.getRightChild() : currentNode.getLeftChild();
        }

        result.setNode(lastNode);
        result.setSearchCount(searchCount);

        fixAfterSearch(lastNode);

        return result;
    }
}
