package ru.netology.moneytransferservice;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.testcontainers.containers.GenericContainer;
import ru.netology.moneytransferservice.model.Amount;
import ru.netology.moneytransferservice.model.SuccessfulOperation;
import ru.netology.moneytransferservice.model.TransferDataObject;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class MoneyTransferServiceImplApplicationTests {

    @Autowired
    private TestRestTemplate restTemplate;
    private GenericContainer<?> transferService = new GenericContainer<>("transferservice:1.0")
            .withExposedPorts(5500);

    @BeforeEach
    public void setUp(){
        transferService.start();
    }

    @Test
    void contextLoads() {
        Integer port = transferService.getMappedPort(5500);
        HttpEntity<TransferDataObject> tdo = new HttpEntity<>(
                new TransferDataObject("0000000000000000", "12/25", "123",
                        "1234567891234567", new Amount(100, "RUB")));


        SuccessfulOperation suc =
                restTemplate.postForObject("http://localhost:" + port + "/transfer", tdo, SuccessfulOperation.class );
        SuccessfulOperation so = new SuccessfulOperation("1");
        Assertions.assertEquals( suc, so);
    }

}
