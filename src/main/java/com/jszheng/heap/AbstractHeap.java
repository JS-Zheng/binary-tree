package com.jszheng.heap;

import com.jszheng.Env;
import com.jszheng.base.LinkedBinaryTree;
import com.jszheng.node.BinTreeNode;

public abstract class AbstractHeap<E extends Comparable<? super E>> extends LinkedBinaryTree<E> implements Heap<E, BinTreeNode<E>> {

    protected void downHeap(BinTreeNode<E> node, boolean maxHeap) {
        BinTreeNode<E> lChild = node.getLeftChild();
        BinTreeNode<E> rChild = node.getRightChild();
        BinTreeNode<E> targetChild;

        while (lChild != null || rChild != null) {
            E data = node.getData();

            // max{lChild, rChild} or min{lChild, rChild}
            targetChild = compareNode(lChild, rChild, maxHeap);

            E childData = targetChild.getData();
            int compare = data.compareTo(childData);
            boolean swapCondition = maxHeap ? compare < 0 : compare > 0;

            if (swapCondition) {
                if (Env.debug) {
                    System.out.println("[down heap] swap node: " + data + "  &  child: " + childData);
                }
                node.setData(childData);
                targetChild.setData(data);
            } else
                break;

            node = targetChild;
            lChild = node.getLeftChild();
            rChild = node.getRightChild();
        }
    }

    protected void upHeap(BinTreeNode<E> node, boolean maxHeap) {
        BinTreeNode<E> parent = lastLevelNode(node);

        while (parent != null) {
            E parentData = parent.getData();
            if (parentData == null) return;
            E data = node.getData();
            int compare = data.compareTo(parentData);

            boolean swapCondition = maxHeap ? compare > 0 : compare < 0;
            if (swapCondition) {
                if (Env.debug) {
                    System.out.println("[up heap] swap node: " + data + "  &  parent: " + parentData);
                }
                node.setData(parentData);
                parent.setData(data);
            } else
                break;

            node = parent;
            parent = lastLevelNode(node);
        }
    }

    protected BinTreeNode<E> lastLevelNode(BinTreeNode<E> currentNode) {
        // Root of Tree probably has parent (i.e., SubTree)
        if (currentNode == getRoot()) return null;
        return currentNode.getParent();
    }
}
