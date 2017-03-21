package com.david.commandChain;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by haojk on 1/25/17.
 */
public enum  CommandEnum {

    ls("com.david.commandChain.LSCommand"),
    df("com.david.commandChain.DFCommand");

    private String value = "";

    //定义的构造函数,目的是Data(value)类型的相匹配
    CommandEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

    //返回所有的Enum对象
    public static List<String> getNames() {
        CommandEnum[] commandEnums = CommandEnum.values();
        List<String> names = new ArrayList<>();
        for (CommandEnum c : commandEnums) {
            names.add(c.name());
        }
        return names;
    }
}
