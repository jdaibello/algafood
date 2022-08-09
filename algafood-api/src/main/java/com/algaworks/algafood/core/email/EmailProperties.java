package com.algaworks.algafood.core.email;

import javax.validation.constraints.NotNull;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import lombok.Getter;
import lombok.Setter;

@Validated
@Getter
@Setter
@Component
@ConfigurationProperties("algafood.email")
public class EmailProperties {
	private Implementation impl = Implementation.FAKE;
	private Sandbox sandbox = new Sandbox();

	@NotNull
	private String sender;

	@Getter
	@Setter
	public class Sandbox {
		private String recipient;
	}

	public enum Implementation {
		SMTP, FAKE, SANDBOX
	}
}
