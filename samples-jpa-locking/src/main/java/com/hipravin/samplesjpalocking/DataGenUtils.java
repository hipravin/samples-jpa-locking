package com.hipravin.samplesjpalocking;

import com.hipravin.samplesjpalocking.repository.entity.AccountEntity;
import com.hipravin.samplesjpalocking.repository.entity.ClientEntity;

import java.math.BigDecimal;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.UUID;

public abstract class DataGenUtils {
    private DataGenUtils() {
    }

    public static IdentityHashMap<ClientEntity, List<AccountEntity>> generateRandomClientsWith2Accounts(long clientCount) {
        IdentityHashMap<ClientEntity, List<AccountEntity>> result = new IdentityHashMap<>();

        for (int i = 0; i < clientCount; i++) {
             ClientEntity client = clientWithRandomFields();
             List<AccountEntity> accounts = coupleNewAccountsForClient(client);

             result.put(client, accounts);
        }

        return result;
    }

    static ClientEntity clientWithRandomFields() {
        ClientEntity ce = new ClientEntity();
        ce.setFirstName(UUID.randomUUID().toString());
        ce.setLastName(UUID.randomUUID().toString());
        ce.setEmail(ce.getFirstName() + "." + ce.getLastName() + "@mail.ru");

        return ce;
    }

    static List<AccountEntity> coupleNewAccountsForClient(ClientEntity ce) {
        AccountEntity acc1 = new AccountEntity();
        acc1.setClient(ce);
        acc1.setAvailableAmount(BigDecimal.valueOf(1_000_000));

        AccountEntity acc2 = new AccountEntity();
        acc2.setClient(ce);
        acc2.setAvailableAmount(BigDecimal.valueOf(500_000));

        return List.of(acc1, acc2);
    }
}
