package com.ywm.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ywm.SetMealService;
import com.ywm.constant.MessageConstant;
import com.ywm.entity.Result;
import com.ywm.pojo.Setmeal;
import com.ywm.utils.QiNiuUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author: YEWENMAO
 * @data: 2020/10/28 18:38
 */

@RestController
@RequestMapping("/setMeal")
public class SetmealMobileController {


    @Reference
    private SetMealService setMealService;

    /**
     * 查询所有的套餐
     * @return
     */
    @GetMapping("/getSetMeal")
    public Result getSetMeal() {
        // 查询所有的套餐
        List<Setmeal> list = setMealService.findAll();
        // 套餐里有图片有全路径吗? 拼接全路径
        list.forEach(s->{
            s.setImg(QiNiuUtils.DOMAIN + s.getImg());
        });
        return new Result(true, MessageConstant.GET_SETMEAL_LIST_SUCCESS,list);
    }

    /**
     *查询套餐详情
     * @param id
     * @return
     */
    @GetMapping("/findDetailById")
    public Result findDetailById(int id) {
        Setmeal setmeal = setMealService.findDetailById(id);
        setmeal.setImg(QiNiuUtils.DOMAIN+setmeal.getImg());
        return new Result(true,MessageConstant.QUERY_SETMEAL_SUCCESS,setmeal);
    }

    @GetMapping("/findById")
    public Result findById(int id) {
        Setmeal setmeal = setMealService.findById(id);
        setmeal.setImg(QiNiuUtils.DOMAIN+setmeal.getImg());
        return new Result(true, MessageConstant.QUERY_SETMEAL_SUCCESS,setmeal);
    }
}
