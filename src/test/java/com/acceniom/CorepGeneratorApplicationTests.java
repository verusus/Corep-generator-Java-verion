package com.acceniom;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

@SpringBootTest
class CorepGeneratorApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	void floor(){
		Assert.isTrue(Math.round(3.3)==4);
	}

}
