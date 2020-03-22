package org.fancy.netty.config.switchs;

import java.util.BitSet;

public class ProtocolSwitch implements Switch {

    private BitSet bs = new BitSet();

    public static ProtocolSwitch create(byte value) {
        ProtocolSwitch protocolSwitch = new ProtocolSwitch();
        protocolSwitch.setBs(toBitSet(value));
        return protocolSwitch;
    }

    private static BitSet toBitSet(byte value) {
        BitSet bs = new BitSet();
        int index = 0;
        while (value % 2 != 0) {
            bs.set(index);
            value = (byte) (value >> 1);
            index++;
        }
        return bs;
    }

    public byte toBytes() {
        return toByte(bs);
    }

    public static byte toByte(BitSet bs) {
        int value = 0;
        if (bs.length() > 7) {
            throw new IllegalArgumentException("Out of range [" +
                    Byte.MIN_VALUE + "," + Byte.MAX_VALUE + ']');
        }
        for (int i = 0; i < bs.length(); i++) {
            if (bs.get(i)) {
                value += 1;
            }
        }
        return (byte) value;
    }

    @Override
    public void turnOn(int idx) {
        this.bs.set(idx);
    }

    @Override
    public void turnOff(int idx) {
        this.bs.clear(idx);
    }

    @Override
    public boolean isOn(int idx) {
        return bs.get(idx);
    }

    public BitSet getBs() {
        return bs;
    }

    public void setBs(BitSet bs) {
        this.bs = bs;
    }
}
