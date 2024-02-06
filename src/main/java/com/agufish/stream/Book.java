package com.agufish.stream;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode// 用于后期的去重使用
public class Book {
    // id
    private Long id;
    // 书名
    private String name;
    // 分类
    private String category;
    // 简介
    private String intro;
    // 评分
    private Integer score;
}