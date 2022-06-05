package rng;

import java.math.BigInteger;

public class BlumBlumShub implements BigIntegerRNG {
    private static BigInteger prime = BigInteger.valueOf(11).multiply(BigInteger.valueOf(59));
    private BigInteger state;

    public BlumBlumShub(BigInteger seed) {
        this.state = seed;
    }

    public BlumBlumShub() {}

    public BigInteger nextInt() {
        this.state = state.modPow(BigInteger.valueOf(2), prime);
        return state;
    }

    public void setSeed(BigInteger seed) {
        this.state = seed;
    }
}
