package com.jszheng.node;

import java.util.List;

public interface TreeNode<E> extends Cloneable {

    int degree();

    void deleteParent();

    // prevent redundant type cast
    List<? extends TreeNode<E>> getChildren();

    E getData();

    void setData(E item);
}
