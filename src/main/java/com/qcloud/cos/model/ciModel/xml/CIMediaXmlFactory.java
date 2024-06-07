package com.qcloud.cos.model.ciModel.xml;

import com.qcloud.cos.internal.RequestXmlFactory;
import com.qcloud.cos.internal.XmlWriter;
import com.qcloud.cos.model.ciModel.common.BatchInputObject;
import com.qcloud.cos.model.ciModel.common.MediaInputObject;
import com.qcloud.cos.model.ciModel.common.MediaOutputObject;
import com.qcloud.cos.model.ciModel.job.AudioConfig;
import com.qcloud.cos.model.ciModel.job.BatchJobOperation;
import com.qcloud.cos.model.ciModel.job.BatchJobRequest;
import com.qcloud.cos.model.ciModel.job.CallBackMqConfig;
import com.qcloud.cos.model.ciModel.job.ColorEnhance;
import com.qcloud.cos.model.ciModel.job.EffectConfig;
import com.qcloud.cos.model.ciModel.job.ExtractDigitalWatermark;
import com.qcloud.cos.model.ciModel.job.FrameEnhance;
import com.qcloud.cos.model.ciModel.job.JobParam;
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
import com.qcloud.cos.model.ciModel.job.MediaTtsConfig;
import com.qcloud.cos.model.ciModel.job.MediaVideoObject;
import com.qcloud.cos.model.ciModel.job.MsSharpen;
import com.qcloud.cos.model.ciModel.job.QualityEstimateConfig;
import com.qcloud.cos.model.ciModel.job.SDRtoHDR;
import com.qcloud.cos.model.ciModel.job.SceneChangeInfo;
import com.qcloud.cos.model.ciModel.job.Subtitle;
import com.qcloud.cos.model.ciModel.job.Subtitles;
import com.qcloud.cos.model.ciModel.job.SuperResolution;
import com.qcloud.cos.model.ciModel.job.TtsTpl;
import com.qcloud.cos.model.ciModel.job.VideoEnhance;
import com.qcloud.cos.model.ciModel.job.VideoTag;
import com.qcloud.cos.model.ciModel.job.VideoTargetRec;
import com.qcloud.cos.model.ciModel.job.VoiceSeparate;
import com.qcloud.cos.model.ciModel.template.MediaHlsEncryptObject;
import com.qcloud.cos.model.ciModel.template.MediaSegmentObject;
import com.qcloud.cos.model.ciModel.template.MediaSmartCoverObject;
import com.qcloud.cos.model.ciModel.template.MediaSnapshotObject;
import com.qcloud.cos.model.ciModel.template.MediaTemplateRequest;
import com.qcloud.cos.model.ciModel.template.MediaVideoMontageObject;
import com.qcloud.cos.model.ciModel.template.MediaWaterMarkImage;
import com.qcloud.cos.model.ciModel.template.MediaWaterMarkText;
import com.qcloud.cos.model.ciModel.template.MediaWatermark;
import com.qcloud.cos.model.ciModel.template.SlideConfig;
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
     * 媒体任务xml转换
     */
    public static byte[] convertToXmlByteArray(BatchJobRequest request) {
        XmlWriter xml = new XmlWriter();
        xml.start("Request");
        addBatchCommonParams(xml, request);
        addInput(xml, request.getInput());
        addBatchOperation(xml, request.getOperation());
        xml.end();
        return xml.getBytes();
    }

    private static void addBatchCommonParams(XmlWriter xml, BatchJobRequest request) {
        if (objIsNotValid(request)) {
            addIfNotNull(xml, "Name", request.getName());
            addIfNotNull(xml, "Type", request.getType());
        }
    }

    /**
     * 模板任务xml转换
     */
    public static byte[] convertToXmlByteArray(MediaTemplateRequest request) {
        if ("Concat".equalsIgnoreCase(request.getTag())) {
            return CIAuditingXmlFactoryV2.convertToXmlByteArray(request);
        }
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
            addTimeInterval(xml, request.getTimeInterval());
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
            addSlideConfig(xml, request.getWatermark().getSlideConfig());
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
            addAudioMix(xml, request.getAudioMix(), "AudioMix");
            addContainer(xml, request.getContainer());
        } else if ("VideoTargetRec".equalsIgnoreCase(tag)) {
            VideoTargetRec videoTargetRec = request.getVideoTargetRec();
            addVideoTargetRec(xml, videoTargetRec);
        } else if ("Tts".equalsIgnoreCase(tag)) {
            addIfNotNull(xml, "Codec", request.getCodec());
            addIfNotNull(xml, "Mode", request.getMode());
            addIfNotNull(xml, "Speed", request.getSpeed());
            addIfNotNull(xml, "Volume", request.getVolume());
            addIfNotNull(xml, "VoiceType", request.getVoiceType());
        } else if ("VideoEnhance".equalsIgnoreCase(tag)) {
            addVideoEnhance(xml, request.getVideoEnhance());
        } else if ("VideoMontage".equalsIgnoreCase(tag)) {
            addIfNotNull(xml, "Duration", request.getDuration());
            addIfNotNull(xml, "Scene", request.getScene());
            addVideo(xml, request.getVideo());
            addAudio(xml, request.getAudio());
            addAudioMix(xml, request.getAudioMix(), "AudioMix");
            addAudioMixArray(xml, request.getAudioMixArray());
            addContainer(xml, request.getContainer());
        } else if ("HighSpeedHd".equalsIgnoreCase(tag)) {
            addVideo(xml, request.getVideo());
            addAudio(xml, request.getAudio());
            addContainer(xml, request.getContainer());
            addTransConfig(xml, request.getTransConfig());
            addTimeInterval(xml, request.getTimeInterval());
        }
        xml.end();
        return xml.getBytes();
    }

    private static void addOperation(XmlWriter xml, MediaJobsRequest request) {
        MediaJobOperation operation = request.getOperation();
        xml.start("Operation");

        addIfNotNull(xml, "TemplateId", operation.getTemplateId());
        addIfNotNull(xml, "JobLevel", operation.getJobLevel());
        addIfNotNull(xml, "DecryptKey", operation.getDecryptKey());
        addIfNotNull(xml, "DecryptIv", operation.getDecryptIv());
        addIfNotNull(xml, "DecryptMode", operation.getDecryptMode());
        addIfNotNull(xml, "EncryptKey", operation.getEncryptKey());
        addIfNotNull(xml, "EncryptIv", operation.getEncryptIv());
        addIfNotNull(xml, "EncryptMode", operation.getEncryptMode());
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
        addVoiceSeparate(xml, operation.getVoiceSeparate());
        addTtsConfig(xml, operation.getTtsConfig());
        addTtsTpl(xml, operation.getTtsTpl());
        addVideoEnhance(xml, operation.getVideoEnhance());
        addSubtitles(xml, operation.getSubtitles());
        addVideoTag(xml, operation.getVideoTag());
        addQualityEstimateConfig(xml, operation.getQualityEstimateConfig());
        xml.end();
    }

    private static void addQualityEstimateConfig(XmlWriter xml, QualityEstimateConfig qec) {
        if (objIsNotValid(qec)) {
            xml.start("QualityEstimateConfig");
            addIfNotNull(xml, "Mode", qec.getMode());
            addIfNotNull(xml, "Rotate", qec.getRotate());
            xml.end();
        }
    }

    private static void addVideoTag(XmlWriter xml, VideoTag videoTag) {
        if (objIsNotValid(videoTag)) {
            xml.start("VideoTag");
            addIfNotNull(xml, "Scenario", videoTag.getScenario());
            xml.end();
        }
    }

    private static void addSubtitles(XmlWriter xml, Subtitles subtitles) {
        if (objIsNotValid(subtitles)) {
            xml.start("Subtitles");
            List<Subtitle> subtitle = subtitles.getSubtitle();
            for (Subtitle sub : subtitle) {
                if (objIsNotValid(subtitle)) {
                    xml.start("Subtitle");
                    addIfNotNull(xml, "Url", sub.getUrl());
                    addIfNotNull(xml, "Embed", sub.getEmbed());
                    addIfNotNull(xml, "FontType", sub.getFontType());
                    addIfNotNull(xml, "FontSize", sub.getFontSize());
                    addIfNotNull(xml, "FontColor", sub.getFontColor());
                    addIfNotNull(xml, "OutlineColor", sub.getOutlineColor());
                    addIfNotNull(xml, "VMargin", sub.getvMargin());
                    xml.end();
                }
            }
            xml.end();
        }
    }

    private static void addVideoEnhance(XmlWriter xml, VideoEnhance videoEnhance) {
        if (objIsNotValid(videoEnhance)) {
            xml.start("VideoEnhance");
            addTranscode(xml, videoEnhance.getTrascode());
            addSuperResolution(xml, videoEnhance.getSuperResolution());
            addColorEnhance(xml, videoEnhance.getColorEnhance());
            addMsSharpen(xml, videoEnhance.getMsSharpen());
            addSdrToHDR(xml, videoEnhance.getSdrToHDR());
            addFrameEnhance(xml, videoEnhance.getFrameEnhance());
            xml.end();
        }
    }

    private static void addFrameEnhance(XmlWriter xml, FrameEnhance frameEnhance) {
        if (objIsNotValid(frameEnhance)) {
            xml.start("FrameEnhance");
            addIfNotNull(xml, "FrameDoubling", frameEnhance.getFrameDoubling());
            xml.end();
        }
    }

    private static void addSdrToHDR(XmlWriter xml, SDRtoHDR sdrToHDR) {
        if (objIsNotValid(sdrToHDR)) {
            xml.start("SDRtoHDR");
            addIfNotNull(xml, "HdrMode", sdrToHDR.getHdrMode());
            xml.end();
        }
    }

    private static void addMsSharpen(XmlWriter xml, MsSharpen msSharpen) {
        if (objIsNotValid(msSharpen)) {
            xml.start("MsSharpen");
            addIfNotNull(xml, "SharpenLevel", msSharpen.getSharpenLevel());
            xml.end();
        }
    }

    private static void addColorEnhance(XmlWriter xml, ColorEnhance colorEnhance) {
        if (objIsNotValid(colorEnhance)) {
            xml.start("ColorEnhance");
            addIfNotNull(xml, "Contrast", colorEnhance.getContrast());
            addIfNotNull(xml, "Correction", colorEnhance.getCorrection());
            addIfNotNull(xml, "Saturation", colorEnhance.getSaturation());
            xml.end();
        }
    }

    private static void addSuperResolution(XmlWriter xml, SuperResolution superResolution) {
        if (objIsNotValid(superResolution)) {
            xml.start("SuperResolution");
            addIfNotNull(xml, "Resolution", superResolution.getResolution());
            addIfNotNull(xml, "EnableScaleUp", superResolution.getEnableScaleUp());
            addIfNotNull(xml, "Version", superResolution.getVersion());
            xml.end();
        }
    }

    private static void addTtsTpl(XmlWriter xml, TtsTpl ttsTpl) {
        if (objIsNotValid(ttsTpl)) {
            xml.start("TtsTpl");
            addIfNotNull(xml, "Codec", ttsTpl.getCodec());
            addIfNotNull(xml, "Mode", ttsTpl.getMode());
            addIfNotNull(xml, "Speed", ttsTpl.getSpeed());
            addIfNotNull(xml, "Volume", ttsTpl.getVolume());
            addIfNotNull(xml, "VoiceType", ttsTpl.getVoiceType());
            xml.end();
        }
    }

    private static void addTtsConfig(XmlWriter xml, MediaTtsConfig ttsConfig) {
        if (objIsNotValid(ttsConfig)) {
            xml.start("TtsConfig");
            addIfNotNull(xml, "Input", ttsConfig.getInput());
            addIfNotNull(xml, "InputType", ttsConfig.getInputType());
            xml.end();
        }
    }

    private static void addBatchOperation(XmlWriter xml, BatchJobOperation operation) {
        xml.start("Operation");
        addTimeInterval(xml, operation.getTimeInterval());
        addIfNotNull(xml, "QueueId", operation.getQueueId());
        addIfNotNull(xml, "UserData", operation.getUserData());
        addIfNotNull(xml, "CallBack", operation.getCallBack());
        addIfNotNull(xml, "Tag", operation.getTag());
        addIfNotNull(xml, "JobLevel", operation.getJobLevel());
        addIfNotNull(xml, "WorkflowIds", operation.getWorkflowIds());

        addJobParam(xml, operation.getJobParam());
        addOutput(xml, operation.getOutput());
        addCallBackMqConfig(xml, operation.getCallBackMqConfig());

        xml.end();
    }

    private static void addJobParam(XmlWriter xml, JobParam jobParam) {
        if (objIsNotValid(jobParam)) {
            xml.start("JobParam");
            addIfNotNull(xml, "TemplateId", jobParam.getTemplateId());
            addPicProcess(xml, jobParam.getPicProcess());
            xml.end();
        }
    }

    private static void addCallBackMqConfig(XmlWriter xml, CallBackMqConfig callBackMqConfig) {
        if (objIsNotValid(callBackMqConfig)) {
            xml.start("CallBackMqConfig");
            addIfNotNull(xml, "MqMode", callBackMqConfig.getMqMode());
            addIfNotNull(xml, "MqName", callBackMqConfig.getMqName());
            addIfNotNull(xml, "MqRegion", callBackMqConfig.getMqRegion());
            xml.end();
        }
    }

    private static void addPicProcess(XmlWriter xml, MediaPicProcessTemplateObject picProcess) {
        if (objIsNotValid(picProcess)) {
            xml.start("PicProcess");
            addIfNotNull(xml, "IsPicInfo", picProcess.getIsPicInfo());
            addIfNotNull(xml, "ProcessRule", picProcess.getProcessRule());
            xml.end();
        }
    }

    private static void addVoiceSeparate(XmlWriter xml, VoiceSeparate voiceSeparate) {
        if (objIsNotValid(voiceSeparate)) {
            xml.start("VoiceSeparate");
            addIfNotNull(xml, "AudioMode", voiceSeparate.getAudioMode());
            AudioConfig audioConfig = voiceSeparate.getAudioConfig();
            if (objIsNotValid(audioConfig)) {
                xml.start("AudioConfig");
                addIfNotNull(xml, "Bitrate", audioConfig.getBitrate());
                addIfNotNull(xml, "Channels", audioConfig.getChannels());
                addIfNotNull(xml, "Codec", audioConfig.getCodec());
                addIfNotNull(xml, "Samplerate", audioConfig.getSamplerate());
                xml.end();
            }
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
            addIfNotNull(xml, "Scene", videoMontage.getScene());
            addVideo(xml, videoMontage.getVideo());
            addAudio(xml, videoMontage.getAudio());
            addAudioMix(xml, videoMontage.getAudioMix(), "AudioMix");
            addAudioMixArray(xml, videoMontage.getAudioMixArray());
            addContainer(xml, videoMontage.getContainer());
            xml.end();
        }
    }

    private static void addAudioMix(XmlWriter xml, MediaAudioMixObject audioMix, String key) {
        if (objIsNotValid(audioMix)) {
            xml.start(key);
            addIfNotNull(xml, "MixMode", audioMix.getMixMode());
            addIfNotNull(xml, "AudioSource", audioMix.getAudioSource());
            addIfNotNull(xml, "Replace", audioMix.getReplace());
            addIfNotNull(xml, "DirectMix", audioMix.getDirectMix());
            EffectConfig effectConfig = audioMix.getEffectConfig();
            if (objIsNotValid(effectConfig)) {
                addIfNotNull(xml, "BgmFadeTime", effectConfig.getBgmFadeTime());
                addIfNotNull(xml, "EnableBgmFade", effectConfig.getEnableBgmFade());
                addIfNotNull(xml, "EnableEndFadeout", effectConfig.getEnableEndFadeout());
                addIfNotNull(xml, "EnableStartFadein", effectConfig.getEnableStartFadein());
                addIfNotNull(xml, "EndFadeoutTime", effectConfig.getEndFadeoutTime());
                addIfNotNull(xml, "StartFadeinTime", effectConfig.getStartFadeinTime());
            }
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
                addIfNotNull(xml, "Duration", concatFragment.getDuration());
                xml.end();
            }
            addVideo(xml, mediaConcatTemplate.getVideo());
            addAudio(xml, mediaConcatTemplate.getAudio());
            addIfNotNull(xml, "Index", mediaConcatTemplate.getIndex());
            addIfNotNull(xml, "DirectConcat", mediaConcatTemplate.getDirectConcat());
            addContainer(xml, mediaConcatTemplate.getContainer());
            addAudioMix(xml, mediaConcatTemplate.getAudioMix(), "AudioMix");
            addAudioMixArray(xml, mediaConcatTemplate.getAudioMixArray());
            addSceneChangeInfo(xml, mediaConcatTemplate.getSceneChangeInfo());
            xml.end();
        }
    }

    private static void addSceneChangeInfo(XmlWriter xml, SceneChangeInfo sceneChangeInfo) {
        if (objIsNotValid(sceneChangeInfo)) {
            xml.start("SceneChangeInfo");
            addIfNotNull(xml, "Time", sceneChangeInfo.getTime());
            addIfNotNull(xml, "Mode", sceneChangeInfo.getMode());
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

    protected static void addOutput(XmlWriter xml, MediaOutputObject output) {
        if (objIsNotValid(output)) {
            xml.start("Output");
            addIfNotNull(xml, "Region", output.getRegion());
            addIfNotNull(xml, "Object", output.getObject());
            addIfNotNull(xml, "Bucket", output.getBucket());
            addIfNotNull(xml, "SpriteObject", output.getSpriteObject());
            addIfNotNull(xml, "AuObject", output.getAuObject());
            xml.end();
        }
    }

    private static void addSegment(XmlWriter xml, MediaSegmentObject segment) {
        if (objIsNotValid(segment)) {
            xml.start("Segment");
            addIfNotNull(xml, "Duration", segment.getDuration());
            addIfNotNull(xml, "Format", segment.getFormat());
            addIfNotNull(xml, "TranscodeIndex", segment.getTranscodeIndex());
            addIfNotNull(xml, "StartTime", segment.getStartTime());
            addIfNotNull(xml, "EndTime", segment.getEndTime());
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
            addIfNotNull(xml, "Tag", request.getTag());
            addIfNotNull(xml, "BucketName", request.getBucketName());
            addIfNotNull(xml, "QueueId", request.getQueueId());
            addIfNotNull(xml, "CallBack", request.getCallBack());
            addIfNotNull(xml, "CallBackFormat", request.getCallBackFormat());
            addIfNotNull(xml, "CallBackType", request.getCallBackType());
            addIfNotNull(xml, "QueueType", request.getQueueType());
        }
    }

    static void addTranscode(XmlWriter xml, MediaTranscodeObject transcode) {
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
            addAudioMix(xml, transcode.getAudioMix(), "AudioMix");
            addAudioMixArray(xml, transcode.getAudioMixArray());
            xml.end();
        }
    }

    private static void addAudioMixArray(XmlWriter xml, List<MediaAudioMixObject> audioMixArray) {
        if (audioMixArray != null && !audioMixArray.isEmpty()) {
            for (MediaAudioMixObject mediaAudioMixObject : audioMixArray) {
                addAudioMix(xml, mediaAudioMixObject, "AudioMixArray");
            }
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
                addIfNotNull(xml, "ScaleMethod", snapshotConfig.getScaleMethod());
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
        addIfNotNull(xml, "Rotate", video.getRotate());
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
        addIfNotNull(xml, "Pixfmt", video.getPixfmt());
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
            addIfNotNull(xml, "End", timeInterval.getEnd());
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
        if (objIsNotValid(inputObject)) {
            xml.start("Input");
            addIfNotNull(xml, "Object", inputObject.getObject());
            addIfNotNull(xml, "Url", inputObject.getUrl());
            xml.end();
        }
    }

    private static void addInput(XmlWriter xml, BatchInputObject inputObject) {
        xml.start("Input");
        addIfNotNull(xml, "Object", inputObject.getObject());
        addIfNotNull(xml, "Url", inputObject.getUrl());
        addIfNotNull(xml, "Manifest", inputObject.getManifest());
        addIfNotNull(xml, "Prefix", inputObject.getPrefix());
        addIfNotNull(xml, "UrlFile", inputObject.getUrlFile());
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
            addSlideConfig(xml, watermark.getSlideConfig());
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
                addIfNotNull(xml, "Background", image.getBackground());
                xml.end();
            }
            xml.end();
        }
    }

    private static void addSlideConfig(XmlWriter xml, SlideConfig slideConfig) {
        if (objIsNotValid(slideConfig)) {
            xml.start("SlideConfig");
            addIfNotNull(xml, "SlideMode", slideConfig.getSlideMode());
            addIfNotNull(xml, "XSlideSpeed", slideConfig.getxSlideSpeed());
            addIfNotNull(xml, "YSlideSpeed", slideConfig.getxSlideSpeed());
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
        if (obj == null) {
            return false;
        }
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
