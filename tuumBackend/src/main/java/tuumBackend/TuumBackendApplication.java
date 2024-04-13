package tuumBackend;

import org.apache.ibatis.type.MappedTypes;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tuumBackend.Model.Transaction;

@MappedTypes(Transaction.class)
@MapperScan("tuumBackend.Mapper")
@SpringBootApplication
public class TuumBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(TuumBackendApplication.class, args);
	}

}
