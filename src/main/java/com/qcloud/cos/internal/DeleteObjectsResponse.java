package com.qcloud.cos.internal;

import java.util.ArrayList;
import java.util.List;

import com.qcloud.cos.exception.MultiObjectDeleteException.DeleteError;
import com.qcloud.cos.model.DeleteObjectsResult.DeletedObject;


public class DeleteObjectsResponse {
    private List<DeletedObject> deletedObjects;
    private List<DeleteError> errors;
    
    public DeleteObjectsResponse() {
        this(new ArrayList<DeletedObject>(), new ArrayList<DeleteError>());
    }

    public DeleteObjectsResponse(List<DeletedObject> deletedObjects, List<DeleteError> errors) {
        this.deletedObjects = deletedObjects;
        this.errors = errors;
    }

    public List<DeletedObject> getDeletedObjects() {
        return deletedObjects;
    }

    public void setDeletedObjects(List<DeletedObject> deletedObjects) {
        this.deletedObjects = deletedObjects;
    }

    public List<DeleteError> getErrors() {
        return errors;
    }

    public void setErrors(List<DeleteError> errors) {
        this.errors = errors;
    }
}
