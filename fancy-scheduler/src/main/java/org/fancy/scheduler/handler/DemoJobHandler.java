/**
 * @Copyright (c) 2019, Denali System Co., Ltd. All Rights Reserved.
 * Website: www.denalisystem.com | Email: marketing@denalisystem.com
 */
package org.fancy.scheduler.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * 任务Handler示例（Bean模式）
 *
 * 开发步骤：
 * 1、继承"IJobHandler"：“com.xxl.job.core.handler.IJobHandler”；
 * 2、注册到执行器工厂：在 "JFinalCoreConfig.initXxlJobExecutor" 中手动注册，注解key值对应的是调度中心新建任务的JobHandler属性的值。
 * 3、执行日志：需要通过 "XxlJobLogger.log" 打印执行日志；
 *
 * @author xuxueli 2015-12-19 19:43:36
 */
public class DemoJobHandler extends IJobHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(DemoJobHandler.class);

    @Override
    public void execute(String param) throws Exception {
        LOGGER.info("XXL-JOB, Hello World.");

        for (int i = 0; i < 5; i++) {
            LOGGER.info("beat at: {}", i);
            TimeUnit.SECONDS.sleep(2);
        }
    }

}
