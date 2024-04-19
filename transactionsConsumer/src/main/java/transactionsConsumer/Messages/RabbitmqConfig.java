package transactionsConsumer.Messages;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitmqConfig {
    public final static String TRANSACTION_QUEUE = "transaction_queue";
    public final static String ACCOUNT_QUEUE = "account_queue";
    public final static String EXCHANGE = "exchange";
    public final static String TRANSACTION_ROUTING = "transactionRouting";
    public final static String ACCOUNT_ROUTING = "accountRouting";

    @Bean(name="transactionQueue")
    public Queue transactionQueue(){
        return new Queue(TRANSACTION_QUEUE);
    }

    @Bean(name="accountQueue")
    public Queue accountQueue(){
        return new Queue(ACCOUNT_QUEUE);
    }

    @Bean
    public TopicExchange exchange(){
        return new TopicExchange(EXCHANGE);
    }

    @Bean
    public Binding transactionBinding(@Qualifier("transactionQueue") Queue queue, TopicExchange exchange){
        return BindingBuilder
                .bind(queue)
                .to(exchange)
                .with(TRANSACTION_ROUTING);
    }


    @Bean
    public Binding accountBinding(@Qualifier("accountQueue") Queue queue, TopicExchange exchange){
        return BindingBuilder
                .bind(queue)
                .to(exchange)
                .with(ACCOUNT_ROUTING);
    }

    @Bean
    public Jackson2JsonMessageConverter messageConverter(){
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public AmqpTemplate template(ConnectionFactory connectionFactory){
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(messageConverter());
        return template;
    }


}
