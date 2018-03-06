package com.jszheng.heap.binomial;

import com.jszheng.Env;
import com.jszheng.heap.MeldableHeap;
import com.jszheng.heap.MinHeap;
import com.jszheng.printer.HorizontalTreePrinter;
import com.jszheng.printer.TreePrinter;

import java.util.*;

import static com.jszheng.printer.Printer.printByCount;

public class BinomialHeap<E extends Comparable<? super E>>
        implements MinHeap<E, BinomialTreeNode<E>>, MeldableHeap<BinomialHeap<E>, E, BinomialTreeNode<E>> {

    // Root List
    protected BinomialTreeNode<E> first;
    protected BinomialTreeNode<E> root; // min

    private TreePrinter printer;

    private boolean mergeWithSort;

    public BinomialHeap() {
    }

    public BinomialHeap(boolean mergeWithSort) {
        this.mergeWithSort = mergeWithSort;
    }

    @Override
    public E deleteMin() {
        if (root == null) return null;
        E min = root.data;

        BinomialTreeNode<E> consolidateTarget = root.getRightSibling();

        deleteNodeWithConcatChild(root);

        if (consolidateTarget == null) consolidateTarget = first;
        consolidate(consolidateTarget);

        return min;
    }

    @Override
    public E searchMin() {
        if (root == null) return null;
        return root.data;
    }

    @Override
    @SafeVarargs
    public final void insert(E... data) {
        for (E d : data) {
            if (Env.debug) System.out.println("[insert] data: " + d);
            insertData(d);
            if (Env.debug) System.out.println();
        }

    }

    @Override
    public BinomialTreeNode<E> newNode() {
        return new BinomialTreeNode<>();
    }

    @Override
    public void print() {
        if (root == null) {
            System.out.println("Tree is empty.");
            System.out.println();
            return;
        }
        if (printer == null)
            printer = new HorizontalTreePrinter();


        printByCount(35, "=");
        System.out.println();

        BinomialTreeNode<E> currentNode = first;

        while (currentNode != null) {
            if (currentNode == root)
                System.out.println("最小值");
            BinomialHeap<E> tmp = new BinomialHeap<>();
            tmp.root = currentNode;
            printer.print(tmp);

            currentNode = currentNode.getRightSibling();
        }

        printByCount(35, "=");
        System.out.println();
    }

    @Override
    public int size() {
        return size(first);
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

    @Override
    public BinomialTreeNode<E> getRoot() {
        return root;
    }

    // 設置新的根節點 (將取代現有之 root list)
    @Override
    public void setRoot(BinomialTreeNode<E> node) {
        first = node;
        root = node;
    }

    @Override
    public boolean isEmpty() {
        return root == null;
    }

    public int maxDegree() {
        if (first == null) return 0;

        int size = size();
        return (int) Math.floor(Math.log(size) / Math.log(2));
    }

    @Override
    public void merge(BinomialHeap<E> bh) {
        BinomialTreeNode<E> targetNode;
        if (bh == null || (targetNode = bh.root) == null)
            return;

        merge(targetNode);
    }

    public void postOrder() {
        postOrderTraverse(first);
        System.out.println("\n");
    }

    private BinomialTreeNode<E> getRightmostRoot() {
        return first.lLink;
    }

    protected void insertData(E value) {
        BinomialHeap<E> bh = new BinomialHeap<>();
        BinomialTreeNode<E> node = newNode();
        node.data = value;
        bh.setRoot(node);
        merge(bh);
    }

    // 將 greaterRoot 作為 smallerRoot 之子樹
    protected void mergeSameDegreeTree(BinomialTreeNode<E> smallerRoot, BinomialTreeNode<E> greaterRoot) {
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

    // precondition: first 及 target 所在之串列，需維護好 circular doubly linked list
    // postcondition: 將以 first 起始之 root list，與 target node list 進行串連
    void concatRootList(BinomialTreeNode<E> target) {
        if (target == null) return;
        target = getLMostOrRMostNode(target, true);
        target.isLLinkCircular = false;

        BinomialTreeNode<E> rMostRootOfTarget = target.lLink;

        BinomialTreeNode<E> rMostRoot = getRightmostRoot();
        rMostRoot.isRLinkCircular = false;

        setSibling(rMostRoot, target);
        first.lLink = rMostRootOfTarget;

        rMostRootOfTarget.rLink = first;
        rMostRootOfTarget.isRLinkCircular = true;
    }

    // 尋找、合併相同分支度節點，並維護 min root
    void consolidate(BinomialTreeNode<E> start) {
        if (start == null) return;
        BinomialTreeNode<E> currentRoot = start;
        Map<Integer, BinomialTreeNode<E>> nodes = new HashMap<>();
        List<BinomialTreeNode<E>> roots = new ArrayList<>();

        // 建節點串列 (方便迭代 -- currentRoot 可能會被 merge)，並尋找最小值
        BinomialTreeNode<E> minNode = null;
        do {
            roots.add(currentRoot);
            minNode = getSmallerNode(minNode, currentRoot);
            currentRoot = currentRoot.rLink;
        } while (currentRoot != start);

        root = minNode;

        if (mergeWithSort)
            sortRootList(roots);

        for (BinomialTreeNode<E> node : roots) {
            currentRoot = node;

            int currentDegree = currentRoot.degree;

            while (nodes.containsKey(currentDegree)) {
                // Another node with the same degree as currentRoot
                BinomialTreeNode<E> y = nodes.get(currentDegree);

                if (currentRoot.compareTo(y) > 0) {
                    BinomialTreeNode<E> tmp = currentRoot;
                    currentRoot = y;
                    y = tmp;
                }

                BinomialTreeNode<E> newFirst = null;
                if (y == first)
                    newFirst = first.rLink;

                mergeSameDegreeTree(currentRoot, y);

                if (newFirst != null)
                    setFirstRoot(newFirst);

                nodes.remove(currentDegree);
                currentDegree++;
            }

            nodes.put(currentDegree, currentRoot);
        }
    }

    void consolidateByPriority(BinomialTreeNode<E> start) {
        if (start == null) return;
        BinomialTreeNode<E> currentRoot = start;
        List<BinomialTreeNode<E>> roots = new ArrayList<>();
        PriorityQueue<Integer> degreeQueue = new PriorityQueue<>();
        Map<Integer, PriorityQueue<BinomialTreeNode<E>>> degreeMap = new HashMap<>();

        // 建分支表、節點串列，並尋找最小值
        BinomialTreeNode<E> minNode = null;
        do {
            roots.add(currentRoot);

            minNode = getSmallerNode(minNode, currentRoot);

            int currentDegree = currentRoot.degree;
            if (!degreeQueue.contains(currentDegree))
                degreeQueue.add(currentDegree);

            PriorityQueue<BinomialTreeNode<E>> nodeQueue = getNodeQueueFromDegreeMap(degreeMap, currentDegree);
            nodeQueue.add(currentRoot);

            currentRoot = currentRoot.rLink;
        } while (currentRoot != start);

        root = minNode;

        if (mergeWithSort)
            sortRootList(roots);

        while (!degreeQueue.isEmpty()) {
            int targetDegree = degreeQueue.peek(); // 取出最小目標 degree
            PriorityQueue<BinomialTreeNode<E>> nodeQueue = degreeMap.get(targetDegree);

            if (nodeQueue.size() < 2)
                degreeQueue.poll(); // 至少需兩節點才能進行合併
            else {
                BinomialTreeNode<E> smallerNode = nodeQueue.poll(); // 較小節點
                BinomialTreeNode<E> greaterNode = nodeQueue.poll(); // 較大節點

                if (greaterNode == first)
                    setFirstRoot(first.rLink);

                mergeSameDegreeTree(smallerNode, greaterNode); // 分支度必相同，直接進行合併

                int newDegree = smallerNode.degree;
                degreeQueue.add(newDegree); // 新分支度可能不存在於 degreeQueue 中

                PriorityQueue<BinomialTreeNode<E>> newNodeQueue = getNodeQueueFromDegreeMap(degreeMap, newDegree);
                newNodeQueue.add(smallerNode);
            }
        }
    }

    // 需維護 first node
    private void deleteNodeWithConcatChild(BinomialTreeNode<E> node) {
        if (node == null) return;
        BinomialTreeNode<E> lSibling = node.getLeftSibling();
        BinomialTreeNode<E> rSibling = node.getRightSibling();
        BinomialTreeNode<E> rightmostOfRSibling = rSibling != null ? first.lLink : null;

        BinomialTreeNode<E> child = node.child;

        if (lSibling == null && rSibling == null && child == null) {
            first = null;
            root = null;
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

    private BinomialTreeNode<E> getLMostOrRMostNode(BinomialTreeNode<E> start, boolean leftmost) {
        BinomialTreeNode<E> result = start;
        BinomialTreeNode<E> currentRoot = start;
        while (currentRoot != null) {
            result = currentRoot;
            currentRoot = leftmost ? currentRoot.getLeftSibling() : currentRoot.getRightSibling();
        }
        return result;
    }

    private PriorityQueue<BinomialTreeNode<E>> getNodeQueueFromDegreeMap(Map<Integer, PriorityQueue<BinomialTreeNode<E>>> map, int degree) {
        PriorityQueue<BinomialTreeNode<E>> queue;
        if (!map.containsKey(degree)) {
            queue = new PriorityQueue<>();
            map.put(degree, queue);
        } else
            queue = map.get(degree);

        return queue;
    }

    private BinomialTreeNode<E> getSmallerNode(BinomialTreeNode<E> n1, BinomialTreeNode<E> n2) {
        if (n1 == null) return n2;
        if (n2 == null) return n1;
        return n1.compareTo(n2) > 0 ? n2 : n1;
    }

    private void merge(BinomialTreeNode<E> targetNode) {
        if (root == null) {
            setRoot(targetNode);
            return;
        }

        if (targetNode != null)
            concatRootList(targetNode);

        consolidate(targetNode);
    }

    // 以 parent 判斷節點是否位於 rootList
    private void postOrderTraverse(BinomialTreeNode<E> node) {
        if (node == null) return;

        postOrderTraverse(node.child);

        // 已走訪完子節點，直接印 data
        System.out.printf("%s ", node.data);

        postOrderTraverse(node.getRightSibling());
    }

    private void setFirstRoot(BinomialTreeNode<E> node) {
        first = node;
        first.isLLinkCircular = true;
        first.lLink.isRLinkCircular = true;
    }

    // low-level API -- Do not need to maintain circular state of link.
    private void setSibling(BinomialTreeNode<E> n1, BinomialTreeNode<E> n2) {
        n1.rLink = n2;
        n2.lLink = n1;
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
        root = first;

        BinomialTreeNode<E> rMostRoot = roots.get(i);
        rMostRoot.rLink = first;
        rMostRoot.isRLinkCircular = true;

        first.lLink = rMostRoot;
        first.isLLinkCircular = true;
    }
}
