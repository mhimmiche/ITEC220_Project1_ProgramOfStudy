import java.io.Serializable;
import java.util.*;

/**
 * Program of Study class Creates a program of study and populates it with 
 * courses
 * 
 * @author Mehdi Himmiche
 *
 */
public class ProgramOfStudy implements Serializable {
	/**
	 * SID
	 */
	private static final long serialVersionUID = 1L;
	// Sooooooooooooooo many variables...
	private final int NUM_OF_SEMESTER = 10;
	private final int COURSE_PER_SEMESTER = 7;
	private Course[][] myCourses;

	/**
	 * Constructor for the POS class. Creates the 2D array of courses.
	 */
	public ProgramOfStudy() {
		// TODO Auto-generated constructor stub
		myCourses = new Course[NUM_OF_SEMESTER][COURSE_PER_SEMESTER];
	}

	/**
	 * Adds a course to the 2D array
	 * 
	 * @param semester
	 * @param newCourse
	 */
	public void addCourse(int semester, Course newCourse) {
		boolean isAdded = false;
		for (int i = 0; i < COURSE_PER_SEMESTER; i++) {
			if (myCourses[semester - 1][i] == null && !isAdded) {
				myCourses[semester - 1][i] = newCourse;
				isAdded = true;
			}
		}
	}

	/**
	 * Return the max number of semesters
	 * 
	 * @return NUM_OF_SEMESTER
	 */
	public int getMaxSemester() {
		return NUM_OF_SEMESTER;
	}

	/**
	 * return the max number of courses per semester
	 * 
	 * @return COURSE_PER_SEMESTER
	 */
	public int getMaxCourse() {
		return COURSE_PER_SEMESTER;
	}

	/**
	 * Override the toString() Method. Not used in this program.
	 */
	public String toString() {
		String finalString = "";
		for (int i = 0; i < NUM_OF_SEMESTER; i++) {
			for (int j = 0; j < COURSE_PER_SEMESTER; j++) {
				if (myCourses[i][j] != null) {
					finalString = finalString + "Semester: " + (i + 1) + " => " + myCourses[i][j].toString() + "\n";
				}
			}
		}
		return finalString;
	}

	/**
	 * Returns the course at a specific location in the 2D course array
	 * 
	 * @param semester
	 * @param courseNum
	 * @return Course
	 */
	public Course getCourse(int semester, int courseNum) {
		Course returnCourse = myCourses[semester][courseNum];
		return returnCourse;
	}

	/**
	 * Check if the prerequisites are met for every course in the program.
	 * This loops through every course starting from the second semester onwards and checks their prerequisites. 
	 * 
	 * @param semester
	 * @param courseNum
	 * @return Arraylist of prerequisite not met.
	 */
	public List<String> checkPreReq() {
		List<String> returnList = new ArrayList<String>();
		for (int i = 1; i < NUM_OF_SEMESTER; i++) {
			for (int j = 0; j < COURSE_PER_SEMESTER; j++) {
				Course checkedCourse = myCourses[i][j];
				List<String> preReqList = new ArrayList<String>();
				if (checkedCourse != null) {
					preReqList = checkedCourse.getPreReq();
				}					
				if (!preReqList.isEmpty()) {
					boolean isAdded = false;					
					for (int k = 0; k < preReqList.size(); k++) {
						if (!isAdded) {
							int index = 1;
							for (int l = 0; l < i; l++) {
								for (int m = 0; m < COURSE_PER_SEMESTER; m++) {
									index++;
									if (myCourses[l][m] != null) {
										if (myCourses[l][m].getCourseName().equals(preReqList.get(k))) {
											index = 0;
										}
									}
									if (index == COURSE_PER_SEMESTER && !isAdded) {
										returnList.add(checkedCourse.getCourseName());
										isAdded = true;
									}
								}
							}
						}
					}
				}
			}
		}
		return returnList;
	}
	// ^ That up there is totally an ungodly amount of loops... Like waaaaaaaaaaaaaaaay more than anyone should ever have! 
	// I wanted to make it prettier... I couldn't... I'm sorry :'(
	
	/**
	 * Remove a course off the 2D course array
	 * 
	 * @param semester
	 * @param courseNum
	 */
	public void removeCourse(int semester, int courseNum) {
		myCourses[semester][courseNum] = null;
	}
}
