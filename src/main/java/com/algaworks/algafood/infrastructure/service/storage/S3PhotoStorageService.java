package com.algaworks.algafood.infrastructure.service.storage;

import com.algaworks.algafood.core.storage.StorageProperties;
import com.algaworks.algafood.domain.service.PhotoStorageService;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;

@Service
public class S3PhotoStorageService implements PhotoStorageService {

    @Autowired
    private AmazonS3 amazonS3;

    @Autowired
    private StorageProperties storageProperties;

    @Override
    public InputStream recover(String fileName) {
        return null;
    }

    @Override
    public void store(NewPhoto newPhoto) {
        try {
            String filePath = getFilePath(newPhoto.getFileName());

            var objectMetadata = new ObjectMetadata();
            objectMetadata.setContentType(newPhoto.getContentType());

            var putObjectRequest = new PutObjectRequest(storageProperties.getS3().getBucket(), filePath,
                    newPhoto.getInputStream(), objectMetadata).withCannedAcl(CannedAccessControlList.PublicRead);

            amazonS3.putObject(putObjectRequest);
        } catch (Exception e) {
            throw new StorageException("Não foi possível enviar o arquivo para a Amazon S3", e);
        }
    }

    @Override
    public void remove(String fileName) {

    }

    private String getFilePath(String fileName) {
        return String.format("%s/%s", storageProperties.getS3().getPhotosDir(), fileName);
    }
}
