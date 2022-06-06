package rng.b32;

public interface Rng32Bit {
    public void setSeed(int seed);
    public int nextInt();
}
