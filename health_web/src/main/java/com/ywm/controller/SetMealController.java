package com.ywm.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ywm.SetMealService;
import com.ywm.constant.MessageConstant;
import com.ywm.entity.PageResult;
import com.ywm.entity.QueryPageBean;
import com.ywm.entity.Result;
import com.ywm.pojo.Setmeal;
import com.ywm.utils.QiNiuUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @author: YEWENMAO
 * @data: 2020/10/25 11:36
 */

@RestController
@RequestMapping("/setMeal")
public class SetMealController {
    private static final Logger logger = LoggerFactory.getLogger(SetMealController.class);

    @Reference
    private SetMealService setMealService;

    /**
     * 添加套餐
     *
     * @param setmeal
     * @param checkgroupIds
     * @return
     */
    @PostMapping("/add")
    public Result add(@RequestBody Setmeal setmeal, Integer[] checkgroupIds) {

        setMealService.add(setmeal, checkgroupIds);
        return new Result(true, MessageConstant.ADD_SETMEAL_SUCCESS);
    }

    /**
     * 上传图片
     * @param imgFile
     * @return
     */
    @PostMapping("/upload")
    public Result upload(@RequestBody MultipartFile imgFile) {


        //回显图片数据封装
        Map<String, String> dataMap = new HashMap<String, String>();
        dataMap.put("domain", QiNiuUtils.DOMAIN);

        //判断
        if (null == imgFile) {
            return new Result(true,"回显图片地址",dataMap);
        }
        String originalFilename = imgFile.getOriginalFilename();
        //获取图片的后缀名 .jpg
        String extend = originalFilename.substring(originalFilename.lastIndexOf("."));
        //生成唯一的名称
        String uniqueName = UUID.randomUUID().toString() + extend;

        try {
            //上传图片
            QiNiuUtils.uploadViaByte(imgFile.getBytes(), uniqueName);
        } catch (Exception e) {
            return new Result(false, MessageConstant.PIC_UPLOAD_FAIL);
        }

        dataMap.put("imgName", uniqueName);
        return new Result(true, MessageConstant.PIC_UPLOAD_SUCCESS, dataMap);
    }


    /**
     * 分页查询套餐列表
     *
     * @params:
     * @methodReturnType:
     */
    @PostMapping("/findPage")
    public Result findPage(@RequestBody QueryPageBean queryPageBean) {
        PageResult<Setmeal> list = setMealService.findPage(queryPageBean);
        return new Result(true, MessageConstant.QUERY_SETMEALLIST_SUCCESS, list);
    }

    @PostMapping("/deleteById")
    public Result deleteById(int id) {
        setMealService.deleteById(id);
        return new Result(true, "删除套餐成功");
    }


    /**
     * 通过setMealId查询选中的检查组ids
     * @param setMealId
     * @return
     */
    @GetMapping("/findCheckgroupIdsBySetMealId")
    public Result findCheckgroupIdsBySetMealId(int setMealId) {
        List<Integer> checkGroupIds = setMealService.findCheckgroupIdsBySetMealId(setMealId);
        return new Result(true,"查询检查组Ids成功",checkGroupIds );
    }


    /**
     * 编辑套餐
     * @param setmeal
     * @param checkgroupIds
     * @return
     */
    @PostMapping("/update")
    public Result update(@RequestBody Setmeal setmeal,Integer[] checkgroupIds) {
        setMealService.update(setmeal,checkgroupIds);
        return new Result(true,"编辑套餐成功");

    }
}
