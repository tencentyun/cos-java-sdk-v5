package com.qcloud.cos.ci;

import com.qcloud.cos.internal.cihandler.XStreamXmlResponsesSaxParser;
import com.qcloud.cos.model.ciModel.job.v2.MediaJobResponseV2;

import java.io.ByteArrayInputStream;
import java.io.IOException;

public class Test {
    public static void main(String[] args) throws IOException {
        String xml = "<Response>\n" +
                "    <JobsDetail>\n" +
                "        <Code>Success</Code>\n" +
                "        <Message/>\n" +
                "        <JobId>j8d121820f5e411ec926ef19d53ba9c6f</JobId>\n" +
                "        <State>Submitted</State>\n" +
                "        <Progress>0</Progress>\n" +
                "        <CreationTime>2022-06-27T15:23:10+0800</CreationTime>\n" +
                "        <StartTime>-</StartTime>\n" +
                "        <EndTime>-</EndTime>\n" +
                "        <QueueId>p2242ab62c7c94486915508540933a2c6</QueueId>\n" +
                "        <Tag>Transcode</Tag>\n" +
                "        <Input>\n" +
                "            <BucketId>test-123456789</BucketId>\n" +
                "            <Object>input/demo.mp4</Object>\n" +
                "            <Region>ap-chongqing</Region>\n" +
                "        </Input>\n" +
                "        <Operation>\n" +
                "            <Transcode>\n" +
                "                <Container>\n" +
                "                    <Format>hls</Format>\n" +
                "                </Container>\n" +
                "                <Video>\n" +
                "                    <Codec>H.264</Codec>\n" +
                "                    <Bitrate>1000</Bitrate>\n" +
                "                    <Width>1280</Width>\n" +
                "                    <Fps>30</Fps>\n" +
                "                </Video>\n" +
                "            </Transcode>\n" +
                "            <Output>\n" +
                "                <Region>ap-chongqing</Region>\n" +
                "                <Bucket>test-123456789</Bucket>\n" +
                "                <Object>output/out.${ext}</Object>\n" +
                "            </Output>\n" +
                "            <UserData>This is my data.</UserData>\n" +
                "            <JobLevel>0</JobLevel>\n" +
                "        </Operation>\n" +
                "    </JobsDetail>\n" +
                "</Response>\n";
        while (true) {
            ByteArrayInputStream inputStream = new ByteArrayInputStream(xml.getBytes());
            MediaJobResponseV2 bean = XStreamXmlResponsesSaxParser.toBean(inputStream, MediaJobResponseV2.class);
            System.out.println(bean);
//            inputStream.close();
        }

    }
}
