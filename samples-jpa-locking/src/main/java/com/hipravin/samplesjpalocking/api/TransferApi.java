package com.hipravin.samplesjpalocking.api;

import com.hipravin.samplesjpalocking.repository.AccountRepository;
import com.hipravin.samplesjpalocking.repository.ClientRepository;
import com.hipravin.samplesjpalocking.repository.exception.OperationFailedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Stream;

@RestController
@RequestMapping("/api/v1/transfer")
public class TransferApi {
    private static final Logger log = LoggerFactory.getLogger(TransferApi.class);

    private final ClientRepository clientRepository;
    private final AccountRepository accountRepository;

    public TransferApi(ClientRepository clientRepository, AccountRepository accountRepository) {
        this.clientRepository = clientRepository;
        this.accountRepository = accountRepository;
    }

    @PostMapping("/single")
    ResponseEntity<?> transfer(
            @RequestParam("owner") long owner,
            @RequestParam("from") long from,
            @RequestParam("to") long to,
            @RequestParam("amount") BigDecimal amount) throws OperationFailedException {
        accountRepository.transfer(owner, from, to, amount);

        return ResponseEntity.ok("success");
    }

    @PostMapping("/stupidrandombulktranfer")
    ResponseEntity<?> transfer(
            @RequestParam("min") long clientMinId,
            @RequestParam("max") long clientMaxId,
            @RequestParam("iterations") long iterations,
            @RequestParam("threads") long threads)  {
        List<CompletableFuture<Void>> futures =
                Stream.generate(() -> transferRandomBulk(clientMinId, clientMaxId, iterations))
                        .limit(threads)
                        .toList();

        futures.forEach(CompletableFuture::join);

        return ResponseEntity.ok("success");
    }

    CompletableFuture<Void> transferRandomBulk(long clientIdMin, long clientIdMax, long iterations) {
        return CompletableFuture.supplyAsync(() -> {
            for (int i = 0; i < iterations; i++) {
                transferRandom(clientIdMin, clientIdMax);
                if (i % 100 == 0) {
                    log.info("{} iterations completed in thread {}", i, Thread.currentThread().getName());
                }
            }
            return null;
        });
    }


    void transferRandom(long clientIdMin, long clientIdMax) {
        long clientId = ThreadLocalRandom.current().nextLong(clientIdMin, clientIdMax + 1);
        long accountFrom = clientId * 2 - 1;
        long accountTo = clientId * 2;

        try {
            accountRepository.transfer(clientId, accountFrom, accountTo, BigDecimal.valueOf(1));
        } catch (OperationFailedException e) {
            log.error(e.getMessage(), e);
        }
    }

//    @ExceptionHandler(OperationFailedException.class)
//    public ResponseEntity<?> handleNotFound(OperationFailedException e) {
//        return new ResponseEntity<Object>(e.getMessage(), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
//    }
}
