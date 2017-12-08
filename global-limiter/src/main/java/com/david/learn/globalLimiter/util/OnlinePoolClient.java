package com.david.learn.globalLimiter.util;

import com.alibaba.fastjson.JSONObject;
import com.david.learn.globalLimiter.bean.AllPoolName;
import com.david.learn.globalLimiter.bean.PoolIpsResult;
import com.sun.tools.javac.util.Pair;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by jiakang on 2017/11/30.
 * 在线池子客户端工具类
 * @author jiakang
 */
@Slf4j
public class OnlinePoolClient {
    /**
     * 获取某个ip所属的池子列表
     */
    private static final String ip2poolURL = "http://raptor.api.weibo.com:5353/v1/manage/node/list/?appkey=F136D5E5CC25A2A5CC59C6938153563C&ip=";
    /**
     * 获取某个池子下面的ip列表
     * $page$从1开始
     */
    private static final String pool2ipURL = "http://raptor.api.weibo.com:5353/v1/manage/node/list/?page=$page$&limit=10000&appkey=F136D5E5CC25A2A5CC59C6938153563C&service_pool=";
    /**
     * 获取所有的池子列表
     * $page$从1开始
     */
    private static final String poolnamesURL = "http://raptor.api.weibo.com:5353/v1/manage/pool/list/?page=$page$&limit=10000&appkey=F136D5E5CC25A2A5CC59C6938153563C";

    /**
     * apache http客户端
     */
    private static HttpClient client = new HttpClient();

    /**
     * 获取所有以poolNamePrefix开头的池子下面的ip总数
     * @param poolNamePrefix 池子名称前缀
     * @return 以poolNamePrefix开头的池子下面的ip总数
     */
    public static int getPoolIpsCount(String poolNamePrefix) {
        return getPoolIpsPair(poolNamePrefix).fst;
    }

    /**
     * 获取所有以poolNamePrefix开头的池子下面的ip列表
     * @param poolNamePrefix 池子名称前缀
     * @return 以poolNamePrefix开头的池子下面的ip列表
     */
    public static List<String> getPoolIps(String poolNamePrefix) {
        return getPoolIpsPair(poolNamePrefix).snd;
    }

    /**
     * 获取所有以poolNamePrefix开头的池子下面的ip总数和列表Pair
     * @param poolNamePrefix 池子名称前缀
     * @return 以poolNamePrefix开头的池子下面的ip总数和列表Pair
     */
    public static Pair<Integer, List<String>> getPoolIpsPair(String poolNamePrefix) {
        List<String> matchedPoolNames = getMatchedAllPoolNames(poolNamePrefix);
        return getAllPrecisePoolPair(matchedPoolNames);
    }

    /**
     * 获取所有名称以poolNamePrefix开始的池子列表
     * @param poolNamePrefix 池子名称前缀
     * @return 池子名称列表
     */
    private static List<String> getMatchedAllPoolNames(String poolNamePrefix) {
        List<String> allPoolNames = Collections.synchronizedList(new ArrayList<>());
        for (int page = 1; ; page++) {
            String poolNamesUrl = poolnamesURL.replace("$page$", Integer.toString(page));
            String result = executeHttpGet(poolNamesUrl);

            AllPoolName allPoolName = JSONObject.parseObject(result, AllPoolName.class);
            AllPoolName.PoolData poolData = allPoolName.getData();
            poolData.getContent().parallelStream().forEach(poolContent -> {
                String servicePool = poolContent.getServicePool();
                if (servicePool.startsWith(poolNamePrefix)) {
                    allPoolNames.add(servicePool);
                }
            });
            if (page >= poolData.getTotalPage()) {
                break;
            }
        }
        return allPoolNames;
    }

    private static String executeHttpGet(String url) {
        GetMethod poolNamesUrlMethod = new GetMethod(url);
        String result = null;
        try {
            client.executeMethod(poolNamesUrlMethod);
            result = poolNamesUrlMethod.getResponseBodyAsString();
        } catch (IOException e) {
            log.warn("executeHttpGet error, url:{}", url);
        }
        return result;
    }

    /**
     * 获取poolNames池子列表下面的 总数和ip列表Pair
     * @param poolNames 池子名称列表
     * @return 合并后的总数和ip列表Pair
     */
    private static Pair<Integer, List<String>> getAllPrecisePoolPair(List<String> poolNames) {
        AtomicInteger count = new AtomicInteger(0);
        List<String> precisePoolIps = Collections.synchronizedList(new ArrayList<>());
        poolNames.parallelStream().forEach(poolName -> {
            Pair<Integer, List<String>> resultPair = getAllPrecisePoolPair(poolName);
            precisePoolIps.addAll(resultPair.snd);
            count.addAndGet(resultPair.fst);
        });
        return Pair.of(count.get(), precisePoolIps);
    }

    /**
     * 获取poolName池子下面的 总数和ip列表Pair
     * @param poolName 池子名称
     * @return 总数和ip列表Pair
     */
    private static Pair<Integer, List<String>> getAllPrecisePoolPair(String poolName) {
        int count = 0;
        List<String> precisePoolIps = Collections.synchronizedList(new ArrayList<>());
        for (int page = 1; ; page++) {
            String pool2ipUrl = pool2ipURL.replace("$page$", Integer.toString(page)) + poolName;
            String result = executeHttpGet(pool2ipUrl);
            PoolIpsResult poolIpsResult = JSONObject.parseObject(result, PoolIpsResult.class);
            PoolIpsResult.PoolData poolData = poolIpsResult.getData();
            count += poolData.getCount();
            poolData.getContent().parallelStream().forEach(ipContent -> {
                precisePoolIps.add(ipContent.getIp());
            });
            if (page >= poolData.getTotalPage()) {
                break;
            }
        }
        return Pair.of(count, precisePoolIps);
    }
}
