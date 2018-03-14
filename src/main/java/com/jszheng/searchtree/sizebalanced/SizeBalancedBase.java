package com.jszheng.searchtree.sizebalanced;

import com.jszheng.base.LinkedBinaryTree;

public class SizeBalancedBase<E> extends LinkedBinaryTree<E> {

    @Override
    public SizeBalancedBase<E> newTree() {
        return new SizeBalancedBase<>();
    }

    @Override
    public SbTreeNode<E> getRoot() {
        return (SbTreeNode<E>) super.getRoot();
    }

    @Override
    public SbTreeNode<E> newNode() {
        return new SbTreeNode<>();
    }
}
