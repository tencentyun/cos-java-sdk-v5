package com.qcloud.cos.model.ciModel.xml;

import com.qcloud.cos.internal.RequestXmlFactory;
import com.qcloud.cos.internal.XmlWriter;
import com.qcloud.cos.model.ciModel.common.MediaInputObject;
import com.qcloud.cos.model.ciModel.common.MediaOutputObject;
import com.qcloud.cos.model.ciModel.job.ExtractDigitalWatermark;
import com.qcloud.cos.model.ciModel.job.MediaAudioObject;
import com.qcloud.cos.model.ciModel.job.MediaConcatFragmentObject;
import com.qcloud.cos.model.ciModel.job.MediaConcatTemplateObject;
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
import com.qcloud.cos.model.ciModel.template.MediaWaterMarkImage;
import com.qcloud.cos.model.ciModel.template.MediaWaterMarkText;
import com.qcloud.cos.model.ciModel.template.MediaWatermark;
import com.qcloud.cos.utils.StringUtils;

import java.util.List;

/**
 * 数据万象媒体处理xml格式化
 */
public class CIMediaXmlFactory {

    private static void addIfNotNull(XmlWriter xml, String xmlTag, String value) {
        if (value != null) {
            xml.start(xmlTag).value(value).end();
        }
    }

    private static void addIfNotNull(XmlWriter xml, String xmlTag, Object value) {
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
        System.out.println(xml);
        return xml.getBytes();
    }

    private static void addOperation(XmlWriter xml, MediaJobsRequest request) {
        MediaJobOperation operation = request.getOperation();
        xml.start("Operation");

        addIfNotNull(xml, "TemplateId", operation.getTemplateId());

        List<String> watermarkTemplateId = operation.getWatermarkTemplateId();
        if (watermarkTemplateId.size() != 0) {
            for (String templateId : watermarkTemplateId) {
                xml.start("WatermarkTemplateId").value(templateId).end();
            }
        }

        MediaWatermark watermark = operation.getWatermark();
        if (RequestXmlFactory.CheckObjectUtils.objIsNotValid(watermark)) {
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
        }
        MediaRemoveWaterMark removeWatermark = operation.getRemoveWatermark();
        if (RequestXmlFactory.CheckObjectUtils.objIsNotValid(removeWatermark)) {
            xml.start("RemoveWatermark");
            addIfNotNull(xml, "Height", removeWatermark.getHeight());
            addIfNotNull(xml, "Dx", removeWatermark.getDx());
            addIfNotNull(xml, "Dy", removeWatermark.getDy());
            addIfNotNull(xml, "Switch", removeWatermark.get_switch());
            addIfNotNull(xml, "Width", removeWatermark.getWidth());
            xml.end();
        }

        MediaConcatTemplateObject mediaConcatTemplate = operation.getMediaConcatTemplate();
        if (RequestXmlFactory.CheckObjectUtils.objIsNotValid(mediaConcatTemplate)) {
            xml.start("ConcatTemplate");
            List<MediaConcatFragmentObject> concatFragmentList = mediaConcatTemplate.getConcatFragmentList();
            for (MediaConcatFragmentObject concatFragment : concatFragmentList) {
                xml.start("ConcatFragment");
                addIfNotNull(xml, "Mode", concatFragment.getMode());
                addIfNotNull(xml, "Url", concatFragment.getUrl());
                xml.end();
            }
            addVideo(xml, mediaConcatTemplate.getVideo());
            addAudio(xml, mediaConcatTemplate.getAudio());
            addIfNotNull(xml, "Index", mediaConcatTemplate.getIndex());
            String format = mediaConcatTemplate.getContainer().getFormat();
            if (!StringUtils.isNullOrEmpty(format)) {
                xml.start("Container");
                xml.start("Format").value(format).end();
                xml.end();
            }
            xml.end();
        }

        MediaTranscodeObject transcode = operation.getTranscode();
        String format = transcode.getContainer().getFormat();
        if (RequestXmlFactory.CheckObjectUtils.objIsNotValid(transcode) && !StringUtils.isNullOrEmpty(format)) {
            xml.start("Transcode");
            MediaTranscodeVideoObject video = transcode.getVideo();
            MediaAudioObject audio = transcode.getAudio();
            MediaTransConfigObject transConfig = transcode.getTransConfig();
            MediaTimeIntervalObject timeInterval = transcode.getTimeInterval();
            if (format != null) {
                xml.start("Container");
                xml.start("Format").value(format).end();
                xml.end();
            }
            if (RequestXmlFactory.CheckObjectUtils.objIsNotValid(timeInterval)) {
                xml.start("TimeInterval");
                xml.start("Duration").value(timeInterval.getDuration()).end();
                xml.start("Start").value(timeInterval.getStart()).end();
                xml.end();
            }
            if (RequestXmlFactory.CheckObjectUtils.objIsNotValid(video)) {
                addVideo(xml, video);

            }
            if (RequestXmlFactory.CheckObjectUtils.objIsNotValid(audio)) {
                addAudio(xml, audio);
            }

            if (RequestXmlFactory.CheckObjectUtils.objIsNotValid(transConfig)) {
                xml.start("TransConfig");
                addIfNotNull(xml, "AdjDarMethod", transConfig.getAdjDarMethod());
                addIfNotNull(xml, "AudioBitrateAdjMethod", transConfig.getAudioBitrateAdjMethod());
                addIfNotNull(xml, "IsCheckAudioBitrate", transConfig.getIsCheckAudioBitrate());
                addIfNotNull(xml, "IsCheckReso", transConfig.getIsCheckReso());
                addIfNotNull(xml, "IsCheckVideoBitrate", transConfig.getIsCheckVideoBitrate());
                addIfNotNull(xml, "ResoAdjMethod", transConfig.getResoAdjMethod());
                addIfNotNull(xml, "TransMode", transConfig.getTransMode());
                addIfNotNull(xml, "VideoBitrateAdjMethod", transConfig.getVideoBitrateAdjMethod());
                xml.end();
            }
            xml.end();
        }

        MediaDigitalWatermark digitalWatermark = operation.getDigitalWatermark();
        if (RequestXmlFactory.CheckObjectUtils.objIsNotValid(digitalWatermark)) {
            xml.start("DigitalWatermark");
            addIfNotNull(xml, "Message", digitalWatermark.getMessage());
            addIfNotNull(xml, "Type", digitalWatermark.getType());
            addIfNotNull(xml, "Version", digitalWatermark.getVersion());
            xml.end();
        }

        ExtractDigitalWatermark extractDigitalWatermark = operation.getExtractDigitalWatermark();
        if (extractDigitalWatermark.getType() != null || extractDigitalWatermark.getMessage() != null) {
            xml.start("ExtractDigitalWatermark");
            addIfNotNull(xml, "Message", extractDigitalWatermark.getMessage());
            addIfNotNull(xml, "Type", extractDigitalWatermark.getType());
            addIfNotNull(xml, "Version", extractDigitalWatermark.getVersion());
            xml.end();
        }
        MediaOutputObject output = operation.getOutput();
        if (RequestXmlFactory.CheckObjectUtils.objIsNotValid(output)) {
            xml.start("Output");
            addIfNotNull(xml, "Region", output.getRegion());
            addIfNotNull(xml, "Object", output.getObject());
            addIfNotNull(xml, "Bucket", output.getBucket());
            xml.end();
        }
        addPicProcess(xml, operation.getPicProcess());
        xml.end();
    }

    private static void addCommonParams(XmlWriter xml, MediaJobsRequest request) {
        xml.start("Tag").value(request.getTag()).end();
        xml.start("BucketName").value(request.getBucketName()).end();
        xml.start("QueueId").value(request.getQueueId()).end();
        addIfNotNull(xml, "CallBack", request.getCallBack());
    }

    private static void addPicProcess(XmlWriter xml, MediaPicProcessTemplateObject picProcess) {
        if (RequestXmlFactory.CheckObjectUtils.objIsNotValid(picProcess)) {
            xml.start("PicProcess");
            addIfNotNull(xml, "IsPicInfo", picProcess.getIsPicInfo());
            addIfNotNull(xml, "ProcessRule", picProcess.getIsPicInfo());
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
        xml.start("Audio");
        addIfNotNull(xml, "Bitrate", audio.getBitrate());
        addIfNotNull(xml, "Channels", audio.getChannels());
        addIfNotNull(xml, "Codec", audio.getCodec());
        addIfNotNull(xml, "Profile", audio.getProfile());
        addIfNotNull(xml, "Remove", audio.getRemove());
        addIfNotNull(xml, "Samplerate", audio.getSamplerate());
        xml.end();
    }

    private static void addInput(XmlWriter xml, MediaInputObject inputObject) {
        xml.start("Input");
        xml.start("Object").value(inputObject.getObject()).end();
        xml.end();
    }
}
