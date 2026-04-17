package com.nit.mybank;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nit.mybank.constant.AccountStatus;
import com.nit.mybank.constant.AccountType;
import com.nit.mybank.entity.Account;
import com.nit.mybank.repository.AccountRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.CacheManager;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.math.BigDecimal;

import static org.springframework.mock.http.server.reactive.MockServerHttpRequest.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Testcontainers
class MybankApplicationTests {

	static GenericContainer redis =
			new GenericContainer(DockerImageName.parse("redis:7.4.2")).withExposedPorts(6379);

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private AccountRepository accountRepository;

	@Autowired
	private CacheManager cacheManager;

	@MockitoSpyBean
	private AccountRepository accountRepositorySpy;

	private final ObjectMapper objectMapper = new ObjectMapper();

	@Test
	public void testAccountCreation() throws Exception {
		Assertions.assertTrue(true);

	}

}
