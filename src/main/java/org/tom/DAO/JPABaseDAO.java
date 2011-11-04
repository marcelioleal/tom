package org.tom.DAO;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;


public abstract class JPABaseDAO<Entity> {
	
	public JPABaseDAO() {
		super();
	}
	
	public Entity find(Class<? extends Entity> clazz, Number id) throws ModeloException {
		EntityManager manager = JPAUtil.getManager();
		Entity entity = null;
		try {
			entity = (Entity) manager.find(clazz, id);
		} catch (Exception e) {
			throw new ModeloException("Entidade nao encontrada: " + e.getMessage(), e);
		}
		return entity;
	}
	
	public void persist(Entity obj) throws ModeloException {
		try {
			EntityManager manager = JPAUtil.getManager();
			JPAUtil.beginTransaction();
			manager.persist(obj);
			JPAUtil.commitTransaction();
		} catch (Exception e) {
			JPAUtil.rollbackTransaction();
			throw new ModeloException("Falha ao inserir entidade: " + e.getMessage(), e);
		}
	}
	
	public void remove(Entity obj) throws ModeloException {
		try {
			EntityManager manager = JPAUtil.getManager();
			JPAUtil.beginTransaction();
			if (manager.contains(obj))
				manager.remove(obj);
			else {
				obj = manager.merge(obj);
				manager.remove(obj);
			}
			JPAUtil.commitTransaction();
		} catch (Exception e) {
			JPAUtil.rollbackTransaction();
			throw new ModeloException("Falha ao remover entidade: " + e.getMessage(), e);
		}
	}
	
	public void removeAll(List<Entity> list) throws ModeloException {
		try {
			EntityManager manager = JPAUtil.getManager();
			JPAUtil.beginTransaction();
			for (Entity entity : list) {
				if (manager.contains(entity))
					manager.remove(entity);
				else {
					entity = manager.merge(entity);
					manager.remove(entity);
				}
			}
			JPAUtil.commitTransaction();
		} catch (Exception e) {
			JPAUtil.rollbackTransaction();
			throw new ModeloException("Falha ao remover entidade: " + e.getMessage(), e);
		}
	}
	
	public List<Entity> createQueryList(String sql, Object[] ... params) throws ModeloException {
		EntityManager manager = JPAUtil.getManager();
		try {
			Query query = manager.createQuery(sql);
			if(params != null){
				for (Object[] param : params) {
					query.setParameter(param[0].toString(), param[1]);
				}
			}			
			return query.getResultList();
		} catch (Exception e) {
			throw new ModeloException("Falha ao criar consulta: " + e.getMessage(), e);
		}
	}
	
	public Entity createQueryUnique(String sql, Object[] ... params) throws ModeloException {
		EntityManager manager = JPAUtil.getManager();
		try {
			Query query = manager.createQuery(sql);
			for (Object[] param : params) {
				query.setParameter(param[0].toString(), param[1]);
			}
			return (Entity) query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		} catch (Exception e) {
			throw new ModeloException("Falha ao criar consulta: " + e.getMessage(), e);
		}
	}
	
	public List<Entity> createNativeQueryList(String sql, Class type, Object[] ... params) throws ModeloException {
		EntityManager manager = JPAUtil.getManager();
		try {
			Query query = manager.createNativeQuery(sql, type);
			if(params != null){
				for (Object[] param : params) {
					query.setParameter(param[0].toString(), param[1]);
				}
			}			
			return query.getResultList();
		} catch (Exception e) {
			throw new ModeloException("Falha ao criar consulta: " + e.getMessage(), e);
		}
	}
	
}