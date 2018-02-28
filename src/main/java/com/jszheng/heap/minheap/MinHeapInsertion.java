package com.jszheng.heap.minheap;

import com.jszheng.base.BinaryTree;
import com.jszheng.heap.HeapInsertion;
import com.jszheng.node.TreeNode;

public class MinHeapInsertion<E extends Comparable<? super E>> extends HeapInsertion<E> {

    @Override
    protected MinHeap<E> getBt() {
        BinaryTree<E> bt = super.getBt();
        return (MinHeap<E>) bt;
    }

    @Override
    protected void fixAfterInsertion(TreeNode<E> newNode) {
        getBt().upHeap(newNode, false);
    }
}
