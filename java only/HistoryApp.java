import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class HistoryApp extends JFrame {
    private LinkedListBrowserHistory history;
    private JPanel historyPanel;
    private JTextField pageField;

    public HistoryApp() {
        history = new LinkedListBrowserHistory();

        // Set up the frame
        setTitle("Browser History");
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Set up the input panel
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BorderLayout());

        JLabel pageLabel = new JLabel("Enter Page URL:");
        inputPanel.add(pageLabel, BorderLayout.WEST);

        pageField = new JTextField();
        inputPanel.add(pageField, BorderLayout.CENTER);

        JButton visitButton = new JButton("Visit Page");
        inputPanel.add(visitButton, BorderLayout.EAST);

        add(inputPanel, BorderLayout.NORTH);

        // Set up the history panel
        historyPanel = new JPanel();
        historyPanel.setLayout(new BoxLayout(historyPanel, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(historyPanel);
        add(scrollPane, BorderLayout.CENTER);

        // Action listener for visiting a page
        visitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String page = pageField.getText();
                if (!page.isEmpty()) {
                    history.visitPage(page);
                    pageField.setText("");
                    refreshHistory();
                }
            }
        });
    }

    // Refresh the history panel with updated data
    private void refreshHistory() {
        historyPanel.removeAll(); // Clear the panel

        LinkedListBrowserHistory.Node current = history.getHead();
        while (current != null) {
            String page = current.page;

            JPanel pagePanel = new JPanel();
            pagePanel.setLayout(new BorderLayout());

            JLabel pageLabel = new JLabel(page);
            pagePanel.add(pageLabel, BorderLayout.CENTER);

            JButton deleteButton = new JButton("Delete");
            pagePanel.add(deleteButton, BorderLayout.EAST);

            // Action listener for delete button
            deleteButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    history.deletePage(page);
                    refreshHistory();
                }
            });

            historyPanel.add(pagePanel);
            current = current.next;
        }

        historyPanel.revalidate();
        historyPanel.repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                HistoryApp app = new HistoryApp();
                app.setVisible(true);
            }
        });
    }

    // Inner class for managing browser history
    class LinkedListBrowserHistory {
        private Node head;

        // Node to store page data
        class Node {
            String page;
            Node next;

            Node(String page) {
                this.page = page;
                this.next = null;
            }
        }

        // Add a new page to the linked list
        public void visitPage(String page) {
            Node newNode = new Node(page);
            if (head == null) {
                head = newNode;
            } else {
                Node temp = head;
                while (temp.next != null) {
                    temp = temp.next;
                }
                temp.next = newNode;
            }
        }

        // Delete a page by URL
        public boolean deletePage(String page) {
            if (head == null)
                return false;

            // If the first page is the target
            if (head.page.equals(page)) {
                head = head.next;
                return true;
            }

            // Search for the page and delete
            Node temp = head;
            while (temp.next != null) {
                if (temp.next.page.equals(page)) {
                    temp.next = temp.next.next;
                    return true;
                }
                temp = temp.next;
            }
            return false;
        }

        // Return the head of the linked list
        public Node getHead() {
            return head;
        }
    }
}