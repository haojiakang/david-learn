package com.david.learn.globalLimiter.bean;

import lombok.Data;

import java.util.List;

/**
 * Created by jiakang on 2017/11/30.
 * 获取全部池子的结果对象
 * @author jiakang
 */
@Data
public class AllPoolName {

    private int code;
    private String msg;
    private PoolData data;

    @Data
    public static class PoolData {
        private int page;
        private int limit;
        private List<PoolContent> content;
        private int count;
        private int totalPage;
    }

    @Data
    public static class PoolContent {
        private String service;
        private String servicePool;
        private String ipCount;
        private String container;
        private String tag;
        private String port;
        private String comment;
        private String unregisterPort;
        private String clusterId;
        private String identifier;
    }
}
