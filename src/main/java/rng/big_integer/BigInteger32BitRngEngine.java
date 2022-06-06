package rng.big_integer;

import rng.b32.*;
import rng.b64.*;

import java.math.*;
import java.nio.*;

public class BigInteger32BitRngEngine implements BigIntegerRNG {
    private Rng32Bit engine;

    public void start(int seed, Rng32Bit engine) {
        this.engine = engine;
        engine.setSeed(seed);
    }

    public BigInteger32BitRngEngine() {}

    public BigInteger nextValue(int size) {
        if(size % 4 != 0) size += 4 - (size % 4);
        byte[] memory = new byte[size/4];
        for (int i = 0; i < memory.length; i += 4) {
            byte[] cycle = null;
            int value = engine.nextInt();
            cycle = ByteBuffer.allocate(4).putInt(value).array();
            for (int j = 0; j < 4; j++) {
                if (i+j == size/4) {
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
        this.engine.setSeed((int) seed);
    }
}