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
 * File: EmployeeConnection.java
 * Date: 22/10/2017
 * Notes: No connection parameters are used when pushing data because the push functions individually
 * and cannot be chained to one connection
 *
 * @version 1.0.0
 * @author Lee Tzilantonis
 */
public class EmployeeConnection extends ConnectionWrapper {
	
	//Code values assigned to specified return values
	
	/**
	 * Indicates an error returned by the SQL Driver
	 */
	public static final int SQL_ERROR = 0;
	
	/**
	 * Indicates an error with the SQL Driver, mostly connection issues or missing driver
	 */
	public static final int DRIVER_ERROR = 1;
	
	/**
	 * Indicates that the Employee cannot be found in the database
	 */
	public static final int UNKNOWN_EMPLOYEE = 2;
	
	/**
	 * Indicates that a duplicate bank was pushed
	 */
	public static final int DUPLICATE_BANK = 3;
	
	/**
	 * Indicates that a duplicate employee was pushed
	 */
	public static final int DUPLICATE_EMPLOYEE = 4;
	
	/**
	 * Indicates that the push / request was successful
	 */
	public static final int SUCCESS = 5;
	
	/**
	 * Indicates that a parameter was invalid but couldn't figure out which one
	 */
	public static final int INVALID_UNKNOWN = 200;
	
	/**
	 * Indicates that the ID parameter is invalid
	 */
	public static final int INVALID_ID = 201;
	
	/**
	 * Indicates that the First Name parameter is invalid
	 */
	public static final int INVALID_FIRST_NAME = 202;
	
	/**
	 * Indicates that the Last Name parameter is invalid
	 */
	public static final int INVALID_LAST_NAME = 203;
	
	/**
	 * Indicates that the Bank Name parameter is invalid
	 */
	public static final int INVALID_BANK = 204;
	
	/**
	 * Indicates that the BSB Number parameter is invalid
	 */
	public static final int INVALID_BSB_NUMBER = 205;
	
	/**
	 * Indicates that the Account Number parameter is invalid
	 */
	public static final int INVALID_ACCOUNT_NUMBER = 206;
	
	/**
	 * Indicates that all parameters are valid
	 */
	public static final int VALID = 300;
	
	/**
	 * Stores the final ID of the employee associated with the Object
	 */
	private final String id;
	
	/**
	 * Stores editable parameters associated with the Object
	 */
	private String firstName, lastName, bank, bsb, account;
	
	/**
	 * Alternate constructor to instantiate the EmployeeConnection Object
	 * 
	 * @param id The ID of the Employee being used
	 */
	public EmployeeConnection(String id) {
		this(id, "", "", "", "", "");
	}
	
	/**
	 * Alternate constructor to instantiate the EmployeeConnection Object
	 * 
	 * @param id		The ID of the Employee being used
	 * @param firstName	The First Name of the Employee being used
	 * @param lastName	The Last Name of the Employee being used
	 */
	public EmployeeConnection(String id, String firstName, String lastName) {
		this(id, firstName, lastName, "", "", "");
	}
	
	/**
	 * Main constructor to instantiate the EmployeeConnection Object
	 * 
	 * @param id		The ID of the Employee being used
	 * @param firstName	The First Name of the Employee being used
	 * @param lastName	The Last Name of the Employee being used
	 * @param bank		The Bank Name associated with the Employee being used
	 * @param bsb		The BSB Number associated with the Employee being used
	 * @param account	The Account Number associated with the Employee being used
	 */
	public EmployeeConnection(String id, String firstName, String lastName,
									String bank, String bsb, String account) {
		if (id == null)
			id = "";
		if (firstName == null)
			firstName = "";
		if (lastName == null)
			lastName = "";
		if (bank == null)
			bank = "";
		if (bsb == null)
			bsb = "";
		if (account == null)
			account = "";
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.bank = bank;
		this.bsb = bsb;
		this.account = account;
	}
	
	/**
	 * Gets the ID associated with the EmployeeConnection Object
	 * 
	 * @return The ID associated with the EmployeeConnection
	 */
	public String getID() {
		return this.id;
	}
	
	/**
	 * Gets the current First Name associated with the EmployeeConnection Object
	 * 
	 * @return The current First Name associated with the EmployeeConnection
	 */
	public String getFirstName() {
		this.setFirstName(this.firstName);
		return this.firstName;
	}
	
	/**
	 * Gets the current Last Name associated with the EmployeeConnection Object
	 * 
	 * @return The current Last Name associated with the EmployeeConnection
	 */
	public String getLastName() {
		this.setLastName(this.lastName);
		return this.lastName;
	}
	
	/**
	 * Gets the current Bank Name associated with the EmployeeConnection Object
	 * 
	 * @return The current Bank Name associated with the EmployeeConnection
	 */
	public String getBank() {
		this.setBank(this.bank);
		return this.bank;
	}
	
	/**
	 * Gets the current BSB Number associated with the EmployeeConnection Object
	 * 
	 * @return The current BSB Number associated with the EmployeeConnection
	 */
	public String getBSB() {
		this.setBSB(this.bsb);
		return this.bsb;
	}
	
	/**
	 * Gets the current Account Number associated with the EmployeeConnection Object
	 * 
	 * @return The current Account Number associated with the EmployeeConnection
	 */
	public String getAccountNumber() {
		this.setAccountNumber(this.account);
		return this.account;
	}
	
	/**
	 * Sets the First Name associated with the EmployeeConnection Object
	 * 
	 * @param firstName The First Name of the Employee
	 */
	public void setFirstName(String firstName) {
		if (firstName == null)
			firstName = "";
		this.firstName = firstName;
	}
	
	/**
	 * Sets the Last Name associated with the EmployeeConnection Object
	 * 
	 * @param lastName The Last Name of the Employee
	 */
	public void setLastName(String lastName) {
		if (lastName == null)
			lastName = "";
		this.lastName = lastName;
	}
	
	/**
	 * Sets the Bank Name associated with the EmployeeConnection Object
	 * 
	 * @param bank The Bank Name for the Employee
	 */
	public void setBank(String bank) {
		if (bank == null)
			bank = "";
		this.bank = bank;
	}
	
	/**
	 * Sets the BSB Number associated with the EmployeeConnection Object
	 * 
	 * @param bsb The String BSB Number for the Employee
	 * @return Whether or not the BSB is valid or has been set
	 */
	public boolean setBSB(String bsb) {
		if (bsb == null)
			bsb = "";
		try {
			return this.setBSB(Integer.parseInt(bsb));
		} catch (NumberFormatException e) {
			return false;
		}
	}
	
	/**
	 * Sets the BSB Number associated with the EmployeeConnection Object
	 * 
	 * @param bsb The Integer BSB Number for the Employee
	 * @return Whether or not the BSB is valid or has been set
	 */
	public boolean setBSB(int bsb) {
		if (bsb > 0)
			this.bsb = String.valueOf(bsb);
		else
			return false;
		return true;
	}
	
	/**
	 * Sets the Account Number associated with the EmployeeConnection Object
	 * 
	 * @param account The String Account Number for the Employee
	 * @return Whether or not the Account Number is valid or has been set
	 */
	public boolean setAccountNumber(String account) {
		if (account == null)
			account = "";
		try {
			return this.setAccountNumber(Integer.parseInt(account));
		} catch (NumberFormatException e) {
			return false;
		}
	}
	
	/**
	 * Sets the Account Number associated with the EmployeeConnection Object
	 * 
	 * @param account The Integer Account Number for the Employee
	 * @return Whether or not the Account Number is valid or has been set
	 */
	public boolean setAccountNumber(int account) {
		if (account > 0)
			this.account = String.valueOf(account);
		else
			return false;
		return true;
	}
	
	/**
	 * Validates the ID associated with the EmployeeConnection Object
	 * 
	 * @return Whether or not the ID is valid
	 */
	public boolean validateID() {
		if (this.id == null || this.id.length() < 1)
			return false;
		try {
			Integer.parseInt(this.id);
		} catch (NumberFormatException e) {
			return false;
		}
		return true;
	}
	
	/**
	 * Validates the current First Name associated with the EmployeeConnection Object
	 * 
	 * @return Whether or not the First Name is valid
	 */
	public boolean validateFirstName() {
		return this.getFirstName().length() > 0 && this.getFirstName().length() <= 20;
	}
	
	/**
	 * Validates the current Last Name associated with the EmployeeConnection Object
	 * 
	 * @return Whether or not the Last Name is valid
	 */
	public boolean validateLastName() {
		return this.getLastName().length() > 0 && this.getLastName().length() <= 20;
	}
	
	/**
	 * Validates the current Bank Name associated with the EmployeeConnection Object
	 * 
	 * @return Whether or not the Bank Name is valid
	 */
	public boolean validateBank() {
		return this.getBank().length() > 0 && this.getBank().length() <= 30;
	}
	
	/**
	 * Validates the current BSB Number associated with the EMployeeConnection Object
	 * 
	 * @return Whether or not the BSB Number is valid
	 */
	public boolean validateBSB() {
		if (!this.setBSB(this.bsb))
			return false;
		return this.getBSB().length() == 6;
	}
	
	/**
	 * Validates the current Account Number associated with the EmployeeConnection Object
	 * 
	 * @return Whether or not the Account Number is valid
	 */
	public boolean validateAccountNumber() {
		if (!this.setAccountNumber(this.account))
			return false;
		return this.getAccountNumber().length() > 0 && this.getAccountNumber().length() <= 11;
	}
	
	/**
	 * Validates all variables associated with the EmployeeConnection Object
	 * 
	 * @return Whether or not all variables associated with the EmployeeConnection are valid
	 */
	public boolean validatePackage() {
		return this.validateID() && this.validateFirstName() && this.validateLastName()
				&& this.validateBank() && this.validateBSB() && this.validateAccountNumber();
	}
	
	/**
	 * Validates all variables associated with the Employee table
	 * 
	 * @return Whether or not all variables associated with the Employee table are valid
	 */
	public boolean validateEmployeePackage() {
		return this.validateID() && this.validateFirstName() && this.validateLastName();
	}
	
	/**
	 * Validates all variables associated with the Bank table
	 * 
	 * @return Whether or not all variables associated with the Bank table are valid
	 */
	public boolean validateBankPackage() {
		return this.validateBank() && this.validateBSB() && this.validateAccountNumber();
	}
	
	/**
	 * Validates all variables associated with the EmployeeConnection Object and returns the Integer Result
	 * 
	 * @return The Integer representation of the validation result
	 */
	public int validatePackageResult() {
		if (this.validatePackage())
			return EmployeeConnection.VALID;
		if (!this.validateID())
			return EmployeeConnection.INVALID_ID;
		if (!this.validateFirstName())
			return EmployeeConnection.INVALID_FIRST_NAME;
		if (!this.validateLastName())
			return EmployeeConnection.INVALID_LAST_NAME;
		if (!this.validateBank())
			return EmployeeConnection.INVALID_BANK;
		if (!this.validateBSB())
			return EmployeeConnection.INVALID_BSB_NUMBER;
		if (!this.validateAccountNumber())
			return EmployeeConnection.INVALID_ACCOUNT_NUMBER;
		return EmployeeConnection.INVALID_UNKNOWN;
	}
	
	/**
	 * Validates all variables associated with the Employee table and returns the Integer Result
	 * 
	 * @return The Integer representation of the validation result
	 */
	public int validateEmployeePackageResult() {
		if (this.validateEmployeePackage())
			return EmployeeConnection.VALID;
		if (!this.validateID())
			return EmployeeConnection.INVALID_ID;
		if (!this.validateFirstName())
			return EmployeeConnection.INVALID_FIRST_NAME;
		if (!this.validateLastName())
			return EmployeeConnection.INVALID_LAST_NAME;
		return EmployeeConnection.INVALID_UNKNOWN;
	}
	
	/**
	 * Validates all variables associated with the Bank table and returns the Integer Result
	 * 
	 * @return The Integer representation of the validation result
	 */
	public int validateBankPackageResult() {
		if (this.validateBankPackage())
			return EmployeeConnection.VALID;
		if (!this.validateBank())
			return EmployeeConnection.INVALID_BANK;
		if (!this.validateBSB())
			return EmployeeConnection.INVALID_BSB_NUMBER;
		if (!this.validateAccountNumber())
			return EmployeeConnection.INVALID_ACCOUNT_NUMBER;
		return EmployeeConnection.INVALID_UNKNOWN;
	}
	
	/**
	 * Checks whether or not an Employee with the ID associated with the EmployeeConnection Object
	 * exists within the database
	 * 
	 * @return Whether or not the Employee exists within the database
	 */
	public boolean employeeExists() {
		ResultSet r = super.query("SELECT " + C.COLUMN_ID + " FROM " + C.TABLE_EMPLOYEE
				+ " WHERE " + C.COLUMN_ID + "=" + this.getID() + " LIMIT 1");
		if (r == null)
			return false;
		try {
			super.setRecentError(null);
			return r.first();
		} catch (SQLException e) {
			super.setRecentError(e);
			return false;
		}
	}
	
	/**
	 * Checks whether or not an Bank with the ID, BSB Number and Account Number associated with the
	 * EmployeeConnection Object exists within the database
	 * 
	 * @return Whether or not the Bank exists within the database
	 */
	public boolean bankExists() {
		ResultSet r = super.query("SELECT * FROM " + C.TABLE_BANK + " WHERE "
				+ C.COLUMN_ID + "=" + this.getID() + " AND "
				+ C.COLUMN_BSB_NUMBER + "=" + this.getBSB()+ " AND "
				+ C.COLUMN_ACCOUNT_NUMBER + "=" + this.getAccountNumber()+ " LIMIT 1");
		if (r == null)
			return false;
		try {
			super.setRecentError(null);
			return r.first();
		} catch (SQLException e) {
			super.setRecentError(e);
			return false;
		}
	}
	
	/**
	 * Pushes the Employee information associated with the EmployeeConnection Object to the database
	 * and overrides any data that may exist if specified
	 * 
	 * @param override Whether or not to Override existing data
	 * @return The Integer representation of the result of the transaction
	 */
	public int pushEmployee(boolean override) {
		// Can't push invalid data
		if (!this.validateEmployeePackage())
			return this.validateEmployeePackageResult();
		// Manual if's are easier
		if (this.employeeExists()) {
			if (override) {
				int e = super.update("UPDATE " + C.TABLE_EMPLOYEE + " SET "
						+ C.COLUMN_FIRST_NAME + "='" + this.getFirstName() + "'"
						+ C.COLUMN_LAST_NAME + "='" + this.getLastName() + "' WHERE "
						+ C.COLUMN_ID + "=" + this.getID());
				return e > 0 ? EmployeeConnection.SUCCESS :
						e < 0 ? EmployeeConnection.SQL_ERROR :
								EmployeeConnection.DRIVER_ERROR;
			} else
				return EmployeeConnection.DUPLICATE_EMPLOYEE;
		} else {
			int e = super.update("INSERT INTO " + C.TABLE_EMPLOYEE + " (" + C.COLUMN_ID
					+ ", " + C.COLUMN_FIRST_NAME + ", " + C.COLUMN_LAST_NAME
					+ ") VALUES (" + this.getID() + ", '" + this.getFirstName()
					+ "', '" + this.getLastName() + "'");
			return e > 0 ? EmployeeConnection.SUCCESS :
					e < 0 ? EmployeeConnection.SQL_ERROR :
							EmployeeConnection.DRIVER_ERROR;
		}
	}
	
	/**
	 * Pushes the Bank information associated with the EmployeeConnection Object to the database
	 * and overrides any data that may exist if specified
	 * 
	 * @param override Whether or not to Override existing data
	 * @return The Integer representation of the result of the transaction
	 */
	public int pushBank(boolean override) {
		// Can't push invalid data
		if (!this.validateBankPackage())
			return this.validateBankPackageResult();
		// Manual if's are easier
		if (this.employeeExists()) {
			if (this.bankExists()) {
				if (override) {
					int b = super.update("UPDATE " + C.TABLE_BANK + " SET "
							+ C.COLUMN_BANK + "='" + this.getBank() + "' WHERE "
							+ C.COLUMN_ID + "=" + this.getID() + " AND "
							+ C.COLUMN_BSB_NUMBER + "='" + this.getBSB() + "' AND "
							+ C.COLUMN_ACCOUNT_NUMBER + "='" + this.getAccountNumber() + "'");
					return b > 0 ? EmployeeConnection.SUCCESS :
							b < 0 ? EmployeeConnection.SQL_ERROR :
									EmployeeConnection.DRIVER_ERROR;
				} else
					return EmployeeConnection.DUPLICATE_BANK;
			} else {
				int b = super.update("INSERT INTO " + C.TABLE_BANK + " (" + C.COLUMN_ID
						+ ", " + C.COLUMN_BANK + ", " + C.COLUMN_BSB_NUMBER + ", "
						+ C.COLUMN_ACCOUNT_NUMBER + ") VALUES (" + this.getID() + ", '" + this.getBank()
						+ "', '" + this.getBSB() + "', '" + this.getAccountNumber() + "'");
				return b > 0 ? EmployeeConnection.SUCCESS :
						b < 0 ? EmployeeConnection.SQL_ERROR :
								EmployeeConnection.DRIVER_ERROR;
			}
		} else
			return EmployeeConnection.UNKNOWN_EMPLOYEE;
	}
	
	/**
	 * Pushes the Employee and Bank information associated with the EmployeeConnection Object to the
	 * database and overrides any data that may exist if specified
	 * 
	 * @param override Whether or not to Override existing data
	 * @return The Integer representation of the result of the transaction
	 */
	public int push(boolean override) {
		// Can't push invalid data
		if (!this.validatePackage())
			return this.validatePackageResult();
		// Manual if's are easier
		if (this.employeeExists()) {
			if (override) {
				int e = super.update("UPDATE " + C.TABLE_EMPLOYEE + " SET "
						+ C.COLUMN_FIRST_NAME + "='" + this.getFirstName() + "',"
						+ C.COLUMN_LAST_NAME + "='" + this.getLastName() + "' WHERE "
						+ C.COLUMN_ID + "=" + this.getID());
				if (e <= 0)
					return e < 0 ? EmployeeConnection.SQL_ERROR :
									EmployeeConnection.DRIVER_ERROR;
				if (this.bankExists()) {
					int b = super.update("UPDATE " + C.TABLE_BANK + " SET "
							+ C.COLUMN_BANK + "='" + this.getBank() + "' WHERE "
							+ C.COLUMN_ID + "=" + this.getID() + " AND "
							+ C.COLUMN_BSB_NUMBER + "='" + this.getBSB() + "' AND "
							+ C.COLUMN_ACCOUNT_NUMBER + "='" + this.getAccountNumber() + "'");
					return b > 0 ? EmployeeConnection.SUCCESS :
							b < 0 ? EmployeeConnection.SQL_ERROR :
									EmployeeConnection.DRIVER_ERROR;
				} else {
					int b = super.update("INSERT INTO " + C.TABLE_BANK + " (" + C.COLUMN_ID
						+ ", " + C.COLUMN_BANK + ", " + C.COLUMN_BSB_NUMBER + ", "
						+ C.COLUMN_ACCOUNT_NUMBER + ") VALUES (" + this.getID() + ", '" + this.getBank()
						+ "', '" + this.getBSB() + "', '" + this.getAccountNumber() + "')");
					return b > 0 ? EmployeeConnection.SUCCESS :
							b < 0 ? EmployeeConnection.SQL_ERROR :
									EmployeeConnection.DRIVER_ERROR;
				}
			} else {
				if (this.bankExists())
					return EmployeeConnection.DUPLICATE_BANK;
				else {
					int b = super.update("INSERT INTO " + C.TABLE_BANK + " (" + C.COLUMN_ID
						+ ", " + C.COLUMN_BANK + ", " + C.COLUMN_BSB_NUMBER + ", "
						+ C.COLUMN_ACCOUNT_NUMBER + ") VALUES (" + this.getID() + ", '" + this.getBank()
						+ "', '" + this.getBSB() + "', '" + this.getAccountNumber() + "')");
					return b > 0 ? EmployeeConnection.SUCCESS :
							b < 0 ? EmployeeConnection.SQL_ERROR :
									EmployeeConnection.DRIVER_ERROR;
				}
			}
		} else {
			// Can assume there are no bank records because of SQL relations
			int e = super.update("INSERT INTO " + C.TABLE_EMPLOYEE + " (" + C.COLUMN_ID
					+ ", " + C.COLUMN_FIRST_NAME + ", " + C.COLUMN_LAST_NAME
					+ ") VALUES (" + this.getID() + ", '" + this.getFirstName()
					+ "', '" + this.getLastName() + "')");
			if (e > 0) {
				int b = super.update("INSERT INTO " + C.TABLE_BANK + " (" + C.COLUMN_ID
					+ ", " + C.COLUMN_BANK + ", " + C.COLUMN_BSB_NUMBER + ", "
					+ C.COLUMN_ACCOUNT_NUMBER + ") VALUES (" + this.getID() + ", '" + this.getBank()
					+ "', '" + this.getBSB() + "', '" + this.getAccountNumber() + "')");
				return b > 0 ? EmployeeConnection.SUCCESS :
						b < 0 ? EmployeeConnection.SQL_ERROR :
								EmployeeConnection.DRIVER_ERROR;
			} else
				return EmployeeConnection.SQL_ERROR;
		}
	}
	
	/**
	 * Sub-Class used to store private fields for easier typing (C instead of EmployeeConnection)
	 */
	private class C {
		
		/**
		 * Tables used in the EmployeeConnection Object
		 */
		public static final String TABLE_EMPLOYEE = "tblEmployee";
		public static final String TABLE_BANK = "tblBank";

		/**
		 * Columns used within the Tables listed in EmployeeConnection.C.class
		 */
		public static final String COLUMN_ID = "employeeID";
		public static final String COLUMN_FIRST_NAME = "firstName";
		public static final String COLUMN_LAST_NAME = "lastName";
		public static final String COLUMN_BANK = "bank";
		public static final String COLUMN_BSB_NUMBER = "bsb";
		public static final String COLUMN_ACCOUNT_NUMBER = "accountNo";
		
	}
}