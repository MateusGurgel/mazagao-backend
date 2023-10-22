package com.mazagao.mazagao.consumer;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Component
public class RabbitMQConsumer {

    Logger logger = Logger.getLogger(this.getClass().getName());

    @RabbitListener(queues = {"${rabbitmq.queue.name}"})
    public void consume(String message){

        var jsonObj = getJsonObject(message);

        if(jsonObj == null){
            logger.info("A invalid Json was received");
            return;
        }

        logger.info(String.format("Received message -> %s", jsonObj.get("type")));

    }

    public JsonNode getJsonObject(String jsonStr) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonObj = objectMapper.readTree(jsonStr);
            return jsonObj;
        } catch (Exception e) {
            return null;
        }
    }

}