package tuumBackend.Mapper;

import org.apache.ibatis.annotations.Select;
import tuumBackend.Model.Customer;
import tuumBackend.Model.Transaction;

import java.util.ArrayList;

@org.apache.ibatis.annotations.Mapper
public interface Mapper {

    @Select("select * from transactions")
    ArrayList<Transaction> findAllTransactions();

    @Select("select * from customers")
    ArrayList<Customer> findAllCustomers();

}
