package tuumBackend.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Transaction {
    private String transactionId;
    private String accountId;
    private double amount;
    private String currency;
    private String direction;
    private String description;

    public Transaction(String description) {
        this.description = description;
    }
}
