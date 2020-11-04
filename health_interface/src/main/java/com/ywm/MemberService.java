package com.ywm;

import java.util.List;

/**
 * @author: YEWENMAO
 * @data: 2020/11/1 19:09
 */
public interface MemberService {

    //统计过去一年的会员总数
    List<Integer> getMemberReport(List<String> months);
}
