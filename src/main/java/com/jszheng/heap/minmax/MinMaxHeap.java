package com.jszheng.heap.minmax;

import com.jszheng.base.BinaryTree;
import com.jszheng.base.complete.CompleteBinaryTree;
import com.jszheng.heap.DoubleEndedHeap;
import com.jszheng.insertion.InsertionAlgo;
import com.jszheng.node.TreeNode;

public class MinMaxHeap<E extends Comparable<? super E>> extends DoubleEndedHeap<E> implements CompleteBinaryTree<E> {

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
        return new MinMaxHeapInsertion<>();
    }

    @Override
    protected TreeNode<E> lastLevelNode(TreeNode<E> currentNode) {
        TreeNode<E> parent = currentNode.getParent();
        return parent != null ? parent.getParent() : null;
    }

    @Override
    protected void upHeap(TreeNode<E> node, boolean maxHeap) {
        super.upHeap(node, maxHeap);
    }

    @Override
    protected TreeNode<E> searchExtremaNode(boolean max) {
        TreeNode<E> root = getRoot();
        if (root == null) return null;
        if (!max) return root;

        // Find Max
        TreeNode<E> lChild = root.getLeftChild();
        TreeNode<E> rChild = root.getRightChild();
        if (lChild == null && rChild == null)
            return root;
        else
            return compareNode(lChild, rChild, true);
    }

    private E deleteExtrema(boolean max) {
        TreeNode<E> root = getRoot();
        TreeNode<E> target = max ? searchExtremaNode(true) : root;
        if (target == null) return null;

        E extrema = target.getData();

        TreeNode<E> lastNode = getLastNode();
        E lastNodeData = lastNode.getData();
        lastNode.deleteParent();

        // If the target is lastNode, simply delete it.
        if (lastNode == target) {
            if (lastNode == root)
                clearRoot();

            return extrema;
        }

        while (true) {
            TreeNode<E> lChild = target.getLeftChild();
            TreeNode<E> rChild = target.getRightChild();

            if (lChild == null && rChild == null) {
                target.setData(lastNodeData);
                break;
            }

            TreeNode<E> lLNode = null;
            TreeNode<E> lRNode = null;
            TreeNode<E> rLNode = null;
            TreeNode<E> rRNode = null;

            if (lChild != null) {
                lLNode = lChild.getLeftChild();
                lRNode = lChild.getRightChild();
            }
            if (rChild != null) {
                rLNode = rChild.getLeftChild();
                rRNode = rChild.getRightChild();
            }
            TreeNode arr[] = {lChild, rChild, lLNode, lRNode, rLNode, rRNode};
            TreeNode<E> extremaDescendant = getExtremaNodeFromArr(arr, max);
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

                    TreeNode<E> p = extremaDescendant.getParent();
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

    boolean isMinLevel(TreeNode<E> node) {
        int level = node.getLevel();
        return level % 2 == 1;
    }
}
