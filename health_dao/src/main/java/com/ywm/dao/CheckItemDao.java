package com.ywm.dao;

import com.github.pagehelper.Page;
import com.ywm.entity.QueryPageBean;
import com.ywm.pojo.CheckItem;

import java.util.List;

/**
 * @author: YEWENMAO
 * @data: 2020/10/22 14:21
 */

public interface CheckItemDao {

    //查询检查项
    List<CheckItem> findAll();
    //删检查项
    int findCountByCheckItemId(int id);
    void deleteById(int id);
    //分页查检查项二
    Page<CheckItem> findByCondition(String queryString);

    //改检查项
    void update(CheckItem checkItem);
    //添加检查项
    void add(CheckItem checkItem);

}
