package com.algaworks.algafood.core.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.algaworks.algafood.domain.service.EmailSendingService;
import com.algaworks.algafood.infrastructure.service.email.FakeEmailSendingService;
import com.algaworks.algafood.infrastructure.service.email.SmtpEmailSendingService;

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
		default:
			return null;
		}
	}
}
