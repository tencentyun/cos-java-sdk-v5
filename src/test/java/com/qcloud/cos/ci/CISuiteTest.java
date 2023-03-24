package com.qcloud.cos.ci;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ContentAudutingTest.class,DocProcessTest.class,FileProcessTest.class,ImageProcessTest.class,MediaTemplateTest.class
      ,MediaJobTest.class,MediaWorkflowTest.class })
public class CISuiteTest {
}
