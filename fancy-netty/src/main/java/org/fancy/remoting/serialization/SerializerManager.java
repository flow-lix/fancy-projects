package org.fancy.remoting.serialization;

/**
 * 序列化管理类
 */
public class SerializerManager {

    private static Serializer[] serializers = new Serializer[5];

    public static final byte HESSIAN2 = 1;

    static {
        addSerializer(HESSIAN2, new HassianSerializer());
    }

    public Serializer getSerializer(int idx) {
        return serializers[idx];
    }

    public static void addSerializer(int idx, Serializer serializer) {
        if (idx >= serializers.length) {
            throw new IllegalArgumentException("Too much serializer!");
        }
        serializers[idx] = serializer;
    }
}
