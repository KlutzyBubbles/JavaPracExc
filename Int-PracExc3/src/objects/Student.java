package objects;

import java.io.Serializable;

/**
 * File: Student.java
 * Date: 21/08/2017
 * Notes: No variable associated with this class can ever be null, unless a
 * reflection workaround has been implemented, which ultimately defeats the
 * purpose of the validation
 *
 * @version 1.0.0
 * @author Lee Tzilantonis
 */
public class Student implements Serializable {

	/**
	 * The ID that is assigned to the Student Object
	 */
	private final String id;

	/**
	 * Loose first name, last name and course name variables for the student
	 */
	private String first, last, course;

	/**
	 * Main constructor to initialize a full Student Object
	 *
	 * @param id     - The ID of the Student, only set in the constructor
	 * @param first	 - The First Name of the Student
	 * @param last   - The Last Name of the Student
	 * @param course - The Course Name of the Student
	 * @throws IllegalArgumentException - When any of the 4 parameters are null
	 */
	public Student(String id, String first, String last, String course) {
		if (id == null || first == null || last == null || course == null)
			throw new IllegalArgumentException("Cannot create a student with null variables");
		this.id = id;
		this.first = first;
		this.last = last;
		this.course = course;
	}

	/**
	 * Alternate Constructor for the Student Object
	 * Initializes the Student Object with blank first, last and course
	 *
	 * @param id - The ID of the Student, only set in the constructor
	 */
	public Student(String id) {
		this(id, "", "", "");
	}

	/**
	 * Default Constructor for the Student Object
	 * Ultimately i would leave this out, but it is required
	 */
	public Student() {
		this("ABC12345678", "John", "Doe", "Programming Intermediate");
	}

	/**
	 * Gets the Students ID
	 *
	 * @return - The Students ID, never null
	 */
	public String getId() {
		return this.id;
	}

	/**
	 * Compares an ID with the Students ID
	 *
	 * @param id - The ID to compare against
	 * @return TRUE: loose match, FALSE: no match or parameter is null
	 */
	public boolean compare(String id) {
		return id == null ? false : this.id.equalsIgnoreCase(id);
	}

	/**
	 * Gets the Students First Name
	 *
	 * @return - The Students First Name
	 */
	public String getFirstName() {
		return this.first;
	}

	/**
	 * Sets the Students First Name
	 *
	 * @param first - The new First Name of the Student
	 */
	public void setFirstName(String first) {
		if (first == null)
			first = "";
		this.first = first;
	}

	/**
	 * Gets the Students Last Name
	 *
	 * @return - The Students Last Name
	 */
	public String getLastName() {
		return this.last;
	}

	/**
	 * Sets the Students Last Name
	 *
	 * @param first - The new Last Name of the Student
	 */
	public void setLastName(String last) {
		if (last == null)
			last = "";
		this.last = last;
	}

	/**
	 * Gets the Students Course Name
	 *
	 * @return - The Students Course Name
	 */
	public String getCourse() {
		return this.course;
	}

	/**
	 * Sets the Students Course Name
	 *
	 * @param course - The new Course Name of the Student
	 */
	public void setCourse(String course) {
		if (course == null)
			course = "";
		this.course = course;
	}

	/**
	 * Displays the Student Object in a human readable 1 line String
	 *
	 * @return - The String representation of the Student Object
	 */
	@Override
	public String toString() {
		StringBuilder b = new StringBuilder("ID: ");
		b.append(this.getId()).append(", First Name: ");
		b.append(this.getFirstName()).append(", Last Name: ");
		b.append(this.getLastName()).append(", Course: ");
		b.append(this.getCourse());
		return b.toString();
	}

}
