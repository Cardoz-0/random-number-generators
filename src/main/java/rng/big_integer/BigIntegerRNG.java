package rng.big_integer;

import java.math.BigInteger;

public interface BigIntegerRNG {
    public BigInteger nextValue(int size);
    public void setSeed(long seed);
}
