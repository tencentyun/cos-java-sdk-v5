package com.qcloud.cos.demo;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.concurrent.atomic.AtomicBoolean;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.model.CSVInput;
import com.qcloud.cos.model.CSVOutput;
import com.qcloud.cos.model.CompressionType;
import com.qcloud.cos.model.ExpressionType;
import com.qcloud.cos.model.InputSerialization;
import com.qcloud.cos.model.JSONInput;
import com.qcloud.cos.model.JSONOutput;
import com.qcloud.cos.model.JSONType;
import com.qcloud.cos.model.OutputSerialization;
import com.qcloud.cos.model.SelectObjectContentEvent;
import com.qcloud.cos.model.SelectObjectContentEventVisitor;
import com.qcloud.cos.model.SelectObjectContentRequest;
import com.qcloud.cos.model.SelectObjectContentResult;

public class SelectObjectContentDemo {
    public static void main(String[] args) throws Exception {
        selectCsvContentDemo();
        selectJsonContentDemo();
    }

    public static void selectCsvContentDemo() throws Exception {
        // 1 初始化用户身份信息(secretId, secretKey)
        COSCredentials cred = new BasicCOSCredentials("AKIDXXXXXXXX", "1A2Z3YYYYYYYYYY");

        ClientConfig clientConfig = new ClientConfig();

        // 2 设置 bucket 的域名, bucket 对应的 COS 地域的简称请参照 https://www.qcloud.com/document/product/436/6224
        String region = "ap-guangzhou";
        // 如果是公网环境
        clientConfig.setEndpoint(String.format("cos.%s.tencentcos.cn", region));
        // 如果是腾讯云内网环境
        clientConfig.setEndpoint(String.format("cos-internal.%s.tencentcos.cn", region));

        // 生成cos客户端
        COSClient cosclient = new COSClient(cred, clientConfig);
        String key = "test/my_test.csv";
        String bucketName = "mybucket-1251668577";
        String csvContent = "HuNan,ChangSha\nSiChuan,ChengDu\nGuiZhou,GuiYang\n";
        cosclient.putObject(bucketName, key, csvContent);
        String query = "select s._1 from COSObject s";

        SelectObjectContentRequest request = new SelectObjectContentRequest();
        request.setBucketName(bucketName);
        request.setKey(key);
        request.setExpression(query);
        request.setExpressionType(ExpressionType.SQL);

        InputSerialization inputSerialization = new InputSerialization();
        CSVInput csvInput = new CSVInput();
        csvInput.setFieldDelimiter(",");
        csvInput.setRecordDelimiter("\n");
        inputSerialization.setCsv(csvInput);
        inputSerialization.setCompressionType(CompressionType.NONE);
        request.setInputSerialization(inputSerialization);

        OutputSerialization outputSerialization = new OutputSerialization();
        outputSerialization.setCsv(new CSVOutput());
        request.setOutputSerialization(outputSerialization);

        final AtomicBoolean isResultComplete = new AtomicBoolean(false);
        SelectObjectContentResult result = cosclient.selectObjectContent(request);
        InputStream resultInputStream = result.getPayload().getRecordsInputStream(
                new SelectObjectContentEventVisitor() {
                    @Override
                    public void visit(SelectObjectContentEvent.StatsEvent event)
                    {
                        System.out.println(
                                "Received Stats, Bytes Scanned: " + event.getDetails().getBytesScanned()
                                        +  " Bytes Processed: " + event.getDetails().getBytesProcessed());
                    }
                    @Override
                    public void visit(SelectObjectContentEvent.EndEvent event)
                    {
                        isResultComplete.set(true);
                        System.out.println("Received End Event. Result is complete.");
                    }
                }
        );
        BufferedReader reader = new BufferedReader(new InputStreamReader(resultInputStream));
        StringBuffer stringBuffer = new StringBuffer();
        String line;
        while((line = reader.readLine())!= null){
            stringBuffer.append(line).append("\n");
        }
        System.out.println(stringBuffer.toString());
        // 检查结果是否接受完整
        if (!isResultComplete.get()) {
            throw new Exception("result was incomplete");
        }
    }

    public static void selectJsonContentDemo() throws Exception {
        // 1 初始化用户身份信息(secretId, secretKey)
        COSCredentials cred = new BasicCOSCredentials("AKIDXXXXXXXX", "1A2Z3YYYYYYYYYY");

        ClientConfig clientConfig = new ClientConfig();

        // 2 设置 bucket 的域名, bucket 对应的 COS 地域的简称请参照 https://www.qcloud.com/document/product/436/6224
        String region = "ap-guangzhou";
        // 如果是公网环境
        clientConfig.setEndpoint(String.format("cos.%s.tencentcos.cn", region));
        // 如果是腾讯云内网环境
        clientConfig.setEndpoint(String.format("cos-internal.%s.tencentcos.cn", region));

        // 生成cos客户端
        COSClient cosclient = new COSClient(cred, clientConfig);
        String key = "test/my_test.json";
        String bucketName = "mybucket-1251668577";
        String csvContent = "{\"name\":\"xiaoming\",\"mathScore\":89,\"musicScore\":92}\n" +
                "{\"name\":\"xiaowang\",\"mathScore\":93,\"musicScore\":85}\n" +
                "{\"name\":\"xiaoli\",\"mathScore\":82,\"musicScore\":95}\n";
        cosclient.putObject(bucketName, key, csvContent);
        String query = "select * from COSObject s where mathScore > 85'";

        SelectObjectContentRequest request = new SelectObjectContentRequest();
        request.setBucketName(bucketName);
        request.setKey(key);
        request.setExpression(query);
        request.setExpressionType(ExpressionType.SQL);

        InputSerialization inputSerialization = new InputSerialization();
        JSONInput jsonInput = new JSONInput();
        jsonInput.setType(JSONType.LINES);
        inputSerialization.setJson(jsonInput);
        inputSerialization.setCompressionType(CompressionType.NONE);
        request.setInputSerialization(inputSerialization);

        OutputSerialization outputSerialization = new OutputSerialization();
        outputSerialization.setJson(new JSONOutput());
        request.setOutputSerialization(outputSerialization);

        final AtomicBoolean isResultComplete = new AtomicBoolean(false);
        SelectObjectContentResult result = cosclient.selectObjectContent(request);
        InputStream resultInputStream = result.getPayload().getRecordsInputStream(
                new SelectObjectContentEventVisitor() {
                    @Override
                    public void visit(SelectObjectContentEvent.StatsEvent event)
                    {
                        System.out.println(
                                "Received Stats, Bytes Scanned: " + event.getDetails().getBytesScanned()
                                        +  " Bytes Processed: " + event.getDetails().getBytesProcessed());
                    }
                    @Override
                    public void visit(SelectObjectContentEvent.EndEvent event)
                    {
                        isResultComplete.set(true);
                        System.out.println("Received End Event. Result is complete.");
                    }
                }
        );
        BufferedReader reader = new BufferedReader(new InputStreamReader(resultInputStream));
        StringBuffer stringBuffer = new StringBuffer();
        String line;
        while((line = reader.readLine())!= null){
            stringBuffer.append(line).append("\n");
        }
        System.out.println(stringBuffer.toString());
        // 检查结果是否接受完整
        if (!isResultComplete.get()) {
            throw new Exception("result was incomplete");
        }
    }
}
