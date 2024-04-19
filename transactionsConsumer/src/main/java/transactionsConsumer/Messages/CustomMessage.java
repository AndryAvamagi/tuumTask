package transactionsConsumer.Messages;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import transactionsConsumer.Model.Account;
import transactionsConsumer.Model.Transaction;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomMessage {
    private Transaction transaction;
    private Account account;
    private String message;
    private Date messageDate;
    private String asString;

    public CustomMessage(Transaction transaction, String message, Date messageDate) {
        this.transaction = transaction;
        this.message = message;
        this.messageDate = messageDate;
        this.asString = transaction.toString() + ",messageId="+message+",date="+messageDate.getTime();
    }

    public CustomMessage(Account account, String message, Date messageDate) {
        this.account = account;
        this.message = message;
        this.messageDate = messageDate;
        this.asString = account.toString() + ",messageId="+message+",date="+messageDate.getTime();
    }

    @Override
    public String toString() {
        return asString;
    }
}
