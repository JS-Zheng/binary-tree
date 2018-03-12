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

    private final BinTreeNode<E> node;
    private E[] data;

    TopDownTreeNodeConstructor(BinTreeNode<E> node) {
        this.node = node;
    }

    @Override
    public NodeInfo firstItem(BinTreeNode<E> node) {
        NodeInfo info = new NodeInfo();
        info.index = 0;
        info.node = node;
        return info;
    }

    @Override
    public boolean init(BinTreeNode<E> node) {
        if (data != null && data.length > 0) {
            node.setData(data[0]);
            return true;
        }

        return false;
    }

    @Override
    public boolean onDataPolled(Queue<TopDownTreeNodeConstructor<E>.NodeInfo> queue,
                                TopDownTreeNodeConstructor<E>.NodeInfo info) {
        int index = info.index;
        BinTreeNode<E> node = info.node;
        NodeInfo lChildInfo = getChildInfo(node, index, true);
        NodeInfo rChildInfo = getChildInfo(node, index, false);

        if (lChildInfo != null && lChildInfo.node != null)
            queue.offer(lChildInfo);

        if (rChildInfo != null && rChildInfo.node != null)
            queue.offer(rChildInfo);

        return true;
    }


    @Override
    public void setDataByArr(E[] data) {
        this.data = data;
        execute(node);
    }

    private NodeInfo getChildInfo(BinTreeNode<E> node, int index, boolean isLeft) {
        int childIndex = isLeft ? BinaryTreeLemma.lChildIndex(index) : BinaryTreeLemma.rChildIndex(index);

        if (childIndex < data.length) {
            BinTreeNode<E> child = null;
            E childData = data[childIndex];

            if (childData != null) {
                child = this.node.newNode();
                child.setData(childData);
            }

            if (isLeft)
                node.setLeftChild(child);
            else
                node.setRightChild(child);

            NodeInfo info = new NodeInfo();
            info.node = child;
            info.index = childIndex;

            return info;
        }

        return null;
    }

    class NodeInfo {
        BinTreeNode<E> node;
        int index;
    }
}
