package com.jszheng.heap.binary;

import com.jszheng.base.BinaryTree;
import com.jszheng.heap.HeapInsertion;
import com.jszheng.node.BinTreeNode;

/*
 * O(log n)
 */
class MaxHeapInsertion<E extends Comparable<? super E>> extends HeapInsertion<E> {

    @Override
    protected void fixAfterInsertion(BinTreeNode<E> newNode) {
        getBt().upHeap(newNode, true);
    }

    @Override
    protected MaxBinaryHeap<E> getBt() {
        BinaryTree<E> bt = super.getBt();
        return (MaxBinaryHeap<E>) bt;
    }
}
