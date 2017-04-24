package com.qcloud.cos.model;

import java.util.Map;

/**
 * Interface for providing MaterialsDescription.
 */
public interface MaterialsDescriptionProvider {
    
    /**
     * Returns an unmodifiable view of the MaterialsDescription which the caller 
     * can use to load EncryptionMaterials from any {@link EncryptionMaterialsAccessor}
     * @return materials description.
     */
    public Map<String, String> getMaterialsDescription();
}
