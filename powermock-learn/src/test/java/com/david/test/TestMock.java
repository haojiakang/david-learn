package com.david.test;

import com.david.util.PropertyApplicationContext;
import com.david.util.PropertyConstants;
import com.david.util.WebChatUtil;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.Calendar;

/**
 * Created by haojk on 12/5/16.
 */
@RunWith(PowerMockRunner.class)  //1
@PrepareForTest({PropertyApplicationContext.class,WebChatUtil.class}) //2
public class TestMock {

    @Before
    public void init() {
        PowerMockito.mockStatic(PropertyApplicationContext.class);// 3
        PowerMockito.mockStatic(WebChatUtil.class);
    }

    @Test
    public void testWebchatEnable(){
        try {
            Calendar c = Calendar.getInstance();
            c.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DATE), 16, 35);
            PowerMockito.spy(WebChatUtil.class); // 创建spy,如果不创建的话，后面调用WebChatUtil就都是Mock类，这里创建了spy后，只有设置了mock的方法才会调用mock行为
            PowerMockito.doReturn(c).when(WebChatUtil.class, "getCurrentTime"); //Mock私有方法
        } catch (Exception e) {
            e.printStackTrace();
        }
        PowerMockito.when(PropertyApplicationContext.getProperty(PropertyConstants.WEBCHAT_HOURS)).thenReturn("0900,1800"); //4 - Mock静态方法，返回期望值
        PowerMockito.when(PropertyApplicationContext.getProperty(PropertyConstants.WEBCHAT_LOCALES)).thenReturn("en,sc,cn");

        Assert.assertTrue(WebChatUtil.webchatEnable("en"));

        PowerMockito.when(PropertyApplicationContext.getProperty(PropertyConstants.WEBCHAT_HOURS)).thenReturn("090,1800");
        PowerMockito.when(PropertyApplicationContext.getProperty(PropertyConstants.WEBCHAT_LOCALES)).thenReturn("en,sc,cn");
        Assert.assertFalse(WebChatUtil.webchatEnable("en"));

        PowerMockito.when(PropertyApplicationContext.getProperty(PropertyConstants.WEBCHAT_HOURS)).thenReturn("0900,1800");
        PowerMockito.when(PropertyApplicationContext.getProperty(PropertyConstants.WEBCHAT_LOCALES)).thenReturn("en,sc,cn");
        Assert.assertFalse(WebChatUtil.webchatEnable("th"));
    }
}
