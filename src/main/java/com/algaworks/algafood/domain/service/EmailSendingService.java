package com.algaworks.algafood.domain.service;

import lombok.Builder;
import lombok.Getter;

import java.util.Set;

public interface EmailSendingService {

    void send(Message message);

    @Builder
    @Getter
    class Message {
        private Set<String> recipients;
        private String subject;
        private String body;
    }
}
