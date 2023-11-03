package com.mazagao.mazagao.consumer;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mazagao.mazagao.services.UserServices;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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

            String type = jsonObj.get("type").asText();

            logger.info(type);

            if( "pvp".equals(type) ){
                var murder = jsonObj.get("killer").asText().toLowerCase();
                var victim = jsonObj.get("victim").asText().toLowerCase();
                userServices.setMurderScore(murder, victim);
            }
            else if( "mining".equals(type) ){
                var miner = jsonObj.get("miner").asText().toLowerCase();
                var ore = jsonObj.get("ore").asText();
                userServices.setMinerScore(miner, ore);
            }


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