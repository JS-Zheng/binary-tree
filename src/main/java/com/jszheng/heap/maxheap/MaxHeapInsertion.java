package com.jszheng.heap.maxheap;

import com.jszheng.base.BinaryTree;
import com.jszheng.heap.HeapInsertion;
import com.jszheng.node.TreeNode;

public class MaxHeapInsertion<E extends Comparable<? super E>> extends HeapInsertion<E> {

    @Override
    protected MaxHeap<E> getBt() {
        BinaryTree<E> bt = super.getBt();
        return (MaxHeap<E>) bt;
    }

    @Override
    protected void fixAfterInsertion(TreeNode<E> newNode) {
        getBt().upHeap(newNode, true);
    }
}
