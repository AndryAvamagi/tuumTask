//package tuumBackend;
//
//
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.annotation.DirtiesContext;
//import org.springframework.test.context.DynamicPropertyRegistry;
//import org.springframework.test.context.DynamicPropertySource;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.web.context.WebApplicationContext;
//import org.testcontainers.containers.GenericContainer;
//import org.testcontainers.containers.PostgreSQLContainer;
//import org.testcontainers.junit.jupiter.Container;
//import org.testcontainers.junit.jupiter.Testcontainers;
//import org.testcontainers.utility.MountableFile;
//
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
//@Testcontainers
//@AutoConfigureMockMvc
//public class MessageTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @Autowired
//    private WebApplicationContext webApplicationContext;
//
//
//    @Container
//    static PostgreSQLContainer database = new PostgreSQLContainer<>("postgres:latest")
//            .withDatabaseName("db")
//            .withUsername("root")
//            .withPassword("root")
//            .withCopyFileToContainer(
//                    MountableFile.forClasspathResource(
//                            "init.sql"), "/docker-entrypoint-initdb.d/"
//            );
//
//    @Container
//    static GenericContainer<?> container =
//            new GenericContainer<>("/TransactionConsumerDocker/.")
//                    .withExposedPorts(9999);
//
//
//
//
//    @DynamicPropertySource
//    public static void overrideProps(DynamicPropertyRegistry registry){
//        registry.add("spring.datasource.url", database::getJdbcUrl);
//        registry.add("spring.datasource.username", database::getUsername);
//        registry.add("spring.datasource.password", database::getPassword);
//        registry.add("ws.card.base-url",
//                () -> "http://localhost:" + container.getMappedPort(9999));
//    }
//
//    @Test
//    void test(){
//
//    }
//}
