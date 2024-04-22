package tuumBackend;

import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.ContentResultMatchers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.containers.RabbitMQContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.MountableFile;
import tuumBackend.Controller.TransactionsController;
import tuumBackend.Mapper.Mapper;
import tuumBackend.Model.Transaction;

import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.MountableFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@Testcontainers
@AutoConfigureMockMvc
class TuumBackendApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext webApplicationContext;

	@Autowired
	private Mapper mapper;

	@Container
	static PostgreSQLContainer database = new PostgreSQLContainer<>("postgres:latest")
			.withDatabaseName("db")
			.withUsername("root")
			.withPassword("root")
			.withCopyFileToContainer(
					MountableFile.forClasspathResource(
							"init.sql"), "/docker-entrypoint-initdb.d/"
			);



    @DynamicPropertySource
	public static void overrideProps(DynamicPropertyRegistry registry){
		registry.add("spring.datasource.url", database::getJdbcUrl);
		registry.add("spring.datasource.username", database::getUsername);
		registry.add("spring.datasource.password", database::getPassword);
	}


	@BeforeEach
	public void setup(){
		database.start();
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}

	@Test
	void getAllTransactionsBadRequest() throws Exception {

		mockMvc.perform(MockMvcRequestBuilders
						.get("/api/transaction")
						.param("accountId", "somethingWrong")
						.accept("application/json")
				)
				.andExpect(status().isNotFound())
				.andExpect(jsonPath("$[0].description").value("ACCOUNT NOT FOUND"));
	}


	@Test
	void getAllTransactionsValid() throws Exception {

		mockMvc.perform(MockMvcRequestBuilders
				.get("/api/transaction")
				.param("accountId", "acc1")
				.accept("application/json")
				)
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(4)))

				.andExpect(jsonPath("$[0].accountId").value("acc1"))
				.andExpect(jsonPath("$[0].amount").value(0.0))
				.andExpect(jsonPath("$[0].currency").value("EUR"))
				.andExpect(jsonPath("$[0].direction").value("IN"))

				.andExpect(jsonPath("$[1].accountId").value("acc1"))
				.andExpect(jsonPath("$[1].amount").value(0.0))
				.andExpect(jsonPath("$[1].currency").value("USD"))
				.andExpect(jsonPath("$[1].direction").value("IN"))

				.andExpect(jsonPath("$[2].accountId").value("acc1"))
				.andExpect(jsonPath("$[2].amount").value(0.0))
				.andExpect(jsonPath("$[2].currency").value("GBP"))
				.andExpect(jsonPath("$[2].direction").value("IN"))
				.andExpect(jsonPath("$[2].direction").exists())

				.andExpect(jsonPath("$[3].accountId").value("acc1"))
				.andExpect(jsonPath("$[3].amount").value(50.0))
				.andExpect(jsonPath("$[3].currency").value("EUR"))
				.andExpect(jsonPath("$[3].direction").value("IN"));
	}

	@Test
	void getAccountValid() throws Exception {

		mockMvc.perform(MockMvcRequestBuilders
						.get("/api/account")
						.param("accountId", "acc1")
						.accept("application/json")
				)
				.andExpect(status().isOk())


				.andExpect(jsonPath("$.accountId").value("acc1"))
				.andExpect(jsonPath("$.customerId").value("AndryAvamagi"))
				.andExpect(jsonPath("$.country").value("EST"))
				.andExpect(jsonPath("$.balances.[0].accountId").value("acc1"))
				.andExpect(jsonPath("$.balances.[0].currency").value("EUR"))
				.andExpect(jsonPath("$.balances.[0].totalAmount").value(50.0))

				.andExpect(jsonPath("$.balances.[1].accountId").value("acc1"))
				.andExpect(jsonPath("$.balances.[1].currency").value("USD"))
				.andExpect(jsonPath("$.balances.[1].totalAmount").value(0.0))

				.andExpect(jsonPath("$.balances.[2].accountId").value("acc1"))
				.andExpect(jsonPath("$.balances.[2].currency").value("GBP"))
				.andExpect(jsonPath("$.balances.[2].totalAmount").value(0.0));
	}

	@Test
	void getAccountBadAccount() throws Exception {

		mockMvc.perform(MockMvcRequestBuilders
						.get("/api/account")
						.param("accountId", "wrongAccount")
						.accept("application/json")
				)
				.andExpect(status().isNotFound());
	}

	@Test
	void getAllAccounts() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders
				.get("/api/allAccounts")
				.accept("application/json")
		)
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].accountId").value("acc1"));
	}

	@Test
	void getAllAccountIds() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders
						 .get("/api/allAccountIds")
						.accept("application/json")
				)
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0]").value("acc1"));
	}




}
