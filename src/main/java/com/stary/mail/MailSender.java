package com.stary.mail;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;


@Component
public class MailSender {
    @Resource(name = "javaMailSender")
    private JavaMailSender sender;

    public void send(String content) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("673894849@qq.com");
        message.setTo("3303521941@qq.com");
        message.setSubject("新用户注册："+content);
        message.setText("欢迎：" + content);
        //发送邮件
        //sender.send(message);
    }
}
