package com.jszheng.traversal.level;

import com.jszheng.node.BinTreeNode;
import com.jszheng.traversal.AbstractRecursiveTraversal;

import java.util.LinkedList;
import java.util.Queue;

public class LevelOrderRecursiveTraversal<E> extends AbstractRecursiveTraversal<E> implements ILevelOrderTraversal<E> {

    private Queue<BinTreeNode<E>> queue = new LinkedList<>();

    @Override
    protected void traverseTree(BinTreeNode<E> node) {
        queue.offer(node);
        traverse();
    }

    private void traverse() {
        if (!queue.isEmpty()) {

            BinTreeNode<E> node = queue.poll();

            execute(queue, node, dataList);

            traverse();
        }
    }
}
