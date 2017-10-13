package hotel;

import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 * File: HotelBookings.java
 * Date: 01/06/2017
 * @version 1.0.0
 * @author Lee Tzilantonis
 */
public class HotelBookings {

    /**
     * Stores all HotelRoom Objects that are created
     */
    public static List<HotelRoom> rooms = new ArrayList<HotelRoom>();
    
    /**
     * Indicates to the main loop whether the program should run another loop
     */
    public static boolean running = true;

    /**
     * Indicates the maximum room number the user is allowed to enter
     */
    public static final int MAX_ROOM_NUMBER = 600;
    
    /**
     * contains the menu text to be displayed to the user
     */
    public static final String MENU_TEXT = "--HOTEL RENTAL SYSTEM--\n\n"
            + "1. Choose a room type\n"
            + "2. Room rates information\n"
            + "3. Rooms currently booked\n\n"
            + "4. Exit";

    /**
     * Main method run when jar is run
     * 
     * @param args The command line arguments parsed to the program
     */
    public static void main(String[] args) {
        while (HotelBookings.running) {
            HotelBookings.mainMenu();
        }
    }

    /**
     * The programs main method to determine what menu option the user has picked
     */
    public static void mainMenu() {
        String input = JOptionPane.showInputDialog
                    (null, HotelBookings.MENU_TEXT, "Input", JOptionPane.QUESTION_MESSAGE);
        if (input == null) {
            HotelBookings.running = false;
            return;
        }
        int num;
        try {
            num = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            HotelBookings.error("Please only enter a number for the input");
            return;
        }
        if (num == 1) {
            HotelBookings.roomDetails();
        } else if (num == 2) {
            HotelBookings.displayRates();
        } else if (num == 3) {
            HotelBookings.listBooked();
        } else if (num == 4) {
            HotelBookings.running = false;
        } else {
            HotelBookings.error("Please only enter a number between 1 and 4");
        }
    }

    /**
     * Prompts the user with the required inputs to book a HotelRoom Object
     */
    public static void roomDetails() {
        int temp = HotelBookings.askIsSuite();
        if (temp < 0)
            return;
        boolean suite = temp == 1;
        int num = -1;
        while (num < 1 || num > HotelBookings.MAX_ROOM_NUMBER) {
            String input = JOptionPane.showInputDialog
                        (null, "Enter room number", "Input", JOptionPane.QUESTION_MESSAGE);
            if (input == null)
                return;
            try {
                num = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                HotelBookings.error("Please only enter a number for the input");
                num = -1;
                continue;
            }
            if (num < 1 || num > HotelBookings.MAX_ROOM_NUMBER) {
                HotelBookings.error("Please only enter a number between 1 and " + HotelBookings.MAX_ROOM_NUMBER);
                num = -1;
            }
            if (HotelBookings.contains(suite, num)) {
                HotelBookings.error("That room is already booked, please try another room type or number");
                return;
            }
        }
        int index = HotelBookings.rooms.size();
        if (suite) {
            HotelBookings.rooms.add(new Suite(num));
        } else {
            HotelBookings.rooms.add(new HotelRoom(num));
        }
        HotelBookings.display(index);
    }

    /**
     * Prompts the user with the required inputs to find out whether the user
     * wants a suite or a regular hotel room
     * 
     * -1 = Error
     * 0  = Normal Room
     * 1  = Suite
     * 
     * @return - Whether or not the user wants a suite
     */
    public static int askIsSuite() {
        String text = "1. Normal Hotel room\n"
                + "2. Suite\n\n"
                + "Choose room type";
        String input = JOptionPane.showInputDialog
                        (null, text, "Input", JOptionPane.QUESTION_MESSAGE);
        if (input == null)
            return -1;
        int num;
        try {
            num = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            HotelBookings.error("Please only enter a number for the input");
            return HotelBookings.askIsSuite();
        }
        if (num == 1) {
            return 0;
        } else if (num == 2) {
            return 1;
        } else {
            HotelBookings.error("Please only enter the numbers 1 or 2");
            return HotelBookings.askIsSuite();
        }
    }

    /**
     * Prompts the user with information about the booking rates defined
     */
    public static void displayRates() {
        StringBuilder b = new StringBuilder("NIGHTLY ROOM RATES\n\n");
        b.append("Rooms numbered 1-").append(HotelRoom.RATE_CHANGE - 1);
        b.append(" = $").append(HotelRoom.BELOW).append("\n");
        b.append("All other rooms are $").append(HotelRoom.ABOVE);
        b.append("\n\nSuites have an extra $").append(Suite.INCREASE);
        b.append(" surcharge applied");
        HotelBookings.message(b.toString());
    }

    /**
     * Prompts the user with all information about all HotelRoom Objects that are booked
     */
    public static void listBooked() {
        StringBuilder b = new StringBuilder("--Rooms Booked out--\n");
        if (HotelBookings.rooms.isEmpty()) {
            b.append("\nNo Rooms are Booked!");
        } else {
            for (HotelRoom r : HotelBookings.rooms) {
                b.append(r.toString()).append("\n");
            }
        }
        HotelBookings.message(b.toString());
    }

    /**
     * Prompts the user with information about the HotelRoom object at the specified index
     * 
     * @param index 
     */
    public static void display(int index) {
        if (index < HotelBookings.rooms.size() && index >= 0) {
            StringBuilder b = new StringBuilder("--Rooms Booked out--\n");
            b.append(HotelBookings.rooms.get(index).toString());
            HotelBookings.message(b.toString());
        } else {
            HotelBookings.error("Unknown index selected to display!");
        }
    }
    
    /**
     * Checks whether or not the specified room has already been booked
     * 
     * @param suite Whether or not the room is a suite
     * @param roomNo The number of the room being booked
     * @return - Whether or not the specified room has been booked
     */
    public static boolean contains(boolean suite, int roomNo) {
        if (HotelBookings.rooms.isEmpty())
            return false;
        for (HotelRoom r : HotelBookings.rooms) {
            if (r instanceof Suite) {
                if (suite)
                    if (r.getRoomNo() == roomNo)
                        return true;
            } else {
                if (!suite)
                    if (r.getRoomNo() == roomNo)
                        return true;
            }
        }
        return false;
    }

    // NOTE: Both of the below methods were copied from Prac Exc 4
    
    /**
     * Displays a dialog box with an error symbol to the user
     *
     * @param message The message to be displayed to the user
     */
    public static void error(String message) {
        JOptionPane.showMessageDialog
                    (null, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Displays a dialog box with an information symbol to the user
     *
     * @param message The message to be displayed to the user
     */
    public static void message(String message) {
        JOptionPane.showMessageDialog
                    (null, message, "Message", JOptionPane.INFORMATION_MESSAGE);
    }

}
