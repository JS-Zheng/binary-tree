package com.jszheng.heap;

import com.jszheng.base.BinaryTree;
import com.jszheng.base.BtDecorator;
import com.jszheng.node.TreeNode;

public abstract class AbstractHeap<E extends Comparable<? super E>> extends BtDecorator<E> implements Heap<E> {

    protected AbstractHeap(BinaryTree<E> component) {
        super(component);
    }

    protected void downHeap(TreeNode<E> node, boolean maxHeap) {
        TreeNode<E> lChild = node.getLeftChild();
        TreeNode<E> rChild = node.getRightChild();
        TreeNode<E> targetChild;

        while (lChild != null || rChild != null) {
            E data = node.getData();

            // max{lChild, rChild} or min{lChild, rChild}
            targetChild = compareNode(lChild, rChild, maxHeap);

            E childData = targetChild.getData();
            int compare = data.compareTo(childData);
            boolean swapCondition = maxHeap ? compare < 0 : compare > 0;

            if (swapCondition) {
                node.setData(childData);
                targetChild.setData(data);
            } else
                break;

            node = targetChild;
            lChild = node.getLeftChild();
            rChild = node.getRightChild();
        }
    }

    protected TreeNode<E> lastLevelNode(TreeNode<E> currentNode) {
        // Root of Tree probably has parent (i.e., SubTree)
        if (currentNode == getRoot()) return null;
        return currentNode.getParent();
    }

    protected void upHeap(TreeNode<E> node, boolean maxHeap) {
        TreeNode<E> parent = lastLevelNode(node);

        while (parent != null) {
            E parentData = parent.getData();
            if (parentData == null) return;
            E data = node.getData();
            int compare = data.compareTo(parentData);

            boolean swapCondition = maxHeap ? compare > 0 : compare < 0;
            if (swapCondition) {
                node.setData(parentData);
                parent.setData(data);
            } else
                break;

            node = parent;
            parent = lastLevelNode(node);
        }
    }
}
