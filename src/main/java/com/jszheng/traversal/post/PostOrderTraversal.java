package com.jszheng.traversal.post;

import com.jszheng.base.BinaryTree;
import com.jszheng.node.TreeNode;
import com.jszheng.traversal.AbstractIterativeTraversal;
import com.jszheng.traversal.TraversalAlgo;
import com.jszheng.util.DFSAlgo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

public class PostOrderTraversal<E> extends AbstractIterativeTraversal<E> implements TraversalAlgo<E>, DFSAlgo<BinaryTree<E>, TreeNode<E>, List<TreeNode<E>>> {

    private List<TreeNode<E>> dataList = new ArrayList<>();

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
        // Traverse rightest child
        while (node != null) {
            dataList.add(node);
            stack.push(node);
            node = node.getRightChild();
        }

        // traverse each leftChild of Tree in Stack.
        if (!stack.isEmpty()) {
            TreeNode<E> subTree = stack.pop();
            stack.push(subTree.getLeftChild());
        }

        return true;
    }

    @Override
    public List<TreeNode<E>> onFinish(BinaryTree<E> bt) {
        Collections.reverse(dataList);

        for (TreeNode<E> node : dataList) {
            if (!getNodeHandler().handle(node))
                break;
        }

        getNodeHandler().afterTraversed(bt);

        return dataList;
    }

    @Override
    public List<TreeNode<E>> traverse(BinaryTree<E> bt) {
        return execute(bt);
    }
}
