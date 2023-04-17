package com.encore.playground.global.service;

import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class EmailServiceImpl implements EmailService {
    @Autowired
    JavaMailSender emailSender;

    public static final String ePw = createKey();

    private MimeMessage createMessage(String to) throws Exception {
        System.out.println("보내는 대상 : " + to);
        System.out.println("인증 번호 : " + ePw);
        MimeMessage message = emailSender.createMimeMessage();

        String mesg = "";
        mesg+= "<div style='margin:20px;'>";
        mesg+= "<h1> 안녕하세요 플레이그라운드 입니다. </h1>";
        mesg+= "<br>";
        mesg+= "<p>아래 코드를 복사해 입력해주세요<p>";
        mesg+= "<br>";
        mesg+= "<p>감사합니다.<p>";
        mesg+= "<br>";
        mesg+= "<div align='center' style='border:1px solid black; font-family:verdana';>";
        mesg+= "<h3 style='color:blue;'>비밀번호 찾기 인증 코드입니다.</h3>";
        mesg+= "<div style='font-size:130%'>";
        mesg+= "CODE : <strong>";
        mesg+= ePw+"</strong><div><br/> ";
        mesg+= "</div>";
        message.setText(mesg, "utf-8", "html");//내용
        message.setFrom(new InternetAddress("jhnam.aws@gmail.com","playground"));//보내는 사람

        return message;
    }

    public static String createKey() {
        StringBuffer key = new StringBuffer();
        Random rnd = new Random();

        for (int i = 0; i < 8; i++) {
            int index = rnd.nextInt(3);

            switch (index) {
                case 0:
                    // a ~ z
                    key.append((char) ((int) (rnd.nextInt(26)) + 97));
                    break;
                case 1:
                    // A ~ Z
                    key.append((char) ((int) (rnd.nextInt(26)) + 65));
                    break;
                case 2:
                    // 0 ~ 9
                    key.append((rnd.nextInt(10)));
                    break;
            }
        }
        return key.toString();
    }

    @Override
    public String sendSimpleMessage(String to) throws Exception {
        MimeMessage message = createMessage(to);

        try {
            emailSender.send(message);
        } catch (MailException es) {
            es.printStackTrace();
            throw new IllegalArgumentException();
        }

        return ePw;
    }
}
