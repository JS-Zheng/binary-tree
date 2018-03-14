package com.jszheng.searchtree.splay;

import com.jszheng.search.SearchAlgo;
import com.jszheng.searchtree.BinarySearchTree;
import com.jszheng.searchtree.BstDeletion;

class SplayDeletion<E extends Comparable<? super E>> extends BstDeletion<E> {

    @Override
    protected SplayTree<E> getBt() {
        BinarySearchTree<E> bst = super.getBt();
        return (SplayTree<E>) bst;
    }

    @Override
    protected SearchAlgo<E> createSearchAlgo() {
        return new SplaySearch<>();
    }
}
