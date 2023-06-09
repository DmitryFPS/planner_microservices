package ru.orlov.micro.planner.plannerusers.mq.func;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Sinks;

//@Getter
//@RequiredArgsConstructor
//@Service
public class MessageFuncAction {
    // Канал с сообщениями
//    private final MessageFunc messageFunc;

    // Будем вызывать данный метод в контроллере что бы в него положить id Юзера что бы сообщением отправить в innerBus и потом в Supplier
//    public void sendNewUserMessage(final Long id) {
//        messageFunc.getInnerBus().emitNext(MessageBuilder.withPayload(id).build(), Sinks.EmitFailureHandler.FAIL_FAST);
//    }
}