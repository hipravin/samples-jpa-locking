package com.hipravin.samplesjpalocking.repository;

import com.hipravin.samplesjpalocking.repository.entity.AccountEntity;
import com.hipravin.samplesjpalocking.repository.exception.OperationFailedException;
import com.hipravin.samplesjpalocking.repository.exception.OperationForbiddenException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles({"test"})
class AccountRepositoryTest {
    @Autowired
    JpaAccountRepository jpaAccountRepository;
    @Autowired
    AccountRepository accountRepository;

    @Test
    void testFindByClientId() {
        List<AccountEntity> accs = jpaAccountRepository.findByClientId(1L);

        assertEquals(2, accs.size());
        assertEquals(accs.get(0).getClient().getClientId(), accs.get(1).getClient().getClientId());
        assertEquals(accs.get(0).getClient().getFirstName(), accs.get(1).getClient().getFirstName());
        assertSame(accs.get(0).getClient(), accs.get(1).getClient()); //check ==
    }

    @Test
    void testTransferIncorrect() {
        assertThrows(OperationFailedException.class, () -> {
            accountRepository.transfer(1, 1, 2, BigDecimal.valueOf(-50_000));
        });
    }

    @Test
    void testTransferForbidden() {
        assertThrows(OperationForbiddenException.class, () -> {
            //different owner
            accountRepository.transfer(3, 1, 2, BigDecimal.valueOf(50_000));
        });
    }

    @Test
    void testTransferNotEnoughMOney() {
        assertThrows(OperationFailedException.class, () -> {
            //different owner
            accountRepository.transfer(1, 1, 2, BigDecimal.valueOf(1_500_000));
        });
    }

    @Test
    void testTransferInvalidNotFound() {
        assertThrows(OperationFailedException.class, () -> {
            //different owner
            accountRepository.transfer(-1, 1, 2, BigDecimal.valueOf(50_000));
        });
        assertThrows(OperationFailedException.class, () -> {
            //different owner
            accountRepository.transfer(1, -1, 2, BigDecimal.valueOf(50_000));
        });
        assertThrows(OperationFailedException.class, () -> {
            //different owner
            accountRepository.transfer(1, 1, -2, BigDecimal.valueOf(50_000));
        });
    }

    @Test
    void testCorrectTransfer1() throws OperationFailedException {
        accountRepository.transfer(1, 1, 2, BigDecimal.valueOf(50_000));
        AccountEntity fromAccount = jpaAccountRepository.findById(1L).orElseThrow();
        AccountEntity toAccount = jpaAccountRepository.findById(2L).orElseThrow();

        assertTrue(fromAccount.getAvailableAmount().compareTo(BigDecimal.valueOf(1_000_000 - 50_000)) == 0);
        assertTrue(toAccount.getAvailableAmount().compareTo(BigDecimal.valueOf(50_000)) == 0);
    }
}