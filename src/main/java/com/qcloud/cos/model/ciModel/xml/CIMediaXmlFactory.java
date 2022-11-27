package com.qcloud.cos.model.ciModel.xml;

import com.qcloud.cos.internal.RequestXmlFactory;
import com.qcloud.cos.internal.XmlWriter;
import com.qcloud.cos.model.ciModel.common.MediaInputObject;
import com.qcloud.cos.model.ciModel.common.MediaOutputObject;
import com.qcloud.cos.model.ciModel.job.ExtractDigitalWatermark;
import com.qcloud.cos.model.ciModel.job.MediaAudioMixObject;
import com.qcloud.cos.model.ciModel.job.MediaAudioObject;
import com.qcloud.cos.model.ciModel.job.MediaConcatFragmentObject;
import com.qcloud.cos.model.ciModel.job.MediaConcatTemplateObject;
import com.qcloud.cos.model.ciModel.job.MediaContainerObject;
import com.qcloud.cos.model.ciModel.job.MediaDigitalWatermark;
import com.qcloud.cos.model.ciModel.job.MediaJobOperation;
import com.qcloud.cos.model.ciModel.job.MediaJobsRequest;
import com.qcloud.cos.model.ciModel.job.MediaPicProcessTemplateObject;
import com.qcloud.cos.model.ciModel.job.MediaRemoveWaterMark;
import com.qcloud.cos.model.ciModel.job.MediaTimeIntervalObject;
import com.qcloud.cos.model.ciModel.job.MediaTransConfigObject;
import com.qcloud.cos.model.ciModel.job.MediaTranscodeObject;
import com.qcloud.cos.model.ciModel.job.MediaTranscodeVideoObject;
import com.qcloud.cos.model.ciModel.job.MediaVideoObject;
import com.qcloud.cos.model.ciModel.job.VideoTargetRec;
import com.qcloud.cos.model.ciModel.template.MediaHlsEncryptObject;
import com.qcloud.cos.model.ciModel.template.MediaSegmentObject;
import com.qcloud.cos.model.ciModel.template.MediaSmartCoverObject;
import com.qcloud.cos.model.ciModel.template.MediaSnapshotObject;
import com.qcloud.cos.model.ciModel.template.MediaTemplateRequest;
import com.qcloud.cos.model.ciModel.template.MediaVideoMontageObject;
import com.qcloud.cos.model.ciModel.template.MediaWaterMarkImage;
import com.qcloud.cos.model.ciModel.template.MediaWaterMarkText;
import com.qcloud.cos.model.ciModel.template.MediaWatermark;
import com.qcloud.cos.model.ciModel.template.SpriteSnapshotConfig;
import com.qcloud.cos.utils.StringUtils;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 数据万象媒体处理xml格式化
 */
public class CIMediaXmlFactory {

    static void addIfNotNull(XmlWriter xml, String xmlTag, String value) {
        if (value != null) {
            xml.start(xmlTag).value(value).end();
        }
    }

    static void addIfNotNull(XmlWriter xml, String xmlTag, Object value) {
        if (value != null && value.toString() != null) {
            xml.start(xmlTag).value(value.toString()).end();
        }
    }

    /**
     * 媒体任务xml转换
     */
    public static byte[] convertToXmlByteArray(MediaJobsRequest request) {
        XmlWriter xml = new XmlWriter();
        xml.start("Request");
        addCommonParams(xml, request);
        addInput(xml, request.getInput());
        addOperation(xml, request);
        xml.end();
        return xml.getBytes();
    }

    /**
     * 模板任务xml转换
     */
    public static byte[] convertToXmlByteArray(MediaTemplateRequest request) {
        XmlWriter xml = new XmlWriter();
        String tag = request.getTag();
        xml.start("Request");
        xml.start("Tag").value(tag).end();
        xml.start("Name").value(request.getName()).end();

        if ("Animation".equalsIgnoreCase(tag)) {
            xml.start("Container");
            xml.start("Format").value(request.getContainer().getFormat()).end();
            xml.end();
            addVideo(xml, request.getVideo());
            if (objIsNotValid(request.getTimeInterval())) {
                xml.start("TimeInterval");
                xml.start("Duration").value(request.getTimeInterval().getDuration()).end();
                xml.start("Start").value(request.getTimeInterval().getStart()).end();
                xml.end();
            }
        } else if ("Snapshot".equalsIgnoreCase(tag)) {
            MediaSnapshotObject snapshot = request.getSnapshot();
            addSnapshot(xml, snapshot);
        } else if ("Watermark".equalsIgnoreCase(tag)) {
            xml.start("Watermark");
            addIfNotNull(xml, "Type", request.getWatermark().getType());
            addIfNotNull(xml, "Dx", request.getWatermark().getDx());
            addIfNotNull(xml, "Dy", request.getWatermark().getDy());
            addIfNotNull(xml, "EndTime", request.getWatermark().getEndTime());
            addIfNotNull(xml, "LocMode", request.getWatermark().getLocMode());
            addIfNotNull(xml, "Pos", request.getWatermark().getPos());
            addIfNotNull(xml, "StartTime", request.getWatermark().getStartTime());
            if ("Text".equalsIgnoreCase(request.getWatermark().getType())) {
                xml.start("Text");
                MediaWaterMarkText text = request.getWatermark().getText();
                addIfNotNull(xml, "FontColor", text.getFontColor());
                addIfNotNull(xml, "FontSize", text.getFontSize());
                addIfNotNull(xml, "FontType", text.getFontType());
                addIfNotNull(xml, "Text", text.getText());
                addIfNotNull(xml, "Transparency", text.getTransparency());
                xml.end();
            } else if ("Image".equalsIgnoreCase(request.getWatermark().getType())) {
                xml.start("Image");
                MediaWaterMarkImage image = request.getWatermark().getImage();
                addIfNotNull(xml, "Height", image.getHeight());
                addIfNotNull(xml, "Mode", image.getMode());
                addIfNotNull(xml, "Url", image.getUrl());
                addIfNotNull(xml, "Transparency", image.getTransparency());
                addIfNotNull(xml, "Width", image.getWidth());
                addIfNotNull(xml, "Background", image.getBackground());
                xml.end();
            }
            xml.end();
        } else if ("Transcode".equalsIgnoreCase(tag)) {
            addFormat(xml, request.getContainer());
            addTimeInterval(xml, request.getTimeInterval());
            addAudio(xml, request.getAudio());
            addVideo(xml, request.getVideo());
            addTransConfig(xml, request.getTransConfig());
        } else if ("Concat".equalsIgnoreCase(tag)) {
            addConcat(xml, request.getConcat());
            addAudio(xml, request.getAudio());
            addAudioMix(xml, request.getAudioMix());
            addContainer(xml, request.getContainer());
        } else if ("VideoTargetRec".equalsIgnoreCase(tag)) {
            VideoTargetRec videoTargetRec = request.getVideoTargetRec();
            addVideoTargetRec(xml, videoTargetRec);
        }
        xml.end();
        System.out.println(xml);
        return xml.getBytes();
    }

    private static void addOperation(XmlWriter xml, MediaJobsRequest request) {
        MediaJobOperation operation = request.getOperation();
        xml.start("Operation");

        addIfNotNull(xml, "TemplateId", operation.getTemplateId());
        addIfNotNull(xml, "JobLevel", operation.getJobLevel());
        addIfNotNull(xml, "DecryptKey", operation.getDecryptKey());
        addIfNotNull(xml, "UserData", operation.getUserData());

        addWatermarkTemplateId(xml, operation.getWatermarkTemplateId());
        addWatermar(xml, operation.getWatermark());
        addWatermarList(xml, operation.getWatermarkList());
        addRemoveWatermark(xml, operation.getRemoveWatermark());
        addConcat(xml, operation.getMediaConcatTemplate());
        addTranscode(xml, operation.getTranscode());
        addExtractDigitalWatermark(xml, operation.getExtractDigitalWatermark());
        addMediaDigitalWatermark(xml, operation.getDigitalWatermark());
        addOutput(xml, operation.getOutput());
        addSnapshot(xml, operation.getSnapshot());
        addSegment(xml, operation.getSegment());
        addSmartCover(xml, operation.getSmartCover());
        addVideoMontage(xml, operation.getVideoMontage());
        addPicProcess(xml, operation.getPicProcess());
        addVideoTargetRec(xml, operation.getVideoTargetRec());
        xml.end();
    }

    private static void addPicProcess(XmlWriter xml, MediaPicProcessTemplateObject picProcess) {
        if (objIsNotValid(picProcess)) {
            xml.start("PicProcess");
            addIfNotNull(xml, "IsPicInfo", picProcess.getIsPicInfo());
            addIfNotNull(xml, "ProcessRule", picProcess.getProcessRule());
            xml.end();
        }
    }

    private static void addVideoTargetRec(XmlWriter xml, VideoTargetRec videoTargetRec) {
        if (objIsNotValid(videoTargetRec)) {
            xml.start("VideoTargetRec");
            addIfNotNull(xml, "Body", videoTargetRec.getBody());
            addIfNotNull(xml, "Car", videoTargetRec.getCar());
            addIfNotNull(xml, "Pet", videoTargetRec.getPet());
            xml.end();
        }
    }

    private static void addWatermarList(XmlWriter xml, List<MediaWatermark> watermarkList) {
        if (watermarkList != null && !watermarkList.isEmpty()) {
            for (MediaWatermark mediaWatermark : watermarkList) {
                addWatermar(xml, mediaWatermark);
            }
        }
    }

    private static void addWatermarkTemplateId(XmlWriter xml, List<String> watermarkTemplateId) {
        if (watermarkTemplateId != null && !watermarkTemplateId.isEmpty()) {
            for (String templateId : watermarkTemplateId) {
                xml.start("WatermarkTemplateId").value(templateId).end();
            }
        }
    }

    private static void addVideoMontage(XmlWriter xml, MediaVideoMontageObject videoMontage) {
        if (objIsNotValid(videoMontage)) {
            xml.start("VideoMontage");
            addIfNotNull(xml, "Duration", videoMontage.getDuration());
            addVideo(xml, videoMontage.getVideo());
            addAudio(xml, videoMontage.getAudio());
            addAudioMix(xml, videoMontage.getAudioMix());
            addContainer(xml, videoMontage.getContainer());
            xml.end();
        }
    }

    private static void addAudioMix(XmlWriter xml, MediaAudioMixObject audioMix) {
        if (objIsNotValid(audioMix)) {
            xml.start("AudioMix");
            addIfNotNull(xml, "MixMode", audioMix.getMixMode());
            addIfNotNull(xml, "AudioSource", audioMix.getAudioSource());
            addIfNotNull(xml, "Replace", audioMix.getReplace());
            addIfNotNull(xml, "EffectConfig", audioMix.getEffectConfig());
            xml.end();
        }
    }

    private static void addConcat(XmlWriter xml, MediaConcatTemplateObject mediaConcatTemplate) {
        if (objIsNotValid(mediaConcatTemplate)) {
            xml.start("ConcatTemplate");
            List<MediaConcatFragmentObject> concatFragmentList = mediaConcatTemplate.getConcatFragmentList();
            for (MediaConcatFragmentObject concatFragment : concatFragmentList) {
                xml.start("ConcatFragment");
                addIfNotNull(xml, "Mode", concatFragment.getMode());
                addIfNotNull(xml, "Url", concatFragment.getUrl());
                addIfNotNull(xml, "FragmentIndex", concatFragment.getFragmentIndex());
                addIfNotNull(xml, "StartTime", concatFragment.getStartTime());
                addIfNotNull(xml, "EndTime", concatFragment.getEndTime());
                xml.end();
            }
            addVideo(xml, mediaConcatTemplate.getVideo());
            addAudio(xml, mediaConcatTemplate.getAudio());
            addIfNotNull(xml, "Index", mediaConcatTemplate.getIndex());
            addIfNotNull(xml, "DirectConcat", mediaConcatTemplate.getDirectConcat());
            addContainer(xml, mediaConcatTemplate.getContainer());
            addAudioMix(xml, mediaConcatTemplate.getAudioMix());
            xml.end();
        }
    }

    private static void addContainer(XmlWriter xml, MediaContainerObject container) {
        if (!StringUtils.isNullOrEmpty(container.getFormat())) {
            xml.start("Container");
            xml.start("Format").value(container.getFormat()).end();
            xml.end();
        }
    }

    private static void addMediaDigitalWatermark(XmlWriter xml, MediaDigitalWatermark digitalWatermark) {
        if (objIsNotValid(digitalWatermark)) {
            xml.start("DigitalWatermark");
            addIfNotNull(xml, "Message", digitalWatermark.getMessage());
            addIfNotNull(xml, "Type", digitalWatermark.getType());
            addIfNotNull(xml, "Version", digitalWatermark.getVersion());
            xml.end();
        }
    }

    private static void addExtractDigitalWatermark(XmlWriter xml, ExtractDigitalWatermark extractDigitalWatermark) {
        if (extractDigitalWatermark.getType() != null || extractDigitalWatermark.getMessage() != null) {
            xml.start("ExtractDigitalWatermark");
            addIfNotNull(xml, "Message", extractDigitalWatermark.getMessage());
            addIfNotNull(xml, "Type", extractDigitalWatermark.getType());
            addIfNotNull(xml, "Version", extractDigitalWatermark.getVersion());
            xml.end();
        }
    }

    private static void addOutput(XmlWriter xml, MediaOutputObject output) {
        if (objIsNotValid(output)) {
            xml.start("Output");
            addIfNotNull(xml, "Region", output.getRegion());
            addIfNotNull(xml, "Object", output.getObject());
            addIfNotNull(xml, "Bucket", output.getBucket());
            addIfNotNull(xml, "SpriteObject", output.getSpriteObject());
            xml.end();
        }
    }

    private static void addSegment(XmlWriter xml, MediaSegmentObject segment) {
        if (objIsNotValid(segment)) {
            xml.start("Segment");
            addIfNotNull(xml, "Duration", segment.getDuration());
            addIfNotNull(xml, "Format", segment.getFormat());
            MediaHlsEncryptObject hlsEncrypt = segment.getHlsEncrypt();
            if (objIsNotValid(hlsEncrypt)) {
                xml.start("HlsEncrypt");
                addIfNotNull(xml, "IsHlsEncrypt", hlsEncrypt.getIsHlsEncrypt());
                addIfNotNull(xml, "UriKey", hlsEncrypt.getUriKey());
                xml.end();
            }
            xml.end();
        }
    }

    private static void addSmartCover(XmlWriter xml, MediaSmartCoverObject smartCover) {
        if (objIsNotValid(smartCover)) {
            xml.start("SmartCover");
            addIfNotNull(xml, "Format", smartCover.getFormat());
            addIfNotNull(xml, "Width", smartCover.getWidth());
            addIfNotNull(xml, "Height", smartCover.getHeight());
            addIfNotNull(xml, "Count", smartCover.getCount());
            addIfNotNull(xml, "DeleteDuplicates", smartCover.getDeleteDuplicates());
            xml.end();
        }
    }

    private static void addCommonParams(XmlWriter xml, MediaJobsRequest request) {
        if (objIsNotValid(request)) {
            xml.start("Tag").value(request.getTag()).end();
            xml.start("BucketName").value(request.getBucketName()).end();
            xml.start("QueueId").value(request.getQueueId()).end();
            addIfNotNull(xml, "CallBack", request.getCallBack());
            addIfNotNull(xml, "CallBackFormat", request.getCallBackFormat());
            addIfNotNull(xml, "CallBackType", request.getCallBackType());
        }
    }

    private static void addTranscode(XmlWriter xml, MediaTranscodeObject transcode) {
        if (objIsNotValid(transcode)) {
            xml.start("Transcode");
            MediaTranscodeVideoObject video = transcode.getVideo();
            MediaAudioObject audio = transcode.getAudio();
            MediaTransConfigObject transConfig = transcode.getTransConfig();
            MediaTimeIntervalObject timeInterval = transcode.getTimeInterval();
            addFormat(xml, transcode.getContainer());
            addTimeInterval(xml, timeInterval);
            addVideo(xml, video);
            addAudio(xml, audio);
            addTransConfig(xml, transConfig);
            addAudioMix(xml, transcode.getAudioMix());
            xml.end();
        }
    }

    private static void addFormat(XmlWriter xml, MediaContainerObject container) {
        String format = container.getFormat();
        if (format != null) {
            xml.start("Container");
            xml.start("Format").value(format).end();
            if (container.getClipConfig().getDuration() != null) {
                xml.start("ClipConfig");
                xml.start("Duration").value(container.getClipConfig().getDuration()).end();
                xml.end();
            }
            xml.end();
        }
    }

    private static void addSnapshot(XmlWriter xml, MediaSnapshotObject snapshot) {
        if (objIsNotValid(snapshot)) {
            xml.start("Snapshot");
            addIfNotNull(xml, "Mode", snapshot.getMode());
            addIfNotNull(xml, "Count", snapshot.getCount());
            addIfNotNull(xml, "Fps", snapshot.getFps());
            addIfNotNull(xml, "Height", snapshot.getHeight());
            addIfNotNull(xml, "Start", snapshot.getStart());
            addIfNotNull(xml, "TimeInterval", snapshot.getTimeInterval());
            addIfNotNull(xml, "Width", snapshot.getWidth());
            addIfNotNull(xml, "CIParam", snapshot.getCiParam());
            addIfNotNull(xml, "IsCheckCount", snapshot.getIsCheckCount());
            addIfNotNull(xml, "IsCheckBlack", snapshot.getIsCheckBlack());
            addIfNotNull(xml, "BlackLevel", snapshot.getBlackLevel());
            addIfNotNull(xml, "PixelBlackThreshold", snapshot.getPixelBlackThreshold());
            addIfNotNull(xml, "SnapshotOutMode", snapshot.getSnapshotOutMode());
            if (objIsNotValid(snapshot.getSnapshotConfig())) {
                SpriteSnapshotConfig snapshotConfig = snapshot.getSnapshotConfig();
                xml.start("SpriteSnapshotConfig");
                addIfNotNull(xml, "CellWidth", snapshotConfig.getCellWidth());
                addIfNotNull(xml, "CellHeight", snapshotConfig.getCellHeight());
                addIfNotNull(xml, "Padding", snapshotConfig.getPadding());
                addIfNotNull(xml, "Margin", snapshotConfig.getMargin());
                addIfNotNull(xml, "Color", snapshotConfig.getColor());
                addIfNotNull(xml, "Columns", snapshotConfig.getColumns());
                addIfNotNull(xml, "Lines", snapshotConfig.getLines());
                xml.end();
            }
            xml.end();
        }
    }

    private static void addVideo(XmlWriter xml, MediaVideoObject video) {
        if (RequestXmlFactory.CheckObjectUtils.objIsValid(video)) {
            return;
        }
        xml.start("Video");
        addIfNotNull(xml, "Codec", video.getCodec());
        addIfNotNull(xml, "Width", video.getWidth());
        addIfNotNull(xml, "Height", video.getHeight());
        addIfNotNull(xml, "Fps", video.getFps());
        addIfNotNull(xml, "Bitrate", video.getBitrate());
        addIfNotNull(xml, "BufSize", video.getBufSize());
        addIfNotNull(xml, "Crf", video.getCrf());
        addIfNotNull(xml, "Crop", video.getCrop());
        addIfNotNull(xml, "Gop", video.getGop());
        addIfNotNull(xml, "LongShortMode", video.getLongShortMode());
        addIfNotNull(xml, "Maxrate", video.getMaxrate());
        addIfNotNull(xml, "Pad", video.getPad());
        addIfNotNull(xml, "PixFmt", video.getPixFmt());
        addIfNotNull(xml, "Preset", video.getPreset());
        addIfNotNull(xml, "Profile", video.getProfile());
        addIfNotNull(xml, "Qality", video.getQality());
        addIfNotNull(xml, "Quality", video.getQuality());
        addIfNotNull(xml, "Remove", video.getRemove());
        addIfNotNull(xml, "ScanMode", video.getScanMode());
        addIfNotNull(xml, "HlsTsTime", video.getHlsTsTime());
        addIfNotNull(xml, "AnimateFramesPerSecond", video.getAnimateFramesPerSecond());
        addIfNotNull(xml, "AnimateTimeIntervalOfFrame", video.getAnimateTimeIntervalOfFrame());
        addIfNotNull(xml, "AnimateOnlyKeepKeyFrame", video.getAnimateOnlyKeepKeyFrame());
        xml.end();
    }

    private static void addVideo(XmlWriter xml, MediaTranscodeVideoObject video) {
        if (RequestXmlFactory.CheckObjectUtils.objIsValid(video)) {
            return;
        }
        xml.start("Video");
        addIfNotNull(xml, "Codec", video.getCodec());
        addIfNotNull(xml, "Width", video.getWidth());
        addIfNotNull(xml, "Height", video.getHeight());
        addIfNotNull(xml, "Fps", video.getFps());
        addIfNotNull(xml, "Bitrate", video.getBitrate());
        addIfNotNull(xml, "BufSize", video.getBufSize());
        addIfNotNull(xml, "Crf", video.getCrf());
        addIfNotNull(xml, "Gop", video.getGop());
        addIfNotNull(xml, "Maxrate", video.getMaxrate());
        addIfNotNull(xml, "Preset", video.getPreset());
        addIfNotNull(xml, "Profile", video.getProfile());
        addIfNotNull(xml, "Remove", video.getRemove());
        addIfNotNull(xml, "ScanMode", video.getScanMode());
        xml.end();
    }

    private static void addAudio(XmlWriter xml, MediaAudioObject audio) {
        if (objIsNotValid(audio)) {
            xml.start("Audio");
            addIfNotNull(xml, "Bitrate", audio.getBitrate());
            addIfNotNull(xml, "Channels", audio.getChannels());
            addIfNotNull(xml, "Codec", audio.getCodec());
            addIfNotNull(xml, "Profile", audio.getProfile());
            addIfNotNull(xml, "Remove", audio.getRemove());
            addIfNotNull(xml, "Samplerate", audio.getSamplerate());
            xml.end();
        }
    }

    private static void addTimeInterval(XmlWriter xml, MediaTimeIntervalObject timeInterval) {
        if (objIsNotValid(timeInterval)) {
            xml.start("TimeInterval");
            addIfNotNull(xml, "Duration", timeInterval.getDuration());
            addIfNotNull(xml, "Start", timeInterval.getStart());
            xml.end();
        }
    }

    private static void addTransConfig(XmlWriter xml, MediaTransConfigObject transConfig) {
        if (objIsNotValid(transConfig)) {
            xml.start("TransConfig");
            addIfNotNull(xml, "AdjDarMethod", transConfig.getAdjDarMethod());
            addIfNotNull(xml, "AudioBitrateAdjMethod", transConfig.getAudioBitrateAdjMethod());
            addIfNotNull(xml, "IsCheckAudioBitrate", transConfig.getIsCheckAudioBitrate());
            addIfNotNull(xml, "IsCheckReso", transConfig.getIsCheckReso());
            addIfNotNull(xml, "IsCheckVideoBitrate", transConfig.getIsCheckVideoBitrate());
            addIfNotNull(xml, "ResoAdjMethod", transConfig.getResoAdjMethod());
            addIfNotNull(xml, "TransMode", transConfig.getTransMode());
            addIfNotNull(xml, "DeleteMetadata", transConfig.getDeleteMetadata());
            addIfNotNull(xml, "IsHdr2Sdr", transConfig.getIsHdr2Sdr());
            addIfNotNull(xml, "HlsEncrypt", transConfig.getHlsEncrypt());
            xml.end();
        }
    }

    private static void addInput(XmlWriter xml, MediaInputObject inputObject) {
        xml.start("Input");
        addIfNotNull(xml, "Object", inputObject.getObject());
        addIfNotNull(xml, "Url", inputObject.getUrl());
        xml.end();
    }

    private static void addWatermar(XmlWriter xml, MediaWatermark watermark) {
        if (objIsNotValid(watermark)) {
            xml.start("Watermark");
            addIfNotNull(xml, "Type", watermark.getType());
            addIfNotNull(xml, "Dx", watermark.getDx());
            addIfNotNull(xml, "Dy", watermark.getDy());
            addIfNotNull(xml, "EndTime", watermark.getEndTime());
            addIfNotNull(xml, "LocMode", watermark.getLocMode());
            addIfNotNull(xml, "Pos", watermark.getPos());
            addIfNotNull(xml, "StartTime", watermark.getStartTime());

            if ("Text".equalsIgnoreCase(watermark.getType())) {
                MediaWaterMarkText text = watermark.getText();
                xml.start("Text");
                addIfNotNull(xml, "FontColor", text.getFontColor());
                addIfNotNull(xml, "FontSize", text.getFontSize());
                addIfNotNull(xml, "FontType", text.getFontType());
                addIfNotNull(xml, "Text", text.getText());
                addIfNotNull(xml, "Transparency", text.getTransparency());
                xml.end();
            } else if ("Image".equalsIgnoreCase(watermark.getType())) {
                MediaWaterMarkImage image = watermark.getImage();
                xml.start("Image");
                addIfNotNull(xml, "Height", image.getHeight());
                addIfNotNull(xml, "Mode", image.getMode());
                addIfNotNull(xml, "Transparency", image.getTransparency());
                addIfNotNull(xml, "Url", image.getUrl());
                addIfNotNull(xml, "Width", image.getWidth());
                xml.end();
            }
            xml.end();
        }
    }


    private static void addRemoveWatermark(XmlWriter xml, MediaRemoveWaterMark removeWatermark) {
        if (objIsNotValid(removeWatermark)) {
            xml.start("RemoveWatermark");
            addIfNotNull(xml, "Height", removeWatermark.getHeight());
            addIfNotNull(xml, "Dx", removeWatermark.getDx());
            addIfNotNull(xml, "Dy", removeWatermark.getDy());
            addIfNotNull(xml, "Switch", removeWatermark.get_switch());
            addIfNotNull(xml, "Width", removeWatermark.getWidth());
            xml.end();
        }
    }

    private static boolean isEmpty(Object object) {
        if (object == null) {
            return true;
        }
        if (object instanceof String && (((String) object).isEmpty())) {
            return true;
        }
        if (object instanceof Collection && ((Collection) object).isEmpty()) {
            return true;
        }
        if (object instanceof Map && ((Map) object).isEmpty()) {
            return true;
        }
        if (object instanceof Object[] && ((Object[]) object).length == 0) {
            return true;
        }
        return false;
    }


    public static Boolean objIsNotValid(Object obj) {
        //查询出对象所有的属性
        Field[] fields = obj.getClass().getDeclaredFields();
        //用于判断所有属性是否为空,如果参数为空则不查询
        for (Field field : fields) {
            //不检查 直接取值
            field.setAccessible(true);
            try {
                Object o = field.get(obj);
                if (field.isSynthetic()) {
                    continue;
                }
                if (!isEmpty(o)) {
                    //不为空
                    return true;
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return false;
    }


}
