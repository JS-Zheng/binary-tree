package com.jszheng.searchtree.redblack;

import com.jszheng.base.LinkedTreeNode;

public class RedBlackTreeNode<E> extends LinkedTreeNode<E> {

    boolean color;

    @Override
    public RedBlackTreeNode<E> newNode() {
        return new RedBlackTreeNode<>();
    }

    @Override
    public RedBlackTreeNode<E> getLeftChild() {
        return (RedBlackTreeNode<E>) super.getLeftChild();
    }

    @Override
    public RedBlackTreeNode<E> getParent() {
        return (RedBlackTreeNode<E>) super.getParent();
    }

    @Override
    public RedBlackTreeNode<E> getRightChild() {
        return (RedBlackTreeNode<E>) super.getRightChild();
    }

    @Override
    public RedBlackTreeNode<E> getSibling() {
        return (RedBlackTreeNode<E>) super.getSibling();
    }
}
