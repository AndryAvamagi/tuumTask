package transactionsConsumer.Messages;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import transactionsConsumer.Model.Transaction;
import transactionsConsumer.Service.TransactionService;


@Component
public class MessageListener {

    @Autowired
    private TransactionService transactionService;

    @RabbitListener(queues = RabbitmqConfig.TRANSACTION_QUEUE)
    public void listener(CustomMessage message){
        Transaction transaction = message.getTransaction();
        transactionService.createTransaction(
                transaction.getAccountId(),
                transaction.getAmount(),
                transaction.getCurrency(),
                transaction.getDirection(),
                transaction.getDescription()
        );
    }
}
