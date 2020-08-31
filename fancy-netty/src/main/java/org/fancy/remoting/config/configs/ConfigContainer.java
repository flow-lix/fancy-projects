/**
 * @Copyright (c) 2019, Denali System Co., Ltd. All Rights Reserved.
 * Website: www.denalisystem.com | Email: marketing@denalisystem.com
 */
package org.fancy.remoting.config.configs;

/**
 * 配置容器
 */
public interface ConfigContainer {

    boolean contains(ConfigType configType, ConfigItem configItem);

    <T> T get(ConfigType configType, ConfigItem configItem);

    <T> void set(ConfigType configType, ConfigItem configItem, T obj);
}
