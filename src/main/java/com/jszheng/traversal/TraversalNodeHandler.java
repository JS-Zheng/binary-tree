package com.jszheng.traversal;

import com.jszheng.base.BinaryTree;
import com.jszheng.node.TreeNode;

@FunctionalInterface
public interface TraversalNodeHandler<E> {

    default void afterTraversed(BinaryTree<E> bt) {

    }

    /**
     * @param node in process of traversing
     * @return false to terminate traverse
     */
    boolean handle(TreeNode<E> node);
}
