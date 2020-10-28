package com.ywm.dao;

import com.github.pagehelper.Page;
import com.ywm.pojo.Setmeal;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author: YEWENMAO
 * @data: 2020/10/25 15:43
 */
public interface SetMealDao {
    //添加套餐
    void add(Setmeal setmeal);

    //添加套餐与检查组的关系
    void addSetMealCheckGroup(@Param("setmealId") Integer setmealId, @Param("checkgroupId") Integer checkgroupId);

    //分页查询套餐列表
    Page<Setmeal> findByCondition(String queryString);

    int findOrderCountBySetMealId(int id);

    //删关系
    void deleteSetMealCheckGroup(int id);

    //删套餐id
    void deleteById(int id);

    //通过setMealId查询选中的检查组ids
    List<Integer> findCheckgroupIdsBySetMealId(int setMealId);

    //编辑套餐
    void update(Setmeal setmeal);

    List<String> findImg();

    //查询所有的套餐
    List<Setmeal> findAll();

    Setmeal findDetailById(int id);

    Setmeal findById(int id);
}
