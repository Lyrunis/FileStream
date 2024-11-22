package FileStreams;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class RandProductSearch extends JFrame {
    private JTextArea filteredTextArea;
    private JTextField searchField;
    private JButton searchButton;
    private JButton quitButton;

    private final Path filePath;
    
    public RandProductSearch() {
        // products.dat file created in directory
        filePath = Paths.get("products.dat");

        setTitle("Product Search Application");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 400);
        setLayout(new BorderLayout());

        // Text area for displaying filtered results
        filteredTextArea = createTextArea();

        // Scroll pane for the text area
        JScrollPane filteredScrollPane = new JScrollPane(filteredTextArea);

        // TextField for search
        JPanel topPanel = new JPanel(new BorderLayout());
        searchField = new JTextField();
        topPanel.add(new JLabel("Search: "), BorderLayout.WEST);
        topPanel.add(searchField, BorderLayout.CENTER);

        // Buttons at the bottom
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        searchButton = new JButton("Search");
        quitButton = new JButton("Quit");

        buttonPanel.add(searchButton);
        buttonPanel.add(quitButton);

        // Add components to the frame
        add(topPanel, BorderLayout.NORTH);
        add(filteredScrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        // Add button actions
        searchButton.addActionListener(new SearchFileAction());
        quitButton.addActionListener(e -> System.exit(0));

        setVisible(true);
    }

    private JTextArea createTextArea() {
        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setLineWrap(true); // Enable text wrapping
        textArea.setWrapStyleWord(true); // Wrap on word boundaries
        return textArea;
    }

    private class SearchFileAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (!Files.exists(filePath)) {
                JOptionPane.showMessageDialog(RandProductSearch.this, 
                        "The file 'products.dat' was not found in the current directory.", 
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String searchString = searchField.getText().trim();
            if (searchString.isEmpty()) {
                JOptionPane.showMessageDialog(RandProductSearch.this, 
                        "Please enter a search string.", 
                        "Error", JOptionPane.WARNING_MESSAGE);
                return;
            }

            try {
                filteredTextArea.setText("");
                List<String> lines = Files.lines(filePath)
                        .filter(line -> line.toLowerCase().contains(searchString.toLowerCase()))
                        .collect(Collectors.toList());

                if (lines.isEmpty()) {
                    filteredTextArea.setText("No matches found.");
                } else {
                    // Print column headers
                    filteredTextArea.append("ID\tName\tDesc\t\tPrice\n");

                    // Print the formatted product details
                    for (String line : lines) {
                        Product product = Product.fromRandomAccessRecord(line);
                        filteredTextArea.append(String.format("%s\t%s\t%s\t%.2f\n", 
                                product.getID(), 
                                product.getName(), 
                                product.getDescription(), 
                                product.getCost()));
                    }
                }
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(RandProductSearch.this, 
                        "Error reading file: " + ex.getMessage(), 
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // Launch main for application
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new RandProductSearch());
    }
}
