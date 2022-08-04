package com.algaworks.algafood.infrastructure.service.storage;

import java.nio.file.Files;
import java.nio.file.Path;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import com.algaworks.algafood.domain.service.PhotoStorageService;

@Service
public class LocalPhotoStorageService implements PhotoStorageService {

	@Value("${algafood.storage.local.photos-dir}")
	private Path photosDirectory;

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
		return photosDirectory.resolve(Path.of(fileName));
	}
}
