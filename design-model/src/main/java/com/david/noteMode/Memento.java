package com.david.noteMode;

import java.util.Map;

/**
 * Created by haojk on 1/14/17.
 */
public class Memento {

    //接收Map作为状态
    private Map<String, Object> stateMap;

    //构造函数传递参数
    public Memento(Map<String, Object> stateMap) {
        this.stateMap = stateMap;
    }

    public Map<String, Object> getStateMap() {
        return stateMap;
    }

    public void setStateMap(Map<String, Object> stateMap) {
        this.stateMap = stateMap;
    }
}
