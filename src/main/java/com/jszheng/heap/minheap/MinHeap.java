package com.jszheng.heap.minheap;

import com.jszheng.base.BinaryTree;
import com.jszheng.heap.BinaryHeap;
import com.jszheng.insertion.InsertionAlgo;
import com.jszheng.node.TreeNode;

public class MinHeap<E extends Comparable<? super E>> extends BinaryHeap<E> {

    public MinHeap(BinaryTree<E> component) {
        super(component);
    }

    public E deleteMin() {
        return deleteExtrema(false);
    }

    @SafeVarargs
    @Override
    public final void insert(E... data) {
        if (isEmpty()) {
            constructHeapByBottomUp(data, false);
        } else {
            super.insert(data);
        }
    }

    @Override
    public BinaryTree<E> newTree() {
        return new MinHeap<>(component.newTree());
    }

    @Override
    public InsertionAlgo<E> createInsertionAlgo() {
        return new MinHeapInsertion<>();
    }

    public E searchMin() {
        return searchExtrema();
    }

    @Override
    protected void upHeap(TreeNode<E> node, boolean maxHeap) {
        super.upHeap(node, maxHeap);
    }
}
