package com.jszheng.base.complete;

import com.jszheng.base.BinaryTree;
import com.jszheng.search.SearchAlgo;
import com.jszheng.search.SearchResult;

/*
 * O(n)
 */
public class LinearSearch<E> implements SearchAlgo<E> {

    private int searchCount = 0;
    private SearchResult<E> result;

    @Override
    public SearchResult<E> search(BinaryTree<E> bt, E data) {
        if (bt == null)
            return null;

        // init per searching
        this.result = new SearchResult<>();
        this.searchCount = 0;

        bt.traverse("Level", node -> {
            searchCount++;
            E nodeData = node.getData();
            result.setSearchCount(searchCount);

            if (nodeData != null && nodeData.equals(data)) {
                result.setNode(node);
                return false;
            } else
                return true;
        });

        return result;
    }
}
