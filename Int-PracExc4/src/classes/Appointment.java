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

import java.io.Serializable;

/**
 * File: Appointment.java
 * Date: 08/09/2017
 * Notes: N/A
 *
 * @version 1.0.0
 * @author Lee Tzilantonis
 */
public class Appointment implements Serializable {

	/**
	 * The name of the Appointment Object
	 */
	private String name;

	/**
	 * The day and hour of the Appointment Object
	 */
	private int day, hour;

	/**
	 * Main and Default constructor which instantiates the Appointment Object
	 *
	 * @param name - The name that will be assigned to the Object
	 * @param day  - The day of the month the Appointment is on
	 * @param hour - The hour of the Appointment
	 * @throws IllegalArgumentException - Only thrown if a null name is parsed
	 */
	public Appointment(String name, int day, int hour) throws IllegalArgumentException {
		if (name == null)
			throw new IllegalArgumentException("Cannot create an appointment with a null name");
		this.name = name;
		this.day = day;
		this.hour = hour;
	}

	/**
	 * Sets the name of the Appointment Object (Please note the name can be blank or "")
	 * Restrictions:
	 * - Not Null
	 * - Smaller than or equal to 20 characters
	 *
	 * @param name - The name that will be assigned to the Object
	 * @throws IllegalArgumentException - Only thrown if a null name is parsed
	 */
	public void setName(String name) throws IllegalArgumentException {
		if (name == null)
			throw new IllegalArgumentException("Cannot set the appointment name to NULL");
		if (name.length() > 20)
			name = name.substring(0, 19);
		this.name = name;
	}

	/**
	 * Sets the day of the Appointment Object
	 * Restrictions:
	 * - Greater than or equal to 1
	 * - Smaller than or equal to 31
	 *
	 * @param day - The day of the month to change the Appointment to
	 */
	public void setDay(int day) {
		day = day < 1 ? 1 : day > 31 ? 31 : day;
		this.day = day;
	}

	/**
	 * Sets the hour of the Appointment Object
	 * Restrictions:
	 * - Greater than or equal to 1
	 * - Smaller than or equal to 24
	 *
	 * @param hour - The hour of the day to change the Appointment to
	 */
	public void setHour(int hour) {
		hour = hour < 1 ? 1 : hour > 24 ? 24 : hour;
		this.hour = hour;
	}

	/**
	 * Gets the name of the appointment
	 *
	 * @return - The name of the Appointment or "NULL" if null
	 */
	public String getName() {
		if (this.name == null)
			this.setName("NULL");
		if (this.name.length() > 20)
			this.setName(this.name.substring(0, 19));
		return this.name;
	}

	/**
	 * Gets the day of the Appointment
	 *
	 * @return - The day of the Appointment within restrictions set by setDay(Integer)
	 */
	public int getDay() {
		this.day = this.day < 1 ? 1 : this.day > 31 ? 31 : this.day;
		return this.day;
	}

	/**
	 * Gets the hour of the Appointment
	 *
	 * @return - The hour of the Appointment within the restrictions set by setHour(Integer)
	 */
	public int getHour() {
		this.hour = this.hour < 1 ? 1 : this.hour > 31 ? 31 : this.hour;
		return this.hour;
	}

	/**
	 * Returns a string representation of the Appointment Object formatted as a HTML table row
	 *
	 * @return - String representation of the Appointment Object formatted as a HTML table row
	 */
	@Override
	public String toString() {
		StringBuilder b = new StringBuilder("<tr><td>");
		b.append(this.getName()).append("</td><td>");
		b.append(this.getDay()).append("</td><td>");
		b.append(this.getHour()).append("</td></tr>");
		return b.toString();
	}

}
