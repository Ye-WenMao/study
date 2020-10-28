package com.ywm.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.ywm.OrderSettingService;
import com.ywm.dao.OrderSettingDao;
import com.ywm.exception.HealthException;
import com.ywm.pojo.OrderSetting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: YEWENMAO
 * @data: 2020/10/26 13:19
 */

@Service(interfaceClass = OrderSettingService.class)
public class OrderSettingServiceImpl implements OrderSettingService {

    @Autowired
    private OrderSettingDao orderSettingDao;


    @Transactional
    @Override
    public void add(List<OrderSetting> orderSettingList){

        //遍历数据
        for (OrderSetting orderSetting : orderSettingList) {
            //通过日期查询预约设置信息
            OrderSetting orderSettingInDb = orderSettingDao.findByOrderDate(orderSetting.getOrderDate());
            //判断是否存在
            if (null != orderSettingInDb) {
                //存在 判断可预约数是否小于已预约数 可预约数小于已预约数报错
                if (orderSetting.getNumber() < orderSettingInDb.getReservations()) {
                    throw new HealthException("可预约数量不能小于已预约数量");
                }
                //否则更新可预约数
                orderSettingDao.updateOrderNumber(orderSetting);
            } else {
                //不存在 则添加预约设置
                orderSettingDao.addOrder(orderSetting);
            }
        }
    }


    /**
     * 通过月份查询预约设置信息
     * @param month
     * @return
     */
    @Override
    public List<Map<String, Integer>> getOrderSettingByMonth(String month) {
        // 1.组织查询Map，dateBegin表示月份开始时间，dateEnd月份结束时间
        String dateBegin = month + "-1";//2019-03-1
        String dateEnd = month + "-31";//2019-03-31
        Map map = new HashMap();
        map.put("dateBegin",dateBegin);
        map.put("dateEnd",dateEnd);
        // 2.查询当前月份的预约设置
        List<OrderSetting> list = orderSettingDao.getOrderSettingByMonth(map);
        List<Map<String, Integer>> data = new ArrayList<>();
        // 3.将List<OrderSetting>，组织成List<Map>
        for (OrderSetting orderSetting : list) {
            Map orderSettingMap = new HashMap();
            orderSettingMap.put("date",orderSetting.getOrderDate().getDate());//获得日期（几号）
            orderSettingMap.put("number",orderSetting.getNumber());//可预约人数
            orderSettingMap.put("reservations",orderSetting.getReservations());//已预约人数
            data.add(orderSettingMap);
        }

        return data;
    }

    /**
     * 日历的预约设置
     * @param orderSetting
     * @throws HealthException
     */
    @Override
    public void editNumberByDate(OrderSetting orderSetting) throws HealthException {
        //通过日期判断预约设置是否存在？
        OrderSetting os = orderSettingDao.findByOrderDate(orderSetting.getOrderDate());
        //- 存在：
        if(null != os) {
            // 判断已经预约的人数是否大于要更新的最大可预约人数， reverations > 传进来的number数量，则不能更新，要报错
            if(orderSetting.getNumber() < os.getReservations()){
                // 已经预约的人数高于最大预约人数，不允许
                throw new HealthException("最大预约人数不能小已预约人数！");
            }
            // reverations <= 传进来的number数量，则要更新最大可预约数量
            orderSettingDao.updateOrderNumber(orderSetting);
        }else {
            //- 不存在：
            //  - 添加预约设置信息
            orderSettingDao.addOrder(orderSetting);
        }
    }

}
