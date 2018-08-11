package com.jszheng.searchtree;

import com.jszheng.base.BinaryTree;
import com.jszheng.insertion.AbstractInsertionAlgo;
import com.jszheng.search.InsertableSearchResult;

/*
 * O(Log n)
 */
public class BstInsertion<E extends Comparable<? super E>> extends AbstractInsertionAlgo<E> {

    @Override
    public boolean insert(BinaryTree<E> bt, E data) {
        return data != null && super.insert(bt, data);
    }

    @Override
    protected final InsertableSearchResult<E> insertData(E data) {
        BstSearch<E> algo = createSearchAlgo();
        return algo.search(getBt(), data);
    }

    @Override
    protected BinarySearchTree<E> getBt() {
        BinaryTree<E> bt = super.getBt();
        return (BinarySearchTree<E>) bt;
    }

    @Override
    protected boolean validateBt(E data) {
        boolean defaultResult = super.validateBt(data);
        return defaultResult && data != null;
    }

    protected BstSearch<E> createSearchAlgo() {
        return new BstSearch<>();
    }
}
