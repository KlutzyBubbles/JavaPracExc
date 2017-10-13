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

import gui.AddAppointment;
import gui.FrontEnd;
import gui.SearchAppointments;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * File: MeetingCalendar.java
 * Date: 08/09/2017
 * Notes: N/A
 *
 * @version 1.0.0
 * @author Lee Tzilantonis
 */
public class MeetingCalendar {

	/**
	 * All GUI variables used for holding until disposed
	 */
	private static FrontEnd menu;
	private static AddAppointment add;
	private static SearchAppointments search;

	/**
	 * MeetingCalendar method used to initiate the program
	 *
	 * @param args - The command line arguments
	 */
	public static void main(String[] args) {
		MeetingCalendar.openMenu();
	}

	/**
	 * Opens the Menu GUI and closes the Add and Search GUI's
	 */
	public static void openMenu() {
		MeetingCalendar.dispose();
		MeetingCalendar.menu = new FrontEnd();
	}

	/**
	 * Opens the Add GUI and closes the Menu and Search GUI's
	 */
	public static void openAdd() {
		MeetingCalendar.dispose();
		MeetingCalendar.add = new AddAppointment();
	}

	/**
	 * Opens the Search GUI and closes the Add and Menu GUI's
	 */
	public static void openSearch() {
		MeetingCalendar.dispose();
		MeetingCalendar.search = new SearchAppointments();
	}

	/**
	 * Closes or Disposes all open GUI's known to the class
	 */
	private static void dispose() {
		if (MeetingCalendar.menu != null) {
			MeetingCalendar.menu.dispose();
			MeetingCalendar.menu = null;
		}
		if (MeetingCalendar.add != null) {
			MeetingCalendar.add.dispose();
			MeetingCalendar.add = null;
		}
		if (MeetingCalendar.search != null) {
			MeetingCalendar.search.dispose();
			MeetingCalendar.search = null;
		}
	}

	/**
	 * Class: MeetingCalendar.CloseHandler
	 * Date: 08/09/2017
	 * Notes: Class is used to open the menu when the window is closed instead of stopping the
	 * program.
	 * This could be an anonymous class but is more organized due to multiple references
	 *
	 * @version 1.0.0
	 * @author Lee Tzilantonis
	 */
	public static class CloseHandler extends WindowAdapter {

		/**
		 * Invoked when the registered frame is closing
		 *
		 * @param e - The WindowEvent Object defined by the JFrame
		 */
		@Override
		public void windowClosing(WindowEvent e) {
			MeetingCalendar.openMenu();
		}

	}

}
