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
import reactor.core.publisher.Mono;

import java.io.IOException;

@RequiredArgsConstructor
@Service
public class Receiver {

    private static final Logger logger = LoggerFactory.getLogger(Receiver.class);
    private final AccountService accountService;
    private final Sender sender;

    /**
     * response to payment
     *
     * @param param .
     */
    @KafkaListener(topics = Topic.REQUEST_BUY, groupId = "group_id_account")
    public void consumeFormGateway(String param) {
        logger.info("Has been published an insert payment from service gateway-krf : " + param);
        var value = new RequestBuyBootCoin();
        try {
            value = JsonUtils.convertFromJsonToObject(param, RequestBuyBootCoin.class);
            if (value.getPayMode().equals("Transferencia")) {
                logger.info("search account");
                RequestBuyBootCoin finalValue = value;
                var find = accountService.findByNumberAccount(value.getNumber());
                find.switchIfEmpty(Mono.error(new ClassNotFoundException("not exist account")))
                        .doOnNext(p -> {
                            logger.info("print amount :" +finalValue.getAmount());
                            p.setAvailableBalance(p.getAvailableBalance() + finalValue.getAmount());
                            accountService.update(Mono.just(p), p.getId())
                                   .doOnNext(a -> logger.info("update success" + a)).subscribe();

                            sender.responseRequestBuyBootCoinToPayment(finalValue);

                            logger.info("sending RequestBuyBootCoin to payment -->");
                        }).subscribe();


            } else {
                logger.info("do nothing");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * response to wallet.
     *
     * @param param .
     */
    @KafkaListener(topics = Topic.FIND_ACCOUNT_ORIGIN, groupId = "group_id_account")
    public void consumeFromWallet(String param) {
        logger.info("Has been published account number from service wallet-krf : " + param);
        var value = new RequestBuyBootCoin();
        try {
            value = JsonUtils.convertFromJsonToObject(param, RequestBuyBootCoin.class);
                RequestBuyBootCoin finalValue = value;
                var find = accountService.findByNumberAccount(value.getNumber());
                find.switchIfEmpty(Mono.error(new ClassNotFoundException("not exist account")))
                        .doOnNext(p -> {
                            p.setAvailableBalance(p.getAvailableBalance() + finalValue.getAmount());
                            accountService.update(Mono.just(p), p.getId())
                                    .doOnNext(a -> logger.info("update success" + a)).subscribe();

                            sender.responseRequestBuyBootCoinToWallet(finalValue);
                            logger.info("sending RequestBuyBootCoin to wallet -->");
                        }).subscribe();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
