package com.jszheng.base;

import com.jszheng.node.BinTreeNode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhengzhongsheng on 2017/10/7.
 */
public class LinkedTreeNode<E> implements BinTreeNode<E> {

    private E data;
    private BinTreeNode<E> lChild = null, rChild = null, parent = null;

    protected LinkedTreeNode() {
    }

    @Override
    public BinTreeNode<E> copy() {
        BinTreeNode<E> copy = null;
        try {
            copy = clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }

        return copy;
    }

    @Override
    public void deleteParent() {
        if (parent == null) return;

        BinTreeNode<E> formerParent = parent;
        boolean isOriginalLChild = isLeftChild();

        this.parent = null;

        BinTreeNode<E> pChild = isOriginalLChild ? formerParent.getLeftChild() : formerParent.getRightChild();
        if (pChild != null) {
            if (isOriginalLChild) formerParent.setLeftChild(null);
            else formerParent.setRightChild(null);
        }
    }

    @Override
    public boolean hasLeftChild() {
        return lChild != null;
    }

    @Override
    public boolean hasRightChild() {
        return rChild != null;
    }

    @Override
    public BinTreeNode<E> newNode() {
        return new LinkedTreeNode<>();
    }

    @Override
    public void setParent(BinTreeNode<E> node, boolean isLeft) {
        BinTreeNode<E> formerParent = this.parent;
        boolean isOriginalLChild = isLeftChild();

        if (formerParent == node && isLeft == isOriginalLChild)
            return;

        // 1. delete child of former parent
        if (formerParent != null)
            deleteParent();

        if (node != null) {
            // 2. Make sure this is the parent's child
            BinTreeNode<E> validateChild;

            if (isLeft)
                validateChild = node.getLeftChild();
            else
                validateChild = node.getRightChild();

            if (validateChild != this) {
                if (isLeft) {
                    node.setLeftChild(this);
                } else {
                    node.setRightChild(this);
                }
            }
        }

        // 3. set parent
        this.parent = node;
    }

    @Override
    public int getIndex() {
        if (getParent() == null) return 0;

        // Recursive
        BinTreeNode<E> parent = getParent();
        int parentIndex = parent.getIndex();

        return isLeftChild() ? BinaryTreeLemma.lChildIndex(parentIndex)
                : BinaryTreeLemma.rChildIndex(parentIndex);
    }

    @Override
    public BinTreeNode<E> getLeftChild() {
        return lChild;
    }

    @Override
    public int getLevel() {
        int level = 0;

        BinTreeNode<E> current = this;
        while (current != null) {
            level++;
            current = current.getParent();
        }

        return level;
    }

    @Override
    public BinTreeNode<E> getParent() {
        return parent;
    }

    @Override
    public BinTreeNode<E> getRightChild() {
        return rChild;
    }

    @Override
    public void setRightChild(BinTreeNode<E> node) {
        setChild(node, false);
    }

    @Override
    public BinTreeNode<E> getSibling() {
        if (isRoot())
            return null;

        return isLeftChild() ? parent.getRightChild() : parent.getLeftChild();
    }

    @Override
    public boolean isLeftChild() {
        BinTreeNode<E> parent = getParent();
        return parent == null || parent.getLeftChild() == this;
    }

    @Override
    public void setLeftChild(BinTreeNode<E> node) {
        setChild(node, true);
    }

    @Override
    public boolean isRoot() {
        return parent == null;
    }

    private void setChild(BinTreeNode<E> child, boolean isLeft) {
        if (child == this)
            throw new RuntimeException("Can't set self as subTree.");

        BinTreeNode<E> formerChild = isLeft ? lChild : rChild;

        if (formerChild == child) return;

        // 1. delete parent of former child
        if (formerChild != null)
            formerChild.deleteParent();

        // 2. delete old parent of new child (有需要才做)
        // check isLeftChild to prevent situation like set rightChild to left
        BinTreeNode<E> parentOfNewChild;
        if (child != null && (parentOfNewChild = child.getParent()) != null &&
                (parentOfNewChild != this || child.isLeftChild() != isLeft))
            child.deleteParent();

        // 3. set child
        if (isLeft)
            lChild = child;
        else
            rChild = child;

        if (child == null) return;

        // 4. validate parent of new child
        BinTreeNode<E> validateParent = child.getParent();
        if (validateParent != this)
            child.setParent(this, isLeft);
    }

    @Override
    public int degree() {
        if (lChild != null && rChild != null)
            return 2;
        else if (lChild == null && rChild == null)
            return 0;
        else
            return 1;
    }

    @Override
    public List<BinTreeNode<E>> getChildren() {
        List<BinTreeNode<E>> children = new ArrayList<>();
        if (lChild != null) children.add(lChild);
        if (rChild != null) children.add(rChild);
        return children;
    }

    @Override
    public E getData() {
        return data;
    }

    @Override
    public void setData(E item) {
        this.data = item;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;

        if (obj instanceof BinTreeNode) {
            try {
                @SuppressWarnings({"rawtypes", "unchecked"})
                BinTreeNode<E> node = (BinTreeNode<E>) obj;
                return equals(this, node);

            } catch (Exception e) {
                e.printStackTrace();
            }

            return false;
        }

        return super.equals(obj);
    }

    @Override
    @SuppressWarnings({"rawtypes", "unchecked"})
    protected BinTreeNode<E> clone() throws CloneNotSupportedException {
        return (BinTreeNode<E>) super.clone();
    }

    public BinTreeNode<E> getRoot() {
        if (isRoot())
            return this;

        BinTreeNode<E> root = null;
        BinTreeNode<E> tmp = this;

        while (tmp != null) {
            root = tmp;
            tmp = tmp.getParent();
        }
        return root;
    }

    private boolean equals(BinTreeNode<E> orig, BinTreeNode<E> node) {
        boolean result = false;

        if (orig == null && node == null)
            return true;

        else if (orig != null && node != null) {
            if (!orig.getData().equals(node.getData()))
                return false;

            if (equals(orig.getLeftChild(), node.getLeftChild()))
                result = equals(orig.getRightChild(), node.getRightChild());
        }

        return result;
    }
}
