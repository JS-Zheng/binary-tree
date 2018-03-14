package com.jszheng.searchtree;

import com.jszheng.base.BinaryTree;
import com.jszheng.insertion.AbstractInsertionAlgo;
import com.jszheng.search.SearchAlgo;
import com.jszheng.search.SearchResult;

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
    protected final SearchResult<E> insertData(E data) {
        SearchAlgo<E> algo = createSearchAlgo();
        return algo.search(getBt(), data);
    }

    @Override
    protected boolean validateBt(E data) {
        boolean defaultResult = super.validateBt(data);
        return defaultResult && data != null;
    }

    protected SearchAlgo<E> createSearchAlgo() {
        return new BstSearch<>();
    }


}
