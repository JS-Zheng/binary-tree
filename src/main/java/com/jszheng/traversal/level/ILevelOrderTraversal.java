package com.jszheng.traversal.level;

import com.jszheng.node.BinTreeNode;
import com.jszheng.traversal.TraversalAlgo;

import java.util.List;
import java.util.Queue;

public interface ILevelOrderTraversal<E> extends TraversalAlgo<E> {

    // Template Method.
    default boolean execute(Queue<BinTreeNode<E>> queue, BinTreeNode<E> node, List<BinTreeNode<E>> dataList) {
        if (node == null && !isFullBtMode())
            return false;

        dataList.add(node);
        if (!getNodeHandler().handle(node))
            return false;

        if (node != null)
            queue.offer(node.getLeftChild());

        if (node != null)
            queue.offer(node.getRightChild());

        return true;
    }
}
