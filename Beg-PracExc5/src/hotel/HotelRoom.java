package hotel;

/**
 * File: HotelRoom.java
 * Date: 01/06/2017
 * @version 1.0.0
 * @author Lee Tzilantonis
 */
public class HotelRoom {

    /**
     * Indicates at which point the nightly room rate increases
     */
    public static final int RATE_CHANGE = 300;
    
    /**
     * Indicates the lower nightly rate of a room
     */
    public static final double BELOW = 69.95D;
    
    /**
     * Indicates the higher nightly rate of a room
     */
    public static final double ABOVE = 89.95D;

    /**
     * The number of the current HotelRoom Object
     */
    private final int roomNo;
    
    /**
     * The nightly rate for the current HotelRoom Object
     */
    private double rate;

    /**
     * The main constructor for the HotelRoom Object
     * 
     * @param roomNo The number of the room to be booked
     */
    public HotelRoom(int roomNo) {
        this.roomNo = roomNo;
        if (roomNo < HotelRoom.RATE_CHANGE) {
            this.rate = HotelRoom.BELOW;
        } else {
            this.rate = HotelRoom.ABOVE;
        }
    }

    /**
     * Gets the room number
     * 
     * @return - The room number
     */
    public int getRoomNo() {
        return roomNo;
    }

    /**
     * Gets the nightly room rate to 2 decimal places
     * 
     * @return - The nightly room rate
     */
    public double getRate() {
        return (double) (Math.round(this.rate * 100D) / 100D);
    }

    /**
     * Sets the nightly rate of the room
     * 
     * @param rate The new nightly rate
     */
    public void setRate(double rate) {
        this.rate = rate;
    }

    /**
     * Increases the nightly rate of the room
     * 
     * @param rate The amount to add to the current rate
     */
    public void increaseRate(double rate) {
        this.rate += rate;
    }

    /**
     * Gets the user-friendly String representation of the HotelRoom Object
     * 
     * @return - The String representation of the HotelRoom Object
     */
    @Override
    public String toString() {
        StringBuilder b = new StringBuilder("\nRoom No. ");
        b.append(this.getRoomNo()).append("\nNightly Rate $");
        b.append(this.getRate());
        return b.toString();
    }

}
