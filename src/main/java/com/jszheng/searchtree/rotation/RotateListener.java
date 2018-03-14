package com.jszheng.searchtree.rotation;

import com.jszheng.node.BinTreeNode;

public interface RotateListener<E> {

    void onRotateLeft(BinTreeNode<E> parent, BinTreeNode<E> pivot, BinTreeNode<E> lChild);

    void onRotateRight(BinTreeNode<E> parent, BinTreeNode<E> pivot, BinTreeNode<E> rChild);
}
