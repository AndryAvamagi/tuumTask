package tuumBackend.Messages;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tuumBackend.Model.Account;
import tuumBackend.Model.Transaction;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomMessage {
    private Transaction transaction;
    private Account account;
    private String message;
    private Long messageDate;
    private String asString;
    private ArrayList<String> currencies;

    private String customerId;
    private String Country;

    public CustomMessage(Transaction transaction) {
        this.transaction = transaction;
        this.message = UUID.randomUUID().toString();
        this.messageDate = new Date().getTime();
    }

    public CustomMessage(String customerId, String country, ArrayList<String> currencies) {
        this.customerId = customerId;
        this.Country = country;
        this.currencies = currencies;
        this.message = UUID.randomUUID().toString();
        this.messageDate = new Date().getTime();

    }

}
