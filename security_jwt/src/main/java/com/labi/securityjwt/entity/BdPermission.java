package com.labi.securityjwt.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

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
@TableName("bd_permission")
@ApiModel(value = "BdPermission对象", description = "")
public class BdPermission implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("菜单编码,前端判断并展示菜单使用")
    private String menuCode;

    @ApiModelProperty("菜单名称")
    private String menuName;

    @ApiModelProperty("权限值")
    private String permissionCode;

    @ApiModelProperty("权限的中文含义")
    private String permissionName;
}
