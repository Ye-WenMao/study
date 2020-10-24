package com.ywm;

import com.ywm.entity.PageResult;
import com.ywm.entity.QueryPageBean;
import com.ywm.exception.HealthException;
import com.ywm.pojo.CheckGroup;

import java.util.List;

/**
 * @author: YEWENMAO
 * @data: 2020/10/23 13:30
 */
public interface CheckGroupService {

    //分页查询
    PageResult<CheckGroup> findPage(QueryPageBean queryPageBean);

    //添加检查组
    void add(CheckGroup checkGroup, Integer[] checkitemIds);

    //删除检查组
    void deleteById(int id) throws HealthException;

    //通过检查组id查询选中的检查项id
    List<Integer> findCheckItemIdsByCheckGroupId(int checkGroupId);

    //修改检查组
    void uptate(CheckGroup checkGroup, Integer[] checkitemIds);
}
