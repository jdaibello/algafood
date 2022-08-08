package com.algaworks.algafood.domain.service;

import java.util.Set;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Singular;

public interface EmailSendingService {

	void send(Message message);

	@Builder
	@Getter
	class Message {

		@Singular
		private Set<String> recipients;

		@NonNull
		private String subject;

		@NonNull
		private String body;
	}
}
