package com.jszheng.base;

import com.jszheng.node.TreeNode;
import com.jszheng.traversal.Traversable;

public interface BinaryTree<E> extends Traversable<E>, Cloneable {

    void clearRoot();

    BinaryTree<E> copy(boolean deep);

    int count();

    int count(TreeNode<E> node);

    default String getLevelString(int level) {
        return "";
    }

    TreeNode<E> getNodeByIndex(int index);

    default String getNodeString(TreeNode<E> node) {
        Object data = node != null ? node.getData() : null;
        return data != null ? data.toString() :
                (getRoot() == node ? "⊙" : " "); // Keep one space to mock null.
    }

    // root is 1.
    int height();

    int height(TreeNode<E> node);

    void insert(E... data);

    int leavesCount();

    int leavesCount(TreeNode<E> node);

    int maxCount();

    BinaryTree<E> newTree();

    TreeNode<E> search(E data);

    void setDataByArr(E[] data);

    void setRoot(E data);

    SkewedState skewedState();

    void swap();

    void swap(TreeNode<E> node);

    TreeNode<E> getLastNode();

    TreeNode<E> getRoot();

    void setRoot(TreeNode<E> root);

    boolean isEmpty();
}
