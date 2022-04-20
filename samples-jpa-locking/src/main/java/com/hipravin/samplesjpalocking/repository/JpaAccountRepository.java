package com.hipravin.samplesjpalocking.repository;

import com.hipravin.samplesjpalocking.repository.entity.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface JpaAccountRepository extends JpaRepository<AccountEntity, Long> {
    @Query("select a from AccountEntity a join fetch a.client where a.client.clientId = :clientId")
    List<AccountEntity> findByClientId(long clientId);
}
