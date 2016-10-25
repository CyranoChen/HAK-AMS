package com.wonders.nlia.fqdb.Controller;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.nlia.fqdb.util.HTTPUtil;

public class FlightControllerTest {
    private final String SERVER_IP = "http://localhost:8080/fqdb";
    @Test
    public void testRefreshCache(){
        String url = SERVER_IP + "/flight/refreshCache";
        Map<String, String> params = new HashMap<String, String>();
        params.put("startDate", "2014-09-01 00:00:00");
        params.put("endDate", "2014-09-30 23:59:59");
        String result = HTTPUtil.doPost(url, params );
        System.out.println("result: " + result);
    }
}
