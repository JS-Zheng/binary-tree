package com.jszheng.util;

import java.util.Scanner;

public class ScannerUtil {

    public static String errorPrompt = "輸入錯誤，請重新輸入";
    private static int TYPE_INT = 0;
    private static int TYPE_STRING = 1;
    private static int TYPE_LINE = 2;

    public static String getLine(String prompt) {
        return getInput(prompt, TYPE_LINE).nextLine();
    }

    public static String getString(String prompt) {
        return getInput(prompt, TYPE_STRING).next();
    }

    public static int getInteger(String prompt) {
        return getInput(prompt, TYPE_INT).nextInt();
    }

    private static Scanner getInput(String prompt, int type) {
        Scanner sc = new Scanner(System.in);
        System.out.println(prompt);
        boolean condition = getPromptCondition(sc, type);

        while (condition) {
            System.out.println(errorPrompt);
            System.out.println();
            System.out.println(prompt);

            sc.nextLine();

            condition = getPromptCondition(sc, type);
        }

        System.out.println();
        return sc;
    }

    private static boolean getPromptCondition(Scanner sc, int type) {
        boolean condition;
        if (type == TYPE_INT)
            condition = !sc.hasNextInt();
        else if (type == TYPE_LINE)
            condition = !sc.hasNextLine();
        else
            condition = !sc.hasNext();

        return condition;
    }
}
