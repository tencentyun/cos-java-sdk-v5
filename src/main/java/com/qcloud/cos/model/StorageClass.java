package com.qcloud.cos.model;

/**
 * <p>
 * Specifies constants that define COS storage classes. The standard storage class
 * is the default storage class.
 * </p>
 * <p>
 * Qcloud COS offers multiple storage classes for different customers' needs. The
 * <code>STANDARD</code> storage class is the default storage class, and means that
 * redundant copies of data will be stored in different locations.
 * </p>
 * <p>
 * The <code>REDUCED_REDUNDANCY</code> storage class offers customers who are using Qcloud COS
 * for storing non-critical, reproducible data a low-cost highly available,
 * but less redundant, storage option.
 * </p>
 */
public enum StorageClass {

    /**
     * The default COS class. This storage class
     * is recommended for critical, non-reproducible data.  The standard
     * storage class is a highly available and highly redundant storage option
     * provided for an affordable price.
     */
    Standard("STANDARD"),

    /**
     * The reduced redundancy storage class.
     * This storage class allows customers to reduce their storage costs
     * in return for a reduced level of data redundancy. Customers who are using
     * Qcloud COS for storing non-critical, reproducible data can choose this
     * low cost and highly available, but less redundant, storage option.
     */
    ReducedRedundancy("REDUCED_REDUNDANCY"),

    /**
     * The Qcloud Coldline storage class.
     * This storage class means your object's data is stored in Qcloud Coldline,
     * and Qcloud COS stores a reference to the data in the Qcloud COS bucket.
     */
    ColdLine("ColdLine");

    /**
     * Returns the Qcloud COS {@link StorageClass} enumeration value representing the
     * specified Qcloud COS <code>StorageClass</code> ID string.
     * If the specified string doesn't map to a known Qcloud COS storage class,
     * an <code>IllegalArgumentException</code> is thrown.
     *
     * @param Cos StorageClassString
     *            The Qcloud COS storage class ID string.
     *
     * @return The Qcloud COS <code>StorageClass</code> enumeration value representing the
     *         specified Qcloud COS storage class ID.
     *
     * @throws IllegalArgumentException
     *             If the specified value does not map to one of the known
     *             Qcloud COS storage classes.
     */
    public static StorageClass fromValue(String cosStorageClassString) throws IllegalArgumentException {
        for (StorageClass storageClass : StorageClass.values()) {
            if (storageClass.toString().equals(cosStorageClassString)) return storageClass;
        }

        throw new IllegalArgumentException(
                "Cannot create enum from " + cosStorageClassString + " value!");
    }

    private final String storageClassId;

    private StorageClass(String id) {
        this.storageClassId = id;
    }

    /* (non-Javadoc)
     * @see java.lang.Enum#toString()
     */
    @Override
    public String toString() {
        return storageClassId;
    }

}
