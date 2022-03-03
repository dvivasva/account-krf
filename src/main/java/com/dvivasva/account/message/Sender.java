package com.dvivasva.account.message;

import com.dvivasva.account.dto.AccountDto;
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
    private final KafkaTemplate<String, AccountDto> kafkaTemplate;

    public void responseAccountOrigin(AccountDto value) {
        kafkaTemplate.send(Topic.RESPONSE_ACCOUNT_ORIGIN,value);
        logger.info("Messages successfully pushed on topic: " + Topic.RESPONSE_ACCOUNT_ORIGIN);
    }
    public void responseAccountToWallet(AccountDto value) {
        kafkaTemplate.send(Topic.RESPONSE_ACCOUNT_TO_WALLET,value);
        logger.info("Messages successfully pushed on topic: " + Topic.RESPONSE_ACCOUNT_TO_WALLET);
    }
}


