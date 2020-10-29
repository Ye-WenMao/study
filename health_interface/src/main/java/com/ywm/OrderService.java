package com.ywm;

import com.ywm.exception.HealthException;
import com.ywm.pojo.Order;

import java.util.Map;

/**
 * @author: YEWENMAO
 * @data: 2020/10/29 17:21
 */
public interface OrderService {

    //提交预约处理
    Order submit(Map<String, String> orderInfo) throws HealthException;

    //成功预约信息回显
    Map<String, Object> findOrderDetailById(int id);
}
