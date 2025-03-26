package com.rainbowsea.rainbowsealiving.commodity.controller;


import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSException;
import com.rainbowsea.common.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

@RestController
@Slf4j
public class TestController {

    // 编写方法-上传指定的文件到指定 bucket [原生 SDK 完成文件上传到阿里云的 bucket]

    @RequestMapping("/test")
    public R testUpload() throws FileNotFoundException {
        // Endpoint以华东1（杭州）为例，其它Region请按实际情况填写。
        String endpoint = "https://oss-cn-hangzhou.aliyuncs.com";
        // 阿里云账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM用户进行API访问或日常运维，请登录RAM控制台创建RAM用户。
        //String accessKeyId = "LTAI5tP4G6hDJqh7FPe1Cahh";
        //String accessKeySecret = "vl5kaBORH1QADEzKq9NInpRdD8JJeF";
        // 填写Bucket名称，例如examplebucket。
        String bucketName = "rainbowsealiving-10000";
        // 填写Object完整路径，完整路径中不能包含Bucket名称，例如exampledir/exampleobject.txt。
        // 文件上传后的文件名
        String objectName = "2025-3-10/3-rainbowsea.jpg";
        // 填写本地文件的完整路径，例如D:\\localpath\\examplefile.txt。
        // 如果未指定本地路径，则默认从示例程序所属项目对应本地路径中上传文件流。
        // 这里指定你要上传的文件完整的路径
        String filePath = "E:\\Java\\project\\RainbowSealiving\\touxiang.jpg";

        // 创建OSSClient实例。
        //OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        try {
            InputStream inputStream = new FileInputStream(filePath);
            // 创建PutObject请求。
            ossClient.putObject(bucketName, objectName, inputStream);
        } catch (OSSException oe) {
            System.out.println("Caught an OSSException, which means your request made it to OSS, "
                    + "but was rejected with an error response for some reason.");
            System.out.println("Error Message:" + oe.getErrorMessage());
            System.out.println("Error Code:" + oe.getErrorCode());
            System.out.println("Request ID:" + oe.getRequestId());
            System.out.println("Host ID:" + oe.getHostId());
        } catch (ClientException ce) {
            System.out.println("Caught an ClientException, which means the client encountered "
                    + "a serious internal problem while trying to communicate with OSS, "
                    + "such as not being able to access the network.");
            System.out.println("Error Message:" + ce.getMessage());
        } finally {
            if (ossClient != null) {
                // 关闭 OSS 资源
                ossClient.shutdown();
            }
        }
        return null;
    }



    // 装配 OSSClient
    @Resource
    private OSSClient ossClient;

    // 上传指定的文件到 bucket 当中
    @RequestMapping("/test2")
    public R testUpload2() throws FileNotFoundException {
        InputStream inputStream = new FileInputStream("E:\\Java\\project\\RainbowSealiving\\redis.jpg");
        ossClient.putObject("rainbowsealiving-10000","redis.jpg",inputStream);
        ossClient.shutdown();
        // redis.jpg
        return R.ok("上传 OK");
    }


}
