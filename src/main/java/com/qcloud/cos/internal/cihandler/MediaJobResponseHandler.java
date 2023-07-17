package com.qcloud.cos.internal.cihandler;

import com.qcloud.cos.internal.ParserMediaInfoUtils;
import com.qcloud.cos.model.ciModel.common.MediaOutputObject;
import com.qcloud.cos.model.ciModel.job.DetailedResult;
import com.qcloud.cos.model.ciModel.job.ExtractDigitalWatermark;
import com.qcloud.cos.model.ciModel.job.Md5Info;
import com.qcloud.cos.model.ciModel.job.MediaAudioMixObject;
import com.qcloud.cos.model.ciModel.job.MediaAudioObject;
import com.qcloud.cos.model.ciModel.job.MediaBodyInfo;
import com.qcloud.cos.model.ciModel.job.MediaConcatFragmentObject;
import com.qcloud.cos.model.ciModel.job.MediaConcatTemplateObject;
import com.qcloud.cos.model.ciModel.job.MediaContainerObject;
import com.qcloud.cos.model.ciModel.job.MediaDigitalWatermark;
import com.qcloud.cos.model.ciModel.job.MediaJobObject;
import com.qcloud.cos.model.ciModel.job.MediaJobOperation;
import com.qcloud.cos.model.ciModel.job.MediaJobResponse;
import com.qcloud.cos.model.ciModel.job.MediaPicProcessTemplateObject;
import com.qcloud.cos.model.ciModel.job.MediaRecognition;
import com.qcloud.cos.model.ciModel.job.MediaRemoveWaterMark;
import com.qcloud.cos.model.ciModel.job.MediaTimeIntervalObject;
import com.qcloud.cos.model.ciModel.job.MediaTopkRecognition;
import com.qcloud.cos.model.ciModel.job.MediaTransConfigObject;
import com.qcloud.cos.model.ciModel.job.MediaTranscodeVideoObject;
import com.qcloud.cos.model.ciModel.job.MediaVideoObject;
import com.qcloud.cos.model.ciModel.job.OutputFile;
import com.qcloud.cos.model.ciModel.job.ProcessResult;
import com.qcloud.cos.model.ciModel.job.QualityEstimateItem;
import com.qcloud.cos.model.ciModel.job.Subtitle;
import com.qcloud.cos.model.ciModel.job.Subtitles;
import com.qcloud.cos.model.ciModel.job.VideoTargetRec;
import com.qcloud.cos.model.ciModel.job.VqaPlusResult;
import com.qcloud.cos.model.ciModel.mediaInfo.MediaFormat;
import com.qcloud.cos.model.ciModel.mediaInfo.MediaInfoAudio;
import com.qcloud.cos.model.ciModel.mediaInfo.MediaInfoSubtitle;
import com.qcloud.cos.model.ciModel.mediaInfo.MediaInfoVideo;
import com.qcloud.cos.model.ciModel.persistence.ImageInfo;
import com.qcloud.cos.model.ciModel.template.MediaSegmentObject;
import com.qcloud.cos.model.ciModel.template.MediaSnapshotObject;
import com.qcloud.cos.model.ciModel.template.SpriteSnapshotConfig;
import org.xml.sax.Attributes;

import java.util.List;

public class MediaJobResponseHandler extends CIAbstractHandler {
    MediaJobResponse response = new MediaJobResponse();
    List<MediaConcatFragmentObject> concatFragmentList = response.getJobsDetail().getOperation().getMediaConcatTemplate().getConcatFragmentList();

    @Override
    protected void doStartElement(String uri, String name, String qName, Attributes attrs) {
        VideoTargetRec videoTargetRec = response.getJobsDetail().getOperation().getVideoTargetRec();
        if ("ConcatFragment".equals(name)) {
            concatFragmentList.add(new MediaConcatFragmentObject());
        } else if (in("Response", "JobsDetail", "Operation", "VideoTargetRecResult", "BodyRecognition") && "BodyInfo".equalsIgnoreCase(name)) {
            MediaRecognition bodyRecognition = videoTargetRec.getBodyRecognition();
            bodyRecognition.getInfoList().add(new MediaBodyInfo());
        } else if (in("Response", "JobsDetail", "Operation", "VideoTargetRecResult", "CarRecognition") && "CarInfo".equalsIgnoreCase(name)) {
            MediaRecognition carRecognition = videoTargetRec.getCarRecognition();
            carRecognition.getInfoList().add(new MediaBodyInfo());
        } else if (in("Response", "JobsDetail", "Operation", "VideoTargetRecResult", "PetRecognition") && "PetInfo".equalsIgnoreCase(name)) {
            MediaRecognition petRecognition = videoTargetRec.getPetRecognition();
            petRecognition.getInfoList().add(new MediaBodyInfo());
        } else if (in("Response", "JobsDetail", "Operation", "VideoTargetRecResult") && "TopKRecognition".equalsIgnoreCase(name)) {
            List<MediaTopkRecognition> topKRecognition = videoTargetRec.getTopKRecognition();
            topKRecognition.add(new MediaTopkRecognition());
        } else if (in("Response", "JobsDetail", "Operation", "VideoTargetRecResult", "TopKRecognition")) {
            List<MediaTopkRecognition> topKRecognition = videoTargetRec.getTopKRecognition();
            if (!topKRecognition.isEmpty()) {
                MediaTopkRecognition mediaTopkRecognition = topKRecognition.get(topKRecognition.size() - 1);
                if ("BodyInfo".equalsIgnoreCase(name)) {
                    mediaTopkRecognition.getBodyInfoList().add(new MediaBodyInfo());
                } else if ("CarInfo".equalsIgnoreCase(name)) {
                    mediaTopkRecognition.getCarInfoList().add(new MediaBodyInfo());
                } else if ("PetInfo".equalsIgnoreCase(name)) {
                    mediaTopkRecognition.getPetInfoList().add(new MediaBodyInfo());
                }
            }
        } else if (in("Response", "JobsDetail", "Operation", "Transcode") && "AudioMixArray".equalsIgnoreCase(name)) {
            List<MediaAudioMixObject> audioMixArray = response.getJobsDetail().getOperation().getTranscode().getAudioMixArray();
            audioMixArray.add(new MediaAudioMixObject());
        } else if (in("Response", "JobsDetail", "Operation", "Subtitles") && "Subtitle".equalsIgnoreCase(name)) {
            List<Subtitle> subtitle = response.getJobsDetail().getOperation().getSubtitles().getSubtitle();
            subtitle.add(new Subtitle());
        } else if (in("Response", "JobsDetail", "Operation", "QualityEstimate", "VqaPlusResult") && "DetailedResult".equalsIgnoreCase(name)) {
            List<DetailedResult> detailedResults = response.getJobsDetail().getOperation().getQualityEstimate().getVqaPlusResult().getDetailedResults();
            detailedResults.add(new DetailedResult());
        } else if (in("Response", "JobsDetail", "Operation", "QualityEstimate", "VqaPlusResult", "DetailedResult") && "Items".equalsIgnoreCase(name)) {
            List<DetailedResult> detailedResults = response.getJobsDetail().getOperation().getQualityEstimate().getVqaPlusResult().getDetailedResults();
            if (!detailedResults.isEmpty()) {
                DetailedResult detailedResult = detailedResults.get(detailedResults.size() - 1);
                List<QualityEstimateItem> items = detailedResult.getItems();
                items.add(new QualityEstimateItem());
            }
        }
    }

    @Override
    protected void doEndElement(String uri, String name, String qName) {
        MediaJobObject jobsDetail = response.getJobsDetail();
        if (in("Response", "JobsDetail")) {
            ParserMediaInfoUtils.parseMediaJobsDetail(jobsDetail, name, getText());
        } else if (in("Response", "JobsDetail", "Input")) {
            jobsDetail.getInput().setObject(getText());
        } else if (in("Response", "JobsDetail", "Operation")) {
            ParserMediaInfoUtils.parseMediaJobOperation(jobsDetail.getOperation(), name, getText());
        } else if (in("Response", "JobsDetail", "Operation", "MediaInfo", "Format")) {
            MediaFormat format = jobsDetail.getOperation().getMediaInfo().getFormat();
            ParserMediaInfoUtils.ParsingMediaFormat(format, name, getText());
        } else if (in("Response", "JobsDetail", "Operation", "MediaInfo", "Stream", "Audio")) {
            MediaInfoAudio audio = jobsDetail.getOperation().getMediaInfo().getStream().getAudio();
            ParserMediaInfoUtils.ParsingStreamAudio(audio, name, getText());
        } else if (in("Response", "JobsDetail", "Operation", "MediaInfo", "Stream", "Subtitle")) {
            MediaInfoSubtitle subtitle = jobsDetail.getOperation().getMediaInfo().getStream().getSubtitle();
            ParserMediaInfoUtils.ParsingSubtitle(subtitle, name, getText());
        } else if (in("Response", "JobsDetail", "Operation", "MediaInfo", "Stream", "Video")) {
            MediaInfoVideo video = jobsDetail.getOperation().getMediaInfo().getStream().getVideo();
            ParserMediaInfoUtils.ParsingMediaVideo(video, name, getText());
        } else if (in("Response", "JobsDetail", "Operation", "RemoveWatermark")) {
            MediaRemoveWaterMark removeWatermark = jobsDetail.getOperation().getRemoveWatermark();
            ParserMediaInfoUtils.ParsingRemoveWatermark(removeWatermark, name, getText());
        } else if (in("Response", "JobsDetail", "Operation", "Transcode", "Container")) {
            if ("Format".equalsIgnoreCase(name))
                jobsDetail.getOperation().getTranscode().getContainer().setFormat(getText());
        } else if (in("Response", "JobsDetail", "Operation", "Transcode", "Video")) {
            MediaAudioObject audio = jobsDetail.getOperation().getTranscode().getAudio();
            ParserMediaInfoUtils.ParsingStreamAudio(audio, name, getText());
        } else if (in("Response", "JobsDetail", "Operation", "Transcode", "Audio")) {
            MediaTranscodeVideoObject video = jobsDetail.getOperation().getTranscode().getVideo();
            ParserMediaInfoUtils.ParsingMediaVideo(video, name, getText());
        } else if (in("Response", "JobsDetail", "Operation", "Transcode", "TransConfig")) {
            MediaTransConfigObject transConfig = jobsDetail.getOperation().getTranscode().getTransConfig();
            ParserMediaInfoUtils.ParsingTransConfig(transConfig, name, getText());
        } else if (in("Response", "JobsDetail", "Operation", "Transcode", "TimeInterval")) {
            MediaTimeIntervalObject timeInterval = jobsDetail.getOperation().getTranscode().getTimeInterval();
            ParserMediaInfoUtils.ParsingMediaTimeInterval(timeInterval, name, getText());
        } else if (in("Response", "JobsDetail", "Operation", "DigitalWatermark")) {
            MediaDigitalWatermark digitalWatermark = response.getJobsDetail().getOperation().getDigitalWatermark();
            ParserMediaInfoUtils.ParsingDigitalWatermark(digitalWatermark, name, getText());
        } else if (in("Response", "JobsDetail", "Operation", "ExtractDigitalWatermark")) {
            ExtractDigitalWatermark digitalWatermark = response.getJobsDetail().getOperation().getExtractDigitalWatermark();
            ParserMediaInfoUtils.ParsingDigitalWatermark(digitalWatermark, name, getText());
        } else if (in("Response", "JobsDetail", "Operation", "Snapshot")) {
            MediaSnapshotObject snapshot = response.getJobsDetail().getOperation().getSnapshot();
            ParserMediaInfoUtils.ParsingSnapshot(snapshot, name, getText());
        } else if (in("Response", "JobsDetail", "Operation", "Segment")) {
            MediaSegmentObject segment = response.getJobsDetail().getOperation().getSegment();
            ParserMediaInfoUtils.ParsingSegment(segment, name, getText());
        } else if (in("Response", "JobsDetail", "Operation", "Snapshot", "SpriteSnapshotConfig")) {
            SpriteSnapshotConfig snapshotConfig = response.getJobsDetail().getOperation().getSnapshot().getSnapshotConfig();
            ParserMediaInfoUtils.ParsingSnapshotConfig(snapshotConfig, name, getText());
        } else if (in("Response", "JobsDetail", "Operation", "Output")) {
            MediaOutputObject output = jobsDetail.getOperation().getOutput();
            ParserMediaInfoUtils.ParsingOutput(output, name, getText());
        }
        MediaConcatTemplateObject mediaConcatTemplate = response.getJobsDetail().getOperation().getMediaConcatTemplate();
        if (in("Response", "JobsDetail", "Operation", "ConcatTemplate", "ConcatFragment")) {
            MediaConcatFragmentObject mediaConcatFragmentObject = concatFragmentList.get(concatFragmentList.size() - 1);
            switch (name) {
                case "Mode":
                    mediaConcatFragmentObject.setMode(getText());
                    break;
                case "Url":
                    mediaConcatFragmentObject.setUrl(getText());
                    break;
                case "StartTime":
                    mediaConcatFragmentObject.setStartTime(getText());
                    break;
                case "EndTime":
                    mediaConcatFragmentObject.setEndTime(getText());
                    break;
                default:
                    break;
            }

        } else if (in("Response", "JobsDetail", "Operation", "ConcatTemplate", "Audio")) {
            MediaAudioObject audio = mediaConcatTemplate.getAudio();
            ParserMediaInfoUtils.ParsingMediaAudio(audio, name, getText());
        } else if (in("Response", "JobsDetail", "Operation", "ConcatTemplate", "Video")) {
            MediaVideoObject video = mediaConcatTemplate.getVideo();
            ParserMediaInfoUtils.ParsingMediaVideo(video, name, getText());
        } else if (in("Response", "JobsDetail", "Operation", "ConcatTemplate", "Container")) {
            MediaContainerObject container = mediaConcatTemplate.getContainer();
            if ("Format".equals(name)) {
                container.setFormat(getText());
            }
        } else if (in("Response", "JobsDetail", "Operation", "ConcatTemplate")) {
            if ("Index".equals(name)) {
                mediaConcatTemplate.setIndex(getText());
            }
        } else if (in("Response", "JobsDetail", "Operation", "MediaResult", "OutputFile")) {
            OutputFile outputFile = jobsDetail.getOperation().getMediaResult().getOutputFile();
            ParserMediaInfoUtils.ParsingMediaResult(outputFile, name, getText());
        } else if (in("Response", "JobsDetail", "Operation", "MediaResult", "OutputFile", "Md5Info")) {
            Md5Info md5Info = jobsDetail.getOperation().getMediaResult().getOutputFile().getMd5Info();
            ParserMediaInfoUtils.ParsingMd5Info(md5Info, name, getText());
        } else if (in("Response", "JobsDetail", "Operation", "PicProcess")) {
            MediaPicProcessTemplateObject picProcess = jobsDetail.getOperation().getPicProcess();
            if ("IsPicInfo".equalsIgnoreCase(name)) {
                picProcess.setIsPicInfo(getText());
            } else if ("ProcessRule".equalsIgnoreCase(name)) {
                picProcess.setProcessRule(getText());
            }
        } else if (in("Response", "JobsDetail", "Operation", "PicProcess")) {
            MediaPicProcessTemplateObject picProcess = jobsDetail.getOperation().getPicProcess();
            if ("IsPicInfo".equalsIgnoreCase(name)) {
                picProcess.setIsPicInfo(getText());
            } else if ("ProcessRule".equalsIgnoreCase(name)) {
                picProcess.setProcessRule(getText());
            }
        } else if (in("Response", "JobsDetail", "Operation", "PicProcessResult")) {
            if ("ObjectName".equalsIgnoreCase(name)) {
                jobsDetail.getOperation().getPicProcessResult().setObjectName(getText());
            }
        } else if (in("Response", "JobsDetail", "Operation", "PicProcessResult", "OriginalInfo")) {
            if ("Etag".equalsIgnoreCase(name)) {
                jobsDetail.getOperation().getPicProcessResult().getOriginalInfo().setEtag(getText());
            }
        } else if (in("Response", "JobsDetail", "Operation", "PicProcessResult", "OriginalInfo", "ImageInfo")) {
            ImageInfo imageInfo = jobsDetail.getOperation().getPicProcessResult().getOriginalInfo().getImageInfo();
            ParserMediaInfoUtils.ParsingImageInfo(imageInfo, name, getText());
        } else if (in("Response", "JobsDetail", "Operation", "PicProcessResult", "ProcessResult")) {
            ProcessResult processResult = jobsDetail.getOperation().getPicProcessResult().getProcessResult();
            ParserMediaInfoUtils.ParsingProcessResult(processResult, name, getText());
        } else if (in("Response", "JobsDetail", "Operation", "VideoTargetRec")) {
            VideoTargetRec videoTargetRec = jobsDetail.getOperation().getVideoTargetRec();
            ParserMediaInfoUtils.ParsingVideoTargetRec(videoTargetRec, name, getText());
        } else if (in("Response", "JobsDetail", "Operation", "VideoTargetRecResult", "BodyRecognition")) {
            VideoTargetRec videoTargetRec = jobsDetail.getOperation().getVideoTargetRec();
            ParserMediaInfoUtils.ParseMediaRecognition(videoTargetRec.getBodyRecognition(), name, getText());
        } else if (in("Response", "JobsDetail", "Operation", "VideoTargetRecResult", "CarRecognition")) {
            VideoTargetRec videoTargetRec = jobsDetail.getOperation().getVideoTargetRec();
            ParserMediaInfoUtils.ParseMediaRecognition(videoTargetRec.getCarRecognition(), name, getText());
        } else if (in("Response", "JobsDetail", "Operation", "VideoTargetRecResult", "PetRecognition")) {
            VideoTargetRec videoTargetRec = jobsDetail.getOperation().getVideoTargetRec();
            ParserMediaInfoUtils.ParseMediaRecognition(videoTargetRec.getPetRecognition(), name, getText());
        } else if (in("Response", "JobsDetail", "Operation", "VideoTargetRecResult", "TopKRecognition")) {
            VideoTargetRec videoTargetRec = jobsDetail.getOperation().getVideoTargetRec();
            List<MediaTopkRecognition> topKRecognition = videoTargetRec.getTopKRecognition();
            if (!topKRecognition.isEmpty()) {
                MediaTopkRecognition mediaTopkRecognition = topKRecognition.get(topKRecognition.size() - 1);
                ParserMediaInfoUtils.ParseMediaRecognition(mediaTopkRecognition, name, getText());
            }
        } else if (in("Response", "JobsDetail", "Operation", "VideoTargetRecResult", "BodyRecognition", "BodyInfo")) {
            VideoTargetRec videoTargetRec = jobsDetail.getOperation().getVideoTargetRec();
            List<MediaBodyInfo> bodyInfoList = videoTargetRec.getBodyRecognition().getInfoList();
            if (!bodyInfoList.isEmpty()) {
                MediaBodyInfo mediaBodyInfo = bodyInfoList.get(bodyInfoList.size() - 1);
                ParserMediaInfoUtils.ParseMediaBodyInfo(mediaBodyInfo, name, getText());
            }
        } else if (in("Response", "JobsDetail", "Operation", "VideoTargetRecResult", "CarRecognition", "CarInfo")) {
            VideoTargetRec videoTargetRec = jobsDetail.getOperation().getVideoTargetRec();
            List<MediaBodyInfo> catInfoList = videoTargetRec.getCarRecognition().getInfoList();
            if (!catInfoList.isEmpty()) {
                MediaBodyInfo mediaBodyInfo = catInfoList.get(catInfoList.size() - 1);
                ParserMediaInfoUtils.ParseMediaBodyInfo(mediaBodyInfo, name, getText());
            }
        } else if (in("Response", "JobsDetail", "Operation", "VideoTargetRecResult", "PetRecognition", "PetInfo")) {
            VideoTargetRec videoTargetRec = jobsDetail.getOperation().getVideoTargetRec();
            List<MediaBodyInfo> petInfo = videoTargetRec.getPetRecognition().getInfoList();
            if (!petInfo.isEmpty()) {
                MediaBodyInfo mediaBodyInfo = petInfo.get(petInfo.size() - 1);
                ParserMediaInfoUtils.ParseMediaBodyInfo(mediaBodyInfo, name, getText());
            }
        } else if (in("Response", "JobsDetail", "Operation", "VideoTargetRecResult", "BodyRecognition", "BodyInfo", "Location")) {
            VideoTargetRec videoTargetRec = jobsDetail.getOperation().getVideoTargetRec();
            List<MediaBodyInfo> bodyInfoList = videoTargetRec.getBodyRecognition().getInfoList();
            if (!bodyInfoList.isEmpty()) {
                MediaBodyInfo mediaBodyInfo = bodyInfoList.get(bodyInfoList.size() - 1);
                ParserMediaInfoUtils.ParseLocation(mediaBodyInfo.getLocation(), name, getText());
            }
        } else if (in("Response", "JobsDetail", "Operation", "VideoTargetRecResult", "CarRecognition", "CarInfo", "Location")) {
            VideoTargetRec videoTargetRec = jobsDetail.getOperation().getVideoTargetRec();
            List<MediaBodyInfo> catInfoList = videoTargetRec.getCarRecognition().getInfoList();
            if (!catInfoList.isEmpty()) {
                MediaBodyInfo mediaBodyInfo = catInfoList.get(catInfoList.size() - 1);
                ParserMediaInfoUtils.ParseLocation(mediaBodyInfo.getLocation(), name, getText());
            }
        } else if (in("Response", "JobsDetail", "Operation", "VideoTargetRecResult", "PetRecognition", "PetInfo", "Location")) {
            VideoTargetRec videoTargetRec = jobsDetail.getOperation().getVideoTargetRec();
            List<MediaBodyInfo> petInfo = videoTargetRec.getPetRecognition().getInfoList();
            if (!petInfo.isEmpty()) {
                MediaBodyInfo mediaBodyInfo = petInfo.get(petInfo.size() - 1);
                ParserMediaInfoUtils.ParseLocation(mediaBodyInfo.getLocation(), name, getText());
            }
        } else if (in("Response", "JobsDetail", "Operation", "VideoTargetRecResult", "TopKRecognition", "BodyInfo")) {
            VideoTargetRec videoTargetRec = jobsDetail.getOperation().getVideoTargetRec();
            List<MediaTopkRecognition> topKRecognition = videoTargetRec.getTopKRecognition();
            if (!topKRecognition.isEmpty()) {
                List<MediaBodyInfo> bodyInfoList = topKRecognition.get(topKRecognition.size() - 1).getBodyInfoList();
                if (!bodyInfoList.isEmpty()) {
                    MediaBodyInfo mediaBodyInfo = bodyInfoList.get(bodyInfoList.size() - 1);
                    ParserMediaInfoUtils.ParseMediaBodyInfo(mediaBodyInfo, name, getText());
                }
            }
        } else if (in("Response", "JobsDetail", "Operation", "VideoTargetRecResult", "TopKRecognition", "CarInfo")) {
            VideoTargetRec videoTargetRec = jobsDetail.getOperation().getVideoTargetRec();
            List<MediaTopkRecognition> topKRecognition = videoTargetRec.getTopKRecognition();
            if (!topKRecognition.isEmpty()) {
                List<MediaBodyInfo> bodyInfoList = topKRecognition.get(topKRecognition.size() - 1).getCarInfoList();
                if (!bodyInfoList.isEmpty()) {
                    MediaBodyInfo mediaBodyInfo = bodyInfoList.get(bodyInfoList.size() - 1);
                    ParserMediaInfoUtils.ParseMediaBodyInfo(mediaBodyInfo, name, getText());
                }
            }
        } else if (in("Response", "JobsDetail", "Operation", "VideoTargetRecResult", "TopKRecognition", "PetInfo")) {
            VideoTargetRec videoTargetRec = jobsDetail.getOperation().getVideoTargetRec();
            List<MediaTopkRecognition> topKRecognition = videoTargetRec.getTopKRecognition();
            if (!topKRecognition.isEmpty()) {
                List<MediaBodyInfo> bodyInfoList = topKRecognition.get(topKRecognition.size() - 1).getPetInfoList();
                if (!bodyInfoList.isEmpty()) {
                    MediaBodyInfo mediaBodyInfo = bodyInfoList.get(bodyInfoList.size() - 1);
                    ParserMediaInfoUtils.ParseMediaBodyInfo(mediaBodyInfo, name, getText());
                }
            }
        } else if (in("Response", "JobsDetail", "Operation", "VideoTargetRecResult", "TopKRecognition", "BodyInfo", "Location")) {
            VideoTargetRec videoTargetRec = jobsDetail.getOperation().getVideoTargetRec();
            List<MediaTopkRecognition> topKRecognition = videoTargetRec.getTopKRecognition();
            if (!topKRecognition.isEmpty()) {
                List<MediaBodyInfo> bodyInfoList = topKRecognition.get(topKRecognition.size() - 1).getBodyInfoList();
                if (!bodyInfoList.isEmpty()) {
                    MediaBodyInfo mediaBodyInfo = bodyInfoList.get(bodyInfoList.size() - 1);
                    ParserMediaInfoUtils.ParseLocation(mediaBodyInfo.getLocation(), name, getText());
                }
            }
        } else if (in("Response", "JobsDetail", "Operation", "VideoTargetRecResult", "TopKRecognition", "CarInfo", "Location")) {
            VideoTargetRec videoTargetRec = jobsDetail.getOperation().getVideoTargetRec();
            List<MediaTopkRecognition> topKRecognition = videoTargetRec.getTopKRecognition();
            if (!topKRecognition.isEmpty()) {
                List<MediaBodyInfo> bodyInfoList = topKRecognition.get(topKRecognition.size() - 1).getCarInfoList();
                if (!bodyInfoList.isEmpty()) {
                    MediaBodyInfo mediaBodyInfo = bodyInfoList.get(bodyInfoList.size() - 1);
                    ParserMediaInfoUtils.ParseLocation(mediaBodyInfo.getLocation(), name, getText());
                }
            }
        } else if (in("Response", "JobsDetail", "Operation", "VideoTargetRecResult", "TopKRecognition", "PetInfo", "Location")) {
            VideoTargetRec videoTargetRec = jobsDetail.getOperation().getVideoTargetRec();
            List<MediaTopkRecognition> topKRecognition = videoTargetRec.getTopKRecognition();
            if (!topKRecognition.isEmpty()) {
                List<MediaBodyInfo> bodyInfoList = topKRecognition.get(topKRecognition.size() - 1).getPetInfoList();
                if (!bodyInfoList.isEmpty()) {
                    MediaBodyInfo mediaBodyInfo = bodyInfoList.get(bodyInfoList.size() - 1);
                    ParserMediaInfoUtils.ParseLocation(mediaBodyInfo.getLocation(), name, getText());
                }
            }
        } else if (in("Response", "JobsDetail", "Operation", "VoiceSeparate")) {
            if ("AudioMode".equalsIgnoreCase(name)) {
                jobsDetail.getOperation().getVoiceSeparate().setAudioMode(getText());
            }
        } else if (in("Response", "JobsDetail", "Operation", "VoiceSeparate", "AudioConfig")) {
            ParserMediaInfoUtils.ParseAudioConfig(jobsDetail.getOperation().getVoiceSeparate().getAudioConfig(), name, getText());
        } else if (in("Response", "JobsDetail", "Operation", "TtsConfig")) {
            ParserMediaInfoUtils.ParseTtsConfig(jobsDetail.getOperation().getTtsConfig(), name, getText());
        } else if (in("Response", "JobsDetail", "Operation", "TtsTpl")) {
            ParserMediaInfoUtils.ParseTtsTpl(jobsDetail.getOperation().getTtsTpl(), name, getText());
        } else if (in("Response", "JobsDetail", "Operation", "VideoEnhance", "Transcode", "Container")) {
            ParserMediaInfoUtils.ParseContainer(jobsDetail.getOperation().getVideoEnhance().getTrascode().getContainer(), name, getText());
        } else if (in("Response", "JobsDetail", "Operation", "VideoEnhance", "Transcode", "Video")) {
            ParserMediaInfoUtils.ParsingMediaVideo(jobsDetail.getOperation().getVideoEnhance().getTrascode().getVideo(), name, getText());
        } else if (in("Response", "JobsDetail", "Operation", "VideoEnhance", "Transcode", "Audio")) {
            ParserMediaInfoUtils.ParsingMediaAudio(jobsDetail.getOperation().getVideoEnhance().getTrascode().getAudio(), name, getText());
        } else if (in("Response", "JobsDetail", "Operation", "VideoEnhance", "SuperResolution")) {
            ParserMediaInfoUtils.ParsingSuperResolution(jobsDetail.getOperation().getVideoEnhance().getSuperResolution(), name, getText());
        } else if (in("Response", "JobsDetail", "Operation", "VideoEnhance", "ColorEnhance")) {
            ParserMediaInfoUtils.ParsingColorEnhance(jobsDetail.getOperation().getVideoEnhance().getColorEnhance(), name, getText());
        } else if (in("Response", "JobsDetail", "Operation", "VideoEnhance", "MsSharpen")) {
            ParserMediaInfoUtils.ParsingMsSharpen(jobsDetail.getOperation().getVideoEnhance().getMsSharpen(), name, getText());
        } else if (in("Response", "JobsDetail", "Operation", "VideoEnhance", "SDRtoHDR")) {
            ParserMediaInfoUtils.ParsingSDRtoHDR(jobsDetail.getOperation().getVideoEnhance().getSdrToHDR(), name, getText());
        } else if (in("Response", "JobsDetail", "Operation", "VideoEnhance", "FrameEnhance")) {
            ParserMediaInfoUtils.ParsingFrameEnhance(jobsDetail.getOperation().getVideoEnhance().getFrameEnhance(), name, getText());
        } else if (in("Response", "JobsDetail", "Operation", "Transcode", "AudioMixArray")) {
            ParserMediaInfoUtils.ParsingAudioMixArray(jobsDetail.getOperation().getTranscode().getAudioMixArray(), name, getText());
        } else if (in("Response", "JobsDetail", "Operation", "Transcode", "AudioMixArray", "EffectConfig")) {
            ParserMediaInfoUtils.ParsingEffectConfig(jobsDetail.getOperation().getTranscode().getAudioMixArray(), name, getText());
        } else if (in("Response", "JobsDetail", "Operation", "Subtitles", "Subtitle")) {
            ParserMediaInfoUtils.ParsingSubtitles(jobsDetail.getOperation().getSubtitles(), name, getText());
        } else if (in("Response", "JobsDetail", "Operation", "VideoTag")) {
            ParserMediaInfoUtils.ParseVideoTag(jobsDetail.getOperation().getVideoTag(), name, getText());
        } else if (in("Response", "JobsDetail", "Operation", "QualityEstimateConfig")) {
            ParserMediaInfoUtils.ParseQualityEstimateConfig(jobsDetail.getOperation().getQualityEstimateConfig(), name, getText());
        } else if (in("Response", "JobsDetail", "Operation", "QualityEstimate")) {
            ParserMediaInfoUtils.ParseQualityEstimate(jobsDetail.getOperation().getQualityEstimate(), name, getText());
        } else if (in("Response", "JobsDetail", "Operation", "QualityEstimate")) {
            if ("Score".equalsIgnoreCase(name)) jobsDetail.getOperation().getQualityEstimate().setScore(getText());
        } else if (in("Response", "JobsDetail", "Operation", "QualityEstimate", "VqaPlusResult")) {
            ParserMediaInfoUtils.ParseVqaPlusResult(jobsDetail.getOperation().getQualityEstimate().getVqaPlusResult(), name, getText());
        } else if (in("Response", "JobsDetail", "Operation", "QualityEstimate", "VqaPlusResult", "DetailedResult")) {
            DetailedResult detailedResult = getDetailedResult(jobsDetail);
            ParserMediaInfoUtils.ParseDetailedResult(detailedResult, name, getText());
        } else if (in("Response", "JobsDetail", "Operation", "QualityEstimate", "VqaPlusResult", "DetailedResult", "Items")) {
            DetailedResult detailedResult = getDetailedResult(jobsDetail);
            QualityEstimateItem qualityEstimateItem = getQualityEstimateItem(detailedResult.getItems());
            ParserMediaInfoUtils.ParseQualityEstimateItem(qualityEstimateItem, name, getText());
        } else if (in("Response", "JobsDetail", "Operation", "QualityEstimate", "VqaPlusResult", "DetailedResult", "Items", "AreaCoordSet")) {
            DetailedResult detailedResult = getDetailedResult(jobsDetail);
            QualityEstimateItem qualityEstimateItem = getQualityEstimateItem(detailedResult.getItems());
            List<String> areaCoordSet = qualityEstimateItem.getAreaCoordSet();
            areaCoordSet.add(getText());
        }
    }

    private DetailedResult getDetailedResult(MediaJobObject jobsDetail) {
        List<DetailedResult> detailedResults = jobsDetail.getOperation().getQualityEstimate().getVqaPlusResult().getDetailedResults();
        if (!detailedResults.isEmpty()) {
            return detailedResults.get(detailedResults.size() - 1);
        }
        return new DetailedResult();
    }

    private QualityEstimateItem getQualityEstimateItem(List<QualityEstimateItem> items) {
        if (!items.isEmpty()) {
            return items.get(items.size() - 1);
        }
        return new QualityEstimateItem();
    }

    public MediaJobResponse getResponse() {
        return response;
    }
}
