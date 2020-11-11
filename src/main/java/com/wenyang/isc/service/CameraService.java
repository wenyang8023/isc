package com.wenyang.isc.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.wenyang.isc.utils.ArtemisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author wenyang
 * @date 2020/10/27
 */
@Service
@Slf4j
public class CameraService {

    @Value("${isc.uri}")
    private String uri;

    @Value("${isc.url.preview}")
    private String previewUrl;

    @Value("${isc.url.region}")
    private String region;

    @Value("${isc.url.sub_region}")
    private String subRegion;

    @Value("${isc.url.region_camera}")
    private String regionCamera;

    @Value("${isc.url.page_camera}")
    private String cameraPage;

    /** 青岛城管 市南区 */
    @Value("${isc.region.code.snq}")
    private String snq;

    /**
     * 默认分页
     */
    final private static Integer PAGE_NO = 1;

    /**
     * 默认分页数量
     */
    final private static Integer PAGE_SIZE = 100;

    public Map<String, Object> execute() {

        Map<String, Object> resultMap = new HashMap<>();

        JSONObject wsgcCamera = getRegionCamera(snq, PAGE_NO, PAGE_SIZE);
        String total = wsgcCamera.getString("total");
        JSONArray list = wsgcCamera.getJSONArray("list");
        for (int i = 0; i < list.size(); i++) {
            JSONObject jsonObject = list.getJSONObject(i);
            String cameraIndexCode = jsonObject.getString("cameraIndexCode");
            String cameraName = jsonObject.getString("name");
            if (cameraName.contains("栈桥") || cameraName.contains("五四广场")) {
                resultMap.put(cameraIndexCode + "_" + cameraName, getPreviewURL(cameraIndexCode));
            }
        }
        return resultMap;
    }

    public Map<String, Object> execute(String region) {

        Map<String, Object> resultMap = new HashMap<>();

        JSONObject wsgcCamera = getRegionCamera(region, PAGE_NO, PAGE_SIZE);
        String total = wsgcCamera.getString("total");
        JSONArray list = wsgcCamera.getJSONArray("list");
        for (int i = 0; i < list.size(); i++) {
            JSONObject jsonObject = list.getJSONObject(i);
            String cameraIndexCode = jsonObject.getString("cameraIndexCode");
            String cameraName = jsonObject.getString("name");
            resultMap.put(cameraIndexCode + "_" + cameraName, getPreviewURL(cameraIndexCode));
        }
        return resultMap;
    }

    /**
     * 查询区域列表
     */
    public JSONObject getRegionPage(Integer pageNo, Integer pageSize) {

        JSONObject jsonBody = new JSONObject();
        jsonBody.put("resourceType", "region");
        jsonBody.put("treeCode", "0");
        jsonBody.put("pageNo", pageNo);
        jsonBody.put("pageSize", pageSize);
        String body = jsonBody.toJSONString();

        String result = ArtemisUtils.doPostStringArtemis(region, body);
        return getData(result);
    }

    /**
     * 根据区域编号获取下一级区域列表
     */
    public JSONObject getSubRegions(String parentIndexCode) {

        JSONObject jsonBody = new JSONObject();
        jsonBody.put("parentIndexCode", parentIndexCode);
        jsonBody.put("treeCode", "0");
        String body = jsonBody.toJSONString();

        String result = ArtemisUtils.doPostStringArtemis(subRegion, body);
        return getData(result);
    }


    /**
     * 分页查询监控点列表
     */
    public JSONObject getCameraPage(Integer pageNo, Integer pageSize) {

        JSONObject jsonBody = new JSONObject();
        jsonBody.put("pageNo", pageNo);
        jsonBody.put("pageSize", pageSize);
        String body = jsonBody.toJSONString();

        String result = ArtemisUtils.doPostStringArtemis(cameraPage, body);
        return getData(result);
    }

    /**
     * 根据区域编号获取下级监控点列表
     */
    public JSONObject getRegionCamera(String regionIndexCode, Integer pageNo, Integer pageSize) {

        JSONObject jsonBody = new JSONObject();
        jsonBody.put("regionIndexCode", regionIndexCode);
        jsonBody.put("pageNo", pageNo);
        jsonBody.put("pageSize", pageSize);
        String body = jsonBody.toJSONString();

        String result = ArtemisUtils.doPostStringArtemis(regionCamera, body);
        return getData(result);
    }


    /**
     * 根据监控点编号查询引流地址
     */
    public String getPreviewURL(String cameraIndexCode) {

        JSONObject jsonBody = new JSONObject();
        jsonBody.put("cameraIndexCode", cameraIndexCode);
        jsonBody.put("streamType", 0);
        jsonBody.put("protocol", "rtsp");
        jsonBody.put("transmode", 1);
        jsonBody.put("expand", "streamform=rtp");
        String body = jsonBody.toJSONString();

        String result = ArtemisUtils.doPostStringArtemis(previewUrl, body);

        JSONObject jsonObject = JSONObject.parseObject(result);
        String code = jsonObject.getString("code");
        if (!"0".equals(code)) {
            return jsonObject.getString("msg");
        }

        JSONObject data = jsonObject.getJSONObject("data");
        return data.getString("url");
    }

    /**
     * 解析出参
     */
    private JSONObject getData(String result) {

        JSONObject jsonObject = JSONObject.parseObject(result);
        String code = jsonObject.getString("code");
        if (!"0".equals(code)) {
            throw new RuntimeException(jsonObject.getString("msg"));
        }

        return jsonObject.getJSONObject("data");
    }

}
