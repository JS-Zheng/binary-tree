package com.jszheng.printer;

import com.jszheng.base.BinaryTree;

// Only support Binary Tree
public interface BtPrinter extends Printer {

    default void print(BinaryTree bt) {
        String printStr = getPrintString(bt);
        System.out.println(printStr);
    }

    String getPrintString(BinaryTree bt);
}
