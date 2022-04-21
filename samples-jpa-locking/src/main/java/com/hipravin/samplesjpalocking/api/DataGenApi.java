package com.hipravin.samplesjpalocking.api;

import com.hipravin.samplesjpalocking.DataGenUtils;
import com.hipravin.samplesjpalocking.repository.ClientRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;

@RestController
@RequestMapping("/api/v1/gen")
public class DataGenApi {
    private final ClientRepository clientRepository;

    public DataGenApi(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @PostMapping("/addrandom/{count}")
    ResponseEntity<?> generateClientsAndAccounts(@PathVariable("count") long count) {
        clientRepository.saveClientsWithAccountsBatch(DataGenUtils.generateRandomClientsWith2Accounts(count));

        return ResponseEntity.ok("completed");
    }
}
