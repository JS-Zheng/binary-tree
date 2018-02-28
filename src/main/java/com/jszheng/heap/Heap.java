package com.jszheng.heap;

import com.jszheng.base.BinaryTree;
import com.jszheng.base.completebt.CompleteBinaryTree;
import com.jszheng.node.TreeNode;

public abstract class Heap<E extends Comparable<? super E>> extends CompleteBinaryTree<E> {

    Heap(BinaryTree<E> component) {
        super(component);
    }

    protected TreeNode<E> compareNode(TreeNode<E> t1, TreeNode<E> t2, boolean findGreater) {
        E t1Data = t1 != null ? t1.getData() : null;
        E t2Data = t2 != null ? t2.getData() : null;

        if (t1Data == null && t2Data == null) return null;
        else if (t1Data == null) return t2;
        else if (t2Data == null) return t1;

        int compareResult = t1Data.compareTo(t2Data);
        boolean t1GreaterThanT2 = compareResult > 0;

        if (findGreater)
            return t1GreaterThanT2 ? t1 : t2;
        else
            return t1GreaterThanT2 ? t2 : t1;
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

    void downHeap(TreeNode<E> node, boolean maxHeap) {
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
}
