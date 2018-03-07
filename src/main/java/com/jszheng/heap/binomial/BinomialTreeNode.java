package com.jszheng.heap.binomial;

import com.jszheng.node.TreeNode;

import java.util.ArrayList;
import java.util.List;

public class BinomialTreeNode<E extends Comparable<? super E>>
        implements Comparable<BinomialTreeNode<E>>, TreeNode<E> {

    // 在成為其他節點的子節點後，是否曾失去過 child
    public boolean childCut;
    E data;
    int degree = 0;
    BinomialTreeNode<E> parent = null;
    BinomialTreeNode<E> child = null;
    BinomialTreeNode<E> lLink = this;
    BinomialTreeNode<E> rLink = this;
    boolean isRLinkCircular = true;
    boolean isLLinkCircular = true;

    BinomialTreeNode() {
    }

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

        if (tmp == null) return children;

        do {
            children.add(tmp);
            tmp = tmp.rLink;
        } while (tmp != child);

        return children;
    }

    public E getData() {
        return data;
    }

    @Override
    public void setData(E data) {
        this.data = data;
    }

    public BinomialTreeNode<E> getChild() {
        return child;
    }

    public BinomialTreeNode<E> getLeftSibling() {
        return getSibling(false);
    }

    public BinomialTreeNode<E> getParent() {
        return parent;
    }

    public BinomialTreeNode<E> getRightSibling() {
        return getSibling(true);
    }

    public boolean isChildCut() {
        return childCut;
    }

    private BinomialTreeNode<E> getSibling(boolean rSibling) {
        if (rSibling && isRLinkCircular) return null;
        else if (!rSibling && isLLinkCircular) return null;
        else return rSibling ? rLink : lLink;
    }
}
