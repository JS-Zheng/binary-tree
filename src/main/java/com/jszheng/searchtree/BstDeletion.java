package com.jszheng.searchtree;

import com.jszheng.Env;
import com.jszheng.base.BinaryTree;
import com.jszheng.deletion.DeletionAlgo;
import com.jszheng.node.TreeNode;
import com.jszheng.search.SearchResult;

public class BstDeletion<E extends Comparable<? super E>> implements DeletionAlgo<E> {

    private BinarySearchTree<E> bst;
    private boolean replaceByLMax = true;

    @Override
    public boolean delete(BinaryTree<E> bt, E data) {
        if (bt == null)
            return false;

        if (!(bt instanceof BinarySearchTree))
            throw new RuntimeException("This BT isn't a Binary Search Tree.");

        this.bst = (BinarySearchTree<E>) bt;

        TreeNode<E> targetNode = bst.search(data);

        return (targetNode != null) && delete(bst, targetNode);
    }

    protected BinarySearchTree<E> getBt() {
        return this.bst;
    }

    protected boolean delete(BinarySearchTree<E> bst, TreeNode<E> targetNode) {
        // The info of targetNode will loss after deleting.
        TreeNode<E> parent = targetNode.getParent();
        TreeNode<E> sibling = targetNode.getSibling();
        boolean isTargetLeft = targetNode.isLeftChild();

        boolean hasLeftChild = targetNode.hasLeftChild();
        boolean hasRightChild = targetNode.hasRightChild();

        int degree = targetNode.degree();

        if (degree == 2) { // Degree 2
            deleteNodeByExtremaChild(bst, targetNode, replaceByLMax); // Default use lMax
        } else {

            if (hasLeftChild) {// Degree 1
                deleteNodeByReplaceChild(bst, targetNode, true);
            } else if (hasRightChild) {// Degree 1
                deleteNodeByReplaceChild(bst, targetNode, false);
            } else // Degree 0
                deleteNode(bst, targetNode);

            fixAfterDeletion(parent, sibling, targetNode, isTargetLeft, degree);
        }

        return true;
    }

    // Degree 2 (just replace data)
    protected void deleteNodeByExtremaChild(BinarySearchTree<E> bst, TreeNode<E> targetNode, boolean replaceByLMax) {
        TreeNode<E> child = replaceByLMax ? targetNode.getLeftChild() : targetNode.getRightChild();
        // Use tmp tree to find extrema conveniently
        // Use newGeneralBst() to prevent the additional amortized operation after searching (e.g., SplayTree)
        BinarySearchTree<E> subTree = bst.newGeneralBst();
        subTree.setRoot(child);

        // lChild Max or rChild Min
        SearchResult<E> result = replaceByLMax ? subTree.searchMax() : subTree.searchMin();

        TreeNode<E> extremaChild;
        if (result == null || (extremaChild = result.getNode()) == null)
            return;

        delete(subTree, extremaChild);
        E extremaChildData = extremaChild.getData();
        targetNode.setData(extremaChildData);
        if (Env.debug) {
            System.out.println("[delete] replace node data with " + extremaChildData);
        }
    }

    protected void fixAfterDeletion(TreeNode<E> parent, TreeNode<E> sibling, TreeNode<E> targetNode, boolean isTargetLeft, int degree) {
        // do nothing
    }

    // Degree 0
    private void deleteNode(BinarySearchTree<E> bst, TreeNode<E> targetNode) {
        TreeNode<E> parent = targetNode.getParent();
        boolean isTargetLeft = targetNode.isLeftChild();

        if (parent != null) {
            if (isTargetLeft) parent.setLeftChildWithIndex(null);
            else parent.setRightChildWithIndex(null);
        }
        if (parent == null || bst.getRoot() == targetNode) {
            bst.clearRoot();
        }
    }

    // Degree 1 (parent will set new child)
    private void deleteNodeByReplaceChild(BinarySearchTree<E> bst, TreeNode<E> targetNode, boolean replaceByLeft) {
        TreeNode<E> child = replaceByLeft ? targetNode.getLeftChild() : targetNode.getRightChild();
        TreeNode<E> parent = targetNode.getParent();
        boolean isTargetLeft = targetNode.isLeftChild();

        if (bst.getRoot() == targetNode)
            bst.setRoot(child);

        child.setParent(parent, isTargetLeft);
    }

    void setReplaceByLMax(boolean replaceByLMax) {
        this.replaceByLMax = replaceByLMax;
    }
}
