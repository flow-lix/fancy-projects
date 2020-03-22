package org.fancy.netty.config.switchs;

/**
 * 运行时开关接口
 */
public interface Switch {

    void turnOn(int idx);

    void turnOff(int idx);

    boolean isOn(int idx);
}
