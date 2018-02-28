package com.jszheng.node;

import com.jszheng.base.BinaryTreeLemma;

/**
 * Created by zhengzhongsheng on 2017/10/7.
 */
public class LinkedTreeNode<E> implements TreeNode<E> {

    protected E data;
    protected TreeNode<E> lChild = null, rChild = null, parent = null;
    private int index;

    public LinkedTreeNode() {
    }

    public LinkedTreeNode(E item) {
        data = item;
    }

    @SafeVarargs
    public LinkedTreeNode(E... items) { // care heap pollution
        setDataByArr(items);
    }

    @Override
    public TreeNode<E> copy() {
        TreeNode<E> copy = null;
        try {
            copy = clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }

        return copy;
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
        if (parent == null) return;

        TreeNode<E> formerParent = parent;
        boolean isOriginalLChild = isLeftChild();

        this.parent = null;
        this.setIndex(0, true);

        TreeNode<E> pChild = isOriginalLChild ? formerParent.getLeftChild() : formerParent.getRightChild();
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
    public TreeNode<E> newNode() {
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

        TreeNode<E> lChild = getLeftChild();
        TreeNode<E> rChild = getRightChild();

        if (lChild != null)
            lChild.setIndex(lChildIndex(), true);

        if (rChild != null)
            rChild.setIndex(rChildIndex(), true);
    }

    @Override
    public void setLeftChildWithIndex(TreeNode<E> node) {
        setChild(node, true, true);
    }

    @Override
    public void setParent(TreeNode<E> node, boolean isLeft) {
        setParent(node, isLeft, true);
    }

    @Override
    public void setParent(TreeNode<E> newParent, boolean isLeft, boolean validateChildIndex) {
        TreeNode<E> formerParent = this.parent;
        boolean isOriginalLChild = isLeftChild();

        if (formerParent == newParent && isLeft == isOriginalLChild)
            return;

        if (formerParent != null)
            deleteParent();

        this.parent = newParent;

        if (parent == null) {
            setIndex(0, true);
            return;
        }

        int index;
        TreeNode<E> validateChild;

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
        // In this way, parent can easily decide whether need to deleteParent when it setChild().
        this.index = index;

        if (isLeft)
            newParent.setLeftChildWithIndex(this);
        else
            newParent.setRightChildWithIndex(this);
    }

    @Override
    public void setRightChildWithIndex(TreeNode<E> node) {
        setChild(node, false, true);
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
    public int getIndex() {
        return index;
    }

    @Override
    public void setIndex(int index) {
        setIndex(index, false);
    }

    @Override
    public TreeNode<E> getLeftChild() {
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
    public TreeNode<E> getParent() {
        return parent;
    }

    @Override
    public TreeNode<E> getRightChild() {
        return rChild;
    }

    @Override
    public void setRightChild(TreeNode<E> node) {
        setChild(node, false, false);
    }

    @Override
    public TreeNode<E> getSibling() {
        if (isRoot())
            return null;

        return isLeftChild() ? parent.getRightChild() : parent.getLeftChild();
    }

    @Override
    public boolean isLeftChild() {
        return index == 0 || index % 2 == 1;
    }

    @Override
    public void setLeftChild(TreeNode<E> node) {
        setChild(node, true, false);
    }

    @Override
    public boolean isRoot() {
        return getIndex() == 0 && parent == null;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;

        if (obj instanceof TreeNode) {
            try {
                @SuppressWarnings({"rawtypes", "unchecked"})
                TreeNode<E> node = (TreeNode<E>) obj;
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
    protected TreeNode<E> clone() throws CloneNotSupportedException {
        return (TreeNode<E>) super.clone();
    }

    public TreeNode<E> getRoot() {
        if (isRoot())
            return this;

        TreeNode<E> root = null;
        TreeNode<E> tmp = this;

        while (tmp != null) {
            root = tmp;
            tmp = tmp.getParent();
        }
        return root;
    }

    private boolean equals(TreeNode<E> orig, TreeNode<E> node) {
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

    private void setChild(TreeNode<E> child, boolean isLeft, boolean adjustIndex) {
        if (child == this)
            throw new RuntimeException("Can't set self as subTree.");

        // Check Former Child.
        TreeNode<E> formerChild = isLeft ? lChild : rChild;

        if (formerChild == child) return;

        if (formerChild != null)
            formerChild.deleteParent();

        int childIndex = isLeft ? lChildIndex() : rChildIndex();

        if (child != null &&
                (child.getParent() != this || child.getIndex() != childIndex))
            child.deleteParent();

        if (isLeft)
            lChild = child;
        else
            rChild = child;

        if (child != null && adjustIndex)
            child.setIndex(childIndex, true);

        if (child == null) return;

        TreeNode<E> validateParent = child.getParent();
        if (validateParent != this)
            child.setParent(this, isLeft, adjustIndex);
    }
}
