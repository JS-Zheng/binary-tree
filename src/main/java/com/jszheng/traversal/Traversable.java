package com.jszheng.traversal;

import com.jszheng.node.TreeNode;

import java.util.List;

public interface Traversable<E> {

    // Support default order.
    List<TreeNode<E>> traverse(Class clz);

    default List<TreeNode<E>> traverse() {
        return traverse("level");
    }

    default List<TreeNode<E>> traverse(String order) {
        return traverse(order, false);
    }

    default List<TreeNode<E>> traverse(String order, TraversalNodeHandler<E> handler) {
        return traverse(order, false, false, handler);
    }

    default List<TreeNode<E>> traverse(String order, boolean recursive) {
        return traverse(order, recursive, false, null);
    }

    List<TreeNode<E>> traverse(String order, boolean recursive,
                               boolean isFullBtMode, TraversalNodeHandler<E> handler);
}
