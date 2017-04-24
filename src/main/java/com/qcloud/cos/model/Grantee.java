package com.qcloud.cos.model;

/**
 * Represents a grantee (entity) that can be assigned access permissions in an
 * {@link AccessControlList}. All grantees have an ID of some kind, though the
 * format of the ID can differ depending on the kind of grantee.
 */
public interface Grantee {

    /**
     * Returns the identifier for the type of this grant, to be used when
     * specifying grants in the header of a request.
     */
    public String getTypeIdentifier();
    
    /**
     * Sets the identifier for this grantee. The meaning of the identifier is
     * specific to each implementation of the {@link Grantee}.
     *
     * @param id
     *            The identifier for this grantee.
     *            
     * @see Grantee#getIdentifier()
     */
    public void setIdentifier(String id);

    /**
     * Gets the identifier for this grantee. The meaning of the grantee
     * identifier is specific to each implementation of the {@link Grantee}.
     *
     * @return The identifier for this grantee.
     * 
     * @see Grantee#setIdentifier(String)
     */
    public String getIdentifier();

}