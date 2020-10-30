package com.wenyang.isc.utils;

import com.hikvision.artemis.sdk.ArtemisHttpUtil;
import com.hikvision.artemis.sdk.config.ArtemisConfig;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

/**
 * @author wenyang
 * @date 2020/10/28
 * @description
 */
@Slf4j
public class ArtemisUtils {

    static {
        /**
         * STEP1：设置平台参数，根据实际情况,设置host appkey appsecret 三个参数.
         */
        // artemis网关服务器ip端口
        ArtemisConfig.host = "218.58.65.133:1443";
        // 秘钥appkey
        ArtemisConfig.appKey = "24810015";
        // 秘钥appSecret
        ArtemisConfig.appSecret = "AGEyfTg2NKkp6PfEZ0F7";
    }

    public static String doPostStringArtemis(String url, String paramBody) {

        /**
         * STEP1：设置平台参数，根据实际情况,设置host appkey appsecret 三个参数.
         */
        // artemis网关服务器ip端口
        ArtemisConfig.host = "218.58.65.133:1443";
        // 秘钥appkey
        ArtemisConfig.appKey = "24810015";
        // 秘钥appSecret
        ArtemisConfig.appSecret = "AGEyfTg2NKkp6PfEZ0F7";

        /**
         * STEP2：设置OpenAPI接口的上下文
         */
        final String ARTEMIS_PATH = "/artemis";

        /**
         * STEP3：设置接口的URI地址
         */
        final String previewURLsApi = ARTEMIS_PATH + url;
        Map<String, String> path = new HashMap<String, String>(2) {
            {
                put("https://", previewURLsApi);//根据现场环境部署确认是http还是https
            }
        };

        /**
         * STEP4：设置参数提交方式
         */
        String contentType = "application/json";

        /**
         * STEP5：组装请求参数
         */

        /**
         * STEP6：调用接口
         */

        log.info("host:{}, path:{}, param:{}", ArtemisConfig.host, path, paramBody);
        return ArtemisHttpUtil.doPostStringArtemis(path, paramBody, null, null, contentType , null);
    }


}
