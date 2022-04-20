package com.hipravin.samplesjpalocking.repository;

import com.hipravin.samplesjpalocking.repository.entity.ClientEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles({"test"})
class ClientRepositoryTest {
    @Autowired
    JpaClientRepository clientRepository;

    @Test
    void testCountInitial() {
        long count = clientRepository.count();

        assertEquals(3, count);
    }

    @Test
    void testByIdNotFound() {
        Optional<ClientEntity> notFound = clientRepository.findById(-1L);
        assertFalse(notFound.isPresent());
    }

    @Test
    void testByIdFound() {
        Optional<ClientEntity> c1 = clientRepository.findById(1L);
        assertTrue(c1.isPresent());
    }

    @Test
    void testFields() {
        ClientEntity c1 = clientRepository.findById(1L).orElseThrow();

        assertEquals("John", c1.getFirstName());
        assertEquals("Doe", c1.getLastName());
        assertEquals("john.doe@mail.ru", c1.getEmail());
    }
}