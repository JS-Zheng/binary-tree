package com.jszheng.node;

import java.util.ArrayList;
import java.util.List;

public class BinomialTreeNode<E extends Comparable<? super E>>
        implements Comparable<BinomialTreeNode<E>>, TreeNode<E> {

    public E data;
    public int degree = 0;
    public BinomialTreeNode<E> parent;
    public BinomialTreeNode<E> child;
    public BinomialTreeNode<E> lSibling;
    public BinomialTreeNode<E> rSibling;

    @Override
    public int compareTo(BinomialTreeNode<E> node) {
        if (node == null || node.data == null) return 1;
        return data.compareTo(node.data);
    }

    @Override
    public int degree() {
        return degree;
    }

    @Override
    public void deleteParent() {
        parent = null;
    }

    @Override
    public List<TreeNode<E>> getChildren() {
        List<TreeNode<E>> children = new ArrayList<>();
        BinomialTreeNode<E> tmp = child;
        while (tmp != null) {
            children.add(tmp);
            tmp = tmp.rSibling;
        }
        return children;
    }

    public E getData() {
        return data;
    }

    @Override
    public void setData(E data) {
        this.data = data;
    }
}
