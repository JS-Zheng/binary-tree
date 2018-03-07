package com.jszheng.heap;

import com.jszheng.node.TreeNode;

public interface MaxHeap<E extends Comparable<? super E>, Node extends TreeNode<E>> extends Heap<E, Node> {

    E deleteMax();

    E searchMax();
}
