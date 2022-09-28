package com.qcloud.cos.model.ciModel.xml;

import com.qcloud.cos.internal.RequestXmlFactory;
import com.qcloud.cos.internal.XmlWriter;
import com.qcloud.cos.model.ciModel.auditing.AuditingInputObject;
import com.qcloud.cos.model.ciModel.auditing.AuditingSnapshotObject;
import com.qcloud.cos.model.ciModel.auditing.Conf;
import com.qcloud.cos.model.ciModel.auditing.ReportBadCaseRequest;
import com.qcloud.cos.model.ciModel.auditing.UserInfo;
import com.qcloud.cos.model.ciModel.auditing.VideoAuditingRequest;

/**
 * 数据万象内容审核xml格式化
 */
public class CIAuditingXmlFactory {

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

    public static byte[] convertToXmlByteArray(VideoAuditingRequest request) {
        XmlWriter xml = new XmlWriter();

        xml.start("Request");
        addAuditingInput(xml, request.getInput());
        addAuditingConf(xml, request.getConf());
        xml.end();

        return xml.getBytes();
    }

    private static void addUserInfo(XmlWriter xml, UserInfo userInfo) {
        if (RequestXmlFactory.CheckObjectUtils.objIsNotValid(userInfo)) {
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
        if (RequestXmlFactory.CheckObjectUtils.objIsNotValid(inputObject)) {
            xml.start("Input");
            CIMediaXmlFactory.addIfNotNull(xml, "Object", inputObject.getObject());
            CIMediaXmlFactory.addIfNotNull(xml, "Url", inputObject.getUrl());
            CIMediaXmlFactory.addIfNotNull(xml, "DataId", inputObject.getDataId());
            addUserInfo(xml, inputObject.getUserInfo());
            xml.end();
        }
    }

    private static void addAuditingConf(XmlWriter xml, Conf conf) {
        if (RequestXmlFactory.CheckObjectUtils.objIsNotValid(conf)) {
            xml.start("Conf");
            String detectType = conf.getDetectType();
            addAuditingDetectType(xml, detectType);
            CIMediaXmlFactory.addIfNotNull(xml, "BizType", conf.getBizType());
            CIMediaXmlFactory.addIfNotNull(xml, "DetectContent", conf.getDetectContent());
            CIMediaXmlFactory.addIfNotNull(xml, "CallbackVersion", conf.getCallbackVersion());
            CIMediaXmlFactory.addIfNotNull(xml, "Callback", conf.getCallback());
            addAuditingSnapshot(xml, conf.getSnapshot());
            xml.end();
        }
    }

    private static void addAuditingSnapshot(XmlWriter xml, AuditingSnapshotObject snapshot) {
        if (RequestXmlFactory.CheckObjectUtils.objIsNotValid(snapshot)) {
            xml.start("Snapshot");
            CIMediaXmlFactory.addIfNotNull(xml, "Mode", snapshot.getMode());
            CIMediaXmlFactory.addIfNotNull(xml, "TimeInterval", snapshot.getTimeInterval());
            CIMediaXmlFactory.addIfNotNull(xml, "Count", snapshot.getCount());
            xml.end();
        }

    }

}
