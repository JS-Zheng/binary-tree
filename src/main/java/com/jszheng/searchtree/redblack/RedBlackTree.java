package com.jszheng.searchtree.redblack;

import com.jszheng.base.BinaryTree;
import com.jszheng.insertion.InsertionAlgo;
import com.jszheng.node.TreeNode;
import com.jszheng.searchtree.BstDeletion;
import com.jszheng.searchtree.SelfBalancingBst;

import java.util.HashMap;
import java.util.Map;

import static com.jszheng.Env.osName;
import static com.jszheng.searchtree.redblack.RedBlackTree.Color.BLACK;

/**
 * 1. Root always BLACK.
 * 2. Color of Node is either BLACK or RED.
 * 3. Null is BLACK.
 * 4. Child of RED Node are always BLACK.
 * 5. Any path from root to leaf has same count of BLACK Node.
 * 6. Longest path always <= 2 * shortest path
 * <p>
 * Created by zhengzhongsheng on 2017/10/24.
 */
public class RedBlackTree<E extends Comparable<? super E>> extends SelfBalancingBst<E> {

    private Map<TreeNode<E>, Color> colors = new HashMap<>();

    public RedBlackTree(BinaryTree<E> component) {
        super(component);
    }

    // use 'public access modifier' to facilitate testing
    public Color colorOf(TreeNode<E> node) {
        // BLACK of default value is prepared for External Node.
        return colors.getOrDefault(node, BLACK);
    }

    @Override
    public BinaryTree<E> copy(boolean deep) {
        return new RedBlackTree<>(component.copy(deep));
    }

    @Override
    public RedBlackTree<E> newTree() {
        return new RedBlackTree<>(component.newTree());
    }

    @Override
    protected BstDeletion<E> createDeletionAlgo() {
        if (deletionAlgo == null)
            deletionAlgo = new RedBlackDeletion<>();
        return deletionAlgo;
    }

    @Override
    protected InsertionAlgo<E> createInsertionAlgo() {
        if (insertionAlgo == null)
            insertionAlgo = new RedBlackInsertion<>();
        return insertionAlgo;
    }

    @Override
    public String getNodeString(TreeNode<E> node) {
        E data = node != null ? node.getData() : null;
        if (osName.contains("Windows"))
            return data != null ? data.toString() +
                    "(" + (colorOf(node) == BLACK ? "黑" : "紅") + ")" : " ";
        else
            return data != null ? data.toString() +
                    "(" + (colorOf(node) == BLACK ? "⚫" : "🔴") + ")" : " "; // Keep one space to mock null.
    }

    @Override
    public void setRoot(E data) {
        super.setRoot(data);
        colors.put(getRoot(), BLACK);
    }

    public enum Color {
        RED, BLACK
    }

    void putColor(TreeNode<E> node, Color color) {
        colors.put(node, color);
    }

    void removeColor(TreeNode<E> node) {
        colors.remove(node);
    }
}
