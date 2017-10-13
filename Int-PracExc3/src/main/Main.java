package main;

import objects.StudentFactory;
import objects.Student;
import utils.SortUtil;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.swing.JOptionPane;
import utils.IOUtil;

/**
 * File: Main.java
 * Date: 21/08/2017
 *
 * @version 1.0.0
 * @author Lee Tzilantonis
 */
public class Main {

	/**
	 * Stores all Student Objects that are created
	 */
	public static List<Student> students = new ArrayList<>();

	/**
	 * Indicates to the main loop whether the program should run another loop
	 * Variable is public to allow external classes to end the program gracefully
	 */
	public static boolean running = true;

	/**
	 * contains the menu text to be displayed to the user
	 */
	public static final String MENU_TEXT = "--STUDENT DETAILS SYSTEM--\n\n"
										   + "1. Input Student Details\n"
										   + "2. Sort and Display by Last Name\n"
										   + "3. Sort and Display by Course\n"
										   + "4. Search by ID\n"
										   + "5. Search by Last Name\n\n"
										   + "6. Save & Exit";

	/**
	 * Main method run when jar is run
	 *
	 * @param args The command line arguments parsed to the program
	 */
	public static void main(String[] args) {
		// Get any previously saved Student Objects
		List<Student> temp = IOUtil.readArray();
		if (temp != null) { // Array can be null if file doesnt exist
			// Set the new Array and notify the user
			Main.students = temp;
			Main.message("Loaded previous students from file");
		} // No need to re-initialize the Array if temp is null
		while (Main.running)
			Main.mainMenu();
	}

	/**
	 * The programs main method to determine what menu option the user has picked
	 */
	public static void mainMenu() {
		// Ask for the initial input
		String input = JOptionPane.showInputDialog(null, Main.MENU_TEXT, "Input", JOptionPane.QUESTION_MESSAGE);
		// Can be null on 2 occassions, cancel or close
		if (input == null) {
			// In both cases we want to end the program
			Main.running = false;
			return;
		}
		// n = whether or not option to sort or search is inverted (reversed)
		boolean n = false;
		// Input ends with ' -' E.g. '2 -' or '3      -'
		if (input.endsWith(" -")) {
			// Remove the ' -' to allow for integer parsing
			input = input.replace(" -", "");
			// State that the option is now negative, although sometimes ignored
			n = true;
		}
		// Defined out of try to allow for continuing code to 'see' the variable
		int num;
		try {
			num = Integer.parseInt(input);
		} catch (NumberFormatException e) {
			Main.error("Please only enter a number for the input");
			return; // Return to the main while which will run this method again
		}
		// Checking for what option was picked
		switch (num) {
			case 1: // Input new Student Details
				// All Student generation is handled by the factory
				StudentFactory f = new StudentFactory();
				// Add the newly created student and notify the user
				Main.students.add(f.getInput().buildStudent());
				Main.message("Student added");
				break;
			case 2: // Sort by last name
				// Sort using the Last Name comparator
				Collections.sort(Main.students, new SortUtil.LastNameCompare());
				// Used for the JOptionPane title
				String by = "Last Name";
				if (n) {
					// Invert the sorted array
					Collections.reverse(Main.students);
					// Add to the title to notify the user
					by += " (Inverted)";
				}
				// Display the Array with the title
				Main.display(by);
				break;
			case 3: // Sort by course name
				// Sort using the Course Name comparator
				Collections.sort(Main.students, new SortUtil.CourseCompare());
				// Used for the JOptionPane title
				by = "Course";
				if (n) {
					// Invert the sorted array
					Collections.reverse(Main.students);
					// Add to the title to notify the user
					by += " (Inverted)";
				}
				// Display the Array with the title
				Main.display(by);
				break;
			case 4: // Search by ID (can be reversed)
				// Invoke the search method, parameters explained in JavaDoc
				Main.search(true, n);
				break;
			case 5: // Search by Last Name (can be reversed)
				// Invoke the search method, parameters explained in JavaDoc
				Main.search(false, n);
				break;
			case 6:
				IOUtil.writeArray(Main.students);
				Main.message("Finished saving students to file");
				Main.running = false;
				break;
			default:
				Main.error("Please only enter a number between 1 and 6");
		}
	}

	/**
	 * Searches the array for the specified term, note that this does not sort the Array but rather
	 * keep the previous sort. Because the task only needed 2 search methods, a boolean to
	 * differentiate seemed OK
	 *
	 * @param id - Whether or not to search by ID, false means Last Name
	 * @param n	 - Whether or not to reverse the resulting Array
	 */
	public static void search(boolean id, boolean n) {
		// Need to build title based on parameters provided
		StringBuilder message = new StringBuilder("Please enter a Last Name or part to search for");
		if (id) // Switch to ID title
			message = new StringBuilder("Please enter an ID or part to search for");
		// Information for the user
		message.append(" (case in-sensative");
		if (n) // More information
			message.append(" & inverted");
		message.append(")");
		// Term outside of while to keep in in the method scope
		String term = "";
		// Term will only be "" if the user clicks OK with a blank input
		while (term.equals("")) {
			// Get input from user
			term = JOptionPane.showInputDialog(null, message);
			// No need for validation, just return back to the menu if null (Cancel or Close)
			if (term == null)
				return;
			else if (term.equals(""))
				Main.error("Please enter a search term");
		}
		// This copy will contain all students found within the criteria
		List<Student> copy = new ArrayList<>();
		// Both require toLowerCase as there is no containsIgnoreCase() method
		for (Student s : Main.students)
			if (id) { // Search by ID
				if (s.getId().toLowerCase().contains(term.toLowerCase()))
					copy.add(s); // ID's match, add them to the copy
			} else if (s.getLastName().toLowerCase().contains(term.toLowerCase()))
				copy.add(s); // Last Name's match, add them to the copy
		// If the user requested the array be inverted, then invert it
		if (n)
			Collections.reverse(copy);
		// Call the display method with Titles to suit the request
		if (id)
			Main.display(copy, "ID", term);
		else
			Main.display(copy, "Last Name", term);
	}

	/**
	 * Displays the static Array of Student Objects, note that the sortBy is purely cosmetic and
	 * doesn't affect how the array is sorted
	 *
	 * @param sortBy - The String to substitute into the title
	 */
	public static void display(String sortBy) {
		// No Students to display
		if (Main.students.isEmpty())
			Main.message("There are no students to list");
		else {
			// Create a StringBuilder with a blank line between the title and the first Student
			StringBuilder b = new StringBuilder("Students Sorted by ");
			b.append(sortBy).append(":\n\n");
			for (Student s : Main.students)
				b.append(s.toString()).append("\n"); // The Student toString method is human readable
			// Display the generated message
			Main.message(b.toString());
		}
	}

	/**
	 * Displays the provided Array of Student Objects, note that both in and term are purely
	 * cosmetic and don't affect the search results
	 *
	 * @param students - The Array of Student Objects to display
	 * @param in       - The variable name that was searched by
	 * @param term     - The term that was used to find the list of Students
	 */
	public static void display(List<Student> students, String in, String term) {
		// No Students to display
		if (students.isEmpty())
			Main.message("There are no students to list with the term " + term + " in their " + in);
		else {
			// Create a StringBuilder with a blank line between the title and the first Student
			StringBuilder b = new StringBuilder("Students containing ").append(term).append(" in their ");
			b.append(in).append(":\n\n");
			for (Student s : students)
				b.append(s.toString()).append("\n"); // The Student toString method is human readable
			// Display the generated message
			Main.message(b.toString());
		}
	}

	/**
	 * Displays a dialog box with an error symbol to the user
	 *
	 * @param message The message to be displayed to the user
	 */
	public static void error(String message) {
		JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.ERROR_MESSAGE);
	}

	/**
	 * Displays a dialog box with an information symbol to the user
	 *
	 * @param message The message to be displayed to the user
	 */
	public static void message(String message) {
		JOptionPane.showMessageDialog(null, message, "Message", JOptionPane.INFORMATION_MESSAGE);
	}

	/**
	 * Checks if the static Array of Student Objects contains a Student with a specified ID
	 *
	 * @param id - The ID to compare against
	 * @return Whether or not a Student with that ID is in the Array
	 */
	public static boolean contains(String id) {
		for (Student s : Main.students)
			if (s.compare(id)) // Compare method in Student Object
				return true;
		// The loop has ended meaning that nothing has been returned up to this point
		return false;
	}

}
