/*
 * @author Lee Tzilantonis
 * @name Patient.java
 * @date 23/03/2017
 * @desc Stores information about the Patient as an Object
 */
public class Patient {
    
    // Default variables used throughout the class
    private static final String DEFAULT_NAME = "Doe";
    private static final int DEFAULT_MEDI = 999999;
    private static final int DEFAULT_AGE = 1;
    private static final Address DEFAULT_ADDRESS = new Address();
    
    private int medicareNum, age;
    private String lastName;
    private Address address;
    
    // The default Patient Constructor
    public Patient() {
        this(DEFAULT_MEDI, DEFAULT_AGE, DEFAULT_NAME, DEFAULT_ADDRESS);
    }
    
    /**
     * Created a new Patient object using the parameters supplied
     * 
     * @param medicareNum - The Patients medicare number
     * @param age - The patients age
     * @param lastName - The patients last name
     * @param address  - The patients address in the form of an Address Object
     */
    public Patient(int medicareNum, int age, String lastName, Address address) {
        this.medicareNum = medicareNum;
        this.age = age;
        this.lastName = lastName;
        this.address = address;
    }

    // Returns the medicare number and NEVER an invalid value
    public int getMedicareNum() {
        if (this.medicareNum < 1)
            this.medicareNum = DEFAULT_MEDI;
        return this.medicareNum;
    }

    // Returns the Address Object and NEVER a null value
    public Address getAddress() {
        if (this.address == null)
            this.setAddress(DEFAULT_ADDRESS);
        return this.address;
    }
    
    // Returns the last name and NEVER a null value
    public String getLastName() {
        if (this.lastName == null)
            this.lastName = DEFAULT_NAME;
        return this.lastName;
    }
    
    // Returns the age and NEVER an invalid value
    public int getAge() {
        if (this.age < 1)
            this.age = DEFAULT_AGE;
        return this.age;
    }
    
    // Returns whether or not the Patient is an adult (18 or over)
    public boolean isAdult() {
        return this.getAge() >= 18;
    }
    
    // Sets the medicare number, but never to an invalid value
    public void setMedicareNum(int medicareNum) {
        this.medicareNum = medicareNum < 1 ? this.medicareNum : medicareNum;
    }
    
    // Sets the medicare number, but never to an invalid value
    public void setAge(int age) {
        this.age = age < 1 ? this.age : age;
    }
    
    // Sets the last name, but never to a null value
    public void setLastName(String lastName) {
        this.lastName = lastName == null ? this.lastName : lastName;
    }
    
    // Sets the address via an Address Object, but never to a null value
    public void setAddress(Address address) {
        this.address = address == null ? this.address : address;
    }
    
    // Sets the address via suburb and postcode, but never to an invalid value
    public void setAddress(String suburb, int postcode) {
        this.setAddress(new Address(suburb, postcode));
    }
    
    /**
     * Returns the formatted text representation of the Patient Object
     * 
     * @return - The formatted String
     */
    @Override // Overrides the inheritet method inherited from the Object class
    public String toString() {
        return "\nMedicare No: " + this.getMedicareNum()
                    + ", Name: " + this.getLastName()
                    + ", Age: " + this.getAge()
                    + "\nAddress: " + this.getAddress().getSuburb()
                    + ", " + this.getAddress().getPostcode() + "\n";
        // I would have used a StringBuilder as i find it easier and faster but
        // for such a small project i didnt think it was needed
    }
    
}
