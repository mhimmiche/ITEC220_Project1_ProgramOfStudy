import javax.swing.JFrame;
/**
 * Viewer for the Program of Study Program
 * @author mehdi Himmiche
 *
 */
public class Project1Driver {

	public static void main(String[] args) {
		// Thank goodness it's finally over... 
		// Stack overflow and the oracle documentation are by far the best thing I've read!! 
		JFrame frame = new POSFrame();
		frame.setTitle("Program of Study");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    frame.pack();
	    frame.setVisible(true);
	}

}
