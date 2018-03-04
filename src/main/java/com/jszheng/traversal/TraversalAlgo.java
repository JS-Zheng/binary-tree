package com.jszheng.traversal;

import com.jszheng.base.BinaryTree;
import com.jszheng.node.BinTreeNode;

import java.util.List;

public interface TraversalAlgo<E> {

    List<BinTreeNode<E>> traverse(BinaryTree<E> bt);

    TraversalNodeHandler<E> getNodeHandler();

    void setNodeHandler(TraversalNodeHandler<E> handler);

    default boolean isFullBtMode() {
        return false;
    }

    default void setFullBtMode(boolean isFullMode) {
    }
}
