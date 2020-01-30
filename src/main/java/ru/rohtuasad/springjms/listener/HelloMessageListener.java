package ru.rohtuasad.springjms.listener;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import ru.rohtuasad.springjms.config.JmsConfig;
import ru.rohtuasad.springjms.model.HelloWorldMessage;

import javax.jms.Message;

@Component
public class HelloMessageListener {

    @JmsListener(destination = JmsConfig.MY_QUEUE)
    public void listen(@Payload HelloWorldMessage helloWorldMessage, MessageHeaders headers,
                       Message message) {
        System.out.println("I got message");
        System.out.println(helloWorldMessage);
    }
}
