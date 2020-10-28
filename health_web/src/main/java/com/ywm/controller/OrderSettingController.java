package com.ywm.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ywm.OrderSettingService;
import com.ywm.constant.MessageConstant;
import com.ywm.entity.Result;
import com.ywm.pojo.OrderSetting;
import com.ywm.utils.POIUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author: YEWENMAO
 * @data: 2020/10/26 13:11
 */

@RestController
@RequestMapping("/orderSetting")
public class OrderSettingController {

    @Reference
    private OrderSettingService orderSettingService;

    /**
     * 批量导入
     * @param excelFile
     * @return
     * @throws Exception
     */
    @PostMapping("/upload")
    public Result upload(MultipartFile excelFile) throws Exception{

        try {
            //读取excel文件
            List<String[]> strings = POIUtils.readExcel(excelFile);

            //转成List<OrderSetting>
            List<OrderSetting> orderSettingList = new ArrayList<OrderSetting>();

            //日期格式解析
            SimpleDateFormat sdf = new SimpleDateFormat(POIUtils.DATE_FORMAT);

            Date orderDate = null;
            OrderSetting os = null;
            for (String[] string : strings) {

                orderDate = sdf.parse(string[0]);
                Integer number = Integer.valueOf(string[1]);
                os = new OrderSetting(orderDate, number);
                orderSettingList.add(os);
            }
            orderSettingService.add(orderSettingList);
        } catch (Exception e) {
            return new Result(false,e.getMessage());
        }

        return new Result(true, MessageConstant.IMPORT_ORDERSETTING_SUCCESS);
    }

    /**
     * 通过月份查询预约设置信息
     * @param month
     * @return
     */
    @GetMapping("/getOrderSettingByMonth")
    public Result findAllByOrderDate(String month) {

        List<Map<String, Integer>> data = orderSettingService.getOrderSettingByMonth(month);
        return new Result(true,MessageConstant.GET_ORDERSETTING_SUCCESS,data );
    }

    /**
     * 日历的预约设置
     * @param orderSetting
     * @return
     */
    @PostMapping("/editNumberByDate")
    public Result editNumberByDate(@RequestBody OrderSetting orderSetting) {
        orderSettingService.editNumberByDate(orderSetting);
        return new Result(true, MessageConstant.ORDERSETTING_SUCCESS);
    }
}
