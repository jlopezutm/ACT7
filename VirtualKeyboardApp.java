import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class VirtualKeyboardApp extends JFrame implements ActionListener {
    private JTextArea textArea;
    private List<String> pangramList;
    private String currentPangram;
    private int correctKeyPresses;
    private int incorrectKeyPresses;
    private List<Character> difficultKeys;

    public VirtualKeyboardApp() {
        setTitle("Virtual Keyboard App");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Create and add the virtual keyboard GUI using JButtons
        JPanel keyboardPanel = new JPanel(new GridLayout(4, 10));
        for (char c = 'A'; c <= 'Z'; c++) {
            JButton keyButton = new JButton(String.valueOf(c));
            keyButton.addActionListener(this);
            keyboardPanel.add(keyButton);
        }
        add(keyboardPanel, BorderLayout.CENTER);

        // Add the JTextArea to display the user's input
        textArea = new JTextArea(4, 40);
        add(textArea, BorderLayout.SOUTH);

        // Initialize variables
        pangramList = new ArrayList<>();
        currentPangram = "";
        correctKeyPresses = 0;
        incorrectKeyPresses = 0;
        difficultKeys = new ArrayList<>();

        // Load pangrams from a text file (one pangram per line)
        loadPangramsFromFile("pangrams.txt");

        // Start the app by showing a random pangram
        showRandomPangram();

        // Display statistics
        displayStatistics();

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void loadPangramsFromFile(String filePath) {
        // Read the pangrams from the file and store them in the pangramList
        // ...

        // For simplicity, I'm providing some example pangrams here:
        pangramList.add("The quick brown fox jumps over the lazy dog.");
        pangramList.add("Pack my box with five dozen liquor jugs.");
        pangramList.add("Jinxed wizards pluck ivy from the big quilt.");

        // Shuffle the pangramList for random pangram selection
        Collections.shuffle(pangramList);
    }

    private void showRandomPangram() {
        // Show a random pangram on the virtual keyboard GUI
        Random random = new Random();
        int index = random.nextInt(pangramList.size());
        currentPangram = pangramList.get(index);

        // Display the current pangram on the GUI (e.g., above the virtual keyboard)
        // ...
    }

    private void displayStatistics() {
        // Update the GUI to display correct and incorrect key presses
        // For example, you can add a label or a text area to display this information
        // ...

        // Example:
        JLabel statisticsLabel = new JLabel("Correct Key Presses: " + correctKeyPresses + " | Incorrect Key Presses: " + incorrectKeyPresses);
        add(statisticsLabel, BorderLayout.NORTH);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Handle the button click event when the user presses a key
        JButton keyButton = (JButton) e.getSource();
        char pressedKey = keyButton.getText().charAt(0);

        // Update the JTextArea with the user's input
        textArea.append(String.valueOf(pressedKey));

        // Check if the key pressed matches the current pangram
        if (currentPangram.length() > 0 && currentPangram.charAt(0) == pressedKey) {
            correctKeyPresses++;
            currentPangram = currentPangram.substring(1);
            if (currentPangram.isEmpty()) {
                // The user typed the current pangram correctly, show a new random pangram
                showRandomPangram();
            }
        } else {
            incorrectKeyPresses++;
            if (!difficultKeys.contains(pressedKey)) {
                difficultKeys.add(pressedKey);
            }
        }

        // Update the statistics display after each key press
        displayStatistics();
    }

    public void showDifficultyReport() {
        // Display a report with the keys that the user found difficult to press
        StringBuilder report = new StringBuilder("Difficulty Report:\n");
        for (char difficultKey : difficultKeys) {
            report.append(difficultKey).append("\n");
        }

        JOptionPane.showMessageDialog(this, report.toString(), "Difficulty Report", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new VirtualKeyboardApp());
    }
}
