package com.ywm;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author: YEWENMAO
 * @data: 2020/10/26 17:56
 */
public class JobApplication {
    public static void main(String[] args) throws Exception{
        ClassPathXmlApplicationContext japp = new ClassPathXmlApplicationContext("classpath:spring-jobs.xml");

        System.in.read();

    }
}
