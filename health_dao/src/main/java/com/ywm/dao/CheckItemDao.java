package com.ywm.dao;

import com.github.pagehelper.Page;
import com.ywm.pojo.CheckItem;

import java.util.List;

/**
 * @author: YEWENMAO
 * @data: 2020/10/22 14:21
 */

public interface CheckItemDao {

    //查询检查项
    List<CheckItem> findAll();
    //添加检查项
    int add(CheckItem checkItem);
    //删检查项
    int deleteById(Integer id);
    //改检查项
    int update(CheckItem checkItem);

    //获取总数
    int getTotal();
    //分页查检查项
    List<CheckItem> findPage(Integer param, Integer pageSize);
    //分页查检查项二
    Page<CheckItem> findByCondition(String queryString);

}
