import javax.swing.*;
import java.awt.*;


public class MainApp {
    public static void main(String[] args) {
        // Run on the Event Dispatch Thread
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Welcome to My Swing App");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(400, 300);

            JPanel panel = new JPanel();
            panel.setLayout(new FlowLayout());

            JLabel label = new JLabel("Enter your name:");
            JTextField textField = new JTextField(20);
            JButton button = new JButton("Submit");

            panel.add(label);
            panel.add(textField);
            panel.add(button);

            frame.add(panel);
            frame.setVisible(true);
        });
    }
}
