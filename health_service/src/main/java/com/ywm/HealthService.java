package com.ywm;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

/**
 * @author: YEWENMAO
 * @data: 2020/10/22 13:16
 */
public class HealthService {
    public static void main(String[] args) throws IOException {

        ClassPathXmlApplicationContext app =
                new ClassPathXmlApplicationContext("classpath:applicationContext-service.xml");
        System.in.read();

    }
}
