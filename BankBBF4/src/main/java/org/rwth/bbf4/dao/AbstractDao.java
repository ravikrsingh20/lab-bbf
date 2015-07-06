package org.rwth.bbf4.dao;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractDao<T extends Object>
implements Dao<T>{

	@Autowired
	private SessionFactory sessionFactory;
	private Class<T> domainClass;

	protected Session getSession(){
		return sessionFactory.getCurrentSession();
	}

	public void persist(Object entity) {
		getSession().persist(entity);
	}
	
	@SuppressWarnings("unchecked")
	private Class<T> getDomainClass() {
		if (domainClass == null) {
			ParameterizedType thisType =
					(ParameterizedType) getClass().getGenericSuperclass();
			this.domainClass =
					(Class<T>) thisType.getActualTypeArguments()[0];
		}
		return domainClass;
	}
	
	@SuppressWarnings("unchecked")
	public T get(Serializable id) {
		return (T) getSession().get(getDomainClass(), id);
	}
	@SuppressWarnings("unchecked")
	 public T load(Serializable id) {
	 return (T) getSession().load(getDomainClass(), id);
	 }
	public void create(T t) {
		getSession().save(t);
	}
	public void update(T t) {
		getSession().update(t); 
	}
	public void delete(T t) { 
		getSession().delete(t); 
	}
	public void deleteById(Serializable id) { 
		delete(load(id)); 
	}
	
	public boolean exists(Serializable id) { 
		return (get(id) != null); 
	}
}