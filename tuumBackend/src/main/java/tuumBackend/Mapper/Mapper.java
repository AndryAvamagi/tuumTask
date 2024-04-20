package tuumBackend.Mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import tuumBackend.Model.Account;
import tuumBackend.Model.Balance;
import tuumBackend.Model.Transaction;

import java.util.ArrayList;
import java.util.Arrays;

@org.apache.ibatis.annotations.Mapper
public interface Mapper {


//    ---------------------------------------------------------------ALL DATA --------------------------------------------------------------------------------
    @Select("select * from transactions")
    ArrayList<Transaction> findAllTransactions();

    @Select("select * from balances")
    ArrayList<Balance> findAllBalances();

    @Select("select * from accounts")
    ArrayList<Account> findAllAccounts();

//    ---------------------------------------------------------------SPECIFIED---------------------------------------------------------------------------------------

    @Select("select * from balances where accountId = #{accountId}")
    ArrayList<Balance> findAllBalancesById(String accountId);

    @Select("select * from accounts where accountId = #{accountId}")
    Account findAllAccountById(String accountId);


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
