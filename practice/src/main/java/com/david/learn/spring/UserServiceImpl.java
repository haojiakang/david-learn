package com.david.learn.spring;


import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by jiakang on 2018/4/27
 *
 * @author jiakang
 */
@Service("userService")
public class UserServiceImpl implements UserService {

    private Map<Integer, UserInfo> userInfoMap = new HashMap<>();

    @PostConstruct
    public void initMap() {
        userInfoMap.put(1, new UserInfo(1, "David", 23, true));
        userInfoMap.put(2, new UserInfo(2, "Lily", 18, false));
        userInfoMap.put(3, new UserInfo(3, "Jack", 45, true));
        userInfoMap.put(4, new UserInfo(4, "Cherry", 27, false));
    }

    @Override
    public UserInfo getUserInfo(int id) {
        return userInfoMap.get(id);
    }
}