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


//    ----------------------------------------------------------------------------------------ALL DATA-------------------------------------------------------------------------------------------------------------------

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


//    ---------------------------------------------------------------------------------TESTING------------------------------------------------------------------------------------------------------------


    @GetMapping("/testAccountBalance")
    public ResponseEntity<Double> getBalance(){
        Double sum = mapper.getBalanceOfAccount("ec0061a0-c24c-4976-b92b-d08949e02a53", "EUR");
        return new ResponseEntity<>(sum, HttpStatus.OK);
    }

//    --------------------------------------------------------------------------------------ACCOUNT GET----------------------------------------------------------------------------------------
    @RequestMapping(
        method = RequestMethod.GET,
        value = "/account",
        produces = { "application/json" }
    )
    public ResponseEntity<Account> getAccount(@RequestParam String accountId){
        Account foundAcc = mapper.findAllAccountById(accountId);

        if(foundAcc == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        ArrayList<Balance> foundBalances = mapper.findAllBalancesById(accountId);

        return new ResponseEntity<>(new Account(foundAcc.getAccountId(), foundAcc.getCustomerId(), foundAcc.getCountry(), foundBalances), HttpStatus.OK);
    }






//    -------------------------------------------------------------------------------------MESSAGE PUBLISHING------------------------------------------------------------------------------------------------

    @RequestMapping(
            method = RequestMethod.POST,
            value = "/publishTransaction"
    )

    public ResponseEntity<String> publishMessageTransaction(@RequestBody Transaction transaction){

        //TODO CHECK IF OK

        CustomMessage message = new CustomMessage(transaction);
        rabbitTemplate.convertAndSend(
                RabbitmqConfig.EXCHANGE,
                RabbitmqConfig.TRANSACTION_ROUTING,
                message
                );
        return new ResponseEntity<>("Transaction OK", HttpStatus.OK);
    }



    @RequestMapping(
            method = RequestMethod.POST,
            value = "/publishAccount"
    )
    public ResponseEntity<String> publishMessageAccount(@RequestBody CustomMessage message){

        //TODO CHECK IF VALID

        rabbitTemplate.convertAndSend(
                RabbitmqConfig.EXCHANGE,
                RabbitmqConfig.ACCOUNT_ROUTING,
                message
        );
        return new ResponseEntity<>("OK", HttpStatus.OK);
    }



    //---------------------------------------------------------------------------------------------------KORRALIK--------------------------------------------------------------------------------------


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


//    --------------------------------------------------------------------------------------------------------------GOOD TO HAVE ---------------------------------------------------------------------------------------

//    @GetMapping("/allCustomers")
//    public ResponseEntity<ArrayList<Customer>> getAllCustomers(){
//        return new ResponseEntity<>(Mapper.findAllCustomers(), HttpStatus.OK);
//    }

//    @GetMapping("/allBalances/{accountId}")
//    public ResponseEntity<ArrayList<Balance>> getAllBalancesOfAccount(@PathVariable("accountId") String accountId){
//        return new ResponseEntity<>(Mapper.findAllBalancesOfAccount(accountId), HttpStatus.OK);
//    }

}
