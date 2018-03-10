package com.jszheng.huffman;

import com.jszheng.base.BinaryTree;
import com.jszheng.manipulator.BinTreeManipulator;
import com.jszheng.manipulator.TreeOperation;

import static com.jszheng.util.ScannerUtil.errorPrompt;
import static com.jszheng.util.ScannerUtil.getLine;

public class HuffmanTreeManipulator extends BinTreeManipulator {

    public HuffmanTreeManipulator(Class dataType) {
        super(dataType);
    }

    protected void addInsertOp() {
        addOperation("編碼", new EncodeOperation());
    }

    @Override
    protected void comment() {
        System.out.println("[註]: 霍夫曼樹 又稱 最佳二元樹");
        System.out.println("a. 節點結構 --  內部節點：加權   外部節點：data(加權)");
        System.out.println("b. 最佳二元樹 -- 意指 可產生最佳的二進位字元碼 (i.e., 最小加權外部路徑長)");
        System.out.println("c. 然而，這不意味著它是最佳的編碼/壓縮方式");
        System.out.println("d. 編碼代號 -- 左子編號：0，右子編號：1");
        System.out.println("e. 編碼內部實作採優先權佇列，時間複雜度： O(n lg n)");
        System.out.println("f. 支援非原文解碼操作 (利用已構建之霍夫曼樹)\n");
    }

    @Override
    protected BinaryTree createTree() {
        return new HuffmanTree(baseBt());
    }

    @Override
    protected void addOtherOp() {
        addOperation("解碼", new DecodeOperation());
        super.addOtherOp();
    }

    @Override
    protected void addSearchOp() {
        // do nothing
    }

    private class EncodeOperation implements TreeOperation<HuffmanTree> {

        @Override
        public void execute(HuffmanTree tree) {
            String str = getLine("輸入欲編碼內容:");
            str = str.trim();

            if (str.length() < 2) {
                System.out.println("至少需輸入兩字元");
                return;
            }

            if (dataType == Integer.class) {
                if (!str.matches("[\\d\\s]+")) {
                    System.out.println(errorPrompt);
                    return;
                }
            }

            tree.insert(str);

            printTree();

            tree.encode();
        }
    }

    private class DecodeOperation implements TreeOperation<HuffmanTree> {

        @Override
        public void execute(HuffmanTree tree) {
            String str = getLine("輸入解碼內容:");
            tree.decode(str);
        }
    }
}
