package com.hipravin.samplesjpalocking.repository;

import com.hipravin.samplesjpalocking.repository.entity.AccountEntity;
import com.hipravin.samplesjpalocking.repository.entity.ClientEntity;
import com.hipravin.samplesjpalocking.repository.exception.OperationFailedException;
import com.hipravin.samplesjpalocking.repository.exception.OperationForbiddenException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.Objects;
import java.util.function.Supplier;

@Repository
@Transactional
class AccountRepositoryImpl implements AccountRepository {
    private static final Logger log = LoggerFactory.getLogger(AccountRepositoryImpl.class);
    private final EntityManager em;

    public AccountRepositoryImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public void transfer(long ownerClientId, long accountIdFrom, long accountIdTo, BigDecimal transferAmount)
            throws OperationFailedException {
        AccountEntity accountFrom = em.find(AccountEntity.class, accountIdFrom);
        AccountEntity accountTo = em.find(AccountEntity.class, accountIdTo);
        ClientEntity client = em.find(ClientEntity.class, ownerClientId);

        checkAmountPositive(transferAmount);
        checkAllFound(client, accountFrom, accountTo, ownerClientId, accountIdFrom, accountIdTo);
        checkFromSameClient(client, accountFrom);
        checkEnoughMoney(accountFrom, transferAmount);

        BigDecimal fromAmount = accountFrom.getAvailableAmount();
        BigDecimal toAmount = accountTo.getAvailableAmount();
        BigDecimal fromAfterTransfer = fromAmount.subtract(transferAmount);
        BigDecimal toAfterTransfer = toAmount.add(transferAmount);

        accountFrom.setAvailableAmount(fromAfterTransfer);
        accountTo.setAvailableAmount(toAfterTransfer);
    }

    static void checkAmountPositive(BigDecimal amount) throws OperationFailedException{
        check(amount.compareTo(BigDecimal.ZERO) > 0,
                () -> new OperationFailedException("Transfer amount should be positive, actual: " + amount));
    }

    static void checkFromSameClient(ClientEntity owner, AccountEntity accountFrom) throws OperationFailedException {
        check(Objects.equals(owner.getClientId(), accountFrom.getClient().getClientId()),
                () -> new OperationForbiddenException("Forbidden, account belongs to different owner."));
    }

    static void checkEnoughMoney(AccountEntity accountFrom, BigDecimal transferAmount) throws OperationFailedException {
        check(accountFrom.getAvailableAmount().compareTo(transferAmount) >= 0,
                () -> new OperationForbiddenException("Forbidden: not enough money."));
    }

    static <T,ID> void checkFound(T object, ID id) throws OperationFailedException {
        check(object != null,
                () -> new OperationFailedException("Not found: " + id));
    }

    static void checkAllFound(ClientEntity client, AccountEntity accountFrom, AccountEntity accountTo,
                              long ownerClientId, long accountIdFrom, long accountIdTo) throws OperationFailedException {
        checkFound(client, ownerClientId);
        checkFound(accountFrom, accountIdFrom);
        checkFound(accountTo, accountIdTo);
    }

    static void check(boolean condition, Supplier<? extends OperationFailedException> exceptionSupplier) throws OperationFailedException {
        if(!condition) {
            throw exceptionSupplier.get();
        }
    }
}
