package rng;

import java.math.BigInteger;

public class Xorshift implements BigIntegerRNG {
    /**
     * RNG with LFSR (Linear Feedback Shift Register)
     * @param seed long value different to zero
     * @return The randomized number
     */
    private BigInteger state;
    public Xorshift(BigInteger seed) {
        this.state = seed;
    }

    public Xorshift() {}

    public static BigInteger getRandom(BigInteger seed) {
        seed = seed.xor((seed.shiftLeft(13)));
        seed = seed.xor(seed.shiftRight(17));
        seed = seed.xor((seed.shiftLeft(5)));

        return seed;
    }

    public BigInteger nextInt() {
        BigInteger value = getRandom(state);
        this.state = value;
        return value;
    }

    public void setSeed(BigInteger seed) {
        this.state = seed;
    }
}
