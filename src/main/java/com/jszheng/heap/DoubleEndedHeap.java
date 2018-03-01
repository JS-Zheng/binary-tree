package com.jszheng.heap;

import com.jszheng.base.BinaryTree;
import com.jszheng.base.complete.LinearSearch;
import com.jszheng.node.TreeNode;
import com.jszheng.search.SearchAlgo;

public abstract class DoubleEndedHeap<E extends Comparable<? super E>> extends AbstractHeap<E> implements MaxHeap<E>, MinHeap<E> {

    public DoubleEndedHeap(BinaryTree<E> component) {
        super(component);
    }

    @Override
    protected SearchAlgo<E> createSearchAlgo() {
        if (searchAlgo == null)
            searchAlgo = new LinearSearch<>();
        return searchAlgo;
    }

    protected TreeNode<E> getExtremaNodeFromArr(TreeNode<E> nodes[], boolean max) {
        TreeNode<E> extremaNode = null;

        for (TreeNode<E> currentNode : nodes) {
            if (currentNode == null) continue;
            if (extremaNode == null)
                extremaNode = currentNode;
            else {
                E extremaData = extremaNode.getData();
                E currentData = currentNode.getData();
                int compareResult = extremaData.compareTo(currentData);
                if ((max && (compareResult < 0)) || (!max && compareResult > 0))
                    extremaNode = currentNode;
            }
        }
        return extremaNode;
    }

    protected TreeNode<E> searchExtremaNode(boolean max) {
        TreeNode<E> root = getRoot();
        if (root == null) return null;
        else if (max && !root.hasRightChild()) return root.getLeftChild();
        else return max ? root.getRightChild() : root.getLeftChild();
    }
}
