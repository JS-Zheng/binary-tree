package com.jszheng.heap.smmh;

import com.jszheng.base.BinaryTree;
import com.jszheng.heap.DoubleEndedHeap;
import com.jszheng.insertion.InsertionAlgo;
import com.jszheng.node.TreeNode;

public class SymmetricMinMaxHeap<E extends Comparable<? super E>> extends DoubleEndedHeap<E> {

    public SymmetricMinMaxHeap(BinaryTree<E> component) {
        super(component);
    }

    @Override
    public SymmetricMinMaxHeap<E> copy(boolean deep) {
        return new SymmetricMinMaxHeap<>(component.copy(deep));
    }

    @Override
    public SymmetricMinMaxHeap<E> newTree() {
        return new SymmetricMinMaxHeap<>(component.newTree());
    }

    @Override
    public InsertionAlgo<E> createInsertionAlgo() {
        return new SmmhInsertion<>();
    }

    @Override
    public E deleteMax() {
        return deleteExtrema(true);
    }

    @Override
    public E deleteMin() {
        return deleteExtrema(false);
    }

    @Override
    public E searchMax() {
        TreeNode<E> maxNode = searchExtremaNode(true);
        return maxNode != null ? maxNode.getData() : null;
    }

    @Override
    public E searchMin() {
        TreeNode<E> minNode = searchExtremaNode(false);
        return minNode != null ? minNode.getData() : null;
    }

    private E deleteExtrema(boolean max) {
        TreeNode<E> target = searchExtremaNode(max);
        if (target == null) return null;
        E extrema = target.getData();

        TreeNode<E> lastNode = getLastNode();
        E lastNodeData = lastNode.getData();
        lastNode.deleteParent();

        // If the target is lastNode, simply delete it.
        if (lastNode == target)
            return extrema;

        while (target.degree() != 0) {
            TreeNode<E> sibling = target.getSibling();
            TreeNode<E> targetChild = max ? target.getRightChild() : target.getLeftChild();
            TreeNode<E> siblingChild = max ? sibling.getRightChild() : sibling.getLeftChild();
            TreeNode<E> extremaChild = compareNode(targetChild, siblingChild, max);
            E extremaChildData = extremaChild.getData();

            int compareResult = lastNodeData.compareTo(extremaChildData);
            boolean swapCondition = max ? compareResult < 0 : compareResult > 0;

            if (swapCondition) {
                target.setData(extremaChildData);
                target = extremaChild;
            } else
                break;
        }

        target.setData(lastNodeData);
        TreeNode<E> sibling = target.getSibling();

        if (sibling != null) {
            E siblingData = sibling.getData();
            int compareResult = lastNodeData.compareTo(siblingData);
            boolean swapCondition = max ? compareResult < 0 : compareResult > 0;
            if (swapCondition) {
                sibling.setData(lastNodeData);
                target.setData(siblingData);
            }
        }

        return extrema;
    }
}
