package tuumBackend.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tuumBackend.Mapper.Mapper;
import tuumBackend.Model.Account;
import tuumBackend.Model.Balance;
import tuumBackend.Model.Transaction;

import java.util.*;

@Service
public class AccountService {
    @Autowired
    private tuumBackend.Mapper.Mapper mapper;
    private final List<String> ALLOWED_CURRENCIES = Arrays.asList("EUR","SEK","GBP", "USD");

    public void createTransaction(String accountId, double amount, String currency, String direction, String description){
        if(amount < 0.0) return;
        if(!direction.equals("IN") && !direction.equals("OUT")) return;
        if(direction.equals("OUT")) amount = amount * -1;

        Transaction transaction = new Transaction(UUID.randomUUID().toString(), accountId, amount, currency, direction, description);

        Balance balance = mapper.getBalance(accountId, currency);
        double totalAmount = balance.getTotalAmount();

        mapper.updateTotalAmount(amount, balance);
        mapper.insertTransaction(transaction);
    }

    public void createAccount(String customerId, String country, List<String> currencies){
        String accountId = UUID.randomUUID().toString();

        for(String currency : currencies){
            if (!ALLOWED_CURRENCIES.contains(currency)){
                System.out.println("NOT 100% correct");
                return;
            }
        }

        Account account = new Account(accountId, customerId, country);
        mapper.insertAccount(account);

        for(String currency : currencies){
            mapper.insertBalance(new Balance(accountId, currency, 0.0));
            createTransaction(accountId, 0.0, currency, "IN", "CREATED ACCOUNT");
        }
    }



}
