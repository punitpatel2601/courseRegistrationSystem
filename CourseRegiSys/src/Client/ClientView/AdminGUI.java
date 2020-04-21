package Client.ClientView;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import Client.ClientController.ClientCommunication;

/**
 * Creates the graphical user interface for the Admin user type. Authenticated
 * users can use the application in Admin view
 * 
 * @author Punit Patel
 * @author Armaan Mohar
 * @author Tom Pritchard
 * 
 * @since April 17, 2020
 * @version 1.0 (beta)
 * 
 */
public class AdminGUI extends GUI {

    /**
     * serial id
     */
    private static final long serialVersionUID = 1L;

    /**
     * id of admin
     */
    private int adminId;

    /**
     * name of admin
     */
    private String adminName;

    /**
     * cap number of a course
     */
    private int cap;

    /**
     * Creates and Initializes the GUI for the user
     * 
     * @param ccm a pointer to ClientCommunication class
     */
    public AdminGUI(View v) {
        // actions = ccm;
        theView = v;
        adminName = "";
        adminId = -1;
        valid = "";
        tGUI();
    }

    public void tGUI() {
        detailsEntered = false;

        jta = new JTextArea(
                "Welcome!\n This is the Admin version of course registration system (beta).\n Please start by entering your details..\n\nCRITICAL WARNING: You can not do anything without entering your details.");
        jta.setMargin(new Insets(3, 7, 3, 5));
        jta.setEditable(false);

        prepareGUI();
    }

    /**
     * Prepares GUI for the user to interact with server
     */
    public void prepareGUI() {
        setTitle("Course Registration System -- Admin");
        setSize(600, 800);
        setLayout(new GridLayout(2, 1));

        JScrollPane jsp = new JScrollPane(jta);

        add(jsp);
        add(addButtons());

        setVisible(true);
    }

    /**
     * Creates the JPanel which has multiple buttons in order to get choice of user
     * 
     * @return jp JPanel created
     */
    public JPanel addButtons() {
        JPanel jp = new JPanel();
        jp.setLayout(new BoxLayout(jp, 1));

        JButton search = new JButton("Search courses in Course Catalogue");
        JButton addCourse = new JButton("Add course to Student's course");
        JButton remove = new JButton("Remove course from Student's courses");
        JButton viewAll = new JButton("View all the courses in Catalogue");
        JButton viewStuCourses = new JButton("View all the courses taken by Student");
        JButton enterDetails = new JButton("Enter details");
        JButton addNewCourse = new JButton("Add course to Course Catalogue");
        JButton runCourse = new JButton("Check if Course can run");
        JButton checkClassList = new JButton("Get classlists");
        JButton quit = new JButton("Quit the application");

        // setting visibilities of buttons
        search.setVisible(detailsEntered);
        addCourse.setVisible(detailsEntered);
        remove.setVisible(detailsEntered);
        viewAll.setVisible(detailsEntered);
        viewStuCourses.setVisible(detailsEntered);
        enterDetails.setVisible(!detailsEntered);
        addNewCourse.setVisible(detailsEntered);
        runCourse.setVisible(detailsEntered);
        checkClassList.setVisible(detailsEntered);
        quit.setVisible(true);

        jp.add(new JLabel(" "));
        jp.add(enterDetails);

        // creating spaces between buttons using JLabel
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
        jp.add(addNewCourse);
        jp.add(new JLabel(" "));
        jp.add(runCourse);
        jp.add(new JLabel(" "));
        jp.add(checkClassList);
        jp.add(new JLabel(" "));
        jp.add(quit);

        enterDetails.addActionListener((ActionEvent e) -> {
            JFrame log = new JFrame();
            JPanel logIn = new JPanel(new GridLayout(3, 1));

            JLabel user = new JLabel();
            JLabel pw = new JLabel("Enter password:");
            JTextField userName = new JTextField(20);
            JTextField passw = new JTextField(20);

            user.setText("Username: ");
            pw.setText("ID: ");

            JButton submit = new JButton("SUBMIT");
            log.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            logIn.add(user);
            logIn.add(pw);
            logIn.add(userName);
            logIn.add(passw);
            logIn.add(submit);

            log.add(logIn, BorderLayout.CENTER);
            log.setTitle("Please login here");
            log.setSize(1000, 200);
            log.setVisible(true);

            submit.addActionListener((ActionEvent s) -> {
                this.adminName = userName.getText();
                this.adminId = Integer.parseInt(passw.getText());
                this.valid = validateCredentials(adminName, adminId);
                if (valid.contains("VALID")) {
                    guiSerOutput(valid);
                    detailsEntered = true;
                    search.setVisible(detailsEntered);
                    addCourse.setVisible(detailsEntered);
                    remove.setVisible(detailsEntered);
                    viewAll.setVisible(detailsEntered);
                    viewStuCourses.setVisible(detailsEntered);
                    enterDetails.setVisible(!detailsEntered);
                    addNewCourse.setVisible(detailsEntered);
                    runCourse.setVisible(detailsEntered);
                    checkClassList.setVisible(detailsEntered);
                    quit.setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(null, "Incorrect Credentials!");
                    enterDetails.setVisible(true);
                    quit.setVisible(true);
                    detailsEntered = false;
                }
                log.dispose();
            });
            guiSerOutput(valid);
        });
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
        addNewCourse.addActionListener((ActionEvent e) -> {
            guiSerOutput(addNewCourse());
        });
        runCourse.addActionListener((ActionEvent e) -> {
            guiSerOutput(runTheCourse());
        });
        checkClassList.addActionListener((ActionEvent e) -> {
            guiSerOutput(classlist());
        });
        quit.addActionListener((ActionEvent e) -> {
            quit();
        });

        return jp;
    }

    /**
     * gets the list of students registered in course
     * 
     * @return student list
     */
    public String classlist() {
        callForInput(true);
        return theView.getAction().showClasslist(this.cName, this.cID);
    }

    /**
     * adds a new course to database
     * 
     * @return
     */
    public String addNewCourse() {
        callForInput(true);
        callInputForCap();
        return theView.getAction().addNewCourse(this.cName, this.cID, this.cSec, this.cap);
    }

    /**
     * Sets cap data field via user input
     * 
     */
    public void callInputForCap() {
        int c = 1;
        try {
            c = Integer.parseInt(JOptionPane.showInputDialog(null, "Enter the Cap size: "));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Invalid cap size entered, Please try again", "Error!",
                    JOptionPane.ERROR_MESSAGE);
        }
        this.cap = c;
    }

    /**
     * statement regarding if the Course can be run
     * 
     * @return number ofs tudents in course etc
     */
    public String runTheCourse() {
        callForInput(true);
        return theView.getAction().checkifCourseCanRun(this.cName, this.cID);
    }

    /**
     * I am guessing an Admin will find the student in database first then remove
     * from them Returns student courses to the buttons, student courses are
     * received from server through clientCommunication
     * 
     * @return studentCourses String
     */
    public String studentCourses() {
        getStudentInfo();
        return theView.getAction().showStudentCourses(); // make a new admin show function or pass name & id
    }

    /**
     * I am guessing an Admin will find the student in database first then remove
     * from them Invokes remove course in server
     * 
     * @return confirmation String confirming the status of removal
     */
    public String removeCourse() {
        getStudentInfo();
        callForInput(false);
        return theView.getAction().removeCourse(cName, cID);
    }

    /**
     * I am guessing an Admin will find the student in database first then remove
     * from them Invokes the server to add the course
     * 
     * @return String confirmation of addition of course
     */
    public String addTheCourse() {
        getStudentInfo();
        callForInput(true); // true prompts for input of section number
        return theView.getAction().addCourse(cName, cID, cSec);
    }

    /**
     * Validation function to confirm credentials with reponse
     * 
     * @param n name of user
     * @param p id of user
     * @return server response
     */
    public String validateCredentials(String n, int p) {
        String a = theView.getAction().passAdminInfo(n, p);
        if (a != null) {
            return a;
        }
        return "# no reponse #";
    }

    /**
     * Sets student name and id in order to access the student's records
     * 
     */
    public void getStudentInfo() {
        while (true) {
            try {
                studentName = JOptionPane.showInputDialog(null, "Please enter Student name to access");
                studentName = studentName.toUpperCase();
                studentId = Integer.parseInt(JOptionPane.showInputDialog(null, "Please enter Student's ID number"));
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Invalid details", "Error!", JOptionPane.ERROR_MESSAGE);
                continue;
            }
            break;
        }

        if (studentName.contains(" ")) {
            String[] names = studentName.split(" ");
            if (names[0].isEmpty()) {
                studentName = names[1]; // return second words if first words entered was space or empty
            }
            studentName = names[0];
        }
    }

    @Override
    String getInfo() {
        return null;
    }
}