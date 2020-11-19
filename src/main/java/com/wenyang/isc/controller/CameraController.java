package com.wenyang.isc.controller;

import com.alibaba.fastjson.JSONObject;
import com.wenyang.isc.bean.Result;
import com.wenyang.isc.service.CameraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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

    @GetMapping("/region/page")
    public Result<JSONObject> getRegionPage(@RequestParam("pageNo") Integer pageNo, @RequestParam("pageSize") Integer pageSize) {

        return Result.success(cameraService.getRegionPage(pageNo, pageSize));
    }

    @GetMapping("/region/sub/{parentIndexCode}")
    public Result<JSONObject> getRegionPage(@PathVariable(value = "parentIndexCode") String parentIndexCode) {

        return Result.success(cameraService.getSubRegions(parentIndexCode));
    }

    @GetMapping("/preview/{cameraIndexCode}")
    public Result<String> getPreviewURL(@PathVariable(value = "cameraIndexCode") String cameraIndexCode) {

        return Result.success(cameraService.getPreviewURL(cameraIndexCode));
    }

    @GetMapping("/execute")
    public Result<List<JSONObject>> execute() {

        return Result.success(cameraService.execute());
    }

    @GetMapping("/execute/region/{region}")
    public Result<Map<String, Object>> execute(@PathVariable("region") String region) {

        return Result.success(cameraService.execute(region));
    }
}
