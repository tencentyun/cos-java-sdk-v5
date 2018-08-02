package com.qcloud.cos.model;

import java.io.Serializable;
import java.security.Permissions;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

import org.json.JSONObject;
import org.json.JSONArray;

/**
 * <p>
 * Represents an Image Process Rule.
 * </p>
 *
 */
public class ImageProcessRule implements Serializable {
    private static final long serialVersionUID = 1L;

    private static final int maxRuleSize = 5;
    private List<ProcessRule> ruleList;

    /*
     * 是否需要返回原图的信息，包括宽高主色调等
     */
    private boolean obtainImageInfo = false;

    /**
     * Gets the setting of if obtain the image's info.
     *
     * @return boolean
     */
    public boolean getObtainImageInfo() {
        return this.obtainImageInfo;
    }

    /**
     * @param boolean
     */
    public void setObtainImageInfo(boolean obtainImageInfo) {
        this.obtainImageInfo = obtainImageInfo;
    }

    /**
     * Adds a process rule.
     *
     * @param fileId The fileId to save in cos.
     * @param rule   The rule how to process the image.
     *
     * @return boolean Whether it is successful or not
     */
    public boolean appendRule(String fileId, String rule) {
        if (this.ruleList == null) {
            this.ruleList = new ArrayList<ProcessRule>();
        }
        if (this.ruleList.size() >= this.maxRuleSize) {
            return false;
        }
        return this.ruleList.add(new ProcessRule(fileId, rule));
    }

    public String toString() {
        return "ImageProcessRule [" + toJson() + "]";
    }

    public String toJson() {
        JSONObject operation = new JSONObject();
        operation.put("is_pic_info", (this.obtainImageInfo?1:0));

        if (this.ruleList != null) {
            JSONArray rules = new JSONArray();
            for (int i = 0; i < this.ruleList.size(); ++i) {
                ProcessRule rule = ruleList.get(i);
                JSONObject r = new JSONObject();
                r.put("fileid", rule.getFileId());
                r.put("rule", rule.getRule());
                rules.put(r);
            }
            operation.put("rules", rules);
        }

        return operation.toString();
    }
    
    public static class ProcessRule {
    	
        /*
         * 落地存储的fileId名字
         */
    	private String fileId;
        /*
         * 对原图进行的处理操作，详细规则请看 https://cloud.tencent.com/document/product/460/6925
         */
    	private String rule;
    	
    	public ProcessRule() {
    		super();
    	}
    	public ProcessRule(String fileId, String rule) {
    		super();
    		this.fileId = fileId;
    		this.rule = rule;
    	}
    	
    	public void setFileId(String fileId) {
    		this.fileId = fileId;
    	}
    	public ProcessRule withFileId(String fileId) {
    		setFileId(fileId);
    		return this;
    	}
    	public String getFileId() {
    		return this.fileId;
    	}
    	
    	public void setRule(String rule) {
    		this.rule = rule;
    	}
    	public ProcessRule withRule(String rule) {
    		setRule(rule);
    		return this;
    	}
    	public String getRule() {
    		return this.rule;
    	}
    }

}
