package com.algaworks.algafood.infrastructure.service.email;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FakeEmailSendingService extends SmtpEmailSendingService {

    @Override
    public void send(Message message) {
        String body = proccessTemplate(message);
        log.info("[FAKE E-MAIL] To: {}\n{}", message.getRecipients(), body);
    }
}
