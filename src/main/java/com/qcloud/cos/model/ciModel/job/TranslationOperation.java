package com.qcloud.cos.model.ciModel.job;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("Operation")
public class TranslationOperation {

    @XStreamAlias("Output")
    private TranslationOutput output;
    @XStreamAlias("UserData")
    private String userData;
    @XStreamAlias("JobLevel")
    private String jobLevel;
    @XStreamAlias("NoNeedOutput")
    private String noNeedOutput;
    @XStreamAlias("Translation")
    private OperationTranslation translation;

    public TranslationOutput getOutput() {
        if (output == null) {
            output = new TranslationOutput();
        }
        return output;
    }

    public void setOutput(TranslationOutput translationOutput) {
        this.output = translationOutput;
    }

    public String getUserData() {
        return userData;
    }

    public void setUserData(String userData) {
        this.userData = userData;
    }

    public String getJobLevel() {
        return jobLevel;
    }

    public void setJobLevel(String jobLevel) {
        this.jobLevel = jobLevel;
    }

    public String getNoNeedOutput() {
        return noNeedOutput;
    }

    public void setNoNeedOutput(String noNeedOutput) {
        this.noNeedOutput = noNeedOutput;
    }

    public OperationTranslation getTranslation() {
        if (translation == null) {
            translation = new OperationTranslation();
        }
        return translation;
    }

    public void setTranslation(OperationTranslation translation) {
        this.translation = translation;
    }
}
