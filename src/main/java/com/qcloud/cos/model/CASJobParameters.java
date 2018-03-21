package com.qcloud.cos.model;

import java.io.Serializable;


/**
 * CAS related prameters pertaining to a job.
 */
public class CASJobParameters implements Serializable {

    private String tier;

    /**
     * @return CAS retrieval tier at which the restore will be processed.
     */
    public String getTier() {
        return tier;
    }

    /**
     * Sets CAS retrieval tier at which the restore will be processed.
     *
     * @param tier New tier value
     */
    public void setTier(String tier) {
        this.tier = tier;
    }

    /**
     * Sets CAS retrieval tier at which the restore will be processed.
     *
     * @param tier New tier enum value.
     */
    public void setTier(Tier tier) {
        setTier(tier == null ? null : tier.toString());
    }

    /**
     * Sets CAS retrieval tier at which the restore will be processed.
     *
     * @param tier New tier value.
     * @return This object for method chaining.
     */
    public CASJobParameters withTier(String tier) {
        setTier(tier);
        return this;
    }

    /**
     * Sets CAS retrieval tier at which the restore will be processed.
     *
     * @param tier New tier enum value.
     * @return This object for method chaining.
     */
    public CASJobParameters withTier(Tier tier) {
        setTier(tier);
        return this;
    }
}