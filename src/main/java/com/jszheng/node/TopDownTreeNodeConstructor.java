package com.jszheng.node;

import com.jszheng.base.BinaryTreeLemma;
import com.jszheng.util.BFSAlgo;

import java.util.Queue;

/*
 * Iterative Level Order TreeNode Constructor
 *
 * O(n)
 */
public class TopDownTreeNodeConstructor<E> implements TreeNodeConstructor<E>, BFSAlgo<TreeNode<E>, TreeNode<E>, Void> {

    private final TreeNode<E> node;
    private E[] data;

    TopDownTreeNodeConstructor(TreeNode<E> node) {
        this.node = node;
    }

    @Override
    public TreeNode<E> firstItem(TreeNode<E> node) {
        return node;
    }

    @Override
    public boolean init(TreeNode<E> node) {
        if (data != null && data.length > 0) {
            node.setIndex(0);
            node.setData(data[0]);
            return true;
        }

        return false;
    }

    @Override
    public boolean onDataPolled(Queue<TreeNode<E>> queue, TreeNode<E> currentNode) {
        int index = currentNode.getIndex();
        TreeNode<E> lChild = setChild(currentNode, index, true);
        TreeNode<E> rChild = setChild(currentNode, index, false);

        if (lChild != null)
            queue.offer(lChild);

        if (rChild != null)
            queue.offer(rChild);

        return true;
    }

    @Override
    public void setDataByArr(E[] data) {
        this.data = data;
        execute(node);
    }

    private TreeNode<E> setChild(TreeNode<E> currentNode, int index, boolean isLeft) {
        int childIndex = isLeft ? BinaryTreeLemma.lChildIndex(index) : BinaryTreeLemma.rChildIndex(index);

        if (childIndex < data.length) {
            TreeNode<E> child = null;
            E childData = data[childIndex];

            if (childData != null) {
                child = node.newNode();
                child.setData(childData);
            }

            if (isLeft)
                currentNode.setLeftChildWithIndex(child);
            else
                currentNode.setRightChildWithIndex(child);

            return child;
        }

        return null;
    }
}
