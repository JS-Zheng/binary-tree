package com.jszheng.heap.deap;

import com.jszheng.base.BinaryTree;
import com.jszheng.base.BinaryTreeLemma;
import com.jszheng.base.complete.CompleteBinaryTree;
import com.jszheng.heap.DoubleEndedHeap;
import com.jszheng.insertion.InsertionAlgo;
import com.jszheng.node.TreeNode;

public class Deap<E extends Comparable<? super E>> extends DoubleEndedHeap<E> implements CompleteBinaryTree<E> {

    public Deap(BinaryTree<E> component) {
        super(component);
    }

    @Override
    public Deap<E> copy(boolean deep) {
        return new Deap<>(component.copy(deep));
    }

    @Override
    public Deap<E> newTree() {
        return new Deap<>(component.newTree());
    }

    @Override
    public E deleteMax() {
        return deleteExtrema(true);
    }

    @Override
    public E searchMax() {
        TreeNode<E> maxNode = searchExtremaNode(true);
        return maxNode != null ? maxNode.getData() : null;
    }

    @Override
    public E deleteMin() {
        return deleteExtrema(false);
    }

    @Override
    public E searchMin() {
        TreeNode<E> minNode = searchExtremaNode(false);
        return minNode != null ? minNode.getData() : null;
    }

    @Override
    protected InsertionAlgo<E> createInsertionAlgo() {
        if (insertionAlgo == null)
            insertionAlgo = new DeapInsertion<>();
        return insertionAlgo;
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
            TreeNode<E> lChild = target.getLeftChild();
            TreeNode<E> rChild = target.getRightChild();
            TreeNode<E> extremaDescendant = compareNode(lChild, rChild, max);
            E extremaDescendantData = extremaDescendant.getData();

            target.setData(extremaDescendantData);
            target = extremaDescendant;
        }

        target.setData(lastNodeData);
        insertionFixUp(target);

        return extrema;
    }

    private TreeNode<E> getCorrespondingNode(TreeNode<E> node) {
        TreeNode<E> result = null;

        int currentIndex = node.getIndex();
        boolean isNodeInLTree = BinaryTreeLemma.isDescendantIndex(1, currentIndex);
        int lastLevel = node.getLevel() - 1;
        int lastLevelMaxNodeCount = BinaryTreeLemma.getMaxCountByLevel(lastLevel);

        int correspondingIndex = isNodeInLTree ? currentIndex + lastLevelMaxNodeCount
                : currentIndex - lastLevelMaxNodeCount;

        while (result == null && correspondingIndex > -1) {
            result = getNodeByIndex(correspondingIndex);

            if (result == null)
                correspondingIndex = BinaryTreeLemma.parentIndex(correspondingIndex);
        }

        return result;
    }

    void insertionFixUp(TreeNode<E> newNode) {
        // 取得 Deap 對應節點
        TreeNode<E> correspondingNode = getCorrespondingNode(newNode);
        if (correspondingNode == null || correspondingNode == getRoot()) return;

        E data = newNode.getData();
        E correspondingNodeData = correspondingNode.getData();
        // 新節點 是否位於左子樹 (min-heap)
        boolean isNewNodeInLTree = BinaryTreeLemma.isDescendantIndex(1, newNode.getIndex());

        // 將 新節點鍵值 與 對應節點鍵值 作比較
        int compareResult = data.compareTo(correspondingNodeData);
        boolean swapCondition = isNewNodeInLTree ? compareResult > 0 : compareResult < 0;

        if (swapCondition) {
            newNode.setData(correspondingNodeData);
            correspondingNode.setData(data);

            // 若新節點原本位於 min-heap，則進行 max-heap 之調整；反之
            // (因資料已交換)
            upHeap(correspondingNode, isNewNodeInLTree);
        } else
            // 若新節點原本位於 min-heap，則進行 min-heap 之調整；反之
            upHeap(newNode, !isNewNodeInLTree);
    }
}
