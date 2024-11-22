package FileStreams;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.io.RandomAccessFile;

public class RandProductMaker extends JFrame {
    private JTextField nameField, descriptionField, idField, costField, recordCountField;
    private JButton addButton;
    private int recordCount = 0;

    public RandProductMaker() {
        // Set up JFrame
        setTitle("RandProductMaker");
        setSize(658, 595);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Initialize components
        nameField = new JTextField(35);
        descriptionField = new JTextField(75);
        idField = new JTextField(6);
        costField = new JTextField(10);
        recordCountField = new JTextField(5); // Initialize recordCountField

        addButton = new JButton("Add Product");
        addButton.setBackground(UIManager.getColor("Button.light"));
        getContentPane().setLayout(new GridLayout(0, 2, 0, 0));

        // Add components to frame
        getContentPane().add(new JLabel("Product Name:"));
        getContentPane().add(nameField);
        getContentPane().add(new JLabel("Description:"));
        getContentPane().add(descriptionField);
        getContentPane().add(new JLabel("Product ID:"));
        getContentPane().add(idField);
        getContentPane().add(new JLabel("Cost:"));
        getContentPane().add(costField);
        getContentPane().add(addButton);
        getContentPane().add(new JLabel("Record Count:"));
        getContentPane().add(recordCountField);

        // Listener for add
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addProduct();
            }
        });

        // Initialize the record count field with the current record count
        recordCountField.setEditable(false);
        recordCountField.setText(String.valueOf(recordCount));
    }

    private void addProduct() {
        // Get data from text fields
        String name = nameField.getText().trim();
        String description = descriptionField.getText().trim();
        String id = idField.getText().trim();
        double cost;

        // Validate input
        try {
            cost = Double.parseDouble(costField.getText().trim());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid cost value.");
            return;
        }

        if (name.isEmpty() || description.isEmpty() || id.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields.");
            return;
        }

        // Create Product object
        Product product = new Product(name, description, id, cost);

        // Write the product data to a RandomAccessFile
        try (RandomAccessFile file = new RandomAccessFile("products.dat", "rw")) {
            file.seek(file.length());
            file.writeBytes(product.toRandomAccessFormat() + "\n");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error saving product data.");
        }

        // Update record count
        recordCount++;
        recordCountField.setText(String.valueOf(recordCount));

        // Clear fields for next entry
        nameField.setText("");
        descriptionField.setText("");
        idField.setText("");
        costField.setText("");
    }

    public static void main(String[] args) {
        // Show the form
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new RandProductMaker().setVisible(true);
            }
        });
    }
}

