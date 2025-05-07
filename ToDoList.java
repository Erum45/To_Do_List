import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.List;
import java.util.Collections;

class Task implements Serializable {
    String description;
    String dueDate;
    boolean completed;

    public Task(String description, String dueDate) {
        this.description = description;
        this.dueDate = dueDate;
        this.completed = false;
    }

    @Override
    public String toString() {
        return (completed ? "✔ " : "") + description + " [Due: " + dueDate + "]";
    }
}

public class ToDoList {
    private JFrame frame;
    private DefaultListModel<Task> taskListModel;
    private final JList<Task> taskList;
    private JTextField taskInput;
    private JTextField dueDateInput;

    public ToDoList() {
        frame = new JFrame("To-Do List");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 400);

        // Set mint green background for the frame
        frame.getContentPane().setBackground(new Color(189, 252, 201)); // Mint green

        taskListModel = new DefaultListModel<>();
        taskList = new JList<>(taskListModel);
        taskList.setBackground(new Color(240, 255, 240)); // Soft mint green background for the list
        taskList.setForeground(new Color(46, 139, 87)); // Darker green text for contrast
        taskList.setFont(new Font("Arial", Font.BOLD, 16)); // Larger text size

        JScrollPane scrollPane = new JScrollPane(taskList);

        taskInput = new JTextField();
        dueDateInput = new JTextField("YYYY-MM-DD");

        // Set mint green background and dark green text for input fields
        taskInput.setBackground(new Color(240, 255, 240));
        taskInput.setForeground(new Color(46, 139, 87));
        taskInput.setFont(new Font("Arial", Font.PLAIN, 16)); // Bigger text in input fields
        dueDateInput.setBackground(new Color(240, 255, 240));
        dueDateInput.setForeground(new Color(46, 139, 87));
        dueDateInput.setFont(new Font("Arial", Font.PLAIN, 16));

        JButton addButton = new JButton("Add Task");
        JButton removeButton = new JButton("Remove Task");
        JButton completeButton = new JButton("Complete Task");
        JButton saveButton = new JButton("Save Tasks");

        // Keep light pink color for icons and buttons
        addButton.setBackground(new Color(255, 182, 193)); // Light pink
        removeButton.setBackground(new Color(255, 182, 193));
        completeButton.setBackground(new Color(255, 182, 193));
        saveButton.setBackground(new Color(255, 182, 193));

        addButton.setForeground(Color.WHITE);
        removeButton.setForeground(Color.WHITE);
        completeButton.setForeground(Color.WHITE);
        saveButton.setForeground(Color.WHITE);

        // Increase button text size
        addButton.setFont(new Font("Arial", Font.BOLD, 14));
        removeButton.setFont(new Font("Arial", Font.BOLD, 14));
        completeButton.setFont(new Font("Arial", Font.BOLD, 14));
        saveButton.setFont(new Font("Arial", Font.BOLD, 14));

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String taskDescription = taskInput.getText();
                String dueDate = dueDateInput.getText();
                if (!taskDescription.isEmpty()) {
                    taskListModel.addElement(new Task(taskDescription, dueDate));
                    taskInput.setText("");
                    dueDateInput.setText("YYYY-MM-DD");
                    JOptionPane.showMessageDialog(frame, "Task added successfully!");
                }
            }
        });

        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = taskList.getSelectedIndex();
                if (selectedIndex != -1) {
                    taskListModel.remove(selectedIndex);
                    JOptionPane.showMessageDialog(frame, "Task removed.");
                }
            }
        });

        completeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = taskList.getSelectedIndex();
                if (selectedIndex != -1) {
                    Task task = taskListModel.get(selectedIndex);
                    task.completed = true;
                    taskListModel.set(selectedIndex, task);
                    JOptionPane.showMessageDialog(frame, "Task marked as completed!");
                }
            }
        });

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("tasks.dat"))) {
                    List<Task> tasks = Collections.list(taskListModel.elements());
                    oos.writeObject(tasks);
                    JOptionPane.showMessageDialog(frame, "Tasks saved successfully!");
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2));
        panel.setBackground(new Color(189, 252, 201)); // Mint green background for the panel

        panel.add(taskInput);
        panel.add(dueDateInput);
        panel.add(addButton);
        panel.add(removeButton);
        panel.add(completeButton);
        panel.add(saveButton);

        frame.add(scrollPane, BorderLayout.CENTER);
        frame.add(panel, BorderLayout.SOUTH);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ToDoList::new);
    }
}
