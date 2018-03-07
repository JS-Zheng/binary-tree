package com.jszheng.heap;

import com.jszheng.node.TreeNode;

public interface MinHeap<E extends Comparable<? super E>, Node extends TreeNode<E>> extends Heap<E, Node> {

    E deleteMin();

    E searchMin();
}
