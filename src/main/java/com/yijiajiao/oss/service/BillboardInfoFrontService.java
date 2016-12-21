package com.yijiajiao.oss.service;

import java.util.List;
import java.util.Map;


public interface BillboardInfoFrontService {

    List<Map<String, Object>> showbill(String area);

    List<Map<String, Object>> getid(String area);

}
