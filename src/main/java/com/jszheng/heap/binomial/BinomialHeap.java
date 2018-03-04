package com.jszheng.heap.binomial;

import com.jszheng.heap.MeldableHeap;
import com.jszheng.heap.MinHeap;
import com.jszheng.node.BinomialTreeNode;
import com.jszheng.node.TreeNode;
import com.jszheng.printer.HorizontalTreePrinter;
import com.jszheng.printer.TreePrinter;

import java.util.*;

import static com.jszheng.printer.Printer.printByCount;

public class BinomialHeap<E extends Comparable<? super E>> implements MinHeap<E>, MeldableHeap<BinomialHeap<E>, E> {

    private BinomialTreeNode<E> root; //  sibling 串列之最左節點
    private TreePrinter printer;

    @Override
    public E deleteMin() {
        if (root == null) return null;
        E min = root.data;

        BinomialTreeNode<E> rSibling = root.rSibling;
        cutOffSibling(root, true);
        List<TreeNode<E>> children = root.getChildren();
        BinomialTreeNode<E> minChild = null;

        for (TreeNode<E> n : children) {
            BinomialTreeNode<E> node = (BinomialTreeNode<E>) n;
            node.deleteParent();
            if (minChild == null)
                minChild = node;
            else {
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

            currentNode = currentNode.rSibling;
        }

        printByCount(35, "=");
        System.out.println();
    }

    @Override
    public BinomialTreeNode<E> getRoot() {
        return root;
    }

    @Override
    public boolean isEmpty() {
        return root == null;
    }

    @Override
    public void merge(BinomialHeap<E> bh) {
        BinomialTreeNode<E> targetNode;
        if (bh == null || (targetNode = bh.root) == null)
            return;

        merge(targetNode);
    }

    // 將 root 之 sibling 串列，視為不同樹
    public void postOrder() {
        BinomialTreeNode<E> tmp = root;
        while (tmp != null) {
            postOrderTraverse(tmp);
            tmp = tmp.rSibling;
        }
        System.out.println("\n");
    }

    private BinomialTreeNode<E> getLeftmostRoot() {
        return getLMostOrRMostNode(root, true);
    }

    private BinomialTreeNode<E> getRightmostRoot() {
        return getLMostOrRMostNode(root, false);
    }

    int size() {
        BinomialTreeNode<E> tmp = root;
        int count = 0;
        while (tmp != null) {
            count += (1 << tmp.degree);  // 2^degree
            tmp = tmp.rSibling;
        }
        return count;
    }

    private void cutOffSibling(BinomialTreeNode<E> node, boolean rSibling) {
        if (node == null) return;

        BinomialTreeNode<E> sibling = rSibling ? node.rSibling : node.lSibling;
        if (rSibling) {
            node.rSibling = null;
            if (sibling != null)
                sibling.lSibling = null;
        } else {
            node.lSibling = null;
            if (sibling != null)
                sibling.rSibling = null;
        }
    }

    private void findSameDegreeToMerge() {
        BinomialTreeNode<E> currentNode = root;

        PriorityQueue<Integer> degreeQueue = new PriorityQueue<>();
        Map<Integer, PriorityQueue<BinomialTreeNode<E>>> degreeMap = new HashMap<>();

        while (currentNode != null) {
            int currentDegree = currentNode.degree;
            if (!degreeQueue.contains(currentDegree))
                degreeQueue.add(currentDegree);

            PriorityQueue<BinomialTreeNode<E>> nodeQueue = getNodeQueueFromDegreeMap(degreeMap, currentDegree);
            nodeQueue.add(currentNode);

            currentNode = currentNode.rSibling;
        }

        while (!degreeQueue.isEmpty()) {
            int targetDegree = degreeQueue.peek();
            PriorityQueue<BinomialTreeNode<E>> nodeQueue = degreeMap.get(targetDegree);
            if (nodeQueue.size() < 2) {
                degreeQueue.poll();
            } else {
                BinomialTreeNode<E> smallerNode = nodeQueue.poll();
                BinomialTreeNode<E> greaterNode = nodeQueue.poll();
                mergeSameDegreeTree(smallerNode, greaterNode);

                int newDegree = smallerNode.degree;
                PriorityQueue<BinomialTreeNode<E>> newNodeQueue = getNodeQueueFromDegreeMap(degreeMap, newDegree);
                newNodeQueue.add(smallerNode);
            }
        }
    }

    private BinomialTreeNode<E> getLMostOrRMostNode(BinomialTreeNode<E> start, boolean leftmost) {
        BinomialTreeNode<E> result = start;
        BinomialTreeNode<E> currentRoot = start;
        while (currentRoot != null) {
            result = currentRoot;
            if (leftmost)
                currentRoot = currentRoot.lSibling;
            else
                currentRoot = currentRoot.rSibling;
        }
        return result;
    }

    private PriorityQueue<BinomialTreeNode<E>> getNodeQueueFromDegreeMap(Map<Integer, PriorityQueue<BinomialTreeNode<E>>> map, int degree) {
        PriorityQueue<BinomialTreeNode<E>> queue;
        if (!map.containsKey(degree)) {
            queue = new PriorityQueue<>();
            map.put(degree, queue);
        } else {
            queue = map.get(degree);
        }
        return queue;
    }

    private void insertValue(E value) {
        BinomialHeap<E> bh = new BinomialHeap<>();
        bh.root = new BinomialTreeNode<>();
        bh.root.data = value;
        merge(bh);
    }

    private int maxRootDegree() {
        if (root == null) return 0;
        int result = root.degree;
        BinomialTreeNode<E> currentNode = root;
        while (currentNode != null) {
            if (currentNode.degree > result)
                result = currentNode.degree;
            currentNode = currentNode.rSibling;
        }
        return result;
    }

    private void merge(BinomialTreeNode<E> targetNode) {
        if (root == null) {
            root = targetNode;
            return;
        }

        if (targetNode != null) {
            targetNode = setNewRootIfNecessary(targetNode);
            targetNode = getLMostOrRMostNode(targetNode, true);

            // Precondition: root值 小於 targetNode值 && targetNode.lSibling==null
            setSibling(getRightmostRoot(), targetNode);
        }

        sortRootSibling(getLeftmostRoot());
        findSameDegreeToMerge();
    }

    // 將 greaterRoot 作為 smallerRoot 之子樹
    private void mergeSameDegreeTree(BinomialTreeNode<E> smallerRoot, BinomialTreeNode<E> greaterRoot) {
        // greaterRoot sibling part
        if (greaterRoot.rSibling != null)
            greaterRoot.rSibling.lSibling = greaterRoot.lSibling;
        greaterRoot.lSibling.rSibling = greaterRoot.rSibling;

        greaterRoot.lSibling = null;
        greaterRoot.rSibling = smallerRoot.child;
        greaterRoot.parent = smallerRoot;

        if (smallerRoot.child != null)
            smallerRoot.child.lSibling = greaterRoot;
        smallerRoot.child = greaterRoot;

        smallerRoot.degree++;
    }

    // 以 parent 判斷節點是否位於 root 之 sibling 串列
    private void postOrderTraverse(BinomialTreeNode<E> node) {
        if (node == null)
            return;
        postOrderTraverse(node.child);
        if (node.parent != null)
            postOrderTraverse(node.rSibling);
        System.out.printf("%s ", node.data);
    }

    // 若 mergeTarget值 小於 root值，則以之作為新根節點
    // @return 若已設置新根節點，則回傳舊根節點作為 merge目標，否則回傳原 mergeTarget
    private BinomialTreeNode<E> setNewRootIfNecessary(BinomialTreeNode<E> mergeTarget) {
        BinomialTreeNode<E> currentRoot = root;
        E currentRootData = currentRoot.data;
        E targetNodeData = mergeTarget.data;
        BinomialTreeNode<E> minRoot = currentRootData.compareTo(targetNodeData) > 0 ? mergeTarget : currentRoot;
        if (minRoot != root) {
            this.root = mergeTarget;
            mergeTarget = currentRoot;
        }
        return mergeTarget;
    }

    private void setSibling(BinomialTreeNode<E> n1, BinomialTreeNode<E> n2) {
        n1.rSibling = n2;
        n2.lSibling = n1;
    }

    private void sortRootSibling(BinomialTreeNode<E> leftmostRoot) {
        BinomialTreeNode<E> currentRoot = leftmostRoot;
        List<BinomialTreeNode<E>> roots = new ArrayList<>();
        while (currentRoot != null) {
            roots.add(currentRoot);
            currentRoot = currentRoot.rSibling;
        }

        roots.sort(BinomialTreeNode::compareTo);
        int i = 0;
        for (; i < roots.size() - 1; i++) {
            BinomialTreeNode<E> n1 = roots.get(i);
            if (i == 0) {
                root = n1;
                root.lSibling = null;
            }
            BinomialTreeNode<E> n2 = roots.get(i + 1);
            setSibling(n1, n2);
        }
        roots.get(i).rSibling = null;
    }
}
