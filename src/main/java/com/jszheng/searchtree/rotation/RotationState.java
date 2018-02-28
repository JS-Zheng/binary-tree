package com.jszheng.searchtree.rotation;

import com.jszheng.base.BinaryTree;
import com.jszheng.node.TreeNode;

public interface RotationState {

    <E> void rotate(BinaryTree<E> bt, TreeNode<E> parent);
}
