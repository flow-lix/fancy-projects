/**
 * @Copyright (c) 2019, Denali System Co., Ltd. All Rights Reserved.
 * Website: www.denalisystem.com | Email: marketing@denalisystem.com
 */
package org.fancy.remoting.util;

import org.junit.Test;

public class AddressParseUtilTest {

    @Test
    public void testParse() {
        String addr = "localhost:8080?key1=val1?key2=val2";
        System.out.println(AddressParserUtil.parse(addr));
    }
}
