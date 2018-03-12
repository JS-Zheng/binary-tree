package com.jszheng.node;

public interface BinTreeNode<E> extends TreeNode<E> {

    BinTreeNode<E> copy();

    void deleteParent();

    boolean hasLeftChild();

    boolean hasRightChild();

    BinTreeNode<E> newNode();

    void setDataByArr(E[] data);

    void setParent(BinTreeNode<E> node, boolean isLeft);

    int getIndex();

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
