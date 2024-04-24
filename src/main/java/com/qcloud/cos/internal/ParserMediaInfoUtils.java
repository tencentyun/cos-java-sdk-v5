package com.qcloud.cos.internal;

import com.qcloud.cos.model.ciModel.auditing.AudioSectionInfo;
import com.qcloud.cos.model.ciModel.auditing.AuditingCosOutput;
import com.qcloud.cos.model.ciModel.auditing.AuditingLiveInfo;
import com.qcloud.cos.model.ciModel.auditing.AuditingRecordInfo;
import com.qcloud.cos.model.ciModel.auditing.AudtingCommonInfo;
import com.qcloud.cos.model.ciModel.auditing.BatchImageJobDetail;
import com.qcloud.cos.model.ciModel.auditing.LanguageResult;
import com.qcloud.cos.model.ciModel.auditing.LibResult;
import com.qcloud.cos.model.ciModel.auditing.ListResult;
import com.qcloud.cos.model.ciModel.auditing.ObjectResults;
import com.qcloud.cos.model.ciModel.auditing.OcrResults;
import com.qcloud.cos.model.ciModel.auditing.PoliticsInfoObjectResults;
import com.qcloud.cos.model.ciModel.auditing.SectionInfo;
import com.qcloud.cos.model.ciModel.auditing.SnapshotInfo;
import com.qcloud.cos.model.ciModel.auditing.UserInfo;
import com.qcloud.cos.model.ciModel.common.BatchInputObject;
import com.qcloud.cos.model.ciModel.common.MediaOutputObject;
import com.qcloud.cos.model.ciModel.job.AudioConfig;
import com.qcloud.cos.model.ciModel.job.ColorEnhance;
import com.qcloud.cos.model.ciModel.job.DetailedResult;
import com.qcloud.cos.model.ciModel.job.EffectConfig;
import com.qcloud.cos.model.ciModel.job.FrameEnhance;
import com.qcloud.cos.model.ciModel.job.Md5Info;
import com.qcloud.cos.model.ciModel.job.MediaAudioMixObject;
import com.qcloud.cos.model.ciModel.job.MediaAudioObject;
import com.qcloud.cos.model.ciModel.job.MediaBodyInfo;
import com.qcloud.cos.model.ciModel.job.MediaConcatFragmentObject;
import com.qcloud.cos.model.ciModel.job.MediaContainerObject;
import com.qcloud.cos.model.ciModel.job.MediaDigitalWatermark;
import com.qcloud.cos.model.ciModel.job.MediaJobObject;
import com.qcloud.cos.model.ciModel.job.MediaJobOperation;
import com.qcloud.cos.model.ciModel.job.MediaRecognition;
import com.qcloud.cos.model.ciModel.job.MediaRemoveWaterMark;
import com.qcloud.cos.model.ciModel.job.MediaTimeIntervalObject;
import com.qcloud.cos.model.ciModel.job.MediaTopkRecognition;
import com.qcloud.cos.model.ciModel.job.MediaTransConfigObject;
import com.qcloud.cos.model.ciModel.job.MediaTranscodeVideoObject;
import com.qcloud.cos.model.ciModel.job.MediaTtsConfig;
import com.qcloud.cos.model.ciModel.job.MediaVideoObject;
import com.qcloud.cos.model.ciModel.job.MsSharpen;
import com.qcloud.cos.model.ciModel.job.OutputFile;
import com.qcloud.cos.model.ciModel.job.ProcessResult;
import com.qcloud.cos.model.ciModel.job.QualityEstimate;
import com.qcloud.cos.model.ciModel.job.QualityEstimateConfig;
import com.qcloud.cos.model.ciModel.job.QualityEstimateItem;
import com.qcloud.cos.model.ciModel.job.SDRtoHDR;
import com.qcloud.cos.model.ciModel.job.Subtitle;
import com.qcloud.cos.model.ciModel.job.Subtitles;
import com.qcloud.cos.model.ciModel.job.SuperResolution;
import com.qcloud.cos.model.ciModel.job.TtsTpl;
import com.qcloud.cos.model.ciModel.job.VideoTag;
import com.qcloud.cos.model.ciModel.job.VideoTargetRec;
import com.qcloud.cos.model.ciModel.job.VqaPlusResult;
import com.qcloud.cos.model.ciModel.mediaInfo.MediaFormat;
import com.qcloud.cos.model.ciModel.mediaInfo.MediaInfoAudio;
import com.qcloud.cos.model.ciModel.mediaInfo.MediaInfoSubtitle;
import com.qcloud.cos.model.ciModel.mediaInfo.MediaInfoVideo;
import com.qcloud.cos.model.ciModel.persistence.ImageInfo;
import com.qcloud.cos.model.ciModel.template.MediaSegmentObject;
import com.qcloud.cos.model.ciModel.template.MediaSnapshotObject;
import com.qcloud.cos.model.ciModel.template.MediaWaterMarkImage;
import com.qcloud.cos.model.ciModel.template.MediaWaterMarkText;
import com.qcloud.cos.model.ciModel.template.MediaWatermark;
import com.qcloud.cos.model.ciModel.template.SpriteSnapshotConfig;

import java.util.List;

/**
 * MediaInfo 解析工具类
 */
public class ParserMediaInfoUtils {

    public static void ParsingMediaVideo(MediaInfoVideo video, String name, String value) {
        switch (name) {
            case "AvgFps":
                video.setAvgFps(value);
                break;
            case "CodecLongName":
                video.setCodecLongName(value);
                break;
            case "CodecName":
                video.setCodecName(value);
                break;
            case "CodecTag":
                video.setCodecTag(value);
                break;
            case "CodecTagString":
                video.setCodecTagString(value);
                break;
            case "CodecTimeBase":
                video.setCodecTimeBase(value);
                break;
            case "Duration":
                video.setDuration(value);
                break;
            case "FieldOrder":
                video.setFieldOrder(value);
                break;
            case "Fps":
                video.setFps(value);
                break;
            case "HasBFrame":
                video.setHasBFrame(value);
                break;
            case "Height":
                video.setHeight(value);
                break;
            case "Index":
                video.setIndex(value);
                break;
            case "Level":
                video.setLevel(value);
                break;
            case "PixFormat":
                video.setPixFormat(value);
                break;
            case "Profile":
                video.setProfile(value);
                break;
            case "RefFrames":
                video.setRefFrames(value);
                break;
            case "StartTime":
                video.setStartTime(value);
                break;
            case "Timebase":
                video.setTimebase(value);
                break;
            case "Width":
                video.setWidth(value);
                break;
            case "Dar":
                video.setDar(value);
                break;
            case "Rotation":
                video.setRotation(value);
                break;
            case "Sar":
                video.setSar(value);
                break;
            case "Bitrate":
                video.setBitrate(value);
            case "Language":
                video.setLanguage(value);
            case "NumFrames":
                video.setNumFrames(value);
            default:
                break;
        }
    }

    public static void ParsingMediaVideo(MediaVideoObject video, String name, String value) {
        switch (name) {
            case "Codec":
                video.setCodec(value);
                break;
            case "Crf":
                video.setCrf(value);
                break;
            case "Crop":
                video.setCrop(value);
                break;
            case "Fps":
                video.setFps(value);
                break;
            case "Height":
                video.setHeight(value);
                break;
            case "Width":
                video.setWidth(value);
                break;
            case "PixFmt":
                video.setPixFmt(value);
                break;
            case "Maxrate":
                video.setMaxrate(value);
                break;
            case "BufSize":
                video.setBufSize(value);
                break;
            case "Preset":
                video.setPreset(value);
                break;
            case "Bitrate":
                video.setBitrate(value);
                break;
            case "Profile":
                video.setProfile(value);
                break;
            case "AnimateOnlyKeepKeyFrame":
                video.setAnimateOnlyKeepKeyFrame(value);
                break;
            case "AnimateFramesPerSecond":
                video.setAnimateFramesPerSecond(value);
                break;
            case "AnimateTimeIntervalOfFrame":
                video.setAnimateTimeIntervalOfFrame(value);
                break;
            case "Gop":
                video.setGop(value);
                break;
            case "HlsTsTime":
                video.setHlsTsTime(value);
                break;
            case "LongShortMode":
                video.setLongShortMode(value);
                break;
            case "Pad":
                video.setPad(value);
                break;
            case "Qality":
                video.setQality(value);
                break;
            case "Quality":
                video.setQuality(value);
                break;
            case "Remove":
                video.setRemove(value);
                break;
            case "ScanMode":
                video.setScanMode(value);
                break;
            case "Language":
                video.setLanguage(value);
            case "Rotate":
                video.setRotate(value);
            default:
                break;
        }
    }

    public static void ParsingMediaVideo(MediaTranscodeVideoObject video, String name, String value) {
        switch (name) {
            case "Codec":
                video.setCodec(value);
                break;
            case "Crf":
                video.setCrf(value);
                break;
            case "Fps":
                video.setFps(value);
                break;
            case "Height":
                video.setHeight(value);
                break;
            case "Width":
                video.setWidth(value);
                break;
            case "Maxrate":
                video.setMaxrate(value);
                break;
            case "BufSize":
                video.setBufSize(value);
                break;
            case "Preset":
                video.setPreset(value);
                break;
            case "Bitrate":
                video.setBitrate(value);
                break;
            case "Profile":
                video.setProfile(value);
                break;
            case "Gop":
                video.setGop(value);
                break;
            case "Remove":
                video.setRemove(value);
                break;
            case "ScanMode":
                video.setScanMode(value);
                break;
            default:
                break;
        }
    }

    public static void ParsingMediaTimeInterval(MediaTimeIntervalObject timeInterval, String name, String value) {
        switch (name) {
            case "Duration":
                timeInterval.setDuration(value);
                break;
            case "Start":
                timeInterval.setStart(value);
                break;
            default:
                break;
        }
    }

    public static void ParsingDigitalWatermark(MediaDigitalWatermark watermark, String name, String value) {
        switch (name) {
            case "Type":
                watermark.setType(value);
                break;
            case "Version":
                watermark.setVersion(value);
                break;
            case "Message":
                watermark.setMessage(value);
                break;
            default:
                break;
        }
    }

    public static void ParsingSnapshot(MediaSnapshotObject snapshot, String name, String value) {
        switch (name) {
            case "Count":
                snapshot.setCount(value);
                break;
            case "Mode":
                snapshot.setMode(value);
                break;
            case "Start":
                snapshot.setStart(value);
                break;
            case "Width":
                snapshot.setWidth(value);
                break;
            case "Fps":
                snapshot.setFps(value);
                break;
            case "Height":
                snapshot.setHeight(value);
                break;
            case "CIParam":
                snapshot.setCiParam(value);
                break;
            case "IsCheckCount":
                snapshot.setIsCheckCount(value);
                break;
            case "IsCheckBlack":
                snapshot.setIsCheckBlack(value);
                break;
            case "BlackLevel":
                snapshot.setBlackLevel(value);
                break;
            case "TimeInterval":
                snapshot.setTimeInterval(value);
                break;
            case "PixelBlackThreshold":
                snapshot.setPixelBlackThreshold(value);
                break;
            case "SnapshotOutMode":
                snapshot.setSnapshotOutMode(value);
                break;
            default:
                break;
        }
    }

    public static void ParsingMediaAudio(MediaAudioObject audio, String name, String value) {
        switch (name) {
            case "Channels":
                audio.setChannels(value);
                break;
            case "Bitrate":
                audio.setBitrate(value);
                break;
            case "Samplerate":
                audio.setSamplerate(value);
                break;
            case "Codec":
                audio.setCodec(value);
                break;
            case "Profile":
                audio.setProfile(value);
                break;
            case "Remove":
                audio.setRemove(value);
                break;
            default:
                break;
        }
    }

    public static void ParsingTransConfig(MediaTransConfigObject transConfig, String name, String value) {
        switch (name) {
            case "ResoAdjMethod":
                transConfig.setResoAdjMethod(value);
                break;
            case "IsCheckReso":
                transConfig.setIsCheckReso(value);
                break;
            case "AdjDarMethod":
                transConfig.setAdjDarMethod(value);
                break;
            case "AudioBitrateAdjMethod":
                transConfig.setAudioBitrateAdjMethod(value);
                break;
            case "IsCheckAudioBitrate":
                transConfig.setIsCheckAudioBitrate(value);
                break;
            case "IsCheckVideoBitrate":
                transConfig.setIsCheckVideoBitrate(value);
                break;
            case "TransMode":
                transConfig.setTransMode(value);
                break;
            case "VideoBitrateAdjMethod":
                transConfig.setVideoBitrateAdjMethod(value);
                break;
            default:
                break;
        }
    }

    public static void ParsingWatermark(MediaWatermark watermark, String name, String value) {
        switch (name) {
            case "Dx":
                watermark.setDx(value);
                break;
            case "Dy":
                watermark.setDy(value);
                break;
            case "EndTime":
                watermark.setEndTime(value);
                break;
            case "LocMode":
                watermark.setLocMode(value);
                break;
            case "Pos":
                watermark.setPos(value);
                break;
            case "StartTime":
                watermark.setStartTime(value);
                break;
            case "Type":
                watermark.setType(value);
                break;
            default:
                break;
        }
    }

    public static void ParsingWatermarkText(MediaWaterMarkText text, String name, String value) {
        switch (name) {
            case "FontType":
                text.setFontType(value);
                break;
            case "Transparency":
                text.setTransparency(value);
                break;
            case "FontSize":
                text.setFontSize(value);
                break;
            case "Text":
                text.setText(value);
                break;
            case "FontColor":
                text.setFontColor(value);
                break;
            default:
                break;
        }
    }

    public static void ParsingWatermarkImage(MediaWaterMarkImage image, String name, String value) {
        switch (name) {
            case "Transparency":
                image.setTransparency(value);
                break;
            case "Height":
                image.setHeight(value);
                break;
            case "Width":
                image.setWidth(value);
                break;
            case "Mode":
                image.setMode(value);
                break;
            case "Url":
                image.setUrl(value);
                break;
            default:
                break;
        }
    }

    public static void ParsingMediaFormat(MediaFormat format, String name, String value) {
        switch (name) {
            case "Bitrate":
                format.setBitrate(value);
                break;
            case "Duration":
                format.setDuration(value);
                break;
            case "FormatLongName":
                format.setFormatLongName(value);
                break;
            case "FormatName":
                format.setFormatName(value);
                break;
            case "NumProgram":
                format.setNumProgram(value);
                break;
            case "NumStream":
                format.setNumStream(value);
                break;
            case "Size":
                format.setSize(value);
                break;
            case "StartTime":
                format.setStartTime(value);
                break;
            default:
                break;
        }
    }

    public static void ParsingStreamAudio(MediaInfoAudio audio, String name, String value) {
        switch (name) {
            case "Timebase":
                audio.setTimebase(value);
                break;
            case "StartTime":
                audio.setStartTime(value);
                break;
            case "SampleRate":
                audio.setSampleRate(value);
                break;
            case "SampleFmt":
                audio.setSampleFmt(value);
                break;
            case "Language":
                audio.setLanguage(value);
                break;
            case "Index":
                audio.setIndex(value);
                break;
            case "Duration":
                audio.setDuration(value);
                break;
            case "CodecTimeBase":
                audio.setCodecTimeBase(value);
                break;
            case "CodecTagString":
                audio.setCodecTagString(value);
                break;
            case "CodecTag":
                audio.setCodecTag(value);
                break;
            case "Bitrate":
                audio.setBitrate(value);
                break;
            case "Channel":
                audio.setChannel(value);
                break;
            case "ChannelLayout":
                audio.setChannelLayout(value);
                break;
            case "CodecLongName":
                audio.setCodecLongName(value);
                break;
            case "CodecName":
                audio.setCodecName(value);
                break;
            default:
                break;
        }
    }

    public static void ParsingStreamAudio(MediaAudioObject audio, String name, String value) {
        switch (name) {
            case "Codec":
                audio.setCodec(value);
                break;
            case "Samplerate":
                audio.setSamplerate(value);
                break;
            case "Bitrate":
                audio.setBitrate(value);
                break;
            case "Channels":
                audio.setChannels(value);
                break;
            case "Remove":
                audio.setRemove(value);
                break;
            default:
                break;
        }
    }

    public static void ParsingSubtitle(MediaInfoSubtitle subtitle, String name, String value) {
        switch (name) {
            case "Index":
                subtitle.setIndex(value);
                break;
            case "Language":
                subtitle.setLanguage(value);
                break;
            default:
                break;
        }
    }

    public static void ParsingRemoveWatermark(MediaRemoveWaterMark removeWatermark, String name, String value) {
        switch (name) {
            case "Switch":
                removeWatermark.set_switch(value);
                break;
            case "Dx":
                removeWatermark.setDx(value);
                break;
            case "Dy":
                removeWatermark.setDy(value);
                break;
            case "Height":
                removeWatermark.setHeight(value);
                break;
            case "Width":
                removeWatermark.setWidth(value);
                break;
            default:
                break;
        }
    }

    public static void ParsingAuditingUserInfo(UserInfo userInfo, String name, String value) {
        switch (name) {
            case "Nickname":
                userInfo.setNickname(value);
                break;
            case "AppId":
                userInfo.setAppId(value);
                break;
            case "TokenId":
                userInfo.setTokenId(value);
                break;
            case "DeviceId":
                userInfo.setDeviceId(value);
                break;
            case "IP":
                userInfo.setIp(value);
                break;
            case "Room":
                userInfo.setRoom(value);
                break;
            case "Type":
                userInfo.setType(value);
                break;
            default:
                break;
        }
    }

    public static void parsingLastLibResult(List<LibResult> results, String name, String value) {
        if (!results.isEmpty()) {
            parsingLibResults(results.get(results.size() - 1), name, value);
        }
    }

    public static void parsingAuditingListResultInfo(ListResult result, String name, String value) {
        switch (name) {
            case "ListName":
                result.setListName(value);
                break;
            case "Entity":
                result.setEntity(value);
                break;
            case "ListType":
                result.setListType(value);
                break;
            default:
                break;
        }
    }

    public static void parsingLibResults(LibResult result, String name, String value) {
        switch (name) {
            case "ImageId":
                result.setImageId(value);
                break;
            case "Score":
                result.setScore(value);
                break;
            default:
                break;
        }
    }

    public static void ParsingAuditingCommonInfo(AudtingCommonInfo obj, String name, String value) {
        switch (name) {
            case "Code":
                obj.setCode(value);
                break;
            case "Msg":
                obj.setMsg(value);
                break;
            case "HitFlag":
                obj.setHitFlag(value);
                break;
            case "Score":
                obj.setScore(value);
                break;
            case "Label":
                obj.setLabel(value);
                break;
            case "Category":
                obj.setCategory(value);
                break;
            case "Keywords":
                obj.setKeywords(value);
                break;
            case "Count":
                obj.setCount(value);
                break;
            case "SubLabel":
                obj.setSubLabel(value);
                break;
            default:
                break;
        }
    }

    public static void parseOrcInfo(List<OcrResults> obj, String name, String value) {
        if (obj != null && !obj.isEmpty()) {
            OcrResults ocrResults = obj.get(obj.size() - 1);
            switch (name) {
                case "Text":
                    ocrResults.setText(value);
                    break;
                case "Keywords":
                    ocrResults.setKeywords(value);
                    break;
                default:
                    break;
            }
        }
    }

    public static void parseObjectResultsInfo(PoliticsInfoObjectResults obj, String name, String value) {
        switch (name) {
            case "Name":
                obj.setName(value);
                break;
            default:
                break;
        }
    }

    public static void parseSectionInfo(SectionInfo sectionInfo, String name, String value) {
        switch (name) {
            case "StartByte":
                sectionInfo.setStartByte(value);
                break;
            case "Label":
                sectionInfo.setLabel(value);
                break;
            case "Result":
                sectionInfo.setResult(value);
                break;
            case "Url":
                sectionInfo.setUrl(value);
                break;
            case "OffsetTime":
                sectionInfo.setOffsetTime(value);
                break;
            case "Text":
                sectionInfo.setText(value);
                break;
            case "Duration":
                sectionInfo.setDuration(value);
                break;
            case "SubLabel":
                sectionInfo.setSubLabel(value);
                break;
            default:
                break;
        }
    }

    public static void parseSnapshotInfo(SnapshotInfo snapshotInfo, String name, String value) {
        switch (name) {
            case "Url":
                snapshotInfo.setUrl(value);
                break;
            case "SnapshotTime":
                snapshotInfo.setSnapshotTime(value);
                break;
            case "Text":
                snapshotInfo.setText(value);
                break;
            case "Label":
                snapshotInfo.setLabel(value);
                break;
            case "Result":
                snapshotInfo.setResult(value);
                break;
            default:
                break;
        }
    }

    public static void parseAudioSection(AudioSectionInfo audioSectionInfo, String name, String value) {
        switch (name) {
            case "Url":
                audioSectionInfo.setUrl(value);
                break;
            case "OffsetTime":
                audioSectionInfo.setOffsetTime(value);
                break;
            case "Text":
                audioSectionInfo.setText(value);
                break;
            case "Label":
                audioSectionInfo.setLabel(value);
                break;
            case "Result":
                audioSectionInfo.setResult(value);
                break;
            case "Duration":
                audioSectionInfo.setDuration(value);
                break;
            default:
                break;
        }
    }

    public static void parseMediaJobsDetail(MediaJobObject jobsDetail, String name, String value) {
        switch (name) {
            case "Code":
                jobsDetail.setCode(value);
                break;
            case "CreationTime":
                jobsDetail.setCreationTime(value);
                break;
            case "EndTime":
                jobsDetail.setEndTime(value);
                break;
            case "JobId":
                jobsDetail.setJobId(value);
                break;
            case "Message":
                jobsDetail.setMessage(value);
                break;
            case "QueueId":
                jobsDetail.setQueueId(value);
                break;
            case "State":
                jobsDetail.setState(value);
                break;
            case "Tag":
                jobsDetail.setTag(value);
                break;
            case "BucketName":
                jobsDetail.setBucketName(value);
                break;
            case "Progress":
                jobsDetail.setProgress(value);
                break;
            default:
                break;
        }
    }


    public static void ParsingSnapshotConfig(SpriteSnapshotConfig snapshotConfig, String name, String value) {
        switch (name) {
            case "CellHeight":
                snapshotConfig.setCellHeight(value);
                break;
            case "Padding":
                snapshotConfig.setPadding(value);
                break;
            case "Margin":
                snapshotConfig.setMargin(value);
                break;
            case "Color":
                snapshotConfig.setColor(value);
                break;
            case "Columns":
                snapshotConfig.setColumns(value);
                break;
            case "Lines":
                snapshotConfig.setLines(value);
                break;
            case "CellWidth":
                snapshotConfig.setCellWidth(value);
                break;
            default:
                break;
        }
    }

    public static void ParsingOutput(MediaOutputObject output, String name, String value) {
        switch (name) {
            case "Bucket":
                output.setBucket(value);
                break;
            case "Object":
                output.setObject(value);
                break;
            case "Region":
                output.setRegion(value);
                break;
            case "SpriteObject":
                output.setSpriteObject(value);
                break;
            case "AuObject":
                output.setAuObject(value);
                break;
            default:
                break;
        }
    }

    public static void ParsingSegment(MediaSegmentObject segment, String name, String value) {
        switch (name) {
            case "Format":
                segment.setFormat(value);
                break;
            case "Duration":
                segment.setDuration(value);
                break;
            default:
                break;
        }
    }

    public static void ParsingAuditingBatchImageJobDetail(BatchImageJobDetail jobsDetail, String name, String value) {
        switch (name) {
            case "Object":
                jobsDetail.setObject(value);
                break;
            case "DataId":
                jobsDetail.setDataId(value);
                break;
            case "Label":
                jobsDetail.setLabel(value);
                break;
            case "Result":
                jobsDetail.setResult(value);
                break;
            case "Score":
                jobsDetail.setScore(value);
            case "Text":
                jobsDetail.setText(value);
            case "SubLabel":
                jobsDetail.setSubLabel(value);
                break;
            case "Code":
                jobsDetail.setCode(value);
                break;
            case "Message":
                jobsDetail.setMessage(value);
                break;
            case "Url":
                jobsDetail.setUrl(value);
                break;
            case "JobId":
                jobsDetail.setJobId(value);
                break;
            case "Category":
                jobsDetail.setCategory(value);
                break;
            case "CompressionResult":
                jobsDetail.setCompressionResult(value);
                break;
            case "State":
                jobsDetail.setState(value);
                break;
            case "ForbidState":
                jobsDetail.setForbidState(value);
                break;
            default:
                break;
        }
    }


    public static void ParsingMediaResult(OutputFile outputFile, String name, String value) {
        switch (name) {
            case "Region":
                outputFile.setRegion(value);
                break;
            case "Bucket":
                outputFile.setBucket(value);
                break;
            case "ObjectPrefix":
                outputFile.setObjectPrefix(value);
                break;
            case "ObjectName":
                outputFile.setObjectName(value);
                break;
            default:
                break;
        }
    }

    public static void ParsingMd5Info(Md5Info md5Info, String name, String value) {
        switch (name) {
            case "Md5":
                md5Info.setMd5Info(value);
                break;
            case "ObjectName":
                md5Info.setObjectName(value);
                break;
            default:
                break;
        }
    }

    public static void ParsingImageInfo(ImageInfo imageInfo, String name, String value) {
        switch (name) {
            case "Ave":
                imageInfo.setAve(value);
                break;
            case "Format":
                imageInfo.setFormat(value);
                break;
            case "Height":
                imageInfo.setHeight(string2int(value));
                break;
            case "Orientation":
                imageInfo.setOrientation(string2int(value));
                break;
            case "Quality":
                imageInfo.setQuality(string2int(value));
                break;
            case "Width":
                imageInfo.setWidth(string2int(value));
                break;
            default:
                break;
        }
    }

    public static void ParsingProcessResult(ProcessResult processResult, String name, String value) {
        switch (name) {
            case "Etag":
                processResult.setEtag(value);
                break;
            case "Format":
                processResult.setFormat(value);
                break;
            case "Height":
                processResult.setHeight(value);
                break;
            case "Quality":
                processResult.setQuality(value);
                break;
            case "Size":
                processResult.setSize(value);
                break;
            case "Width":
                processResult.setWidth(value);
                break;
            default:
                break;
        }
    }

    private static int string2int(String str) {
        try {
            return Integer.parseInt(str);
        } catch (RuntimeException e) {
            return 0;
        }
    }


    public static void parseLanguageResult(LanguageResult result, String name, String value) {
        switch (name) {
            case "EndTime":
                result.setEndTime(value);
                break;
            case "Label":
                result.setLabel(value);
                break;
            case "Score":
                result.setScore(value);
                break;
            case "StartTime":
                result.setStartTime(value);
                break;
            default:
                break;
        }
    }

    public static void ParseConcatFragment(MediaConcatFragmentObject result, String name, String value) {
        switch (name) {
            case "Mode":
                result.setMode(value);
                break;
            case "Url":
                result.setUrl(value);
                break;
            case "StartTime":
                result.setStartTime(value);
                break;
            case "EndTime":
                result.setEndTime(value);
                break;
            case "Duration":
                result.setDuration(value);
                break;
            default:
                break;
        }
    }

    public static void ParseAudioMix(MediaAudioMixObject result, String name, String value) {
        switch (name) {
            case "AudioSource":
                result.setAudioSource(value);
                break;
            case "MixMode":
                result.setMixMode(value);
                break;
            case "Replace":
                result.setReplace(value);
                break;
            case "DirectMix":
                result.setDirectMix(value);
                break;
            default:
                break;
        }
    }

    public static void ParsingVideoTargetRec(VideoTargetRec videoTargetRec, String name, String value) {
        switch (name) {
            case "Body":
                videoTargetRec.setBody(value);
                break;
            case "Pet":
                videoTargetRec.setPet(value);
                break;
            case "Car":
                videoTargetRec.setCar(value);
                break;
            default:
                break;
        }
    }

    public static void ParseMediaBodyInfo(MediaBodyInfo videoTargetRec, String name, String value) {
        switch (name) {
            case "Score":
                videoTargetRec.setScore(value);
                break;
            case "Name":
                videoTargetRec.setName(value);
                break;

            default:
                break;
        }
    }

    public static void ParseMediaRecognition(MediaRecognition recognition, String name, String value) {
        switch (name) {
            case "Time":
                recognition.setTime(value);
                break;
            case "Url":
                recognition.setUrl(value);
                break;
            default:
                break;
        }
    }

    public static void ParseMediaRecognition(MediaTopkRecognition recognition, String name, String value) {
        switch (name) {
            case "Time":
                recognition.setTime(value);
                break;
            case "Url":
                recognition.setUrl(value);
                break;
            case "Score":
                recognition.setScore(value);
                break;
            default:
                break;
        }
    }

    public static void ParseLocation(ObjectResults.Location location, String name, String value) {
        switch (name) {
            case "Height":
                location.setHeight(value);
                break;
            case "Width":
                location.setWidth(value);
                break;
            case "Rotate":
                location.setRotate(value);
                break;
            case "X":
                location.setX(value);
                break;
            case "Y":
                location.setY(value);
                break;
            default:
                break;
        }
    }

    public static void ParseAudioConfig(AudioConfig audioConfig, String name, String value) {
        switch (name) {
            case "Channels":
                audioConfig.setChannels(value);
                break;
            case "Codec":
                audioConfig.setCodec(value);
                break;
            case "Samplerate":
                audioConfig.setSamplerate(value);
                break;
            case "Bitrate":
                audioConfig.setBitrate(value);
                break;
            default:
                break;
        }
    }

    public static void ParsingInput(BatchInputObject input, String name, String value) {
        switch (name) {
            case "Manifest":
                input.setManifest(value);
                break;
            case "Prefix":
                input.setPrefix(value);
                break;
            case "UrlFile":
                input.setUrlFile(value);
                break;
            default:
                break;
        }
    }

    public static void ParseTtsConfig(MediaTtsConfig ttsConfig, String name, String value) {
        switch (name) {
            case "Input":
                ttsConfig.setInput(value);
                break;
            case "InputType":
                ttsConfig.setInputType(value);
                break;
            default:
                break;
        }
    }

    public static void ParseTtsTpl(TtsTpl ttsTpl, String name, String value) {
        switch (name) {
            case "Codec":
                ttsTpl.setCodec(value);
                break;
            case "Mode":
                ttsTpl.setMode(value);
                break;
            case "Speed":
                ttsTpl.setSpeed(value);
                break;
            case "Volume":
                ttsTpl.setVolume(value);
                break;
            case "VoiceType":
                ttsTpl.setVoiceType(value);
                break;
            default:
                break;
        }
    }

    public static void ParseContainer(MediaContainerObject container, String name, String value) {
        switch (name) {
            case "Format":
                container.setFormat(value);
                break;
            default:
                break;
        }
    }

    public static void ParsingSuperResolution(SuperResolution superResolution, String name, String value) {
        switch (name) {
            case "Resolution":
                superResolution.setResolution(value);
                break;
            case "Version":
                superResolution.setVersion(value);
                break;
            case "EnableScaleUp":
                superResolution.setEnableScaleUp(value);
                break;
            default:
                break;
        }
    }

    public static void ParsingColorEnhance(ColorEnhance colorEnhance, String name, String value) {
        switch (name) {
            case "Saturation":
                colorEnhance.setSaturation(value);
                break;
            case "Correction":
                colorEnhance.setCorrection(value);
                break;
            case "Contrast":
                colorEnhance.setContrast(value);
                break;
            default:
                break;
        }
    }

    public static void ParsingMsSharpen(MsSharpen msSharpen, String name, String value) {
        switch (name) {
            case "SharpenLevel":
                msSharpen.setSharpenLevel(value);
                break;
            default:
                break;
        }
    }

    public static void ParsingSDRtoHDR(SDRtoHDR sdrToHDR, String name, String value) {
        switch (name) {
            case "HdrMode":
                sdrToHDR.setHdrMode(value);
                break;
            default:
                break;
        }
    }

    public static void ParsingFrameEnhance(FrameEnhance frameEnhance, String name, String value) {
        switch (name) {
            case "FrameDoubling":
                frameEnhance.setFrameDoubling(value);
                break;
            default:
                break;
        }
    }

    public static void ParsingAudioMixArray(List<MediaAudioMixObject> audioMixArray, String name, String value) {
        if (!audioMixArray.isEmpty()) {
            MediaAudioMixObject mediaAudioMixObject = audioMixArray.get(audioMixArray.size() - 1);
            ParseAudioMix(mediaAudioMixObject, name, value);
        }
    }

    public static void ParsingEffectConfig(List<MediaAudioMixObject> audioMixArray, String name, String value) {
        if (!audioMixArray.isEmpty()) {
            MediaAudioMixObject mediaAudioMixObject = audioMixArray.get(audioMixArray.size() - 1);
            ParsingEffectConfig(mediaAudioMixObject.getEffectConfig(), name, value);
        }
    }

    public static void ParsingEffectConfig(EffectConfig effectConfig, String name, String value) {
        switch (name) {
            case "EnableBgmFade":
                effectConfig.setEnableBgmFade(value);
                break;
            case "BgmFadeTime":
                effectConfig.setBgmFadeTime(value);
                break;
            case "EnableEndFadeout":
                effectConfig.setEnableEndFadeout(value);
                break;
            case "EnableStartFadein":
                effectConfig.setEnableStartFadein(value);
                break;
            case "EndFadeoutTime":
                effectConfig.setEndFadeoutTime(value);
                break;
            case "StartFadeinTime":
                effectConfig.setStartFadeinTime(value);
                break;
            default:
                break;
        }
    }

    public static void ParsingSubtitles(Subtitles subtitles, String name, String value) {
        List<Subtitle> subtitle = subtitles.getSubtitle();
        if (!subtitle.isEmpty()) {
            Subtitle subtitle1 = subtitle.get(subtitle.size() - 1);
            switch (name) {
                case "Url":
                    subtitle1.setUrl(value);
                    break;
                case "FontColor":
                    subtitle1.setFontColor(value);
                    break;
                case "FontSize":
                    subtitle1.setFontSize(value);
                    break;
                case "FontType":
                    subtitle1.setFontType(value);
                    break;
                case "Embed":
                    subtitle1.setEmbed(value);
                    break;
                case "VMargin":
                    subtitle1.setvMargin(value);
                    break;
                case "OutlineColor":
                    subtitle1.setOutlineColor(value);
                    break;
                default:
                    break;
            }
        }
    }

    public static void parseMediaJobOperation(MediaJobOperation operation, String name, String value) {
        switch (name) {
            case "TemplateId":
                operation.setTemplateId(value);
                break;
            case "TemplateName":
                operation.setTemplateName(value);
                break;
            case "WatermarkTemplateId":
                operation.getWatermarkTemplateId().add(value);
                break;
            case "UserData":
                operation.setUserData(value);
                break;
            case "JobLevel":
                operation.setJobLevel(value);
                break;
            case "DecryptKey":
                operation.setDecryptKey(value);
                break;
            default:
                break;
        }
    }

    public static void ParseVideoTag(VideoTag videoTag, String name, String value) {
        switch (name) {
            case "Scenario":
                videoTag.setScenario(value);
                break;
            default:
                break;
        }
    }

    public static void ParsingAuditingLiveInfo(AuditingLiveInfo auditingLiveInfo, String name, String value) {
        switch (name) {
            case "Output":
                auditingLiveInfo.setOutput(value);
                break;
            case "StartTime":
                auditingLiveInfo.setStartTime(value);
                break;
            case "EndTime":
                auditingLiveInfo.setEndTime(value);
                break;
            default:
                break;
        }
    }

    public static void ParsingAuditingRecordInfo(AuditingRecordInfo recordInfo, String name, String value) {
        switch (name) {
            case "Code":
                recordInfo.setCode(value);
                break;
            case "Message":
                recordInfo.setMessage(value);
                break;
            default:
                break;
        }
    }

    public static void ParsingAuditingOutput(AuditingCosOutput output, String name, String value) {
        switch (name) {
            case "Bucket":
                output.setBucket(value);
                break;
            case "Region":
                output.setRegion(value);
                break;
            case "Object":
                output.setObject(value);
                break;
            default:
                break;
        }
    }

    public static void ParseQualityEstimateConfig(QualityEstimateConfig qualityEstimateConfig, String name, String value) {
        switch (name) {
            case "Mode":
                qualityEstimateConfig.setMode(value);
                break;
            case "Rotate":
                qualityEstimateConfig.setRotate(value);
                break;
            default:
                break;
        }
    }

    public static void ParseVqaPlusResult(VqaPlusResult vqaPlusResult, String name, String value) {
        switch (name) {
            case "NoAudio":
                vqaPlusResult.setNoAudio(value);
                break;
            case "NoVideo":
                vqaPlusResult.setNoVideo(value);
                break;
            default:
                break;
        }
    }

    public static void ParseDetailedResult(DetailedResult detailedResults, String name, String value) {
        switch (name) {
            case "Type":
                detailedResults.setType(value);
                break;
            default:
                break;
        }
    }

    public static void ParseQualityEstimateItem(QualityEstimateItem qei, String name, String value) {
        switch (name) {
            case "Confidence":
                qei.setConfidence(value);
                break;
            case "EndTimeOffset":
                qei.setEndTimeOffset(value);
                break;
            case "StartTimeOffset":
                qei.setStartTimeOffset(value);
                break;
            case "AreaCoordSet":
                qei.getAreaCoordSet().add(value);
                break;
            default:
                break;
        }
    }

    public static void ParseQualityEstimate(QualityEstimate qualityEstimate, String name, String value) {
        switch (name) {
            case "Score":
                qualityEstimate.setScore(value);
                break;
            default:
                break;
        }
    }
}