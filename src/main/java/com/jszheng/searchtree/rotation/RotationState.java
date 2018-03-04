package com.jszheng.searchtree.rotation;

import com.jszheng.base.BinaryTree;
import com.jszheng.node.BinTreeNode;

public interface RotationState {

    <E> void rotate(BinaryTree<E> bt, BinTreeNode<E> parent);
}
