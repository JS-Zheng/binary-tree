package com.jszheng.heap.interval;

import com.jszheng.base.BinaryTree;
import com.jszheng.insertion.InsertionAlgo;
import com.jszheng.node.BinTreeNode;
import com.jszheng.traversal.TraversalNodeHandler;

// O(log n)
public class IntervalInsertion<E extends Comparable<? super E>> implements InsertionAlgo<E> {

    @Override
    public boolean insert(BinaryTree<E> bt, E data) {
        if (bt.isEmpty()) {
            bt.setRoot(data);
            return true;
        }

        IntervalHeap<E> heap = (IntervalHeap<E>) bt;
        IntervalInsertHandler handler = new IntervalInsertHandler();
        heap.traverse("Level", handler);

        BinTreeNode<E> lastNode = handler.lastNode;
        int lastNodeSize = heap.dataSizeOf(lastNode);

        BinTreeNode<E> target;

        if (lastNodeSize < 2) {
            target = lastNode;
            heap.putData(target, data);
        } else if (lastNodeSize == 2 && heap.dataOf(lastNode, false) == null) {
            target = lastNode;
            heap.putLDataWithValidate(target, data);
        } else if (lastNodeSize == 2 && heap.dataOf(lastNode, true) == null) {
            target = lastNode;
            heap.putRDataWithValidate(target, data);
        } else {
            target = heap.newNode();
            BinTreeNode<E> parent = handler.candidate;
            if (handler.isLeft) {
                parent.setLeftChildWithIndex(target);
            } else {
                parent.setRightChildWithIndex(target);
            }

            heap.putData(target, data);
        }


        BinTreeNode<E> parent = target.getParent();
        if (parent == null) return true;
        E lDataOfParent = heap.lDataOf(parent);
        E rDataOfParent = heap.rDataOf(parent);

        E lData = heap.lDataOf(target);
        E rData = heap.rDataOf(target);

        if (lData != null && lData.compareTo(rDataOfParent) > 0) {
            heap.swapNodeData(target);
            E tmp = lData;
            lData = rData;
            rData = tmp;
        }

        if (lData != null && lData.compareTo(lDataOfParent) < 0)
            heap.upHeap(target, false);
        if (rData != null && rData.compareTo(rDataOfParent) > 0)
            heap.upHeap(target, true);

        return true;
    }


    private class IntervalInsertHandler implements TraversalNodeHandler<E> {

        BinTreeNode<E> lastNode;
        BinTreeNode<E> candidate;
        boolean isLeft;

        @Override
        public boolean handle(BinTreeNode<E> node) {
            lastNode = node;

            if (!node.hasLeftChild() && candidate == null) {
                candidate = node;
                isLeft = true;
            }

            if (!node.hasRightChild() && candidate == null) {
                candidate = node;
                isLeft = false;
            }

            return true;
        }
    }
}
