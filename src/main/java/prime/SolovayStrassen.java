package prime;

import rng.b32.MersenneTwister;
import rng.b64.Xorshift;
import rng.big_integer.BigInteger32BitRngEngine;
import rng.big_integer.BigInteger64BitRngEngine;

import java.math.BigInteger;

public class SolovayStrassen {
    public static boolean checkPrimalty(BigInteger p, int size, int k) {
        if (p.compareTo(BigInteger.ONE) > 0 && p.testBit(0)) {
            BigInteger32BitRngEngine engine = new BigInteger32BitRngEngine();
            engine.start(12, new MersenneTwister());
            for (int i = 0; i<k; i++) {
                BigInteger rand = engine.nextValue(size);
                BigInteger x = calculateJacobian(rand, p);
                BigInteger ant = p.subtract(BigInteger.ONE);

                BigInteger x_ = rand.modPow(ant.divide(BigInteger.valueOf(2)), p);
                if (x.compareTo(BigInteger.ZERO) != 0 && x.compareTo(x_) == 0) {
                    continue;
                } else {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    static BigInteger calculateJacobian(BigInteger a, BigInteger p) {
        BigInteger originalP = p;
        BigInteger x = BigInteger.ONE;
        BigInteger aux;
        while (a.compareTo(BigInteger.ZERO) != 0) {
            while (a.mod(BigInteger.valueOf(2)).equals(BigInteger.ZERO)) {
                a = a.divide(BigInteger.valueOf(2));
                aux = p.mod(BigInteger.valueOf(8));
                if (aux.compareTo(BigInteger.valueOf(3)) == 0 || aux.compareTo(BigInteger.valueOf(5)) == 0)
                    x = x.multiply(BigInteger.valueOf(-1));
            }
            aux = a;
            a = p;
            p = aux;

            if (a.mod(BigInteger.valueOf(4)).compareTo(BigInteger.valueOf(3)) == 0 &&
                    p.mod(BigInteger.valueOf(4)).compareTo(BigInteger.valueOf(3)) == 0)
                x = x.multiply(BigInteger.valueOf(-1));
            a = a.mod(p);
        }

        if (p.compareTo(BigInteger.ONE) == 0) {
            if (x.compareTo(BigInteger.valueOf(-1)) == 0) {
                return originalP.subtract(BigInteger.ONE);
            } else {
                return x;
            }
        } else {
            return BigInteger.ZERO;
        }
    }
}
