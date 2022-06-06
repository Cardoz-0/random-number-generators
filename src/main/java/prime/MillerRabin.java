package prime;

import rng.b32.MersenneTwister;
import rng.b64.Xorshift;
import rng.big_integer.BigInteger64BitRngEngine;

import java.math.BigInteger;

public class MillerRabin {
    public static boolean checkPrimalty(int number, int rounds) {
        MersenneTwister rng = new MersenneTwister((int) System.currentTimeMillis());

        if (number <= 1 || number == 4)
            return false;
        if (number <= 3)
            return true;

        int d = number - 1;
        while (d % 2 == 0)
            d /= 2;

        for (int i = 0; i < rounds; i++) {
            int rand = (rng.nextInt() % (number - 2) + 2);
            int x = (int) Math.pow(rand, d) % number;
            if ((x == 1) || (x == number-1)) {
                continue;
            } else {
                for (int j = 0; j < rand - 1; j++) {
                    x = (int) Math.pow(x, 2) % number;
                    if (x == number-1) {
                        continue;
                    } else {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public static boolean checkPrimalty(BigInteger number, int rounds, int size) {
        BigInteger the_number_2 = BigInteger.valueOf(2); // I will not forgive java for making me do this
        BigInteger the_number_3 = BigInteger.valueOf(3);
        BigInteger the_number_4 = BigInteger.valueOf(4); // Like seriously?

        BigInteger64BitRngEngine engine = new BigInteger64BitRngEngine();
        engine.start(12, new Xorshift());

        if (number.compareTo(the_number_3) == 0) {
            return true;
        }

        if ((number.compareTo(BigInteger.ONE) == 0) || (number.compareTo(the_number_4) == 0)) {
            return false;
        }

        BigInteger d = number.subtract(BigInteger.ONE);

        while (d.mod(the_number_2).compareTo(BigInteger.ZERO) == 0)
            d = d.divide(the_number_2);

        for (int i = 0; i < rounds; i++) {
            BigInteger rand = engine.nextValue(size).mod(number.subtract(the_number_2)).add(the_number_2); // Literally just d % 2 == 0... WTF JAVA??
            BigInteger x = rand.modPow(d, number);
            if ((x.compareTo(BigInteger.ONE) == 0) || (x.compareTo(number.subtract(BigInteger.ONE)) == 0)) {
                continue;
            } else {
                for (BigInteger bi = BigInteger.valueOf(0); // This was the most painfull for loop of my life
                           bi.compareTo(rand.subtract(BigInteger.ONE)) < 0;
                           bi = bi.add(BigInteger.ONE)) {
                    x = x.modPow(the_number_2, number);
                    if (x.compareTo(number.subtract(BigInteger.ONE)) == 0) {
                        continue;
                    } else {
                        return false;
                    }
                }
            }
        }
        return true;
    }
}
