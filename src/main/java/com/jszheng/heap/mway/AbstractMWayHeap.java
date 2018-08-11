package com.jszheng.heap.mway;

import com.jszheng.Env;
import com.jszheng.heap.Heap;
import com.jszheng.printer.HorizontalTreePrinter;
import com.jszheng.printer.TreePrinter;

import static com.jszheng.printer.Printer.printByCount;

public abstract class AbstractMWayHeap<E extends Comparable<? super E>,
        Node extends AbstractMWayHeapNode<E, Node>> implements Heap<E, Node> {

    // Root List
    Node first;
    Node min;

    private TreePrinter printer;

    public void postOrder() {
        postOrderTraverse(first, new DefaultNodeHandler());
        System.out.println("\n");
    }

    public Node search(E data) {
        SearchNodeHandler handler = new SearchNodeHandler(data);
        postOrderTraverse(first, handler);
        return handler.result;
    }

    @Override
    public Node getRoot() {
        return min;
    }

    // 設置新的根節點 (將取代現有之 min list)
    @Override
    public void setRoot(Node node) {
        first = node;
        min = node;
    }

    @Override
    public void insert(E datum) {
        if (Env.debug) System.out.println("[insert] data: " + datum);
        Node node = newNode();
        node.data = datum;
        if (min == null)
            setRoot(node);
        else
            insertNode(node);

        if (Env.debug) System.out.println();
    }

    @Override
    public final void insert(E[] data) {
        for (E d : data) {
            insert(d);
        }
    }

    public abstract Node newNode();

    @Override
    public void print() {
        if (min == null) {
            System.out.println("Tree is empty.");
            System.out.println();
            return;
        }
        if (printer == null)
            printer = new HorizontalTreePrinter();


        printByCount(35, "=");
        System.out.println();

        Node currentNode = first;

        while (currentNode != null) {
            if (currentNode == min)
                System.out.println("最小值");
            AbstractMWayHeap<E, Node> tmp = newTree();
            tmp.min = currentNode;
            printer.print(tmp);

            currentNode = currentNode.getRightSibling();
        }

        printByCount(35, "=");
        System.out.println();
        System.out.println();
    }

    @Override
    public int size() {
        return size(first);
    }

    @Override
    public boolean isEmpty() {
        return min == null;
    }

    public abstract AbstractMWayHeap<E, Node> newTree();

    protected abstract void insertNode(Node node);

    // precondition: first 及 target 所在之串列需維護好 circular doubly linked list
    // postcondition: 將以 first 起始之 min list，與 target node list 進行串連
    void concatRootList(Node target) {
        concatNodeList(first, target);
    }

    void concatNodeList(Node node, Node target) {
        if (node == null || target == null) return;
        target = getLMostOrRMostNode(target, true);
        target.isLLinkCircular = false;

        Node rMostRootOfTarget = target.lLink;

        Node rMostOfNode = node == first
                ? getRightmostRoot() : getLMostOrRMostNode(node, false);
        rMostOfNode.isRLinkCircular = false;

        setSibling(rMostOfNode, target);
        node.lLink = rMostRootOfTarget;

        rMostRootOfTarget.rLink = node;
        rMostRootOfTarget.isRLinkCircular = true;
    }

    private Node getLMostOrRMostNode(Node start, boolean leftmost) {
        Node result = start;
        Node currentRoot = start;
        while (currentRoot != null) {
            result = currentRoot;
            currentRoot = leftmost ? currentRoot.getLeftSibling() : currentRoot.getRightSibling();
        }
        return result;
    }

    private Node getRightmostRoot() {
        return first.lLink;
    }

    // low-level API -- Do not need to maintain circular state of link.
    void setSibling(Node n1, Node n2) {
        n1.rLink = n2;
        n2.lLink = n1;
    }

    // 移除 parent 的 子節點 -- child
    void cut(Node child, Node parent) {
        if (Env.debug) System.out.println("[cut] cut child: " + child.data);
        Node lSibling = child.getLeftSibling();
        Node rSibling = child.getRightSibling();
        if (lSibling != null && rSibling != null) {
            lSibling.rLink = rSibling;
            rSibling.lLink = lSibling;
        } else if (lSibling != null) {
            lSibling.rLink = child.rLink;
            lSibling.isRLinkCircular = true;
            lSibling.rLink.lLink = lSibling;
        } else if (rSibling != null) {
            parent.child = rSibling; // set new child
            rSibling.lLink = child.lLink;
            rSibling.isLLinkCircular = true;
            rSibling.lLink.rLink = rSibling;
        } else {
            parent.child = null;
        }

        parent.degree--;

        isolateNodeFromSibling(child);
    }

    // low-level API -- Do not need to maintain its sibling.
    void isolateNodeFromSibling(Node node) {
        node.parent = null;
        node.lLink = node;
        node.rLink = node;
        node.isLLinkCircular = true;
        node.isRLinkCircular = true;
    }

    void setFirstRoot(Node node) {
        first = node;
        first.isLLinkCircular = true;
        first.lLink.isRLinkCircular = true;
    }

    // 以 parent 判斷節點是否位於 rootList
    private void postOrderTraverse(Node node, MWayTreeNodeHandler<Node, E> handler) {
        if (node == null) return;

        postOrderTraverse(node.child, handler);

        if (!handler.handle(node)) return;

        postOrderTraverse(node.getRightSibling(), handler);
    }

    private class DefaultNodeHandler implements MWayTreeNodeHandler<Node, E> {

        @Override
        public boolean handle(Node node) {
            // 已走訪完子節點，直接印 data
            System.out.printf("%s ", node.data);

            return true;
        }
    }

    private class SearchNodeHandler implements MWayTreeNodeHandler<Node, E> {

        Node result;
        private E target;

        SearchNodeHandler(E target) {
            this.target = target;
        }

        @Override
        public boolean handle(Node node) {
            if (node.data.equals(target)) { // use equals rather than == operator
                result = node;
                return false;
            }
            return true;
        }
    }
}
