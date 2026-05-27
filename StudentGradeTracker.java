import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
class Student {
    String name;
    int grade;
    Student(String name, int grade) {
        this.name = name;
        this.grade = grade;
    }
}
public class StudentGradeTracker extends JFrame {
    ArrayList<Student> students = new ArrayList<>();
    JTextField nameField, gradeField;
    JButton addButton, reportButton, clearButton;
    JTable table;
    DefaultTableModel model;
    JLabel averageLabel, highestLabel, lowestLabel, totalLabel;
    public StudentGradeTracker() {
        setTitle("Student Grade Tracker");
        setSize(850, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(new Color(45, 62, 80));
        titlePanel.setBorder(new EmptyBorder(15, 10, 15, 10));
        JLabel title = new JLabel("STUDENT GRADE TRACKER");
        title.setFont(new Font("Arial", Font.BOLD, 28));
        title.setForeground(Color.WHITE);
        titlePanel.add(title);
        add(titlePanel, BorderLayout.NORTH);
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(6, 1, 10, 10));
        inputPanel.setBorder(new TitledBorder("Enter Student Details"));
        inputPanel.setBackground(new Color(240, 248, 255));
        inputPanel.setPreferredSize(new Dimension(250, 0));
        JLabel nameLabel = new JLabel("Student Name:");
        nameLabel.setFont(new Font("Arial", Font.BOLD, 16));
        nameField = new JTextField();
        nameField.setFont(new Font("Arial", Font.PLAIN, 16));
        JLabel gradeLabel = new JLabel("Student Grade:");
        gradeLabel.setFont(new Font("Arial", Font.BOLD, 16));
        gradeField = new JTextField();
        gradeField.setFont(new Font("Arial", Font.PLAIN, 16));
        addButton = createButton("Add Student", new Color(30, 30, 30));
        reportButton = createButton("Generate Report", new Color(30, 30, 30));
        clearButton = createButton("Clear Fields", new Color(231, 76, 60));
        inputPanel.add(nameLabel);
        inputPanel.add(nameField);
        inputPanel.add(gradeLabel);
        inputPanel.add(gradeField);
        inputPanel.add(addButton);
        inputPanel.add(reportButton);
        add(inputPanel, BorderLayout.WEST);
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        model = new DefaultTableModel();
        model.addColumn("Student Name");
        model.addColumn("Grade");
        table = new JTable(model);
        table.setRowHeight(30);
        table.setFont(new Font("Arial", Font.PLAIN, 15));
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 16));
        table.getTableHeader().setBackground(new Color(52, 73, 94));
        table.getTableHeader().setForeground(Color.BLACK);
        JScrollPane scrollPane = new JScrollPane(table);
        tablePanel.add(scrollPane, BorderLayout.CENTER);
        add(tablePanel, BorderLayout.CENTER);
        JPanel reportPanel = new JPanel();
        reportPanel.setLayout(new GridLayout(2, 2, 15, 15));
        reportPanel.setBorder(new TitledBorder("Performance Summary"));
        reportPanel.setBackground(new Color(245, 245, 245));
        averageLabel = createReportLabel("Average Score: 0");
        highestLabel = createReportLabel("Highest Score: 0");
        lowestLabel = createReportLabel("Lowest Score: 0");
        totalLabel = createReportLabel("Total Students: 0");
        reportPanel.add(averageLabel);
        reportPanel.add(highestLabel);
        reportPanel.add(lowestLabel);
        reportPanel.add(totalLabel);
        add(reportPanel, BorderLayout.SOUTH);
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    String name = nameField.getText().trim();
                    int grade = Integer.parseInt(gradeField.getText().trim());
                    if (name.isEmpty()) {
                        JOptionPane.showMessageDialog(null,
                                "Please enter student name");
                        return;
                    }
                    if (grade < 0 || grade > 100) {
                        JOptionPane.showMessageDialog(null,
                                "Grade must be between 0 and 100");
                        return;
                    }
                    students.add(new Student(name, grade));
                    model.addRow(new Object[]{name, grade});
                    JOptionPane.showMessageDialog(null,
                            "Student Added Successfully!");
                    nameField.setText("");
                    gradeField.setText("");
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null,
                            "Please enter valid grade");
                }
            }
        });
        reportButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (students.size() == 0) {
                    JOptionPane.showMessageDialog(null,
                            "No student data available");
                    return;
                }
                int total = 0;
                int highest = students.get(0).grade;
                int lowest = students.get(0).grade;
                for (Student s : students) {
                    total += s.grade;
                    if (s.grade > highest) {
                        highest = s.grade;
                    }
                    if (s.grade < lowest) {
                        lowest = s.grade;
                    }
                }
                double average = (double) total / students.size();
                averageLabel.setText("Average Score: " + String.format("%.2f", average));
                highestLabel.setText("Highest Score: " + highest);
                lowestLabel.setText("Lowest Score: " + lowest);
                totalLabel.setText("Total Students: " + students.size());
            }
        });

        setVisible(true);
    }
    JButton createButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 15));
        button.setBackground(color);
        button.setForeground(Color.BLACK);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return button;
    }
    JLabel createReportLabel(String text) {
        JLabel label = new JLabel(text, SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 16));
        label.setOpaque(true);
        label.setBackground(new Color(220, 230, 242));
        label.setBorder(new LineBorder(Color.GRAY, 1));
        return label;
    }
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(
                    UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        new StudentGradeTracker();
    }
}