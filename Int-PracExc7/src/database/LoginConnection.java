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
import utils.ConnectionWrapper;

/**
 * File: LoginConnection.java
 * Date: 22/10/2017
 * Notes: Decided not to extend the DBConnection.class because it would ruin the
 * point of the DBConnection.class interface. The LoginConnection class not only
 * handles the Connection to the login table but also the comparison between
 * user entered values and stored values.
 *
 * @version 1.1.0
 * @author Lee Tzilantonis
 */
public class LoginConnection extends ConnectionWrapper {
	
	/**
	 * Indicates an error returned by the SQL Driver
	 */
	public static final int SQL_ERROR = 0;
	
	/**
	 * Indicates an error with the SQL Driver, mostly connection issues or missing driver
	 */
	public static final int DRIVER_ERROR = 1;
	
	/**
	 * Indicates that the username parameter is invalid
	 */
	public static final int INVALID_USERNAME = 2;
	
	/**
	 * Indicates that the password parameter is invalid
	 */
	public static final int INVALID_PASSWORD = 3;
	
	/**
	 * Indicates that the account requested isn't enabled
	 */
	public static final int NOT_ENABLED = 4;
	
	/**
	 * Indicates that the account requested is a user account
	 */
	public static final int ACCESS_USER = 5;
	
	/**
	 * Indicates that the account requested is an administrator account
	 */
	public static final int ACCESS_ADMIN = 6;
	
	/**
	 * The class instance variables to store the user provided username and
	 * password
	 */
	private String username, password;
	
	/**
	 * Main constructor to instantiate the LoginConnection Object
	 * 
	 * @param username The user defined value used in comparison functions
	 * @param password The user defined value used in comparison functions
	 */
	public LoginConnection(String username, String password) {
		this.username = username;
		this.password = password;
	}
	
	/**
	 * Gets the current username String stored within the LoginConnection, this
	 * value should NEVER return null
	 * 
	 * @return The current username stored
	 */
	public String getUsername() {
		this.setUsername(this.username);
		return this.username;
	}
	
	/**
	 * Gets the current password String stored within the LoginConnection, this
	 * value should NEVER return null
	 * 
	 * @return The current password stored
	 */
	public String getPassword() {
		this.setPassword(this.password);
		return this.password;
	}
	
	/**
	 * Sets the username to use for comparison with the database table
	 * 
	 * @param username The new username to store for comparison
	 */
	public void setUsername(String username) {
		if (username == null)
			username = "";
		this.username = username;
	}
	
	/**
	 * Sets the password to use for comparison with the database table
	 * 
	 * @param password The new password to store for comparison
	 */
	public void setPassword(String password) {
		if (password == null)
			password = "";
		this.password = password;
	}
	
	/**
	 * Checks whether or not the stored username exists in the database.
	 * This method will create and close a DBConnection, optimized for single queries
	 * 
	 * @return Whether or not the stored username exists in the database
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
	 * @param connection	The DBConnection to use to access the database
	 * @return				Whether or not the stored username exists in the database
	 */
	public boolean userExists(DBConnection connection) {
		if (connection == null)
			return false;
		ResultSet r = super.query(connection, "SELECT " + C.COLUMN_USERNAME
												+ " FROM " + C.TABLE_USERS + " WHERE "
												+ C.COLUMN_USERNAME + " = '"
												+ this.getUsername() + "' LIMIT 1");
		if (r == null)
			return false;
		try {
			return r.first();
		} catch (SQLException e) {
			return false;
		}
	}
	
	/**
	 * Checks whether or not the stored password matches the one in the database.
	 * This method will create and close a DBConnection, optimized for single queries
	 * 
	 * @return Whether or not the stored password matches the one in the database
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
	 * @param connection	The DBConnection to use to access the database
	 * @return				Whether or not the stored password matches the one in the database
	 */
	public boolean passwordMatches(DBConnection connection) {
		if (connection == null || !this.userExists(connection))
			return false;
		ResultSet r = super.query(connection, "SELECT " + C.COLUMN_PASSWORD
												+ " FROM " + C.TABLE_USERS + " WHERE "
												+ C.COLUMN_USERNAME + " = '"
												+ this.getUsername() + "' LIMIT 1");
		if (r == null)
			return false;
		try {
			if (r.first())
				if (r.getString(C.COLUMN_PASSWORD)
						.equals(this.getPassword()))
					return true;
			return false;
		} catch (SQLException e) {
			return false;
		}
	}
	
	/**
	 * Gets the current account status represented as an Integer value based on
	 * the static class variables. This method will create and close a
	 * DBConnection, optimized for single queries
	 * 
	 * @return The Integer representing the current Account Status
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
	 * the static class variables using a DBConnection Object supplied,
	 * optimized for multiple queries
	 * 
	 * @param connection	The DBConnection to use to access the database
	 * @return				The Integer representing the current Account Status
	 */
	public int getAccountStatus(DBConnection connection) {
		if (!this.userExists(connection))
			return LoginConnection.INVALID_USERNAME;
		if (!this.passwordMatches(connection))
			return LoginConnection.INVALID_PASSWORD;
		ResultSet r = super.query(connection, "SELECT " + C.COLUMN_ENABLED
												+ ", " + C.COLUMN_LEVEL
												+ " FROM " + C.TABLE_USERS + " WHERE "
												+ C.COLUMN_USERNAME + " = '"
												+ this.getUsername() + "' LIMIT 1");
		if (r == null) {
			if (super.hasRecentError())
				return LoginConnection.SQL_ERROR;
			return LoginConnection.DRIVER_ERROR;
		}
		try {
			if (r.first()) {
				if (r.getInt(C.COLUMN_ENABLED) != 1)
					return LoginConnection.NOT_ENABLED;
				else if (r.getInt(C.COLUMN_LEVEL) < 2)
					return LoginConnection.ACCESS_USER;
				else
					return LoginConnection.ACCESS_ADMIN;
			} else
				return LoginConnection.INVALID_USERNAME;
		} catch (SQLException e) {
			return LoginConnection.SQL_ERROR;
		}
	}
	
	/**
	 * Sub-Class used to store private fields for easier typing (C instead of LoginConnection)
	 */
	private class C {
		
		/**
		 * Tables used in the LoginConnection Object
		 */
		public static final String TABLE_USERS = "tblUsers";

		/**
		 * Columns used within the Tables listed in LoginConnection.C.class
		 */
		public static final String COLUMN_USERNAME = "userName";
		public static final String COLUMN_FIRST_NAME = "firstName";
		public static final String COLUMN_LAST_NAME = "lastName";
		public static final String COLUMN_PASSWORD = "txtPassword";
		public static final String COLUMN_LEVEL = "AccessLevel";
		public static final String COLUMN_ENABLED = "Enabled";
		
	}
}