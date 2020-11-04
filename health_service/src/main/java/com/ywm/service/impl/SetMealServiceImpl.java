package com.ywm.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ywm.SetMealService;
import com.ywm.dao.SetMealDao;
import com.ywm.entity.PageResult;
import com.ywm.entity.QueryPageBean;
import com.ywm.exception.HealthException;
import com.ywm.pojo.Setmeal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @author: YEWENMAO
 * @data: 2020/10/25 15:36
 */
@Service(interfaceClass = SetMealService.class)
public class SetMealServiceImpl implements SetMealService {

    @Autowired
    private SetMealDao setMealDao;

    /**
     * 添加套餐
     * @param setmeal
     * @param checkgroupIds
     */
    @Transactional
    @Override
    public Integer add(Setmeal setmeal, Integer[] checkgroupIds) {
        setMealDao.add(setmeal);

        Integer setmealId = setmeal.getId();
        //套餐与检查组的关系
        if (checkgroupIds != null) {
            for (Integer checkgroupId : checkgroupIds) {
                setMealDao.addSetMealCheckGroup(setmealId, checkgroupId);
            }
        }
        return setmealId;
    }

    /**
     * 分页查询套餐列表
     * @param queryPageBean
     * @return
     */
    @Override
    public PageResult<Setmeal> findPage(QueryPageBean queryPageBean) {
        PageHelper.startPage(queryPageBean.getCurrentPage(),queryPageBean.getPageSize());
        if (!StringUtils.isEmpty(queryPageBean.getQueryString())) {
            queryPageBean.setQueryString("%" + queryPageBean.getQueryString() + "%");
        }

        Page<Setmeal> page = setMealDao.findByCondition(queryPageBean.getQueryString());
        PageResult<Setmeal> pageResult = new PageResult<>(page.getTotal(),page.getResult());

        return pageResult;
    }

    /**
     * 删除套餐
     * @param id
     */
    @Transactional
    @Override
    public void deleteById(int id) {
        //查套餐是否被订单使用
        int cnt = setMealDao.findOrderCountBySetMealId(id);
        if (cnt>0) {
            throw new HealthException("此套餐已被订单使用,不能删除");
        }
        //删关系
        setMealDao.deleteSetMealCheckGroup(id);
        //删套餐id
        setMealDao.deleteById(id);

    }

    /**
     * 通过setMealId查询选中的检查组ids
     * @param setMealId
     * @return
     */
    @Override
    public List<Integer> findCheckgroupIdsBySetMealId(int setMealId) {
        return setMealDao.findCheckgroupIdsBySetMealId(setMealId);
    }

    /**
     * 编辑套餐
     * @param setmeal
     * @param checkgroupIds
     */
    @Transactional
    @Override
    public void update(Setmeal setmeal, Integer[] checkgroupIds){
        //修改套餐
        setMealDao.update(setmeal);

        //删除久关系
        setMealDao.deleteSetMealCheckGroup(setmeal.getId());

        //判空
        //建立新关系
        if (null != checkgroupIds) {
            for (Integer checkgroupId : checkgroupIds) {
                setMealDao.addSetMealCheckGroup(setmeal.getId(),checkgroupId);
            }
        }

    }

    @Override
    public List<String> findImg() {
        return setMealDao.findImg();
    }

    /**
     * 查询所有的套餐
     * @return
     */
    @Override
    public List<Setmeal> findAll() {
        return setMealDao.findAll();
    }

    /**
     * 查询套餐详情
     * @param id
     * @return
     */
    @Override
    public Setmeal findDetailById(int id) {
        return setMealDao.findDetailById(id);
    }

    @Override
    public Setmeal findById(int id) {
        return setMealDao.findById(id);
    }

    @Override
    public List<Map<String, Object>> findSetMealCount() {
        return setMealDao.findSetMealCount();
    }
}
