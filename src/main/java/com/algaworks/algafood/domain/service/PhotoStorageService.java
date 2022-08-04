package com.algaworks.algafood.domain.service;

import java.io.InputStream;
import java.util.UUID;

import lombok.Builder;
import lombok.Getter;

public interface PhotoStorageService {

	void store(NewPhoto newPhoto);

	void remove(String fileName);

	default void substitute(String oldFileName, NewPhoto newPhoto) {
		this.store(newPhoto);

		if (oldFileName != null) {
			this.remove(oldFileName);
		}
	}

	default String generateFileName(String originalName) {
		return UUID.randomUUID().toString() + "_" + originalName;
	}

	@Builder
	@Getter
	class NewPhoto {
		private String fileName;
		private InputStream inputStream;
	}
}
