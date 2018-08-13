package com.jszheng.base.complete;

import com.jszheng.base.LinkedBinaryTree;
import com.jszheng.insertion.InsertionAlgo;
import com.jszheng.search.SearchAlgo;

public class CompleteBtImpl<E> extends LinkedBinaryTree<E> implements CompleteBinaryTree<E> {

    @Override
    public CompleteBtImpl<E> newTree() {
        return new CompleteBtImpl<>();
    }

    @Override
    protected SearchAlgo<E> createSearchAlgo() {
        return new LinearSearch<>();
    }

    @Override
    protected InsertionAlgo<E> createInsertionAlgo() {
        return new CompleteBtInsertion<>();
    }
}
