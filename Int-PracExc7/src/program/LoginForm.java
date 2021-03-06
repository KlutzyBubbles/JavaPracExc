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
package program;

import gui.LoginScreen;

/**
 * File: LoginForm.java
 * Date: 12/10/2017
 * Notes: N/A
 *
 * @version 1.0.1
 * @author Lee Tzilantonis
 */
public class LoginForm {
    
	/**
	 * Main method used to initiate the LoginScreen JFrame
	 * 
	 * @param args Arguments supplied via the command prompt
	 */
    public static void main(String[] args) {
		// Instanciate LoginScreen as we handle everything within the Object
		// we dont need to store or re-use the Object
        LoginScreen l = new LoginScreen();
		l.updateComponents();
		//l.registerUser();
		/*
		Opening the register screen before the login screen will still not allow the guest to add employees
		because the register screen needs to be opened within a login screen instance, every register
		screen has a user variable to reference and validate that the current user has permission.
		*/
    }
	
	/**
	 * Ideally i would add a debug() method which can be turned on and off for debugging purposes
	 * but within the time frame the implementation of my debug methods wouldn't be justified
	 */
    
}
