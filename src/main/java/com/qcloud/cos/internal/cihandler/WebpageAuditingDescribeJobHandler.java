package com.qcloud.cos.internal.cihandler;

import com.qcloud.cos.internal.ParserMediaInfoUtils;
import com.qcloud.cos.model.ciModel.auditing.ListResult;
import com.qcloud.cos.model.ciModel.auditing.ResultsImageAuditingDetail;
import com.qcloud.cos.model.ciModel.auditing.ResultsTextAuditingDetail;
import com.qcloud.cos.model.ciModel.auditing.WebpageAuditingJobsDetail;
import com.qcloud.cos.model.ciModel.auditing.WebpageAuditingResponse;
import org.xml.sax.Attributes;

import java.util.List;

public class WebpageAuditingDescribeJobHandler extends CIAbstractHandler {
    private WebpageAuditingResponse response = new WebpageAuditingResponse();

    @Override
    protected void doStartElement(String uri, String name, String qName, Attributes attrs) {
        List<ResultsImageAuditingDetail> imageResults = response.getJobsDetail().getImageResults();
        List<ResultsTextAuditingDetail> textResults = response.getJobsDetail().getTextResults();
        if (in("Response", "JobsDetail", "ImageResults") && "Results".equals(name)) {
            imageResults.add(new ResultsImageAuditingDetail());
        } else if (in("Response", "JobsDetail", "TextResults") && "Results".equals(name)) {
            textResults.add(new ResultsTextAuditingDetail());
        } else if (in("Response", "JobsDetail", "ListInfo") && "ListResults".equals(name)) {
            response.getJobsDetail().getListInfo().getListResults().add(new ListResult());
        }
    }

    @Override
    protected void doEndElement(String uri, String name, String qName) {
        WebpageAuditingJobsDetail jobsDetail = response.getJobsDetail();
        List<ResultsImageAuditingDetail> imageResults = jobsDetail.getImageResults();
        List<ResultsTextAuditingDetail> textResults = jobsDetail.getTextResults();
        ResultsImageAuditingDetail imageAuditingDetail = null;
        ResultsTextAuditingDetail textAuditingDetail = null;
        if (imageResults.isEmpty()) {
            imageAuditingDetail = new ResultsImageAuditingDetail();
        } else {
            imageAuditingDetail = imageResults.get(imageResults.size() - 1);
        }
        if (textResults.isEmpty()) {
            textAuditingDetail = new ResultsTextAuditingDetail();
        } else {
            textAuditingDetail = textResults.get(textResults.size() - 1);
        }
        if (in("Response")) {
            if ("RequestId".equalsIgnoreCase(name)) {
                response.setRequestId(getText());
            }
        } else if (in("Response", "JobsDetail")) {
            switch (name) {
                case "Code":
                    jobsDetail.setCode(getText());
                    break;
                case "Message":
                    jobsDetail.setMessage(getText());
                    break;
                case "JobId":
                    jobsDetail.setJobId(getText());
                    break;
                case "dataId":
                    jobsDetail.setDataId(getText());
                    break;
                case "State":
                    jobsDetail.setState(getText());
                    break;
                case "CreationTime":
                    jobsDetail.setCreationTime(getText());
                    break;
                case "Url":
                    jobsDetail.setUrl(getText());
                    break;
                case "Suggestion":
                    jobsDetail.setSuggestion(getText());
                    break;
                case "Label":
                    jobsDetail.setLabel(getText());
                    break;
                case "PageCount":
                    jobsDetail.setPageCount(getText());
                    break;
                case "HighlightHtml":
                    jobsDetail.setHighlightHtml(getText());
                    break;
                default:
                    break;
            }
        } else if (in("Response", "JobsDetail", "ImageResults", "Results")) {
            switch (name) {
                case "Text":
                    imageAuditingDetail.setText(getText());
                    break;
                case "Url":
                    imageAuditingDetail.setUrl(getText());
                    break;
                case "Label":
                    imageAuditingDetail.setLabel(getText());
                    break;
                case "Suggestion":
                    imageAuditingDetail.setSuggestion(getText());
                    break;
                default:
                    break;
            }
        } else if (in("Response", "JobsDetail", "TextResults", "Results")) {
            switch (name) {
                case "Text":
                    textAuditingDetail.setText(getText());
                    break;
                case "Label":
                    textAuditingDetail.setLabel(getText());
                    break;
                case "Suggestion":
                    textAuditingDetail.setSuggestion(getText());
                    break;
                default:
                    break;
            }
        } else if (in("Response", "JobsDetail", "ImageResults", "Results", "PoliticsInfo")) {
            ParserMediaInfoUtils.ParsingAuditingCommonInfo(imageAuditingDetail.getPoliticsInfo(), name, getText());
        } else if (in("Response", "JobsDetail", "ImageResults", "Results", "PornInfo")) {
            ParserMediaInfoUtils.ParsingAuditingCommonInfo(imageAuditingDetail.getPornInfo(), name, getText());
        } else if (in("Response", "JobsDetail", "ImageResults", "Results", "TerrorismInfo")) {
            ParserMediaInfoUtils.ParsingAuditingCommonInfo(imageAuditingDetail.getTerroristInfo(), name, getText());
        } else if (in("Response", "JobsDetail", "ImageResults", "Results", "AdsInfo")) {
            ParserMediaInfoUtils.ParsingAuditingCommonInfo(imageAuditingDetail.getAdsInfo(), name, getText());
        } else if (in("Response", "JobsDetail", "TextResults", "Results", "PoliticsInfo")) {
            ParserMediaInfoUtils.ParsingAuditingCommonInfo(textAuditingDetail.getPoliticsInfo(), name, getText());
        } else if (in("Response", "JobsDetail", "TextResults", "Results", "PornInfo")) {
            ParserMediaInfoUtils.ParsingAuditingCommonInfo(textAuditingDetail.getPornInfo(), name, getText());
        } else if (in("Response", "JobsDetail", "TextResults", "Results", "TerrorismInfo")) {
            ParserMediaInfoUtils.ParsingAuditingCommonInfo(textAuditingDetail.getTerroristInfo(), name, getText());
        } else if (in("Response", "JobsDetail", "TextResults", "Results", "AdsInfo")) {
            ParserMediaInfoUtils.ParsingAuditingCommonInfo(textAuditingDetail.getAdsInfo(), name, getText());
        } else if (in("Response", "JobsDetail", "UserInfo")) {
            ParserMediaInfoUtils.ParsingAuditingUserInfo(response.getJobsDetail().getUserInfo(), name, getText());
        } else if (in("Response", "JobsDetail", "ListInfo", "ListResults")) {
            List<ListResult> listResults = response.getJobsDetail().getListInfo().getListResults();
            if (!listResults.isEmpty()) {
                ParserMediaInfoUtils.parsingAuditingListResultInfo(listResults.get(listResults.size() - 1), name, getText());
            }
        } else if (in("Response", "JobsDetail", "Labels", "PoliticsInfo")) {
            ParserMediaInfoUtils.ParsingAuditingCommonInfo(response.getJobsDetail().getLabels().getPoliticsInfo(), name, getText());
        } else if (in("Response", "JobsDetail", "Labels", "PornInfo")) {
            ParserMediaInfoUtils.ParsingAuditingCommonInfo(response.getJobsDetail().getLabels().getPornInfo(), name, getText());
        } else if (in("Response", "JobsDetail", "Labels", "TerrorismInfo")) {
            ParserMediaInfoUtils.ParsingAuditingCommonInfo(response.getJobsDetail().getLabels().getTerroristInfo(), name, getText());
        } else if (in("Response", "JobsDetail", "Labels", "AdsInfo")) {
            ParserMediaInfoUtils.ParsingAuditingCommonInfo(response.getJobsDetail().getLabels().getAdsInfo(), name, getText());
        }
    }

    public WebpageAuditingResponse getResponse() {
        return response;
    }

    public void setResponse(WebpageAuditingResponse response) {
        this.response = response;
    }

}
