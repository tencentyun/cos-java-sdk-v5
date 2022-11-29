package com.qcloud.cos.model.ciModel.auditing;

public class ListResult {
    /**
     * 命中的名单类型，取值为0（白名单）和1（黑名单）
     */
    private String listType;
    /**
     * 命中的名单名称
     */
    private String listName;
    /**
     * 命中了名单中的哪条内容
     */
    private String entity;

    public String getListType() {
        return listType;
    }

    public void setListType(String listType) {
        this.listType = listType;
    }

    public String getListName() {
        return listName;
    }

    public void setListName(String listName) {
        this.listName = listName;
    }

    public String getEntity() {
        return entity;
    }

    public void setEntity(String entity) {
        this.entity = entity;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("ListResult{");
        sb.append("listType='").append(listType).append('\'');
        sb.append(", listName='").append(listName).append('\'');
        sb.append(", entity='").append(entity).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
