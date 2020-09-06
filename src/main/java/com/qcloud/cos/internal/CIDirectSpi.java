/*
 * Copyright 2020 腾讯云, Inc. or its affiliates. All Rights Reserved.
 *
 * Copyright 2010-2019 Amazon.com, Inc. or its affiliates. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License").
 * You may not use this file except in compliance with the License.
 * A copy of the License is located at
 *
 *  http://aws.amazon.com/apache2.0
 *
 * or in the "license" file accompanying this file. This file is distributed
 * on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing
 * permissions and limitations under the License.

 * According to ci feature, we modify some class，comment, field name, etc.
 */
package com.qcloud.cos.internal;

import com.qcloud.cos.model.ciModel.bucket.MediaBucketRequest;
import com.qcloud.cos.model.ciModel.bucket.MediaBucketResponse;
import com.qcloud.cos.model.ciModel.job.*;
import com.qcloud.cos.model.ciModel.mediaInfo.MediaInfoRequest;
import com.qcloud.cos.model.ciModel.mediaInfo.MediaInfoResponse;
import com.qcloud.cos.model.ciModel.queue.*;
import com.qcloud.cos.model.ciModel.template.*;
import com.qcloud.cos.model.ciModel.snapshot.*;
import com.qcloud.cos.model.ciModel.workflow.*;


import java.io.UnsupportedEncodingException;

/**
 * 数据万象 SPI  文档地址：https://cloud.tencent.com/document/product/460/42865
 */
public interface CIDirectSpi {
    /**
     * CreateMediaJobs 接口用于提交一个任务。 https://cloud.tencent.com/document/product/460/38936
     */
    MediaJobResponse createMediaJobs(MediaJobObject req) throws UnsupportedEncodingException;

    /**
     * CancelMediaJob 接口用于取消一个任务。  https://cloud.tencent.com/document/product/460/38939
     */
    Boolean cancelMediaJob(MediaJobsRequest req);

    /**
     * DescribeMediaJob 用于查询指定的任务。  https://cloud.tencent.com/document/product/460/38937
     *
     * @return
     */
    MediaJobResponse describeMediaJob(MediaJobsRequest req);

    /**
     * DescribeMediaJobs 用于拉取符合条件的任务。  https://cloud.tencent.com/document/product/460/38938
     */
    MediaListJobResponse describeMediaJobs(MediaJobsRequest cIMediaJobsRequest);

    /**
     * DescribeMediaQueues 接口用于搜索队列。 https://cloud.tencent.com/document/product/460/38913
     */
    MediaListQueueResponse describeMediaQueues(MediaQueueRequest mediaQueueRequest);

    /**
     * UpdateMediaQueue 接口用于更新队列。  https://cloud.tencent.com/document/product/460/42324
     */
    MediaQueueResponse updateMediaQueue(MediaQueueRequest mediaQueueRequest) throws UnsupportedEncodingException;

    /**
     * DescribeMediaBuckets 接口用于查询存储桶是否已开通媒体处理功能。  https://cloud.tencent.com/document/product/460/38914
     */
    MediaBucketResponse describeMediaBuckets(MediaBucketRequest mediaBucketRequest);

    /**
     * CreateMediaTemplate 用于新增模板。。
     * 动图模板 https://cloud.tencent.com/document/product/460/46989
     * 截图模板 https://cloud.tencent.com/document/product/460/46994
     * 转码模板 https://cloud.tencent.com/document/product/460/46999
     */
    MediaTemplateResponse createMediaTemplate(MediaTemplateRequest request) throws UnsupportedEncodingException;

    /**
     * DeleteMediaTemplate 用于删除模板。 https://cloud.tencent.com/document/product/460/46990
     *
     * @return
     */
    Boolean deleteMediaTemplate(MediaTemplateRequest request);

    /**
     * DescribeMediaTemplates 用于查询动图模板。  https://cloud.tencent.com/document/product/460/46991
     */
    MediaListTemplateResponse describeMediaTemplates(MediaTemplateRequest request);

    /**
     * UpdateMediaTemplate 用于更新模板。。  https://cloud.tencent.com/document/product/460/46992
     */
    Boolean updateMediaTemplate(MediaTemplateRequest request) throws UnsupportedEncodingException;

    /**
     * GenerateSnapshot 接口用于获取媒体文件某个时间的截图，输出的截图统一为 jpeg 格式。
     * https://cloud.tencent.com/document/product/460/38934
     */
    SnapshotResponse generateSnapshot(SnapshotRequest request) throws UnsupportedEncodingException;

    /**
     * GenerateMediainfo 接口用于获取媒体文件的信息。 https://cloud.tencent.com/document/product/460/38935
     */
    MediaInfoResponse generateMediainfo(MediaInfoRequest request) throws UnsupportedEncodingException;

    /**
     * Delete Workflow 接口用于删除工作流。 https://cloud.tencent.com/document/product/460/45947
     */
    Boolean deleteWorkflow(MediaWorkflowRequest request);

    /**
     * Describe Workflow 接口用于搜索工作流。  https://cloud.tencent.com/document/product/460/45948
     */
    MediaWorkflowResponse describeWorkflow(MediaWorkflowRequest request);

    /**
     * Describe WorkflowExecution 接口用于获取工作流实例详情。 https://cloud.tencent.com/document/product/460/45949
     */
    MediaWorkflowExecutionResponse describeWorkflowExecution(MediaWorkflowRequest request);

    /**
     * Describe WorkflowExecution 接口用于获取工作流实例列表。 https://cloud.tencent.com/document/product/460/45950
     */
    MediaWorkflowExecutionsResponse describeWorkflowExecutions(MediaWorkflowRequest request);
}
