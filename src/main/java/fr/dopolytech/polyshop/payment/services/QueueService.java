package fr.dopolytech.polyshop.payment.services;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import fr.dopolytech.polyshop.payment.models.PolyshopEvent;

@Service
public class QueueService {
    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper mapper = new ObjectMapper();

    public QueueService(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendPaymentSuccess(PolyshopEvent event) throws JsonProcessingException {
        String message = this.stringify(event);
        rabbitTemplate.convertAndSend("paymentExchange", "payment.done.success", message);
    }

    public void sendPaymentFailed(PolyshopEvent event) throws JsonProcessingException {
        String message = this.stringify(event);
        rabbitTemplate.convertAndSend("paymentExchange", "payment.done.failed", message);
    }

    public String stringify(PolyshopEvent data) throws JsonProcessingException {
        return mapper.writeValueAsString(data);
    }

    public PolyshopEvent parse(String data) throws JsonProcessingException {
        return mapper.readValue(data, PolyshopEvent.class);
    }
}
