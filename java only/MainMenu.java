import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class MainMenu {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            new MainMenu().createAndShowGUI();
        });
    }

    private void createAndShowGUI() {
        JFrame frame = new JFrame("Main Menu");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);
        frame.setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("Main Menu", SwingConstants.CENTER);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 24));
        frame.add(titleLabel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new GridLayout(8, 1, 10, 10));

        buttonPanel.add(createMenuButton("1 - About", AboutGUI::new));
        buttonPanel.add(createMenuButton("2 - JavaMap",
                () -> SwingUtilities.invokeLater(() -> new JavaMap().setVisible(true))));
        buttonPanel.add(createMenuButton("3 - BioskopApp", () -> SwingUtilities.invokeLater(BioskopApp::new)));
        buttonPanel.add(createMenuButton("4 - AplikasiBelanja",
                () -> SwingUtilities.invokeLater(() -> new AplikasiBelanja().setVisible(true))));
        buttonPanel.add(createMenuButton("5 - PenerjemahKodeMorse",
                () -> SwingUtilities.invokeLater(() -> new PenerjemahKodeMorse().createAndShowGUI())));
        buttonPanel.add(createMenuButton("6 - WishlistAppGUI", () -> SwingUtilities.invokeLater(WishlistAppGUI::new)));
        buttonPanel.add(createMenuButton("7 - HistoryApp",
                () -> SwingUtilities.invokeLater(() -> new HistoryApp().setVisible(true))));
        buttonPanel.add(createMenuButton("8 - Exit", () -> System.exit(0)));

        frame.add(buttonPanel, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    private JButton createMenuButton(String text, Runnable guiLauncher) {
        JButton button = new JButton(text);
        button.addActionListener((ActionEvent e) -> SwingUtilities.invokeLater(guiLauncher));
        return button;
    }
}

// Placeholder class definitions for referenced GUIs.
// Replace these with the actual implementations or imports.
class AboutGUI extends JFrame {
    public AboutGUI() {
        setTitle("About");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        JLabel label = new JLabel(
                "Tentang: Made by Kelompok 1 =)",
                SwingConstants.CENTER);
        add(label);
        setVisible(true);
    }
}
