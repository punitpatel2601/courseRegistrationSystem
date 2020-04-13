package Client.ClientView;

import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import Client.ClientController.ClientCommunication;

public class ClientGUI extends JFrame {
	private static final long serialVersionUID = 1L;
	private ClientCommunication actions;
    private JTextArea jta;
    private String cName;
    private int cID;
    private int cSec;
    private String studentName;
    private int studentId;

    public ClientGUI(ClientCommunication ccm) {
        actions = ccm;
        jta = new JTextArea(
                "Welcome!\n This is a course registration system (beta).\n Please select from options below.");
        jta.setMargin(new Insets(3, 7, 3, 5));
        // jta.setLineWrap(true);
        // jta.setWrapStyleWord(true);
        prepareGUI();
    }

    private void prepareGUI() {
        setTitle("Course Registration System");
        setSize(600, 750);
        setLayout(new GridLayout(2, 1));

        JScrollPane jsp = new JScrollPane(jta);

        add(jsp);
        add(addButtons());

        setVisible(true);
        getStudentInfo();
    }
    
    private void getStudentInfo() {
    	try {
    		studentName = JOptionPane.showInputDialog(null, "Please enter your name");
    		studentId = Integer.parseInt(JOptionPane.showInputDialog(null, "Please enter your ID number"));
    	} catch (Exception e) {
    		e.getStackTrace();
    	}
    	actions.passStudentInfo(studentName, studentId);
    }

    public JPanel addButtons() {
        JPanel jp = new JPanel();
        jp.setLayout(new BoxLayout(jp, 1));

        JButton search = new JButton("Search courses in Course Catalogue");
        JButton addCourse = new JButton("Add course to Student's course");
        JButton remove = new JButton("Remove course from Student's courses");
        JButton viewAll = new JButton("View all the courses in Catalogue");
        JButton viewStuCourses = new JButton("View all the courses taken by Student");
        JButton quit = new JButton("Quit the application");

        jp.add(new JLabel(" ")); // creating spaces between buttons
        jp.add(search);
        jp.add(new JLabel(" "));
        jp.add(addCourse);
        jp.add(new JLabel(" "));
        jp.add(remove);
        jp.add(new JLabel(" "));
        jp.add(viewAll);
        jp.add(new JLabel(" "));
        jp.add(viewStuCourses);
        jp.add(new JLabel(" "));
        jp.add(quit);

        search.addActionListener((ActionEvent e) -> {
            guiSerOutput(searchCourse());
        });
        addCourse.addActionListener((ActionEvent e) -> {
            guiSerOutput(addTheCourse());
        });
        remove.addActionListener((ActionEvent e) -> {
            guiSerOutput(removeCourse());
        });
        viewAll.addActionListener((ActionEvent e) -> {
            guiSerOutput(viewAllCourses());
        });
        viewStuCourses.addActionListener((ActionEvent e) -> {
            guiSerOutput(studentCourses());
        });
        quit.addActionListener((ActionEvent e) -> {
            quit();
        });

        return jp;
    }

    private void guiSerOutput(String serverOutput) {
        System.out.println("gui -> " + serverOutput);

        jta.setText(""); // resetting text area
        if (serverOutput == null) { // checking if null response from server
            jta.setText("Error in your input, Server didn't respond!");
            return;
        }

        // showing server output to text area
        String[] outputs = serverOutput.split("#");
        for (String o : outputs) {
            jta.append(o);
            jta.append("\n");
        }
    }

    private void quit() {
        int res = JOptionPane.showConfirmDialog(null, "Press OK to quit.", "Quit?", JOptionPane.OK_CANCEL_OPTION);
        if (res == JOptionPane.OK_OPTION) { // exit only if ok is pressed
            actions.closeCon();
            System.exit(0);
        } else { // otherwise back to program
            return;
        }
    }

    private String studentCourses() {
        return actions.showStudentCourses();
    }

    private String viewAllCourses() {
        return actions.viewAllCourses();
    }

    private String removeCourse() {
        callForInput();
        System.out.println(cName + "\t" + cID);

        return actions.removeCourse(cName, cID);
    }

    private String addTheCourse() {
        callForInput();
        System.out.println(cName + "\t" + cID + "\t" + cSec);

        return actions.addCourse(cName, cID, cSec);
    }

    private String searchCourse() {
        callForInput();
        System.out.println(cName + "\t" + cID);

        return actions.searchCourse(cName, cID);
    }

    private void callForInput() {
        this.cName = callInputForName();
        if (cName.isEmpty() || (cName.compareTo(" ") == 0) || cName == null) {
            JOptionPane.showMessageDialog(null, "Invalid name entered, Please enter a String", "Error!",
                    JOptionPane.ERROR_MESSAGE);
            return; // don't ask for id input if name not entered correctly
        }
        this.cID = callInputForID();
        this.cSec = callInputForSection();

        this.cName.toUpperCase();
    }
    
    private int callInputForSection() {
    	int sec = 1;
    	try {
    		sec = Integer.parseInt(JOptionPane.showInputDialog(null, "Enter the Section Number: "));
    	} catch (Exception e) {
    		JOptionPane.showMessageDialog(null, "Invalid section entered, Please try again", "Error",
    									  JOptionPane.ERROR_MESSAGE);
    	}
    	return sec;
    }

    private int callInputForID() {
        int id = -1;
        try {
            id = Integer.parseInt(JOptionPane.showInputDialog(null, "Enter the Course ID: "));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Invalid ID entered, Please enter only numeric value", "Error!",
                    JOptionPane.ERROR_MESSAGE);
        }

        return id;
    }

    private String callInputForName() {
        String name = "";
        try {
            name = JOptionPane.showInputDialog(null, "Enter the name of the course");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Invalid name entered, Please enter a String", "Error!",
                    JOptionPane.ERROR_MESSAGE);
        }
        String[] mulWords = name.split(" ");
        if (mulWords[0].isEmpty()) {
            return mulWords[1]; // return second words if first words entered was space or empty
        }

        return mulWords[0]; // only accepting the first word entered as the name
    }
}