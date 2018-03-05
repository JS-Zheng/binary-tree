package com.jszheng.heap.binomial;

import com.jszheng.heap.MeldableHeap;
import com.jszheng.heap.MinHeap;
import com.jszheng.node.TreeNode;
import com.jszheng.printer.HorizontalTreePrinter;
import com.jszheng.printer.TreePrinter;

import java.util.*;

import static com.jszheng.printer.Printer.printByCount;

public class BinomialHeap<E extends Comparable<? super E>>
        implements MinHeap<E, BinomialTreeNode<E>>, MeldableHeap<BinomialHeap<E>, E, BinomialTreeNode<E>> {

    // Root List
    private BinomialTreeNode<E> first;
    private BinomialTreeNode<E> root; // min

    private TreePrinter printer;

    @Override
    public E deleteMin() {
        if (root == null) return null;
        E min = root.data;

        BinomialTreeNode<E> rSibling = root.rLink;
        if (rSibling == root) rSibling = null;
        List<TreeNode<E>> children = root.getChildren();
        deleteNode(root);

        BinomialTreeNode<E> minChild = null;

        for (TreeNode<E> n : children) {
            BinomialTreeNode<E> node = (BinomialTreeNode<E>) n;
            node.deleteParent();

            if (minChild == null) {
                first = node;
                minChild = node;
            } else {
                E minChildData = minChild.data;
                E currentNodeData = node.data;
                if (currentNodeData.compareTo(minChildData) < 0)
                    minChild = node;
            }
        }

        if (minChild != null) {
            root = minChild;
            merge(rSibling);
        } else {
            first = rSibling;
            root = rSibling;
        }

        return min;
    }

    @Override
    public E searchMin() {
        if (root == null) return null;
        return root.data;
    }

    @Override
    @SafeVarargs
    public final void insert(E... value) {
        for (E v : value)
            insertValue(v);
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

        BinomialTreeNode<E> currentNode = root;

        for (int i = 0; currentNode != null; i++) {
            if (i == 0)
                System.out.println("主樹");
            else
                System.out.println("副樹");
            BinomialHeap<E> tmp = new BinomialHeap<>();
            tmp.root = currentNode;
            printer.print(tmp);

            currentNode = currentNode.getRightSibling();
        }

        printByCount(35, "=");
        System.out.println();
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

    @Override
    public boolean isEmpty() {
        return root == null;
    }

    private void setRoot(BinomialTreeNode<E> node) {
        first = node;
        root = node;
        node.rLink = node.lLink = node;
        node.isLLinkCircular = true;
        node.isRLinkCircular = true;
    }

    @Override
    public void merge(BinomialHeap<E> bh) {
        BinomialTreeNode<E> targetNode;
        if (bh == null || (targetNode = bh.root) == null)
            return;

        merge(targetNode);
    }

    // 將 rootList 之節點分別視為不同樹
    public void postOrder() {
        BinomialTreeNode<E> tmp = root;
        if (tmp == null) {
            System.out.println("Tree is empty.");
            return;
        }
        do {
            postOrderTraverse(tmp);
            tmp = tmp.getRightSibling();
        } while (tmp != root);
        System.out.println("\n");
    }

    private BinomialTreeNode<E> getLeftmostRoot() {
        return first;
    }

    private BinomialTreeNode<E> getRightmostRoot() {
        return first.lLink;
    }

    private void concatRootList(BinomialTreeNode<E> target) {
        if (target == null) return;
        target = getLMostOrRMostNode(target, true);
        target.isLLinkCircular = false;

        BinomialTreeNode<E> rMostRootOfTarget = target.lLink;

        // Precondition: root值 小於 targetNode值 && targetNode.lLink==null
        BinomialTreeNode<E> rMostRoot = getRightmostRoot();
        rMostRoot.isRLinkCircular = false;

        setSibling(rMostRoot, target);
        first.lLink = rMostRootOfTarget;

        rMostRootOfTarget.rLink = first;
        rMostRootOfTarget.isRLinkCircular = true;
    }

    private void deleteNode(BinomialTreeNode<E> node) {
        if (node == first) {
            first = node.rLink;
            if (node == first) {
                first = null;
                return;
            }
        }

        node.lLink.rLink = node.rLink;
        node.rLink.lLink = node.lLink;

        if (node.isLLinkCircular) {
            node.rLink.isLLinkCircular = true;
            node.lLink.isRLinkCircular = true;
        } else if (node.isRLinkCircular) {
            node.rLink.isLLinkCircular = true;
            node.lLink.isRLinkCircular = true;
        }
    }

    private BinomialTreeNode<E> getLMostOrRMostNode(BinomialTreeNode<E> start, boolean leftmost) {
        BinomialTreeNode<E> result = start;
        BinomialTreeNode<E> currentRoot = leftmost ? start.lLink : start.rLink;
        while (currentRoot != start) {
            result = currentRoot;
            if (leftmost)
                currentRoot = currentRoot.lLink;
            else
                currentRoot = currentRoot.rLink;
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

    private void insertValue(E value) {
        BinomialHeap<E> bh = new BinomialHeap<>();
        BinomialTreeNode<E> node = new BinomialTreeNode<>();
        node.data = value;
        bh.setRoot(node);
        merge(bh);
    }

    private void merge(BinomialTreeNode<E> targetNode) {
        if (root == null) {
            setRoot(targetNode);
            return;
        }

        if (targetNode != null)
            concatRootList(targetNode);

        sortRootListAndMerge(getLeftmostRoot());
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

    // 以 parent 判斷節點是否位於 rootList
    private void postOrderTraverse(BinomialTreeNode<E> node) {
        if (node == null) return;

        postOrderTraverse(node.child);
        if (node.parent != null)
            postOrderTraverse(node.getRightSibling());
        System.out.printf("%s ", node.data);
    }

    // low-level API -- Do not need to maintain circular state of link.
    private void setSibling(BinomialTreeNode<E> n1, BinomialTreeNode<E> n2) {
        n1.rLink = n2;
        n2.lLink = n1;
    }

    private void sortRootListAndMerge(BinomialTreeNode<E> leftmostRoot) {
        if (leftmostRoot == null) return;
        BinomialTreeNode<E> currentRoot = leftmostRoot;
        List<BinomialTreeNode<E>> roots = new ArrayList<>();
        PriorityQueue<Integer> degreeQueue = new PriorityQueue<>();
        Map<Integer, PriorityQueue<BinomialTreeNode<E>>> degreeMap = new HashMap<>();
        do {
            roots.add(currentRoot);
            int currentDegree = currentRoot.degree;
            if (!degreeQueue.contains(currentDegree))
                degreeQueue.add(currentDegree);

            PriorityQueue<BinomialTreeNode<E>> nodeQueue = getNodeQueueFromDegreeMap(degreeMap, currentDegree);
            nodeQueue.add(currentRoot);

            currentRoot = currentRoot.rLink;
        } while (currentRoot != leftmostRoot);

        roots.sort(BinomialTreeNode::compareTo);

        int i = 0;
        for (; i < roots.size() - 1; i++) {
            BinomialTreeNode<E> n1 = roots.get(i);
            if (i == 0) {
                first = n1;
                root = n1;
            }
            BinomialTreeNode<E> n2 = roots.get(i + 1);
            setSibling(n1, n2);

            n1.isRLinkCircular = false;
            n2.isLLinkCircular = false;
        }

        BinomialTreeNode<E> rMostRoot = roots.get(i);
        rMostRoot.rLink = first;
        rMostRoot.isRLinkCircular = true;

        first.lLink = rMostRoot;
        first.isLLinkCircular = true;

        while (!degreeQueue.isEmpty()) {
            int targetDegree = degreeQueue.peek();
            PriorityQueue<BinomialTreeNode<E>> nodeQueue = degreeMap.get(targetDegree);

            if (nodeQueue.size() < 2)
                degreeQueue.poll();
            else {
                BinomialTreeNode<E> smallerNode = nodeQueue.poll();
                BinomialTreeNode<E> greaterNode = nodeQueue.poll();
                mergeSameDegreeTree(smallerNode, greaterNode);

                int newDegree = smallerNode.degree;
                PriorityQueue<BinomialTreeNode<E>> newNodeQueue = getNodeQueueFromDegreeMap(degreeMap, newDegree);
                newNodeQueue.add(smallerNode);
            }
        }
    }
}
