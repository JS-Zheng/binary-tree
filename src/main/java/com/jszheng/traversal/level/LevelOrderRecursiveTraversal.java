package com.jszheng.traversal.level;

import com.jszheng.node.TreeNode;
import com.jszheng.traversal.AbstractRecursiveTraversal;

import java.util.LinkedList;
import java.util.Queue;

public class LevelOrderRecursiveTraversal<E> extends AbstractRecursiveTraversal<E> implements ILevelOrderTraversal<E> {

    private Queue<TreeNode<E>> queue = new LinkedList<>();

    @Override
    protected void traverseTree(TreeNode<E> node) {
        queue.offer(node);
        traverse();
    }

    private void traverse() {
        if (!queue.isEmpty()) {

            TreeNode<E> node = queue.poll();

            execute(queue, node, dataList);

            traverse();
        }
    }
}
