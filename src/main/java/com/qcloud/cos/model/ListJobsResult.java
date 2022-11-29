package com.qcloud.cos.model;

import com.qcloud.cos.utils.Jackson;

import java.io.Serializable;
import java.util.List;

public class ListJobsResult implements Serializable {

    private List<DecompressionJob> jobs;

    private String nextToken;

    public List<DecompressionJob> getJobs() {
        return jobs;
    }

    public void setJobs(List<DecompressionJob> jobs) {
        this.jobs = jobs;
    }

    public String getNextToken() {
        return nextToken;
    }

    public void setNextToken(String nextToken) {
        this.nextToken = nextToken;
    }

    public static class DecompressionJob implements Serializable{

        private String key;
        private String decompressionPrefix;
        private String creationTime;
        private String TerminationTime;
        private String jobId;
        private String status;

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getDecompressionPrefix() {
            return decompressionPrefix;
        }

        public void setDecompressionPrefix(String decompressionPrefix) {
            this.decompressionPrefix = decompressionPrefix;
        }

        public String getCreationTime() {
            return creationTime;
        }

        public void setCreationTime(String creationTime) {
            this.creationTime = creationTime;
        }

        public String getTerminationTime() {
            return TerminationTime;
        }

        public void setTerminationTime(String terminationTime) {
            TerminationTime = terminationTime;
        }

        public String getJobId() {
            return jobId;
        }

        public void setJobId(String jobId) {
            this.jobId = jobId;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        @Override
        public String toString() {
            return Jackson.toJsonPrettyString(this);
        }
    }

    @Override
    public String toString() {
        return Jackson.toJsonPrettyString(this);
    }


}
