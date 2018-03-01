package com.jszheng.heap.binaryheap;

import com.jszheng.Env;
import com.jszheng.base.BinaryTree;
import com.jszheng.base.BinaryTreeLemma;
import com.jszheng.base.completebt.CompleteBinaryTree;
import com.jszheng.base.completebt.LinearSearch;
import com.jszheng.heap.AbstractHeap;
import com.jszheng.node.TreeNode;
import com.jszheng.search.SearchAlgo;

import java.util.List;

abstract class BinaryHeap<E extends Comparable<? super E>> extends AbstractHeap<E> implements CompleteBinaryTree<E> {

    private final boolean maxHeap;

    BinaryHeap(BinaryTree<E> component) {
        super(component);
        maxHeap = isMaxHeap();
    }

    abstract boolean isMaxHeap();

    @Override
    protected SearchAlgo<E> createSearchAlgo() {
        if (searchAlgo == null)
            searchAlgo = new LinearSearch<>();
        return searchAlgo;
    }

    // O(n)
    void constructHeapByBottomUp(E[] data) {
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

    // Θ(log n)
    E deleteExtrema() {
        // Extrema == Root
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

    // O(1)
    E searchExtrema() {
        TreeNode<E> root = getRoot();
        if (root == null) return null;
        return root.getData();
    }
}
