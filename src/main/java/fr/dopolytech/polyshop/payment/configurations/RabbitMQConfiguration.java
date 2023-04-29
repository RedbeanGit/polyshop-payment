package fr.dopolytech.polyshop.payment.configurations;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfiguration {
    @Bean
    public Queue startPaymentQueue() {
        return new Queue("startPaymentQueue", true);
    }

    @Bean
    public TopicExchange paymentExchange() {
        return new TopicExchange("inventoryExchange");
    }

    @Bean
    public Binding paymentDoneBinding(Queue startPaymentQueue, TopicExchange paymentExchange) {
        return BindingBuilder.bind(startPaymentQueue).to(paymentExchange).with("inventory.update.success");
    }
}
