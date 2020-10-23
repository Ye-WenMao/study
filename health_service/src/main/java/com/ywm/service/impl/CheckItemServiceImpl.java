package com.ywm.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.ywm.CheckItemService;
import com.ywm.dao.CheckItemDao;
import com.ywm.entity.PageResult;
import com.ywm.entity.QueryPageBean;
import com.ywm.pojo.CheckItem;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author: YEWENMAO
 * @data: 2020/10/22 14:16
 */

/**
 * 检查项服务
 */
@Service(interfaceClass = CheckItemService.class)
public class CheckItemServiceImpl implements CheckItemService {

    @Autowired
    private CheckItemDao checkItemDao;

    //查询检查项
    @Override
    public List<CheckItem> findAll() {
        return checkItemDao.findAll();
    }

    //添加检查项
    @Override
    public boolean add(CheckItem checkItem) {
        int rows = checkItemDao.add(checkItem);
        return rows > 0;
    }

    //删检查项
    @Override
    public boolean deleteById(Integer id) {
        int rows = checkItemDao.deleteById(id);
        return rows > 0;
    }

    //分页查检查项
    @Override
    public PageResult<CheckItem> findPage(QueryPageBean queryPageBean) {

//        //第二种，Mapper接口方式的调用，推荐这种使用方式。
//        PageHelper.startPage(queryPageBean.getCurrentPage(), queryPageBean.getPageSize());
//        // 模糊查询 拼接 %
//        // 判断是否有查询条件
//        if(!StringUtils.isEmpty(queryPageBean.getQueryString())){
//            // 有查询条件，拼接%
//            queryPageBean.setQueryString("%" + queryPageBean.getQueryString() + "%");
//        }
//        // 紧接着的查询语句会被分页
//        Page<CheckItem> page = checkItemDao.findByCondition(queryPageBean.getQueryString());
//        // 封装到分页结果对象中
//        PageResult<CheckItem> pageResult = new PageResult<CheckItem>(page.getTotal(), page.getResult());
//        return pageResult;



        //第一种 未完待续.........
        String queryString = queryPageBean.getQueryString();

        //获得总数
        int total = checkItemDao.getTotal();

        int currentPage = queryPageBean.getCurrentPage();
        Integer pageSize = Integer.valueOf(queryPageBean.getPageSize());

        Integer param = Integer.valueOf((currentPage-1)*pageSize);


        List<CheckItem> list = checkItemDao.findPage( param, pageSize);

        PageResult pageResult = new PageResult();
        pageResult.setRows(list);
        pageResult.setTotal(Long.valueOf(total));

        return pageResult;
    }
    //改检查项
    @Override
    public boolean update(CheckItem checkItem) {
        int rows = checkItemDao.update(checkItem);
        return rows>0;
    }
}
