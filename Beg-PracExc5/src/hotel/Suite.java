package hotel;

/**
 * File: Suite.java
 * Date: 01/06/2017
 * @version 1.0.0
 * @author Lee Tzilantonis
 */
public class Suite extends HotelRoom {

    /**
     * Indicates the amount to increase the HotelRoom fee by
     */
    public static final double INCREASE = 40D;

    /**
     * The main constructor for the Suite Object
     * 
     * @param roomNo The number of the room to be booked
     */
    public Suite(int roomNo) {
        super(roomNo);
        super.increaseRate(Suite.INCREASE);
    }

    /**
     * Gets the user-friendly String representation of the Suite Object
     * 
     * @return - The String representation of the Suite Object
     */
    @Override
    public String toString() {
        StringBuilder b = new StringBuilder(super.toString());
        b.append("\nSuite Surcharge (Incl.) $").append(Suite.INCREASE);
        return b.toString();
    }

}
