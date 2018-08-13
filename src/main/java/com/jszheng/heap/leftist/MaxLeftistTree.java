package com.jszheng.heap.leftist;

import com.jszheng.heap.MaxHeap;
import com.jszheng.node.BinTreeNode;

public class MaxLeftistTree<E extends Comparable<? super E>> extends LeftistTree<E> implements MaxHeap<E, BinTreeNode<E>> {

    @Override
    public E deleteMax() {
        return deleteExtrema();
    }

    @Override
    public E searchMax() {
        return searchExtrema();
    }

    @Override
    public MaxLeftistTree<E> newTree() {
        return new MaxLeftistTree<>();
    }

    @Override
    boolean isMaxHeap() {
        return true;
    }
}
