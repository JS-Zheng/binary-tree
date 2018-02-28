package com.jszheng.base.completebt;


import com.jszheng.base.BinaryTree;
import com.jszheng.base.BtDecorator;
import com.jszheng.insertion.InsertionAlgo;
import com.jszheng.search.SearchAlgo;

public class CompleteBinaryTree<E> extends BtDecorator<E> {

    public CompleteBinaryTree(BinaryTree<E> component) {
        super(component);
    }

    @Override
    public CompleteBinaryTree<E> copy(boolean deep) {
        return new CompleteBinaryTree<>(component.copy(deep));
    }

    @Override
    public BinaryTree<E> newTree() {
        return new CompleteBinaryTree<>(component.newTree());
    }

    @Override
    public InsertionAlgo<E> createInsertionAlgo() {
        return new CompleteBtInsertion<>();
    }

    @Override
    public SearchAlgo<E> createSearchAlgo() {
        return new LinearSearch<>();
    }
}
