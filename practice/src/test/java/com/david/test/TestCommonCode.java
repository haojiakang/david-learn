package com.david.test;

import com.david.common.util.Commands;
import com.david.common.util.Consoles;
import com.david.common.util.Prints;
import org.junit.Test;

/**
 * Created by jiakang on 2018/4/16
 *
 * @author jiakang
 */
public class TestCommonCode {

    @Test
    public void testPrints() {
        Prints.println("zhangsan, uid:{}, gender:{}", 15236L, "male");
    }

    @Test
    public void testCommandExecutor() {
        String cmd = "echo haha";
        String result = Commands.execute(cmd);
        Prints.println("cmd:{}, result:{}", "ls", result);
    }

    @Test
    public void testConsoles() {
        Consoles.info("person info, name:{}, age:{}", "Jackie", 25);
    }
}