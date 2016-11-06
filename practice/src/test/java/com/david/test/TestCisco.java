package com.david.test;

import com.david.cisco.JsonUtil;
import com.david.cisco.RouterUtil;
import org.junit.Test;

import java.util.Map;

/**
 * Created by haojk on 11/4/16.
 */
public class TestCisco {

    @Test
    public void testParseJson() {
        String json = "{'totalCount':'1','ID':'1029','IP':'10.0.0.1'}";
        Map<String, Object> result = JsonUtil.parseJson(json);
        System.out.println(result);
    }

    @Test
    public void testCalculateRouter() {
        int num = 11;
        String result = RouterUtil.calculateRouter2(num);
        System.out.println(result);
    }
}
