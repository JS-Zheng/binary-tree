package com.jszheng.base.complete;

import com.jszheng.base.BinaryTree;
import com.jszheng.base.BtDecorator;
import com.jszheng.insertion.InsertionAlgo;
import com.jszheng.search.SearchAlgo;

public class CompleteBtImpl<E> extends BtDecorator<E, BinaryTree<E>> implements CompleteBinaryTree<E> {

    CompleteBtImpl(BinaryTree<E> component) {
        super(component);
    }

    @Override
    public CompleteBtImpl<E> copy(boolean deep) {
        return new CompleteBtImpl<>(component.copy(deep));
    }

    @Override
    public BinaryTree<E> newTree() {
        return new CompleteBtImpl<>(component.newTree());
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
