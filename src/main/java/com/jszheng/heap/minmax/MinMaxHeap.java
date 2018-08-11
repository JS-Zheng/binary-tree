package com.jszheng.heap.minmax;

import com.jszheng.base.BinaryTree;
import com.jszheng.base.complete.CompleteBinaryTree;
import com.jszheng.heap.AbsBinDoubleEndedHeap;
import com.jszheng.insertion.InsertionAlgo;
import com.jszheng.node.BinTreeNode;

public class MinMaxHeap<E extends Comparable<? super E>> extends AbsBinDoubleEndedHeap<E> implements CompleteBinaryTree<E> {

    public MinMaxHeap(BinaryTree<E> component) {
        super(component);
    }

    @Override
    public BinaryTree<E> copy(boolean deep) {
        return new MinMaxHeap<>(component.copy(deep));
    }

    @Override
    public String getLevelString(int level) {
        return level % 2 == 1 ? " -- Min" : " -- Max";
    }

    @Override
    public BinaryTree<E> newTree() {
        return new MinMaxHeap<>(component.newTree());
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

    private E deleteExtrema(boolean max) {
        BinTreeNode<E> root = getRoot();
        BinTreeNode<E> target = max ? searchExtremaNode(true) : root;
        if (target == null) return null;

        E extrema = target.getData();

        BinTreeNode<E> lastNode = getLastNode();
        E lastNodeData = lastNode.getData();
        lastNode.deleteParent();

        // If the target is lastNode, simply delete it.
        if (lastNode == target) {
            if (lastNode == root)
                clearRoot();

            return extrema;
        }

        while (true) {
            BinTreeNode<E> lChild = target.getLeftChild();
            BinTreeNode<E> rChild = target.getRightChild();

            if (lChild == null && rChild == null) {
                target.setData(lastNodeData);
                break;
            }

            BinTreeNode<E> lLNode = null;
            BinTreeNode<E> lRNode = null;
            BinTreeNode<E> rLNode = null;
            BinTreeNode<E> rRNode = null;

            if (lChild != null) {
                lLNode = lChild.getLeftChild();
                lRNode = lChild.getRightChild();
            }
            if (rChild != null) {
                rLNode = rChild.getLeftChild();
                rRNode = rChild.getRightChild();
            }
            BinTreeNode arr[] = {lChild, rChild, lLNode, lRNode, rLNode, rRNode};
            BinTreeNode<E> extremaDescendant = getExtremaNodeFromArr(arr, max);
            E extremaDescendantData = extremaDescendant.getData();

            int compareResult = extremaDescendantData.compareTo(lastNodeData);
            boolean swapCondition = max ? compareResult > 0 : compareResult < 0;

            if (extremaDescendant == lChild || extremaDescendant == rChild) {
                if (swapCondition) {
                    extremaDescendant.setData(lastNodeData);
                    target.setData(extremaDescendantData);
                } else {
                    target.setData(lastNodeData);
                }
            } else { // extremaDescendant is grandson
                if (swapCondition) {
                    target.setData(extremaDescendantData);
                    target = extremaDescendant;

                    BinTreeNode<E> p = extremaDescendant.getParent();
                    E pData = p.getData();

                    int parentCompareResult = lastNodeData.compareTo(pData);
                    boolean parentSwapCondition = max ? parentCompareResult < 0 : parentCompareResult > 0;

                    if (parentSwapCondition) {
                        p.setData(lastNodeData);
                        lastNodeData = pData;
                    }

                    continue;

                } else {
                    target.setData(lastNodeData);
                }
            }

            break;
        }

        return extrema;
    }

    @Override
    protected BinTreeNode<E> searchExtremaNode(boolean max) {
        BinTreeNode<E> root = getRoot();
        if (root == null) return null;
        if (!max) return root;

        // Find Max
        BinTreeNode<E> lChild = root.getLeftChild();
        BinTreeNode<E> rChild = root.getRightChild();
        if (lChild == null && rChild == null)
            return root;
        else
            return compareNode(lChild, rChild, true);
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
        return new MinMaxHeapInsertion<>();
    }

    @Override
    protected void upHeap(BinTreeNode<E> node, boolean maxHeap) {
        super.upHeap(node, maxHeap);
    }

    @Override
    protected BinTreeNode<E> lastLevelNode(BinTreeNode<E> currentNode) {
        BinTreeNode<E> parent = currentNode.getParent();
        return parent != null ? parent.getParent() : null;
    }

    boolean isMinLevel(BinTreeNode<E> node) {
        int level = node.getLevel();
        return level % 2 == 1;
    }
}
