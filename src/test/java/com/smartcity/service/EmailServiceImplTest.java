package com.smartcity.service;

import com.smartcity.config.ProfileConfig;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;

import java.io.File;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@WebAppConfiguration
@ContextConfiguration(classes = {ProfileConfig.class})
public class EmailServiceImplTest {

    @Autowired
    private EmailService emailSender;

    @Test
    public void testSendEmailWithAttachment() {
        File[] attachments = new File[]{new File("README.md")};
        emailSender.sendMessageWithAttachment("Subject",
                "Hello Dear, Please open the attachments.",
                attachments,
                "test.mail.client008@gmail.com"
        );
    }

    @Test
    public void testSendEmail() {
        emailSender.sendSimpleMessage("Subject",
                "Hello Dear, How are you ?",
                "test.mail.client008@gmail.com"
        );
    }

}
