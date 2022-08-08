package com.algaworks.algafood.infrastructure.service.email;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import com.algaworks.algafood.core.email.EmailProperties;
import com.algaworks.algafood.domain.service.EmailSendingService;

import freemarker.template.Configuration;
import freemarker.template.Template;

@Service
public class SmtpEmailSendingService implements EmailSendingService {

	@Autowired
	private JavaMailSender mailSender;

	@Autowired
	private EmailProperties emailProperties;

	@Autowired
	private Configuration freemarkerConfig;

	@Override
	public void send(Message message) {
		try {
			String body = proccessTemplate(message);

			MimeMessage mimeMessage = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "UTF-8");

			helper.setFrom(emailProperties.getSender());
			helper.setTo(message.getRecipients().toArray(new String[0]));
			helper.setSubject(message.getSubject());
			helper.setText(body, true);

			mailSender.send(mimeMessage);
		} catch (Exception e) {
			throw new EmailException("Não foi possível enviar o e-mail", e);
		}
	}

	private String proccessTemplate(Message message) {
		try {
			Template template = freemarkerConfig.getTemplate(message.getBody());

			return FreeMarkerTemplateUtils.processTemplateIntoString(template, message.getVariables());
		} catch (Exception e) {
			throw new EmailException("Não foi possível montar o template do e-mail", e);
		}
	}
}
