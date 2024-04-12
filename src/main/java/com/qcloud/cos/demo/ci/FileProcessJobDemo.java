package com.qcloud.cos.demo.ci;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.model.ciModel.common.MediaOutputObject;
import com.qcloud.cos.model.ciModel.job.*;
import com.qcloud.cos.utils.Jackson;

import java.util.List;

/**
 * 文件处理任务demo https://cloud.tencent.com/document/product/460/83091
 */
public class FileProcessJobDemo {
    public static void main(String[] args) {
        // 1 初始化用户身份信息（secretId, secretKey）。
        COSClient client = ClientUtils.getTestClient();
        // 2 调用要使用的方法。
        describeFileProcessJob(client);
    }

    /**
     * createFileCompressJob 提交文件压缩任务。
     *
     * @param client
     */
    public static void createFileCompressJob(COSClient client) {
        //1.创建任务请求对象
        FileProcessRequest request = new FileProcessRequest();
        //2.添加请求参数 参数详情请见api接口文档
        request.setBucketName("demo-1234567890");
        request.setTag(FileProcessJobType.FileCompress);
        FileCompressConfig fileCompressConfig = request.getOperation().getFileCompressConfig();
        fileCompressConfig.setFormat("zip");
        fileCompressConfig.setFlatten("0");
        fileCompressConfig.setIgnoreError("true");
        List<KeyConfig> keyConfigList = fileCompressConfig.getKeyConfigList();
        KeyConfig keyConfig = new KeyConfig();
        keyConfig.setKey("1.jpg");
        keyConfig.setRename("rename-1.jpg");
        keyConfigList.add(keyConfig);

        keyConfig = new KeyConfig();
        keyConfig.setKey("2.jpg");
        keyConfig.setRename("rename-2.jpg");
        keyConfigList.add(keyConfig);

        MediaOutputObject output = request.getOperation().getOutput();
        output.setBucket("demo-1234567890");
        output.setRegion("ap-chongqing");
        output.setObject("output/demo.zip");

        //3.调用接口,获取任务响应对象
        FileProcessJobResponse response = client.createFileProcessJob(request);
        System.out.println(response.getJobDetail().getJobId());
    }

    /**
     * createFileCompressJob 提交文件解压任务。
     *
     * @param client
     */
    public static void createFileUncompressJob(COSClient client) {
        //1.创建任务请求对象
        FileProcessRequest request = new FileProcessRequest();
        //2.添加请求参数 参数详情请见api接口文档
        request.setBucketName("demo-1234567890");
        request.setTag(FileProcessJobType.FileUncompress);
        request.getInput().setObject("output/demo.zip");
        FileUnCompressConfig fileUnCompressConfig = request.getOperation().getFileUnCompressConfig();
        fileUnCompressConfig.setPrefix("output/");
        fileUnCompressConfig.setPrefixReplaced("1");
        MediaOutputObject output = request.getOperation().getOutput();
        output.setBucket("demo-1234567890");
        output.setRegion("ap-chongqing");
        //3.调用接口,获取任务响应对象
        FileProcessJobResponse response = client.createFileProcessJob(request);
        System.out.println(response.getJobDetail().getJobId());
    }

    /**
     * createFileHashCodeJob 提交文件hash计算任务。
     *
     * @param client
     */
    public static void createFileHashCodeJob(COSClient client) {
        //1.创建任务请求对象
        FileProcessRequest request = new FileProcessRequest();
        //2.添加请求参数 参数详情请见api接口文档
        request.setBucketName("demo-1234567890");
        request.setTag(FileProcessJobType.FileHashCode);
        request.getInput().setUrl("https://demo-1234567890.cos.ap-chongqing.myqcloud.com/1.docx");
        FileHashCodeConfig fileHashCodeConfig = request.getOperation().getFileHashCodeConfig();
        fileHashCodeConfig.setType("MD5");
        fileHashCodeConfig.setAddToHeader("true");
        //3.调用接口,获取任务响应对象
        FileProcessJobResponse response = client.createFileProcessJob(request);
        System.out.println(response.getJobDetail().getJobId());
    }

    /**
     * describeFileProcessJob 根据jobId查询任务信息
     *
     * @param client
     */
    public static void describeFileProcessJob(COSClient client) {
        //1.创建任务请求对象
        FileProcessRequest request = new FileProcessRequest();
        //2.添加请求参数 参数详情请见api接口文档
        request.setBucketName("demo-1234567890");
        request.setJobId("ff96f4bacba8511ee949b8116795*****");
        //3.调用接口,获取任务响应对象
        FileProcessJobResponse response = client.describeFileProcessJob(request);
        FileProcessJobDetail jobDetail = response.getJobDetail();
        System.out.println(Jackson.toJsonString(jobDetail));
    }

    /**
     * zipPreview 该接口可以在不解压文件的情况下预览压缩包内的内容，包含文件数量、名称、文件时间等，接口为同步请求方式。
     * 支持预览的压缩包格式：zip、tar、gz、7zip、rar
     * API： https://cloud.tencent.com/document/product/460/93030
     */
    public static void zipPreview(COSClient client) {
        //1.创建任务请求对象
        ZipPreviewRequest request = new ZipPreviewRequest();
        //2.添加请求参数 参数详情请见api接口文档
        request.setBucketName("demo-1234567890");
        request.setObjectKey("filelist_encrypt.rar");
//        request.setUncompressKey("Y2l0ZXN0");
        //3.调用接口,获取任务响应对象
        ZipPreviewResponse response = client.zipPreview(request);
        System.out.println(Jackson.toJsonString(response));
    }


}
