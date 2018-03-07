package com.jszheng.heap;

import com.jszheng.node.TreeNode;

public interface MeldableHeap<Heap, E, Node extends TreeNode<E>> extends com.jszheng.heap.Heap<E, Node> {

    void merge(Heap node);
}
