package com.jszheng.base.simplebalanced;

import com.jszheng.base.BinaryTree;
import com.jszheng.base.BtDecorator;
import com.jszheng.base.completebt.LinearSearch;
import com.jszheng.insertion.InsertionAlgo;
import com.jszheng.search.SearchAlgo;

public class SimpleBalancedTree<E> extends BtDecorator<E> {

    public SimpleBalancedTree(BinaryTree<E> component) {
        super(component);
    }

    @Override
    public BinaryTree<E> copy(boolean deep) {
        return new SimpleBalancedTree<>(component.copy(deep));
    }

    @Override
    public BinaryTree<E> newTree() {
        return new SimpleBalancedTree<>(component.newTree());
    }

    @Override
    protected InsertionAlgo<E> createInsertionAlgo() {
        return new SimpleBalancedInsertion<>();
    }

    @Override
    protected SearchAlgo<E> createSearchAlgo() {
        return new LinearSearch<>();
    }
}
