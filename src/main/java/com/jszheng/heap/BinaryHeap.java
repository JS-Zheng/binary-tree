package com.jszheng.heap;

import com.jszheng.Env;
import com.jszheng.base.BinaryTree;
import com.jszheng.base.BinaryTreeLemma;
import com.jszheng.node.TreeNode;

import java.util.List;

public abstract class BinaryHeap<E extends Comparable<? super E>> extends Heap<E> {

    public BinaryHeap(BinaryTree<E> component) {
        super(component);
    }

    // O(n)
    protected void constructHeapByBottomUp(E[] data, boolean maxHeap) {
        if (Env.debug)
            System.out.println("===== Bottom-up Construct "
                    + (maxHeap ? "MaxHeap" : "MinHeap") + " =====");

        // Build CompleteBinaryTree
        setDataByArr(data);

        List<TreeNode<E>> nodes = traverse("Level", false, false, node -> true);
        int lastNodeIndex = nodes.size() - 1;
        int lastParentIndex = BinaryTreeLemma.parentIndex(lastNodeIndex);

        for (int i = lastParentIndex; i > -1; i--) {
            TreeNode<E> parent = nodes.get(i);
            downHeap(parent, maxHeap);
        }
    }

    // Extrema == Root
    protected E deleteExtrema(boolean maxHeap) {
        TreeNode<E> root = getRoot();
        if (root == null) return null;
        else if (root.degree() == 0) {
            E extrema = root.getData();
            clearRoot();
            return extrema;
        }

        TreeNode<E> lastNode = getLastNode();

        E extrema = root.getData();
        E lastNodeData = lastNode.getData();

        lastNode.deleteParent();
        root.setData(lastNodeData);

        downHeap(root, maxHeap);

        return extrema;
    }

    protected E searchExtrema() {
        TreeNode<E> root = getRoot();
        if (root == null) return null;
        return root.getData();
    }
}
