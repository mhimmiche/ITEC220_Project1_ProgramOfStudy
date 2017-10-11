import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import java.awt.event.ActionListener;
import java.io.*;
import java.awt.event.ActionEvent;
import javax.swing.JOptionPane;

/**
 * Creates the GUI Frame for the project 
 * 
 * @author Mehdi Himmiche
 *
 */
public class POSFrame extends JFrame {
	private static final long serialVersionUID = -1154162692199485194L;
	// Dear god so many variables... Just. So. Many.
	private JLabel POSLabel;
	private JTable POSTable;
	private JRadioButton[] termButtons = new JRadioButton[11];
	private JButton newPOSButton = new JButton("New");
	private JButton savePOSButton = new JButton("Save");
	private JButton loadPOSButton = new JButton("Load");;
	private JButton findButton;
	private JButton addButton;
	private JButton removeButton;
	private JButton addGradeButton;
	private JButton addPreReqButton;
	private JButton checkPreReqButton;
	private ButtonGroup termButtonGroup;
	private ProgramOfStudy POS;
	private JTextField termField;
	private JTextField deptField;
	private JTextField courseNumField;
	private JTextField courseNameField;
	private JTextField creditsField;
	private JTextField gradeField;
	private int term = 0;
	private String dept = null;
	private int courseNum = 0;
	private String courseName = null;
	private int credits = 0;
	private String grade = null;

	/**
	 * Constructor for the GUI. Creates all elements.
	 */
	public POSFrame() {
		//POS = new ProgramOfStudy();		
		createPanel();
		toggleButtonsOff();
	}
	

	/**
	 * Creates the panel for the table. Displays the courses
	 * 
	 * @return
	 */
	private JScrollPane createPOSTable() {
		String columnNames[] = { "Term", "Course #", "Department", "Course Name", "Credits", "Grade" };
		DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
		tableModel.setColumnIdentifiers(columnNames);
		POSTable = new JTable(tableModel);

		JScrollPane resultPanel = new JScrollPane(POSTable);
		POSTable.setFillsViewportHeight(true);
		
		return resultPanel;
	}

	/**
	 * Creates the radio button to change the display between terms/show all
	 * courses
	 * 
	 * @return
	 */
	private JPanel createRadioButton() {
		JPanel radioButton = new JPanel();
		ActionListener radioListener = new radioListener();
		termButtons[0] = new JRadioButton("All Terms");
		for (int i = 1; i < termButtons.length; i++) {
			termButtons[i] = new JRadioButton("Term " + i);
		}

		termButtonGroup = new ButtonGroup();
		for (int i = 0; i < termButtons.length; i++) {
			termButtonGroup.add(termButtons[i]);
		}
		termButtons[0].setSelected(true);

		Box vertAlignBox = Box.createVerticalBox();
		for (int i = 0; i < termButtons.length; i++) {
			vertAlignBox.add(termButtons[i]);
			termButtons[i].addActionListener(radioListener);
			termButtons[i].setEnabled(false);
		}

		radioButton.add(vertAlignBox);

		return radioButton;
	}

	/**
	 * Creates the top panel. It contains the new/load/save buttons.
	 * 
	 * @return
	 */
	private JPanel createTopPanel() {
		JPanel topPanel = new JPanel();
		topPanel.setLayout(new BorderLayout());
		POSLabel = new JLabel("<html>Program of Study Schedule. <br>By Mehdi Himmiche</html>");
		Box POSBox = Box.createHorizontalBox();
		POSBox.add(Box.createRigidArea(new Dimension(10, 0)));
		POSBox.add(POSLabel);

		ActionListener loadListener = new loadListener();
		loadPOSButton.addActionListener(loadListener);

		ActionListener newListener = new newListener();
		newPOSButton.addActionListener(newListener);

		ActionListener saveListener = new saveListener();
		savePOSButton.addActionListener(saveListener);
		savePOSButton.setEnabled(false); // Because I have to hold the user's hand through everything... 
		
		JPanel topButtons = topPanelButtons();

		topPanel.add(POSBox, BorderLayout.WEST);
		topPanel.add(topButtons, BorderLayout.EAST);
		topPanel.add(Box.createRigidArea(new Dimension(0, 10)), BorderLayout.SOUTH);
		topPanel.add(Box.createRigidArea(new Dimension(0, 10)), BorderLayout.NORTH);

		return topPanel;
	}

	/**
	 * Creates the buttons for the top panel.
	 * 
	 * @return
	 */
	private JPanel topPanelButtons() {
		JPanel topPanelButtons = new JPanel();
		Box horAlignBox = Box.createHorizontalBox();
		horAlignBox.add(newPOSButton);
		horAlignBox.add(Box.createRigidArea(new Dimension(10, 0)));
		horAlignBox.add(savePOSButton);
		horAlignBox.add(Box.createRigidArea(new Dimension(10, 0)));
		horAlignBox.add(loadPOSButton);
		horAlignBox.add(Box.createRigidArea(new Dimension(10, 0)));
		topPanelButtons.add(horAlignBox);

		return topPanelButtons;
	}

	/**
	 * Creates the bottom panel. It contains all the buttons that handle the
	 * operations on courses.
	 * 
	 * @return
	 */
	private JPanel createBottomPanel() {
		JPanel bottomPanel = new JPanel();
		bottomPanel.setLayout(new GridLayout(2, 3, 10, 10));
		addButton = new JButton("Add Course");
		findButton = new JButton("Find Course");
		removeButton = new JButton("Remove Course");
		addGradeButton = new JButton("Add Grade");
		addPreReqButton = new JButton("Add Prerequisite");
		checkPreReqButton = new JButton("Check Prerequisites");
		bottomPanelAddListener();
		bottomPanel.add(addButton);
		bottomPanel.add(findButton);
		bottomPanel.add(removeButton);
		bottomPanel.add(addGradeButton);
		bottomPanel.add(addPreReqButton);
		bottomPanel.add(checkPreReqButton);
		return bottomPanel;
	}
	
	/**
	 * Turns the bottom buttons off to prevent user from adding course to nothing... 
	 */
	private void toggleButtonsOff() {	
		// I cannot in good conscience assume the user knows what they're doing.
		addButton.setEnabled(false);
		findButton.setEnabled(false);
		removeButton.setEnabled(false);
		addGradeButton.setEnabled(false);
		addPreReqButton.setEnabled(false);
		checkPreReqButton.setEnabled(false);
	}
	
	/**
	 * Turns the bottom buttons on to Let the user use the operations.
	 */
	private void toggleButtonsOn() {	
		// Yes, yes, user. I am now letting you be free!!
		addButton.setEnabled(true);
		findButton.setEnabled(true);
		removeButton.setEnabled(true);
		addGradeButton.setEnabled(true);
		addPreReqButton.setEnabled(true);
		checkPreReqButton.setEnabled(true);
	}

	/**
	 * Adds the action listeners to the bottom panel buttons
	 */
	private void bottomPanelAddListener() {
		ActionListener addListener = new addListener();
		addButton.addActionListener(addListener);

		ActionListener findListener = new findListener();
		findButton.addActionListener(findListener);

		ActionListener removeCourseListener = new removeCourseListener();
		removeButton.addActionListener(removeCourseListener);

		ActionListener addGradeListener = new addGradeListener();
		addGradeButton.addActionListener(addGradeListener);

		ActionListener addPreReqListener = new addPreReqListener();
		addPreReqButton.addActionListener(addPreReqListener);

		ActionListener checkPreReqListener = new checkPreReqListener();
		checkPreReqButton.addActionListener(checkPreReqListener);
	}

	/**
	 * Action Listener for the radio buttons
	 * 
	 * @author Mehdi Himmiche
	 *
	 */
	class radioListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (termButtons[0].isSelected()) {
				showAllCourses();
			} else {
				showSpecificSemester();
			}
		}
	}

	/**
	 * Display all the courses in the table
	 */
	private void showAllCourses() {
		DefaultTableModel model = (DefaultTableModel) POSTable.getModel();
		model.setRowCount(0);
		for (int i = 0; i < POS.getMaxSemester(); i++) {
			for (int j = 0; j < POS.getMaxCourse(); j++) {
				if (POS.getCourse(i, j) != null) {
					Course curCourse = POS.getCourse(i, j);
					model.addRow(new Object[] { i + 1, curCourse.getCourseNum(), curCourse.getDeptName(),
							curCourse.getCourseName(), curCourse.getCredits(), curCourse.getGrade() });
				}
			}
		}
	}

	/**
	 * Display only the selected radio button's term
	 */
	private void showSpecificSemester() {
		DefaultTableModel model = (DefaultTableModel) POSTable.getModel();
		model.setRowCount(0);
		for (int i = 1; i < termButtons.length; i++) {
			if (termButtons[i].isSelected()) {
				for (int j = 0; j < POS.getMaxCourse(); j++) {
					if (POS.getCourse(i - 1, j) != null) {
						Course curCourse = POS.getCourse(i - 1, j);
						model.addRow(new Object[] { i, curCourse.getCourseNum(), curCourse.getDeptName(),
								curCourse.getCourseName(), curCourse.getCredits(), curCourse.getGrade() });
					}
				}
			}
		}
	}

	/**
	 * Disable the radio buttons for the terms who do not have any courses in
	 * them.
	 */
	private void enableRadioButtons() {
		termButtons[0].setEnabled(true);
		for (int i = 0; i < POS.getMaxSemester(); i++) {
			if (POS.getCourse(i, 0) != null) {
				termButtons[i + 1].setEnabled(true);
			}
		}
	}


	/**
	 * Action Listener for loading a file. Used the tutorial for lab 1
	 * 
	 * @author Mehdi Himmiche
	 *
	 */	
	class loadListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			boolean loadFile = true;
			String fileToLoad = loadPanel();
			if (fileToLoad == null) {
				loadFile = false;
			}
			if (loadFile) {
				termButtons[0].setSelected(true);
				fileToLoad = "savedFiles/" + fileToLoad;
				try {
					//POS = new ProgramOfStudy();
					FileInputStream fileLoader = new FileInputStream(fileToLoad);
					ObjectInputStream objectLoader = new ObjectInputStream(fileLoader);
					POS = (ProgramOfStudy) objectLoader.readObject();
					showAllCourses();
					enableRadioButtons();
					toggleButtonsOn();
					savePOSButton.setEnabled(true);
					objectLoader.close();
				} catch (FileNotFoundException exc) {
					JOptionPane.showMessageDialog(null, "File could not be found. \nPlease enter appropriate name",
							"File not found", JOptionPane.ERROR_MESSAGE);
				} catch (IOException exc) {
					JOptionPane.showMessageDialog(null, "There was an error attempting to read the file.",
							"Error reading file", JOptionPane.ERROR_MESSAGE);
					exc.printStackTrace();
				} catch (ClassNotFoundException exc) {
					JOptionPane.showMessageDialog(null, "File could not be found. \nPlease enter appropriate name",
							"File not found", JOptionPane.ERROR_MESSAGE);
				}
			}
		}
	}

	/**
	 * Action Listener for saving a file. Used the tutorial for lab 1 as well as
	 * the following:
	 * https://www.caveofprogramming.com/java/java-file-reading-and-writing-
	 * files-in-java.html
	 * 
	 * @author Mehdi Himmiche
	 *
	 */
	class saveListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			String saveFile = (String) JOptionPane.showInputDialog(null,
					"What would you like to name your program of study?", "Saving file", JOptionPane.PLAIN_MESSAGE);
			try {
				saveFile = "savedFiles/" + saveFile + ".ser";
				FileOutputStream fileToSave = new FileOutputStream(saveFile);
				ObjectOutputStream objectSaver = new ObjectOutputStream(fileToSave);
				objectSaver.writeObject(POS);
				objectSaver.close();
			} catch (IOException exc) {
				JOptionPane.showMessageDialog(null, "There was an error attempting to save your program of study.",
						"Error saving file", JOptionPane.ERROR_MESSAGE);
				System.out.println(exc.getMessage());
			}
		}
	}

	/**
	 * Action Listener for finding a course.
	 * 
	 * @author Mehdi Himmiche
	 *
	 */
	class findListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			boolean isFound = false;
			String courseToFind = (String) JOptionPane.showInputDialog(null, "Please enter the course name",
					"Find a course", JOptionPane.PLAIN_MESSAGE);
			if (courseToFind != null) {
				for (int i = 0; i < POS.getMaxSemester(); i++) {
					for (int j = 0; j < POS.getMaxCourse(); j++) {
						if (POS.getCourse(i, j) != null) {
							if (POS.getCourse(i, j).getCourseName().equals(courseToFind)) {
								isFound = true;
								termButtons[i + 1].setSelected(true);
								showSpecificSemester();
							}
						}
					}
				}
				if (!isFound) {
					JOptionPane.showMessageDialog(null, "Sorry, could not find " + courseToFind, "Finding course error",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		}
	}

	/**
	 * Action Listener for removing a course.
	 * 
	 * @author Mehdi Himmiche
	 *
	 */
	class removeCourseListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			boolean isRemoved = false;
			String courseToRemove = (String) JOptionPane.showInputDialog(null, "Please enter the course name",
					"Remove a course", JOptionPane.PLAIN_MESSAGE);
			for (int i = 0; i < POS.getMaxSemester(); i++) {
				for (int j = 0; j < POS.getMaxCourse(); j++) {
					if (POS.getCourse(i, j) != null) {
						if (POS.getCourse(i, j).getCourseName().equals(courseToRemove)) {
							isRemoved = true;
							POS.removeCourse(i, j);
							showAllCourses();
						}
					}
				}
			}
			if (!isRemoved) {
				JOptionPane.showMessageDialog(null, "Sorry, could not find " + courseToRemove, "Removing course error",
						JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	/**
	 * Action Listener for starting a new program of study.
	 * 
	 * @author Mehdi Himmiche
	 *
	 */
	class newListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			DefaultTableModel model = (DefaultTableModel) POSTable.getModel();
			model.setRowCount(0);
			toggleButtonsOn();
			savePOSButton.setEnabled(true);
			POS = new ProgramOfStudy();
			for (int i = 0; i < termButtons.length; i++) {
				termButtons[i].setEnabled(false);
			}
		}
	}

	/**
	 * Action Listener for adding a new course.
	 * 
	 * @author Mehdi Himmiche
	 *
	 */
	class addListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			JPanel addPanel = createAddPanel();
			int selection = 0;
			do {
				selection = JOptionPane.showConfirmDialog(null, addPanel, "Adding new course",
						JOptionPane.OK_CANCEL_OPTION);
				if (selection == JOptionPane.OK_OPTION) {
					boolean isFilled = isAddFilled();
					if (isFilled) {
						Course newCourse = new Course(dept, courseNum, credits, courseName, grade);
						try {
							POS.addCourse(term, newCourse);
						} catch (ArrayIndexOutOfBoundsException ex) {
							JOptionPane.showMessageDialog(addPanel, "You cannot add anymore courses for that semester",
									"Cannot add course", JOptionPane.ERROR_MESSAGE);
						}
						selection = JOptionPane.CANCEL_OPTION;
						termButtons[term].setSelected(true);
						showSpecificSemester();
						enableRadioButtons();
					} else {
						JOptionPane.showMessageDialog(addPanel, "There was an error adding the class. "
								+ "\nPlease check that you've entered correct information."
								+ "\nYou may have entered a term greater than the allowed value(10)",
								"Cannot add course", JOptionPane.ERROR_MESSAGE);
					}
				}
			} while (selection != JOptionPane.CANCEL_OPTION);

		}
	}

	private boolean isAddFilled() {
		boolean isFilled = true;
		try {
			term = Integer.parseInt(termField.getText());
			dept = deptField.getText();
			courseNum = Integer.parseInt(courseNumField.getText());
			courseName = courseNameField.getText();
			credits = Integer.parseInt(creditsField.getText());
			grade = gradeField.getText();
		} catch (NumberFormatException num) {
			isFilled = false;
		}
		if (term > POS.getMaxSemester()){
			isFilled = false;
		}
		return isFilled;
	}

	/**
	 * Action Listener for adding a grade to a course.
	 * 
	 * @author Mehdi Himmiche
	 *
	 */
	class addGradeListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			boolean isFound = false;
			String courseToFind = (String) JOptionPane.showInputDialog(null, "Please enter the course name",
					"Find a course", JOptionPane.PLAIN_MESSAGE);
			if (courseToFind != null) {
				for (int i = 0; i < POS.getMaxSemester(); i++) {
					for (int j = 0; j < POS.getMaxCourse(); j++) {
						if (POS.getCourse(i, j) != null) {
							if (POS.getCourse(i, j).getCourseName().equals(courseToFind)) {
								isFound = true;
								String grade = (String) JOptionPane.showInputDialog(null, "Please enter the grade",
										"Add grade", JOptionPane.PLAIN_MESSAGE);
								POS.getCourse(i, j).setGrade(grade);
								termButtons[i + 1].setSelected(true);
								showSpecificSemester();
							}
						}
					}
				}
				if (!isFound) {
					JOptionPane.showMessageDialog(null, "Sorry, could not find " + courseToFind, "Finding course error",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		}
	}

	/**
	 * Action Listener for adding a prerequisite to a course.
	 * 
	 * @author Mehdi Himmiche
	 *
	 */
	class addPreReqListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			boolean isFound = false;
			String courseToFind = (String) JOptionPane.showInputDialog(null,
					"Please enter the course name to add prerequisite to", "Adding prerequisite",
					JOptionPane.PLAIN_MESSAGE);
			if (courseToFind != null) {
				for (int i = 0; i < POS.getMaxSemester(); i++) {
					for (int j = 0; j < POS.getMaxCourse(); j++) {
						if (POS.getCourse(i, j) != null) {
							if (POS.getCourse(i, j).getCourseName().equals(courseToFind)) {
								isFound = true;
								String prereq = (String) JOptionPane.showInputDialog(null,
										"Please enter the prerequisite name \nBe careful of spelling!",
										"Add prerequisite", JOptionPane.PLAIN_MESSAGE);
								POS.getCourse(i, j).setPreReq(prereq);
								System.out.println(POS.getCourse(i, j).getPreReq());
							}
						}
					}
				}
				if (!isFound) {
					JOptionPane.showMessageDialog(null, "Sorry, could not find " + courseToFind, "Finding course error",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		}
	}

	/**
	 * Action Listener for checking the prerequisites of a course.
	 * 
	 * @author Mehdi Himmiche
	 *
	 */
	class checkPreReqListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			List<String> returnList = POS.checkPreReq();
			if (returnList.isEmpty()) {
				JOptionPane.showMessageDialog(null, "All prerequisites are fulfilled", "Yay!",
						JOptionPane.PLAIN_MESSAGE);
			} else {
				String returnString = "The following courses are missing prerequisites: \n";
				for (int i = 0; i < returnList.size(); i++) {
					returnString = returnString + "- " + returnList.get(i);
				}
				JOptionPane.showMessageDialog(null, returnString, "Missing prerequisites", JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	/**
	 * Creates the panel for adding a new course.
	 * 
	 * @author Mehdi Himmiche
	 *
	 */
	private JPanel createAddPanel() {
		JPanel addCoursePanel = new JPanel();
		addCoursePanel.setLayout(new GridLayout(2, 6, 10, 10));
		JLabel termLabel = new JLabel("Term");
		JLabel deptLabel = new JLabel("Department");
		JLabel courseNumLabel = new JLabel("Course #");
		JLabel courseNameLabel = new JLabel("Course Name");
		JLabel creditsLabel = new JLabel("Credits");
		JLabel gradeLAbel = new JLabel("Grade");
		JTextField[] textFieldArray = new JTextField[6]; 
		termField = new JTextField();
		textFieldArray[0] = termField;
		deptField = new JTextField();
		textFieldArray[1] = deptField;
		courseNumField = new JTextField();
		textFieldArray[2] = courseNumField;
		courseNameField = new JTextField();
		textFieldArray[3] = courseNameField;
		creditsField = new JTextField();
		textFieldArray[4] = creditsField;
		gradeField = new JTextField();
		textFieldArray[5] = gradeField;
		addCoursePanel.add(termLabel);
		addCoursePanel.add(deptLabel);
		addCoursePanel.add(courseNumLabel);
		addCoursePanel.add(courseNameLabel);
		addCoursePanel.add(creditsLabel);
		addCoursePanel.add(gradeLAbel);
		for (int i = 0; i < textFieldArray.length; i++) {
			addCoursePanel.add(textFieldArray[i]);
		}
		return addCoursePanel;
	}

	/**
	 * Creates the panel for loading a program of study.
	 * 
	 * @author Mehdi Himmiche
	 *
	 */
	private String loadPanel() {

		Object[] loadString = { "Other", "ComputerScience", "Database", "InformationSystems", "Networks",
				"SoftwareEngineering", "WebDevelopment" };
		String choice = null;
		choice = (String) JOptionPane.showInputDialog(null, "Choose file to load", "Loading file",
				JOptionPane.PLAIN_MESSAGE, null, loadString, "Other");

		if (choice != null) {
			if (!choice.equals("Other")) {
				choice = choice + ".ser";
			} else {
				choice = (String) JOptionPane.showInputDialog(null,
						"What is the file name to load? \nYou need not worry about the file extension.", "File to load",
						JOptionPane.PLAIN_MESSAGE);
				choice = choice + ".ser";
			}
		}
		return choice;
	}

	/**
	 * Creates the final GUI.
	 * 
	 * @author Mehdi Himmiche
	 *
	 */
	private void createPanel() {
		JPanel panel = new JPanel();
		add(panel);
		panel.setLayout(new BorderLayout());

		JPanel topPanel = createTopPanel();
		panel.add(topPanel, BorderLayout.NORTH);

		JScrollPane resultPanel = createPOSTable();
		panel.add(resultPanel, BorderLayout.CENTER);

		panel.add(Box.createRigidArea(new Dimension(10, 0)), BorderLayout.EAST);

		JPanel radioButtons = createRadioButton();
		panel.add(radioButtons, BorderLayout.WEST);

		JPanel fullBottomPanel = new JPanel();
		fullBottomPanel.setLayout(new BorderLayout());
		JPanel bottomPanel = createBottomPanel();
		fullBottomPanel.add(Box.createRigidArea(new Dimension(10, 0)), BorderLayout.EAST);
		fullBottomPanel.add(Box.createRigidArea(new Dimension(10, 0)), BorderLayout.WEST);
		fullBottomPanel.add(Box.createRigidArea(new Dimension(0, 10)), BorderLayout.NORTH);
		fullBottomPanel.add(Box.createRigidArea(new Dimension(0, 10)), BorderLayout.SOUTH);
		fullBottomPanel.add(bottomPanel, BorderLayout.CENTER);
		panel.add(fullBottomPanel, BorderLayout.SOUTH);
	}
}
