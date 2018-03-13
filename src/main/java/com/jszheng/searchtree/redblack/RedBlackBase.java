package com.jszheng.searchtree.redblack;

import com.jszheng.base.LinkedBinaryTree;

public class RedBlackBase<E> extends LinkedBinaryTree<E> {

    @Override
    public LinkedBinaryTree<E> newTree() {
        return new RedBlackBase<>();
    }

    @Override
    public RedBlackTreeNode<E> getRoot() {
        return (RedBlackTreeNode<E>) super.getRoot();
    }

    @Override
    public RedBlackTreeNode<E> newNode() {
        return new RedBlackTreeNode<>();
    }
}
