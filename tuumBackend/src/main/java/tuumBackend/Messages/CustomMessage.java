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

    public CustomMessage(Transaction transaction) {
        this.transaction = transaction;
        this.message = UUID.randomUUID().toString();
        this.messageDate = new Date().getTime();
        //this.asString = transaction.toString() + ",messageId="+message+",date="+messageDate.getTime();
    }

    public CustomMessage(Account account, ArrayList<String> currencies) {
        this.account = account;
        this.message = UUID.randomUUID().toString();
        this.messageDate = new Date().getTime();
        this.currencies = currencies;
        //this.asString = account.toString() + ",messageId="+message+",date="+messageDate.getTime();
    }

//    @Override
//    public String toString() {
//        return asString;
//    }
}
