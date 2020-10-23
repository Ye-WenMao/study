package com.ywm;

import com.ywm.entity.PageResult;
import com.ywm.entity.QueryPageBean;
import com.ywm.pojo.CheckItem;

import java.util.List;

/**
 * @author: YEWENMAO
 * @data: 2020/10/22 14:13
 */

/**
 * 检查项服务接口
 */
public interface CheckItemService {
    //查询检查项
    List<CheckItem> findAll();

    //添加检查项
    boolean add(CheckItem checkItem);

    //删检查项
    boolean deleteById(Integer id);

    //改检查项
    boolean update(CheckItem checkItem);
    //分页查检查项
    PageResult<CheckItem> findPage(QueryPageBean queryPageBean);
}
