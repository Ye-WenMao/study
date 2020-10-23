package com.ywm;

import com.ywm.entity.PageResult;
import com.ywm.entity.QueryPageBean;
import com.ywm.pojo.CheckGroup;

/**
 * @author: YEWENMAO
 * @data: 2020/10/23 13:30
 */
public interface CheckGroupService {
    PageResult<CheckGroup> findPage(QueryPageBean queryPageBean);
}
