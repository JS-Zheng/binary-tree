package com.jszheng.manipulator;

import com.jszheng.Env;
import com.jszheng.util.ScannerUtil;

import java.util.ArrayList;
import java.util.List;

public abstract class Manipulator<Operation> {

    List<String> operationNames = new ArrayList<>();
    List<Operation> operations = new ArrayList<>();

    Manipulator() {
        addDefaultOperation();
    }

    public void executeWithPrompt() {
        while (true) {
            try {
                System.out.println();
                int typeId = ScannerUtil.getInteger(getPrompt());
                boolean validateId = validateInput(typeId);
                if (!validateId) continue;
                boolean result = handleOperation(typeId);
                if (!result) break;
                afterExecute(typeId);
            } catch (Exception ex) {
                if (Env.dev) ex.printStackTrace();
            }
        }
    }

    public boolean handleOperation(int operationId) {
        return operationId != 0;
    }

    private String getBackPrompt() {
        return "      [0]返回";
    }

    protected abstract String getChoosePrompt();

    private String getOperationsName() {
        StringBuilder result = new StringBuilder();
        int opSize = operationNames.size();
        for (int i = 0; i < opSize; i++) {
            result.append("[").append(i + 1).append("]")
                    .append(operationNames.get(i)).append(" ");

            if (opSize > 8 && i >= 7 && i % 7 == 0 && i != opSize - 1)
                result.append("\n");
        }
        return result.toString();
    }

    String getPrompt() {
        StringBuilder result = new StringBuilder();
        String choosePrompt = getChoosePrompt();
        String operationsName = getOperationsName();
        String backPrompt = getBackPrompt();

        result.append(choosePrompt)
                .append(operationsName)
                .append(backPrompt);

        return result.toString();
    }

    protected void addDefaultOperation() {
        // Empty
    }

    protected void addOperation(String name, Operation operation) {
        this.operationNames.add(name);
        this.operations.add(operation);
    }

    protected void afterExecute(int typeId) {
        // Empty
    }

    boolean validateInput(int operationId) {
        if (operationId > operations.size() || operationId < 0) {
            String errorPrompt = "輸入錯誤，請重新輸入\n";
            System.out.println(errorPrompt);
            return false;
        }

        return true;
    }
}
