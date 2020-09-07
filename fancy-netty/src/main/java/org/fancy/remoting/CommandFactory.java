/**
 * @Copyright (c) 2019, Denali System Co., Ltd. All Rights Reserved.
 * Website: www.denalisystem.com | Email: marketing@denalisystem.com
 */
package org.fancy.remoting;

import org.fancy.remoting.common.RequestBody;
import org.fancy.remoting.protocol.RpcRequestCommand;

public interface CommandFactory {

    RpcRequestCommand createRequestCommand(Object req);
}
