package com.labi.securityjwt.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 
 * </p>
 *
 * @author labi
 * @since 2021-12-03
 */
@Getter
@Setter
@TableName("bd_role_permission")
@ApiModel(value = "BdRolePermission对象", description = "")
public class BdRolePermission implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("角色表主键id")
    private Long roleId;

    @ApiModelProperty("权限表主键id")
    private Long permissionId;


}
