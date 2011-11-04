package org.tom.properties;

import java.util.ResourceBundle;

public class LoadProperties {
	private static LoadProperties loadPropertiesUnstructuredTom;
	private static LoadProperties loadPropertiesTom;
	
	private static String dialect = null;
	private static String driverClass = null;
	private static String url = null;
	private static String userName = null;
	private static String password = null;
	
	private LoadProperties(String propertiesPath) {
		ResourceBundle bundle = ResourceBundle.getBundle(propertiesPath);
		dialect = bundle.getString("dialect");
		driverClass = bundle.getString("driverClass");
		url = bundle.getString("url");
		userName = bundle.getString("userName");
		password = bundle.getString("password");
	}
	

	
	public String getDialect() {
		return dialect;
	}

	private void setDialect(String dialect) {
		this.dialect = dialect;
	}

	public String getDriverClass() {
		return driverClass;
	}

	private void setDriverClass(String driverClass) {
		this.driverClass = driverClass;
	}

	public String getUrl() {
		return url;
	}

	private void setUrl(String url) {
		this.url = url;
	}

	public String getUserName() {
		return userName;
	}

	private void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	private void setPassword(String password) {
		this.password = password;
	}
}
