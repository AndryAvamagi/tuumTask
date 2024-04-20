package tuumBackend.Controller;

import netscape.javascript.JSObject;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import tuumBackend.Mapper.Mapper;
import tuumBackend.Messages.CustomMessage;
import tuumBackend.Messages.RabbitmqConfig;
import tuumBackend.Model.Account;
import tuumBackend.Model.Balance;
import tuumBackend.Model.ResponseTransaction;
import tuumBackend.Model.Transaction;
import tuumBackend.Service.AccountService;
//import tuumBackend.Service.AccountService;

import java.util.*;

@RestController
@RequestMapping("/api")
public class TransactionsController {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private Mapper mapper;

    @Autowired
    private AccountService accountService;





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



//    ---------------------------------------------------------------------------------TESTING------------------------------------------------------------------------------------------------------------


    @GetMapping("/testAccountBalance")
    public ResponseEntity<Double> getBalance(){
        Double sum = mapper.findBalanceOfAccount("ec0061a0-c24c-4976-b92b-d08949e02a53", "EUR");
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


    @RequestMapping(
            method = RequestMethod.GET,
            value = "/transaction",
            produces = { "application/json" }
    )
    public ResponseEntity<ArrayList<Transaction>> getTransactions(@RequestParam String accountId){
        Account foundAcc = mapper.findAllAccountById(accountId);

        if(foundAcc == null){
            return new ResponseEntity<>((ArrayList<Transaction>) Arrays.asList(new Transaction("ACCOUNT NOT FOUND")),HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(mapper.findAllTransactionsById(accountId), HttpStatus.OK);
    }




//    -------------------------------------------------------------------------------------MESSAGE PUBLISHING------------------------------------------------------------------------------------------------

    @RequestMapping(
            method = RequestMethod.POST,
            value = "/publishTransaction"
    )
    public ResponseEntity<ResponseTransaction> publishMessageTransaction(@RequestBody Transaction transaction){

        ResponseTransaction res = accountService.transactionValid(transaction);

        if(res.getAccountId() == null) return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);

        CustomMessage message = new CustomMessage(transaction);

        rabbitTemplate.convertAndSend(
                RabbitmqConfig.EXCHANGE,
                RabbitmqConfig.TRANSACTION_ROUTING,
                message
                );

        return new ResponseEntity<>(res, HttpStatus.OK);
    }



    @RequestMapping(
            method = RequestMethod.POST,
            value = "/publishAccount"
    )
    public ResponseEntity<String> publishMessageAccount(@RequestBody CustomMessage message){

        if(!accountService.currenciesValid(message.getCurrencies())) return new ResponseEntity<>("Currencies not valid", HttpStatus.BAD_REQUEST);

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
