package tuumBackend.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Transaction {
    private String accountId;
    private String transactionId;
    private double amount;
    private Timestamp actionTime;
}
