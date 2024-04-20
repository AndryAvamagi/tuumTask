package transactionsConsumer.Messages;


import lombok.Data;
import lombok.NoArgsConstructor;
import transactionsConsumer.Model.Transaction;


@Data
@NoArgsConstructor
public class CustomMessage {
    private Transaction transaction;
    private String message;
    private Long messageDate;

    public CustomMessage(Transaction transaction, String message, Long messageDate) {
        this.transaction = transaction;
        this.message = message;
        this.messageDate = messageDate;
    }
}
