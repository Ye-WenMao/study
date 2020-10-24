package com.ywm.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ywm.CheckGroupService;
import com.ywm.constant.MessageConstant;
import com.ywm.entity.PageResult;
import com.ywm.entity.QueryPageBean;
import com.ywm.entity.Result;
import com.ywm.pojo.CheckGroup;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * @author: YEWENMAO
 * @data: 2020/10/23 12:17
 */

@RestController
@RequestMapping("/checkgroup")
public class CheckGroupController {

    @Reference
    private CheckGroupService checkGroupService;

    /**
     *分页查询
     * @param queryPageBean
     * @return
     */
    @PostMapping("/findPage")
    public Result findPage(@RequestBody QueryPageBean queryPageBean) {
        PageResult<CheckGroup> pageResult = checkGroupService.findPage(queryPageBean);
        return new Result(true, MessageConstant.QUERY_CHECKGROUP_SUCCESS,pageResult);
    }

    /**
     * 添加检查组
     * @param checkGroup
     * @param checkitemIds
     * @return
     */
    @PostMapping("/add")
    public Result add(@RequestBody CheckGroup checkGroup,Integer[] checkitemIds) {
        checkGroupService.add(checkGroup,checkitemIds);
        return new Result(true, MessageConstant.ADD_CHECKGROUP_SUCCESS);
    }

    /**
     * 删除检查组
     * @params:
     * @methodReturnType:
     */
    @PostMapping("/deleteById")
    public Result deleteById(int id) {
        checkGroupService.deleteById(id);
        return new Result(true, MessageConstant.DELETE_CHECKGROUP_SUCCESS);
    }

    /**
     * 通过检查组id查询选中的检查项id
     * @param checkGroupId
     * @return
     */
    @GetMapping("/findCheckItemIdsByCheckGroupId")
    public Result findCheckItemIdsByCheckGroupId(int checkGroupId) {
        List<Integer> checkItemIds = checkGroupService.findCheckItemIdsByCheckGroupId(checkGroupId);
        return new Result(true, MessageConstant.QUERY_SETMEALLIST_SUCCESS,checkItemIds);
    }

    /**
     * 提交修改
     * @param checkGroup
     * @param checkitemIds
     * @return
     */
    @PostMapping("/update")
    public Result update(@RequestBody CheckGroup checkGroup, Integer[] checkitemIds) {

        checkGroupService.uptate(checkGroup,checkitemIds);
        return new Result(true, MessageConstant.EDIT_CHECKGROUP_SUCCESS);
    }
}
