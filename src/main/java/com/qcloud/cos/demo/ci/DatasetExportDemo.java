package com.qcloud.cos.demo.ci;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.model.ciModel.metaInsight.CancelDatasetExportJobRequest;
import com.qcloud.cos.model.ciModel.metaInsight.CancelDatasetExportJobResponse;
import com.qcloud.cos.model.ciModel.metaInsight.CreateDatasetExportJobRequest;
import com.qcloud.cos.model.ciModel.metaInsight.CreateDatasetExportJobResponse;
import com.qcloud.cos.model.ciModel.metaInsight.DescribeDatasetExportJobRequest;
import com.qcloud.cos.model.ciModel.metaInsight.DescribeDatasetExportJobResponse;
import com.qcloud.cos.model.ciModel.metaInsight.DescribeDatasetExportJobsRequest;
import com.qcloud.cos.model.ciModel.metaInsight.DescribeDatasetExportJobsResponse;
import com.qcloud.cos.model.ciModel.metaInsight.GfsPreHeatConfig;
import com.qcloud.cos.model.ciModel.metaInsight.DatasetExportOutput;
import com.qcloud.cos.utils.Jackson;

/**
 * 数据集异步导出 Demo（V2.7.0 新增）。
 * <p>
 * 演示完整生命周期：创建 → 查询单个 → 列表 → 取消。
 * 支持两种导出任务类型：
 * </p>
 * <ol>
 *   <li>{@code ExportJobType=1}：数据导出 — 把检索结果集打包写到 COS 对象</li>
 *   <li>{@code ExportJobType=2}：GFS 数据预热 — 把命中文件预热到 GFS 集群</li>
 * </ol>
 *
 * <p>本 Demo 包含 5 个示例方法：</p>
 * <ul>
 *   <li>{@link #createExportJobDataExport(COSClient)}：创建数据导出任务</li>
 *   <li>{@link #createExportJobGfsPreheat(COSClient)}：创建 GFS 预热任务</li>
 *   <li>{@link #describeExportJob(COSClient, String)}：查询单个任务（轮询状态用）</li>
 *   <li>{@link #describeExportJobs(COSClient)}：分页列出所有任务</li>
 *   <li>{@link #cancelExportJob(COSClient, String)}：取消任务</li>
 * </ul>
 */
public class DatasetExportDemo {

    public static void main(String[] args) {
        // 1 初始化用户身份信息（secretId, secretKey）。
        COSClient client = ClientUtils.getTestClient();
        // 2 演示完整生命周期：创建 → 查询 → 列表 → 取消。
        CreateDatasetExportJobResponse createResp = createExportJobDataExport(client);
        if (createResp != null && createResp.getJob() != null) {
            String jobId = createResp.getJob().getJobId();
            describeExportJob(client, jobId);
            describeExportJobs(client);
            // 注：仅当任务仍在 Unstart/Running 状态时取消才有效
            // cancelExportJob(client, jobId);
        }
        // 也可单独尝试 GFS 预热场景：
        // createExportJobGfsPreheat(client);
    }

    /**
     * 场景一：创建数据导出任务（ExportJobType=1）。
     * <p>把检索结果集打包写到指定 COS 对象。</p>
     */
    public static CreateDatasetExportJobResponse createExportJobDataExport(COSClient client) {
        CreateDatasetExportJobRequest request = new CreateDatasetExportJobRequest();
        request.setAppId("1250000000");
        // 任务名称，用户自定义
        request.setName("export-task-2026-05-22");
        // 数据集名称
        request.setDatasetName("test");
        // 任务类型：1=数据导出
        request.setExportJobType(1);

        // 输出目标：必填（ExportJobType=1 时）
        DatasetExportOutput output = new DatasetExportOutput();
        output.setRegion("ap-guangzhou");
        output.setBucket("examplebucket-1250000000");
        output.setObject("export/result_20260522.json");
        request.setOutput(output);

        CreateDatasetExportJobResponse response = client.createDatasetExportJob(request);
        System.out.println(Jackson.toJsonString(response));
        return response;
    }

    /**
     * 场景二：创建 GFS 数据预热任务（ExportJobType=2）。
     * <p>把检索命中文件预热到指定 GFS 集群。</p>
     */
    public static CreateDatasetExportJobResponse createExportJobGfsPreheat(COSClient client) {
        CreateDatasetExportJobRequest request = new CreateDatasetExportJobRequest();
        request.setAppId("1250000000");
        request.setName("gfs-preheat-2026-05-22");
        request.setDatasetName("test");
        // 任务类型：2=GFS 数据预热
        request.setExportJobType(2);

        // GFS 预热配置：必填（ExportJobType=2 时）
        GfsPreHeatConfig config = new GfsPreHeatConfig();
        config.setClusterId("cfs-xxxxxxxx");
        config.setPriority(1);
        request.setGfsPreHeatConfig(config);

        CreateDatasetExportJobResponse response = client.createDatasetExportJob(request);
        System.out.println(Jackson.toJsonString(response));
        return response;
    }

    /**
     * 场景三：查询单个任务详情。
     * <p>常用于轮询任务状态（State=Running → Success/Failed）和进度（Progress 0-100）。</p>
     */
    public static DescribeDatasetExportJobResponse describeExportJob(COSClient client, String jobId) {
        DescribeDatasetExportJobRequest request = new DescribeDatasetExportJobRequest();
        request.setAppId("1250000000");
        request.setDatasetname("test");
        request.setJobid(jobId);

        DescribeDatasetExportJobResponse response = client.describeDatasetExportJob(request);
        System.out.println(Jackson.toJsonString(response));
        return response;
    }

    /**
     * 场景四：分页列出数据集下所有导出任务。
     */
    public static void describeExportJobs(COSClient client) {
        DescribeDatasetExportJobsRequest request = new DescribeDatasetExportJobsRequest();
        request.setAppId("1250000000");
        request.setDatasetname("test");
        // 单页大小，默认 100，最大 200
        request.setMaxresults(100);
        // 翻页 token；首次查询不传，后续从上一次响应的 NextToken 取
        // request.setNexttoken("xxx");

        DescribeDatasetExportJobsResponse response = client.describeDatasetExportJobs(request);
        System.out.println(Jackson.toJsonString(response));
    }

    /**
     * 场景五：取消任务。
     * <p>仅可取消处于 {@code Unstart} / {@code Running} 状态的任务，
     * 已完成（{@code Success} / {@code Failed} / {@code Cancelled}）的任务再取消会得到后端的 4xx 错误。</p>
     */
    public static void cancelExportJob(COSClient client, String jobId) {
        CancelDatasetExportJobRequest request = new CancelDatasetExportJobRequest();
        request.setAppId("1250000000");
        request.setDatasetName("test");
        request.setJobId(jobId);

        CancelDatasetExportJobResponse response = client.cancelDatasetExportJob(request);
        System.out.println(Jackson.toJsonString(response));
    }
}
