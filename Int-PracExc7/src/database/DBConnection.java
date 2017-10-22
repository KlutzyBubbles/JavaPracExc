/*
 * The MIT License
 *
 * Copyright 2017 Lee Tzilantonis.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * File: DBConnection.java
 * Date: 12/10/2017
 * Notes: DBConnection.class and LoginConnection.class were separated to allow
 * me to re use the DBConnection.class in future projects.
 *
 * @version 1.0.0
 * @author Lee Tzilantonis
 */
public class DBConnection {
	
	/**
	 * Default values used for establishing a connection to the database
	 */
	private static final String HOST = "aa1pbp9r9ep7nr4.cjteaf7u2cci.ap-southeast-2.rds.amazonaws.com";
	private static final String USERNAME = "dataappsmysql";
	private static final String PASSWORD = "_d&cQA*2!|g_VnuM";
	private static final String DATABASE = "int_java_login";
	private static final int PORT = 3306;
	
	/**
	 * Driver package and class name
	 */
	public static final String DRIVER = "com.mysql.jdbc.Driver";
	
	/**
	 * DBMS Type associated with the Driver
	 */
	public static final String TYPE = "mysql";
	
	/**
	 * The class instance variables used to establish a connection to the database
	 */
	private final String host, username, password, database;
	private final int port;
	
	/**
	 * The class instance Connection variable used to interact with the database
	 */
	private Connection con;
	
	/**
	 * Main constructor for creating a DBConnection
	 * 
	 * @param host		The host name to be used
	 * @param port		The port of the server
	 * @param username	The username to connect with
	 * @param password	The password to connect with
	 * @param database	The database name to connect to
	 */
	public DBConnection(String host, int port, String username, String password, String database) {
		if (host == null || username == null || password == null || database == null)
			throw new IllegalArgumentException("Cannot create a connection from null values");
		if (port < 1)
			port = 3306;
		this.host = host;
		this.username = username;
		this.password = password;
		this.database = database;
		this.port = port;
	}
	
	/**
	 * Alternate constructor for creating a DBConnection, uses default static
	 * PORT variable
	 * 
	 * @param host		The host name to be used
	 * @param username	The username to connect with
	 * @param password	The password to connect with
	 * @param database	The database name to connect to
	 */
	public DBConnection(String host, String username, String password, String database) {
		this(host, DBConnection.PORT, username, password, database);
	}
	
	/**
	 * Alternate constructor for creating a DBConnection, uses default static
	 * PORT, USERNAME and PASSWORD variables
	 * 
	 * @param host		The host name to be used
	 * @param database	The database name to connect to
	 */
	public DBConnection(String host, String database) {
		this(host, DBConnection.USERNAME, DBConnection.PASSWORD, database);
	}
	
	/**
	 * Default constructor for creating a DBConnection, uses all of the default
	 * static variables
	 */
	public DBConnection() {
		this(DBConnection.HOST, DBConnection.DATABASE);
	}
	
	/**
	 * Gets the host name associated with the DBConnection
	 * 
	 * @return The host name associated with the DBConnection
	 */
	public String getHost() {
		return this.host;
	}
	
	/**
	 * Gets the username associated with the DBConnection
	 * 
	 * @return The username associated with the DBConnection
	 */
	public String getUsername() {
		return this.username;
	}
	
	/**
	 * Gets the database name associated with the DBConnection
	 * 
	 * @return The database name associated with the DBConnection
	 */
	public String getDatabase() {
		return this.database;
	}
	
	/**
	 * Gets the port associated with the DBConnection
	 * 
	 * @return The port associated with the DBConnection
	 */
	public int getPort() {
		return this.port;
	}
	
	/**
	 * Checks if the Connection to the database server is open
	 * 
	 * @return Whether or not the Connection to the database is open
	 */
	public boolean isOpen() {
		if (this.con == null)
			return false;
		try {
			return !this.con.isClosed();
		} catch (SQLException e) {
			return false;
		}
	}
	
	/**
	 * Gets the Connection instance associated with the DBConnection or null
	 * if the connection is closed or not valid
	 * 
	 * @return The Connection instance associated with the DBConnection
	 */
	public Connection getConnection() {
		if (!this.isValid() || !this.isOpen()) {
			this.close();
			return null;
		}
		return this.con;
	}
	
	/**
	 * Gets a Statement Object to execute queries from or null if the connection
	 * is closed or not valid. Can also return null if an error occurred.
	 * 
	 * @return The Statement Object to execute queries from
	 */
	public Statement getStatement() {
		if (!this.isValid() || !this.isOpen())
			return this.close() ? null : null;
		try {
			return this.getConnection().createStatement();
		} catch (SQLException e) {
			return null;
		}
	}
	
	/**
	 * Checks if the supplied values for the database Connection are valid
	 * 
	 * @return Whether or not the values supplied are valid
	 */
	public boolean isValid() {
		return this.getHost() != null
				&& this.getUsername() != null
				&& this.password != null
				&& this.getDatabase() != null
				&& this.getPort() > 0;
	}
	
	/**
	 * Builds the JDBC Connection URL and returns the value as a String Object
	 * or null if the Connection values supplied are in-valid
	 * 
	 * @return The JDBC Connection URL String
	 */
	public String buildURL() {
		if (!this.isValid())
			return null;
		StringBuilder b = new StringBuilder("jdbc:");
		b.append(DBConnection.TYPE).append("://");
		b.append(this.getHost()).append(':');
		b.append(this.getPort()).append('/');
		b.append(this.getDatabase()).append("?autoreconnect=true");
		return b.toString();
	}
	
	/**
	 * Establishes a Connection with the database server and closes the previous
	 * Connection if it was open.
	 * 
	 * @return Whether or not the Connection successfully established
	 */
	public boolean connect() {
		if (!this.isValid())
			return false;
		try {
			if (this.con != null)
				this.close();
			Class.forName(DBConnection.DRIVER);
			this.con = DriverManager.getConnection(this.buildURL(), this.getUsername(), this.password);
			return true;
		} catch (SQLException | ClassNotFoundException e) {
			return false;
		}
	}
	
	/**
	 * Closes the current Connection made to the database server
	 * 
	 * @return Whether or not the Connection was able to be closed
	 */
	public boolean close() {
		if (this.con == null)
			return false;
		try {
			this.con.close();
			return true;
		} catch (SQLException e) {
			return false;
		}
	}
}
