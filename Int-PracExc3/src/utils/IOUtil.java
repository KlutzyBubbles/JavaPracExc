package utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;
import main.Main;
import objects.Student;

/**
 * File: IOUtil.java
 * Date: 21/08/2017
 * Notes: All IOExceptions are displayed as internal errors as the error
 * cannot be directly described from the Exception class name.
 *
 * @version 1.0.0
 * @author Lee Tzilantonis
 */
public class IOUtil {

	/**
	 * Defines the final path the file will be saved
	 */
	private static final String path = "students.bin";

	/**
	 * Writes an array of Student Objects to the file path listed above
	 *
	 * @param list - The list to serialize and store
	 */
	public static void writeArray(List<Student> list) {
		// No need to save or overwrite a file with an empty array
		if (list == null || list.isEmpty())
			return;
		// Defined outside try to assist finally scope
		FileOutputStream f = null;
		ObjectOutputStream o = null;
		try {
			// Initialise the output streams
			f = new FileOutputStream(IOUtil.path);
			o = new ObjectOutputStream(f);
			// Write the object
			o.writeObject(list);
		} catch (FileNotFoundException e) {
			Main.error("The file couldnt be found, MESSAGE: " + e.getMessage());
		} catch (IOException e) {
			Main.error("An internal error has occurred: " + e.getMessage());
		} finally { // Always execute closing streams
			try {
				// But dont try to invoke a method on a null Object
				if (o != null)
					o.close();
				if (f != null)
					f.close();
			} catch (IOException e) {
				Main.error("An internal error has occurred: " + e.getMessage());
			}
		}
	}

	/**
	 * Reads an array of Student Objects from the file path listed above
	 *
	 * @return - Array of Student Objects loaded, null if an error occurred
	 */
	public static List<Student> readArray() {
		// Defined outside try to assist finally scope
		FileInputStream f = null;
		ObjectInputStream o = null;
		try {
			// Initialise the output streams
			f = new FileInputStream(IOUtil.path);
			o = new ObjectInputStream(f);
			// Read the Object
			return (List<Student>) o.readObject();
		} catch (FileNotFoundException e) {
			// #1 file isnt found so we assume nothing is saved
			return null;
		} catch (IOException e) {
			Main.error("An internal error has occurred: " + e.getMessage());
		} catch (ClassCastException | ClassNotFoundException e) {
			Main.error("The previously saved students file has been corrupted, it will be deleted");
			// Perfectly safe to call the delete as we know the file exists refer to #1
			(new File(IOUtil.path)).delete();
		} finally { // Always execute closing streams
			try {
				// But dont try to invoke a method on a null Object
				if (o != null)
					o.close();
				if (f != null)
					f.close();
			} catch (IOException e) {
				Main.error("An internal error has occurred: " + e.getMessage());
			}
		}
		return null;
	}

}
