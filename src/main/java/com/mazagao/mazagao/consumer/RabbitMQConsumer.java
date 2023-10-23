package com.mazagao.mazagao.consumer;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mazagao.mazagao.services.UserServices;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.amqp.core.AcknowledgeMode;

import java.util.logging.Logger;

@Component
public class RabbitMQConsumer {

    @Autowired
    private UserServices userServices;

    Logger logger = Logger.getLogger(this.getClass().getName());

    @RabbitListener(queues = {"${rabbitmq.queue.name}"}, ackMode = "AUTO")
    public void consume(String message){

        var jsonObj = getJsonObject(message);

        if(jsonObj == null){
            logger.info("A invalid Json was received");
            return;
        }

        try{

            var murder = jsonObj.get("killer").asText();
            var victim = jsonObj.get("victim").asText();
            userServices.setMurderScore(murder, victim);

        } catch (Exception e){
            logger.info("Invalid message, " + e.getMessage() + " on : " + jsonObj);
        }

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