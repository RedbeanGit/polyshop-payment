package fr.dopolytech.polyshop.payment.services;

import java.util.Random;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;

import fr.dopolytech.polyshop.payment.models.PolyshopEvent;

@Service
public class ShippingService {
    private final QueueService queueService;

    public ShippingService(QueueService queueService) {
        this.queueService = queueService;
    }

    @RabbitListener(queues = "startPaymentQueue")
    public void onInventoryUpdateSuccess(String message) {
        try {
            PolyshopEvent event = this.queueService.parse(message);

            // This is fake so we randomly send a success or a failure
            Random random = new Random();

            if (random.nextBoolean() || random.nextBoolean()) {
                this.queueService.sendPaymentSuccess(event);
            } else {
                this.queueService.sendPaymentFailed(event);
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
