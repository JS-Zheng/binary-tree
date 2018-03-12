package com.jszheng.heap.mway;

import com.jszheng.heap.MinHeap;
import com.jszheng.node.TreeNode;

public interface MinReducibleHeap<E extends Comparable<? super E>, Node extends TreeNode<E>> extends MinHeap<E, Node> {

    void decreaseKey(Node node, E newKey);

    E delete(E value);
}
