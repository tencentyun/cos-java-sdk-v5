package com.qcloud.cos.model.ciModel.job;

import com.qcloud.cos.model.ciModel.auditing.ObjectResults;

public class MediaBodyInfo {
    private ObjectResults.Location location;
    private String name;
    private String score;

    public ObjectResults.Location getLocation() {
        if (location == null) {
            location = new ObjectResults().getLocation();
        }
        return location;
    }

    public void setLocation(ObjectResults.Location location) {
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("BodyInfo{");
        sb.append("location=").append(location);
        sb.append(", name='").append(name).append('\'');
        sb.append(", score='").append(score).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
