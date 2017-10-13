package utils;

import objects.Student;
import java.util.Comparator;

/**
 * File: SortUtil.java
 * Date: 21/08/2017
 *
 * @version 1.0.0
 * @author Lee Tzilantonis
 */
public class SortUtil {

	/**
	 * Comparator class for sorting by Last Name
	 */
	public static class LastNameCompare implements Comparator<Student> {

		/**
		 * Compares two students Last Name's to determine the 'higher' value
		 * To help decrease chances of equal, the comparison is CaSe-SeNsAtIvE
		 *
		 * @param a - Compare subject a
		 * @param b - Compare subject b
		 * @return Negative if a is first, 0 if they are equal, positive if a is
		 *         last (in relation to b)
		 */
		@Override
		public int compare(Student a, Student b) {
			return a.getLastName().compareTo(b.getLastName());
		}
	}

	/**
	 * Comparator class for sorting by Course Name
	 */
	public static class CourseCompare implements Comparator<Student> {

		/**
		 * Compares two students Course Name's to determine the 'higher' value
		 * To help decrease chances of equal, the comparison is CaSe-SeNsAtIvE
		 *
		 * @param a - Compare subject a
		 * @param b - Compare subject b
		 * @return Negative if a is first, 0 if they are equal, positive if a is
		 *         last (in relation to b)
		 */
		@Override
		public int compare(Student a, Student b) {
			return a.getCourse().compareTo(b.getCourse());
		}
	}

}
