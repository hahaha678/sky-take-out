package com.sky.mapper;

import com.sky.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Mapper
public interface UserMapper {
    User getByOpenid(String openid);

    void insert(User user);

    User getById(Long userId);

    Integer countByDate(LocalDateTime begin, LocalDateTime end);
}
