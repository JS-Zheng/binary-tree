package com.jszheng.heap;

import com.jszheng.node.TreeNode;

public interface MeldableHeap<E> extends Heap<E> {

    void merge(TreeNode<E> node);
}
