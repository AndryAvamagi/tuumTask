package accountConsumer.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import accountConsumer.Model.Account;
import accountConsumer.Model.Balance;
import accountConsumer.Model.Transaction;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
public class AccountService {
    @Autowired
    private accountConsumer.Mapper.Mapper mapper;

    private final List<String> ALLOWED_CURRENCIES = Arrays.asList("EUR","SEK","GBP", "USD");

    public void createTransaction(String accountId, double amount, String currency, String direction, String description){
        if(amount < 0.0) return;
        if(!direction.equals("IN") && !direction.equals("OUT")) return;
        if(direction.equals("OUT")) amount = amount * -1;

        Transaction transaction = new Transaction(UUID.randomUUID().toString(), accountId, amount, currency, direction, description);

        Balance balance = mapper.getBalance(accountId, currency);

        mapper.updateTotalAmount(amount, balance);
        mapper.insertTransaction(transaction);
    }


    /**
     * Creates account
     * @param customerId
     * @param country
     * @param currencies
     */
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


//        with each currency creates a base transaction for the Balance making the application logic simpler
        for(String currency : currencies){
            mapper.insertBalance(new Balance(accountId, currency, 0.0));
            createTransaction(accountId, 0.0, currency, "IN", "CREATED ACCOUNT");
        }
    }



}
