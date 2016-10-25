package com.wonders.frame.ams.dao;

import java.io.Serializable;

import javax.persistence.EntityManager;

import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactory;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.data.repository.core.RepositoryMetadata;
import org.springframework.util.Assert;

import com.wonders.frame.ams.dao.impl.AmsGenericRepositoryImpl;
import com.wonders.frame.core.dao.impl.GenericRepositoryImpl;

/**
 * @author mengjie
 *
 */
public class AmsRepositoryFactory extends JpaRepositoryFactory {

	private final EntityManager entityManager;
    
	public AmsRepositoryFactory(EntityManager entityManager) {
		super(entityManager);
		Assert.notNull(entityManager);
		this.entityManager = entityManager;
		
	}
	
	@Override
	protected <T, ID extends Serializable> SimpleJpaRepository<?, ?> getTargetRepository(RepositoryMetadata metadata, EntityManager entityManager) {

		//TODO
		JpaEntityInformation<?, Serializable> entityInformation = getEntityInformation(metadata.getDomainType());
		return new AmsGenericRepositoryImpl(entityInformation, entityManager); // custom implementation
	}

    @Override
    protected Class<?> getRepositoryBaseClass(RepositoryMetadata metadata) {
 
    	return AmsGenericRepositoryImpl.class;
    }
}