package com.hipravin.samplesjpalocking.repository;

import com.hipravin.samplesjpalocking.repository.entity.AccountEntity;
import com.hipravin.samplesjpalocking.repository.entity.ClientEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.IdentityHashMap;
import java.util.List;

@Repository
@Transactional
public class ClientRepositoryImpl implements ClientRepository {

    private final EntityManager em;

    public ClientRepositoryImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public void saveClientsWithAccountsBatch(IdentityHashMap<ClientEntity, List<AccountEntity>> clientsWithAccounts) {
        List<ClientEntity> clients = clientsWithAccounts.keySet().stream().toList();
        List<AccountEntity> accounts = clientsWithAccounts.entrySet().stream()
                .flatMap(e -> e.getValue().stream())
                .toList();

        clients.forEach(em::persist);
        accounts.forEach(em::persist);
    }
}
