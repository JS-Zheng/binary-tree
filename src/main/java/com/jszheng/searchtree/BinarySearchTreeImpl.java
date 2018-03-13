package com.jszheng.searchtree;

import com.jszheng.base.BinaryTree;

public class BinarySearchTreeImpl<E extends Comparable<? super E>> extends AbstractBst<E, BinaryTree<E>> {

    public BinarySearchTreeImpl(BinaryTree<E> component) {
        super(component);
    }

    @Override
    public BinaryTree<E> copy(boolean deep) {
        return new BinarySearchTreeImpl<>(component.copy(deep));
    }

    @Override
    public BinarySearchTree<E> newTree() {
        return new BinarySearchTreeImpl<>(component.newTree());
    }
}
