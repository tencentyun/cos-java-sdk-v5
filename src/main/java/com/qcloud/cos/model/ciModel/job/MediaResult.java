package com.qcloud.cos.model.ciModel.job;

public class MediaResult {
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
}
