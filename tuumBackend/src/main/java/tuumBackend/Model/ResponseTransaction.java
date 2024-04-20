package tuumBackend.Model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseTransaction {
    private String accountId;
    private double amount;
    private String currency;
    private String direction;
    private String description;
    private double newAmount;

    public ResponseTransaction(String description) {
        this.description = description;
    }
}
