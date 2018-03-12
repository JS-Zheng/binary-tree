package com.jszheng.heap;

import com.jszheng.node.TreeNode;

public interface DoubleEndedHeap<E extends Comparable<? super E>, Node extends TreeNode<E>>
        extends MinHeap<E, Node>, MaxHeap<E, Node> {
}
