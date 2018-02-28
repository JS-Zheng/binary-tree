package com.jszheng.printer;

import com.jszheng.base.BinaryTree;
import com.jszheng.base.SkewedState;
import com.jszheng.node.TreeNode;
import com.jszheng.traversal.TraversalNodeHandler;
import com.jszheng.traversal.level.ILevelOrderTraversal;
import com.jszheng.traversal.level.LevelOrderTraversal;

import java.util.ArrayList;
import java.util.List;

import static com.jszheng.base.BinaryTreeLemma.getMaxCountByLevel;
import static com.jszheng.base.BinaryTreeLemma.maxCount;

@SuppressWarnings({"rawtypes", "unchecked"})
public class MyBtPrinter implements BtPrinter {

    public boolean debug = false;

    private int maxWidth;
    // Coefficient
    private int leavesSiblingPadding;
    private int leavesSiblingGapLength;
    private int leavesMiddlePadding;
    // Root BinaryTree Info.
    private int maxLevel;
    private int maxLeavesCount;

    private int maxDataLength = 1;

    private BinaryTree bt;

    public MyBtPrinter() {
    }

    public MyBtPrinter(int leavesSiblingPadding, int leavesSiblingGapLength, int leavesMiddlePadding) {
        this.leavesSiblingPadding = leavesSiblingPadding;
        this.leavesSiblingGapLength = leavesSiblingGapLength;
        this.leavesMiddlePadding = leavesMiddlePadding;
    }

    @Override
    public String getPrintString(BinaryTree bt) {
        if (bt == null || bt.isEmpty())
            return "Tree is empty.";

        StringBuilder result = new StringBuilder();

        this.bt = bt;

        setMaxLevel(bt.height());

        setMaxDataLength(bt);

        validateRenderCoefficient();

        if (maxLevel == 1)
            handleSinglyTree(bt);
        else if (bt.skewedState() != SkewedState.NORMAL)
            handleSkewedTree(bt);
        else
            handleTree(bt, result);

        result.append("\n"); // just a delimiter

        return result.toString();
    }

    private int getSiblingGapLength(int level) {
        if (level >= maxLevel)
            return leavesSiblingGapLength;

        return getSiblingPadding(level);
    }

    private int getSiblingPadding(int level) {
        int marginLeft = getMarginLeft(level);
        return getSiblingPadding(level, marginLeft);
    }

    private int getSiblingPadding(int level, int marginLeft) {
        if (level >= maxLevel)
            return leavesSiblingPadding;

        int reduceLevel = level - 2;
        int subTreeMaxWidth = getSubTreeMaxWidth(reduceLevel);
        return subTreeMaxWidth - (maxDataLength << 1) - (marginLeft << 1);
    }

    private int getSubTreeMaxWidth(int reduceLevel) {
        if (reduceLevel == 0)
            return maxWidth;

        int tmpLeavesCount = maxLeavesCount;

        for (int i = 0; i < reduceLevel; i++)
            tmpLeavesCount = tmpLeavesCount / 2;

        // setup middlePadding to origin leavesSiblingGapLength
        return getWidth(tmpLeavesCount, leavesSiblingPadding,
                leavesSiblingGapLength, leavesSiblingGapLength);
    }

    private int getWidth(int maxLeavesCount, int leavesSiblingPadding, int leavesSiblingGapLength, int leavesMiddlePadding) {
        if (maxLevel == 2) {
            return maxDataLength * maxLeavesCount + leavesMiddlePadding;
        }

        int halfLeavesCount = maxLeavesCount / 2;
        int leavesSiblingGapCount = Math.max(halfLeavesCount - 2, 0);

        if (maxLeavesCount == 2) {
            leavesMiddlePadding = 0; // Prevent Level 2 Tree
        }
        return maxDataLength * maxLeavesCount + halfLeavesCount * leavesSiblingPadding
                + leavesSiblingGapCount * leavesSiblingGapLength
                + leavesMiddlePadding;
    }

    private void handleSinglyTree(BinaryTree bt) {
        Printer.printWhitespaces((leavesMiddlePadding - 1) / 2 + 1);
        System.out.print(bt.getNodeString(bt.getRoot()));
    }

    private void handleSkewedTree(BinaryTree bt) {
        SkewedState skewedState = bt.skewedState();
        boolean isLeftSkewed = skewedState == SkewedState.LEFT_SKEWED;
        BtPrinter skewedPrinter;

        if (!isLeftSkewed)
            skewedPrinter = new RightSkewedBtPrinter();
        else
            skewedPrinter = new LeftSkewedBtPrinter();

        skewedPrinter.printBt(bt);
    }

    private void handleTree(BinaryTree bt, StringBuilder result) {
        calculateMaxWidth();

        ILevelOrderTraversal algo = new PrinterLevelOrderTraversal(result);
        algo.traverse(bt);
    }

    private void setDefaultRenderCoefficient() {
        if (maxLevel > 7) {
            setRenderCoefficient(1, 1, 1);
            return;
        }

        switch (maxLevel) {
            case 3:
                setRenderCoefficient(3, 3, 3);
                break;
            case 4:
                setRenderCoefficient(3, 3, 5);
                break;
            case 6:
                setRenderCoefficient(1, 1, 3);
                break;
            case 7:
                setRenderCoefficient(1, 1, 3);
                break;
            default:
                setRenderCoefficient(1, 3, 5);
        }
    }

    private void setMaxDataLength(BinaryTree bt) {
        bt.traverse("Level", node -> {
            int length;
            String s = bt.getNodeString(node);
            if ((length = s.length()) > maxDataLength)
                maxDataLength = length;
            return true;
        });
    }

    private void validateRenderCoefficient() {
        if (maxDataLength % 2 == 0)
            maxDataLength++; // keep odd

        if (leavesSiblingPadding == 0 || leavesSiblingGapLength == 0 ||
                leavesMiddlePadding == 0) {
            if (debug) {
                System.out.println("No specify Render Coefficient.");
                System.out.println("Switch to default value.");
            }
            setDefaultRenderCoefficient();
        } else if (leavesSiblingPadding % 2 != 1 || leavesSiblingGapLength % 2 != 1 ||
                leavesMiddlePadding % 2 != 1) {
            if (debug) {
                System.out.println("Render Coefficient must be odd Number.");
                System.out.println("Switch to default value.");
            }
            setDefaultRenderCoefficient();
        }
    }

    private class PrinterLevelOrderTraversal extends LevelOrderTraversal {

        List<StringBuilder> builderList = new ArrayList<>();
        private StringBuilder result;

        private int lastLevel;
        private int currentMarginLeft, nextMarginLeft;
        private int nodeCountForLevel;

        PrinterLevelOrderTraversal(StringBuilder result) {
            this.result = result;
            setFullBtMode(true);
        }

        @Override
        public boolean init(BinaryTree bt) {
            lastLevel = 0;
            currentMarginLeft = 0;
            nextMarginLeft = getMarginLeft(1); // Prepare getHeightByIndex-1 marginLeft
            nodeCountForLevel = 0;
            builderList.clear();
            return super.init(bt);
        }

        @Override
        public TraversalNodeHandler getNodeHandler() {
            return node -> {
                int level = getLevel(node);
                int index = getIndex(node);

                // line feed for newLevel && Prepare Margin Left.
                checkNewLevel(level);

                // Record the number of occurrences of each node for this getHeightByIndex.
                nodeCountForLevel++;

                printData(node);

                // Prepare edge in buffer and not printed yet.
                prepareEdges(node);

                boolean isMiddleNode = isMiddleNode(index);
                boolean isEndNode = index == maxCount(level) - 1;
                printWhitespacesOfNodeAndEdges(level, isMiddleNode, isEndNode);

                lastLevel = level;

                return true;
            };
        }

        private void checkNewLevel(int level) {
            if (level != lastLevel) {
                nodeCountForLevel = 0;

                currentMarginLeft = nextMarginLeft;
                nextMarginLeft = getMarginLeft(level + 1);

                // Move to next Level.
                if (level != 1)
                    result.append("\n");

                printLastLevelEdge();

                // clear bufferList and cal size of the current getHeightByIndex.
                createEdgeBuffer();

                // print newLevel marginLeft.
                printWhitespaces(currentMarginLeft);

                addLeftMarginOfEdgesToBuffer(currentMarginLeft);
            }
        }

        private void createEdgeBuffer() {
            builderList.clear();
            builderList.add(new StringBuilder()); // for leaves
            for (int i = 0; i < currentMarginLeft - nextMarginLeft - 2; i++)
                builderList.add(new StringBuilder());
        }

        private boolean isMiddleNode(int index) {
            // Check if it is a middle node.
            int tmp = 1;
            while (tmp < index)
                tmp = (tmp << 1) + 2;

            return tmp == index;
        }

        private String nullNodeStr() {
            // Keep one space to mock null.
            return " ";
        }

        private void prepareEdges(TreeNode node) {
            if (node.hasLeftChild()) {
                addEdgeSlashToBuffer(true);
                addEdgeSpacesToBuffer(1, false); // prepare for backSlash
            } else {
                addEdgeSpacesToBuffer(2, false);
            }

            if (node.hasRightChild())
                addEdgeSlashToBuffer(false);
            else
                addSpacesToBuffer(); // mock backSlash
        }

        private void printData(TreeNode node) {
            String dataStr;
            if (node.getData() == null && isPseudoNode(node)) {
                dataStr = nullNodeStr();
            } else {
                dataStr = bt.getNodeString(node);
            }

            if (dataStr.length() < 1) return;

            int strLength = dataStr.length();
            if (strLength == maxDataLength) {
                result.append(dataStr);
                return;
            }

            int widthDelta = maxDataLength - strLength;
            int paddingLeft = strLength + widthDelta / 2;

            dataStr = String.format("%" + paddingLeft + "s", dataStr);
            dataStr = String.format("%-" + maxDataLength + "s", dataStr);
            result.append(dataStr);
        }

        private void printLastLevelEdge() {
            if (builderList.size() > 0) {
                for (StringBuilder builder : builderList)
                    result.append(builder).append("\n");
            }
        }

        private void printWhitespaces(int count) {
            for (int i = 0; i < count; i++)
                result.append(" ");
        }

        private void printWhitespacesOfNodeAndEdges(int level, boolean isMiddleNode, boolean isEndNode) {
            if (isMiddleNode) {
                int middlePadding = getMiddlePadding(level);
                // same as middlePadding - 2 + (maxDataLength-1)
                addEdgeSpacesToBuffer(middlePadding - 3 + maxDataLength, true);
                printWhitespaces(middlePadding);
            } else if (isEndNode) {
                addLeftMarginOfEdgesToBuffer(currentMarginLeft);
                printWhitespaces(currentMarginLeft);
                result.append(bt.getLevelString(level));
            } else {
                if (nodeCountForLevel % 2 == 1) {
                    int siblingPadding = getSiblingPadding(level);
                    addEdgeSpacesToBuffer(siblingPadding - 3 + maxDataLength, true);
                    printWhitespaces(siblingPadding);
                } else {
                    int siblingGapLength = getSiblingGapLength(level);
                    addEdgeSpacesToBuffer(siblingGapLength - 3 + maxDataLength, true);
                    printWhitespaces(siblingGapLength);
                }
            }
        }

        void addEdgeSlashToBuffer(boolean isForward) {
            for (StringBuilder aBuilderList : builderList)
                if (isForward)
                    aBuilderList.append("/");
                else
                    aBuilderList.append("\\");
        }

        void addEdgeSpacesToBuffer(int length, boolean nextIsForward) {
            for (int i = 0; i < builderList.size(); i++) {
                if (nextIsForward)
                    for (int j = 0; j < length - (i << 1); j++)
                        builderList.get(i).append(" ");

                else
                    for (int j = 0; j < length + (i << 1); j++)
                        builderList.get(i).append(" ");
            }
        }

        void addLeftMarginOfEdgesToBuffer(int currentMarginLeft) {
            for (int i = 0; i < builderList.size(); i++) {
                StringBuilder builder = builderList.get(i);

                for (int j = 0; j < (currentMarginLeft - i - 1) + Math.floorDiv(maxDataLength, 2); j++)
                    builder.append(" ");
            }
        }

        void addSpacesToBuffer() {
            for (StringBuilder aBuilderList : builderList)
                aBuilderList.append(" ");
        }
    }

    void calculateMaxWidth() {
        this.maxLeavesCount = getMaxCountByLevel(maxLevel);

        this.maxWidth =
                getWidth(maxLeavesCount, leavesSiblingPadding, leavesSiblingGapLength, leavesMiddlePadding);
    }

    int getMarginLeft(int level) {
        if (level >= maxLevel)
            return 0;

        if (level == 1)
            return (maxWidth - maxDataLength) / 2;

        // setup middlePadding to origin leavesSiblingGapLength
        int subTreeWidth = getSubTreeMaxWidth(level - 1);

        return (subTreeWidth - maxDataLength) / 2;
    }

    int getMiddlePadding(int level) {
        if (level >= maxLevel)
            return leavesMiddlePadding;

        if (level == 1)
            return 0;

        int marginLeft = getMarginLeft(level);
        int maxCountForLevel = getMaxCountByLevel(level);
        int siblingGapCount = Math.max(maxCountForLevel - 2, 0);

        if (level == 2) {
            return maxWidth - (maxDataLength << 1) - (marginLeft << 1);
        }

        int siblingPaddingForLevel = getSiblingPadding(level, marginLeft);

        return maxWidth - maxCountForLevel * maxDataLength - (marginLeft << 1)
                - siblingGapCount * siblingPaddingForLevel;
    }

    void setMaxLevel(int maxLevel) {
        this.maxLevel = maxLevel;
    }

    void setRenderCoefficient(int leavesSiblingPadding, int leavesSiblingGapLength, int leavesMiddlePadding) {
        this.leavesSiblingPadding = leavesSiblingPadding;
        this.leavesSiblingGapLength = leavesSiblingGapLength;
        this.leavesMiddlePadding = leavesMiddlePadding;
    }
}
