package tuumBackend.Controller;

import netscape.javascript.JSObject;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tuumBackend.Mapper.Mapper;
import tuumBackend.Messages.CustomMessage;
import tuumBackend.Messages.RabbitmqConfig;
import tuumBackend.Model.Account;
import tuumBackend.Model.Balance;
import tuumBackend.Model.Transaction;
import tuumBackend.Service.AccountService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class TransactionsController {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private Mapper mapper;

    @Autowired
    private AccountService service;


    @GetMapping("/allTransactions")
    public ResponseEntity<ArrayList<Transaction>> getAllTransactions(){
        return new ResponseEntity<>(mapper.findAllTransactions(), HttpStatus.OK);
    }
    @GetMapping("/allBalances")
    public ResponseEntity<ArrayList<Balance>> getAllBalances(){
        return new ResponseEntity<>(mapper.findAllBalances(), HttpStatus.OK);
    }

    @GetMapping("/allAccounts")
    public ResponseEntity<ArrayList<Account>> getAllAccounts(){
        return new ResponseEntity<>(mapper.findAllAccounts(), HttpStatus.OK);
    }

    @GetMapping("/allCurrencies")
    public ResponseEntity<ArrayList<String>> getAllUsedCurrencies(){
        return new ResponseEntity<>(mapper.getAllUsedCurrenciesByAccountId("ec0061a0-c24c-4976-b92b-d08949e02a53"), HttpStatus.OK);
    }




    @GetMapping("/testCreatingAccount")
    public ResponseEntity<String> createAccount(){
        service.createAccount("4", "Japan", Arrays.asList("EUR", "USD"));
        return new ResponseEntity<>("OK", HttpStatus.OK);
    }

    @GetMapping("/testTransaction")
    public ResponseEntity<String> insertTransaction(){
        service.createTransaction("d8f32f9f-6cfc-4846-a1cd-0bc073c9a81c", 10.5, "EUR", "IN", "TEST3");
        return new ResponseEntity<>("OK", HttpStatus.OK);
    }


    @GetMapping("/testAccountBalance")
    public ResponseEntity<Double> getBalance(){
        Double sum = mapper.getBalanceOfAccount("ec0061a0-c24c-4976-b92b-d08949e02a53", "EUR");
        return new ResponseEntity<>(sum, HttpStatus.OK);
    }


    @PostMapping("/publishTransaction")
    public String publishMessageTransaction(@RequestBody Transaction transaction){
        CustomMessage message = new CustomMessage(transaction);
        rabbitTemplate.convertAndSend(
                RabbitmqConfig.EXCHANGE,
                RabbitmqConfig.TRANSACTION_ROUTING,
                message
                );
        return "edukas";
    }

    @PostMapping("/publishAccount")
    public String publishMessageTransaction(@RequestBody CustomMessage message){
        rabbitTemplate.convertAndSend(
                RabbitmqConfig.EXCHANGE,
                RabbitmqConfig.ACCOUNT_ROUTING,
                message
        );
        return "edukas";
    }



    //KORRALIK


//    @RequestMapping(
//            method = RequestMethod.POST,
//            value = "/applications",
//            produces = { "application/json" },
//            consumes = { "application/json" }
//    )
//
//    default ResponseEntity<ApplicationDto> addApplication(@Valid @RequestBody ApplicationDto applicationDto) {
//
//
//    @RequestMapping(
//        method = RequestMethod.GET,
//        value = "/applications",
//        produces = { "application/json" }
//    )
//
//    default ResponseEntity<List<ApplicationDto>> getApplications() {


//    @GetMapping("/allCustomers")
//    public ResponseEntity<ArrayList<Customer>> getAllCustomers(){
//        return new ResponseEntity<>(Mapper.findAllCustomers(), HttpStatus.OK);
//    }

//    @GetMapping("/allBalances/{accountId}")
//    public ResponseEntity<ArrayList<Balance>> getAllBalancesOfAccount(@PathVariable("accountId") String accountId){
//        return new ResponseEntity<>(Mapper.findAllBalancesOfAccount(accountId), HttpStatus.OK);
//    }

}
