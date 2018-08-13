package com.jszheng.base.complete;

import com.jszheng.base.BinaryTree;
import com.jszheng.base.LinkedBinaryTree;
import com.jszheng.manipulator.BinTreeManipulator;
import com.jszheng.printer.MyBtPrinter;

public class CompleteBtManipulator extends BinTreeManipulator<CompleteBinaryTree> {

    public CompleteBtManipulator(Class dataType) {
        super(dataType);
    }

    @Override
    protected CompleteBinaryTree createTree() {
        return new CompleteBtImpl();
    }

    @Override
    protected void comment() {
        System.out.println("[註]: 完整二元樹 (Complete Binary Tree)");
        System.out.println("a. 這裡指所有「外部節點」都擁有相同 深度 (depth)，");
        System.out.println("   且所有「內部節點」分支度恰為 2 之二元樹；");
        System.out.println("   簡單來說，即節點需由上至下、由左至右。");
        System.out.println();
        System.out.println("b. 有些書的定義則傾向於 正規二元樹 (Proper Binary Tree)，");
        System.out.println("   不要求「外部節點」擁有相同深度，但所有「內部節點」分支度需為 2，例如：");
        String arr[] = {"a", "b", "c", null, null, "d", "e"};
        BinaryTree<String> ex = new LinkedBinaryTree<>(arr);
        new MyBtPrinter().print(ex);
    }
}
