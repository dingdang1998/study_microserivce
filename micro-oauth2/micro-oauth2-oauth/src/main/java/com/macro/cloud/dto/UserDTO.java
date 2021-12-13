package com.macro.cloud.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @program: study_microservice
 * @description:
 * @author: dzp
 * @create: 2021-12-09 16:21
 **/
@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
public class UserDTO {
    private Long id;
    private String username;
    private String password;
    private Integer status;
    private List<String> roles;
}
