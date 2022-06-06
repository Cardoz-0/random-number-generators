package rng.b32;

public class MersenneTwister implements Rng32Bit{
    /**
     * word size (in number of bits)
     */
    private static final int W = 32;
    /**
     * degree of recurrence
     */
    private static final int N = 624;
    /**
     * middle word, an offset used in the recurrence relation defining the series x, 1 ≤ m ≤ n
     */
    private static final int M = 397;
    /**
     * separation point of one word, or the number of bits of the lower bitmask, 0 ≤ r ≤ w − 1
     */
    private static final int R = 31;
    /**
     * coefficients of the rational normal form twist matrix
     */
    private static final int A = 0x9908B0DF;
    /**
     * additional Mersenne Twister tempering bit shifts/masks
     */
    private static final int U = 11;
    /**
     * additional Mersenne Twister tempering bit shifts/masks
     */
    private static final int D = 0xFFFFFFFF;
    /**
     * TGFSR(R) tempering bit shifts
     */
    private static final int S = 7;
    /**
     * TGFSR(R) tempering bitmasks
     */
    private static final int B = 0x9D2C5680;
    /**
     * TGFSR(R) tempering bit shifts
     */
    private static final int T = 15;
    /**
     * TGFSR(R) tempering bit shifts
     */
    private static final int C = 0xEFC60000;
    /**
     * additional Mersenne Twister tempering bit shifts/masks
     */
    private static final int L = 18;
    private static final long F = 1812433253l;
    /**
     * (1 << r) - 1
     */
    private static int LOWER_MASK = 0x7FFFFFFF;
    /**
     *  lowest w bits of (not lower_mask)
     */
    private static int UPPER_MASK = 0x80000000;

    /**
     * A length n array to store the state of the generator
     */
    private int[] mt;
    private int index;
    /**
     * MT19937
     * Generates 32-bit pseudo-random numbers
     * @param seed
     */
    public MersenneTwister(int seed) {
        setSeed(seed);
    }
    public MersenneTwister() {

    }

    public void setSeed(int seed) {
        mt = new int[N];
        mt[0] = seed;
        // Initializes the array
        for (int i = 1; i < (N-1) ;i++) {
            long mti_long = (F * mt[i-1] ^ (mt[i-1] >>> (W-2))) & 0xFFFFFFFFL;
            // '& 0xffffffffL' Does the bit masking and gives us the lowest 32-bits
            mt[i] = (int) mti_long;
        }
    }

    public int nextInt() {
        if (index >= N) {
            for (int i = 0; i < (N - 1); i++) {
                int x = (mt[i] & UPPER_MASK) + (mt[i] & LOWER_MASK);
                int xA = x >> 1;
                if ((x % 2) != 0) { // lowest bit of x is 1
                    xA = xA ^ A;
                }
                mt[i] = mt[(i + M) % N] ^ xA;
            }
            index = 0;
        }

        int y = mt[index];

        y = y ^ ((y >> U) & D);
        y = y ^ ((y << S) & B);
        y = y ^ ((y << T) & C);
        y = y ^ (y >> L);

        index += 1;
        return y;
    }
}
