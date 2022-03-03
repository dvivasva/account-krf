package com.dvivasva.account.message;

import com.dvivasva.account.model.RequestBuyBootCoin;
import com.dvivasva.account.service.AccountService;
import com.dvivasva.account.utils.JsonUtils;
import com.dvivasva.account.utils.Topic;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.io.IOException;

@RequiredArgsConstructor
@Service
public class Receiver {

    private static final Logger logger = LoggerFactory.getLogger(Receiver.class);
    private final AccountService accountService;
    private final Sender sender;

    /**
     * response to payment
     * @param param .
     */
    @KafkaListener(topics = Topic.REQUEST_BUY, groupId = "group_id_account")
    public void consumeFormGateway(String param) {
        logger.info("Has been published an insert payment from service gateway-mobile : " + param);
        var value = new RequestBuyBootCoin();
        try {
            value = JsonUtils.convertFromJsonToObject(param, RequestBuyBootCoin.class);
            if(value.getPayMode().equals("Transferencia")){
                logger.info("search account");
                var find = accountService.findByNumberAccount(value.getNumber());
                find.doOnNext(sender::responseAccountOrigin).subscribe();
                logger.info("send details account origin to payment -->");

            }else {
                logger.info("do nothing");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * response to wallet
     * @param param .
     */
    @KafkaListener(topics = Topic.FIND_ACCOUNT_ORIGIN, groupId = "group_id_account")
    public void consumeFindAccountOrigin(String param) {
        logger.info("Has been published account number from service wallet-krf : " + param);
        String newNumberAccount = JsonUtils.removeFirstAndLast(param);
        var find = accountService.findByNumberAccount(newNumberAccount);
        find.doOnNext(sender::responseAccountToWallet).subscribe();
        logger.info("send details account origin to wallet -->");
    }


}
