package com.jszheng.printer;

import com.jszheng.base.Tree;

// Support m-way Tree
public interface TreePrinter extends Printer {

    String getPrintString(Tree tree);

    default void print(Tree tree) {
        String printStr = getPrintString(tree);
        System.out.println(printStr);
    }
}
