package rng.b64;

public interface Rng64Bit {
    public void setSeed(long seed);
    public long nextLong();
}
