package com.ddrissq.sigdosi;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@SpringBootTest
@Import(value = TestcontainersConfiguration.class)
class SigdosiApplicationTests {

    @Test
    void contextLoads() {
    }

}