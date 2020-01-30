package ru.rohtuasad.springjms.sender;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.rohtuasad.springjms.config.JmsConfig;
import ru.rohtuasad.springjms.model.HelloWorldMessage;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import java.util.UUID;

@RequiredArgsConstructor
@Component
public class HelloSender {

    private final JmsTemplate jmsTemplate;
    private final ObjectMapper objectMapper;

    @Scheduled(fixedRate = 2000)
    public void SendMessage() {
        HelloWorldMessage message = HelloWorldMessage
                .builder()
                .id(UUID.randomUUID())
                .message("hello world")
                .build();

        jmsTemplate.convertAndSend(JmsConfig.MY_QUEUE, message);
    }

    @Scheduled(fixedRate = 2000)
    public void SendAndReceiveMessage() throws JMSException {
        HelloWorldMessage message = HelloWorldMessage
                .builder()
                .id(UUID.randomUUID())
                .message("Hello ")
                .build();

        Message receivedMessage = jmsTemplate.sendAndReceive(JmsConfig.MY_SEND_RCV_QUEUE, new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                Message helloMessage = null;
                try {
                    helloMessage = session.createTextMessage(objectMapper.writeValueAsString(message));
                    helloMessage.setStringProperty("_type", "ru.rohtuasad.springjms.model.HelloWorldMessage");
                    System.out.println("Sending hello");
                    return helloMessage;
                } catch (JsonProcessingException e) {
                    throw new JMSException(e.getMessage());
                }
            }
        });
        System.out.println(receivedMessage.getBody(String.class));
    }
}
