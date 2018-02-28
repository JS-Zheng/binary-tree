package com.jszheng.util;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

public class Combinatorics {

    private static final Map<Integer, BigInteger> F_RECORD = new HashMap<>();
    private static final Map<Integer, BigInteger[]> P_RECORD = new HashMap<>();
    private static final Map<Integer, BigInteger[]> C_RECORD = new HashMap<>();
    public static boolean debug = false;
    private static int lastNumInFacRecord = 1;

    public static BigInteger fac(int n) {
        if (n == 0 || n == 1)
            return BigInteger.ONE;

        BigInteger result;

        if ((result = F_RECORD.get(n)) != null) {
            if (debug)
                System.out.printf("Use Stored Value => fac(%d): %s\n", n, result);
            return result;
        }

        result = BigInteger.ONE;

        while (lastNumInFacRecord != n) {
            BigInteger l = BigInteger.valueOf(lastNumInFacRecord);

            result = result.multiply(l.add(BigInteger.ONE));

            lastNumInFacRecord++;

            F_RECORD.put(lastNumInFacRecord, result);
        }

        return result;

    }

    public static BigInteger P(int n, int r) {
        if (n < r)
            return BigInteger.ZERO;

        BigInteger pn[] = P_RECORD.containsKey(n) ? P_RECORD.get(n) : new BigInteger[n + 1];

        BigInteger result;

        if (pn[r] != null) {
            result = pn[r];

            if (debug)
                System.out.printf("Use Stored Value => P(%d, %d): %s\n", n, r, result);
        } else {
            result = BigInteger.ONE;

            // 階乘約分
            for (BigInteger i = BigInteger.valueOf(n); i.intValue() > n - r; i = i.subtract(BigInteger.ONE))
                result = result.multiply(i);
        }

        pn[r] = result;

        P_RECORD.put(n, pn);

        return result;
    }

    /*
     * C(n, r) = 1, if (r = 0 || r = n)
     * C(n, r) = C(n-1, r) + C(n-1, r-1), otherwise.
     */
    public static BigInteger C(int n, int r) {
        if (n > 0 && n < r)
            return BigInteger.ZERO;

        if (n < 0) {
            int positiveN = n * -1;
            boolean rIsEven = r % 2 == 0;
            int coef = rIsEven ? 1 : -1;

            return BigInteger.valueOf(coef).multiply(C(positiveN + r - 1, r));
        }

        BigInteger previousCn[] = C_RECORD.containsKey(n - 1) ? C_RECORD.get(n - 1) : new BigInteger[n];
        BigInteger cn[] = C_RECORD.containsKey(n) ? C_RECORD.get(n) : new BigInteger[n + 1];
        initRecord(n - 1, previousCn);
        initRecord(n, cn);

        BigInteger result;

        if (cn[r] != null) {
            result = cn[r];
            if (debug)
                System.out.printf("Use Stored Value => C(%d, %d): %s\n", n, r, result);
        } else if (previousCn[r] != null && previousCn[r - 1] != null) {
            result = previousCn[r].add(previousCn[r - 1]);
            if (debug)
                System.out.printf("Use C(%d, %d) + C(%d, %d): %s\n", n - 1, r, n - 1, r - 1, result);
        } else {

            // 利用 P(n, k)
            int k = Math.min(r, n - r);
            BigInteger Pnk = P(n, k);
            BigInteger denominator = fac(k);

            result = Pnk.divide(denominator);
        }

        setNthRecord(n, r, cn, result);

        return result;
    }


    public static BigInteger H(int n, int r) {
        return C(n + r - 1, r);
    }

    private static void initRecord(int n, BigInteger[] cnr) {
        cnr[0] = BigInteger.ONE;
        cnr[n] = BigInteger.ONE;
    }

    private static void setNthRecord(int n, int r, BigInteger[] cnr, BigInteger result) {
        if (cnr == null || result == null)
            throw new RuntimeException();

        cnr[r] = result;
        cnr[n - r] = result;
        C_RECORD.put(n, cnr);
    }
}
