///*
// * Created on 02/03/2005
// *
// * TODO To change the template for this generated file go to
// * Window - Preferences - Java - Code Style - Code Templates
// */
//package org.tom.DAO;
//
//import java.io.IOException;
//
//import org.hibernate.HibernateException;
//import org.tom.properties.LoadProperties;
//
///**
// * @author Administrador
// *
// * TODO To change the template for this generated type comment go to
// * Window - Preferences - Java - Code Style - Code Templates
// */
//public class ConnectionControl {
//	public void connect(int option) throws IOException, HibernateException {
//		LoadProperties loadProperties;
//		if(option == 0)	{
//			loadProperties = LoadProperties.getTomInstance();
//			JPAUtil.addParameter("hibernate.connection.driver_class", loadProperties.getDriverClass());
//			JPAUtil.addParameter("hibernate.connection.url", loadProperties.getUrl());
//			JPAUtil.addParameter("hibernate.connection.username", loadProperties.getUserName());
//			JPAUtil.addParameter("hibernate.connection.password", loadProperties.getPassword());
//			JPAUtil.addParameter("hibernate.dialect", loadProperties.getDialect());
//			JPAUtil.addParameter("hibernate.show_sql", "false");
//			JPAUtil.addParameter("hibernate.c3p0.min_size", "5");
//			JPAUtil.addParameter("hibernate.c3p0.max_size", "20");
//			JPAUtil.addParameter("hibernate.c3p0.timeout", "1800");
//			JPAUtil.addParameter("hibernate.c3p0.max_statements", "50");
//			JPAUtil.addParameter("hibernate.hbm2ddl.auto", "update");
//		}
//	}
//	
//	public void disconnect() throws HibernateException {
//		JPAUtil.closeManager();
//	}
//}