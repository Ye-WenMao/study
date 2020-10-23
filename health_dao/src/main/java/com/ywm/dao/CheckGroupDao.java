package com.ywm.dao;


import com.ywm.entity.QueryPageBean;
import com.ywm.pojo.CheckGroup;

import java.util.List;

/**
 * @author: YEWENMAO
 * @data: 2020/10/23 13:55
 */
public interface CheckGroupDao {
    int getTotal();

    List<CheckGroup> findPage(QueryPageBean queryPageBean);
}
