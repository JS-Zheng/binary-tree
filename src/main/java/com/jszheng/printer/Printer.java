package com.jszheng.printer;

interface Printer {

    static void printWhitespaces(int count) {
        printByCount(count, " ");
    }

    static void printByCount(int count, Object obj) {
        for (int i = 0; i < count; i++)
            System.out.print(obj);
    }
}
