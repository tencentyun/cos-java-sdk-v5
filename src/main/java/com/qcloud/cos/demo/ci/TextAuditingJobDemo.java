package com.qcloud.cos.demo.ci;

import java.util.List;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.model.ciModel.auditing.AuditingInfo;
import com.qcloud.cos.model.ciModel.auditing.CallbackVersion;
import com.qcloud.cos.model.ciModel.auditing.ReportBadCaseRequest;
import com.qcloud.cos.model.ciModel.auditing.SuggestedLabel;
import com.qcloud.cos.model.ciModel.auditing.TextAuditingRequest;
import com.qcloud.cos.model.ciModel.auditing.TextAuditingResponse;
import com.tencent.cloud.cos.util.Base64;

/**
 * 内容审核 文本审核接口相关demo 详情见https://cloud.tencent.com/document/product/436/56289
 */
public class TextAuditingJobDemo {

    public static void main(String[] args) {
        // 1 初始化用户身份信息（secretId, secretKey）。
        COSClient client = ClientUtils.getTestClient();
        // 2 调用要使用的方法。
        createAuditingTextJobs(client);
    }

    /**
     * createAuditingTextJobs 接口用于创建文本审核任务。
     */
    public static void createAuditingTextJobs(COSClient client) {
        //1.创建任务请求对象
        TextAuditingRequest request = new TextAuditingRequest();
        //2.添加请求参数 参数详情请见api接口文档
        request.setBucketName("markjrzhang-1251704708");
        //2.1.1设置对象地址
//        request.getInput().setObject("1.txt");
        //2.1.2或直接设置请求内容,文本内容的Base64编码
        String a ="因为故意杀人罪分别被判了死刑和死缓，这一辈子怕是都要在牢狱里面度过了。从法院出来那一天，我站在高高的台阶上，抬头望着蓝天白云。\n" +
                "\n" +
                "这家店真好吃\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "当地时间11月16日上午，国家主席习近平在旧金山会见墨西哥总统洛佩斯。\n" +
                "\n" +
                "习近平指出，中墨两国友谊历经风雨，历久弥坚。去年两国隆重庆祝建交50周年，今年又迎来建立全面战略伙伴关系10周年。当前，中墨关系的战略性、互补性、互利性不断凸显，在涉及彼此核心利益和重大关切问题上相互理解和支持更加坚定。中方高度重视中墨关系，愿同墨方一道，加强战略对接，挖掘合作潜力，发挥互补优势，推动两国关系再上新台阶。\n" +
                "\n" +
                "习近平强调，中方支持墨西哥独立自主走符合本国国情的发展道路，愿同墨方加强治国理政交流。中墨双边贸易额同建交时相比增长超过7000倍，在铁路、汽车、新能源等领域合作亮点纷呈。双方要用好两国政府间工作机制，深化基础设施建设等传统领域合作，拓展金融、电动汽车等新兴产业合作，深化禁毒执法合作。双方要积极支持互办文艺演出、文化展览等人文交流活动，密切多边协作，坚定维护多边主义和国际关系民主化，维护国际公平正义和发展中国家正当权益。明年是中拉论坛成立10周年。中方愿同墨方一道，推动新时代中拉关系行稳致远。\n" +
                "\n" +
                "习近平还再次就前不久墨西哥太平洋沿岸遭遇飓风灾害表示慰问，表示中方将为墨方救灾物资采购提供大力帮助。\n" +
                "\n" +
                "洛佩斯表示，墨中都有灿烂文明，两国人民相知相亲，情如兄弟。在新冠疫情最严重的时候，是中国最早驰援，向墨方提供了大量宝贵抗疫物资，帮助墨西哥渡过了难关。近期墨方遭遇太平洋严重飓风灾害后，中方第一时间表示慰问和支持，墨方对此表示感谢。墨中都维护自身独立自主，都坚决反对干涉别国内政。墨方将一如既往坚持对华友好政策，坚定相互支持，为中国企业赴墨投资合作提供便利，深化各领域互利合作，深化打击制毒贩毒合作。墨方愿同中方密切在多边事务中协作，积极促进拉中关系。\n" +
                "\n" +
                "蔡奇、王毅等参加会见";
        String encode = Base64.encode(a.getBytes());
        request.getInput().setContent(encode);
        //2.2设置审核模板（可选）
        request.getConf().setBizType("7e114b6ebaaf11eea5da5254000fc30f");
        //设置回调信息内容类型 simple精简 Detail详细
        request.getConf().setCallbackVersion(CallbackVersion.Simple);
        //3.调用接口,获取任务响应对象
        TextAuditingResponse response = client.createAuditingTextJobs(request);
    }

    /**
     * DescribeAuditingJob 接口用于查询文本审核任务。
     *
     * @param client
     */
    public static void describeAuditingTextJob(COSClient client) {
        //1.创建任务请求对象
        TextAuditingRequest request = new TextAuditingRequest();
        //2.添加请求参数 参数详情请见api接口文档
        request.setBucketName("demo-123456789");
        request.setJobId("st68d08596f35011eb9324525400*****");
        //3.调用接口,获取任务响应对象
        TextAuditingResponse response = client.describeAuditingTextJob(request);
    }

    /**
     * reportBadCase 接口用于文本审核结果反馈。
     *
     * @param client
     */
    public static void reportBadCase(COSClient client) {
        //1.创建任务请求对象
        ReportBadCaseRequest request = new ReportBadCaseRequest();
        //2.添加请求参数 参数详情请见api接口文档
        request.setBucketName("demo-123456789");
        request.setContentType("1");
        request.setLabel("porn");
        request.setSuggestedLabel(SuggestedLabel.Normal);
        request.setText("6L+Z5piv5Li65LuA5LmI");
        request.setJobId("st68d08596f35011eb9324525400*****");
        //3.调用接口,获取任务响应对象 上报成功则返回
        String requestId = client.reportBadCase(request);
    }
}
