package rng;

import java.math.BigInteger;

public interface BigIntegerRNG {
    public BigInteger nextInt();
    public void setSeed(BigInteger seed);
}
