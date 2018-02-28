package com.jszheng.heap.maxheap;

import com.jszheng.base.BinaryTree;
import com.jszheng.heap.BinaryHeap;
import com.jszheng.insertion.InsertionAlgo;
import com.jszheng.node.TreeNode;

public class MaxHeap<E extends Comparable<? super E>> extends BinaryHeap<E> {

    public MaxHeap(BinaryTree<E> component) {
        super(component);
    }

    public E deleteMax() {
        return deleteExtrema(true);
    }

    @SafeVarargs
    @Override
    public final void insert(E... data) {
        if (isEmpty()) {
            constructHeapByBottomUp(data, true);
        } else {
            super.insert(data);
        }
    }

    @Override
    public BinaryTree<E> newTree() {
        return new MaxHeap<>(component.newTree());
    }

    @Override
    public InsertionAlgo<E> createInsertionAlgo() {
        return new MaxHeapInsertion<>();
    }

    public E searchMax() {
        return searchExtrema();
    }

    @Override
    protected void upHeap(TreeNode<E> node, boolean maxHeap) {
        super.upHeap(node, maxHeap);
    }
}
