
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

/**
 * @author Lee Tzilantonis
 */
public class PayrollReport {

    // List of all employees that have been loaded
    public static List<Employee> employees = new ArrayList<Employee>();
    // The current employee that is being loaded
    public static Employee current;
    
    // The location of the INPUT file
    public static final String FILE = "Employees.txt";
    // The location for the OUTPUT file
    public static final String OUTPUT = "PayrollReport.txt";
    
    // Public reader objects for multiple methods to access
    public static FileReader fr;
    public static BufferedReader br;
    
    // Public variables that are changed as employees are changed
    public static double manager, sales, admin, total;
    
    /**
     * The main method only calls on other methods.
     * 
     * @param args  - Command line arguments if any
     */
    public static void main(String[] args) {
        readFile();
        displayAll();
        displayTotal();
        printReport();
    }
    
    /**
     * Reads the FILE and outputs the employees into the employees list
     */
    public static void readFile() {
        // Ingore previous file readers and close them.
        closeReaders();
        try {
            // Initialising the reader Objects
            fr = new FileReader(FILE);
            br = new BufferedReader(fr);
            // Read the contents of the file (or throws IOException)
            loopContents();
        } catch (FileNotFoundException e) {
            System.out.println(FILE + " file not found");
        } catch (IOException e) {
            // The IOException caught here is actually onyl thrown in the loopContents() method
            System.out.println("IOException when reading files");
        } finally { // Executed even if there is an Exception
            closeReaders();
        }
    }
    
    /**
     * Loops through the contents of the open BufferedReader and saves data
     * 
     * @throws IOException  - Thrown if the file reading encounters an IOException
     */
    public static void loopContents() throws IOException {
        if (fr == null || br == null) { // Cant read anything if the readers are null
            System.out.println("loopContents call when readers are null");
            return; // No need to continue, the program will display no results
        }
        // The current line being read
        String line;
        // The line number being read
        int count = 0;
        while ((line = br.readLine()) != null) { // Making sure the line being read exists
            count++;
            int data = count % 4; // Data is in sets of 4 so the modulus comes in handy
            // 1: id, 2: lastName, 3: position, 0: salary
            if (data == 1) { // This is the line for the ID (also the first for another Employee Obnject)
                try {
                    current = new Employee(Integer.parseInt(line));
                } catch (NumberFormatException e) {
                    // The text on the line isnt an Integer
                    System.out.println("Employee ID not number");
                    break;
                }
            } else { // No longer the first for the current Employee
                // HIGHLY unlikely it will ever occur but always check first before using an Object
                if (current == null) {
                    System.out.println("No current employee");
                    break;
                }
                if (data == 2) { // This is the line for lastName
                    current.setLastName(line);
                } else if (data == 3) { // This is the line for Position
                    current.setPosition(line);
                } else { // This is the last line or the Salary
                    try {
                        current.setSalary(Double.parseDouble(line));
                    } catch (NumberFormatException e) {
                        // The text on the line isnt a Double
                        System.out.println("Employee Salary not double");
                        break;
                    }
                    // Because its the last variable for the employee, the
                    // Employee Object isnow built and can be added to the list
                    employees.add(current);
                    // The current is set to null to prevent any unknown errors occuring
                    current = null;
                }
            }
        }
    }
    
    /**
     * Displays all of the employees and their info in one big JOptionPane message dialog
     */
    public static void displayAll() {
        if (employees.isEmpty()) { // No employees to display
            JOptionPane.showMessageDialog(null, "There are no employees to display");
        } else {
            // Using StringBuilder because its efficient
            StringBuilder b = new StringBuilder("EMPLOYEE LIST\n\n");
            for (Employee e : employees ) { // Loop and display on seperate lines
                b.append(e.toString());
                b.append("\n");
            }
            JOptionPane.showMessageDialog(null, b.toString());
        }
    }
    
    /**
     * Displays the total of each category employee in a JOptionPane dialog
     */
    public static void displayTotal() {
        // Using StringBuilder because tis efficient
        StringBuilder b = new StringBuilder("PAYROLL SUMMARY\n\n");
        b.append("Managers\tSales\tAdmin\n");
        b.append("$");
        // Always rounding the values only when displaying them for maximum accuracy when calculating
        b.append(round(manager));
        b.append("\t$");
        b.append(round(sales));
        b.append("\t$");
        b.append(round(admin));
        // JTextArea fixes the \t problem, i left the background white for simplicity
        JOptionPane.showMessageDialog(null, new JTextArea(b.toString()));
    }
    
    /**
     * Prints a full report on the values set and notifies the user of the file
     */
    public static void printReport() {
        if (employees.isEmpty()) { // No employees for a report
            System.out.println("Cannot create report with no employees");
            return; // Could be replaced with an else but then the indentation would be too much
        }
        // Initialise to null to allow checking in finally
        FileWriter fw = null;
        BufferedWriter bw = null;
        PrintWriter pw = null;
        try {
            // Initialise writer Objects
            fw = new FileWriter(OUTPUT);
            bw = new BufferedWriter(fw);
            pw = new PrintWriter(bw);
            // Print the report line by line (rounding all values)
            pw.println("Total payroll $" + round(total));
            // Average pay is never stored because calculating each time its updated
            pw.println("Average pay $" + round(total / employees.size()));
            pw.println();
            pw.println("Total pay for:");
            pw.println("Managers $" + round(manager));
            pw.println("Sales Staff $" + round(sales));
            pw.println("Admin Staff $" + round(admin));
        } catch (IOException e) {
            System.out.println("Error writing to file " + OUTPUT);
        } finally {
            try {
                if (pw != null)
                    pw.close();
                if (bw != null)
                    bw.close();
                if (fw != null)
                    fw.close();
            } catch (IOException e) {
                System.out.println("Error closing writing streams");
            }
        }
        // Notify the user that the OUTPUT file has been created as a full report
        JOptionPane.showMessageDialog(null, OUTPUT + " has been created with the full report");
    }
    
    /**
     * Rounds a double down to 2 decimal places, NOT up.
     * 
     * @param num   - The number that will be rounded
     * @return      - The rounded number
     */
    public static double round(double num) {
        // Generic rounding line using Math.round()
        return Math.round(num * 100D) / 100D;
    }
    
    /**
     * Closes all readers if they are not null
     */
    public static void closeReaders() {
        try {
            // Prevents any IOExceptions if the stream is already closed
            if (fr != null) 
                if (!fr.ready())
                    return; // Streams have already been closed
            if (br != null)
                br.close();
            if (fr != null)
                fr.close();
        } catch (IOException e) {
            System.out.println("Error closing reading streams");
        }
    }
    
}
