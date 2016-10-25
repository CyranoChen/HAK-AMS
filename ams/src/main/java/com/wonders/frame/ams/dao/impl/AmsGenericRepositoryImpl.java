package com.wonders.frame.ams.dao.impl;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import com.wonders.frame.ams.model.IRemoveFlagModel;
import com.wonders.frame.core.dao.GenericRepository;
import com.wonders.frame.core.dao.impl.GenericRepositoryImpl;
import com.wonders.frame.core.model.IRemovedModel;

/**
 * {@link GenericRepository}接口实现类，并在{@link SimpleJpaRepository}基础上扩展。
 * 
 * @param <T>
 *            ORM对象
 * @param <ID>
 *            主键ID
 */
@NoRepositoryBean
// 必须的
public class AmsGenericRepositoryImpl<T, ID extends Serializable> extends
	GenericRepositoryImpl<T, ID> implements GenericRepository<T, ID> {

	public AmsGenericRepositoryImpl(
			final JpaEntityInformation<T, ?> entityInformation,
			EntityManager entityManager) {

		super(entityInformation, entityManager);

	}
	public AmsGenericRepositoryImpl(Class<T> domainClass, EntityManager em) {
		super(domainClass, em);

	}
	

	@Override
	public T findById(final ID id) {
		return this.findOne(new Specification<T>() {

			@Override
			public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query,
					CriteriaBuilder cb) {
				query.where(cb.equal(root.get("id"), id), cb.equal(root
						.get("removeFlag"), 1));

				return null;
			}
		});

	}
	
	@Override
	public T findExistOne() {
		List<T> rs=this.findAll(new Specification<T>() {
			@Override
			public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query,
					CriteriaBuilder cb) {
				query.where(cb.equal(root
						.get("removeFlag"), 1));

				return null;
			}
		});
		
		if(rs.size()>0){
			return rs.get(0);
		}else{
			return null;
		}

	}
	

	
	@Override
	public Integer remove(T entity) {
		if (entity != null) {
			if(entity instanceof IRemoveFlagModel){
				IRemoveFlagModel dm = (IRemoveFlagModel) entity;
				dm.setRemoveFlag("0");
				// this.em.merge(dm);
				this.save(entity);
				return 1;
			}else{
				this.delete(entity);
				return 1;
			}
		} else {
			return 0;
		}
	}
	
	

	
	





}