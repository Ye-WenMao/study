package com.ywm.controller;

import com.aliyuncs.exceptions.ClientException;
import com.ywm.constant.MessageConstant;
import com.ywm.constant.RedisMessageConstant;
import com.ywm.entity.Result;
import com.ywm.utils.SMSUtils;
import com.ywm.utils.ValidateCodeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * @author: YEWENMAO
 * @data: 2020/10/29 11:39
 */

@RestController
@RequestMapping("/validateCode")
public class ValidateCodeController {

    private Logger logger = LoggerFactory.getLogger(ValidateCodeController.class);

    @Autowired
    private JedisPool jedisPool;

    @PostMapping("/send4Order")
    public Result submit(String telephone) {

        Jedis jedis = jedisPool.getResource();

        String key = RedisMessageConstant.SENDTYPE_ORDER+"_"+telephone;
        String codeInRedis = jedis.get(key);
        if (null == codeInRedis) {
            String code = String.valueOf(ValidateCodeUtils.generateValidateCode(6));
            logger.debug("code="+code);
            try {
                SMSUtils.sendShortMessage(SMSUtils.VALIDATE_CODE,telephone,code);
                jedis.setex(key, 60 * 30, code);

            } catch (ClientException e) {
                logger.error(MessageConstant.SEND_VALIDATECODE_FAIL,e);
                return new Result(false, MessageConstant.SEND_VALIDATECODE_FAIL);
            }
        }
        logger.debug("code="+jedis.get(key));
        return new Result(true, MessageConstant.SEND_VALIDATECODE_SUCCESS);
    }


    /**
     * 登录校验
     * @return
     */
    @PostMapping("/send4Login")
    public Result send4Login() {

        //未完...
        return new Result(true, MessageConstant.LOGIN_SUCCESS);
    }
}
