package com.david.test;

import com.ctrip.framework.apollo.Config;
import com.ctrip.framework.apollo.ConfigChangeListener;
import com.ctrip.framework.apollo.ConfigService;
import com.ctrip.framework.apollo.model.ConfigChange;
import com.ctrip.framework.apollo.model.ConfigChangeEvent;
import com.google.common.base.Charsets;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Slf4j
public class ApiConfigTest {
    private static final String DEFAULT_VALUE = "undefined";

    private Config config;

    private ApiConfigTest() {
        config = ConfigService.getAppConfig();
        ConfigChangeListener changeListener = new ConfigChangeListener() {
            @Override
            public void onChange(ConfigChangeEvent changeEvent) {
                log.info("Changes for namespace {}", changeEvent.getNamespace());
                for (String key : changeEvent.changedKeys()) {
                    ConfigChange change = changeEvent.getChange(key);
                    log.info("Change - key: {}, oldValue:{}, newValue:{}, changeType:{}",
                            change.getPropertyName(), change.getOldValue(), change.getNewValue(), change.getChangeType());
                }
            }
        };
        config.addChangeListener(changeListener);
    }

    private String getConfig(String key) {
        String result = config.getProperty(key, DEFAULT_VALUE);
        log.info("Loading key: {} with value: {}", key, result);
        return result;
    }


    public static void main(String[] args) throws IOException {
        ApiConfigTest test = new ApiConfigTest();
        System.out.println("Apollo Config Demo. Please input key to get the value. Input quit to exit");
        while (true) {
            System.out.print("> ");
            String input = new BufferedReader(new InputStreamReader(System.in, Charsets.UTF_8)).readLine();
            if (input == null || input.length() == 0) {
                continue;
            }
            input = input.trim();
            if (input.equalsIgnoreCase("quit")) {
                System.exit(0);
            }
            test.getConfig(input);
        }
    }
}
