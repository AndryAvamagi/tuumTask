package tuumBackend.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tuumBackend.Model.Balance;
import tuumBackend.Model.ResponseTransaction;
import tuumBackend.Model.Transaction;
import java.util.*;

@Service
public class AccountService {

    @Autowired
    private tuumBackend.Mapper.Mapper mapper;


    private final List<String> ALLOWED_CURRENCIES = Arrays.asList("EUR","SEK","GBP", "USD");

    public boolean currenciesValid(ArrayList<String> givenCurrencies){
        for (String currency : givenCurrencies){
            if(!ALLOWED_CURRENCIES.contains(currency)) return false;
        }
        return true;
    }


    public ResponseTransaction transactionValid(Transaction transaction){
        String accountId = transaction.getAccountId();
        double amount = transaction.getAmount();
        String currency = transaction.getCurrency();
        String description = transaction.getDescription();
        String direction = transaction.getDirection();

        if(mapper.findAllAccountById(accountId) == null) return new ResponseTransaction("NO SUCH ACCOUNTID");

        ArrayList<String> currencies = mapper.findAllUsedCurrenciesByAccountId(accountId);

        if(!currencies.contains(currency)) return new ResponseTransaction("NO SUCH BALANCE WITH SUCH CURRENCY");

        Balance balance = mapper.findBalance(accountId, currency);

        if(!direction.equals("IN") && !direction.equals("OUT")) return new ResponseTransaction("BAD DIRECTION");
        if(amount < 0) return new ResponseTransaction("BAD AMOUNT");
        if(description.isEmpty())return new ResponseTransaction("NO DESCRIPTION");
        if(balance.getTotalAmount() < amount && direction.equals("OUT")) return new ResponseTransaction("INSUFFICIENT FUNDS");

        double newAmount = direction.equals("IN") ? balance.getTotalAmount() + amount : balance.getTotalAmount() - amount;

        return new ResponseTransaction(accountId, amount, currency, description, direction, newAmount);
    }



}
