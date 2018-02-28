package com.jszheng.traversal;

import com.jszheng.base.BinaryTree;
import com.jszheng.node.TreeNode;

import java.util.ArrayList;
import java.util.List;

// For In, Pre, Post Algo
public abstract class AbstractRecursiveTraversal<E> implements TraversalAlgo<E> {

    protected List<TreeNode<E>> dataList = new ArrayList<>();
    private TraversalNodeHandler<E> handler;

    @Override
    public List<TreeNode<E>> traverse(BinaryTree<E> bt) {
        // Template Method.
        traverseTree(bt.getRoot());
        handler.afterTraversed(bt);

        return dataList;
    }

    @Override
    public TraversalNodeHandler<E> getNodeHandler() {
        if (handler == null)
            handler = new DefaultNodeHandler<>();

        return handler;
    }

    @Override
    public void setNodeHandler(TraversalNodeHandler<E> handler) {
        this.handler = handler;
    }

    protected abstract void traverseTree(TreeNode<E> node);
}
