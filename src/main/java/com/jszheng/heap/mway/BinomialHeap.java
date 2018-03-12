package com.jszheng.heap.mway;

import com.jszheng.Env;
import com.jszheng.heap.MeldableHeap;
import com.jszheng.heap.MinHeap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BinomialHeap<E extends Comparable<? super E>> extends AbstractMWayHeap<E, BinomialTreeNode<E>>
        implements MinHeap<E, BinomialTreeNode<E>>, MeldableHeap<BinomialHeap<E>, E, BinomialTreeNode<E>> {

    private boolean mergeWithSort;

    public BinomialHeap() {
    }

    public BinomialHeap(boolean mergeWithSort) {
        this.mergeWithSort = mergeWithSort;
    }

    @Override
    public E deleteMin() {
        if (min == null) return null;
        E min = this.min.data;
        if (Env.debug) System.out.println("[delete] data: " + min);
        BinomialTreeNode<E> consolidateTarget = this.min.getRightSibling();

        deleteRootWithConcatChild(this.min);

        if (consolidateTarget == null) consolidateTarget = first;
        consolidate(consolidateTarget);
        if (Env.debug) System.out.println();
        return min;
    }

    @Override
    public E searchMin() {
        if (min == null) return null;
        return min.data;
    }

    @Override
    public String getNodeString(BinomialTreeNode<E> node) {
        Object data = node != null ? node.getData() : null;
        return data != null ? data.toString() + (node.isChildCut() ? "(#)" : "") :
                (getRoot() == node ? "⊙" : " ");
    }

    @Override
    public int size(BinomialTreeNode<E> node) {
        BinomialTreeNode<E> tmp = node;
        int count = 0;
        while (tmp != null) {
            count += (1 << tmp.degree);  // 2^degree
            tmp = tmp.getRightSibling();
        }
        return count;
    }

    public int maxDegree() {
        if (first == null) return 0;

        int size = size();
        return (int) Math.floor(Math.log(size) / Math.log(2));
    }

    @Override
    public void merge(BinomialHeap<E> bh) {
        BinomialTreeNode<E> node;
        if (bh == null || (node = bh.min) == null)
            return;

        merge(node);

        if (Env.debug) System.out.println();
    }

    @Override
    public BinomialTreeNode<E> newNode() {
        return new BinomialTreeNode<>();
    }

    @Override
    public AbstractMWayHeap<E, BinomialTreeNode<E>> newTree() {
        return new BinomialHeap<>();
    }

    @Override
    public BinomialTreeNode<E> search(E data) {
        return super.search(data);
    }

    @Override
    protected void insertNode(BinomialTreeNode<E> node) {
        merge(node);
    }

    // 尋找、合併相同分支度節點，並維護 min min
    private void consolidate(BinomialTreeNode<E> start) {
        if (start == null) return;
        BinomialTreeNode<E> currentRoot = start;
        Map<Integer, BinomialTreeNode<E>> nodes = new HashMap<>();
        List<BinomialTreeNode<E>> roots = new ArrayList<>();

        // 建節點串列 (方便迭代 -- currentRoot 可能會被 merge)，並尋找最小值
        BinomialTreeNode<E> minNode = null;
        do {
            roots.add(currentRoot);
            minNode = compareNode(minNode, currentRoot, false);
            currentRoot = currentRoot.rLink;
        } while (currentRoot != start);

        min = minNode;

        if (mergeWithSort)
            sortRootList(roots);

        for (BinomialTreeNode<E> node : roots) {
            currentRoot = node;

            int currentDegree = currentRoot.degree;

            while (nodes.containsKey(currentDegree)) {
                // Another node with the same degree as currentRoot
                BinomialTreeNode<E> mergeTarget = nodes.get(currentDegree);

                if (currentRoot.compareTo(mergeTarget) > 0) {
                    BinomialTreeNode<E> tmp = currentRoot;
                    currentRoot = mergeTarget;
                    mergeTarget = tmp;
                }

                BinomialTreeNode<E> newFirst = null;
                if (mergeTarget == first)
                    newFirst = first.rLink;

                // Prevent same min issue
                if (mergeTarget == min) min = currentRoot;
                mergeSameDegreeTree(currentRoot, mergeTarget);

                if (newFirst != null)
                    setFirstRoot(newFirst);

                nodes.remove(currentDegree);
                currentDegree++;
            }

            nodes.put(currentDegree, currentRoot);
        }
    }

    // 需維護 first node
    private void deleteRootWithConcatChild(BinomialTreeNode<E> node) {
        if (node == null) return;
        BinomialTreeNode<E> lSibling = node.getLeftSibling();
        BinomialTreeNode<E> rSibling = node.getRightSibling();
        BinomialTreeNode<E> rightmostOfRSibling = rSibling != null ? first.lLink : null;

        BinomialTreeNode<E> child = node.child;

        if (lSibling == null && rSibling == null && child == null) {
            first = null;
            min = null;
            return;
        }

        if (child != null) {
            BinomialTreeNode<E> curr = child;
            do {
                curr.parent = null;
                curr = curr.rLink;
            } while (curr != child);
        }

        if (lSibling != null) {
            first.lLink = lSibling;
            lSibling.rLink = first;
            lSibling.isRLinkCircular = true;

            // child 本身已維護好 circular 性質
            concatRootList(child);

        } else { // 被移除之 node 為原 first
            first = child; // 可能為空
        }

        if (rSibling != null) {
            rSibling.lLink = rightmostOfRSibling;
            rSibling.isLLinkCircular = true;
            rightmostOfRSibling.rLink = rSibling;

            if (first != null)
                concatRootList(rSibling);
            else
                first = rSibling;
        }
    }

    private void merge(BinomialTreeNode<E> node) {
        if (node == null) return;

        if (Env.debug) {
            E data = node.getData();
            System.out.println("[merge] target: " + data);
        }

        if (min == null) {
            setRoot(node);
            return;
        }

        concatRootList(node);

        consolidate(node);
    }

    // 將 greaterRoot 作為 smallerRoot 之子樹
    private void mergeSameDegreeTree(BinomialTreeNode<E> smallerRoot, BinomialTreeNode<E> greaterRoot) {
        if (greaterRoot.isRLinkCircular) {
            first.lLink = greaterRoot.lLink;
            greaterRoot.lLink.isRLinkCircular = true;
        } else
            greaterRoot.rLink.lLink = greaterRoot.lLink;

        greaterRoot.lLink.rLink = greaterRoot.rLink;

        greaterRoot.lLink = greaterRoot;
        greaterRoot.rLink = greaterRoot;
        greaterRoot.parent = smallerRoot;
        greaterRoot.isLLinkCircular = true;
        greaterRoot.isRLinkCircular = true;

        BinomialTreeNode<E> child = smallerRoot.child;

        if (child != null) {
            greaterRoot.rLink = child;
            greaterRoot.isRLinkCircular = false;

            BinomialTreeNode<E> rMostNodeOfChild = child.lLink;
            greaterRoot.lLink = rMostNodeOfChild;
            rMostNodeOfChild.rLink = greaterRoot;

            child.lLink = greaterRoot;
            child.isLLinkCircular = false;
        }

        smallerRoot.child = greaterRoot;

        smallerRoot.degree++;
    }

    private void sortRootList(List<BinomialTreeNode<E>> roots) {
        roots.sort(BinomialTreeNode::compareTo);

        int i = 0;
        first = roots.get(0);

        for (; i < roots.size() - 1; i++) {
            BinomialTreeNode<E> n1 = roots.get(i);
            if (i == 0) {
                first = n1;
            }
            BinomialTreeNode<E> n2 = roots.get(i + 1);
            setSibling(n1, n2);

            n1.isRLinkCircular = false;
            n2.isLLinkCircular = false;
        }

        first = roots.get(0);
        min = first;

        BinomialTreeNode<E> rMostRoot = roots.get(i);
        rMostRoot.rLink = first;
        rMostRoot.isRLinkCircular = true;

        first.lLink = rMostRoot;
        first.isLLinkCircular = true;
    }
}
