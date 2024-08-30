package com.qcloud.cos.model.ciModel.job;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

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

    public List<PersonTag> getPersonTags() {
        return personTags;
    }

    public void setPersonTags(List<PersonTag> personTags) {
        this.personTags = personTags;
    }

    public List<PlaceTag> getPlaceTags() {
        return placeTags;
    }

    public void setPlaceTags(List<PlaceTag> placeTags) {
        this.placeTags = placeTags;
    }

    public List<ActionTag> getActionTags() {
        return actionTags;
    }

    public void setActionTags(List<ActionTag> actionTags) {
        this.actionTags = actionTags;
    }

    public List<ObjectTag> getObjectTags() {
        return ObjectTags;
    }

    public void setObjectTags(List<ObjectTag> objectTags) {
        ObjectTags = objectTags;
    }

    public List<MediaTags> getTags() {
        return tags;
    }

    public void setTags(List<MediaTags> tags) {
        this.tags = tags;
    }
}
