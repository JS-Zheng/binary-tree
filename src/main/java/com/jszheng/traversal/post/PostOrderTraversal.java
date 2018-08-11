package com.jszheng.traversal.post;

import com.jszheng.base.BinaryTree;
import com.jszheng.node.BinTreeNode;
import com.jszheng.traversal.AbstractIterativeTraversal;
import com.jszheng.traversal.TraversalAlgo;
import com.jszheng.util.DFSAlgo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

public class PostOrderTraversal<E> extends AbstractIterativeTraversal<E> implements TraversalAlgo<E>, DFSAlgo<BinaryTree<E>, BinTreeNode<E>, List<BinTreeNode<E>>> {

    private List<BinTreeNode<E>> dataList = new ArrayList<>();

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
        // Traverse rightest child
        while (node != null) {
            dataList.add(node);
            stack.push(node);
            node = node.getRightChild();
        }

        // traverse each leftChild of Tree in Stack.
        if (!stack.isEmpty()) {
            BinTreeNode<E> subTree = stack.pop();
            stack.push(subTree.getLeftChild());
        }

        return true;
    }

    @Override
    public List<BinTreeNode<E>> onFinish(BinaryTree<E> bt) {
        Collections.reverse(dataList);

        for (BinTreeNode<E> node : dataList) {
            if (!getNodeHandler().handle(node))
                break;
        }

        getNodeHandler().afterTraversed(bt);

        return dataList;
    }

    @Override
    public List<BinTreeNode<E>> traverse(BinaryTree<E> bt) {
        return execute(bt);
    }
}
