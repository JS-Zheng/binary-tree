package com.jszheng.searchtree.treap;

import com.jszheng.base.BinaryTree;
import com.jszheng.insertion.InsertionAlgo;
import com.jszheng.node.TreeNode;
import com.jszheng.searchtree.BstDeletion;
import com.jszheng.searchtree.SelfBalancingBst;
import com.jszheng.searchtree.rotation.LlRotation;
import com.jszheng.searchtree.rotation.RotationState;
import com.jszheng.searchtree.rotation.RrRotation;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Treap<E extends Comparable<? super E>> extends SelfBalancingBst<E> {

    private boolean minHeapImplement = true;
    private Map<TreeNode<E>, Integer> priority = new HashMap<>();

    public Treap(BinaryTree<E> component) {
        super(component);
    }

    @Override
    public String getNodeString(TreeNode<E> node) {
        Object data = node != null ? node.getData() : null;
        return data != null ? data.toString() + " (" + priorityOf(node) + ")" :
                (getRoot() == node ? "⊙" : " "); // Keep one space to mock null.
    }

    public void insertWithPriority(E data, int p) {
        if (isEmpty()) {
            setRoot(data, p);
            return;
        }

        // Default Algo
        InsertionAlgo<E> algo = new TreapInsertion<>(minHeapImplement, p);
        algo.insert(this, data);
    }

    @Override
    public void setRoot(E data) {
        super.setRoot(data);
        putRandomPriority(getRoot());
    }

    boolean isMinHeapImplement() {
        return minHeapImplement;
    }

    public void setMinHeapImplement(boolean minHeapImplement) {
        this.minHeapImplement = minHeapImplement;
    }

    @Override
    protected BstDeletion<E> createDeletionAlgo() {
        if (deletionAlgo == null)
            deletionAlgo = new TreapDeletion<>();
        return deletionAlgo;
    }

    @Override
    protected InsertionAlgo<E> createInsertionAlgo() {
        if (insertionAlgo == null)
            insertionAlgo = new TreapInsertion<>(minHeapImplement);
        return insertionAlgo;
    }

    private void putRandomPriority(TreeNode<E> node, int bound) {
        Random rdm = new Random();
        int i = rdm.nextInt(bound);
        priority.put(node, i);
    }

    private void setRoot(E data, int p) {
        super.setRoot(data);
        putPriority(getRoot(), p);
    }

    Integer priorityOf(TreeNode<E> node) {
        return priority.get(node);
    }

    void putPriority(TreeNode<E> node, int p) {
        priority.put(node, p);
    }

    void putRandomPriority(TreeNode<E> node) {
        putRandomPriority(node, 1000);
    }

    void removePriority(TreeNode<E> node) {
        priority.remove(node);
    }

    void upHeap(TreeNode<E> node, boolean maxHeap) {
        TreeNode<E> parent = node.getParent();

        while (parent != null) {
            Integer parentPriority = priorityOf(parent);
            if (parentPriority == null) return;
            Integer currentPriority = priorityOf(node);
            int compare = currentPriority.compareTo(parentPriority);

            boolean rotateCondition = maxHeap ? compare > 0 : compare < 0;
            if (rotateCondition) {
                RotationState state = node.isLeftChild() ? new LlRotation() : new RrRotation();
                state.rotate(this, parent);
            } else
                break;

            parent = node.getParent();
        }
    }
}
