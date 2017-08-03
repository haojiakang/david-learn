package com.david.learn.test;

import org.junit.Test;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

/**
 * Created by jiakang on 2017/6/12.
 */
public class Script {

    @Test
    public void testNashorn() throws ScriptException {
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("nashorn");
        Object result = engine.eval("'Hello, World'.length()");
        System.out.println(result);
    }
}
