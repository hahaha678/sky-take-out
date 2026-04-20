package com.sky.mapper;

import com.sky.entity.SetmealDish;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SetmealDishMapper {

    @Select("select count(*) from setmeal_dish where dish_id = #{id}")
    Integer countByDishId(Long id);

    void insert(List<SetmealDish> setmealDishes);

    void deleteBySetmealIds(List<Integer> setmealIds);

    List<SetmealDish> selectBySetmealId(Long id);
}
