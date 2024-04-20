package accountConsumer.Messages;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import accountConsumer.Model.Account;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomMessage {
    private String customerId;
    private String country;
    private String message;
    private Long messageDate;
    private ArrayList<String> currencies;

    public CustomMessage(String customerId, String country, ArrayList<String> currencies) {
        this.customerId = customerId;
        this.country = country;
        this.message = UUID.randomUUID().toString();
        this.messageDate = new Date().getTime();
        this.currencies = currencies;
    }

}