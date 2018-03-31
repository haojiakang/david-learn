package com.david.test;

import cn.vika.memcached.CasValue;
import com.weibo.api.commons.memcache.CacheServiceTemplate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)//让测试运行于Spring测试环境
@ContextConfiguration("classpath:cache_service.xml")
public class CacheServiceTest {

    @Resource
    private CacheServiceTemplate<String> cacheService;

    @Test
    public void testSet() {
        String key = "test_key";
        String value = "test_value";
        boolean set = cacheService.set(key, value);
        System.out.println(set);
        boolean add = cacheService.add(key, "test_value2");
        System.out.println(add);
        Object result = cacheService.get(key);
        System.out.println(result);
        boolean delete = cacheService.delete(key);
        System.out.println(delete);
    }

    @Test
    public void testCas() {
        String key = "test_cas";
//        boolean set = cacheService.set(key, "test_casValue0");
//        System.out.println(set);
        CasValue<String> cas = cacheService.getCas(key);
        System.out.println(cas);
        if (cas == null) {
            cas = new CasValue<>();
            cas.setValue("test_casValue");
        } else {
            cas.setValue("test_casValue1");
        }
        boolean cas1 = cacheService.cas(key, cas);
        System.out.println(cas1);
        String get = cacheService.get(key);
        System.out.println(get);
        boolean delete = cacheService.delete(key);
        System.out.println(delete);
    }

    @Test
    public void testGet() {
        long uid = 1885521345L;
        List<String> keySuffixes = Arrays.asList(".vsm", ".svmf", ".svmo", ".svmb");
        for (String keySuffix : keySuffixes) {
            String key = uid + keySuffix;
            String result = cacheService.get(key);
            System.out.println(String.format("key:%s, result:%s", key, result));
        }
    }

    @Test
    public void reset() {
        long uid = 1885521345L;
        List<String> keySuffixes = Arrays.asList(".vsm", ".svmf", ".svmo", ".svmb");
        for (String keySuffix : keySuffixes) {
            String key = uid + keySuffix;
            boolean result = cacheService.delete(key);
            System.out.println(String.format("key:%s, result:%s", key, result));
        }
    }
}
