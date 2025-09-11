package com.qcloud.cos.model.ciModel.job;

import com.thoughtworks.xstream.annotations.XStreamImplicit;

import java.util.ArrayList;
import java.util.List;

public class VideoTagData {
    @XStreamImplicit(itemFieldName = "Tags")
    private List<MediaTags> tags;
    @XStreamImplicit(itemFieldName = "PersonTags")
    private List<PersonTag> personTags;
    @XStreamImplicit(itemFieldName = "PlaceTags")
    private List<PlaceTag> placeTags;
    @XStreamImplicit(itemFieldName = "ActionTags")
    private List<ActionTag> actionTags;
    @XStreamImplicit(itemFieldName = "ObjectTags")
    private List<ObjectTag> ObjectTags;
    @XStreamImplicit(itemFieldName = "Labels")
    private List<VideoTagLabels> labels;

    public List<PersonTag> getPersonTags() {
        if (personTags == null) {
            personTags = new ArrayList<>();
        }
        return personTags;
    }

    public void setPersonTags(List<PersonTag> personTags) {
        this.personTags = personTags;
    }

    public List<PlaceTag> getPlaceTags() {
        if (placeTags == null) {
            placeTags = new ArrayList<>();
        }
        return placeTags;
    }

    public void setPlaceTags(List<PlaceTag> placeTags) {
        this.placeTags = placeTags;
    }

    public List<ActionTag> getActionTags() {
        if (actionTags == null) {
            actionTags = new ArrayList<>();
        }
        return actionTags;
    }

    public void setActionTags(List<ActionTag> actionTags) {
        this.actionTags = actionTags;
    }

    public List<ObjectTag> getObjectTags() {
        if (ObjectTags == null) {
            ObjectTags = new ArrayList<>();
        }
        return ObjectTags;
    }

    public void setObjectTags(List<ObjectTag> objectTags) {
        ObjectTags = objectTags;
    }

    public List<MediaTags> getTags() {
        if (tags == null) {
            tags = new ArrayList<>();
        }
        return tags;
    }

    public void setTags(List<MediaTags> tags) {
        this.tags = tags;
    }

    public List<VideoTagLabels> getLabels() {
        if (labels == null) {
            labels = new ArrayList<>();
        }
        return labels;
    }

    public void setLabels(List<VideoTagLabels> labels) {
        this.labels = labels;
    }
}
