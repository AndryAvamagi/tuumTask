package tuumBackend.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Account {
    private String accountId;
    private String customerId;
    private String country;
    private ArrayList<Balance> balances;


    public Account(String customerId, String country) {
        this.customerId = customerId;
        this.country = country;
    }
}
