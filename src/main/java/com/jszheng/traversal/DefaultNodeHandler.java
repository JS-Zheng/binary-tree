package com.jszheng.traversal;

import com.jszheng.Env;
import com.jszheng.base.BinaryTree;
import com.jszheng.node.TreeNode;

public class DefaultNodeHandler<E> implements TraversalNodeHandler<E> {

    @Override
    public void afterTraversed(BinaryTree<E> bt) {
        System.out.println();
    }

    @Override
    public boolean handle(TreeNode<E> node) {
        if (node != null) {
            E data = node.getData();
            if (Env.debug && data != null)
                System.out.print(data + "  ");
        }

        return true;
    }
}
