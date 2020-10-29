package com.ywm.dao;

import com.ywm.pojo.OrderSetting;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author: YEWENMAO
 * @data: 2020/10/26 21:14
 */
public interface OrderSettingDao {
    //通过日期查询预约设置信息
    OrderSetting findByOrderDate(Date orderDate);

    //更新可预约数
    void updateOrderNumber(OrderSetting orderSetting);

    //添加预约设置信息
    void addOrder(OrderSetting orderSetting);

    //通过月份查询预约设置信息
    List<OrderSetting> getOrderSettingByMonth(Map map);

    int editReservationsByOrderDate(OrderSetting orderSetting);
}
