/**
 * @Copyright (c) 2019, Denali System Co., Ltd. All Rights Reserved.
 * Website: www.denalisystem.com | Email: marketing@denalisystem.com
 */
package org.fancy.remoting;

public interface BizContext {

    boolean isRequestTimeout();

    String getRemoteAddr();

    Connection getConnection();

    InvokeContext getInvokeContext();
}
