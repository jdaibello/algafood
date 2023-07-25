package com.algaworks.algafood.core.email;

import com.algaworks.algafood.domain.service.EmailSendingService;
import com.algaworks.algafood.infrastructure.service.email.FakeEmailSendingService;
import com.algaworks.algafood.infrastructure.service.email.SandboxEmailSendingService;
import com.algaworks.algafood.infrastructure.service.email.SmtpEmailSendingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EmailConfig {

    @Autowired
    private EmailProperties emailProperties;

    @Bean
    public EmailSendingService emailSendingService() {
        switch (emailProperties.getImpl()) {
            case FAKE:
                return new FakeEmailSendingService();
            case SMTP:
                return new SmtpEmailSendingService();
            case SANDBOX:
                return new SandboxEmailSendingService();
            default:
                return new FakeEmailSendingService();
        }
    }
}
