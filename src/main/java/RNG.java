import prime.*;
import rng.b64.*;
import rng.big_integer.*;
import rng.b32.*;
import java.math.*;
import java.security.*;
import java.util.*;


public class RNG {

    public static void main(String[] args) {
        List<Integer> sizes = Arrays.asList( 128, 168, 224, 256, 512, 1024, 2048, 4096);

        BigInteger64BitRngEngine engine_64 = new BigInteger64BitRngEngine();
        BigInteger32BitRngEngine engine_32 = new BigInteger32BitRngEngine();

        engine_64.start(12, new Xorshift());
        engine_32.start(12, new MersenneTwister());

        measureGenerationTime(engine_32, sizes);
        measureGenerationTime(engine_32, sizes);

        measurePrimeGenerationTime(engine_32, sizes);
        measurePrimeGenerationTime(engine_64, sizes);

        crackMT19937Seed();
    }

    public static void measureGenerationTime(BigIntegerRNG rng, List<Integer> sizes) {
        for (Integer size : sizes) {
            long start = System.currentTimeMillis();
            for (int i = 0; i < 1000000; i++) rng.nextValue(size);
            System.out.printf("PRIME Size of %d takes %d milliseconds%n", size, (System.currentTimeMillis() - start));
        }
    }
    public static void measurePrimeGenerationTime(BigIntegerRNG rng, List<Integer> sizes) {
        for (Integer size : sizes) {
            long start = System.currentTimeMillis();
            BigInteger number = rng.nextValue(size);
            while(!MillerRabin.checkPrimalty(number, 200, size)) {
                number = rng.nextValue(size);
            }
            System.out.printf("PRIME Size of %d takes %d milliseconds%n", size, (System.currentTimeMillis() - start));
        }
    }
    public static void crackMT19937Seed() {
        int mt19937_seed = (int)System.currentTimeMillis();
        int time_to_rollback = 3600000;
        int time_start = mt19937_seed + 3600000;
        System.out.println("Seed: " + mt19937_seed);

        MersenneTwister mt19937 = new MersenneTwister(mt19937_seed);
        List<Integer> sample = new ArrayList<Integer>();
        for (int i=0; i<10; i++) {
            sample.add(mt19937.nextInt());
        }

        for (int i = time_start; i >= time_start-time_to_rollback; i--) {
            MersenneTwister rng = new MersenneTwister(i);
            Iterator<Integer> it = sample.iterator();
            while(it.hasNext()) {
                if(it.next() != rng.nextInt()) {
                    break;
                }
                System.out.println(mt19937_seed);
                break;
            }
        }
    }
}
