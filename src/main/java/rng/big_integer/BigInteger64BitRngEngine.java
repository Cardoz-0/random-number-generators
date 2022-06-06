package rng.big_integer;

import rng.b64.Rng64Bit;

import java.math.BigInteger;
import java.nio.ByteBuffer;

public class BigInteger64BitRngEngine implements BigIntegerRNG {

    private Rng64Bit engine;
    public void start(long seed, Rng64Bit engine) {
        this.engine = engine;
        engine.setSeed(seed);
    }

    public BigInteger64BitRngEngine() {}

    public BigInteger nextValue(int size) {
        if(size % 8 != 0) size += 8 - (size % 8);
        byte[] memory = new byte[size/8];
        for (int i = 0; i < memory.length; i += 8) {
            byte[] cycle = null;
            long value = engine.nextLong();
            cycle = ByteBuffer.allocate(8).putLong(value).array();
            for (int j = 0; j < 8; j++) {
                if (i+j == size/8) {
                    break;
                }
                memory[i+j] = cycle[j];
            }
        }
        return new BigInteger(readAsUnsignedNumber(memory));
    }
    public static byte[] readAsUnsignedNumber(byte[] bytes) {
        int length = bytes.length + 1;
        byte[] unsignedBytes = new byte[length];
        unsignedBytes[0] = 0;
        System.arraycopy(bytes, 0, unsignedBytes, 1, bytes.length);
        return unsignedBytes;
    }

    public void setSeed(long seed) {
        this.engine.setSeed(seed);
    }
}
