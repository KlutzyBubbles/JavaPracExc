
import javax.swing.JOptionPane;

/**
 *
 * @author Lee Tzilantonis
 * File: StudentRegistration.java
 * Date: 2/03/2017
 * This java programs collects data about 3 students and their course info
 * and displays the total amount in fees all the courses will cost.
 * 
 */
public class StudentRegistration {

    public static void main(String[] args) {
        headings(); // Call the headings method to write headings
        double total = inputStudentDetails(); // Store the students fees in a value names total
        total += inputStudentDetails(); // Add to the total fees with a new students input
        total += inputStudentDetails(); // Same as line above
        
        outputTotalFee(total); // Output the total fees as calculated above
    }
    
    public static void headings() { // Prints the main headings
        System.out.println("--- HOLMESGLEN INSTITUTE ---\n");
        System.out.println("ID\tNAME\t\tCOURSE\t\tFEE\n");
    }
    
    public static double inputStudentDetails() { // Gives the user input boxes to give details about students
        // State variables and their names
        String id, firstName, lastName, course;
        double courseFee;
        
        // Assign a value to the variables using the showInputDialog
        id = JOptionPane.showInputDialog("Enter student id");
        firstName = JOptionPane.showInputDialog("Enter first name");
        lastName = JOptionPane.showInputDialog("Enter last name");
        course = JOptionPane.showInputDialog("Enter course");
        courseFee = Double.parseDouble(JOptionPane.showInputDialog("Enter course fee")); // Parse to a double because showInputDialog returns String
        
        // Use \t to tab indent the text so it is all in-line
        System.out.println(id + "\t" + firstName + " " + lastName + "\t" + course + "\t" + courseFee);
        return courseFee;
    }
    
    public static void outputTotalFee(double totalFees) { // Outputs the total fees given in the parameters
        System.out.println("\nTotal Fees $" + totalFees);
    }
    
}
