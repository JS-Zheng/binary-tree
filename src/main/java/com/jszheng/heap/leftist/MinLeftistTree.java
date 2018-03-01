package com.jszheng.heap.leftist;

import com.jszheng.base.BinaryTree;
import com.jszheng.heap.MinHeap;

public class MinLeftistTree<E extends Comparable<? super E>> extends LeftistTree<E> implements MinHeap<E> {

    public MinLeftistTree(BinaryTree<E> component) {
        super(component);
    }

    @Override
    public BinaryTree<E> copy(boolean deep) {
        return new MinLeftistTree<>(component.copy(deep));
    }

    @Override
    public BinaryTree<E> newTree() {
        return new MinLeftistTree<>(component.newTree());
    }

    @Override
    public E deleteMin() {
        return deleteExtrema();
    }

    @Override
    public E searchMin() {
        return searchExtrema();
    }

    @Override
    boolean isMaxHeap() {
        return false;
    }
}
