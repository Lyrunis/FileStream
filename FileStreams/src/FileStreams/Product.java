package FileStreams;

import java.io.Serializable;

public class Product implements Serializable {
    private String name;
    private String description;
    private final String ID;
    private double cost;

    // fixed length padding
    static final int NAME_LENGTH = 35;
    static final int DESCRIPTION_LENGTH = 75;
    static final int ID_LENGTH = 6;

    // Constructor
    public Product(String name, String description, String ID, double cost) {
        this.name = name.length() > NAME_LENGTH ? name.substring(0, NAME_LENGTH) : name;
        this.description = description.length() > DESCRIPTION_LENGTH ? description.substring(0, DESCRIPTION_LENGTH) : description;
        this.ID = ID.length() > ID_LENGTH ? ID.substring(0, ID_LENGTH) : ID;
        this.cost = cost;
    }
    
    // Getters
    public String getID() {
        return ID;
    }
    
    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public double getCost() {
        return cost;
    }

    // Method to get a properly padded name for RAF
    public String getFormattedName() {
        return String.format("%-" + NAME_LENGTH + "s", name); // Left-pad with spaces
    }

    // Method to get a properly padded description for RAF
    public String getFormattedDescription() {
        return String.format("%-" + DESCRIPTION_LENGTH + "s", description); // Left-pad with spaces
    }

    // Method to get a fixed-size formatted ID for RAF
    public String getFormattedID() {
        return String.format("%-" + ID_LENGTH + "s", ID); // Left-pad with spaces
    }

    // Method to convert a product to a RAF format string
    public String toRandomAccessFormat() {
        return getFormattedID() + getFormattedName() + getFormattedDescription() + cost;
    }
    
    public static Product fromRandomAccessRecord(String record) {
        String id = record.substring(0, ID_LENGTH).trim(); //trim ID
        String name = record.substring(ID_LENGTH, ID_LENGTH + NAME_LENGTH).trim(); //trim Name
        String description = record.substring(ID_LENGTH + NAME_LENGTH, ID_LENGTH + NAME_LENGTH + DESCRIPTION_LENGTH).trim(); //trim Description
        double cost = Double.parseDouble(record.substring(ID_LENGTH + NAME_LENGTH + DESCRIPTION_LENGTH).trim()); 
        return new Product(name, description, id, cost);
    }

    // Convert to CSV format
    public String toCSV() {
        return ID + "," + name + "," + description + "," + cost;
    }

    @Override
    public String toString() {
        return "Product{" +
               "ID='" + ID + '\'' +
               ", name='" + name + '\'' +
               ", description='" + description + '\'' +
               ", cost=" + cost +
               '}';
    }
}
