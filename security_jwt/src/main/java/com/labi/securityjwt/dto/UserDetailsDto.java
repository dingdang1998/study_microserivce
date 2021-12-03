package com.labi.securityjwt.dto;

import com.labi.securityjwt.entity.BdPermission;
import com.labi.securityjwt.entity.BdUser;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @program: study_microservice
 * @description:SpringSecurity需要的用户详情
 * @author: dzp
 * @create: 2021-12-03 15:03
 **/
@AllArgsConstructor
@Getter
public class UserDetailsDto implements UserDetails {

    public static final Byte ENABLE = 1;

    private final BdUser user;
    private final List<BdPermission> permissionList;

    /**
     * 返回当前用户的权限
     *
     * @return
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        //返回当前用户的权限
        return permissionList.stream()
                .filter(permission -> permission.getPermissionCode() != null)
                .map(permission -> new SimpleGrantedAuthority(permission.getPermissionCode()))
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return ENABLE.equals(user.getStatus());
    }
}
