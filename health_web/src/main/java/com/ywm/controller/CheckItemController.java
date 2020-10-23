package com.ywm.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ywm.CheckItemService;
import com.ywm.constant.MessageConstant;
import com.ywm.entity.PageResult;
import com.ywm.entity.QueryPageBean;
import com.ywm.entity.Result;
import com.ywm.pojo.CheckItem;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author: YEWENMAO
 * @data: 2020/10/22 14:03
 */


/**
 * 体检检查项管理模块
 */
@RestController
@RequestMapping("/checkitem")
public class CheckItemController {

    @Reference
    private CheckItemService checkItemService;

    //查询检查项
    @GetMapping("/findAll")
    public Result findAll() {
        List<CheckItem> list = checkItemService.findAll();
        return new Result(true,"ok", list);
    }

    //新增检查项
    @PostMapping("/add")
    public Result add(@RequestBody CheckItem checkItem) {
        boolean flag = checkItemService.add(checkItem);
        return new Result(true, MessageConstant.ADD_CHECKGROUP_SUCCESS);
    }

    //删除检查项
    @PostMapping("/deleteById")
    public Result deleteById(@RequestBody CheckItem checkItem) {
        if (checkItem==null) {
            return new Result(false, MessageConstant.DELETE_CHECKITEM_FAIL);
        }
        Integer id = checkItem.getId();
        boolean flag = checkItemService.deleteById(id);
        return new Result(true, MessageConstant.ADD_CHECKGROUP_SUCCESS);
    }
    //改检查项
    @PostMapping("/update")
    public Result update(@RequestBody CheckItem checkItem) {
        boolean flag = checkItemService.update(checkItem);
        return new Result(true,"ok");
    }
    //分页查检查项
    @PostMapping("/findPage")
    public Result findPage(@RequestBody QueryPageBean queryPageBean) {

        PageResult<CheckItem> pageResult = checkItemService.findPage(queryPageBean);

        return new Result(true, "ok",pageResult);
    }
}
