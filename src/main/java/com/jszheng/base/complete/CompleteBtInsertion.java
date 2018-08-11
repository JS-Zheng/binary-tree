package com.jszheng.base.complete;

import com.jszheng.insertion.AbstractInsertionAlgo;
import com.jszheng.search.InsertableSearchResult;

/*
 * O(n)
 */
public class CompleteBtInsertion<E> extends AbstractInsertionAlgo<E> {

    protected InsertableSearchResult<E> result;

    @Override
    public InsertableSearchResult<E> insertData(E data) {
        result = null; // Must do this.
        final int[] searchCount = {0};

        getBt().traverse("Level", node -> {
            searchCount[0]++;

            if (!node.hasLeftChild()) {
                result = new InsertableSearchResult<>(true, node, searchCount[0]);
                return false;
            }

            if (!node.hasRightChild()) {
                result = new InsertableSearchResult<>(false, node, searchCount[0]);
                return false;
            }

            return true;
        });

        return result;
    }
}
