package com.ywm.dao;

import com.ywm.pojo.Order;
import java.util.List;
import java.util.Map;

/**
 * @author: YEWENMAO
 * @data: 2020/10/29 17:33
 */

public interface OrderDao {

    void add(Order order);

    List<Order> findByCondition(Order order);

    Map findById4Detail(Integer id);

    Integer findOrderCountByDate(String date);

    Integer findOrderCountAfterDate(String date);

    Integer findVisitsCountByDate(String date);

    Integer findVisitsCountAfterDate(String date);

    List<Map> findHotSetmeal();

}
