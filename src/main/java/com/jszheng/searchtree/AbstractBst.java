package com.jszheng.searchtree;

import com.jszheng.Env;
import com.jszheng.base.LinkedBinaryTree;
import com.jszheng.insertion.InsertionAlgo;
import com.jszheng.search.SearchResult;

abstract class AbstractBst<E extends Comparable<? super E>> extends LinkedBinaryTree<E> implements BinarySearchTree<E> {

    protected BstDeletion<E> deletionAlgo;
    protected BstSearch<E> searchAlgo;

    @Override
    public void delete(E data) {
        delete(data, true);
    }

    @Override
    public void delete(E data, boolean replaceByLMax) {
        if (Env.debug) System.out.println("[delete] data: " + data);
        BstDeletion<E> algo = createDeletionAlgo();
        algo.setReplaceByLMax(replaceByLMax);
        algo.delete(this, data);
        if (Env.debug) System.out.println();
    }

    @Override
    public SearchResult<E> searchMax() {
        return createSearchAlgo().searchMax(this);
    }

    @Override
    public SearchResult<E> searchMin() {
        return createSearchAlgo().searchMin(this);
    }

    @Override
    protected BstSearch<E> createSearchAlgo() {
        if (searchAlgo == null)
            searchAlgo = new BstSearch<>();
        return searchAlgo;
    }

    @Override
    protected InsertionAlgo<E> createInsertionAlgo() {
        if (insertionAlgo == null)
            insertionAlgo = new BstInsertion<>();
        return insertionAlgo;
    }

    protected BstDeletion<E> createDeletionAlgo() {
        if (deletionAlgo == null)
            deletionAlgo = new BstDeletion<>();
        return deletionAlgo;
    }
}
