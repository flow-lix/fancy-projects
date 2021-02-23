package org.fancy.mq.core.serializer.hessian;

import com.caucho.hessian.io.HessianInput;
import com.caucho.hessian.io.HessianOutput;
import lombok.extern.slf4j.Slf4j;
import org.fancy.mq.core.serializer.Serializer;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Slf4j
public class HessianSerializer<T> implements Serializer<T> {

    @Override
    public byte[] serializer(T obj) {
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
            HessianOutput output = new HessianOutput(bos);
            output.writeObject(obj);
            return bos.toByteArray();
        } catch (IOException e) {
            log.error("Hessian encode error:{}", e.getMessage(), e);
        }
        return new byte[0];
    }

    @Override
    public T deserializer(byte[] bytes) {
        try (ByteArrayInputStream bis = new ByteArrayInputStream(bytes)) {
            HessianInput input = new HessianInput(bis);
            @SuppressWarnings({"unchecked", "cast"})
            T obj = (T) input.readObject();
            return obj;
        } catch (IOException e) {
            log.error("Hessian decode error:{}", e.getMessage(), e);
        }
        return null;
    }
}
