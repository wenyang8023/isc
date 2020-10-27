package com.wenyang.isc.controller;

import com.alibaba.fastjson.JSONObject;
import com.wenyang.isc.bean.Result;
import com.wenyang.isc.service.CameraService;
import java.util.Map;
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


    @Autowired
    private CameraService cameraService;

    @GetMapping
    public String getCamera() {

        return "1";
    }

    @PostMapping("/page")
    public Result<JSONObject> getCameraPage(@RequestParam("pageNo") Integer pageNo, @RequestParam("pageSize") Integer pageSize) {

        return Result.success(cameraService.getCameraPage(pageNo, pageSize));
    }

    @PostMapping("/node/{regionIndexCode}")
    public Result<JSONObject> getRegionCamera(@PathVariable("regionIndexCode") String regionIndexCode, @RequestParam("pageNo") Integer pageNo, @RequestParam("pageSize") Integer pageSize) {

        return Result.success(cameraService.getRegionCamera(regionIndexCode, pageNo, pageSize));
    }

    @GetMapping("/preview/{cameraIndexCode}")
    public Result<String> getPreviewURL(@PathVariable(value = "cameraIndexCode") String cameraIndexCode) {

        return Result.success(cameraService.getPreviewURL(cameraIndexCode));
    }

    @GetMapping("/execute")
    public Result<Map<String, Object>> execute() {

        return Result.success(cameraService.execute());
    }
}
