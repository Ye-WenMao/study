package com.ywm.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ywm.MemberService;
import com.ywm.SetMealService;
import com.ywm.constant.MessageConstant;
import com.ywm.entity.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author: YEWENMAO
 * @data: 2020/11/1 10:43
 */
@RestController
@RequestMapping("/report")
public class ReportController {

    @Reference
    private MemberService memberService;

    @Reference
    private SetMealService setMealService;

    /**
     * 会员折线图
     * @return
     */
    @GetMapping("/getMemberReport")
    public Result getMemberReport() {

        //
        List<String> months = new ArrayList<String>();
        //获取日历对象
        Calendar calendar = Calendar.getInstance();
        //当前日历第前十二个月
        calendar.add(Calendar.MONTH,-12);
        //格式化
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");

        //遍历获取前十二个月 并保存到list集合中
        for (int i = 0; i < 12; i++) {
            calendar.add(Calendar.MONTH,1);
            Date date = calendar.getTime();
            months.add(sdf.format(date));
        }

        //调用服务
        List<Integer> memberCount = memberService.getMemberReport(months);

        //封装结果
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("months", months);
        resultMap.put("memberCount", memberCount);
        return new Result(true, MessageConstant.GET_MEMBER_NUMBER_REPORT_SUCCESS,resultMap);
    }

    /**
     * 套餐预约占比
     * @return
     */
    @GetMapping("/getSetmealReport")
    public Result getSetmealReport() {

        //套餐数量
        List<Map<String, Object>> setmealCount = setMealService.findSetMealCount();


        List<String> setmealNames = new ArrayList<>();
        if (null!=setmealCount) {

            for (Map<String, Object> map : setmealCount) {
                setmealNames.add((String)map.get("name"));
            }
        }

        Map<String, Object> resultMap = new HashMap<>();

        resultMap.put("setmealNames", setmealNames);
        resultMap.put("setmealCount", setmealCount);
        return new Result(true,MessageConstant.GET_SETMEAL_COUNT_REPORT_SUCCESS,resultMap);
    }
}
