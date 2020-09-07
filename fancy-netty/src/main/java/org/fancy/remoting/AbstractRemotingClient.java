/**
 * @Copyright (c) 2019, Denali System Co., Ltd. All Rights Reserved.
 * Website: www.denalisystem.com | Email: marketing@denalisystem.com
 */
package org.fancy.remoting;

import org.fancy.remoting.exception.RemotingException;

public abstract class AbstractRemotingClient extends AbstractNettyConfigRemoting {

    public abstract void oneway(final String addr, final Object req) throws RemotingException;

}
