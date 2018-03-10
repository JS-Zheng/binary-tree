package com.jszheng.heap.smmh;

import com.jszheng.base.BinaryTree;
import com.jszheng.base.complete.CompleteBinaryTree;
import com.jszheng.heap.AbsBinDoubleEndedHeap;
import com.jszheng.insertion.InsertionAlgo;
import com.jszheng.node.BinTreeNode;

public class SymmetricMinMaxHeap<E extends Comparable<? super E>> extends AbsBinDoubleEndedHeap<E> implements CompleteBinaryTree<E> {

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
    public E deleteMax() {
        return deleteExtrema(true);
    }

    @Override
    public E searchMax() {
        BinTreeNode<E> maxNode = searchExtremaNode(true);
        return maxNode != null ? maxNode.getData() : null;
    }

    @Override
    public E deleteMin() {
        return deleteExtrema(false);
    }

    @Override
    public E searchMin() {
        BinTreeNode<E> minNode = searchExtremaNode(false);
        return minNode != null ? minNode.getData() : null;
    }

    @Override
    protected InsertionAlgo<E> createInsertionAlgo() {
        return new SmmhInsertion<>();
    }

    private E deleteExtrema(boolean max) {
        BinTreeNode<E> target = searchExtremaNode(max);
        if (target == null) return null;
        E extrema = target.getData();

        BinTreeNode<E> lastNode = getLastNode();
        E lastNodeData = lastNode.getData();
        lastNode.deleteParentAndCheckItsChild();

        // If the target is lastNode, simply delete it.
        if (lastNode == target)
            return extrema;

        while (target.degree() != 0) {
            BinTreeNode<E> sibling = target.getSibling();
            BinTreeNode<E> targetChild = max ? target.getRightChild() : target.getLeftChild();
            BinTreeNode<E> siblingChild = max ? sibling.getRightChild() : sibling.getLeftChild();
            BinTreeNode<E> extremaChild = compareNode(targetChild, siblingChild, max);
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
        BinTreeNode<E> sibling = target.getSibling();

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
