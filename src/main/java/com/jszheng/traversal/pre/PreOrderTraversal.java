package com.jszheng.traversal.pre;

import com.jszheng.base.BinaryTree;
import com.jszheng.node.TreeNode;
import com.jszheng.traversal.AbstractIterativeTraversal;
import com.jszheng.util.DFSAlgo;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class PreOrderTraversal<E> extends AbstractIterativeTraversal<E> implements DFSAlgo<BinaryTree<E>, TreeNode<E>, List<TreeNode<E>>> {

    protected List<TreeNode<E>> dataList = new ArrayList<>();

    @Override
    public TreeNode<E> firstItem(BinaryTree<E> bt) {
        return bt.getRoot();
    }

    @Override
    public boolean init(BinaryTree<E> bt) {
        return bt != null && bt.getRoot() != null;
    }

    @Override
    public boolean onDataPopped(Stack<TreeNode<E>> stack, TreeNode<E> node) {
        if (node == null)
            return false;

        dataList.add(node);
        if (!getNodeHandler().handle(node))
            return false;

        TreeNode<E> rChild = node.getRightChild();
        if (rChild != null)
            stack.push(rChild);// Right First (Stack)

        TreeNode<E> lChild = node.getLeftChild();
        if (lChild != null)
            stack.push(lChild);

        return true;
    }

    @Override
    public List<TreeNode<E>> onFinish(BinaryTree<E> bt) {
        getNodeHandler().afterTraversed(bt);
        return dataList;
    }

    @Override
    public List<TreeNode<E>> traverse(BinaryTree<E> bt) {
        return execute(bt);
    }
}