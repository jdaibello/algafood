package com.algaworks.algafood.infrastructure.service.storage;

import com.algaworks.algafood.core.StorageProperties;
import com.algaworks.algafood.domain.service.PhotoStorageService;
import org.springframework.util.FileCopyUtils;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

// @Service
public class LocalPhotoStorageService implements PhotoStorageService {

	private StorageProperties storageProperties;

	@Override
	public InputStream recover(String fileName) {
		Path filePath = getFilePath(fileName);

		try {
			return Files.newInputStream(filePath);
		} catch (Exception e) {
			throw new StorageException("Não foi possível recuperar o arquivo", e);
		}
	}

	@Override
	public void store(NewPhoto newPhoto) {
		try {
			Path filePath = getFilePath(newPhoto.getFileName());
			FileCopyUtils.copy(newPhoto.getInputStream(), Files.newOutputStream(filePath));
		} catch (Exception e) {
			throw new StorageException("Não foi possível armazenar o arquivo", e);
		}
	}

	@Override
	public void remove(String fileName) {
		try {
			Path filePath = getFilePath(fileName);
			Files.deleteIfExists(filePath);
		} catch (Exception e) {
			throw new StorageException("Não foi possível excluir o arquivo", e);
		}
	}

	private Path getFilePath(String fileName) {
		return storageProperties.getLocal().getPhotosDirectory().resolve(Path.of(fileName));
	}
}
