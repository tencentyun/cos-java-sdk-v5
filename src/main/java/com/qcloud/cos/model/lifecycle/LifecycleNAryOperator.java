package com.qcloud.cos.model.lifecycle;

import java.util.List;

/**
 *  Abstract class representing an operator that acts on N number of predicates.
 */
abstract class LifecycleNAryOperator extends LifecycleFilterPredicate {

    private final List<LifecycleFilterPredicate> operands;

    public LifecycleNAryOperator(List<LifecycleFilterPredicate> operands) {
        this.operands = operands;
    }

    public List<LifecycleFilterPredicate> getOperands() {
        return operands;
    }
}
