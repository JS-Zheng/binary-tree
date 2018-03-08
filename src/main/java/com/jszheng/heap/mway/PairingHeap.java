package com.jszheng.heap.mway;

import com.jszheng.Env;
import com.jszheng.heap.MeldableHeap;

import java.util.List;
import java.util.Stack;

/*
 * They are considered a "robust choice" for implementing such algorithms as Prim's MST algorithm,
 * and support the following operations (assuming a min-heap):
 *
 * find-min [Θ(1)] : simply return the top element of the heap.
 * merge [Θ(1)]: compare the two root elements, the smaller remains the root of the result, the larger element and its subtree is appended as a child of this root.
 * insert [Θ(1)]: create a new heap for the inserted element and merge into the original heap.
 * decrease-key [Amortized o(log n)]: remove the subtree rooted at the key to be decreased, replace the key with a smaller key, then merge the result back into the heap.
 * delete-min [amortized O(log n)]: remove the root and merge its subtrees. Various strategies are employed.
 *
 * wikipedia -- https://en.wikipedia.org/wiki/Pairing_heap
 * paper -- http://www.cs.cmu.edu/afs/cs.cmu.edu/user/sleator/www/papers/pairing-heaps.pdf
 */
public class PairingHeap<E extends Comparable<? super E>> extends AbstractMWayHeap<E, PairingTreeNode<E>>
        implements MinReducibleHeap<E, PairingTreeNode<E>>, MeldableHeap<PairingHeap<E>, E, PairingTreeNode<E>> {

    public PairingHeap() {
    }

    public void decreaseKey(PairingTreeNode<E> node, E newKey) {
        if (node == null) return;
        if (Env.debug)
            System.out.println("[decrease key] make key of node: " + node.getData() + " to " + newKey);

        if (newKey.compareTo(node.data) > 0) {
            System.out.println("[decrease key] new key is greater than current key.\n");
            return;
        }

        node.data = newKey;

        PairingTreeNode<E> parent = node.parent;
        if (node == min || node.compareTo(parent) > 0) return;

        cut(node, parent);
        merge(node); // node != min, just merge
        if (Env.debug) System.out.println();
    }

    @Override
    public E delete(E value) {
        PairingTreeNode<E> node = search(value);
        if (node != null) return delete(node);
        else if (Env.debug) System.out.println("[delete] not found\n");
        return null;
    }

    @Override
    public E deleteMin() {
        // implement by two-pass pairing heap
        // Using the two-pass combining method reduces
        // the number of nodes at the child level by at least half
        // https://stackoverflow.com/questions/22478773/

        if (min == null) return null;
        E minData = min.getData();

        List<PairingTreeNode<E>> children = min.getChildren();
        PairingTreeNode<E> firstChild;
        first = null;
        min = null;

        if (children == null || children.size() == 0) return minData;

        firstChild = children.get(0);

        if (children.size() == 1) {
            firstChild.parent = null;
            first = firstChild;
            min = firstChild;
        } else {
            Stack<PairingHeap<E>> mergeStack = new Stack<>();

            for (int i = 0; i < children.size(); i = i + 2) {
                int j = i + 1;
                PairingTreeNode<E> c1 = children.get(i);
                PairingTreeNode<E> c2 = j < children.size() ? children.get(j) : null;

                if (c1 != null) isolateNodeFromSibling(c1);
                if (c2 != null) isolateNodeFromSibling(c2);

                PairingHeap<E> h1 = newTree();
                PairingHeap<E> h2 = newTree();

                h1.setRoot(c1);
                h2.setRoot(c2);
                h1.merge(h2);

                mergeStack.push(h1);
            }

            while (!mergeStack.isEmpty()) {
                PairingHeap<E> mergeTarget = mergeStack.pop();
                merge(mergeTarget);
            }
        }

        return minData;
    }

    @Override
    public E searchMin() {
        return min != null ? min.getData() : null;
    }

    @Override
    public void merge(PairingHeap<E> ph) {
        PairingTreeNode<E> targetNode;
        if (ph == null || (targetNode = ph.min) == null)
            return;

        merge(targetNode);

        if (Env.debug) System.out.println();
    }

    @Override
    public PairingTreeNode<E> newNode() {
        return new PairingTreeNode<>();
    }

    @Override
    public PairingHeap<E> newTree() {
        return new PairingHeap<>();
    }

    @Override
    protected void insertNode(PairingTreeNode<E> node) {
        merge(node);
    }

    @Override
    public int size(PairingTreeNode<E> node) {
        return 0;
    }

    private E delete(PairingTreeNode<E> node) {
        if (node == null) return null;
        if (node == min) return deleteMin();

        E deleteData = node.data;
        PairingTreeNode<E> parent = node.parent;
        cut(node, parent);

        PairingHeap<E> newHeap = newTree();
        newHeap.setRoot(node);
        newHeap.deleteMin();

        merge(newHeap);

        return deleteData;
    }

    private void merge(PairingTreeNode<E> node) {
        if (node == null) return;

        if (Env.debug) {
            E data = node.getData();
            System.out.println("[merge] target: " + data);
        }

        if (min == null) {
            setRoot(node);
            return;
        }

        // compare link
        PairingTreeNode<E> smallerNode = compareNode(min, node, false);
        PairingTreeNode<E> greaterNode = smallerNode == min ? node : min;
        boolean isReplaceRoot = greaterNode == min;

        PairingTreeNode<E> formerChild = smallerNode.child;
        concatNodeList(greaterNode, formerChild);

        smallerNode.child = greaterNode;
        smallerNode.degree++;

        greaterNode.parent = smallerNode;

        if (isReplaceRoot) {
            first = smallerNode;
            min = smallerNode;
        }
    }
}