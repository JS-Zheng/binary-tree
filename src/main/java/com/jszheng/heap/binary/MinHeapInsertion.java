package com.jszheng.heap.binary;

import com.jszheng.base.BinaryTree;
import com.jszheng.heap.HeapInsertion;
import com.jszheng.node.TreeNode;

/*
 * O(log n)
 */
class MinHeapInsertion<E extends Comparable<? super E>> extends HeapInsertion<E> {

    @Override
    protected MinBinaryHeap<E> getBt() {
        BinaryTree<E> bt = super.getBt();
        return (MinBinaryHeap<E>) bt;
    }

    @Override
    protected void fixAfterInsertion(TreeNode<E> newNode) {
        getBt().upHeap(newNode, false);
    }
}
