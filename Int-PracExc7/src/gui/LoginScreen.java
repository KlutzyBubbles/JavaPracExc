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

import database.LoginConnection;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.sql.SQLException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.SwingUtilities;
import utils.SpringUtilities;

/**
 * File: LoginScreen.java
 * Date: 12/10/2017
 * Notes: Char[] from getPassword() is converted to a String for simplicity, it
 * isn't the best option in a real world situation, but in a real world situation
 * the password stored in the table would be encrypted.
 *
 * @version 1.0.1
 * @author Lee Tzilantonis
 */
public class LoginScreen extends JFrame implements ActionListener {
    
	/**
	 * Values used for naming and recognizing buttons and clicks
	 */
    private static final String LOGIN = "Login";
    private static final String CANCEL = "Cancel";
	private static final String REGISTER = "Register User";
	private static final String LOGOUT = "Logout";
    
	/**
	 * Values used for JFrame Size and Padding
	 */
    private static final int FRAME_WIDTH = 300;
    private static final int FRAME_HEIGHT = 200;
    private static final int BORDER = 20;
    
	/**
	 * JLabel used to store the result of the login attempt
	 */
    private JLabel result;
	
	/**
	 * JTextField used as the username input
	 */
    private JTextField username;
	
	/**
	 * JPasswordField used as the password input
	 */
    private JPasswordField password;
	
	/**
	 * Invalid attempts at logging in
	 */
	private int attempt = 0;
	
	/**
	 * Current state of user login, references LoginConnection static Integer variables
	 * -1: Logged out
	 * Other: Refer to LoginConnection.class
	 */
	private int login = -1;
	
	/**
	 * Indicates the current loading state of the Object, this alters the state of certain UI elements
	 */
	private boolean loading = false;
    
	/**
	 * Height to adjust components by to compensate for the JFrame's frame
	 */
    private final int frameAdjust;
    
	/**
	 * Default constructor to instantiate the LoginScreen and initialize
	 * contained components
	 */
    public LoginScreen() {
        super();
        super.pack();
        this.frameAdjust = super.getInsets().top + super.getInsets().bottom;
        super.setTitle("Login Screen");
        super.setBounds(100, 100, LoginScreen.FRAME_WIDTH, LoginScreen.FRAME_HEIGHT);
        super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        super.setResizable(false);
        super.setLayout(new FlowLayout());
        super.setVisible(true);
    }
	
	/**
	 * Displays a message on the LoginScreen frame
	 * 
	 * @param message	The message to display in String format (HTML Supported)
	 * @param error		Whether or not this message is an error (for formatting)
	 */
	public void message(String message, boolean error) {
		if (this.result != null) {
			this.result.setText("<html><p width=" + LoginScreen.FRAME_WIDTH
									+ " align=center>" + message);
			if (error) {
				this.result.setForeground(Color.RED);
				if (this.attempt <= 3 && this.attempt > 0)
					this.result.setText(this.result.getText()
									+ "<br>Failed Attempts: " + this.attempt);
			} else
				this.result.setForeground(Color.BLACK);
			this.updateComponents();
		}
	}
    
	/**
	 * Initiates or re-initiates the components within the JFrame while reserving
	 * any input values and Object states
	 */
    public void updateComponents() {
		// Get previous username and password values
		String uv = this.username == null ? "" : this.username.getText();
		String pv = this.password == null ? "" : new String(this.password.getPassword());
		int height = 0;
		super.getContentPane().removeAll();
		super.setLayout(new FlowLayout());
		// Only initialize the result Object if it isn't already initialized
		if (this.result == null) {
			this.result = new JLabel("");
			this.result.setFont(new Font("Arial", Font.BOLD, 14));
			this.result.setSize(LoginScreen.FRAME_WIDTH, 20);
		}
		if (this.attempt == 3) {
			this.attempt++;
			this.message("Too many invalid attempts", true);
			return;
		}
		int reserved = this.result.getPreferredSize().height;
		
		// Submit button (AKA Login button)
        JButton submit = new JButton(LoginScreen.LOGIN);
        submit.setPreferredSize(new Dimension((LoginScreen.FRAME_WIDTH / 2) - (LoginScreen.BORDER * 2),
                                                                                  submit.getPreferredSize().height));
        submit.setMnemonic(KeyEvent.VK_S);
        submit.setName(LoginScreen.LOGIN);
        submit.addActionListener(this);
		submit.setEnabled(this.attempt < 3 && !this.loading && (this.login <= 0));
		
		// Cancel button
        JButton cancel = new JButton(LoginScreen.CANCEL);
        cancel.setPreferredSize(new Dimension((LoginScreen.FRAME_WIDTH / 2) - (LoginScreen.BORDER * 2),
                                                                                  cancel.getPreferredSize().height));
        cancel.setMnemonic(KeyEvent.VK_C);
        cancel.setName(LoginScreen.CANCEL);
        cancel.addActionListener(this);
		
		// Store the amount reserved for the components created above
        reserved += submit.getPreferredSize().height;
		reserved += this.frameAdjust + LoginScreen.BORDER;
		
		// Login Form
        JPanel form = new JPanel(new SpringLayout());
        form.setPreferredSize(new Dimension(LoginScreen.FRAME_WIDTH,
                                            LoginScreen.FRAME_HEIGHT
                                            - reserved));
		
		// Login form Labels and Inputs
        JLabel u = new JLabel("Username: ");
        form.add(u);
        this.username = new JTextField(uv);
		this.username.setEnabled(this.attempt < 3 && this.login < LoginConnection.ACCESS_USER && !this.loading);
        u.setLabelFor(this.username);
        form.add(this.username);
        JLabel p = new JLabel("Password: ");
        form.add(p);
        this.password = new JPasswordField(pv);
		this.password.setEnabled(this.attempt < 3 && this.login < LoginConnection.ACCESS_USER && !this.loading);
        p.setLabelFor(this.password);
        form.add(this.password);
		// Compress form
        SpringUtilities.makeCompactGrid(form, 2, 2, LoginScreen.BORDER, 0, 0, 0);
		
		height += reserved;
		height += form.getPreferredSize().height;
		
		// Add all components in order
        super.add(form);
		super.add(this.result);
        super.add(cancel);
        super.add(submit);
		
		if (this.login >= LoginConnection.ACCESS_USER) {
			if (this.login == LoginConnection.ACCESS_ADMIN) {
				JButton register = new JButton(LoginScreen.REGISTER);
				register.setPreferredSize(new Dimension(LoginScreen.FRAME_WIDTH - (LoginScreen.BORDER * 2),
																						  register.getPreferredSize().height));
				register.setMnemonic(KeyEvent.VK_C);
				register.setName(LoginScreen.REGISTER);
				register.addActionListener(this);
				height += register.getPreferredSize().height;
				super.add(register);
			}
			JButton logout = new JButton(LoginScreen.LOGOUT);
			logout.setPreferredSize(new Dimension(LoginScreen.FRAME_WIDTH - (LoginScreen.BORDER * 2),
																					  logout.getPreferredSize().height));
			logout.setMnemonic(KeyEvent.VK_C);
			logout.setName(LoginScreen.LOGOUT);
			logout.addActionListener(this);
			height += logout.getPreferredSize().height + LoginScreen.BORDER;
			super.add(logout);
		}
		
		// Make 'enter' hit submit
        super.getRootPane().setDefaultButton(submit);
		
		super.setBounds(100, 100, LoginScreen.FRAME_WIDTH, height);
		
		// Revalidate and Repaint because the initComponents could be called after
		// The JFrame has been initialized and painted.
		super.revalidate();
		super.repaint(1L);
    }

	/**
	 * Initiates the RegisterScreen Object and opens it within the current JFrame
	 */
	private void registerUser() {
		// If registerUser() is called before initComponents, some references dont work.
		// Originally added because testing the 'no admin user' error for register didn't work when
		// opening the register interface before the login screen.
		if (this.result == null)
			this.updateComponents();
		super.getContentPane().removeAll();
		super.setLayout(new BorderLayout());
		super.setBounds(100, 100, 800, 400);
		RegisterScreen r = new RegisterScreen(this);
		super.add(r, BorderLayout.CENTER);
		r.updateComponents();
		// Revalidate and Repaint because the registerUsers can only be called after
		// The JFrame has been initialized and painted.
		super.revalidate();
		super.repaint(1L);
	}
	
	/**
	 * Checks whether the current user (if any) has administrator privileges.
	 * 
	 * @return Whether or not the current user has administrator privileges
	 */
	public boolean hasAdminPrivileges() {
		return this.login >= LoginConnection.ACCESS_ADMIN;
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
		if (b.getName().equals(LoginScreen.CANCEL))
			System.exit(0);
		else if (b.getName().equals(LoginScreen.LOGIN)) {
			if (this.attempt >= 3)
				JOptionPane.showMessageDialog(null, "Too many attempts!",
									"Invalid Request", JOptionPane.ERROR_MESSAGE);
			else {
				this.message("Loading...", false);
				this.loading = true;
				// Using invokeLater(Runnable) as it executes the code on a seperate thread
				// To allow the execution of repaint() on the main AWT thread to display "Loading..."
				SwingUtilities.invokeLater(new BackgroundCheck(this));
			}
		} else if (b.getName().equals(LoginScreen.LOGOUT)) {
			this.login = -1;
			this.attempt = 0;
			this.message("Logged Out", false);
		} else if (b.getName().equals(LoginScreen.REGISTER)) {
			if (this.login < LoginConnection.ACCESS_ADMIN)
				this.message("Cannot use that button", true);
			else {
				this.registerUser();
			}
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
		 * The class instance value used to store a reference to the LoginScreen
		 * instance.
		 */
		private final LoginScreen reference;
		
		/**
		 * Main constructor to instantiate the BackgroundCheck also checks and
		 * sets the reference value.
		 * 
		 * @param reference The LoginScreen instance to be used as a reference
		 */
		public BackgroundCheck(LoginScreen reference) {
			if (reference == null)
				throw new IllegalArgumentException("Cannot create an instance of BackgroundCheck using a null reference");
			this.reference = reference;
		}
		
		/**
		 * Called when the Runnable Object is invoked.
		 */
		@Override
		public void run() {
			// Private variables can be referenced because of the class being
			// contained within the LoginScreen class.
			LoginConnection l = new LoginConnection(this.reference.username.getText(),
										new String(this.reference.password.getPassword()));
			int r = l.getAccountStatus();
			if (r > LoginConnection.SQL_ERROR && r < LoginConnection.NOT_ENABLED)
				this.reference.attempt++;
			this.reference.login = r;
			this.reference.loading = false;
			// Unfortunately there was no easy alternative to manually setting
			// checking and focusing on inputs and return values
			// All LoginConnection return values are briefly described inside LoginConnection.class
			switch (r) {
				case LoginConnection.SQL_ERROR:
					SQLException recent = l.getRecentError();
					if (recent == null)
						this.reference.message("Unknown SQL Error occurred", true);
					else
						this.reference.message(recent.getErrorCode() + ": "
												+ recent.getMessage(), true);
					this.reference.username.setText("");
					this.reference.password.setText("");
					this.reference.attempt = 0;
					this.reference.username.requestFocus();
					break;
				case LoginConnection.INVALID_USERNAME:
					this.reference.message("User doesn't exist", true);
					this.reference.username.setText("");
					this.reference.password.setText("");
					this.reference.username.requestFocus();
					break;
				case LoginConnection.INVALID_PASSWORD:
					this.reference.message("Invalid Password", true);
					this.reference.password.setText("");
					this.reference.password.requestFocus();
					break;
				case LoginConnection.NOT_ENABLED:
					this.reference.message("Account is not enabled", true);
					this.reference.username.setText("");
					this.reference.password.setText("");
					this.reference.username.requestFocus();
					break;
				case LoginConnection.ACCESS_USER:
					this.reference.message("Regular User", false);
					this.reference.password.setText("");
					this.reference.attempt = 0;
					this.reference.username.requestFocus();
					break;
				case LoginConnection.ACCESS_ADMIN:
					this.reference.message("Admin User", false);
					this.reference.password.setText("");
					this.reference.attempt = 0;
					this.reference.username.requestFocus();
					break;
				default:
					this.reference.message("An unknown error occurred", true);
					this.reference.username.setText("");
					this.reference.password.setText("");
					this.reference.attempt = 0;
					this.reference.username.requestFocus();
					break;
			}
		}
	}
}
