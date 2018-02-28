package com.jszheng.node;

import com.jszheng.base.BinaryTreeLemma;

public interface TreeNode<E> extends Cloneable {

    TreeNode<E> copy();

    int degree();

    void deleteParent();

    boolean hasLeftChild();

    boolean hasRightChild();

    default int lChildIndex() {
        return BinaryTreeLemma.lChildIndex(getIndex());
    }

    TreeNode<E> newNode();

    default int rChildIndex() {
        return BinaryTreeLemma.rChildIndex(getIndex());
    }

    void setDataByArr(E[] data);

    void setIndex(int index, boolean withDescendant);

    void setLeftChildWithIndex(TreeNode<E> node);

    // Delegate Parent to set child index.
    void setParent(TreeNode<E> node, boolean isLeft);

    void setParent(TreeNode<E> node, boolean isLeft, boolean validateChildIndex);

    void setRightChildWithIndex(TreeNode<E> node);

    E getData();

    void setData(E item);

    int getIndex();

    void setIndex(int index);

    TreeNode<E> getLeftChild();

    int getLevel();

    TreeNode<E> getParent();

    TreeNode<E> getRightChild();

    void setRightChild(TreeNode<E> node);

    TreeNode<E> getSibling();

    boolean isLeftChild();

    void setLeftChild(TreeNode<E> node);

    boolean isRoot();
}
