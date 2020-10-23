package com.ywm.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ywm.CheckGroupService;
import com.ywm.entity.PageResult;
import com.ywm.entity.QueryPageBean;
import com.ywm.entity.Result;
import com.ywm.pojo.CheckGroup;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author: YEWENMAO
 * @data: 2020/10/23 12:17
 */

@RestController
@RequestMapping("/checkGroup")
public class CheckGroupController {

    @Reference
    private CheckGroupService checkGroupService;

    @PostMapping("/findPage")
    public Result findPage(@RequestBody QueryPageBean queryPageBean) {
        PageResult<CheckGroup> pageResult = checkGroupService.findPage(queryPageBean);
        return new Result(true, "ok",pageResult);
    }
}
