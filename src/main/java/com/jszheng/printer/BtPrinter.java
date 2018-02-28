package com.jszheng.printer;

import com.jszheng.base.BinaryTree;

public interface BtPrinter extends Printer {

    String getPrintString(BinaryTree bt);

    default void printBt(BinaryTree bt) {
        String printStr = getPrintString(bt);
        System.out.println(printStr);
    }
}
