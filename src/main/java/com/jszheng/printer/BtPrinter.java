package com.jszheng.printer;

import com.jszheng.base.BinaryTree;

// Only support Binary Tree
public interface BtPrinter extends Printer {

    String getPrintString(BinaryTree bt);

    default void print(BinaryTree bt) {
        String printStr = getPrintString(bt);
        System.out.println(printStr);
    }
}
