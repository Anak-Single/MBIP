package com.internetprogramming.mbip;

import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootTest
@EnableTransactionManagement
@EntityScan(basePackages = "com.internetprogramming.mbip.Entity.User")
class MbipApplicationTests {

	@Test
	void contextLoads() {
	}

}
