package com.jszheng.traversal.pre;

import com.jszheng.base.BinaryTree;
import com.jszheng.node.BinTreeNode;
import com.jszheng.traversal.AbstractIterativeTraversal;
import com.jszheng.util.DFSAlgo;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class PreOrderTraversal<E> extends AbstractIterativeTraversal<E> implements DFSAlgo<BinaryTree<E>, BinTreeNode<E>, List<BinTreeNode<E>>> {

    protected List<BinTreeNode<E>> dataList = new ArrayList<>();

    @Override
    public boolean init(BinaryTree<E> bt) {
        return bt != null && bt.getRoot() != null;
    }

    @Override
    public BinTreeNode<E> firstItem(BinaryTree<E> bt) {
        return bt.getRoot();
    }

    @Override
    public boolean onDataPopped(Stack<BinTreeNode<E>> stack, BinTreeNode<E> node) {
        if (node == null)
            return false;

        dataList.add(node);
        if (!getNodeHandler().handle(node))
            return false;

        BinTreeNode<E> rChild = node.getRightChild();
        if (rChild != null)
            stack.push(rChild);// Right First (Stack)

        BinTreeNode<E> lChild = node.getLeftChild();
        if (lChild != null)
            stack.push(lChild);

        return true;
    }

    @Override
    public List<BinTreeNode<E>> onFinish(BinaryTree<E> bt) {
        getNodeHandler().afterTraversed(bt);
        return dataList;
    }

    @Override
    public List<BinTreeNode<E>> traverse(BinaryTree<E> bt) {
        return execute(bt);
    }
}