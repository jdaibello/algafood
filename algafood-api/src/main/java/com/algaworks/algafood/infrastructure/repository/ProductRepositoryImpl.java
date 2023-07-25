package com.algaworks.algafood.infrastructure.repository;

import com.algaworks.algafood.domain.model.ProductPhoto;
import com.algaworks.algafood.domain.repository.ProductRepositoryQueries;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class ProductRepositoryImpl implements ProductRepositoryQueries {

    @PersistenceContext
    private EntityManager manager;

    @Transactional
    @Override
    public ProductPhoto save(ProductPhoto photo) {
        return manager.merge(photo);
    }

    @Override
    public void delete(ProductPhoto photo) {
        manager.remove(photo);
    }
}
