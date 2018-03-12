package com.jszheng.heap.binary;

import com.jszheng.base.BinaryTree;
import com.jszheng.heap.MinHeap;
import com.jszheng.insertion.InsertionAlgo;
import com.jszheng.node.BinTreeNode;

public class MinBinaryHeap<E extends Comparable<? super E>> extends BinaryHeap<E> implements MinHeap<E, BinTreeNode<E>> {

    public MinBinaryHeap(BinaryTree<E> component) {
        super(component);
    }

    @Override
    public BinaryTree<E> copy(boolean deep) {
        return new MinBinaryHeap<>(component.copy(deep));
    }

    @Override
    public BinaryTree<E> newTree() {
        return new MinBinaryHeap<>(component.newTree());
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
    @SafeVarargs
    public final void insert(E... data) {
        if (isEmpty())
            constructHeapByBottomUp(data);
        else
            super.insert(data);
    }

    @Override
    protected InsertionAlgo<E> createInsertionAlgo() {
        return new MinHeapInsertion<>();
    }

    @Override
    boolean isMaxHeap() {
        return false;
    }

    @Override
    protected void upHeap(BinTreeNode<E> node, boolean maxHeap) {
        super.upHeap(node, maxHeap);
    }
}
