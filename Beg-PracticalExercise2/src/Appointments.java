import javax.swing.JOptionPane;

/*
 * @author Lee Tzilantonis
 * @name Appointments.java
 * @date 23/03/2017
 * @desc The main class used in collecting Patient information and displaying it
 */
public class Appointments {

    private static final boolean debug = true;
    
    /**
     * Main method called when running the file
     * 
     * @param args - The arguments specified when running the file
     */
    public static void main(String[] args) {
        // Getting the data for all the Patients
        JOptionPane.showMessageDialog(null, "Click OK to continue to enter the details of Patient #1"
                                            + "\nEntering incorrect details will require you to enter ALL fields again");
        Patient p1 = inputPatientDetails();
        JOptionPane.showMessageDialog(null, "Click OK to continue to enter the details of Patient #2"
                                            + "\nEntering incorrect details will require you to enter ALL fields again");
        Patient p2 = inputPatientDetails();
        JOptionPane.showMessageDialog(null, "Click OK to continue to enter the details of Patient #3"
                                            + "\nEntering incorrect details will require you to enter ALL fields again");
        Patient p3 = inputPatientDetails();
        
        // Printing the Adults header
        String text = "APPOINTMENTS\n\nAdults\n";
        debug("Text before adult: " + text);
        // Prints only the Patients that are 18 or over (adults)
        if (p1.isAdult()) {
            text += p1.toString();
        }
        if (p2.isAdult()) {
            text += p2.toString();
        }
        if (p3.isAdult()) {
            text += p3.toString();
        }
        debug("Text after adult: " + text);
        // Printing the Children Header
        text += "\nChildren\n";
        debug("Text before children: " + text);
        // Print only the Patients that are under 18 (Children)
        if (!p1.isAdult()) {
            text += p1.toString();
        }
        if (!p2.isAdult()) {
            text += p2.toString();
        }
        if (!p3.isAdult()) {
            text += p3.toString();
        }
        debug("Text after children: " + text);
        // Display the formatted results
        JOptionPane.showMessageDialog(null, text);
    }
    
    /**
     * Returns a patient object based on the values supplied by the user input
     * 
     * @return The Patient Object created (Should contain relatively valid values)
     */
    public static Patient inputPatientDetails() {
        debug("inputPatientDetails() Call");
        // The temp variable is used for holding the string input before it has been validated
        String temp = JOptionPane.showInputDialog(null, "Please enter the patients medicare number");
        debug("Temp MediNum: " + temp);
        if (!isInt(temp)) { // Not a number so cant be a medicare number
            debug("MediNum not Integer");
            JOptionPane.showMessageDialog(null, "Please enter a number as the patients medicare number");
            // Returning the method means that the method will restart, effectively an infinite loop until the user puts the correct input
            return inputPatientDetails();
        } // Due to the return, there is no need for an else
        int mediNum = Integer.parseInt(temp); // The parsed int after it has been validated
        if (mediNum < 1) { // Secondary validation to make sure the value is greater than 0
            debug("MediNum smaller than 1");
            JOptionPane.showMessageDialog(null, "Please enter a valid medicare number");
            return inputPatientDetails();
        }
        // Because the lastName is a string there is no need to hold it using the temp variable
        String lastName = JOptionPane.showInputDialog(null, "Please enter their last name");
        debug("lastName: " + lastName);
        if (lastName == null || lastName.equals("")) { // Checks that the lastName isnt null or empty
            debug("Last Name is null or empty");
            JOptionPane.showMessageDialog(null, "Please enter the patients last name");
            return inputPatientDetails();
        }
        // The following is the same as the medicare number except the integer is defined as 'age'
        temp = JOptionPane.showInputDialog(null, "Please enter their age");
        debug("Temp Age: " + temp);
        if (!isInt(temp)) {
            debug("Age not Integer");
            JOptionPane.showMessageDialog(null, "Please enter a number as their age");
            return inputPatientDetails();
        }
        int age = Integer.parseInt(temp);
        if (age < 1) {
            debug("Age smaller than 1");
            JOptionPane.showMessageDialog(null, "Please enter a valid age");
            return inputPatientDetails();
        }
        debug("FINISH inputPatientDetails()");
        // Return the newly constructed patient object using the inputAddresDetails() as the Address
        return new Patient(mediNum, age, lastName, inputAddressDetails());
    }
    
    /**
     * Returns an Address object based on the values supplied by the user
     * 
     * @return The Address Object (Should contain relatively valid values)
     */
    public static Address inputAddressDetails() {
        debug("inputAddressDetails() Call");
        // The suburb input from the user
        String suburb = JOptionPane.showInputDialog(null, "Please enter their suburb");
        debug("Suburb: " + suburb);
        if (suburb == null || suburb.equals("")) { // Checks if the suburb entered is null or empty
            debug("Suburb is null or empty");
            JOptionPane.showMessageDialog(null, "Please enter a suburb for the patient");
            // Returns the method essentially repeating the inputAddressDetails() until the user gets the inputs right
            return inputAddressDetails();
        }
        // The temp string of the postcode
        String temp = JOptionPane.showInputDialog(null, "Please enter their postcode");
        debug("Temp Postcode: " + temp);
        if (!isInt(temp)) { // The postcode entered isnt a number
            debug("Postcode not Integer");
            JOptionPane.showMessageDialog(null, "Please enter a number as the patients postcode");
            return inputAddressDetails();
        } else if (Integer.parseInt(temp) < Address.MIN_POSTCODE) { // The postcode entered isnt valid
            debug("Postcode smaller than " + Address.MIN_POSTCODE);
            JOptionPane.showMessageDialog(null, "Please enter a valid postcode");
            return inputAddressDetails();
        }
        debug("FINISH inputAddressDetails()");
        // Return the newly constructed Address Object from the users inputs
        return new Address(suburb, Integer.parseInt(temp));
    }
    
    /**
     * Checks if the String is an integer
     * 
     * @param num - the string you want to check
     * @return - whether or not the string is an integer
     */
    public static boolean isInt(String num) {
        debug("isInt() Call");
        try {
            Integer.parseInt(num);
        } catch (NumberFormatException e) { // NumberFormatException is thrown by parseInt() if the String cannot be parsed
            debug("isInt() RETURN: false");
            return false;
        }
        debug("isInt() RETURN: true");
        return true;
    }
    
    /**
     * Prints a message to the console if debug is set to true
     * Used in the Appointments class to supply messages useful for debugging and testing the program
     * 
     * @param message - The message to be printed
     */
    public static void debug(String message) {
        if (debug)
            System.out.println(message);
    }
    
}
