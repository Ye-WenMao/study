package com.ywm.controller;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.dubbo.config.annotation.Reference;
import com.ywm.OrderService;
import com.ywm.constant.MessageConstant;
import com.ywm.constant.RedisMessageConstant;
import com.ywm.entity.Result;
import com.ywm.pojo.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.Map;

/**
 * @author: YEWENMAO
 * @data: 2020/10/29 16:43
 */

@RestController
@RequestMapping("/order")
public class OrderMobileController {


    @Autowired
    private JedisPool jedisPool;

    @Reference
    private OrderService orderService;

    /**
     * 提交预约处理
     * @param orderInfo
     * @return
     */
    @PostMapping("/submit")
    public Result submit(@RequestBody Map<String, String> orderInfo) {
        Jedis jedis = jedisPool.getResource();

        //根据telephone 获取redis中的key
        String key = RedisMessageConstant.SENDTYPE_ORDER + "_" + orderInfo.get("telephone");
        //根据key获取redis中的验证码
        String code = jedis.get(key);

        //判断code是否为空
        if (StringUtils.isEmpty(code)) {
            //为空返回数据
            return new Result(false, "请重新获取验证码");
        }

        // code不为空
        //获取页面验证码
        String validateCode = orderInfo.get("validateCode");

        // 判断code是否跟页面传过来的验证码相同
        Order order = null;
        if (code.equals(validateCode)) {
            //相同验证通过 移除redis中的验证码，防止重复提交，
            jedis.del(key);
            //设置预约类型
            orderInfo.put("orderType","微信预约");
            //掉用预约服务
            order = orderService.submit(orderInfo);
        }
        return new Result(true, MessageConstant.ORDER_SUCCESS,order);
    }

    /**
     * 成功预约信息回显
     * @param id
     * @return
     */
    @GetMapping("/findById")
    public Result findById(int id) {

        Map<String,Object> orderInfo = orderService.findOrderDetailById(id);

        return new Result(true, MessageConstant.QUERY_ORDER_SUCCESS,orderInfo);
    }
}
