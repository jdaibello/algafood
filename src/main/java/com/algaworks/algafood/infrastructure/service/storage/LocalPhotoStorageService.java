package com.algaworks.algafood.infrastructure.service.storage;

import java.io.IOException;
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
		} catch (IOException e) {
			throw new StorageException("Não foi possível armazenar o arquivo", e);
		}
	}

	private Path getFilePath(String fileName) {
		return photosDirectory.resolve(Path.of(fileName));
	}
}
