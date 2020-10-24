package com.ywm.dao;


import com.github.pagehelper.Page;
import com.ywm.entity.QueryPageBean;
import com.ywm.pojo.CheckGroup;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author: YEWENMAO
 * @data: 2020/10/23 13:55
 */
public interface CheckGroupDao {
    //添加检查组
    void add(CheckGroup checkGroup);
    //分页查询
    Page<CheckGroup> findByCondition(String queryString);

    //判断检查组是否被套餐使用
    int findSetmealCountByCheckGroupId(int id);


    //删除检查组
    void deleteById(int id);

    //通过检查组id查询选中的检查项id
    List<Integer> findCheckItemIdsByCheckGroupId(int checkGroupId);
    //修改检查组
    void update(CheckGroup checkGroup);
    //删除检查组与检查项的关系
    void deleteCheckGroupCheckItem(int id);
    //建立新关系
    void addCheckGroupCheckItem(@Param("groupId") Integer groupId, @Param("checkitemId") Integer checkitemId);

}
