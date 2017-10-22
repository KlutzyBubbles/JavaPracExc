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
package gui;

import database.EmployeeConnection;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.sql.SQLException;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import utils.SpringUtilities;

/**
 * File: RegisterScreen.java
 * Date: 21/10/2017
 * Notes: Char[] from getPassword() is converted to a String for simplicity, it
 * isn't the best option in a real world situation, but in a real world situation
 * the password stored in the table would be encrypted. And sending an un-encrypted
 * password on an un-encrypted connection is DEFINITELY not safe.
 *
 * @version 1.0.0
 * @author Lee Tzilantonis
 */
public class RegisterScreen extends JPanel implements ActionListener {
	
	/**
	 * Border for JPanel Title and form padding
	 */
	private static final int BORDER = 10;
	
	/**
	 * Values used for naming and recognizing buttons and clicks
	 */
    private static final String CANCEL = "Cancel";
	private static final String REGISTER = "Register";
	
	/**
	 * LoginScreen reference used ONLY for updating parent components to dispose of the RegisterScreen
	 */
	private final LoginScreen reference;
	
	/**
	 * JLabel used to store the result of the login attempt
	 */
    private JLabel result;
	
	/**
	 * Input text fields used for registering an employee
	 */
	private JTextField id, first, last, bank, bsb, account;
	
	/**
	 * Override JCheckBox reference used to get the override value
	 */
	private JCheckBox override;
	
	/**
	 * Indicates the current loading state of the Object, this alters the state of certain UI elements
	 */
	private boolean loading = false;
    
	/**
	 * Default constructor to instantiate the LoginScreen and initialize
	 * contained components
	 * 
	 * @param reference The LoginScreen instance to be used as a reference
	 */
    public RegisterScreen(LoginScreen reference) {
        //super();
		if (reference == null)
			throw new IllegalArgumentException("Cannot create a register form without the LoginScreen context");
		this.reference = reference;
        super.setLayout(new BorderLayout());
    }
	
	/**
	 * Displays a message on the LoginScreen frame
	 * 
	 * @param message	The message to display in String format (HTML Supported)
	 * @param error		Whether or not this message is an error (for formatting)
	 */
	public void message(String message, boolean error) {
		if (this.result != null) {
			this.result.setText("<html><p align=center>" + message);
			if (error)
				this.result.setForeground(Color.RED);
			else
				this.result.setForeground(Color.BLACK);
			this.updateComponents();
		}
	}
    
	/**
	 * Initiates or re-initiates the components within the JFrame while reserving
	 * any input values and Object states
	 */
    public void updateComponents() {
		// All Components are initialised in the order of their corresponding stores value
		String a = this.id == null ? "" : this.id.getText();
		String b = this.first == null ? "" : this.first.getText();
		String c = this.last == null ? "" : this.last.getText();
		String d = this.bank == null ? "" : this.bank.getText();
		String e = this.bsb == null ? "" : this.bsb.getText();
		String f = this.account == null ? "" : this.account.getText();
		boolean g = this.override == null ? false : this.override.isSelected();
		
		super.removeAll();
		
		if (this.result == null)
			this.result = this.createLabel(null, Font.BOLD, 14, null);
		
		JLabel title = this.createLabel("Register Employee", Font.BOLD, 24, Color.BLACK, RegisterScreen.BORDER);
		
		// Employee Panel
		JPanel employee = new JPanel(new SpringLayout());
		employee.add(this.createLabel("Employee Details", Font.BOLD, 18, Color.RED));
		employee.add(this.createLabel(null, Font.PLAIN, 1, null));
		
		// ID
		JLabel l0 = this.createLabel("ID: ", Font.PLAIN, 14, null);
		this.id = new JTextField(a);
		this.id.setEnabled(!this.loading);
		l0.setLabelFor(this.id);
		employee.add(l0);
		employee.add(this.id);
		
		// First Name
		JLabel l1 = this.createLabel("First Name: ", Font.PLAIN, 14, null);
		this.first = new JTextField(b);
		this.first.setEnabled(!this.loading);
		l1.setLabelFor(this.first);
		employee.add(l1);
		employee.add(this.first);
		
		// Last Name
		JLabel l2 = this.createLabel("Last Name: ", Font.PLAIN, 14, null);
		this.last = new JTextField(c);
		this.last.setEnabled(!this.loading);
		l2.setLabelFor(this.last);
		employee.add(l2);
		employee.add(this.last);
		
		// Banking Panel
		JPanel bankPanel = new JPanel(new SpringLayout());
		bankPanel.add(this.createLabel("Banking Details", Font.BOLD, 18, Color.RED));
		bankPanel.add(this.createLabel(null, Font.PLAIN, 1, null));
		
		// Bank Name
		JLabel l3 = this.createLabel("Bank: ", Font.PLAIN, 14, null);
		this.bank = new JTextField(d);
		this.bank.setEnabled(!this.loading);
		l3.setLabelFor(this.bank);
		bankPanel.add(l3);
		bankPanel.add(this.bank);
		
		// BSB Number
		JLabel l4 = this.createLabel("BSB Number: ", Font.PLAIN, 14, null);
		this.bsb = new JTextField(e);
		this.bsb.setEnabled(!this.loading);
		l4.setLabelFor(this.bsb);
		bankPanel.add(l4);
		bankPanel.add(this.bsb);
		
		// Account Number
		JLabel l5 = this.createLabel("Account Number: ", Font.PLAIN, 14, null);
		this.account = new JTextField(f);
		this.account.setEnabled(!this.loading);
		l5.setLabelFor(this.account);
		bankPanel.add(l5);
		bankPanel.add(this.account);
		
		// Form Panel
		JPanel form = new JPanel(new GridLayout(1, 2));
		form.add(employee);
		form.add(bankPanel);
		
		// Register button
        JButton register = new JButton(RegisterScreen.REGISTER);
        register.setMnemonic(KeyEvent.VK_C);
        register.setName(RegisterScreen.REGISTER);
        register.addActionListener(this);
		register.setEnabled(!this.loading);
		
		// Cancel button
        JButton cancel = new JButton(RegisterScreen.CANCEL);
        cancel.setMnemonic(KeyEvent.VK_C);
        cancel.setName(RegisterScreen.CANCEL);
        cancel.addActionListener(this);
		
		this.override = new JCheckBox("Override Current", g);
		this.override.setMnemonic(KeyEvent.VK_O);
		
		JPanel buttons = new JPanel();
		buttons.add(this.result);
		buttons.add(cancel);
		buttons.add(register);
		buttons.add(this.override);
		
		super.add(title, BorderLayout.NORTH);
		super.add(form, BorderLayout.CENTER);
		super.add(buttons, BorderLayout.SOUTH);
		
        SpringUtilities.makeCompactGrid(employee, 4, 2, RegisterScreen.BORDER, 0, 0, 0);
        SpringUtilities.makeCompactGrid(bankPanel, 4, 2, RegisterScreen.BORDER, 0, 0, 0);
		
		// Revalidate and Repaint because the initComponents could be called after
		// The JFrame has been initialized and painted.
		super.revalidate();
		super.repaint(1L);
    }
	
	/**
	 * Generates a new JLabel Object based on the parameters supplied
	 * 
	 * @param text		The text to be used in the JLabel
	 * @param style		The style of the text in the JLabel
	 * @param size		The size of the text in the JLabel
	 * @param color		The color of the text in the JLabel
	 * @param padding	The padding for the text in the JLabel
	 * @return The newly constructed JLabel Object with formatted text
	 */
	private JLabel createLabel(String text, int style, int size, Color color, int padding) {
		if (color == null)
			color = Color.BLACK;
		if (text == null)
			return new JLabel("");
		JLabel temp = new JLabel(text);
		temp.setFont(new Font("Arial", style, size));
		temp.setForeground(color);
		temp.setBorder(new EmptyBorder(padding, padding, padding, padding));
		return temp;
	}
	
	/**
	 * Generates a new JLabel Object based on the parameters supplied
	 * 
	 * @param text	The text to be used in the JLabel
	 * @param style	The style of the text in the JLabel
	 * @param size	The size of the text in the JLabel
	 * @param color	The color of the text in the JLabel
	 * @return The newly constructed JLabel Object with formatted text
	 */
	private JLabel createLabel(String text, int style, int size, Color color) {
		return this.createLabel(text, style, size, color, 0);
	}

	/**
	 * Event called on a JButton action or click
	 * 
	 * @param e The ActionEvent Object associated with this event call
	 */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (!(e.getSource() instanceof JButton))
			return;
		JButton b = (JButton) e.getSource();
		if (b.getName().equals(RegisterScreen.CANCEL))
			this.reference.updateComponents();
		else if (b.getName().equals(RegisterScreen.REGISTER)) {
			this.message("Loading...", false);
			this.loading = true;
			SwingUtilities.invokeLater(new RegisterScreen.BackgroundCheck(this));
		} else
			JOptionPane.showMessageDialog(null, "That button isnt supported yet",
									"Invalid Button", JOptionPane.ERROR_MESSAGE);
    }
	
	/**
	 * BackgroundCheck class used for multi-threaded database querying to prevent
	 * disturbances to the AWT main GUI Thread
	 */
	private class BackgroundCheck implements Runnable {
		
		/**
		 * The class instance value used to store a reference to the RegisterScreen
		 * instance.
		 */
		private final RegisterScreen reference;
		
		/**
		 * Main constructor to instantiate the BackgroundCheck also checks and
		 * sets the reference value.
		 * 
		 * @param reference The RegisterScreen instance to be used as a reference
		 */
		public BackgroundCheck(RegisterScreen reference) {
			if (reference == null)
				throw new IllegalArgumentException("Cannot create an instance of BackgroundCheck using a null reference");
			this.reference = reference;
		}
		
		/**
		 * Called when the Runnable Object is invoked.
		 */
		@Override
		public void run() {
			// this.reference.reference is the LoginScreen Object.
			if (!this.reference.reference.hasAdminPrivileges()) {
				this.reference.loading = false;
				// Calling the message method within LoginScreen will autmatically dispose of the
				// RegisterScreen and display the previous LoginScreen state.
				this.reference.reference.message("Cannot register a user without an admin logged in", true);
				return;
			}
			EmployeeConnection c = new EmployeeConnection(
										this.reference.id.getText(),
										this.reference.first.getText(),
										this.reference.last.getText(),
										this.reference.bank.getText(),
										this.reference.bsb.getText(),
										this.reference.account.getText());
			// push() does value validation for us
			int r = c.push(this.reference.override.isSelected());
			this.reference.loading = false;
			// Unfortunately another case of manual entry is the easiest approach
			// All EmployeeConnection return values are briefly described inside EmployeeConnection.class
			switch (r) {
				case EmployeeConnection.SQL_ERROR:
					SQLException recent = c.getRecentError();
					if (recent == null)
						this.reference.message("Unknown SQL Error occurred", true);
					else
						this.reference.message(recent.getErrorCode() + ": "
												+ recent.getMessage(), true);
					this.reference.id.setText("");
					this.reference.first.setText("");
					this.reference.last.setText("");
					this.reference.bank.setText("");
					this.reference.bsb.setText("");
					this.reference.account.setText("");
					this.reference.id.requestFocus();
					break;
				case EmployeeConnection.DRIVER_ERROR:
					this.reference.message("An unknown Driver error has ocurred", true);
					this.reference.id.setText("");
					this.reference.first.setText("");
					this.reference.last.setText("");
					this.reference.bank.setText("");
					this.reference.bsb.setText("");
					this.reference.account.setText("");
					this.reference.id.requestFocus();
					break;
				case EmployeeConnection.DUPLICATE_BANK:
					this.reference.message("The bank you are trying to add is already associated with the employee", true);
					this.reference.bank.setText("");
					this.reference.bsb.setText("");
					this.reference.account.setText("");
					this.reference.bank.requestFocus();
					break;
				case EmployeeConnection.SUCCESS:
					this.reference.message("Added/Updated Employee and Bank Details", false);
					this.reference.id.setText("");
					this.reference.first.setText("");
					this.reference.last.setText("");
					this.reference.bank.setText("");
					this.reference.bsb.setText("");
					this.reference.account.setText("");
					this.reference.id.requestFocus();
					break;
				case EmployeeConnection.INVALID_ID:
					this.reference.message("Invalid ID (Number >0)", true);
					this.reference.id.setText("");
					this.reference.id.requestFocus();
					break;
				case EmployeeConnection.INVALID_FIRST_NAME:
					this.reference.message("Invalid First Name (String 1<20)", true);
					this.reference.first.setText("");
					this.reference.first.requestFocus();
					break;
				case EmployeeConnection.INVALID_LAST_NAME:
					this.reference.message("Invalid Last Name (String 1<20)", true);
					this.reference.last.setText("");
					this.reference.last.requestFocus();
					break;
				case EmployeeConnection.INVALID_BANK:
					this.reference.message("Invalid Bank Name (String 1<30)", true);
					this.reference.bank.setText("");
					this.reference.bank.requestFocus();
					break;
				case EmployeeConnection.INVALID_BSB_NUMBER:
					this.reference.message("Invalid BSB Number (Number Length=6)", true);
					this.reference.bsb.setText("");
					this.reference.bsb.requestFocus();
					break;
				case EmployeeConnection.INVALID_ACCOUNT_NUMBER:
					this.reference.message("Invalid Account Number (Number >0)", true);
					this.reference.account.setText("");
					this.reference.account.requestFocus();
					break;
				default:
					this.reference.message("Unknown Error Ocurred", true);
					this.reference.id.setText("");
					this.reference.first.setText("");
					this.reference.last.setText("");
					this.reference.bank.setText("");
					this.reference.bsb.setText("");
					this.reference.account.setText("");
					this.reference.id.requestFocus();
					break;
				// Unknown Employee and Duplicate Employee are only returned
				// in pushBank and pushEmployee respectively
			}
		}
	}
}
