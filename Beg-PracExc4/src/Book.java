/**
 * File: BookStore.java
 * Date: 24/05/2017
 * @author Lee Tzilantonis
 * @version 1.0.0
 */
public class Book {
    
    /**
     * All class dependant final variables used for defaults and easy(er) language changing
     */
    private static final int DEFAULT_ID = 1000;
    private static final String DEFAULT_TITLE = "Book Title";
    private static final String DEFAULT_AUTHOR = "Smith";
    private static final double DEFAULT_PRICE = 10.0D;
    private static final String ID_TITLE = "ISBN: ";
    private static final String TITLE_NAME = "Title: ";
    private static final String AUTHOR_TITLE = "Author: ";
    private static final String PRICE_TITLE = "Price $";
    
    /**
     * The ID of the book (ISBN)
     */
    private final int id;
    
    /**
     * The title and author of the book
     */
    private String title, author;
    
    /**
     * The price of the book
     */
    private double price;
    
    /**
     * The Main constructor for the Book Object
     * 
     * @param id The ID of the book
     * @param title The title of the book
     * @param author The books Author
     * @param price The price of the book
     */
    public Book(int id, String title, String author, double price) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.price = price;
    }
    
    /**
     * The default constructor for the Book Object
     * NOTE: Calls hard coded defaults set at the start of the class
     */
    public Book() {
        this(Book.DEFAULT_ID, Book.DEFAULT_TITLE, Book.DEFAULT_AUTHOR, Book.DEFAULT_PRICE);
    }

    /**
     * Gets the ID of the book
     * 
     * @return -  The ID of the book
     */
    public int getId() {
        return this.id;
    }
    
    /**
     * Gets the title of the book, null is replaced with default value
     * 
     * @return - The title of the book
     */
    public String getTitle() {
        if (this.title == null)
            this.setTitle(Book.DEFAULT_TITLE);
        return this.title;
    }

    /**
     * Sets the title of the book
     * 
     * @param title The new title that the book is to be set to
     */
    public void setTitle(String title) {
        if (title == null)
            return;
        this.title = title;
    }

    /**
     * Gets the books author, null is replaced with default value
     * 
     * @return - The author of the book
     */
    public String getAuthor() {
        if (this.author == null)
            this.setAuthor(Book.DEFAULT_AUTHOR);
        return this.author;
    }

    /**
     * Sets the books author
     * 
     * @param author The new author of the book that is to be set
     */
    public void setAuthor(String author) {
        if (author == null)
            return;
        this.author = author;
    }

    /**
     * Gets the price of the book
     * 
     * @return - The price of the book
     */
    public double getPrice() {
        return this.price;
    }

    /**
     * Sets the price of the book
     * 
     * @param price The new price that the book is to be set to
     */
    public void setPrice(double price) {
        this.price = price;
    }
    
    /**
     * Gets the user-friendly String representation of the Book Object
     * 
     * @return - The String representation of the Book Object
     */
    @Override
    public String toString() {
        StringBuilder b = new StringBuilder(Book.ID_TITLE);
        b.append(this.getId());
        b.append(", ").append(Book.TITLE_NAME);
        b.append(this.getTitle());
        b.append(" (").append(Book.AUTHOR_TITLE);
        b.append(this.getAuthor()).append(")");
        b.append(", ").append(Book.PRICE_TITLE);
        b.append(this.getPrice());
        return b.toString();
    }
    
}
