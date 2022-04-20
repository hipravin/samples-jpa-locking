package com.hipravin.samplesjpalocking;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

@SpringBootTest
@ActiveProfiles({"test"})
class SamplesJpaLockingApplicationTests {

    @Test
    void contextLoads() {
    }

}
