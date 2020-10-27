package com.wenyang.isc.controller;

import com.alibaba.fastjson.JSONObject;
import com.wenyang.isc.service.CameraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.swing.plaf.synth.Region;

/**
 * @author wenyang
 * @date 2020/10/27
 * @description
 */
@RestController
@RequestMapping("/api/cameras")
public class CameraController {

    @Value("${isc.uri}")
    private String uri;

    @Value("${isc.previewurl}")
    private String preview_url;

    @Value("${isc.region}")
    private String region;

    @Autowired
    private CameraService cameraService;

    @GetMapping
    public String getCamera() {

        return uri + region;
    }

    @GetMapping("/node/{region}")
    public String getCameraByRegion(@PathVariable(value = "region") String region) {

        return region;
    }

    @PostMapping("/page")
    public JSONObject getCameraPage(@RequestParam("pageNo") Integer pageNo, @RequestParam("pageSize") Integer pageSize) {

        return cameraService.getCameraPage(pageNo, pageSize);
    }

    @GetMapping("/preview/{cameraIndexCode}")
    public String getPreviewURL(@PathVariable(value = "cameraIndexCode") String cameraIndexCode) {

        return cameraService.getPreviewURL(cameraIndexCode);
    }
}
