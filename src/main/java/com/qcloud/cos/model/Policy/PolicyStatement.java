package com.qcloud.cos.model.Policy;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class PolicyStatement implements Serializable {
    public StatementPrincipal principal;
    public String effect;
    public List<String> action = new ArrayList<>();
    public List<String> resource = new ArrayList<>();
    public String sid;

    public StatementPrincipal getPrincipal() {
        return principal;
    }

    public void setPrincipal(StatementPrincipal statementPrincipal) {
        this.principal = statementPrincipal;
    }

    public String getEffect() {
        return effect;
    }

    public void setEffect(String Effect) {
        this.effect = Effect;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String Sid) {
        this.sid = Sid;
    }

    public List<String> getAction() {
        return action;
    }

    public void addAction(String action) {
        this.action.add(action);
    }

    public void addActions(List<String> actions) {
        this.action.addAll(actions);
    }

    public List<String> getResource() {
        return resource;
    }

    public void addResource(String resources) {
        this.resource.add(resources);
    }

    public void setResource(List<String> resources) {
        this.resource = resources;
    }

    public void addResources(List<String> resources) {
        this.resource.addAll(resources);
    }
}
