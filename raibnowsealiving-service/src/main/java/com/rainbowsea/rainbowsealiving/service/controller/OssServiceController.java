package com.rainbowsea.rainbowsealiving.service.controller;


import com.aliyun.oss.OSS;
import com.aliyun.oss.common.utils.BinaryUtil;
import com.aliyun.oss.model.MatchMode;
import com.aliyun.oss.model.PolicyConditions;
import com.rainbowsea.common.utils.R;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

@RestController
public class OssServiceController {
    // 装配属性
    @Resource
    OSS ossClient;

    @Value("${spring.cloud.alicloud.access-key}")
    private String accessId;


    @Value("${spring.cloud.alicloud.oss.endpoint}")
    private String endpoint;


    @Value("${spring.cloud.alicloud.oss.bucket}")
    private String bucket;


    @RequestMapping("/oss/policy")
    public R policy() {

        String host = "https://" + bucket + "." + endpoint;
        // host的 格 式 为 // bucketname.endpoint
        // callbackUrl为 上传回调服务器的 URL，请将下面的 IP和 Port配置为您自己的真实信息。暂时不用
        // String callbackUrl = "http://88.88.88.88:8888";
        // 我们可以将文件按照 年-月-日的形式分目录存放在阿里云[以后优化]
        // 得到 dir
        // java 基础的日期 -SimpleDateFormat
        String format = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        String dir = format + "/"; //用户上传文件时指定的前缀。
        Map<String, String> respMap = null;
        try {
            long expireTime = 30;  // 默认超时时间 30s
            long expireEndTime = System.currentTimeMillis() + expireTime * 1000;
            Date expiration = new Date(expireEndTime);
            PolicyConditions policyConds = new PolicyConditions();
            policyConds.addConditionItem(PolicyConditions.COND_CONTENT_LENGTH_RANGE, 0,
                    1048576000);
            policyConds.addConditionItem(MatchMode.StartWith,
                    PolicyConditions.COND_KEY, dir);
            String postPolicy = ossClient.generatePostPolicy(expiration, policyConds);
            byte[] binaryData = postPolicy.getBytes("utf-8");
            String encodedPolicy = BinaryUtil.toBase64String(binaryData);
            String postSignature = ossClient.calculatePostSignature(postPolicy);
            respMap = new LinkedHashMap<String, String>();
            respMap.put("accessid", accessId);
            respMap.put("policy", encodedPolicy);
            respMap.put("signature", postSignature);
            respMap.put("dir", dir);
            respMap.put("host", host);
            respMap.put("expire", String.valueOf(expireEndTime / 1000));
            // respMap.put("expire", formatISO8601Date(expiration));
        } catch (Exception e) {
            // Assert.fail(e.getMessage());
            System.out.println(e.getMessage());
        }
        return R.ok().put("data", respMap);
    }

}
