package org.fancy.mq.core.compressor;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * 压缩工厂
 * @author l
 */
public class CompressorFactory {

    private static final ConcurrentMap<CompressorType, Compressor> COMPRESSOR_MAP = new ConcurrentHashMap<>();

    static {
        COMPRESSOR_MAP.put(CompressorType.NONE, new NoneCompressor());
    }

    public static Compressor getCompressor(byte type) {
        CompressorType compressorType = CompressorType.get(type);
        return COMPRESSOR_MAP.computeIfAbsent(compressorType, (key) -> new NoneCompressor());
    }

    private static class NoneCompressor implements Compressor {
        @Override
        public byte[] compressor(byte[] bytes) {
            return bytes;
        }

        @Override
        public byte[] decompressor(byte[] bytes) {
            return bytes;
        }
    }
}
