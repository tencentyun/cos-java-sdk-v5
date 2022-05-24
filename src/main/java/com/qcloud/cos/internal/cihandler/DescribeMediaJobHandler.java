package com.qcloud.cos.internal.cihandler;

import com.qcloud.cos.internal.ParserMediaInfoUtils;
import com.qcloud.cos.model.ciModel.common.MediaOutputObject;
import com.qcloud.cos.model.ciModel.job.ExtractDigitalWatermark;
import com.qcloud.cos.model.ciModel.job.MediaAudioObject;
import com.qcloud.cos.model.ciModel.job.MediaConcatFragmentObject;
import com.qcloud.cos.model.ciModel.job.MediaConcatTemplateObject;
import com.qcloud.cos.model.ciModel.job.MediaContainerObject;
import com.qcloud.cos.model.ciModel.job.MediaDigitalWatermark;
import com.qcloud.cos.model.ciModel.job.MediaJobObject;
import com.qcloud.cos.model.ciModel.job.MediaJobResponse;
import com.qcloud.cos.model.ciModel.job.MediaRemoveWaterMark;
import com.qcloud.cos.model.ciModel.job.MediaTimeIntervalObject;
import com.qcloud.cos.model.ciModel.job.MediaTransConfigObject;
import com.qcloud.cos.model.ciModel.job.MediaTranscodeVideoObject;
import com.qcloud.cos.model.ciModel.job.MediaVideoObject;
import com.qcloud.cos.model.ciModel.mediaInfo.MediaFormat;
import com.qcloud.cos.model.ciModel.mediaInfo.MediaInfoAudio;
import com.qcloud.cos.model.ciModel.mediaInfo.MediaInfoSubtitle;
import com.qcloud.cos.model.ciModel.mediaInfo.MediaInfoVideo;
import org.xml.sax.Attributes;

import java.util.List;

public class DescribeMediaJobHandler extends CIAbstractHandler {
    MediaJobResponse response = new MediaJobResponse();
    List<MediaConcatFragmentObject> concatFragmentList = response.getJobsDetail().getOperation().getMediaConcatTemplate().getConcatFragmentList();

    @Override
    protected void doStartElement(String uri, String name, String qName, Attributes attrs) {
        if ("ConcatFragment".equals(name)) {
            concatFragmentList.add(new MediaConcatFragmentObject());
        }
    }

    @Override
    protected void doEndElement(String uri, String name, String qName) {
        process(response, name);
    }

    public MediaJobResponse getResponse() {
        return response;
    }

    public void process(MediaJobResponse response, String name) {
        MediaJobObject jobsDetail = response.getJobsDetail();
        if (in("Response", "JobsDetail")) {
            ParserMediaInfoUtils.parseMediaJobsDetail(jobsDetail, name, getText());
        } else if (in("Response", "JobsDetail", "Input")) {
            jobsDetail.getInput().setObject(getText());
        } else if (in("Response", "JobsDetail", "Operation")) {
            if ("TemplateId".equalsIgnoreCase(name)) {
                jobsDetail.getOperation().setTemplateId(getText());
            } else if ("WatermarkTemplateId".equalsIgnoreCase(name)) {
                jobsDetail.getOperation().getWatermarkTemplateId().add(getText());
            }
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
        } else if (in("Response", "JobsDetail", "Operation", "Output")) {
            MediaOutputObject output = jobsDetail.getOperation().getOutput();
            switch (name) {
                case "Bucket":
                    output.setBucket(getText());
                    break;
                case "Object":
                    output.setObject(getText());
                    break;
                case "Region":
                    output.setRegion(getText());
                    break;
            }
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
        }
    }
}
