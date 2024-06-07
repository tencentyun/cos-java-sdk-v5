package com.qcloud.cos.ci;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ContentAudutingTest.class,DocProcessTest.class,FileProcessTest.class,ImageProcessTest.class,MediaTemplateTest.class
      ,MediaJobTest.class,MediaWorkflowTest.class, AuditingKeywordTest.class, AuditingStrategyTest.class, AuditingTextLibTest.class,
    HlsTest.class, ImageSearchDemoTest.class, LiveAuditingTest.class, MediaDnaDbsTest.class, PersonFaceTest.class, SpeechRecognitionTest.class})
public class CISuiteTest {
}
