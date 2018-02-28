package com.jszheng.base;

import com.jszheng.Env;
import com.jszheng.base.completebt.CompleteBtInsertion;
import com.jszheng.base.completebt.LinearSearch;
import com.jszheng.insertion.InsertionAlgo;
import com.jszheng.node.LinkedTreeNode;
import com.jszheng.node.TreeNode;
import com.jszheng.search.SearchAlgo;
import com.jszheng.traversal.TraversalAlgo;
import com.jszheng.traversal.TraversalAlgoFactory;
import com.jszheng.traversal.TraversalNodeHandler;

import java.util.List;

import static com.jszheng.base.BinaryTreeLemma.parentIndex;

public class LinkedBinaryTree<E> extends AbstractBinaryTree<E> {

    private TreeNode<E> root;

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
        TreeNode<E> newNode = null;
        if (!isEmpty())
            newNode = copyNodes(root, deep);

        BinaryTree<E> newTree = newTree();
        newTree.setRoot(newNode);

        return newTree;
    }

    @Override
    public TreeNode<E> getNodeByIndex(int index) {
        TreeNode<E> root = getRoot();
        if (index == 0) return root;
        else if (index == 1) return root.getLeftChild();
        else if (index == 2) return root.getRightChild();
        else if (index < 0) return null;

        int parentIndex = parentIndex(index);
        TreeNode<E> parent = getNodeByIndex(parentIndex);
        if (parent == null) return null;


        return index % 2 == 1 ? parent.getLeftChild() : parent.getRightChild();
    }

    @Override
    public LinkedBinaryTree<E> newTree() {
        return new LinkedBinaryTree<>();
    }

    @Override
    public void setDataByArr(E[] data) {
        root = new LinkedTreeNode<>();
        root.setDataByArr(data);
    }

    @Override
    public void setRoot(E data) {
        if (root == null)
            root = new LinkedTreeNode<>();

        root.setData(data);
    }

    @Override
    public TreeNode<E> getLastNode() {
        List<TreeNode<E>> list = traverse("Level", false, false, node -> true);
        int i = list.size() - 1;
        return list.get(i);
    }

    @Override
    public TreeNode<E> getRoot() {
        return this.root;
    }

    @Override
    public void setRoot(TreeNode<E> node) {
        this.root = node;
    }

    @Override
    public boolean isEmpty() {
        return root == null;
    }

    @Override
    public List<TreeNode<E>> traverse(Class clz) {
        TraversalAlgo algo = TraversalAlgoFactory.create(clz, false, null);
        return traverse(algo);
    }

    @Override
    public List<TreeNode<E>> traverse(String order, boolean recursive,
                                      boolean isFullMode, TraversalNodeHandler<E> handler) {
        TraversalAlgo algo = TraversalAlgoFactory.create(order, recursive, isFullMode, handler);
        return traverse(algo);
    }

    @Override
    protected InsertionAlgo<E> createInsertionAlgo() {
        // Default Algo
        return new CompleteBtInsertion<>();
    }

    @Override
    protected SearchAlgo<E> createSearchAlgo() {
        return new LinearSearch<>();
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    private List<TreeNode<E>> traverse(TraversalAlgo algo) {
        List<TreeNode<E>> result = null;

        if (algo != null)
            result = algo.traverse(this);
        else if (Env.debug)
            System.out.println("TraversalAlgo Factory Error");

        return result;
    }
}
