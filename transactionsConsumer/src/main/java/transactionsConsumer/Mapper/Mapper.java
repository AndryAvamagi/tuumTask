package transactionsConsumer.Mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import transactionsConsumer.Model.Account;
import transactionsConsumer.Model.Balance;
import transactionsConsumer.Model.Transaction;

import java.util.ArrayList;

@org.apache.ibatis.annotations.Mapper
public interface Mapper {

    @Select("select * from transactions")
    ArrayList<Transaction> findAllTransactions();

    @Select("select * from balances")
    ArrayList<Balance> findAllBalances();

    @Select("select * from accounts")
    ArrayList<Account> findAllAccounts();

    @Insert("INSERT INTO transactions(transactionid, accountid, amount, currency, direction, description) VALUES (#{transactionId}, #{accountId}, #{amount}, #{currency} ,#{direction}, #{description})")
    void insertTransaction(Transaction transaction);

    @Insert("INSERT INTO accounts(accountid, customerid, country) values (#{accountId}, #{customerId}, #{country})")
    void insertAccount(Account account);

    @Insert("INSERT INTO Balances(accountid, currency, totalAmount) values (#{accountId}, #{currency}, #{totalAmount})")
    void insertBalance(Balance balance);

    @Update("Update balances set totalamount = #{balance.totalAmount} + #{amount} where accountid = #{balance.accountId} and currency = #{balance.currency}")
    void updateTotalAmount(double amount, Balance balance);

    @Select("select distinct currency from transactions where accountid = #{AccountId}")
    ArrayList<String> getAllUsedCurrenciesByAccountId(String AccountId);

    @Select("SELECT sum(amount) from transactions where accountid = #{AccountId} and currency = #{currency} group by currency")
    Double getBalanceOfAccount(String AccountId, String currency);

    @Select("Select * from balances where accountId = #{accountId} and currency = #{currency} limit 1")
    Balance getBalance(String accountId, String currency);

//    @Select("select * from customers")
//    ArrayList<Customer> findAllCustomers();
//
//    @Select("select * from currencies where accountid = #{AccountId}")
//    ArrayList<Balance> findAllBalancesOfAccount(String AccountId);

}
