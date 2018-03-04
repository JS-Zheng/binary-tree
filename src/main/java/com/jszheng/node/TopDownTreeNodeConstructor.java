package com.jszheng.node;

import com.jszheng.base.BinaryTreeLemma;
import com.jszheng.util.BFSAlgo;

import java.util.Queue;

/*
 * Iterative Level Order TreeNode Constructor
 *
 * O(n)
 */
class TopDownTreeNodeConstructor<E> implements TreeNodeConstructor<E>, BFSAlgo<BinTreeNode<E>, BinTreeNode<E>, Void> {

    private final BinTreeNode<E> node;
    private E[] data;

    TopDownTreeNodeConstructor(BinTreeNode<E> node) {
        this.node = node;
    }

    @Override
    public BinTreeNode<E> firstItem(BinTreeNode<E> node) {
        return node;
    }

    @Override
    public boolean init(BinTreeNode<E> node) {
        if (data != null && data.length > 0) {
            node.setIndex(0);
            node.setData(data[0]);
            return true;
        }

        return false;
    }

    @Override
    public boolean onDataPolled(Queue<BinTreeNode<E>> queue, BinTreeNode<E> currentNode) {
        int index = currentNode.getIndex();
        BinTreeNode<E> lChild = setChild(currentNode, index, true);
        BinTreeNode<E> rChild = setChild(currentNode, index, false);

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

    private BinTreeNode<E> setChild(BinTreeNode<E> currentNode, int index, boolean isLeft) {
        int childIndex = isLeft ? BinaryTreeLemma.lChildIndex(index) : BinaryTreeLemma.rChildIndex(index);

        if (childIndex < data.length) {
            BinTreeNode<E> child = null;
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
