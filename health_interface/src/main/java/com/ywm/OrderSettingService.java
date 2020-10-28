package com.ywm;

import com.ywm.exception.HealthException;
import com.ywm.pojo.OrderSetting;

import java.util.List;
import java.util.Map;

/**
 * @author: YEWENMAO
 * @data: 2020/10/26 13:17
 */
public interface OrderSettingService {

    //批量导入
    void add(List<OrderSetting> orderSettingList) throws HealthException;

    //通过月份查询预约设置信息
    List<Map<String, Integer>> getOrderSettingByMonth(String month);

    //日历的预约设置
    void editNumberByDate(OrderSetting orderSetting) throws HealthException;
}
