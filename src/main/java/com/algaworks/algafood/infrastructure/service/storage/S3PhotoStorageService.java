package com.algaworks.algafood.infrastructure.service.storage;

import com.algaworks.algafood.domain.service.PhotoStorageService;
import org.springframework.stereotype.Service;

import java.io.InputStream;

@Service
public class S3PhotoStorageService implements PhotoStorageService {
    @Override
    public InputStream recover(String fileName) {
        return null;
    }

    @Override
    public void store(NewPhoto newPhoto) {

    }

    @Override
    public void remove(String fileName) {

    }
}
