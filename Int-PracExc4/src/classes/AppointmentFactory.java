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
package classes;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.GregorianCalendar;

/**
 * File: AppointmentFactory.java
 * Date: 08/09/2017
 * Notes: N/A
 *
 * @version 1.0.0
 * @author Lee Tzilantonis
 */
public class AppointmentFactory {

	/**
	 * The filename to be used when loading and saving Objects
	 */
	private static final String FILE = "appointment-%d-%d.bin";

	/**
	 * Minimum, Default and Maximum year available to store
	 */
	public static final int MIN_YEAR = 1970;
	public static final int DEFAULT_YEAR = 2000;
	public static final int MAX_YEAR = 2100;

	/**
	 * List of all Appointment Objects for the loaded month
	 */
	private List<Appointment> apps;

	/**
	 * The month to assign Appointment Objects too, also used to name the FILE variable
	 */
	private int month = Calendar.JANUARY;

	/**
	 * The year to assign Appointment Objects too, also used to name the FILE variable
	 */
	private int year = AppointmentFactory.DEFAULT_YEAR;

	/**
	 * Main constructor used to instantiate the AppointmentFactory Object
	 *
	 * @param month - The Month of the year to assign to the Object, this is defined by the Calendar
	 *              class constants which start at 0 and end at 11
	 * @param year  - The year to assign to the Object, within the bounds set by the Minimum and
	 *              Maximum year class constants
	 */
	public AppointmentFactory(int month, int year) {
		this.apps = new ArrayList<>();
		this.setMonth(month);
		this.setYear(year);
		this.load();
	}

	/**
	 * Secondary constructor used to instantiate the AppointmentFactory Object using the Systems
	 * current year
	 *
	 * @param month - The Month of the year to assign to the Object, this is defined by the Calendar
	 *              class constants which start at 0 and end at 11
	 */
	public AppointmentFactory(int month) {
		this(month, Calendar.getInstance().get(Calendar.YEAR));
	}

	/**
	 * Default constructor used to instantiate the AppointmentFactory Object using the Systems
	 * current year and month
	 */
	public AppointmentFactory() {
		this(Calendar.getInstance().get(Calendar.MONTH));
	}

	/**
	 * Gets the month assigned to the AppointmentFactory Object
	 *
	 * @return - The month linked to the AppointmentFactory Object
	 */
	public int getMonth() {
		return this.month;
	}

	/**
	 * Gets the year assigned to the AppointmentFactory Object
	 *
	 * @return - The year linked to the AppointmentFactory Object
	 */
	public int getYear() {
		return this.year;
	}

	/**
	 * Sets the month associated with the AppointmentFactory Object, defined by the Calendar class
	 * constants which range from 0 to 11
	 * NOTE: This resets the List of Appointment Objects and it is recommended to call commit()
	 * before invoking this method and load() after
	 *
	 * @param month - The month of the year to set the AppointmentFactory to target defined by the
	 *              Calendar class constants
	 */
	public final void setMonth(int month) {
		if (month < Calendar.JANUARY)
			month = Calendar.JANUARY;
		if (month > Calendar.DECEMBER)
			month = Calendar.DECEMBER;
		this.apps = new ArrayList<>();
		this.month = month;
	}

	/**
	 * Sets the year associated with the AppointmentFactory Object, within the bounds set by the
	 * Minimum and Maximum year class constants
	 * NOTE: This resets the List of Appointment Objects and it is recommended to call commit()
	 * before invoking this method and load() after
	 *
	 * @param year - The year to set the AppointmentFactory to target, within the bounds set by the
	 *             Minimum and Maximum year class constants
	 */
	public final void setYear(int year) {
		if (year < AppointmentFactory.MIN_YEAR)
			year = AppointmentFactory.MIN_YEAR;
		if (year > AppointmentFactory.MAX_YEAR)
			year = AppointmentFactory.MAX_YEAR;
		this.apps = new ArrayList<>();
		this.year = year;
	}

	/**
	 * Gets the amount of days in the current month associated with the AppointmentFactory Object
	 *
	 * @return - The amount of days in the AppointmentFactory Object's current month
	 */
	public int getDays() {
		return new GregorianCalendar(this.getYear(), this.getMonth(), 1)
		 .getActualMaximum(Calendar.DAY_OF_MONTH);
	}

	/**
	 * Validates that the String parameter is within the constraints to be a valid day of the month.
	 * These restrictions are being an Integer, and being between 1 and the value of getDays()
	 *
	 * @param day - The String value to be validated
	 * @return - Whether or not the String provided is a valid day
	 */
	public boolean validateDay(String day) {
		try {
			return this.validateDay(Integer.parseInt(day));
		} catch (NumberFormatException e) {
			return false;
		}
	}

	/**
	 * Validates that the Integer parameter is within the constraints to be a valid day of the month
	 * These restrictions are being between 1 and the value of getDays()
	 *
	 * @param day - The Integer value to be validated
	 * @return - Whether or not the Integer provided is a valid day
	 */
	public boolean validateDay(int day) {
		return day >= 1 && day <= this.getDays();
	}

	/**
	 * Validates that the String parameter is within the constraints to be a valid hour of the day.
	 * These restrictions are being an Integer, and being between 1 and 24
	 *
	 * @param hour - The String value to be validated
	 * @return - Whether or not the String provided is a valid hour
	 */
	public boolean validateHour(String hour) {
		try {
			return this.validateHour(Integer.parseInt(hour));
		} catch (NumberFormatException e) {
			return false;
		}
	}

	/**
	 * Validates that the Integer parameter is within the constraints to be a valid hour of the day.
	 * These restrictions are being between 1 and 24
	 *
	 * @param hour - The Integer value to be validated
	 * @return - Whether or not the Integer provided is a valid hour
	 */
	public boolean validateHour(int hour) {
		return hour >= 1 && hour <= 24;
	}

	/**
	 * Validates that the String parameter is within the constraints to be a valid name.
	 * These restrictions are being not null or empty, having a length of no more than 20 characters
	 * and being unique to the Appointment names currently stored for this month
	 *
	 * @param name - The String value to be validated
	 * @return - Whether or not the String provided is a valid name
	 */
	public boolean validateName(String name) {
		return name != null && !name.equals("") && name.length() <= 20 && this.contains(name) == -1;
	}

	/**
	 * Validates that the Appointment is within the constraints to be valid or safe to store in the
	 * list of Appointment Objects for the month
	 * These restrictions are being not null and all values within the Object pass an individual
	 * check
	 *
	 * @param a - The Appointment to be validated
	 * @return - Whether or not the Appointment Object provided is valid or safe to store in the
	 *         list of Appointment Objects
	 */
	public boolean validateAppointment(Appointment a) {
		if (a == null)
			return false;
		if (!this.validateName(a.getName())
			|| !this.validateDay(a.getDay())
			|| !this.validateHour(a.getHour()))
			return false;
		return true;
	}

	/**
	 * Validates that the Object is within the constraints to be a valid Appointment Object and safe
	 * to store in the list of Appointment Objects for the month
	 * These restrictions are being not null, instance of Appointment Object and all values within
	 * the Object pass an individual check
	 *
	 * @param o - The Object to be validated
	 * @return - Whether or not the Object provided is a valid Appointment Object and safe to store
	 *         in the list of Appointment Objects
	 */
	public boolean validateAppointment(Object o) {
		if (o == null || !(o instanceof Appointment))
			return false;
		return this.validateAppointment((Appointment) o);
	}

	/**
	 * Checks if the list of Appointments contains a name matching the String provided
	 *
	 * @param name - The name of the Appointment Object being tested
	 * @return - Whether or not the list of Appointments contains an Appointment with the specified
	 *         name
	 */
	public int contains(String name) {
		if (name == null)
			return -1;
		for (int i = 0; i < this.apps.size(); i++)
			if (this.apps.get(i).getName().equals(name))
				return i;
		return -1;
	}

	/**
	 * Searches and filters out Appointments that match the search term provided
	 *
	 * @param term          - The term of an Appointment name to search for
	 * @param caseSensative - Whether or not the search should be case sensitive
	 * @return - A list containing all the Appointments found with a name that contains the search
	 *         term or an empty Array if none are found
	 */
	public List<Appointment> search(String term, boolean caseSensative) {
		List<Appointment> temp = new ArrayList<>();
		new ArrayList<>(this.apps).stream().forEach((Appointment a) -> {
			if (caseSensative) {
				if (a.getName().contains(term))
					temp.add(a);
			} else if (a.getName().toLowerCase().contains(term.toLowerCase()))
				temp.add(a);
		});
		return temp;
	}

	/**
	 * Gets a copy of the Appointments for the current AppointmentFactory Objects month
	 *
	 * @return - A List of all Appointment Objects for the current AppointmentFactory Objects month
	 */
	public List<Appointment> getAppointments() {
		return new ArrayList<>(this.apps);
	}

	/**
	 * Adds an Appointment Object constructed by specified values to the list of Appointment Objects
	 * if the values meet the criteria set by the validate<ITEM>() methods
	 *
	 * @param name - The name of the Appointment Object to be created
	 * @param day  - The day of the Appointment Object to be created
	 * @param hour - The hour of the Appointment Object to be created
	 * @return - TRUE: The Appointment Object was created and added, FALSE: The Appointment Object
	 *         couldn't be created due to invalid arguments
	 */
	public boolean addAppointment(String name, int day, int hour) {
		if (!this.validateName(name) || !this.validateDay(day) || !this.validateHour(hour))
			return false;
		Appointment temp;
		try {
			temp = new Appointment(name, day, hour);
		} catch (IllegalArgumentException e) {
			return false;
		}
		return this.addAppointment(temp);
	}

	/**
	 * Adds an Appointment Object to the list of Appointment Objects if the values meet the criteria
	 * set by the validate<ITEM>() methods
	 *
	 * @param a - The Appointment Object to be added
	 * @return - TRUE: The Appointment Object was added, FALSE: The Appointment Object
	 *         couldn't be added due to invalid arguments
	 */
	public boolean addAppointment(Appointment a) {
		if (a == null)
			return false;
		if (!this.validateName(a.getName()) || !this.validateDay(a.getDay()))
			return false;
		return this.apps.add(a) && this.commit();
	}

	/**
	 * Commit the current List of Appointment Objects to a binary file
	 *
	 * @return - Whether or not the File creation and writing was successful
	 */
	public final boolean commit() {
		FileOutputStream f = null;
		ObjectOutputStream o = null;
		try {
			f = new FileOutputStream(String.format(AppointmentFactory.FILE,
												   this.getYear(),
												   this.getMonth()));
			o = new ObjectOutputStream(f);
			o.writeObject(this.apps);
		} catch (IOException e) {
			System.err.println("Failed to save file: " + String.format(AppointmentFactory.FILE,
																	   this.getYear(),
																	   this.getMonth()));
			return false;
		} finally {
			try {
				if (o != null)
					o.close();
				if (f != null)
					f.close();
			} catch (IOException e) {
				System.err.println("Failed to close streams");
				return false;
			}
		}
		return true;
	}

	/**
	 * Load the List of Appointment Objects from a binary file
	 *
	 * @return - Whether or not the File loading and reading was successful.
	 *         NOTE: this doesn't always mean that an error occurred
	 */
	public final boolean load() {
		FileInputStream f = null;
		ObjectInputStream o = null;
		try {
			f = new FileInputStream(String.format(AppointmentFactory.FILE,
												  this.getYear(),
												  this.getMonth()));
			o = new ObjectInputStream(f);
			List<Appointment> list = (List<Appointment>) o.readObject();
			if (list != null)
				for (Appointment a : list)
					if (this.validateAppointment(a))
						this.apps.add(a);
			return true;
		} catch (ClassCastException | ClassNotFoundException e) {
			File s = new File(String.format(AppointmentFactory.FILE,
											this.getYear(),
											this.getMonth()));
			s.delete();
			System.err.println("Failed to cast file, deleting...");
			return false;
		} catch (IOException e) {
			System.out.println("[SOFT] Failed to load file: "
							   + String.format(AppointmentFactory.FILE,
											   this.getYear(),
											   this.getMonth()));
			return false;
		} finally {
			try {
				if (o != null)
					o.close();
				if (f != null)
					f.close();
			} catch (IOException e) {
				System.err.println("Failed to close streams");
				return false;
			}
		}
	}

	/**
	 * Formats all of the current List of Appointment Objects data into a multidimensional Array of
	 * Strings for use with a JTable Object
	 *
	 * @param a - The List of Appointment Objects to be formatted
	 * @return - The formatted multidimensional Array of Strings
	 */
	public static String[][] formatData(List<Appointment> a) {
		if (a == null || a.isEmpty())
			return new String[0][0];
		String[][] temp = new String[a.size()][3];
		int count = 0;
		for (Appointment app : a) {
			if (app == null)
				continue;
			temp[count][0] = app.getName();
			temp[count][1] = String.valueOf(app.getDay());
			temp[count][2] = String.valueOf(app.getHour());
			count++;
		}
		return temp;
	}

	/**
	 * Counts how many Appointment Objects are currently within the List of Appointment Objects
	 *
	 * @return - How many Appointment Objects are associated with the AppointmentFactory instance
	 */
	public int countAppointments() {
		return this.apps.size();
	}

	/**
	 * Deletes the Appointment Object with a name that matches the one specified
	 *
	 * @param name - The name of the Appointment to be deleted
	 * @return - Whether or not the Appointment Object was removed from the Array AND committed to
	 *         file
	 */
	public boolean delete(String name) {
		if (this.contains(name) == -1)
			return false;
		this.apps.remove(this.contains(name));
		return this.commit();
	}

	/**
	 * Returns a string representation of the AppointmentFactory Object formatted as a HTML table
	 *
	 * @return - String representation of the AppointmentFactory Object formatted as a HTML table
	 */
	public String toString() {
		// Only time we sort the array so no need for a method
		this.apps.sort(new Comparator<Appointment>() {
			@Override
			public int compare(Appointment a, Appointment b) {
				if (a.getDay() == b.getDay()) {
					if (a.getHour() == b.getHour())
						return a.getName().compareTo(b.getName());
					return a.getHour() - b.getHour();
				}
				return a.getDay() - b.getDay();
			}
		});
		// In the case of this program, the toString() method is only used in conjunction with a
		// JOptionPane which supports HTML formatting
		StringBuilder b = new StringBuilder("<html><table>");
		b.append("<tr><th width='100px' align='left'><font size=+1>Name</font></th>");
		b.append("<th width='50px' align='left'><font size=+1>Day</font></th>");
		b.append("<th width='50px' align='left'><font size=+1>Hour</font></th></tr><tr></tr>");
		for (Appointment a : this.apps)
			b.append(a.toString());
		b.append("</table></html>");
		return b.toString();
	}

}
