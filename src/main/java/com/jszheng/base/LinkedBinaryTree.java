package com.jszheng.base;

import com.jszheng.Env;
import com.jszheng.base.complete.CompleteBtInsertion;
import com.jszheng.base.complete.LinearSearch;
import com.jszheng.insertion.InsertionAlgo;
import com.jszheng.node.BinTreeNode;
import com.jszheng.node.LinkedTreeNode;
import com.jszheng.search.SearchAlgo;
import com.jszheng.traversal.TraversalAlgo;
import com.jszheng.traversal.TraversalAlgoFactory;
import com.jszheng.traversal.TraversalNodeHandler;

import java.util.List;

import static com.jszheng.base.BinaryTreeLemma.parentIndex;

public class LinkedBinaryTree<E> extends AbstractBinaryTree<E> {

    private BinTreeNode<E> root;

    // Prevent execute empty-varargs constructor
    public LinkedBinaryTree() {
    }

    @SafeVarargs
    public LinkedBinaryTree(E... items) { // care heap pollution
        setDataByArr(items);
    }

    @Override
    public void clearRoot() {
        this.root = null;
    }

    @Override
    public BinaryTree<E> copy(boolean deep) {
        BinTreeNode<E> newNode = null;
        if (!isEmpty())
            newNode = copyNodes(root, deep);

        BinaryTree<E> newTree = newTree();
        newTree.setRoot(newNode);

        return newTree;
    }

    @Override
    public BinTreeNode<E> getNodeByIndex(int index) {
        BinTreeNode<E> root = getRoot();
        if (index == 0) return root;
        else if (index == 1) return root.getLeftChild();
        else if (index == 2) return root.getRightChild();
        else if (index < 0) return null;

        int parentIndex = parentIndex(index);
        BinTreeNode<E> parent = getNodeByIndex(parentIndex);
        if (parent == null) return null;


        return index % 2 == 1 ? parent.getLeftChild() : parent.getRightChild();
    }

    @Override
    public LinkedBinaryTree<E> newTree() {
        return new LinkedBinaryTree<>();
    }

    @Override
    public BinTreeNode<E> getRoot() {
        return this.root;
    }

    @Override
    public void setRoot(E data) {
        if (root == null)
            root = new LinkedTreeNode<>();

        root.setData(data);
    }

    @Override
    public void setDataByArr(E[] data) {
        root = new LinkedTreeNode<>();
        root.setDataByArr(data);
    }

    @Override
    public void setRoot(BinTreeNode<E> node) {
        this.root = node;
    }

    @Override
    public BinTreeNode<E> getLastNode() {
        List<BinTreeNode<E>> list = traverse("Level", false, false, node -> true);
        int i = list.size() - 1;
        return list.get(i);
    }

    @Override
    public List<BinTreeNode<E>> traverse(Class clz) {
        TraversalAlgo algo = TraversalAlgoFactory.create(clz, false, null);
        return traverse(algo);
    }

    @Override
    public List<BinTreeNode<E>> traverse(String order, boolean recursive,
                                         boolean isFullMode, TraversalNodeHandler<E> handler) {
        TraversalAlgo algo = TraversalAlgoFactory.create(order, recursive, isFullMode, handler);
        return traverse(algo);
    }

    @Override
    public boolean isEmpty() {
        return root == null;
    }

    @Override
    protected InsertionAlgo<E> createInsertionAlgo() {
        // Default Algo
        if (insertionAlgo == null)
            insertionAlgo = new CompleteBtInsertion<>();
        return insertionAlgo;
    }

    @Override
    protected SearchAlgo<E> createSearchAlgo() {
        // Default Algo
        if (searchAlgo == null)
            searchAlgo = new LinearSearch<>();
        return searchAlgo;
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    private List<BinTreeNode<E>> traverse(TraversalAlgo algo) {
        List<BinTreeNode<E>> result = null;

        if (algo != null)
            result = algo.traverse(this);
        else if (Env.debug)
            System.out.println("TraversalAlgo Factory Error");

        return result;
    }
}
