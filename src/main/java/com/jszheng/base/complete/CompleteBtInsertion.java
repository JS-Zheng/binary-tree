package com.jszheng.base.complete;

import com.jszheng.insertion.AbstractInsertionAlgo;
import com.jszheng.insertion.InsertionLocator;

/*
 * O(n)
 */
public class CompleteBtInsertion<E> extends AbstractInsertionAlgo<E> {

    private InsertionLocator<E> locator;

    @Override
    public InsertionLocator<E> insertData(E data) {
        locator = null; // Must do this.
        final int[] searchCount = {0};

        getBt().traverse("Level", node -> {
            searchCount[0]++;

            if (!node.hasLeftChild()) {
                locator = new InsertionLocator<>(true, node, searchCount[0]);
                return false;
            }

            if (!node.hasRightChild()) {
                locator = new InsertionLocator<>(false, node, searchCount[0]);
                return false;
            }

            return true;
        });

        return locator;
    }
}
