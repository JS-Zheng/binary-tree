package com.jszheng.traversal;

import com.jszheng.node.BinTreeNode;

import java.util.List;

public interface Traversable<E> {

    // Support default order.
    List<BinTreeNode<E>> traverse(Class clz);

    default List<BinTreeNode<E>> traverse() {
        return traverse("level");
    }

    default List<BinTreeNode<E>> traverse(String order) {
        return traverse(order, false);
    }

    default List<BinTreeNode<E>> traverse(String order, TraversalNodeHandler<E> handler) {
        return traverse(order, false, false, handler);
    }

    default List<BinTreeNode<E>> traverse(String order, boolean recursive) {
        return traverse(order, recursive, false, null);
    }

    List<BinTreeNode<E>> traverse(String order, boolean recursive,
                                  boolean isFullBtMode, TraversalNodeHandler<E> handler);
}
