/**
 * @Copyright (c) 2019, Denali System Co., Ltd. All Rights Reserved.
 * Website: www.denalisystem.com | Email: marketing@denalisystem.com
 */
package org.fancy.remoting.config.switches;

/**
 * 开关接口
 */
public interface Switch {

    boolean isOn(int index);

    void turnOn(int index);

    void turnOff(int index);
}
