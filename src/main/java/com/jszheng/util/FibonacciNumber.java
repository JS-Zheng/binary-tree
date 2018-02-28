package com.jszheng.util;

import java.math.BigInteger;

/**
 * Created by zhengzhongsheng on 2017/11/3.
 */
public class FibonacciNumber {

    /*
     * Fast doubling method.
	 * F(2n) = F(n) * [2 * F(n + 1) - F(n)].
	 * F(2n + 1) = F(n + 1)^2 + F(n)^2.
	 */
    public static BigInteger getNumber(int n) {
        BigInteger a = BigInteger.ZERO; // F(k)
        BigInteger b = BigInteger.ONE; // F(k + 1)

        for (int bit = Integer.highestOneBit(n); bit != 0; bit >>>= 1) {

            // Double it
            BigInteger c = a.multiply(b.shiftLeft(1).subtract(a)); // F(2k)
            BigInteger d = a.multiply(a).add(b.multiply(b)); // F(2k + 1)

            if ((n & bit) != 0) {
                BigInteger e = c.add(d); // F(2k + 2)
                a = d; // k = 2k + 1
                b = e;
            } else {
                a = c; // k = 2k
                b = d;
            }
        }

        // current k == n
        return a;
    }
}
