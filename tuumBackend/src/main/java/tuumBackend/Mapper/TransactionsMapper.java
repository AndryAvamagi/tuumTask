package tuumBackend.Mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import tuumBackend.Model.Transaction;

import java.util.ArrayList;

@Mapper
public interface TransactionsMapper {

    @Select("select * from transactions")
    ArrayList<Transaction> findAll();

}
