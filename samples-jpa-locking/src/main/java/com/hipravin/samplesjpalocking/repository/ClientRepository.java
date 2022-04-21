package com.hipravin.samplesjpalocking.repository;

import com.hipravin.samplesjpalocking.repository.entity.AccountEntity;
import com.hipravin.samplesjpalocking.repository.entity.ClientEntity;

import java.util.IdentityHashMap;
import java.util.List;

public interface ClientRepository {
    void saveClientsWithAccountsBatch(IdentityHashMap<ClientEntity, List<AccountEntity>> clientsWithAccounts);
}
