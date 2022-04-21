package com.hipravin.samplesjpalocking.repository;

import com.hipravin.samplesjpalocking.DataGenUtils;
import com.hipravin.samplesjpalocking.repository.entity.AccountEntity;
import com.hipravin.samplesjpalocking.repository.entity.ClientEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.IdentityHashMap;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles({"test"})
class ClientRepositoryTest {
    @Autowired
    JpaClientRepository jpaClientRepository;

    @Autowired
    ClientRepository clientRepository;

    @Test
    void testByIdNotFound() {
        Optional<ClientEntity> notFound = jpaClientRepository.findById(-1L);
        assertFalse(notFound.isPresent());
    }

    @Test
    void testByIdFound() {
        Optional<ClientEntity> c1 = jpaClientRepository.findById(1L);
        assertTrue(c1.isPresent());
    }

    @Test
    void testFields() {
        ClientEntity c1 = jpaClientRepository.findById(1L).orElseThrow();

        assertEquals("John", c1.getFirstName());
        assertEquals("Doe", c1.getLastName());
        assertEquals("john.doe@mail.ru", c1.getEmail());
    }

    @Test
    void testInsertRandom() {
        long count = 10;
        IdentityHashMap<ClientEntity, List<AccountEntity>> clientsWithAccounts =
                DataGenUtils.generateRandomClientsWith2Accounts(count);

        clientRepository.saveClientsWithAccountsBatch(clientsWithAccounts);

        clientsWithAccounts.forEach((key, value) -> {
            assertNotNull(key.getClientId());
            assertNotNull(value.get(0).getAccountId());
            assertNotNull(value.get(1).getAccountId());
        });
    }
}