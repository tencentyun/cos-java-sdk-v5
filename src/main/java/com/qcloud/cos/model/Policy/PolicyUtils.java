package com.qcloud.cos.model.Policy;

import com.qcloud.cos.exception.CosClientException;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class PolicyUtils {
    public static String POLICY_EFFECT_ALLOW = "allow";

    public static String ACTION_TEMPLATE_READ = "read";
    public static String ACTION_TEMPLATE_READ_WRITE = "read_write";
    public static String ACTION_TEMPLATE_HEAD_BUCKET = "head_bucket";

    private static final Set<String> READ_ACTIONS = new HashSet<>();
    private static final Set<String> READ_WRITE_ACTIONS = new HashSet<>();

    private static final Set<String> VALID_ACTION_TEMPLATES = new HashSet<>();
    /**
     * "name/cos:GetObject",
     * "name/cos:HeadObject",
     * "name/cos:OptionsObject",
     * "name/cos:GetBucket",
     *
     * "name/cos:PutObject",
     * "name/cos:PostObject",
     * "name/cos:DeleteObject",
     * "name/cos:InitiateMultipartUpload",
     * "name/cos:UploadPart",
     * "name/cos:CompleteMultipartUpload",
     * "name/cos:AbortMultipartUpload",
     * "name/cos:ListParts",
     * "name/cos:ListMultipartUploads",
     */

    static {
        VALID_ACTION_TEMPLATES.add(ACTION_TEMPLATE_READ);
        VALID_ACTION_TEMPLATES.add(ACTION_TEMPLATE_READ_WRITE);
        VALID_ACTION_TEMPLATES.add(ACTION_TEMPLATE_HEAD_BUCKET);

        READ_ACTIONS.add("name/cos:GetObject");
        READ_ACTIONS.add("name/cos:HeadObject");
        READ_ACTIONS.add("name/cos:OptionsObject");
        READ_ACTIONS.add("name/cos:GetBucket");

        READ_WRITE_ACTIONS.addAll(READ_ACTIONS);
        READ_WRITE_ACTIONS.add("name/cos:PutObject");
        READ_WRITE_ACTIONS.add("name/cos:PostObject");
        READ_WRITE_ACTIONS.add("name/cos:DeleteObject");
        READ_WRITE_ACTIONS.add("name/cos:InitiateMultipartUpload");
        READ_WRITE_ACTIONS.add("name/cos:UploadPart");
        READ_WRITE_ACTIONS.add("name/cos:CompleteMultipartUpload");
        READ_WRITE_ACTIONS.add("name/cos:AbortMultipartUpload");
        READ_WRITE_ACTIONS.add("name/cos:ListParts");
        READ_WRITE_ACTIONS.add("name/cos:ListMultipartUploads");
    }

    public static void fillActions(PolicyStatement statement, String action_template) {
        if (Objects.equals(action_template, ACTION_TEMPLATE_READ)) {
            for (String action : READ_ACTIONS) {
                statement.addAction(action);
            }
            return;
        }
        if (Objects.equals(action_template, ACTION_TEMPLATE_READ_WRITE)) {
            for (String action : READ_WRITE_ACTIONS) {
                statement.addAction(action);
            }
            return;
        }
        if (Objects.equals(action_template, ACTION_TEMPLATE_HEAD_BUCKET)) {
            statement.addAction("name/cos:HeadBucket");
            return;
        }
        CosClientException cce = new CosClientException("The action template must be either read or read_write");
        throw cce;
    }

    public static String buildQcsResourcePath(String region, String appid, String bucketName, String filePath) {
        if (region.isEmpty() || appid.isEmpty() || bucketName.isEmpty() || filePath.isEmpty()){
            throw new IllegalArgumentException("The params region、appid、bucketName、filePath should be specified");
        }
        String resourcePath = String.format("qcs::cos:%s:uid/%s:%s", region, appid, bucketName);
        if (!filePath.startsWith("/")) {
            resourcePath = resourcePath + "/" + filePath;
        } else {
            resourcePath = resourcePath + filePath;
        }
        // 对于文件路径传了个目录但是没传*的情况，手动补上*
        if (resourcePath.endsWith("/")) {
            resourcePath = resourcePath + "*";
        }
        return resourcePath;
    }

    public static boolean isMatchPrefix(String source, String target) {
        if (!source.isEmpty() && !target.isEmpty()) {
            if (source.endsWith("*")) {
                String prefix = source.substring(0,source.length()-1);
                return target.startsWith(prefix);
            }
        }
        return false;
    }

    public static String[] parseQcsResourcePath(String qcsResourcePath) {
        String[] resourceInfo =  qcsResourcePath.split(":", 6);
        if (resourceInfo.length < 6) {
            String errMsg = String.format("The qcs resource path %s does not match the pattren:   qcs:project_id:service_type:region:account:resource", qcsResourcePath);
            throw new CosClientException(errMsg);
        }
        if (resourceInfo[5].isEmpty()) {
            String errMsg = String.format("The qcs resource path %s is invalid cause the file path is empty", qcsResourcePath);
            throw new CosClientException(errMsg);
        }
        String[] pathInfo = resourceInfo[5].split("/", 5);
        if (pathInfo.length < 5 && pathInfo.length != 2) {
            String errMsg = String.format("The file path %s does not match either the pattren:   bucketName/usr/warehouse/schemaName/tableName or bucketName/*", resourceInfo[5]);
            throw new CosClientException(errMsg);
        }
        if (pathInfo[0].isEmpty()) {
            String errMsg = String.format("The bucketname of the file path %s is empty", resourceInfo[5]);
            throw new CosClientException(errMsg);
        }

        // head_bucket 权限只能指定resource为 bucket/* 的格式才能生效
        if (pathInfo.length == 2 && Objects.equals(pathInfo[1], "*")) {
            return new String[]{"*", "*"};
        }

        if (pathInfo[3].isEmpty() || pathInfo[4].isEmpty()) {
            String errMsg = String.format("The file path %s is invalid cause the schemaName or the tableName is empty", resourceInfo[5]);
            throw new CosClientException(errMsg);
        }
        String[] schemaTableName = {pathInfo[3], pathInfo[4]};
        return schemaTableName;
    }

    public static boolean isValidActionTemplate(String actionTemplate) {
        return VALID_ACTION_TEMPLATES.contains(actionTemplate);
    }
}
