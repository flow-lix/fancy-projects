/**
 * @Copyright (c) 2019, Denali System Co., Ltd. All Rights Reserved.
 * Website: www.denalisystem.com | Email: marketing@denalisystem.com
 */
package org.fancy.remoting.config.switches;

import java.util.BitSet;

public class GlobalSwitch implements Switch {

    public static final byte SERVER_CONNECTION_MANAGE_SWITCH = 0x01;
    public static final byte CONN_RECONNECT_SWITCH = 0x02;
    public static final byte SERVER_SYNC_STOP = 0x03;


    private BitSet userSettings = new BitSet();

    @Override
    public boolean isOn(int index) {
        return userSettings.get(index);
    }

    @Override
    public void turnOn(int index) {
        userSettings.set(index, true);
    }

    @Override
    public void turnOff(int index) {
        userSettings.set(index, false);
    }
}
