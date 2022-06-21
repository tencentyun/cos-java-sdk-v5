package com.qcloud.cos.model.ciModel.auditing;

/**
 * @author markjrzhang
 * @date 2022/4/1 4:37 下午
 */
public class UserInfo {
    /**
     * 用户业务TokenId
     */
    private String tokenId;
    /**
     * 用户业务Nickname
     */
    private String nickname;
    /**
     * 用户业务deviceId
     */
    private String deviceId;
    /**
     * 用户业务appId
     */
    private String appId;
    /**
     * 用户业务room
     */
    private String room;
    /**
     * 用户业务ip
     */
    private String ip;
    /**
     * 用户业务type
     */
    private String type;

    private String receiveTokenId;

    private String gender;

    private String level;

    private String role;

    public String getTokenId() {
        return tokenId;
    }

    public void setTokenId(String tokenId) {
        this.tokenId = tokenId;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getReceiveTokenId() {
        return receiveTokenId;
    }

    public void setReceiveTokenId(String receiveTokenId) {
        this.receiveTokenId = receiveTokenId;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("UserInfo{");
        sb.append("tokenId='").append(tokenId).append('\'');
        sb.append(", nickname='").append(nickname).append('\'');
        sb.append(", deviceId='").append(deviceId).append('\'');
        sb.append(", appId='").append(appId).append('\'');
        sb.append(", room='").append(room).append('\'');
        sb.append(", ip='").append(ip).append('\'');
        sb.append(", type='").append(type).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
