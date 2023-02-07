package com.qcloud.cos.model.HeadObjects;

import com.qcloud.cos.model.ObjectMetadata;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

public class HeadObjectsResult {
    private LinkedBlockingQueue<HeadedObject> headedObjects;

    public HeadObjectsResult(LinkedBlockingQueue<HeadedObject> headedObjects) {
        this.headedObjects = headedObjects;
    }

    public List<HeadedObject> getHeadedObjects() {
        return new ArrayList<>(headedObjects);
    }

    /**
     * A successfully deleted object.
     */
    static public class HeadedObject implements Serializable {
        private static final int STATUS_SUCCESS_ = 1;
        private static final int STATUS_FAILED_ = 0;

        private String key;
        private ObjectMetadata objectMetadata;
        private boolean isExist;
        private int request_status;

        public HeadedObject(String key, ObjectMetadata objectMetadata, boolean isExist, boolean isSuccess) {
            this.key = key;
            this.objectMetadata = objectMetadata;
            this.isExist = isExist;
            if (isSuccess) {
                this.request_status = STATUS_SUCCESS_;
            } else {
                this.request_status = STATUS_FAILED_;
            }
        }


        /**
         * Returns the key that was successfully headed.
         */
        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public void setExist(boolean isExist) {
            this.isExist = isExist;
        }

        public boolean isExist() {
            return isExist;
        }

        public boolean isSuccess() {
            return request_status == STATUS_SUCCESS_;
        }

        public boolean isFailed() {
            return request_status == STATUS_FAILED_;
        }

        /**
         * Returns the version deleted, or null for unversioned objects.
         */
        public ObjectMetadata getObjectMetadata() {
            return objectMetadata;
        }

        public void setObjectMetadata(ObjectMetadata objectMetadata) {
            this.objectMetadata = objectMetadata;
        }
    }
}


