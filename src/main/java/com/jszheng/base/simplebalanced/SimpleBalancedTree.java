package com.jszheng.base.simplebalanced;

import com.jszheng.base.LinkedBinaryTree;
import com.jszheng.base.complete.LinearSearch;
import com.jszheng.insertion.InsertionAlgo;
import com.jszheng.search.SearchAlgo;

public class SimpleBalancedTree<E> extends LinkedBinaryTree<E> {

    @Override
    protected SearchAlgo<E> createSearchAlgo() {
        return new LinearSearch<>();
    }

    @Override
    protected InsertionAlgo<E> createInsertionAlgo() {
        return new SimpleBalancedInsertion<>();
    }
}
