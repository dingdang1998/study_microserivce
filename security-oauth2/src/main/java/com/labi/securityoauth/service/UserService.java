package com.labi.securityoauth.service;

import com.labi.securityoauth.component.CustomPasswordEncoder;
import com.labi.securityoauth.doadmin.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @program: study_microservice
 * @description: 加载用户信息
 * @author: dzp
 * @create: 2021-12-07 16:17
 **/
@Service
public class UserService implements UserDetailsService {

    private List<User> userList;

    @Autowired
    private CustomPasswordEncoder passwordEncoder;

    @PostConstruct
    public void initData() {
        String password = passwordEncoder.encode("123456");
        userList = new ArrayList<>();
        userList.add(new User("macro", password, AuthorityUtils.commaSeparatedStringToAuthorityList("admin")));
        userList.add(new User("andy", password, AuthorityUtils.commaSeparatedStringToAuthorityList("client")));
        userList.add(new User("mark", password, AuthorityUtils.commaSeparatedStringToAuthorityList("client")));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Map<String, User> collect = userList.stream().collect(Collectors.toMap(User::getUsername, t -> t));
        User user = collect.get(username);
        if (user != null) {
            return user;
        } else {
            throw new UsernameNotFoundException("用户名或密码错误");
        }
    }
}
