package com.dvivasva.account.message;

import com.dvivasva.account.model.RequestBuyBootCoin;
import com.dvivasva.account.utils.Topic;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class Sender {


    private static final Logger logger = LoggerFactory.getLogger(Sender.class);

    private final KafkaTemplate<String, RequestBuyBootCoin> requestBuyBootCoinKafkaTemplateKafka;
    public void responseRequestBuyBootCoinToPayment(RequestBuyBootCoin value){
        requestBuyBootCoinKafkaTemplateKafka.send(Topic.RESPONSE_REQUEST_BUY_BOOT_COIN_PAYMENT,value);
        logger.info("Messages successfully pushed on topic: " + Topic.RESPONSE_REQUEST_BUY_BOOT_COIN_PAYMENT);
    }

    public void responseRequestBuyBootCoinToWallet(RequestBuyBootCoin value){
        requestBuyBootCoinKafkaTemplateKafka.send(Topic.RESPONSE_REQUEST_BUY_BOOT_COIN_WALLET,value);
        logger.info("Messages successfully pushed on topic: " + Topic.RESPONSE_REQUEST_BUY_BOOT_COIN_WALLET);
    }
}



