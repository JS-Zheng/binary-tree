package com.jszheng.base;

import com.jszheng.node.BinTreeNode;
import com.jszheng.util.BFSAlgo;

import java.util.Queue;

/*
 * Iterative Level Order TreeNode Constructor
 *
 * O(n)
 */
class TopDownTreeNodeConstructor<E> implements TreeNodeConstructor<E>, BFSAlgo<BinTreeNode<E>, TopDownTreeNodeConstructor<E>.NodeInfo, Void> {

    private final BinTreeNode<E> root;
    private E[] arr;
    private boolean nullable;

    TopDownTreeNodeConstructor(BinTreeNode<E> root) {
        this.root = root;
    }

    @Override
    public boolean init(BinTreeNode<E> node) {
        if (arr == null || arr.length < 1) return false;
        E rootData = this.arr[0];
        if (rootData == null && !nullable) return false;
        node.setData(rootData);
        return true;
    }

    @Override
    public NodeInfo firstItem(BinTreeNode<E> node) {
        NodeInfo info = new NodeInfo();
        info.index = 0;
        info.node = node;
        return info;
    }

    @Override
    public boolean onDataPolled(Queue<TopDownTreeNodeConstructor<E>.NodeInfo> queue,
                                TopDownTreeNodeConstructor<E>.NodeInfo info) {
        int index = info.index;
        BinTreeNode<E> node = info.node;

        NodeInfo lChildInfo = getChildInfo(node, index, true);
        NodeInfo rChildInfo = getChildInfo(node, index, false);

        if (lChildInfo != null)
            queue.offer(lChildInfo);

        if (rChildInfo != null)
            queue.offer(rChildInfo);

        return true;
    }

    private NodeInfo getChildInfo(BinTreeNode<E> node, int index, boolean isLeft) {
        int childIndex = isLeft ? BinaryTreeLemma.lChildIndex(index) : BinaryTreeLemma.rChildIndex(index);
        if (childIndex >= arr.length) return null;

        BinTreeNode<E> child;
        E childData = arr[childIndex];

        if (childData != null || nullable) {
            child = this.root.newNode();
            child.setData(childData);
        } else
            return null;

        if (isLeft)
            node.setLeftChild(child);
        else
            node.setRightChild(child);

        NodeInfo info = new NodeInfo();
        info.node = child;
        info.index = childIndex;

        return info;
    }

    @Override
    public void setDataArr(E[] data, boolean nullable) {
        this.arr = data;
        this.nullable = nullable;
        execute(root);
    }

    class NodeInfo {
        BinTreeNode<E> node;
        int index;
    }
}
