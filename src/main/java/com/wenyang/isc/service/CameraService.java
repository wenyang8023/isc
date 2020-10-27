package com.wenyang.isc.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.wenyang.isc.utils.HttpUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author wenyang
 * @date 2020/10/27
 * @description
 */
@Service
@Slf4j
public class CameraService {

    @Value("${isc.uri}")
    private String uri;

    @Value("${isc.previewurl}")
    private String previewUrl;

    @Value("${isc.region}")
    private String region;

    @Value("${isc.camera.page}")
    private String cameraPage;


    public JSONObject getCameraPage(Integer pageNo, Integer pageSize) {

        String url = uri + cameraPage;
        Map<String, String> map = new HashMap<>();
        map.put("pageNo", pageNo.toString());
        map.put("pageSize", pageSize.toString());

        String result = null;
        try {
            result = HttpUtils.doPost(url, map);
        } catch (IOException e) {
            log.error("查询监控点列表失败,error={}", ExceptionUtils.getStackTrace(e));
            throw new RuntimeException("查询监控点列表失败！");
        }

        return getData(result);
    }

    /**
     * 根据监控点编号查询引流地址
     */
    public String getPreviewURL(String cameraIndexCode) {

        String url = uri + previewUrl;
        Map<String, String> map = new HashMap<>();
        map.put("cameraIndexCode", cameraIndexCode);

        String result = HttpUtils.doGet(url, map);

        JSONObject data = getData(result);
        return data.getString("url");
    }


    private JSONObject getData(String result) {

        JSONObject jsonObject = JSONObject.parseObject(result);
        String code = jsonObject.getString("code");
        if (!"0".equals(code)) {
            throw new RuntimeException(jsonObject.getString("msg"));
        }

        return jsonObject.getJSONObject("data");
    }

}
