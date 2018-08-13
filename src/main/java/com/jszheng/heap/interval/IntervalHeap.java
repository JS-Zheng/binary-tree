package com.jszheng.heap.interval;

import com.jszheng.Env;
import com.jszheng.base.complete.CompleteBinaryTree;
import com.jszheng.heap.AbsBinDoubleEndedHeap;
import com.jszheng.heap.DoubleEndedHeap;
import com.jszheng.insertion.InsertionAlgo;
import com.jszheng.node.BinTreeNode;
import com.jszheng.search.SearchAlgo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 * Using multi-data nodes doesn't meet the current design.
 * Unless this demand continues to increase, it'll not be refactored,
 * which will undermine the simplicity of the design.
 *
 * Follow the spirit of the decorator pattern, without modify the node structure,
 * using additional objects (e.g., Map) to achieve multi-node feature.
 */
public class IntervalHeap<E extends Comparable<? super E>> extends AbsBinDoubleEndedHeap<E>
        implements CompleteBinaryTree<E>, DoubleEndedHeap<E, BinTreeNode<E>> {

    private Map<BinTreeNode<E>, List<E>> nodeData = new HashMap<>();

    @Override
    public void clearRoot() {
        super.clearRoot();
        nodeData.clear();
    }

    @Override
    public IntervalHeap<E> newTree() {
        return new IntervalHeap<>();
    }

    @Override
    public void setRoot(E data) {
        super.setRoot(data);
        putData(getRoot(), data);
    }

    // 需維護區間性質 (左小右大)
    void putData(BinTreeNode<E> node, E data) {
        List<E> dataList = nodeData.computeIfAbsent(node, k -> new ArrayList<>());
        if (dataList.size() > 1)
            throw new RuntimeException("element size overflow");
        else if (dataList.size() == 1) {
            dataList.add(data);
            validateInterval(node);
        } else {
            dataList.add(data);
        }
    }

    private void validateInterval(BinTreeNode<E> node) {
        List<E> dataList = nodeData.computeIfAbsent(node, k -> new ArrayList<>());
        if (dataList.size() != 2) return;

        E lData = dataList.get(0);
        E rData = dataList.get(1);
        if (lData == null || rData == null) return;
        if (lData.compareTo(rData) < 0) return;

        if (Env.debug)
            System.out.println("[swap] lInterval: " + lData + " is greater than rInterval: " + rData + " => swap!");
        // swap
        swapNodeData(node);
    }

    // low-level API -- 不需維護區間性質
    void swapNodeData(BinTreeNode<E> node) {
        List<E> dataList = nodeData.computeIfAbsent(node, k -> new ArrayList<>());
        E lData = dataList.size() > 0 ? dataList.get(0) : null;
        E rData = dataList.size() > 1 ? dataList.get(1) : null;
        putLData(node, rData);
        putRData(node, lData);
    }

    // low-level API -- 不需維護區間性質
    private void putLData(BinTreeNode<E> node, E data) {
        List<E> dataList = nodeData.computeIfAbsent(node, k -> new ArrayList<>());
        if (dataList.size() < 1)
            dataList.add(data);
        else
            dataList.set(0, data); // 覆蓋舊左區間
    }

    // low-level API -- 不需維護區間性質
    private void putRData(BinTreeNode<E> node, E data) {
        List<E> dataList = nodeData.computeIfAbsent(node, k -> new ArrayList<>());
        // 填補左區間
        if (dataList.size() < 1)
            dataList.add(null);

        if (dataList.size() > 1)
            dataList.set(1, data); // 覆蓋舊右區間
        else
            dataList.add(data);
    }

    @Override
    protected InsertionAlgo<E> createInsertionAlgo() {
        return new IntervalInsertion<>();
    }

    @Override
    public E deleteMax() {
        return deleteExtrema(true);
    }

    @Override
    public E searchMax() {
        return searchExtrema(true);
    }

    // // O(1)
    private E searchExtrema(boolean max) {
        if (isEmpty()) return null;

        BinTreeNode<E> root = getRoot();
        E lData = lDataOf(root);
        E rData = rDataOf(root);
        if (max)
            return rData != null ? rData : lData;
        else
            return lData != null ? lData : rData;
    }

    E lDataOf(BinTreeNode<E> node) {
        return dataOf(node, false);
    }

    E rDataOf(BinTreeNode<E> node) {
        return dataOf(node, true);
    }

    public E dataOf(BinTreeNode<E> node, boolean maxHeap) {
        List<E> dataList = nodeData.get(node);
        if (dataList == null) return null;
        int index = !maxHeap ? 0 : 1;
        if (dataList.size() <= index) return null;
        return dataList.get(index);
    }

    @Override
    public E deleteMin() {
        return deleteExtrema(false);
    }

    @Override
    public E searchMin() {
        return searchExtrema(false);
    }

    @Override
    public String getNodeString(BinTreeNode<E> node) {
        E lData = lDataOf(node);
        E rData = rDataOf(node);
        if (lData != null & rData != null)
            return lData + "," + rData;
        else if (lData != null)
            return lData.toString();
        else if (rData != null)
            return rData.toString();
        return " ";
    }

    @Override
    protected SearchAlgo<E> createSearchAlgo() {
        return new IntervalSearchAlgo<>();
    }

    @Override
    protected BinTreeNode<E> searchExtremaNode(boolean max) {
        return getRoot();
    }

    @Override
    protected void downHeap(BinTreeNode<E> node, boolean maxHeap) {
        BinTreeNode<E> lChild = node.getLeftChild();
        BinTreeNode<E> rChild = node.getRightChild();
        BinTreeNode<E> targetChild;

        while (lChild != null || rChild != null) {
            E data = dataOf(node, maxHeap);
            if (data == null) return;

            // max{lChild, rChild} or min{lChild, rChild}
            E lChildData = dataOf(lChild, maxHeap);
            E rChildData = dataOf(rChild, maxHeap);

            if (lChildData == null && rChildData == null) return;

            if (rChildData == null) targetChild = lChild;
            else if (lChildData == null) targetChild = rChild;
            else {
                int compare = lChildData.compareTo(rChildData);
                if (maxHeap)
                    targetChild = compare < 0 ? rChild : lChild;
                else
                    targetChild = compare < 0 ? lChild : rChild;
            }

            E childData = dataOf(targetChild, maxHeap);
            int compare = childData != null ? data.compareTo(childData) : 1;
            boolean swapCondition = maxHeap ? compare < 0 : compare > 0;

            if (swapCondition) {
                if (Env.debug) {
                    System.out.println("[down heap] swap node: " + data + "  &  child: " + childData);
                }
                swap(node, data, targetChild, childData, maxHeap);
            } else
                break;

            node = targetChild;
            lChild = node.getLeftChild();
            rChild = node.getRightChild();
        }
    }

    @Override
    protected void upHeap(BinTreeNode<E> node, boolean maxHeap) {
        BinTreeNode<E> parent = lastLevelNode(node);

        while (parent != null) {
            E parentData = dataOf(parent, maxHeap);
            if (parentData == null) return;
            E data = dataOf(node, maxHeap);
            int compare = data.compareTo(parentData);

            boolean swapCondition = maxHeap ? compare > 0 : compare < 0;
            if (swapCondition) {
                if (Env.debug) {
                    System.out.println("[up heap] swap node: " + data + "  &  parent: " + parentData);
                }
                swap(node, data, parent, parentData, maxHeap);
            } else
                break;

            node = parent;
            parent = lastLevelNode(node);
        }
    }

    int dataSizeOf(BinTreeNode<E> node) {
        List<E> data = nodeData.get(node);
        if (data != null) return data.size();
        return 0;
    }

    void putLDataWithValidate(BinTreeNode<E> node, E data) {
        putLData(node, data);
        validateInterval(node);
    }

    void putRDataWithValidate(BinTreeNode<E> node, E data) {
        putRData(node, data);
        validateInterval(node);
    }

    private void deleteData(BinTreeNode<E> node, boolean max) {
        List<E> dataList = nodeData.computeIfAbsent(node, k -> new ArrayList<>());
        if (max)
            putRData(node, null);
        else
            putLData(node, null);

        boolean allNull = true;
        for (E d : dataList) {
            if (d != null) {
                allNull = false;
                break;
            }
        }

        if (allNull)
            node.deleteParent();
    }

    // O(log n)
    private E deleteExtrema(boolean max) {
        // Extrema == Root
        E extrema = searchExtrema(max);
        if (extrema == null) return null;

        BinTreeNode<E> root = getRoot();

        if (root.degree() == 0) {
            E lData = lDataOf(root);
            E rData = rDataOf(root);

            // 根節點只有一筆資料
            if ((lData == null && rData != null) || (rData == null && lData != null))
                clearRoot();
            else {
                deleteData(root, max);
            }

            return extrema;
        }

        BinTreeNode<E> lastNode = getLastNode();
        E lastNodeData = max ? rDataOf(lastNode) : lDataOf(lastNode);

        if (lastNodeData == null) { // lastNode 只有一筆資料，且位於另一區間
            lastNodeData = max ? lDataOf(lastNode) : rDataOf(lastNode);
            deleteData(lastNode, !max);
        } else {
            deleteData(lastNode, max);
        }

        if (max)
            putRDataWithValidate(root, lastNodeData);
        else
            putLDataWithValidate(root, lastNodeData);

        downHeap(root, max);

        return extrema;
    }

    // 需維護區間性質
    private void swap(BinTreeNode<E> node, E data, BinTreeNode<E> parent, E parentData, boolean maxHeap) {
        if (maxHeap) {
            putRDataWithValidate(node, parentData);
            putRDataWithValidate(parent, data);
        } else {
            putLDataWithValidate(node, parentData);
            putLDataWithValidate(parent, data);
        }
    }


}
