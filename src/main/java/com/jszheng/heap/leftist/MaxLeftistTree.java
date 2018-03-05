package com.jszheng.heap.leftist;

import com.jszheng.base.BinaryTree;
import com.jszheng.heap.MaxHeap;
import com.jszheng.node.BinTreeNode;

public class MaxLeftistTree<E extends Comparable<? super E>> extends LeftistTree<E> implements MaxHeap<E, BinTreeNode<E>> {

    public MaxLeftistTree(BinaryTree<E> component) {
        super(component);
    }

    @Override
    public BinaryTree<E> copy(boolean deep) {
        return new MaxLeftistTree<>(component.copy(deep));
    }

    @Override
    public BinaryTree<E> newTree() {
        return new MaxLeftistTree<>(component.newTree());
    }

    @Override
    public E deleteMax() {
        return deleteExtrema();
    }

    @Override
    public E searchMax() {
        return searchExtrema();
    }

    @Override
    boolean isMaxHeap() {
        return true;
    }
}
