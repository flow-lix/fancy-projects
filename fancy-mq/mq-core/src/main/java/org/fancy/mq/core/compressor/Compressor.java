package org.fancy.mq.core.compressor;

/**
 * 压缩器
 */
public interface Compressor {

    /**
     * 压缩
     */
    byte[] compressor(byte[] bytes);

    /**
     * 解压
     */
    byte[] decompressor(byte[] bytes);
}
