/**
 * @author Lee Tzilantonis
 */
public class Employee {
    
    // All variables relating to an employee
    private int id;
    private String lastName, position;
    private double salary;
    
    /**
     * Constructs the Employee Object using the parameters
     * 
     * @param id        - The Employees ID
     * @param lastName  - The Employees last name
     * @param position  - The Employees position
     * @param salary    - The Employees salary
     */
    public Employee(int id, String lastName, String position, double salary) {
        this.id = id;
        this.lastName = lastName;
        this.position = position;
        this.salary = salary;
    }
    
    /**
     * Constructs the Employee Object using the parameters
     * 
     * @param id        - The Employees ID
     */
    public Employee(int id) {
        this(id, "", "", 0D);
    }
    
    /**
     * Constructs the Employee Object using default parameters
     */
    public Employee() {
        this(0, "", "", 0D);
    }

    /**
     * Gets the Employees ID
     * 
     * @return      - The Employees ID
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the Employees ID
     * 
     * @param id    - The ID to set to
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets the Employees Last Name
     * 
     * @return      - The Employees Last Name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets the Employees Last Name
     * 
     * @param lastName  - The Last Name to set to
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Gets the Employees Position
     * 
     * @return      - The Employees Position
     */
    public String getPosition() {
        return position;
    }

    /**
     * Sets the Employees Position
     * 
     * @param position  - The Position to set to
     */
    public void setPosition(String position) {
        this.position = position;
    }

    /**
     * Gets the Employees Salary (rounded)
     * 
     * @return      - The Employees rounded Salary
     */
    public double getSalary() {
        // Generic rounding line using Math.round()
        return Math.round(this.salary * 100D) / 100D;
    }

    /**
     * Sets the Employees salary and updates the reports public variables
     * 
     * @param salary - The Employees salary
     */
    public void setSalary(double salary) {
        // The values are minused to prevent extra values if the salary is changed
        String p = this.getPosition();
        if (p.equalsIgnoreCase("Manager")) { // Update the manager values
            PayrollReport.manager -= this.salary;
            PayrollReport.manager += salary;
        } else if (p.equalsIgnoreCase("Sales")) { // Update the sales values
            PayrollReport.sales -= this.salary;
            PayrollReport.sales += salary;
        } else if (p.equalsIgnoreCase("Administration")) { // Update the admin values
            PayrollReport.admin -= this.salary;
            PayrollReport.admin += salary;
        }
        // Always update the total
        PayrollReport.total -= this.salary;
        PayrollReport.total += salary;
        this.salary = salary;
    }
    
    /**
     * Turns the Employee Object into a readable String
     * 
     * @return  - Readable String version of the Employee Object
     */
    @Override
    public String toString() {
        // Using string builder because its efficient
        StringBuilder b = new StringBuilder("ID: ");
        b.append(this.getId());
        b.append(", Last: ");
        b.append(this.getLastName());
        b.append(" (");
        b.append(this.getPosition());
        b.append("), Salary $");
        b.append(this.getSalary());
        return b.toString();
    }
    
}
