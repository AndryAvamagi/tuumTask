package transactionsConsumer.Mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import transactionsConsumer.Model.Balance;
import transactionsConsumer.Model.Transaction;

@org.apache.ibatis.annotations.Mapper
public interface Mapper {

    @Insert("INSERT INTO transactions(transactionid, accountid, amount, currency, direction, description) VALUES (#{transactionId}, #{accountId}, #{amount}, #{currency} ,#{direction}, #{description})")
    void insertTransaction(Transaction transaction);

    @Update("Update balances set totalamount = #{balance.totalAmount} + #{amount} where accountid = #{balance.accountId} and currency = #{balance.currency}")
    void updateTotalAmount(double amount, Balance balance);

    @Select("Select * from balances where accountId = #{accountId} and currency = #{currency} limit 1")
    Balance getBalance(String accountId, String currency);
}
