package com.labi.securityjwt.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.labi.securityjwt.entity.BdPermission;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author labi
 * @since 2021-12-03
 */
@Repository
public interface BdPermissionMapper extends BaseMapper<BdPermission> {

    /**
     * 根据用户id获取权限
     *
     * @param userId
     * @return
     */
    List<BdPermission> getPermissionsByUserId(@Param("userId") Long userId);
}
