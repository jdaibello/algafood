package com.algaworks.algafood.infrastructure.service.email;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.MimeMessageHelper;

import com.algaworks.algafood.core.email.EmailProperties;

public class SandboxEmailSendingService extends SmtpEmailSendingService {

	@Autowired
	private EmailProperties emailProperties;

	@Override
	protected MimeMessage createMimeMessage(Message message) throws MessagingException {
		MimeMessage mimeMessage = super.createMimeMessage(message);
		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
		helper.setTo(emailProperties.getSandbox().getRecipient());

		return mimeMessage;
	}
}
