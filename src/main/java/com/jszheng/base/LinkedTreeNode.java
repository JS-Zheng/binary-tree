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
    private int index;

    protected LinkedTreeNode() {
    }

    @SafeVarargs
    LinkedTreeNode(E... items) { // care heap pollution
        setDataByArr(items);
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
    public void deleteParentAndCheckItsChild() {
        if (parent == null) return;

        BinTreeNode<E> formerParent = parent;
        boolean isOriginalLChild = isLeftChild();

        this.parent = null;
        this.setIndex(0, true);

        BinTreeNode<E> pChild = isOriginalLChild ? formerParent.getLeftChild() : formerParent.getRightChild();
        if (pChild != null) {
            if (isOriginalLChild) formerParent.setLeftChildWithIndex(null);
            else formerParent.setRightChildWithIndex(null);
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

    // Need to prevent empty node has non-null child.
    @Override
    public void setDataByArr(E[] data) {
        TreeNodeConstructor<E> constructor = new TopDownTreeNodeConstructor<>(this);
        constructor.setDataByArr(data);
    }

    @Override
    public void setIndex(int index, boolean withDescendant) {
        this.index = index;

        if (!withDescendant) return;

        BinTreeNode<E> lChild = getLeftChild();
        BinTreeNode<E> rChild = getRightChild();

        if (lChild != null)
            lChild.setIndex(lChildIndex(), true);

        if (rChild != null)
            rChild.setIndex(rChildIndex(), true);
    }

    @Override
    public void setLeftChildWithIndex(BinTreeNode<E> node) {
        setChild(node, true, true);
    }

    @Override
    public void setParent(BinTreeNode<E> node, boolean isLeft) {
        setParent(node, isLeft, true);
    }

    @Override
    public void setParent(BinTreeNode<E> newParent, boolean isLeft, boolean validateChildIndex) {
        BinTreeNode<E> formerParent = this.parent;
        boolean isOriginalLChild = isLeftChild();

        if (formerParent == newParent && isLeft == isOriginalLChild)
            return;

        if (formerParent != null)
            deleteParentAndCheckItsChild();

        this.parent = newParent;

        if (parent == null) {
            setIndex(0, true);
            return;
        }

        int index;
        BinTreeNode<E> validateChild;

        if (isLeft) {
            index = parent.lChildIndex();
            validateChild = parent.getLeftChild();
        } else {
            index = parent.rChildIndex();
            validateChild = parent.getRightChild();
        }

        if (!validateChildIndex) return;

        // e.g., already call setChild()
        if (validateChild == this) {

            if (validateChild.getIndex() != index)
                throw new RuntimeException("Index is not correct.");

            return;
        }

        // Don't setIndex() directly, otherwise it will waste time.
        // Delegate to parent and just set one index,
        // In this way, parent can easily decide whether need to deleteParentAndCheckItsChild when it setChild().
        this.index = index;

        if (isLeft)
            newParent.setLeftChildWithIndex(this);
        else
            newParent.setRightChildWithIndex(this);
    }

    @Override
    public void setRightChildWithIndex(BinTreeNode<E> node) {
        setChild(node, false, true);
    }

    @Override
    public int getIndex() {
        return index;
    }

    @Override
    public void setIndex(int index) {
        setIndex(index, false);
    }

    @Override
    public BinTreeNode<E> getLeftChild() {
        return lChild;
    }

    @Override
    public int getLevel() {
        int index = getIndex();
        if (index == 0)
            return 1;

        int level = 1;
        while (index >= BinaryTreeLemma.maxCount(level)) {
            level++;
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
        setChild(node, false, false);
    }

    @Override
    public BinTreeNode<E> getSibling() {
        if (isRoot())
            return null;

        return isLeftChild() ? parent.getRightChild() : parent.getLeftChild();
    }

    @Override
    public boolean isLeftChild() {
        return index == 0 || index % 2 == 1;
    }

    @Override
    public void setLeftChild(BinTreeNode<E> node) {
        setChild(node, true, false);
    }

    @Override
    public boolean isRoot() {
        return getIndex() == 0 && parent == null;
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
    public void deleteParent() {
        parent = null;
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

    private void setChild(BinTreeNode<E> child, boolean isLeft, boolean adjustIndex) {
        if (child == this)
            throw new RuntimeException("Can't set self as subTree.");

        // Check Former Child.
        BinTreeNode<E> formerChild = isLeft ? lChild : rChild;

        if (formerChild == child) return;

        if (formerChild != null)
            formerChild.deleteParentAndCheckItsChild();

        int childIndex = isLeft ? lChildIndex() : rChildIndex();

        if (child != null &&
                (child.getParent() != this || child.getIndex() != childIndex))
            child.deleteParentAndCheckItsChild();

        if (isLeft)
            lChild = child;
        else
            rChild = child;

        if (child != null && adjustIndex)
            child.setIndex(childIndex, true);

        if (child == null) return;

        BinTreeNode<E> validateParent = child.getParent();
        if (validateParent != this)
            child.setParent(this, isLeft, adjustIndex);
    }
}
