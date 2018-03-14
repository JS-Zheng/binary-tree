package com.jszheng.searchtree.sizebalanced;

import com.jszheng.base.LinkedTreeNode;

public class SbTreeNode<E> extends LinkedTreeNode<E> {

    int size = 1;

    @Override
    public SbTreeNode<E> newNode() {
        return new SbTreeNode<>();
    }

    @Override
    public SbTreeNode<E> getLeftChild() {
        return (SbTreeNode<E>) super.getLeftChild();
    }

    @Override
    public SbTreeNode<E> getParent() {
        return (SbTreeNode<E>) super.getParent();
    }

    @Override
    public SbTreeNode<E> getRightChild() {
        return (SbTreeNode<E>) super.getRightChild();
    }

    @Override
    public SbTreeNode<E> getSibling() {
        return (SbTreeNode<E>) super.getSibling();
    }

    public int getSize() {
        return size;
    }
}
