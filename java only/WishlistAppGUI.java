import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

class Item {
    private String name;
    private double price;
    private int priority;

    public Item(String name, double price, int priority) {
        this.name = name;
        this.price = price;
        this.priority = priority;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getPriority() {
        return priority;
    }

    @Override
    public String toString() {
        return "Name: " + name + ", Price: " + price + ", Priority: " + priority;
    }
}

public class WishlistAppGUI {
    private final ArrayList<Item> wishlist = new ArrayList<>();
    private final DefaultTableModel tableModel;

    public WishlistAppGUI() {
        JFrame frame = new JFrame("Wishlist Application");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);

        // Table for displaying wishlist items
        String[] columnNames = {"Name", "Price", "Priority"};
        tableModel = new DefaultTableModel(columnNames, 0);
        JTable table = new JTable(tableModel);

        JScrollPane tableScrollPane = new JScrollPane(table);
        frame.add(tableScrollPane, BorderLayout.CENTER);

        // Panel for buttons
        JPanel buttonPanel = new JPanel();
        JButton addButton = new JButton("Add Item");
        JButton removeButton = new JButton("Remove Item");
        JButton calculateButton = new JButton("Calculate Total Price");
        buttonPanel.add(addButton);
        buttonPanel.add(removeButton);
        buttonPanel.add(calculateButton);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        // Action Listeners
        addButton.addActionListener(e -> addItem());
        removeButton.addActionListener(e -> removeItem(table));
        calculateButton.addActionListener(e -> calculateTotalPrice());

        frame.setVisible(true);
    }

    private void addItem() {
        JTextField nameField = new JTextField();
        JTextField priceField = new JTextField();
        JTextField priorityField = new JTextField();

        JPanel panel = new JPanel(new GridLayout(3, 2));
        panel.add(new JLabel("Name:"));
        panel.add(nameField);
        panel.add(new JLabel("Price:"));
        panel.add(priceField);
        panel.add(new JLabel("Priority (1-5):"));
        panel.add(priorityField);

        int result = JOptionPane.showConfirmDialog(null, panel, "Add Item", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            try {
                String name = nameField.getText();
                double price = Double.parseDouble(priceField.getText());
                int priority = Integer.parseInt(priorityField.getText());

                if (priority < 1 || priority > 5) {
                    throw new IllegalArgumentException("Priority must be between 1 and 5.");
                }

                Item item = new Item(name, price, priority);
                wishlist.add(item);
                tableModel.addRow(new Object[]{item.getName(), item.getPrice(), item.getPriority()});

                JOptionPane.showMessageDialog(null, "Item added successfully!");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Invalid input. Please enter valid numbers for price and priority.", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void removeItem(JTable table) {
        int selectedRow = table.getSelectedRow();
        if (selectedRow >= 0) {
            wishlist.remove(selectedRow);
            tableModel.removeRow(selectedRow);
            JOptionPane.showMessageDialog(null, "Item removed successfully!");
        } else {
            JOptionPane.showMessageDialog(null, "Please select an item to remove.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void calculateTotalPrice() {
        double total = wishlist.stream().mapToDouble(Item::getPrice).sum();
        JOptionPane.showMessageDialog(null, "Total price of items in wishlist: $" + total);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(WishlistAppGUI::new);
    }
}
