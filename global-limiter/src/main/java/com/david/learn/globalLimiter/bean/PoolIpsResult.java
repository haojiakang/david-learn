package com.david.learn.globalLimiter.bean;

import lombok.Data;

import java.util.List;

/**
 * Created by jiakang on 2017/11/30.
 * 池子ip结果对象
 * @author jiakang
 */
@Data
public class PoolIpsResult {

    private int code;
    private String msg;
    private PoolData data;

    @Data
    public static class PoolData {
        private int page;
        private int limit;
        private List<IpContent> content;
        private int count;
        private int totalPage;
    }

    @Data
    public static class IpContent {
        private String servicePool;
        private String ip;
        private String weight;
        private String createTime;
        private String oprUser;
        private String port;
    }
}
