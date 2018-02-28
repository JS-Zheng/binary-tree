package com.jszheng.base;

import com.jszheng.node.TreeNode;
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
    public TreeNode<E> getNodeByIndex(int index) {
        return component.getNodeByIndex(index);
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
    public TreeNode<E> getLastNode() {
        return component.getLastNode();
    }

    @Override
    public TreeNode<E> getRoot() {
        return component.getRoot();
    }

    @Override
    public void setRoot(TreeNode<E> root) {
        component.setRoot(root);
    }

    @Override
    public boolean isEmpty() {
        return component.isEmpty();
    }

    @Override
    public int count() {
        return component.count();
    }

    @Override
    public int count(TreeNode<E> node) {
        return component.count(node);
    }

    @Override
    public int height() {
        return component.height();
    }

    @Override
    public int height(TreeNode<E> node) {
        return component.height(node);
    }

    @Override
    public int leavesCount() {
        return component.leavesCount();
    }

    @Override
    public int leavesCount(TreeNode<E> node) {
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
    public void swap(TreeNode<E> node) {
        component.swap(node);
    }

    @Override
    public List<TreeNode<E>> traverse(Class clz) {
        return component.traverse(clz);
    }

    @Override
    public List<TreeNode<E>> traverse(String order, boolean recursive, boolean isFullBtMode, TraversalNodeHandler<E> handler) {
        return component.traverse(order, recursive, isFullBtMode, handler);
    }
}
