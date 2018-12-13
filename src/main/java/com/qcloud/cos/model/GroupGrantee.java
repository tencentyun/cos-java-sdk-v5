package com.qcloud.cos.model;

/**
 * Specifies constants defining a group of COS users who can be granted permissions to buckets and
 * objects. This enumeration contains all the valid COS group grantees.
 */
public enum GroupGrantee implements Grantee {

    AllUsers("http://cam.qcloud.com/groups/global/AllUsers");
    
    @Override
    public String getTypeIdentifier() {
        return "uri";
    }

    private String groupUri;
    
    private GroupGrantee(String groupUri) {
        this.groupUri = groupUri;
    }
    
    /**
     * Gets the group grantee's URI.
     * 
     * @return The group grantee's URI. 
     */
    public String getIdentifier() {
        return groupUri;
    }
  
    public String toString() {
        return "GroupGrantee [" + groupUri + "]";
    }


    public static GroupGrantee parseGroupGrantee(String groupUri) {
        for (GroupGrantee grantee : GroupGrantee.values()) {
            if (grantee.groupUri.equals(groupUri)) {
                return grantee;
            }
        }
        return null;
    }

    @Override
    public void setIdentifier(String id) {
        throw new UnsupportedOperationException(
                "Group grantees identifiers can't be modified, legal value is uri");        
    }

}
