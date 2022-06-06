package rng.b64;

public class Xorshift implements Rng64Bit {
    public Xorshift() {}
    private long state;

    /**
     * RNG with LFSR (Linear Feedback Shift Register)
     * @param seed long value different to zero
     * @return The randomized number
     */
    public Xorshift(long seed) {
        setSeed(seed);
    }

    public void setSeed(long seed) {
        this.state = seed;
    }

    public long nextLong() {
        long result = nextLong(state);
        state = result;
        return result;
    }
    public static long nextLong(long seed) {
        seed ^= seed << 13;
        seed ^= seed >>> 17;
        seed ^= seed << 5;

        return seed;
    }
}
