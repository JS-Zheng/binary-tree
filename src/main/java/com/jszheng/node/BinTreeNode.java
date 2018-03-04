package com.jszheng.node;

import com.jszheng.base.BinaryTreeLemma;

public interface BinTreeNode<E> extends TreeNode<E> {

    BinTreeNode<E> copy();

    void deleteParentAndCheckItsChild();

    boolean hasLeftChild();

    boolean hasRightChild();

    default int lChildIndex() {
        return BinaryTreeLemma.lChildIndex(getIndex());
    }

    BinTreeNode<E> newNode();

    default int rChildIndex() {
        return BinaryTreeLemma.rChildIndex(getIndex());
    }

    void setDataByArr(E[] data);

    void setIndex(int index, boolean withDescendant);

    void setLeftChildWithIndex(BinTreeNode<E> node);

    // Delegate Parent to set child index.
    void setParent(BinTreeNode<E> node, boolean isLeft);

    void setParent(BinTreeNode<E> node, boolean isLeft, boolean validateChildIndex);

    void setRightChildWithIndex(BinTreeNode<E> node);

    int getIndex();

    void setIndex(int index);

    BinTreeNode<E> getLeftChild();

    int getLevel();

    BinTreeNode<E> getParent();

    BinTreeNode<E> getRightChild();

    void setRightChild(BinTreeNode<E> node);

    BinTreeNode<E> getSibling();

    boolean isLeftChild();

    void setLeftChild(BinTreeNode<E> node);

    boolean isRoot();
}
