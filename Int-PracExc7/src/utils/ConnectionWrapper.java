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
package utils;

import database.DBConnection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * File: ConnectionWrapper.java
 * Date: 22/10/2017
 * Notes: Although it may seem like a pointless implementation seeing as i could combine both this
 * class and the DBConnection.class but i didn't want other classes extending from DBConnection.
 * This class can also be useful for a quick query.
 *
 * @version 1.0.0
 * @author Lee Tzilantonis
 */
public class ConnectionWrapper {
	
	/**
	 * The class instance DBConnection variable used for database server interactions
	 */
	protected DBConnection connection;
	
	/**
	 * The class instance SQLException variable used to store the most recent
	 * SQLException for later use (null if no SQLException occurred)
	 */
	private SQLException recent;
	
	/**
	 * Creates and opens the connection variable stored within DBConnection
	 * 
	 * @param connection The DBConnection Object to open
	 * @return Whether or not opening a connection was successful
	 */
	public boolean openConnection(DBConnection connection) {
		if (connection == null)
			return false;
		if (!connection.isOpen())
			return connection.connect();
		return true;
	}
	
	/**
	 * Creates and opens the connection variable stored within DBConnection
	 * 
	 * @return Whether or not opening a connection was successful
	 */
	public boolean openConnection() {
		if (this.connection == null)
			this.connection = new DBConnection();
		return this.openConnection(this.connection);
	}
	
	/**
	 * Closes the connection variable stored within DBConnection
	 * 
	 * @param connection The DBConnection Object to close
	 * @return Whether or not closing the connection was successful
	 */
	public boolean closeConnection(DBConnection connection) {
		if (connection == null)
			return true;
		if (!connection.isOpen())
			return true;
		return connection.close();
	}
	
	/**
	 * Closes the connection variable stored within DBConnection
	 * 
	 * @return Whether or not closing the connection was successful
	 */
	public boolean closeConnection() {
		if (this.connection == null)
			this.connection = new DBConnection();
		return this.closeConnection(this.connection);
	}
	
	/**
	 * Gets the most recent SQLException Object or null if none exists
	 * 
	 * @return The most recent SQLException Object
	 */
	public SQLException getRecentError() {
		return this.recent;
	}

	/**
	 * Checks whether or not the Wrapper has had an error from the last transaction
	 * 
	 * @return Whether or not there was an SQLException from the last transaction
	 */
	public boolean hasRecentError() {
		return this.getRecentError() != null;
	}
	
	/**
	 * Sets the recent SQLException, can be null.
	 * 
	 * @param e The new SQLException Object to set the most recent error to
	 */
	protected void setRecentError(SQLException e) {
		this.recent = e;
	}
	
	/**
	 * Queries the DBConnection Object supplied and returns a ResultSet Object generated by the query.
	 * Use ConnectionWrapper.hasRecentError() to determine whether or not the fail was SQL or Driver related.
	 * 
	 * @param connection	The DBConnection Object to use for querying
	 * @param q				The String SQL formatted query
	 * @return The ResultSet Object generated by the query or null on fail
	 */
	public ResultSet query(DBConnection connection, String q) {
		this.setRecentError(null);
		if (connection == null || q == null)
			return null;
		if (!this.openConnection(connection))
			return null;
		Statement s = connection.getStatement();
		if (s == null) {
			this.setRecentError(new SQLException("Statement couldn't be created"));
			return null;
		}
		try {
			return s.executeQuery(q);
		} catch (SQLException e) {
			this.setRecentError(e);
			return null;
		}
	}
	
	/**
	 * Queries the Wrappers DBConnection Object and returns a ResultSet Object generated by the query.
	 * Use ConnectionWrapper.hasRecentError() to determine whether or not the fail was SQL or Driver related.
	 * 
	 * @param q	The String SQL formatted query
	 * @return The ResultSet Object generated by the query or null on fail
	 */
	public ResultSet query(String q) {
		if (!this.openConnection())
			return null;
		ResultSet r = this.query(this.connection, q);
		this.closeConnection();
		return r;
	}
	
	/**
	 * Queries the DBConnection Object supplied and returns the amount of rows affected by the query.
	 * This method will produce an SQLException if any query that isn't INSERT, UPDATE or DELETE is used.
	 * Use ConnectionWrapper.hasRecentError() to determine whether or not the fail was SQL or Driver related.
	 * 
	 * @param connection	The DBConnection Object to use for querying
	 * @param q				The String SQL formatted query
	 * @return The amount of rows affected by the query
	 */
	public int update(DBConnection connection, String q) {
		this.setRecentError(null);
		if (connection == null || q == null)
			return 0;
		if (!this.openConnection(connection))
			return 0;
		Statement s = connection.getStatement();
		if (s == null) {
			this.setRecentError(new SQLException("Statement couldn't be created"));
			return -1;
		}
		try {
			return s.executeUpdate(q);
		} catch (SQLException e) {
			this.setRecentError(e);
			return -1;
		}
	}
	
	/**
	 * Queries the Wrappers DBConnection Object and returns the amount of rows affected by the query.
	 * This method will produce an SQLException if any query that isn't INSERT, UPDATE or DELETE is used.
	 * Use ConnectionWrapper.hasRecentError() to determine whether or not the fail was SQL or Driver related.
	 * 
	 * @param q The String SQL formatted query
	 * @return The amount of rows affected by the query
	 */
	public int update(String q) {
		if (!this.openConnection())
			return 0;
		int r = this.update(this.connection, q);
		this.closeConnection();
		return r;
	}
}
