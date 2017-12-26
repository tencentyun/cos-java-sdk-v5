# cos-java-sdk-v5  ![Build Status](https://api.travis-ci.org/tencentyun/cos-java-sdk-v5.svg?branch=master)



## maven坐标

```xml
<groupId>com.qcloud</groupId>
<artifactId>cos_api</artifactId>
<version>5.2.4</version>
```

cos-java-sdk-v5 适用于COS XML API https://www.qcloud.com/document/product/436/7751, 

JSON API 请参照 https://github.com/tencentyun/cos-java-sdk-v4

示例程序 demo 请参照 https://github.com/tencentyun/cos-java-sdk-v5/blob/master/src/main/java/com/qcloud/cos/demo
下的示例代码

## 常见问题:

1 引入SDK运行后，出现 java.lang.NoSuchMethodError的异常。

原因: 一般是发生了JAR包冲突，比如用户的工程中的http的JAR包版本没有A方法，但是SDK依赖的JAR包有A方法。此时运行时加载顺序的问题，加载了用户工程中的http库，运行时便会抛出NoSuchMethodError的异常。

解决方法: 将已包含的工程中引起NoSuchMethodError的包的版本和SDK中pom.xml里的对应库的版本改成一致。
