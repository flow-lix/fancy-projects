/**
 * @Copyright (c) 2019, Denali System Co., Ltd. All Rights Reserved.
 * Website: www.denalisystem.com | Email: marketing@denalisystem.com
 */
package org.fancy.remoting;

import org.fancy.remoting.common.RequestBody;
import org.fancy.remoting.config.configs.ConfigContainer;
import org.fancy.remoting.config.configs.ConfigType;
import org.fancy.remoting.config.configs.DefaultConfigContainer;
import org.fancy.remoting.config.switches.GlobalSwitch;
import org.fancy.remoting.exception.RemotingException;

public abstract class AbstractNettyConfigRemoting extends AbstractLifeCycle implements ConfigurableRemoting {

    private ConfigType configType;
    private ConfigContainer configContainer;
    private GlobalSwitch globalSwitch;

    public AbstractNettyConfigRemoting() {
        configContainer = new DefaultConfigContainer();
        globalSwitch = new GlobalSwitch();
    }

    protected ConfigType getConfigType() {
        return configType;
    }

    protected void setConfigType(ConfigType configType) {
        this.configType = configType;
    }

    public GlobalSwitch getGlobalSwitch() {
        return globalSwitch;
    }

    public void setGlobalSwitch(GlobalSwitch globalSwitch) {
        this.globalSwitch = globalSwitch;
    }

    public abstract void oneway(String addr, RequestBody req) throws RemotingException;
}
