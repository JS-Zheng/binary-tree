package com.jszheng.heap.leftist;

import com.jszheng.heap.MinHeap;
import com.jszheng.node.BinTreeNode;

public class MinLeftistTree<E extends Comparable<? super E>> extends LeftistTree<E> implements MinHeap<E, BinTreeNode<E>> {

    @Override
    public E deleteMin() {
        return deleteExtrema();
    }

    @Override
    public E searchMin() {
        return searchExtrema();
    }

    @Override
    public MinLeftistTree<E> newTree() {
        return new MinLeftistTree<>();
    }

    @Override
    boolean isMaxHeap() {
        return false;
    }
}
