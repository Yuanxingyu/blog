package com.stary.activemq;

import com.stary.mail.MailSender;
import lombok.extern.slf4j.Slf4j;
import org.apache.activemq.command.ActiveMQTextMessage;
import org.springframework.jms.listener.SessionAwareMessageListener;

import javax.annotation.Resource;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;


@Slf4j
public class MqListener implements SessionAwareMessageListener<Message>{
    @Resource
    private MailSender mailSender;

    @Override
    public synchronized void onMessage(Message message, Session session) throws JMSException {
        ActiveMQTextMessage msg = (ActiveMQTextMessage) message;
        String username = msg.getText();
        log.info("new user:{}", username);
        //mailSender.send(username);
    }
}