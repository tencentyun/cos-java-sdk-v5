package com.qcloud.cos.model.ciModel.xml;

import com.qcloud.cos.internal.XmlWriter;
import com.qcloud.cos.model.ciModel.auditing.AudioAuditingRequest;
import com.qcloud.cos.model.ciModel.auditing.AuditingInputObject;
import com.qcloud.cos.model.ciModel.auditing.AuditingSnapshotObject;
import com.qcloud.cos.model.ciModel.auditing.BatchImageAuditingInputObject;
import com.qcloud.cos.model.ciModel.auditing.BatchImageAuditingRequest;
import com.qcloud.cos.model.ciModel.auditing.Conf;
import com.qcloud.cos.model.ciModel.auditing.DocumentAuditingRequest;
import com.qcloud.cos.model.ciModel.auditing.Encryption;
import com.qcloud.cos.model.ciModel.auditing.Freeze;
import com.qcloud.cos.model.ciModel.auditing.ReportBadCaseRequest;
import com.qcloud.cos.model.ciModel.auditing.TextAuditingRequest;
import com.qcloud.cos.model.ciModel.auditing.UserInfo;
import com.qcloud.cos.model.ciModel.auditing.VideoAuditingRequest;
import com.qcloud.cos.model.ciModel.auditing.WebpageAuditingRequest;

import java.util.List;

/**
 * 数据万象内容审核xml格式化
 */
public class CIAuditingXmlFactory {

    /**
     * 批量图片处理xml
     */
    public static byte[] convertToXmlByteArray(BatchImageAuditingRequest request) {
        XmlWriter xml = new XmlWriter();

        xml.start("Request");
        List<BatchImageAuditingInputObject> inputList = request.getInputList();
        for (BatchImageAuditingInputObject inputObject : inputList) {
            xml.start("Input");
            CIMediaXmlFactory.addIfNotNull(xml, "Url", inputObject.getUrl());
            CIMediaXmlFactory.addIfNotNull(xml, "Object", inputObject.getObject());
            CIMediaXmlFactory.addIfNotNull(xml, "DataId", inputObject.getDataId());
            CIMediaXmlFactory.addIfNotNull(xml, "MaxFrames", inputObject.getMaxFrames());
            CIMediaXmlFactory.addIfNotNull(xml, "Interval", inputObject.getInterval());
            CIMediaXmlFactory.addIfNotNull(xml, "LargeImageDetect", inputObject.getLargeImageDetect());
            CIMediaXmlFactory.addIfNotNull(xml, "Content", inputObject.getContent());
            addUserInfo(xml, inputObject.getUserInfo());
            addEncryption(xml, inputObject.getEncryption());
            xml.end();
        }

        addAuditingConf(xml, request.getConf());
        xml.end();
        return xml.getBytes();
    }

    /**
     * 文本审核处理xml
     */
    public static byte[] convertToXmlByteArray(TextAuditingRequest request) {
        XmlWriter xml = new XmlWriter();

        xml.start("Request");
        addAuditingInput(xml, request.getInput());
        addAuditingConf(xml, request.getConf());
        xml.end();

        return xml.getBytes();
    }

    /**
     * 文档审核处理xml
     */
    public static byte[] convertToXmlByteArray(DocumentAuditingRequest request) {
        XmlWriter xml = new XmlWriter();

        xml.start("Request");
        xml.start("Input");
        CIMediaXmlFactory.addIfNotNull(xml, "Url", request.getInput().getUrl());
        CIMediaXmlFactory.addIfNotNull(xml, "Object", request.getInput().getObject());
        CIMediaXmlFactory.addIfNotNull(xml, "Type", request.getInput().getType());
        CIMediaXmlFactory.addIfNotNull(xml, "DataId", request.getInput().getDataId());
        addUserInfo(xml, request.getInput().getUserInfo());
        xml.end();
        addAuditingConf(xml, request.getConf());

        xml.end();
        return xml.getBytes();
    }

    /**
     * 音频审核处理xml
     */
    public static byte[] convertToXmlByteArray(AudioAuditingRequest request) {
        XmlWriter xml = new XmlWriter();

        xml.start("Request");
        addAuditingInput(xml, request.getInput());
        addAuditingConf(xml, request.getConf());
        xml.end();

        return xml.getBytes();
    }

    /**
     * 文本审核结果反馈
     */
    public static byte[] convertToXmlByteArray(ReportBadCaseRequest request) {
        XmlWriter xml = new XmlWriter();
        xml.start("Request");
        CIMediaXmlFactory.addIfNotNull(xml, "ContentType", request.getContentType());
        CIMediaXmlFactory.addIfNotNull(xml, "Text", request.getText());
        CIMediaXmlFactory.addIfNotNull(xml, "Label", request.getLabel());
        CIMediaXmlFactory.addIfNotNull(xml, "SuggestedLabel", request.getSuggestedLabel());
        CIMediaXmlFactory.addIfNotNull(xml, "JobId", request.getJobId());
        CIMediaXmlFactory.addIfNotNull(xml, "ModerationTime", request.getModerationTime());
        xml.end();
        return xml.getBytes();
    }

    /**
     * 视频审核请求
     */
    public static byte[] convertToXmlByteArray(VideoAuditingRequest request) {
        XmlWriter xml = new XmlWriter();

        xml.start("Request");
        addAuditingInput(xml, request.getInput());
        addAuditingConf(xml, request.getConf());
        xml.end();

        return xml.getBytes();
    }

    public static byte[] convertToXmlByteArray(WebpageAuditingRequest request) {
        XmlWriter xml = new XmlWriter();

        xml.start("Request");
        addAuditingInput(xml, request.getInput());
        addAuditingConf(xml, request.getConf());
        xml.end();
        return xml.getBytes();
    }

    private static void addUserInfo(XmlWriter xml, UserInfo userInfo) {
        if (CIMediaXmlFactory.objIsNotValid(userInfo)) {
            xml.start("UserInfo");
            CIMediaXmlFactory.addIfNotNull(xml, "TokenId", userInfo.getTokenId());
            CIMediaXmlFactory.addIfNotNull(xml, "Nickname", userInfo.getNickname());
            CIMediaXmlFactory.addIfNotNull(xml, "DeviceId", userInfo.getDeviceId());
            CIMediaXmlFactory.addIfNotNull(xml, "AppId", userInfo.getAppId());
            CIMediaXmlFactory.addIfNotNull(xml, "Room", userInfo.getRoom());
            CIMediaXmlFactory.addIfNotNull(xml, "IP", userInfo.getIp());
            CIMediaXmlFactory.addIfNotNull(xml, "Type", userInfo.getType());
            CIMediaXmlFactory.addIfNotNull(xml, "ReceiveTokenId", userInfo.getReceiveTokenId());
            CIMediaXmlFactory.addIfNotNull(xml, "Gender", userInfo.getGender());
            CIMediaXmlFactory.addIfNotNull(xml, "Level", userInfo.getLevel());
            CIMediaXmlFactory.addIfNotNull(xml, "Role", userInfo.getRole());
            xml.end();
        }
    }

    private static void addAuditingDetectType(XmlWriter xml, String detectType) {
        if (!"all".equalsIgnoreCase(detectType)) {
            CIMediaXmlFactory.addIfNotNull(xml, "DetectType", detectType);
        }
    }

    private static void addAuditingInput(XmlWriter xml, AuditingInputObject inputObject) {
        if (CIMediaXmlFactory.objIsNotValid(inputObject)) {
            xml.start("Input");
            CIMediaXmlFactory.addIfNotNull(xml, "Object", inputObject.getObject());
            CIMediaXmlFactory.addIfNotNull(xml, "Url", inputObject.getUrl());
            CIMediaXmlFactory.addIfNotNull(xml, "DataId", inputObject.getDataId());
            CIMediaXmlFactory.addIfNotNull(xml, "Content", inputObject.getContent());
            addUserInfo(xml, inputObject.getUserInfo());
            addEncryption(xml, inputObject.getEncryption());
            xml.end();
        }
    }

    private static void addEncryption(XmlWriter xml, Encryption encryption) {
        if (CIMediaXmlFactory.objIsNotValid(encryption)) {
            xml.start("Encryption");
            CIMediaXmlFactory.addIfNotNull(xml, "Algorithm", encryption.getAlgorithm());
            CIMediaXmlFactory.addIfNotNull(xml, "IV", encryption.getIV());
            CIMediaXmlFactory.addIfNotNull(xml, "Key", encryption.getKey());
            CIMediaXmlFactory.addIfNotNull(xml, "KeyId", encryption.getKeyId());
            CIMediaXmlFactory.addIfNotNull(xml, "KeyType", encryption.getKeyType());
            xml.end();
        }
    }

    private static void addAuditingConf(XmlWriter xml, Conf conf) {
        if (CIMediaXmlFactory.objIsNotValid(conf)) {
            xml.start("Conf");
            String detectType = conf.getDetectType();
            addAuditingDetectType(xml, detectType);
            CIMediaXmlFactory.addIfNotNull(xml, "BizType", conf.getBizType());
            CIMediaXmlFactory.addIfNotNull(xml, "DetectContent", conf.getDetectContent());
            CIMediaXmlFactory.addIfNotNull(xml, "CallbackVersion", conf.getCallbackVersion());
            CIMediaXmlFactory.addIfNotNull(xml, "Callback", conf.getCallback());
            CIMediaXmlFactory.addIfNotNull(xml, "ReturnHighlightHtml", conf.getReturnHighlightHtml());
            CIMediaXmlFactory.addIfNotNull(xml, "Async", conf.getAsync());
            CIMediaXmlFactory.addIfNotNull(xml, "CallbackType", conf.getCallbackType());

            addAuditingSnapshot(xml, conf.getSnapshot());
            addFreeze(xml, conf.getFreeze());
            xml.end();
        }
    }

    private static void addFreeze(XmlWriter xml, Freeze freeze) {
        if (CIMediaXmlFactory.objIsNotValid(freeze)) {
            xml.start("Freeze");
            CIMediaXmlFactory.addIfNotNull(xml, "PornScore", freeze.getPornScore());
            CIMediaXmlFactory.addIfNotNull(xml, "AdsScore", freeze.getAdsScore());
            CIMediaXmlFactory.addIfNotNull(xml, "TerrorismScore", freeze.getTerrorismScore());
            CIMediaXmlFactory.addIfNotNull(xml, "PoliticsScore", freeze.getPoliticsScore());
            xml.end();
        }
    }

    private static void addAuditingSnapshot(XmlWriter xml, AuditingSnapshotObject snapshot) {
        if (CIMediaXmlFactory.objIsNotValid(snapshot)) {
            xml.start("Snapshot");
            CIMediaXmlFactory.addIfNotNull(xml, "Mode", snapshot.getMode());
            CIMediaXmlFactory.addIfNotNull(xml, "TimeInterval", snapshot.getTimeInterval());
            CIMediaXmlFactory.addIfNotNull(xml, "Count", snapshot.getCount());
            xml.end();
        }

    }

}
