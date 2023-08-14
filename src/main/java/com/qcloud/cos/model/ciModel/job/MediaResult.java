package com.qcloud.cos.model.ciModel.job;

import com.thoughtworks.xstream.annotations.XStreamAlias;

public class MediaResult {
    @XStreamAlias("OutputFile")
    private OutputFile outputFile;

    public OutputFile getOutputFile() {
        if (outputFile == null) {
            outputFile = new OutputFile();
        }
        return outputFile;
    }

    public void setOutputFile(OutputFile outputFile) {
        this.outputFile = outputFile;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("MediaResult{");
        sb.append("outputFile=").append(outputFile);
        sb.append('}');
        return sb.toString();
    }
}
