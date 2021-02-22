package org.fancy.mq.core.serializer.hessian;

import com.caucho.hessian.io.HessianFactory;

public class HessianSerializerFactory extends HessianFactory {

    private static final HessianSerializerFactory INSTANCE = new HessianSerializerFactory();

    public static HessianSerializerFactory getInstance() {
        return INSTANCE;
    }


}
