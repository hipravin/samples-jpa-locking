package com.hipravin.samplesjpalocking.repository;

import com.hipravin.samplesjpalocking.repository.entity.ClientEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaClientRepository extends JpaRepository<ClientEntity, Long> {
}
