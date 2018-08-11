package com.jszheng.heap.smmh;

import com.jszheng.base.BinaryTree;
import com.jszheng.base.complete.CompleteBtInsertion;
import com.jszheng.node.BinTreeNode;

public class SmmhInsertion<E extends Comparable<? super E>> extends CompleteBtInsertion<E> {

    @Override
    public boolean insert(BinaryTree<E> bt, E data) {
        if (data == null) return false;

        if (bt.isEmpty()) super.insert(bt, null);
        return super.insert(bt, data);
    }

    @Override
    protected void fixAfterInsertion(BinTreeNode<E> newNode) {
        int newNodeIndex = newNode.getIndex();
        if (newNodeIndex == 1) return;
        BinTreeNode<E> target = newNode;
        BinTreeNode<E> sibling = target.getSibling();
        E targetData = target.getData();

        if (sibling != null) {
            boolean isTargetLChild = target.isLeftChild();
            E siblingData = sibling.getData();
            int compareResult = targetData.compareTo(siblingData);
            boolean swapCondition = isTargetLChild ? compareResult > 0 : compareResult < 0;
            if (swapCondition) {
                target.setData(siblingData);
                sibling.setData(targetData);

                target = sibling;
            }
        }

        BinTreeNode<E> parent = target.getParent();
        if (parent == null) return;
        BinTreeNode<E> grandParent = parent.getParent();

        while (grandParent != null) {
            BinTreeNode<E> lElder = grandParent.getLeftChild();
            BinTreeNode<E> rElder = grandParent.getRightChild();
            // 理論上，不需檢查節點為空 (∵ Complete Binary Tree)
            E lData = lElder.getData();
            E rData = rElder.getData();

            if (targetData.compareTo(lData) < 0) {
                lElder.setData(targetData);
                target.setData(lData);

                target = lElder;
            } else if (targetData.compareTo(rData) > 0) {
                rElder.setData(targetData);
                target.setData(rData);

                target = rElder;
            } else
                break;

            parent = target.getParent();
            if (parent == null) return;
            grandParent = parent.getParent();
        }
    }

    @Override
    protected SymmetricMinMaxHeap<E> getBt() {
        BinaryTree<E> bt = super.getBt();
        return (SymmetricMinMaxHeap<E>) bt;
    }
}
