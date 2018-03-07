package com.jszheng.heap.mway;

import com.jszheng.Env;

public class FibonacciHeap<E extends Comparable<? super E>> extends BinomialHeap<E> {

    public FibonacciHeap() {
    }

    public FibonacciHeap(boolean mergeWithSort) {
        super(mergeWithSort);
    }

    public void decreaseKey(BinomialTreeNode<E> node, E newKey) {
        if (newKey.compareTo(node.data) > 0) {
            System.out.println("[decrease key] new key is greater than current key.\n");
            return;
        }

        node.data = newKey;
        BinomialTreeNode<E> parent = node.parent;

        if (parent != null && node.compareTo(parent) < 0) {
            cut(node, parent);
            cascadingCut(parent); // 瀑布/階層式截斷
        }

        if (node.compareTo(min) < 0)
            min = node;

        if (Env.debug) System.out.println();
    }

    public E delete(E value) {
        BinomialTreeNode<E> node = search(value);
        if (node != null) return delete(node);
        else if (Env.debug) System.out.println("[delete] not found\n");
        return null;
    }

    public E delete(BinomialTreeNode<E> node) {
        E data = node.data;
        decreaseKeyToMin(node);
        deleteMin(); // it will find new min
        if (Env.debug) System.out.println();
        return data;
    }

    @Override
    protected void insertData(E value) {
        BinomialTreeNode<E> node = newNode();
        node.data = value;

        if (isEmpty())
            setRoot(node);
        else {
            concatRootList(node);
            E rootData = min.data;
            if (value.compareTo(rootData) < 0)
                min = node;
        }
    }

    private void cascadingCut(BinomialTreeNode<E> parent) {
        BinomialTreeNode<E> grandParent = parent.parent;
        if (grandParent == null) return;
        if (!parent.childCut)
            parent.childCut = true;
        else {
            if (Env.debug)
                System.out.println("[cut] cascading cut: " + parent.data + " was marked.");

            cut(parent, grandParent);
            cascadingCut(grandParent);
        }
    }

    // 移除 parent 的 子節點 -- child，將其新增至 min list
    private void cut(BinomialTreeNode<E> child, BinomialTreeNode<E> parent) {
        if (Env.debug) System.out.println("[cut] cut child: " + child.data);
        BinomialTreeNode<E> lSibling = child.getLeftSibling();
        BinomialTreeNode<E> rSibling = child.getRightSibling();
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
        concatRootList(child);
    }

    private void decreaseKeyToMin(BinomialTreeNode<E> node) {
        BinomialTreeNode<E> parent = node.parent;

        if (parent != null) {
            cut(node, parent);
            cascadingCut(parent); // 瀑布/階層式截斷
        }

        min = node;
    }
}