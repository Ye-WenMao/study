package com.ywm.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ywm.CheckGroupService;
import com.ywm.constant.MessageConstant;
import com.ywm.dao.CheckGroupDao;
import com.ywm.entity.PageResult;
import com.ywm.entity.QueryPageBean;
import com.ywm.exception.HealthException;
import com.ywm.pojo.CheckGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * @author: YEWENMAO
 * @data: 2020/10/23 13:50
 */
@Service(interfaceClass = CheckGroupService.class)
public class CheckGroupServiceImpl implements CheckGroupService {

    @Autowired
    private CheckGroupDao checkGroupDao;

    /**
     * 分页查询
     * @param queryPageBean
     * @return
     */
    @Override
    public PageResult<CheckGroup> findPage(QueryPageBean queryPageBean) {


        PageHelper.startPage(queryPageBean.getCurrentPage(), queryPageBean.getPageSize());
        if(!StringUtils.isEmpty(queryPageBean.getQueryString())){
            // 拼接%
            queryPageBean.setQueryString("%" + queryPageBean.getQueryString()+ "%");
        }

        Page<CheckGroup> page = checkGroupDao.findByCondition(queryPageBean.getQueryString());
        return new PageResult<CheckGroup>(page.getTotal(), page.getResult());

    }

    /**
     * 添加检查组
     * @params:
     * @methodReturnType:
     */
    @Transactional
    @Override
    public void add(CheckGroup checkGroup, Integer[] checkitemIds) {

        checkGroupDao.add(checkGroup);
        if (null != checkitemIds) {
            Integer groupId = checkGroup.getId();
            for (Integer checkitemId : checkitemIds) {
                checkGroupDao.addCheckGroupCheckItem(groupId,checkitemId);
            }
        }

    }

    /**
     * 删除检查组
     * @params:
     * @methodReturnType:
     */
    @Transactional
    @Override
    public void deleteById(int id) {
        //判断检查组是否被套餐使用
        int cnt = checkGroupDao.findSetmealCountByCheckGroupId(id);
        if (cnt>0) {
            throw new HealthException(MessageConstant.DELETE_CHECKGROUP_FAIL);
        }
        //删除检查组与检查项的关系
        checkGroupDao.deleteCheckGroupCheckItem(id);
        //删除检查组
        checkGroupDao.deleteById(id);
    }

    /**
     * 通过检查组id查询选中的检查项id
     * @param checkGroupId
     * @return
     */
    @Override
    public List<Integer> findCheckItemIdsByCheckGroupId(int checkGroupId) {
        return checkGroupDao.findCheckItemIdsByCheckGroupId(checkGroupId);
    }

    /**
     *修改检查组
     * @param checkGroup
     * @param checkitemIds
     */
    @Override
    public void uptate(CheckGroup checkGroup, Integer[] checkitemIds) {
        //修改检查组
        checkGroupDao.update(checkGroup);
        // 删除旧关系
        checkGroupDao.deleteCheckGroupCheckItem(checkGroup.getId());
        // 建立新关系
        if(null != checkitemIds){
            for (Integer checkitemId : checkitemIds) {
                checkGroupDao.addCheckGroupCheckItem(checkGroup.getId(), checkitemId);
            }
        }

    }
}
