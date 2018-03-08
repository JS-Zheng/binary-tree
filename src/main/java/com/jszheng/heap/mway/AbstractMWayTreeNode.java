package com.jszheng.heap.mway;

import com.jszheng.node.TreeNode;

import java.util.ArrayList;
import java.util.List;

public class AbstractMWayTreeNode<E, Self extends AbstractMWayTreeNode<E, Self>> implements TreeNode<E> {

    E data;
    int degree = 0;
    Self parent = null;
    Self child = null;
    Self lLink; // need to init on subClass
    Self rLink; // need to init on subClass
    boolean isRLinkCircular = true;
    boolean isLLinkCircular = true;

    @Override
    public int degree() {
        return degree;
    }

    @Override
    public void deleteParent() {
        parent = null;
    }

    @Override
    public List<Self> getChildren() {
        List<Self> children = new ArrayList<>();
        Self tmp = child;

        if (tmp == null) return children;

        do {
            children.add(tmp);
            tmp = tmp.rLink;
        } while (tmp != child);

        return children;
    }

    @Override
    public E getData() {
        return data;
    }

    @Override
    public void setData(E data) {
        this.data = data;
    }

    public Self getChild() {
        return child;
    }

    public Self getLeftSibling() {
        return getSibling(false);
    }

    public Self getParent() {
        return parent;
    }

    public Self getRightSibling() {
        return getSibling(true);
    }

    private Self getSibling(boolean rSibling) {
        if (rSibling && isRLinkCircular) return null;
        else if (!rSibling && isLLinkCircular) return null;
        else return rSibling ? rLink : lLink;
    }
}
