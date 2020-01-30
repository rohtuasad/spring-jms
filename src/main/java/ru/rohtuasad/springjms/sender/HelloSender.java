package ru.rohtuasad.springjms.sender;

import lombok.RequiredArgsConstructor;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.rohtuasad.springjms.config.JmsConfig;
import ru.rohtuasad.springjms.model.HelloWorldMessage;

import java.util.UUID;

@RequiredArgsConstructor
@Component
public class HelloSender {

    private final JmsTemplate jmsTemplate;

    @Scheduled(fixedRate = 2000)
    public void SendMessage() {
        HelloWorldMessage message = HelloWorldMessage
                .builder()
                .id(UUID.randomUUID())
                .message("hello world")
                .build();

        jmsTemplate.convertAndSend(JmsConfig.MY_QUEUE, message);
    }
}
