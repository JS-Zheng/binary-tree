package com.jszheng.base;

import com.jszheng.node.BinTreeNode;
import com.jszheng.traversal.TraversalNodeHandler;

import java.util.List;

public abstract class BtDecorator<E> extends AbstractBinaryTree<E> {

    protected BinaryTree<E> component;

    public BtDecorator(BinaryTree<E> component) {
        this.component = component;
    }

    @Override
    public void clearRoot() {
        component.clearRoot();
    }

    @Override
    public BinTreeNode<E> getNodeByIndex(int index) {
        return component.getNodeByIndex(index);
    }

    @Override
    public BinTreeNode<E> getRoot() {
        return component.getRoot();
    }

    @Override
    public void setRoot(BinTreeNode<E> root) {
        component.setRoot(root);
    }

    @Override
    public void setDataByArr(E[] data) {
        component.setDataByArr(data);
    }

    @Override
    public void setRoot(E data) {
        component.setRoot(data);
    }

    @Override
    public BinTreeNode<E> getLastNode() {
        return component.getLastNode();
    }

    @Override
    public BinTreeNode<E> newNode() {
        return component.newNode();
    }

    @Override
    public int size() {
        return component.size();
    }

    @Override
    public boolean isEmpty() {
        return component.isEmpty();
    }

    @Override
    public int size(BinTreeNode<E> node) {
        return component.size(node);
    }

    @Override
    public int height() {
        return component.height();
    }

    @Override
    public int height(BinTreeNode<E> node) {
        return component.height(node);
    }

    @Override
    public int leavesCount() {
        return component.leavesCount();
    }

    @Override
    public int leavesCount(BinTreeNode<E> node) {
        return component.leavesCount(node);
    }

    @Override
    public int maxCount() {
        return component.maxCount();
    }

    @Override
    public SkewedState skewedState() {
        return component.skewedState();
    }

    @Override
    public void swap() {
        component.swap();
    }

    @Override
    public void swap(BinTreeNode<E> node) {
        component.swap(node);
    }

    @Override
    public List<BinTreeNode<E>> traverse(Class clz) {
        return component.traverse(clz);
    }

    @Override
    public List<BinTreeNode<E>> traverse(String order, boolean recursive, boolean isFullBtMode, TraversalNodeHandler<E> handler) {
        return component.traverse(order, recursive, isFullBtMode, handler);
    }
}
