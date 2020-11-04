package com.ywm.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.ywm.MemberService;
import com.ywm.dao.MemberDao;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: YEWENMAO
 * @data: 2020/11/1 19:10
 */

@Service(interfaceClass = MemberService.class)
public class MemberServiceImpl implements MemberService {

    @Autowired
    private MemberDao memberDao;

    /**
     * 统计过去一年的会员总数
     * @param months
     * @return
     */
    @Override
    public List<Integer> getMemberReport(List<String> months) {

        List<Integer> memberCount = new ArrayList<>();

        if (null != months) {

            for (String month : months) {
                Integer count = memberDao.findMemberCountBeforeDate(month);
                memberCount.add(count);
            }
        }
        return memberCount;
    }
}
