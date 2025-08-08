package com.qcloud.cos.model.inventory;

import com.qcloud.cos.model.Tag.Tag;

import java.util.ArrayList;
import java.util.List;

public class InventoryAndPredicate extends InventoryFilterPredicate {
    private String prefix;

    private List<Tag> tags = new ArrayList<>();

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public List<Tag> getTags() {
        return tags;
    }

    @Override
    public void accept(InventoryPredicateVisitor inventoryPredicateVisitor) {}
}
