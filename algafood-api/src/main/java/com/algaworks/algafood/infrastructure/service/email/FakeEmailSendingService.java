package com.algaworks.algafood.infrastructure.service.email;

import com.algaworks.algafood.domain.service.EmailSendingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
public class FakeEmailSendingService implements EmailSendingService {

    @Autowired
    private EmailTemplateProcessor emailTemplateProcessor;

    @Override
    public void send(Message message) {
        String body = emailTemplateProcessor.processTemplate(message);
        log.info("[FAKE E-MAIL] To: {}\n{}", message.getRecipients(), body);
    }
}
