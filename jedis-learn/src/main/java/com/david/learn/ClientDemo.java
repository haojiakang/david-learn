package com.david.learn;

import redis.clients.jedis.Jedis;

/**
 * Created by haojk on 6/7/16.
 */
public class ClientDemo {

    private void singleTest(){
        Jedis jedis = new Jedis("localhost");
        jedis.set("name", "Jackie");
        String value = jedis.get("name");
        System.out.println(value);
    }

    /*private void clusterTest(){
        Set<HostAndPort> jedisClusterNodes = new HashSet<HostAndPort>();
        //jedis Cluster will attempt to discover cluster nodes automatically
        jedisClusterNodes.add(new HostAndPort("localhost", 6379));
        JedisCluster jedisCluster = new JedisCluster(jedisClusterNodes);
        jedisCluster.set("age", "23");
        String value = jedisCluster.get("age");
        System.out.println(value);
    }*/

    public static void main(String[] args){
        ClientDemo clientDemo = new ClientDemo();
        clientDemo.singleTest();
//        clientDemo.clusterTest();
    }

}
