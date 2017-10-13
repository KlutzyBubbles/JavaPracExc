/*
 * @author Lee Tzilantonis
 * @name Address.java
 * @date 23/03/2017
 * @desc Stores information about the Address as an Object
 */
public class Address {
    
    // Default variables used throughout the class
    private static final String DEFAULT_SUBURB = "Melbourne";
    private static final int DEFAULT_POSTCODE = 3000;
    public static final int MIN_POSTCODE = 1000; // This one is public as it is used in the Appointments class also
    
    private String suburb;
    private int postcode;
    
    public Address() {
        this(DEFAULT_SUBURB, DEFAULT_POSTCODE); // Calls another contructor of the same object with the default values
    }
    
    public Address (String suburb, int postcode) {
        // Sets both values to the ones specified (no validation)
        this.suburb = suburb;
        this.postcode = postcode;
    }

    // Returns the suburb, and NEVER a null value
    public String getSuburb() {
        if (this.suburb == null)
            this.suburb = DEFAULT_SUBURB;
        return this.suburb;
    }
    
    // Returns the postcode, and NEVER an invalid value
    public int getPostcode() {
        if (this.postcode < MIN_POSTCODE)
            this.postcode = DEFAULT_POSTCODE;
        return this.postcode;
    }

    // Sets the suburb, but never to a null value
    public void setSuburb(String suburb) {
        this.suburb = suburb == null ? this.suburb : suburb;
    }

    // Sets the postcode but never to an invalid value
    public void setPostcode(int postcode) {
        this.postcode = postcode < MIN_POSTCODE ? this.postcode : postcode;
    }
}
