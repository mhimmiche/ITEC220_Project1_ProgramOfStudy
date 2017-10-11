import java.io.*;
import java.util.*;
/**
 * Course class for the Program of Study Project  
 * @author Mehdi Himmiche
 *
 */
public class Course implements Serializable{
	private static final long serialVersionUID = 1L;
	private String deptName; 
	private String courseName;
	private int courseNum;
	private String grade;
	private int credits;
	private List<String> preReq = new ArrayList<String>();
	
	/**
	 * Creating a default course
	 */
	public Course () {
		this.deptName = "N/A";
		this.courseName = "N/A";
		this.courseNum = 0;
	}
	
	/**
	 * Constructor for the courses
	 * @param inputDeptName
	 * @param inputCourseNum
	 * @param inputCredits
	 * @param inputCourseName
	 * @param inputGrade
	 */
	public Course (String inputDeptName, int inputCourseNum, int inputCredits, String inputCourseName, String inputGrade) {
		this.deptName = inputDeptName;
		this.courseNum = inputCourseNum;
		this.credits = inputCredits;
		this.courseName = inputCourseName;
		if (inputGrade == null) {
			this.grade = null;
		}
		else {
			this.grade = inputGrade;
		} 		
	}
	
	
	// Setters
	
	/**
	 * Setter for the department name string
	 * @param newDeptName
	 */
	public void setDept (String newDeptName) {
		this.deptName = newDeptName;
	}
	
	/**
	 * Setter for the course's credits
	 * @param newCredits
	 */
	public void setCredits (int newCredits) {
		this.credits = newCredits;
	}
	
	/**
	 * Setter for the course number
	 * @param newCourseNum
	 */
	public void setCourseNum (int newCourseNum) {
		this.courseNum = newCourseNum;
	}
	
	/**
	 * Setter for the course name
	 * @param newCourseName
	 */
	public void setCourseName (String newCourseName) {
		this.courseName = newCourseName;
	}
	
	/**
	 * Setter for the grade
	 * @param newGrade
	 */
	public void setGrade (String newGrade) {
		this.grade = newGrade;
	}
	
	/**
	 * Add a prerequisite to the course
	 * @param newCourse
	 */
	public void setPreReq (String newCourse) {
		this.preReq.add(newCourse);
	}
	
	// Getter
	
	/**
	 * Getter for the department name
	 * @return deptName : The department name
	 */
	public String getDeptName () {
		return this.deptName;
	}
	
	/**
	 * Getter for the course's credits
	 * @return credits : The course's credits
	 */
	public int getCredits () {
		return this.credits;
	}
	
	/**
	 * Getter for the course name
	 * @return courseName : The course name
	 */
	public String getCourseName () {
		return this.courseName;
	}
	
	/**
	 * Getter for the course number
	 * @return courseNum : The course number
	 */
	public int getCourseNum () {
		return this.courseNum;
	}
	
	/**
	 * Getter for the grade
	 * @return grade : The grade
	 */
	public String getGrade () {
		return this.grade;
	}
	
	/**
	 * Getter for the prerequisite list
	 * @return Arraylist of prerequisite
	 */
	public List<String> getPreReq() {
		return this.preReq;
	}
	
	// Other Methods
	
	/**
	 * Overriding the toString method to only return course info
	 */
	public String toString () {
		String returnString = this.deptName + " " + this.courseNum + " " + this.credits + " " + this.courseName;
		if (this.grade != null) {
			if (!this.grade.equals("")) { 
				returnString = returnString + " grade is: " + this.grade;
			}			
		}
		return returnString;
	}
}
