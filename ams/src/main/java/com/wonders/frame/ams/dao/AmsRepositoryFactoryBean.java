/**
 * 
 */
package com.wonders.frame.ams.dao;
import java.io.Serializable;
import javax.persistence.EntityManager;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactoryBean;
import org.springframework.data.repository.core.support.RepositoryFactorySupport;

/**
 * @author mengjie
 *
 */
public class AmsRepositoryFactoryBean<T extends JpaRepository<S, ID>, S, ID extends Serializable>
		extends JpaRepositoryFactoryBean<T, S, ID> {
	/**
     *
     * @param entityManager
     * @return
     */
	@Override
	protected RepositoryFactorySupport createRepositoryFactory(EntityManager entityManager) {
 
    	return new AmsRepositoryFactory(entityManager);
    }
}