package com.hipravin.samplesjpalocking.repository;

import com.hipravin.samplesjpalocking.repository.exception.OperationFailedException;

import java.math.BigDecimal;

public interface AccountRepository {
    void transfer(long ownerClientId, long accountIdFrom, long accountIdTo, BigDecimal amount) throws OperationFailedException;
}
