/**
 * @Copyright (c) 2019, Denali System Co., Ltd. All Rights Reserved.
 * Website: www.denalisystem.com | Email: marketing@denalisystem.com
 */
package org.fancy.remoting.config.configs;

import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class DefaultConfigContainer implements ConfigContainer {

    private Map<ConfigType, Map<ConfigItem, Object>> userConfigs = new HashMap<>();

    @Override
    public boolean contains(ConfigType configType, ConfigItem configItem) {
        validate(configType, configItem);
        return userConfigs.get(configType) != null
                && userConfigs.get(configType).containsKey(configItem);
    }

    private void validate(ConfigType configType, ConfigItem configItem) {
        if (configType == null || configItem == null) {
            throw new IllegalStateException("configType and configItem can't null!");
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T get(ConfigType configType, ConfigItem configItem) {
        validate(configType, configItem);
        return userConfigs.containsKey(configType) ? (T) userConfigs.get(configType).get(configItem) : null;
    }

    @Override
    public <T> void set(ConfigType configType, ConfigItem configItem, T value) {
        Map<ConfigItem, Object> map = userConfigs.computeIfAbsent(configType, a -> new HashMap<>());
        Object prev = map.put(configItem, value);
        if (null != prev) {
            log.warn("the value of ConfigType {}, ConfigItem {} changed from {} to {}",
                    configType, configItem, prev.toString(), value.toString());
        }
    }
}
