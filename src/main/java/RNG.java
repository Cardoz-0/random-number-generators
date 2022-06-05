import prime.MillerRabin;
import rng.BigIntegerRNG;
import rng.BlumBlumShub;
import rng.MersenneTwister;
import rng.Xorshift;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class RNG {

    public static void main(String[] args) {
//        System.out.println(MillerRabin.checkPrimalty(BigInteger.valueOf(59), 5));
//        measureGenerationTime(new Xorshift());
//        measureGenerationTime(new BlumBlumShub());
        int mt19937_seed = (int)System.currentTimeMillis();
        System.out.println("Seed: " + mt19937_seed);

        MersenneTwister mt19937 = new MersenneTwister(mt19937_seed);
        List<Integer> sample = new ArrayList<Integer>();
        for (int i=0; i<10; i++) {
            sample.add(mt19937.nextInt());
        }
        System.out.println(crackMT19937Seed(sample,3600000, mt19937_seed + 3600000));
    }

    public static void measureGenerationTime(BigIntegerRNG rng) {
        List<Integer> sizes = Arrays.asList(40, 56, 80, 128, 168, 224, 256, 512, 1024, 2048, 4096);
        for (Integer size : sizes) {
            rng.setSeed(new BigInteger(size, new SecureRandom()));
            long start = System.currentTimeMillis();
            rng.nextInt();
            System.out.printf("Size of %d takes %d milliseconds%n", size, (System.currentTimeMillis() - start) * 1000);
        }
    }

    public static int crackMT19937Seed(List<Integer> sample, int time_to_rollback, int time_start) {
        for (int i = time_start; i >= time_start-time_to_rollback; i--) {
            MersenneTwister rng = new MersenneTwister(i);
            Iterator<Integer> it = sample.iterator();
            while(it.hasNext()) {
                if(it.next() != rng.nextInt()) {
                    break;
                }
                return i;
            }
        }
        return 0;
    }
}
