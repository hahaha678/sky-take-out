package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.MessageConstant;
import com.sky.constant.StatusConstant;
import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.entity.DishFlavor;
import com.sky.exception.DeletionNotAllowedException;
import com.sky.mapper.DishFlavorMapper;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetmealDishMapper;
import com.sky.result.PageResult;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DishServiceImp implements DishService {

    @Autowired
    private DishMapper dishMapper;
    @Autowired
    private DishFlavorMapper dishFlavorMapper;
    @Autowired
    private SetmealDishMapper setmealDishMapper;

    @Override
    @Transactional
    public void save(DishDTO dishDTO) {
        Dish dish = new Dish();
        BeanUtils.copyProperties(dishDTO, dish);
        dish.setStatus(StatusConstant.ENABLE);
        dishMapper.insert(dish);
        Long dishId = dish.getId();

        List<DishFlavor> flavors = dishDTO.getFlavors();
        if(flavors != null && flavors.size() > 0){
            for (DishFlavor flavor : flavors) {
                flavor.setDishId(dishId);
            }
            dishFlavorMapper.insert(flavors);
        }
    }

    @Override
    public PageResult page(DishPageQueryDTO dishPageQueryDTO) {
        PageHelper.startPage(dishPageQueryDTO.getPage(), dishPageQueryDTO.getPageSize());
        Page<DishVO> dishes = dishMapper.page(dishPageQueryDTO);
        return new PageResult(dishes.getTotal(), dishes.getResult());
    }

    @Override
    @Transactional
    public void delete(String ids) {
        List<Long> idList = Arrays.asList(ids.split(","))
                .stream()
                .map(Long::parseLong)
                .collect(Collectors.toList());
        idList.forEach(id->{
            DishVO dish = dishMapper.selectById(id);
            if(dish.getStatus() == StatusConstant.ENABLE){
                throw new DeletionNotAllowedException(MessageConstant.DISH_ON_SALE);
            } else if ( setmealDishMapper.countByDishId(id) > 0) {
                throw new DeletionNotAllowedException(MessageConstant.DISH_BE_RELATED_BY_SETMEAL);
                
            }
        });
        dishMapper.deleteByIds(idList);
        dishFlavorMapper.deleteByDishIds(idList);
    }

    @Override
    public DishVO getById(Long id) {
        DishVO dishVO = dishMapper.selectById(id);
        dishVO.setFlavors(dishFlavorMapper.selectByDishId(id));
        return dishVO;
    }

    @Override
    public List<DishVO> getByCategoryId(Long categoryId) {
        return dishMapper.selectByCategoryId(categoryId);
    }

    @Override
    @Transactional
    public void update(DishDTO dishDTO) {
        Dish dish = new Dish();
        BeanUtils.copyProperties(dishDTO, dish);
        dishMapper.update(dish);
        List<DishFlavor> dishFlavor = dishDTO.getFlavors();
        if(dishFlavor != null && dishFlavor.size() > 0){
            ArrayList<Long> dishIds = new ArrayList<Long>();
            dishIds.add(dish.getId());
            dishFlavorMapper.deleteByDishIds(dishIds);
            for (DishFlavor flavor : dishFlavor) {
                flavor.setDishId(dish.getId());
            }
            dishFlavorMapper.insert(dishFlavor);
        }
    }
}
