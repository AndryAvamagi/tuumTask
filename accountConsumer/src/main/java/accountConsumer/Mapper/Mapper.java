package accountConsumer.Mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import accountConsumer.Model.Account;
import accountConsumer.Model.Balance;
import accountConsumer.Model.Transaction;

@org.apache.ibatis.annotations.Mapper
public interface  Mapper {

    @Insert("INSERT INTO transactions(transactionid, accountid, amount, currency, direction, description) VALUES (#{transactionId}, #{accountId}, #{amount}, #{currency} ,#{direction}, #{description})")
    void insertTransaction(Transaction transaction);

    @Insert("INSERT INTO accounts(accountid, customerid, country) values (#{accountId}, #{customerId}, #{country})")
    void insertAccount(Account account);

    @Insert("INSERT INTO Balances(accountid, currency, totalAmount) values (#{accountId}, #{currency}, #{totalAmount})")
    void insertBalance(Balance balance);

    @Update("Update balances set totalamount = #{balance.totalAmount} + #{amount} where accountid = #{balance.accountId} and currency = #{balance.currency}")
    void updateTotalAmount(double amount, Balance balance);

    @Select("Select * from balances where accountId = #{accountId} and currency = #{currency} limit 1")
    Balance getBalance(String accountId, String currency);

}
