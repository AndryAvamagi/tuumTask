package accountConsumer.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Transaction {
    private String transactionId;
    private String accountId;
    private double amount;
    private String currency;
    private String direction;
    private String description;

    @Override
    public String toString() {
        return
                "transactionId=" + transactionId +
                ",accountId=" + accountId +
                ",amount=" + amount +
                ",currency=" + currency +
                ",direction=" + direction +
                ",description=" + description;
    }
}
