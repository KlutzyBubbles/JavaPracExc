package objects;

import java.util.regex.Pattern;
import main.Main;
import javax.swing.JOptionPane;

/**
 * File: StudentFactory.java
 * Date: 21/08/2017
 * Notes: This class is essentially a Student sandbox before finalizing variables and creating the
 * object
 *
 * @version 1.0.0
 * @author Lee Tzilantonis
 */
public class StudentFactory {

	/**
	 * All variables that are used to construct a Student Object
	 * ID is no longer final as the Student Object hasn't been constructed yet
	 */
	private String id, first, last, course;

	/**
	 * Main constructor to initialize a full StudentFactory Object
	 *
	 * @param id     - The ID of the Student, only set in the constructor
	 * @param first	 - The First Name of the Student
	 * @param last   - The Last Name of the Student
	 * @param course - The Course Name of the Student
	 */
	public StudentFactory(String id, String first, String last, String course) {
		this.id = id;
		this.first = first;
		this.last = last;
		this.course = course;
	}

	/**
	 * Default Constructor for the Student Object
	 */
	public StudentFactory() {
		// Perfectly safe to have null values in a StudentFactory Object
		this(null, null, null, null);
	}

	/**
	 * Prompts the user with all the information for editing the new Students values, including
	 * their current value if it exists
	 *
	 * @return StudentFactory instance for method appending
	 */
	public StudentFactory getInput() {
		this.editId().editFirstName().editLastName().editCourse();
		return this;
	}

	/**
	 * Checks whether a variable within the Factory is already set or not
	 *
	 * @param variable - The name of the variable to check
	 * @return Whether or not the specified variable is set, false if variable name is incorrect
	 */
	public boolean isSet(String variable) {
		// All checks are for null or ""
		if (variable.equalsIgnoreCase("id"))
			return !(this.id == null
					 || this.id.equals(""));
		else if (variable.equalsIgnoreCase("first"))
			return !(this.first == null
					 || this.first.equals(""));
		else if (variable.equalsIgnoreCase("last"))
			return !(this.last == null
					 || this.last.equals(""));
		else if (variable.equalsIgnoreCase("course"))
			return !(this.course == null
					 || this.course.equals(""));
		// Variable doesnt exist, default to false
		return false;
	}

	/**
	 * Builds a student object with all the variables associated with the Object
	 *
	 * @return Student Object from variables defined, or null if validation or null checks fail
	 */
	public Student buildStudent() {
		// A variable is null, cannot create a Student Object
		if (this.id == null || this.first == null || this.last == null || this.course == null)
			return null;
		// ID is invalid, cannot create a Student Object
		if (this.id.length() < 3)
			return null;
		// First Name is invalid, cannot create a Student Object
		if (!this.validateName(this.first))
			return null;
		// Last Name is invalid, cannot create a Student Object
		if (!this.validateName(this.last))
			return null;
		// Course Name is invalid, cannot create a Student Object
		if (this.course.length() < 5)
			return null;
		// Return a new Student Object
		return new Student(this.id, this.first, this.last, this.course);
	}

	/**
	 * Forces the user to enter valid information to add a new Student
	 *
	 * @return Student Object from variables defined, never null
	 */
	public Student forceBuildStudent() {
		Student s = null;
		// I am 99% sure there is no way to get stuck in this loop
		while (s == null) {
			s = this.buildStudent();
			if (s == null) // Something went wrong, re-enter data
				this.getInput();
		}
		return s;
	}

	/**
	 * Validates a first or last name against the RegEx "^[A-Za-z ,.'-]{3,}$"
	 *
	 * @param name - The name to be validated
	 * @return Whether or not the name is 'valid'
	 */
	public boolean validateName(String name) {
		if (name == null) // Cannot validate a null name
			return false;
		// RegEx pattern to match against
		String rx = "^[A-Za-z ,.'-]{2,}$";
		// Return whether or not the pattern can be found in the string
		return Pattern.compile(rx).matcher(name).find();
	}

	// EDIT METHODS WILL BE LIGHTLY DOCUMENTED AS THEY ARE VERY SIMILAR \\
	/**
	 * Prompts the user to forcefully edit the ID, the current ID (if any) is displayed to the user
	 *
	 * @return StudentFactory instance for method appending
	 */
	public StudentFactory editId() {
		// Prepare the title for the user
		String message = "Please enter an ID for the student";
		if (isSet("id")) // The ID is set, display it to the user
			message += ", it is currently: " + this.id;
		String temp = "";
		// Loop until an ID is entered
		while (temp.equals("")) {
			// Get the user input
			temp = JOptionPane.showInputDialog(null, message);
			// Validate the input for the requested variable and notify of errors
			// Always reset to "" if invalid to force the loop
			if (temp == null) {
				Main.error("Please enter an ID");
				temp = "";
			} else if (temp.length() < 3) {
				Main.error("Please enter an ID with atleast 3 characters");
				temp = "";
			} else if (Main.contains(temp)) {
				Main.error("Please enter a unique ID");
				temp = "";
			}
		}
		// Set the new variable
		this.id = temp;
		// Return an instance of itself to append methods
		return this;
	}

	/**
	 * Prompts the user to forcefully edit the First Name, the current First Name (if any) is
	 * displayed to the user
	 *
	 * @return StudentFactory instance for method appending
	 */
	public StudentFactory editFirstName() {
		// Prepare the title for the user
		String message = "Please enter a First Name for the student";
		if (isSet("first")) // The First Name is set, display it to the user
			message += ", it is currently: " + this.first;
		String temp = "";
		// Loop until a First Name is entered
		while (temp.equals("")) {
			// Get the user input
			temp = JOptionPane.showInputDialog(null, message);
			// Validate the input for the requested variable and notify of errors
			// Always reset to "" if invalid to force the loop
			if (!this.validateName(temp)) {
				Main.error("Please enter a valid First Name, Valid names are alphabetic strings"
						   + " with a length of 2 or more characters, extra characters include "
						   + "' ', \"'\", ',', '-', '.'");
				temp = "";
			}
		}
		// Set the new variable
		this.first = temp;
		// Return an instance of itself to append methods
		return this;
	}

	/**
	 * Prompts the user to forcefully edit the Last Name, the current Last Name (if any) is
	 * displayed to the user
	 *
	 * @return StudentFactory instance for method appending
	 */
	public StudentFactory editLastName() {
		// Prepare the title for the user
		String message = "Please enter a Last Name for the student";
		if (isSet("last")) // The Last Name is set, display it to the user
			message += ", it is currently: " + this.last;
		String temp = "";
		// Loop until a Last Name is entered
		while (temp.equals("")) {
			// Get the user input
			temp = JOptionPane.showInputDialog(null, message);
			// Validate the input for the requested variable and notify of errors
			// Always reset to "" if invalid to force the loop
			if (!this.validateName(temp)) {
				Main.error("Please enter a valid Last Name");
				temp = "";
			}
		}
		// Set the new variable
		this.last = temp;
		// Return an instance of itself to append methods
		return this;
	}

	/**
	 * Prompts the user to forcefully edit the Course Name, the current Course Name (if any) is
	 * displayed to the user
	 *
	 * @return StudentFactory instance for method appending
	 */
	public StudentFactory editCourse() {
		// Prepare the title for the user
		String message = "Please enter a Course for the student";
		if (isSet("course")) // The Course Name is set, display it to the user
			message += ", it is currently: " + this.course;
		String temp = "";
		// Loop until a Course Name is entered
		while (temp.equals("")) {
			// Get the user input
			temp = JOptionPane.showInputDialog(null, message);
			// Validate the input for the requested variable and notify of errors
			// Always reset to "" if invalid to force the loop
			if (temp == null) {
				Main.error("Please enter a Course name");
				temp = "";
			} else if (temp.length() < 5) {
				Main.error("Please enter a Course name with atleast 5 characters");
				temp = "";
			}
		}
		// Set the new variable
		this.course = temp;
		// Return an instance of itself to append methods
		return this;
	}

}
