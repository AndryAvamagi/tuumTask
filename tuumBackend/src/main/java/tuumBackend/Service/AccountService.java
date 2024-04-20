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




}
