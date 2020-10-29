package com.ywm.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.ywm.OrderService;
import com.ywm.dao.MemberDao;
import com.ywm.dao.OrderDao;
import com.ywm.dao.OrderSettingDao;
import com.ywm.exception.HealthException;
import com.ywm.pojo.Member;
import com.ywm.pojo.Order;
import com.ywm.pojo.OrderSetting;
import com.ywm.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author: YEWENMAO
 * @data: 2020/10/29 17:31
 */

@Service(interfaceClass = OrderService.class)
public class OrderServiceImpl implements OrderService {


    @Autowired
    private OrderSettingDao orderSettingDao;

    @Autowired
    private MemberDao memberDao;

    @Autowired
    private OrderDao orderDao;
    /**
     * 提交预约处理
     * @param orderInfo
     * @return
     */


    @Transactional
    @Override
    public Order submit(Map<String, String> orderInfo) {
        Date orderDate = null;

        try {
            //获取页面预约日期
            orderDate = DateUtils.parseString2Date(orderInfo.get("orderDate"));
        } catch (Exception e) {
            throw new HealthException("日期格式错误");
        }

        //通过日期查询预约设置信息
        OrderSetting orderSetting = orderSettingDao.findByOrderDate(orderDate);

        //判断该日期能否预约
        if (null == orderSetting) {
            throw new HealthException("所选日期不能预约,请选择其他日期");
        }
        //判断预约人数是否已满
        if (orderSetting.getReservations() >= orderSetting.getNumber()) {
            throw new HealthException("所选日期预约人数已满,请选择其他日期");
        }
        //判断会员是否重预约
        //根据手机号查询会员是否存在
        Member member = memberDao.findByTelephone(orderInfo.get("telephone"));



        Order order = new Order();
        order.setOrderDate(orderDate);
        order.setSetmealId(Integer.valueOf(orderInfo.get("setmealId")));
        //判断会员是否存在
        if(null != member) {
            //存在 则判断是否重复预约

            //查询t_order, 条件orderDate=? and setmeal_id=?,member_id=?
            order.setMemberId(member.getId());
            //判断是否重复预约
            List<Order> orderList = orderDao.findByCondition(order);
            if(null != orderList && orderList.size() > 0){
                throw new HealthException("该套餐已经预约过了，请不要重复预约");
            }
        }
        //不存在 添加会员 t_member
        Member newMember = new Member();
        newMember.setPhoneNumber(orderInfo.get("telephone"));
        newMember.setSex(orderInfo.get("sex"));
        newMember.setName(orderInfo.get("name"));
        newMember.setRegTime(new Date());
        newMember.setIdCard(orderInfo.get("idCard"));

        memberDao.add(newMember);
        Integer memberId = newMember.getId();

        //进行预约 操作两表t_ordersetting t_order
        //t_ordersetting 表 已预约人数reservitions+1
        int affectRows = orderSettingDao.editReservationsByOrderDate(orderSetting);
        if (affectRows==0) {
            throw new HealthException("预约失败");
        }


        order.setOrderType(orderInfo.get("orderType"));
        order.setOrderStatus(Order.ORDERSTATUS_NO);
        order.setMemberId(memberId);

        orderDao.add(order);
        return order;
    }

    /**
     * 成功预约信息回显
     * @param id
     * @return
     */
    @Override
    public Map<String, Object> findOrderDetailById(int id) {
        return orderDao.findById4Detail(id);
    }
}
