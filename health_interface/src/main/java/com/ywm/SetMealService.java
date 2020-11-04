package com.ywm;

import com.ywm.entity.PageResult;
import com.ywm.entity.QueryPageBean;
import com.ywm.exception.HealthException;
import com.ywm.pojo.Setmeal;

import java.util.List;
import java.util.Map;

/**
 * @author: YEWENMAO
 * @data: 2020/10/25 15:34
 */

/**
 * 套餐
 */
public interface SetMealService {

    //添加套餐
    Integer add(Setmeal setmeal, Integer[] checkgroupIds);

    //分页查询套餐
    PageResult<Setmeal> findPage(QueryPageBean queryPageBean);

    //删除套餐
    void deleteById(int id) throws HealthException;

    //通过setMealId查询选中的检查组ids
    List<Integer> findCheckgroupIdsBySetMealId(int setMealId);

    //编辑套餐
    void update(Setmeal setmeal, Integer[] checkgroupIds);

    //获取db图片
    List<String> findImg();

    //查询所有的套餐
    List<Setmeal> findAll();

    //查询套餐详情
    Setmeal findDetailById(int id);

    Setmeal findById(int id);

    List<Map<String, Object>> findSetMealCount();
}
