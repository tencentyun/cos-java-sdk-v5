package com.qcloud.cos.model.agentbucket;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.qcloud.cos.model.CosServiceResult;

/**
 * Delete Object Recursive 的返回结果。
 * <p>
 * {@link #getDeletedKeys()} 为服务端返回的 Deleted/Key。真机实测只返回请求删除的那个 key（如目录 "my-space/dir/"），
 * 不会逐个列出目录下被删除的文件，不能用于统计删除的文件数。官方文档未定义逐条失败条目，
 * SDK 对 DeleteResult 下可能出现的 Error 子节点做防御性解析，存放于 {@link #getErrors()}，
 * 调用方可据此判断是否部分失败。
 * </p>
 *
 * @since 5.6.283
 */
public class DeleteObjectRecursiveResult extends CosServiceResult implements Serializable {
    private List<String> deletedKeys = new ArrayList<String>();
    private List<DeleteError> errors = new ArrayList<DeleteError>();

    public List<String> getDeletedKeys() {
        return deletedKeys;
    }

    public void setDeletedKeys(List<String> deletedKeys) {
        this.deletedKeys = deletedKeys == null ? new ArrayList<String>() : deletedKeys;
    }

    public List<DeleteError> getErrors() {
        return errors;
    }

    public void setErrors(List<DeleteError> errors) {
        this.errors = errors == null ? new ArrayList<DeleteError>() : errors;
    }

    /** 是否存在逐条删除失败的条目 */
    public boolean hasErrors() {
        return !errors.isEmpty();
    }

    @Override
    public String toString() {
        return "DeleteObjectRecursiveResult{deletedKeys=" + deletedKeys.size()
                + ", errors=" + errors.size() + ", requestId='" + getRequestId() + "'}";
    }

    /** DeleteResult 下的逐条失败条目 */
    public static class DeleteError implements Serializable {
        private String key;
        private String code;
        private String message;

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        @Override
        public String toString() {
            return "DeleteError{key='" + key + "', code='" + code + "', message='" + message + "'}";
        }
    }
}
