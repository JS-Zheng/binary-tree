package com.jszheng.searchtree;

import com.jszheng.Env;
import com.jszheng.base.BinaryTree;
import com.jszheng.base.BtDecorator;
import com.jszheng.insertion.InsertionAlgo;
import com.jszheng.search.SearchResult;

abstract class AbstractBst<E extends Comparable<? super E>, Base extends BinaryTree<E>> extends BtDecorator<E, Base> implements BinarySearchTree<E> {

    protected BstDeletion<E> deletionAlgo;
    protected BstSearch<E> searchAlgo;

    public AbstractBst(Base component) {
        super(component);
    }

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

    protected BstDeletion<E> createDeletionAlgo() {
        if (deletionAlgo == null)
            deletionAlgo = new BstDeletion<>();
        return deletionAlgo;
    }

    @Override
    protected InsertionAlgo<E> createInsertionAlgo() {
        if (insertionAlgo == null)
            insertionAlgo = new BstInsertion<>();
        return insertionAlgo;
    }

    @Override
    protected BstSearch<E> createSearchAlgo() {
        if (searchAlgo == null)
            searchAlgo = new BstSearch<>();
        return searchAlgo;
    }
}
