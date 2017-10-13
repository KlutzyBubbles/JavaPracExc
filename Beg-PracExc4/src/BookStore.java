import javax.swing.JOptionPane;

/**
 * File: BookStore.java
 * Date: 24/05/2017
 * @author Lee Tzilantonis
 * @version 1.0.0
 */
public class BookStore {

    /**
     * Indicates to the main loop whether the program should run another loop
     */
    public static boolean running = true;
    
    /**
     * Library contains all books that have been instantiated
     */
    private static final Book[] LIBRARY = new Book[10];
    
    /**
     * Menu contains the menu text to be displayed to the user
     */
    private static final String MENU = "--Book Store--\n\n"
                                    + "1. Add book to catalogue\n"
                                    + "2. Sort and display books by price\n"
                                    + "3. Search for a book by title\n"
                                    + "4. Display all books\n\n"
                                    + "5. Exit";
    
    /**
     * Main method run when jar is run
     * 
     * @param args The command line arguments parsed to the program
     */
    public static void main(String[] args) {
        while(BookStore.running) {
            BookStore.loop();
        }
    }
    
    /**
     * The programs main loop to determine what menu option the user wants
     */
    public static void loop() {
        String input = JOptionPane.showInputDialog
                            (null, BookStore.MENU, "Input", JOptionPane.QUESTION_MESSAGE);
		if (input == null) {
			running = false;
			return;
		}
        int num;
        try {
            num = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            BookStore.error("Please only enter a number for the input");
            return;
        }
        if (num == 1) {
            BookStore.addBook();
        } else if (num == 2) {
            BookStore.sortDisplay();
        } else if (num == 3) {
            BookStore.search();
        } else if (num == 4) {
            BookStore.display("Library", BookStore.LIBRARY);
        } else if (num == 5) {
            BookStore.running = false;
        } else {
            BookStore.error("Please only enter a number between 1 and 5");
        }
    }
    
    /**
     * Prompts the user with the required inputs to create a new book 
     */
    public static void addBook() {
        int num = -1;
        for (int i = 0; i < BookStore.LIBRARY.length; i++)
            if (BookStore.LIBRARY[i] == null) {
                num = i;
                break;
            }
        if (num == -1)
            BookStore.message("Catalogue full - cannot add any more books");
        else if (num > BookStore.LIBRARY.length - 1)
            BookStore.error("An unknown error occured");
        else {
            int id = -1;
            while (id < 1000 || id > 9999) {
                String input = JOptionPane.showInputDialog(null, "Please enter the books ID");
                if (input == null) {
                    BookStore.error("Please enter an ID");
                    continue;
                }
                try {
                    id = Integer.parseInt(input);
                } catch (NumberFormatException e) {
                    BookStore.error("Please only enter an integer for the ID");
                    id = -1;
                    continue;
                }
                if (id < 1000 || id > 9999)
                    BookStore.error("Please only enter an ID between 1000 and 9999");
                if (BookStore.exists(id)) {
                    BookStore.error("That ID already exists, please enter another");
                    id = -1;
                }
            }
            String title = "", author = "";
            while (title.equals("")) {
                title = JOptionPane.showInputDialog(null, "Please enter the books title");
                if (title == null) {
                    BookStore.error("Please make sure you enter something for the title");
                    title = "";
                    continue;
                }
                if (title.equals(""))
                    BookStore.error("Please make sure you enter something for the title");
            }
            while (author.equals("")) {
                author = JOptionPane.showInputDialog(null, "Please enter the books author");
                if (author == null) {
                    BookStore.error("Please make sure you enter something for the author");
                    author = "";
                    continue;
                }
                if (author.equals(""))
                BookStore.error("Please make sure you enter something for the author");
            }
            double price = -1D;
            while (price < 0D) {
                String input = JOptionPane.showInputDialog(null, "Please enter the books price");
                if (input == null) {
                    BookStore.error("Please enter a price");
                    continue;
                }
                try {
                    price = Double.parseDouble(input);
                } catch (NumberFormatException e) {
                    BookStore.error("Please only enter a double for the price");
                    price = -1D;
                    continue;
                }
                if (price < 0D)
                    BookStore.error("Please only enter a price that is non-negative");
            }
            Book temp = new Book(id, title, author, price);
            BookStore.LIBRARY[num] = temp;
            BookStore.message("Book ID: " + id + " was added to the library");
        }
    }
    
    /**
     * Sorts the library by price ascending and displays the appropriate information
     */
    public static void sortDisplay() {
        if (BookStore.isLibraryEmpty()) {
            BookStore.message("There are no books added to sort or display");
        } else {
            Book[] temp = BookStore.LIBRARY.clone();
            int length = 0;
            for (Book b : temp) {
                if (b == null)
                    break;
                length++;
            }
            int c = length - 1;
            for (int i = 0; i < length - 1; i++) {
                for (int j = 0; j < c; j++)
                    if (temp[j].getPrice() > temp[j + 1].getPrice()) {
                        Book b = temp[j];
                        temp[j] = temp[j + 1];
                        temp[j + 1] = b;
                    }
                c--;
            }
            BookStore.display("Sorted Library", temp);
        }
    }
    
    /**
     * Prompts the user with the required inputs to be able
     * to enter a search query to search library titles
     */
    public static void search() {
        boolean empty = true;
        for (Book b : BookStore.LIBRARY) {
            if (b != null) {
                empty = false;
                break;
            }
        }
        if (empty) {
            BookStore.error("There are no books to search through");
            return;
        }
        String title = "";
        while (title.equals("")) {
            title = JOptionPane.showInputDialog(null, "Please enter the books title to search for\n\nUse flag '--i' for case insensative searching");
			if (title == null)
				return;
            if (title.equals(""))
                BookStore.error("Please make sure you enter something for the title to search for");
        }
        boolean ci = false;
        if (title.equals("--i")) {
            JOptionPane.showMessageDialog(null, "Please make sure you enter something for the title to search for");
            BookStore.search();
            return;
        }
        if (title.contains(" --i")) {
            ci = true;
            title = BookStore.remove(title, " --i");
        }
        while (title.startsWith(" "))
            title = title.substring(1);
        while (title.endsWith(" "))
            title = title.substring(0, title.length() - 1);
        if (title.equals("")) {
            JOptionPane.showMessageDialog(null, "Please make sure you enter something for the title to search for");
            BookStore.search();
            return;
        }
        System.out.println("CASE INSENSATIVE: " + ci); // Used for input testing
        Book[] search = new Book[10];
        for (int i = 0; i < BookStore.LIBRARY.length; i++) {
            if (BookStore.LIBRARY[i] == null)
                continue;
            if (ci) {
                if (BookStore.LIBRARY[i].getTitle().toUpperCase().contains(title.toUpperCase()))
                    search[i] = BookStore.LIBRARY[i];
            } else {
                if (BookStore.LIBRARY[i].getTitle().contains(title))
                    search[i] = BookStore.LIBRARY[i];
            }
        }
        empty = true;
        for (Book b : search) {
            if (b != null) {
                empty = false;
                break;
            }
        }
        if (empty) {
            JOptionPane.showMessageDialog(null, "The title does not exist in the collection");
        } else {
            BookStore.display("Book Details", search);
        }
    }
    
    /**
     * Removes the specified string from the stack string including
     * every character after the specified needle
     * 
     * @param stack The String stack to be split
     * @param needle The needs in which indicated where the stack is to be split
     * @return - The split stack
     */
    public static String remove(String stack, String needle) {
        String result = stack.substring(0, stack.indexOf(needle));
        return result;
    }
    
    /**
     * Displays book information or a relevant message to the user based on
     * the contents of Book[]
     * 
     * @param title The title of the dialog box that is to be displayed to the user
     * @param library The Book[] that is to be displayed to the user
     */
    public static void display(String title, Book[] library) {
        if (BookStore.isLibraryEmpty()) {
            BookStore.message("There are no books added to display");
        } else {
            StringBuilder b = new StringBuilder(title);
            b.append(":\n\n");
            for (Book book : library) {
                if (book != null)
                    b.append(book.toString()).append("\n");
            }
            BookStore.message(b.toString());
        }
    }
    
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
    
    /**
     * Checks if a specified id exists in the library of books available
     * 
     * @param id The id that will be used for the search
     * @return - Whether or not a book with the specified id exists in the library
     */
    public static boolean exists(int id) {
        for (Book b : BookStore.LIBRARY) {
            if (b != null && b.getId() == id)
                return true;
        }
        return false;
    }
    
    /**
     * Checks whether or not the library has any books available
     * 
     * @return - Whether or not the library contains any Book Objects
     */
    public static boolean isLibraryEmpty() {
        for (Book b : BookStore.LIBRARY)
            if (b != null)
                return false;
        return true;
    }
    
}
