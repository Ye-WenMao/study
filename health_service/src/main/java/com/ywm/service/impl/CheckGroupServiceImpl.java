package com.ywm.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.ywm.CheckGroupService;
import com.ywm.dao.CheckGroupDao;
import com.ywm.entity.PageResult;
import com.ywm.entity.QueryPageBean;
import com.ywm.pojo.CheckGroup;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author: YEWENMAO
 * @data: 2020/10/23 13:50
 */
@Service(interfaceClass = CheckGroupService.class)
public class CheckGroupServiceImpl implements CheckGroupService {

    @Autowired
    private CheckGroupDao checkGroupDao;
    @Override
    public PageResult<CheckGroup> findPage(QueryPageBean queryPageBean) {

        PageResult<CheckGroup> pageResult = new PageResult<>();
        Long total = Long.valueOf(checkGroupDao.getTotal());
        List<CheckGroup> list = checkGroupDao.findPage(queryPageBean);
        pageResult.setTotal(total);
        pageResult.setRows(list);
        return pageResult;
    }
}
