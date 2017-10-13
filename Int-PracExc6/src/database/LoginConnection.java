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

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * File: LoginConnection.java
 * Date: 12/10/2017
 * Notes: Decided not to extend the DBConnection.class because it would ruin the
 * point of the DBConnection.class interface. The LoginConnection class not only
 * handles the Connection to the login table but also the comparison between
 * user entered values and stored values.
 *
 * @version 1.0.0
 * @author Lee Tzilantonis
 */
public class LoginConnection {
	
	/**
	 * Code values assigned to specified return values
	 */
	public static final int SQL_ERROR = 0;
	public static final int INVALID_USERNAME = 1;
	public static final int INVALID_PASSWORD = 2;
	public static final int NOT_ENABLED = 3;
	public static final int ACCESS_USER = 4;
	public static final int ACCESS_ADMIN = 5;
	
	/**
	 * Values for the names of the table and its columns (because we are only
	 * using 1 table, i decided to move against INFORMATION_SCHEMA)
	 */
	public static final String TABLE = "tblUsers";
	public static final String COLUMN_USERNAME = "userName";
	public static final String COLUMN_FIRST_NAME = "firstName";
	public static final String COLUMN_LAST_NAME = "lastName";
	public static final String COLUMN_PASSWORD = "txtPassword";
	public static final String COLUMN_LEVEL = "AccessLevel";
	public static final String COLUMN_ENABLED = "Enabled";
	
	/**
	 * The class instance DBConnection variable used for database server interactions
	 */
	private DBConnection connection;
	
	/**
	 * The class instance SQLException variable used to store the most recent
	 * SQLException for later use (null if no SQLException occurred)
	 */
	private SQLException recent;
	
	/**
	 * The class instance variables to store the user provided username and
	 * password
	 */
	private String username, password;
	
	/**
	 * Main constructor to instantiate the LoginConnection Object
	 * 
	 * @param username - The user defined value used in comparison functions
	 * @param password - The user defined value used in comparison functions
	 */
	public LoginConnection(String username, String password) {
		this.username = username;
		this.password = password;
	}
	
	/**
	 * Gets the current username String stored within the LoginConnection, this
	 * value should NEVER return null
	 * 
	 * @return - The current username stored
	 */
	public String getUsername() {
		this.setUsername(this.username);
		return this.username;
	}
	
	/**
	 * Gets the current password String stored within the LoginConnection, this
	 * value should NEVER return null
	 * 
	 * @return - The current password stored
	 */
	public String getPassword() {
		this.setPassword(this.password);
		return this.password;
	}
	
	/**
	 * Sets the username to use for comparison with the database table
	 * 
	 * @param username - The new username to store for comparison
	 */
	public void setUsername(String username) {
		if (username == null)
			username = "";
		this.username = username;
	}
	
	/**
	 * Sets the password to use for comparison with the database table
	 * 
	 * @param password - The new password to store for comparison
	 */
	public void setPassword(String password) {
		if (password == null)
			password = "";
		this.password = password;
	}
	
	/**
	 * Creates and opens the connection variable stored within DBConnection
	 * 
	 * @return - Whether or not opening a connection was successful
	 */
	public boolean openConnection() {
		if (this.connection == null)
			this.connection = new DBConnection();
		if (!this.connection.isOpen())
			return this.connection.connect();
		return true;
	}
	
	/**
	 * Gets the most recent SQLException Object or null if none exists
	 * 
	 * @return - The most recent SQLException Object
	 */
	public SQLException getRecentError() {
		return this.recent;
	}
	
	/**
	 * Checks whether or not the stored username exists in the database.
	 * This method will create and close a DBConnection, optimized for single queries
	 * 
	 * @return - Whether or not the stored username exists in the database
	 */
	public boolean userExists() {
		if (!this.openConnection())
			return false;
		boolean r = this.userExists(this.connection);
		this.connection.close();
		return r;
	}
	
	/**
	 * Checks whether or not the stored username exists in the database using
	 * a DBConnection Object supplied, optimized for multiple queries
	 * 
	 * @param connection	- The DBConnection to use to access the database
	 * @return				- Whether or not the stored username exists in the database
	 */
	public boolean userExists(DBConnection connection) {
		if (connection == null)
			return false;
		String q = "SELECT " + LoginConnection.COLUMN_USERNAME
				+ " FROM " + LoginConnection.TABLE + " WHERE "
				+ LoginConnection.COLUMN_USERNAME + " = '"
				+ this.getUsername() + "' LIMIT 1";
		Statement s = connection.getStatement();
		if (s == null)
			return false;
		try {
			ResultSet r = s.executeQuery(q);
			this.recent = null;
			return r.next();
		} catch (SQLException e) {
			this.recent = e;
			return false;
		}
	}
	
	/**
	 * Checks whether or not the stored password matches the one in the database.
	 * This method will create and close a DBConnection, optimized for single queries
	 * 
	 * @return - Whether or not the stored password matches the one in the database
	 */
	public boolean passwordMatches() {
		if (!this.openConnection())
			return false;
		boolean r = this.passwordMatches(this.connection);
		this.connection.close();
		return r;
	}
	
	/**
	 * Checks whether or not the stored password matches the one in the database using
	 * a DBConnection Object supplied, optimized for multiple queries
	 * 
	 * @param connection	- The DBConnection to use to access the database
	 * @return				- Whether or not the stored password matches the one in the database
	 */
	public boolean passwordMatches(DBConnection connection) {
		if (connection == null || !this.userExists(connection))
			return false;
		String q = "SELECT " + LoginConnection.COLUMN_PASSWORD
				+ " FROM " + LoginConnection.TABLE + " WHERE "
				+ LoginConnection.COLUMN_USERNAME + " = '"
				+ this.getUsername() + "' LIMIT 1";
		Statement s = connection.getStatement();
		if (s == null)
			return false;
		try {
			ResultSet r = s.executeQuery(q);
			boolean result = false;
			if (r.next()) {
				if (r.getString(LoginConnection.COLUMN_PASSWORD)
						.equals(this.getPassword()))
					result = true;
			}
			this.recent = null;
			return result;
		} catch (SQLException e) {
			this.recent = e;
			return false;
		}
	}
	
	/**
	 * Gets the current account status represented as an Integer value based on
	 * the static class variables. This method will create and close a
	 * DBConnection, optimized for single queries
	 * 
	 * @return - The Integer representing the current Account Status
	 */
	public int getAccountStatus() {
		if (!this.openConnection())
			return LoginConnection.SQL_ERROR;
		int r = this.getAccountStatus(this.connection);
		this.connection.close();
		return r;
	}
	
	/**
	 * Gets the current account status represented as an Integer value based on
	 * the static class variables using a DBConnection Object supplied, optimized for multiple queries
	 * 
	 * @param connection	- The DBConnection to use to access the database
	 * @return				- The Integer representing the current Account Status
	 */
	public int getAccountStatus(DBConnection connection) {
		if (!this.userExists(connection))
			return LoginConnection.INVALID_USERNAME;
		if (!this.passwordMatches(connection))
			return LoginConnection.INVALID_PASSWORD;
		String q = "SELECT " + LoginConnection.COLUMN_ENABLED
				+ ", " + LoginConnection.COLUMN_LEVEL
				+ " FROM " + LoginConnection.TABLE + " WHERE "
				+ LoginConnection.COLUMN_USERNAME + " = '"
				+ this.getUsername() + "' LIMIT 1";
		Statement s = connection.getStatement();
		if (s == null)
			return LoginConnection.SQL_ERROR;
		try {
			ResultSet r = s.executeQuery(q);
			int result;
			if (r.next()) {
				if (r.getInt(LoginConnection.COLUMN_ENABLED) != 1)
					result = LoginConnection.NOT_ENABLED;
				else if (r.getInt(LoginConnection.COLUMN_LEVEL) < 2)
					result = LoginConnection.ACCESS_USER;
				else
					result = LoginConnection.ACCESS_ADMIN;
			} else
				result = LoginConnection.INVALID_USERNAME;
			this.recent = null;
			return result;
		} catch (SQLException e) {
			this.recent = e;
			return LoginConnection.SQL_ERROR;
		}
	}
}
