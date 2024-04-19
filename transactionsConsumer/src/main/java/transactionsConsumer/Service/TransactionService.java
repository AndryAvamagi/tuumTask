package transactionsConsumer.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import transactionsConsumer.Messages.MessageListener;
import transactionsConsumer.Model.Balance;
import transactionsConsumer.Model.Transaction;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
public class TransactionService {

    @Autowired
    private transactionsConsumer.Mapper.Mapper mapper;
    private static final List<String> ALLOWED_CURRENCIES = Arrays.asList("EUR","SEK","GBP", "USD");

    public void createTransaction(String accountId, double amount, String currency, String direction, String description){
        if(amount < 0.0) return;
        if(!direction.equals("IN") && !direction.equals("OUT")) return;
        if(direction.equals("OUT")) amount = amount * -1;

        Transaction transaction = new Transaction(UUID.randomUUID().toString(), accountId, amount, currency, direction, description);

        Balance balance = mapper.getBalance(accountId, currency);

        mapper.updateTotalAmount(amount, balance);
        mapper.insertTransaction(transaction);
    }
}
