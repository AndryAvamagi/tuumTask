package accountConsumer.Messages;

import accountConsumer.Model.Account;
import accountConsumer.Service.AccountService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import accountConsumer.Model.Account;



@Component
public class MessageListener {

    @Autowired
    private AccountService accountService;

    @RabbitListener(queues = RabbitmqConfig.ACCOUNT_QUEUE)
    public void listener(CustomMessage message){
        Account account = message.getAccount();
        System.out.println(account + " " + message.getCurrencies());
        accountService.createAccount(
                account.getCustomerId(),
                account.getCountry(),
                message.getCurrencies()
        );
    }
}
