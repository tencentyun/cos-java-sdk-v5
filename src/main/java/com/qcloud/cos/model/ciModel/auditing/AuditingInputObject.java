package com.qcloud.cos.model.ciModel.auditing;

/**
 * 输入文件在cos中的位置
 * 例 cos根目录下的1.txt文件  则object = 1.txt
 * cos根目录下test文件夹中的1.txt文件 object = test/1.txt
 */
public class AuditingInputObject {
    private String object;
    private String content;
    private String url;
    private String dataId;
    private UserInfo userInfo = new UserInfo();
    private ListInfo listInfo = new ListInfo();
    private Encryption encryption = new Encryption();

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDataId() {
        return dataId;
    }

    public void setDataId(String dataId) {
        this.dataId = dataId;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public ListInfo getListInfo() {
        return listInfo;
    }

    public void setListInfo(ListInfo listInfo) {
        this.listInfo = listInfo;
    }

    public Encryption getEncryption() {
        return encryption;
    }

    public void setEncryption(Encryption encryption) {
        this.encryption = encryption;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("AuditingInputObject{");
        sb.append("object='").append(object).append('\'');
        sb.append(", content='").append(content).append('\'');
        sb.append(", url='").append(url).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
