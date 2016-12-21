package com.yijiajiao.oss.util;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pub.tbc.mybatis.plugin.Objs;

/**
 * @author tbc on 2016/10/19 15:41:24.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResultWrapper {
    private String code;
    private String requestId;
    private String message;
    private Object result;

    public static ResultWrapper ok(Object result) {
        if (Objs.isEmpty(result))
            return new ResultWrapper("200", null, "没数据", result);
        return new ResultWrapper("200", null, "success", result);

    }

    public static ResultWrapper bad(String code, String message) {
        return new ResultWrapper(code, null, message, null);
    }

    public static ResultWrapper bad(String message) {
        return bad("500", message);
    }
}
