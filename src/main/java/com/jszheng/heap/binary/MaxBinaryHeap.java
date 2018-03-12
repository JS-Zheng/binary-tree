package com.jszheng.heap.binary;

import com.jszheng.base.BinaryTree;
import com.jszheng.heap.MaxHeap;
import com.jszheng.insertion.InsertionAlgo;
import com.jszheng.node.BinTreeNode;

public class MaxBinaryHeap<E extends Comparable<? super E>> extends BinaryHeap<E> implements MaxHeap<E, BinTreeNode<E>> {

    public MaxBinaryHeap(BinaryTree<E> component) {
        super(component);
    }

    @Override
    public BinaryTree<E> copy(boolean deep) {
        return new MaxBinaryHeap<>(component.copy(deep));
    }

    @Override
    public BinaryTree<E> newTree() {
        return new MaxBinaryHeap<>(component.newTree());
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
    @SafeVarargs
    public final void insert(E... data) {
        if (isEmpty())
            constructHeapByBottomUp(data);
        else
            super.insert(data);
    }

    @Override
    protected InsertionAlgo<E> createInsertionAlgo() {
        return new MaxHeapInsertion<>();
    }

    @Override
    boolean isMaxHeap() {
        return true;
    }

    @Override
    protected void upHeap(BinTreeNode<E> node, boolean maxHeap) {
        super.upHeap(node, maxHeap);
    }
}
