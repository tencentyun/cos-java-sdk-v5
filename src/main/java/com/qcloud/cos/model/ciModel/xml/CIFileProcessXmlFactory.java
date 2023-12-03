package com.qcloud.cos.model.ciModel.xml;

import com.qcloud.cos.internal.XmlWriter;
import com.qcloud.cos.model.ciModel.common.MediaInputObject;
import com.qcloud.cos.model.ciModel.job.CallBackMqConfig;
import com.qcloud.cos.model.ciModel.job.FileCompressConfig;
import com.qcloud.cos.model.ciModel.job.FileHashCodeConfig;
import com.qcloud.cos.model.ciModel.job.FileProcessOperation;
import com.qcloud.cos.model.ciModel.job.FileProcessRequest;
import com.qcloud.cos.model.ciModel.job.FileUnCompressConfig;

import java.util.List;

/**
 * 数据万象文件处理xml
 */
public class CIFileProcessXmlFactory {

    /**
     * 文件处理xml转换
     */
    public static byte[] convertToXmlByteArray(FileProcessRequest request) {
        XmlWriter xml = new XmlWriter();
        xml.start("Request");
        xml.start("Tag").value(request.getTag().toString()).end();
        addInput(xml, request.getInput());
        addOperation(xml, request.getOperation());
        CIMediaXmlFactory.addIfNotNull(xml, "QueueId", request.getQueueId());
        CIMediaXmlFactory.addIfNotNull(xml, "CallBack", request.getCallBack());
        CIMediaXmlFactory.addIfNotNull(xml, "CallBackFormat", request.getCallBackFormat());
        CIMediaXmlFactory.addIfNotNull(xml, "CallBackType", request.getCallBackType());
        addCallBackMqConfig(xml,request.getCallBackMqConfig());
        xml.end();
        return xml.getBytes();
    }
    private static void addCallBackMqConfig(XmlWriter xml, CallBackMqConfig callBackMqConfig) {
        if (CIMediaXmlFactory.objIsNotValid(callBackMqConfig)) {
            xml.start("CallBackMqConfig");
            xml.start("MqRegion").value(callBackMqConfig.getMqRegion()).end();
            xml.start("MqMode").value(callBackMqConfig.getMqMode()).end();
            xml.start("MqName").value(callBackMqConfig.getMqName()).end();
            xml.end();
        }
    }

    private static void addInput(XmlWriter xml, MediaInputObject input) {
        if (CIMediaXmlFactory.objIsNotValid(input)) {
            xml.start("Input");
            xml.start("Object").value(input.getObject()).end();
            xml.end();
        }
    }

    private static void addOperation(XmlWriter xml, FileProcessOperation operation) {
        if (CIMediaXmlFactory.objIsNotValid(operation)) {
            xml.start("Operation");
            CIMediaXmlFactory.addIfNotNull(xml, "UserData", operation.getUserData());
            FileCompressConfig fileCompressConfig = operation.getFileCompressConfig();
            if (CIMediaXmlFactory.objIsNotValid(fileCompressConfig)) {
                xml.start("FileCompressConfig");
                CIMediaXmlFactory.addIfNotNull(xml, "Flatten", fileCompressConfig.getFlatten());
                CIMediaXmlFactory.addIfNotNull(xml, "Format", fileCompressConfig.getFormat());
                CIMediaXmlFactory.addIfNotNull(xml, "UrlList", fileCompressConfig.getUrlList());
                CIMediaXmlFactory.addIfNotNull(xml, "Prefix", fileCompressConfig.getPrefix());
                List<String> list = fileCompressConfig.getKey();
                if (!list.isEmpty()) {
                    for (String s : list) {
                        xml.start("Key").value(s).end();
                    }
                }
                xml.end();
            } else if (CIMediaXmlFactory.objIsNotValid(operation.getFileUnCompressConfig())) {
                FileUnCompressConfig fileUnCompressConfig = operation.getFileUnCompressConfig();
                if (CIMediaXmlFactory.objIsNotValid(fileUnCompressConfig)) {
                    xml.start("FileUncompressConfig");
                    CIMediaXmlFactory.addIfNotNull(xml, "Prefix", fileUnCompressConfig.getPrefix());
                    CIMediaXmlFactory.addIfNotNull(xml, "PrefixReplaced", fileUnCompressConfig.getPrefixReplaced());
                    xml.end();
                }
            } else if (CIMediaXmlFactory.objIsNotValid(operation.getFileHashCodeConfig())) {
                FileHashCodeConfig fileHashCodeConfig = operation.getFileHashCodeConfig();
                if (CIMediaXmlFactory.objIsNotValid(fileHashCodeConfig)) {
                    xml.start("FileHashCodeConfig");
                    CIMediaXmlFactory.addIfNotNull(xml, "Type", fileHashCodeConfig.getType());
                    CIMediaXmlFactory.addIfNotNull(xml, "AddToHeader", fileHashCodeConfig.getAddToHeader());
                    xml.end();
                }
            }
            CIMediaXmlFactory.addOutput(xml, operation.getOutput());
            xml.end();
        }
    }


}
