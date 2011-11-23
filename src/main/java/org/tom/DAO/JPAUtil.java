package org.tom.DAO;

import java.util.Properties;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class JPAUtil {
	private static String nome = "tom";
	private static Properties params = new Properties();
	private static EntityManagerFactory factory;
	private static EntityManager em = null;

	public static void setNome(String nome) {
		JPAUtil.nome = nome;
	}

	public static void addParameter(String key, String value) {
		params.put(key, value);
	}

	private static EntityManagerFactory getFactory() {
		if (factory == null){
			//factory = Persistence.createEntityManagerFactory("tom");
			factory = Persistence.createEntityManagerFactory(nome, params);
		}
		return factory;
	}

	public static EntityManager getManager() {
		if (em == null)
			em = getFactory().createEntityManager();
		return em;
	}

	public static void closeManager() {
		if (em != null && em.isOpen())
			em.close();
		em = null;
	}

	public static void beginTransaction() {
		EntityTransaction tx = getManager().getTransaction();
		if (tx != null)
			tx.begin();
	}

	public static void commitTransaction() throws ModeloException {
		EntityTransaction tx = null;
		try {
			tx = getManager().getTransaction();
			if (tx != null && tx.isActive())
				tx.commit();
		} catch (Exception e) {
			if (tx != null && tx.isActive())
				tx.rollback();
//			LogService.getLogger().error(e.getMessage());
			throw new ModeloException(e);
		}
	}

	public static void rollbackTransaction() {
		EntityTransaction tx = null;
		try {
			tx = getManager().getTransaction();
			if (tx != null && tx.isActive())
				tx.rollback();
		} catch (Exception e) {
//			LogService.getLogger().error(e.getMessage());
		}
	}
}