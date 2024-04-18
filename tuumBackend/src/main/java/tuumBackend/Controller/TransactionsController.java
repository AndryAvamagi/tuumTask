package tuumBackend.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tuumBackend.Mapper.Mapper;
import tuumBackend.Model.Account;
import tuumBackend.Model.Balance;
import tuumBackend.Model.Transaction;
import tuumBackend.Service.AccountService;

import java.util.ArrayList;
import java.util.Arrays;

@RestController
@RequestMapping("/api")
public class TransactionsController {

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



//    @GetMapping("/allCustomers")
//    public ResponseEntity<ArrayList<Customer>> getAllCustomers(){
//        return new ResponseEntity<>(Mapper.findAllCustomers(), HttpStatus.OK);
//    }

//    @GetMapping("/allBalances/{accountId}")
//    public ResponseEntity<ArrayList<Balance>> getAllBalancesOfAccount(@PathVariable("accountId") String accountId){
//        return new ResponseEntity<>(Mapper.findAllBalancesOfAccount(accountId), HttpStatus.OK);
//    }

}
