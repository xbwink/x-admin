package com.xb.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author xb
 * @description TODO
 * @create 2023-07-04 10:01
 * @vesion 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Page {
    private String userName;
    private String phone;
    private Integer pageNo;
    private Integer pageSize;

}
