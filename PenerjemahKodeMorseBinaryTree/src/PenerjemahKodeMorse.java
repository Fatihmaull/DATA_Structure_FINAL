import java.awt.*;
import javax.sound.sampled.*;
import javax.swing.*;

public class PenerjemahKodeMorse {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (UnsupportedLookAndFeelException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
                System.err.println("An error occurred: " + e.getMessage());
            }            
            new PenerjemahKodeMorse().createAndShowGUI();
        });
    }

    // Bagian GUI
    private void createAndShowGUI() {
        JFrame frame = new JFrame("Morse Code Translator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 500);
        frame.setLayout(new BorderLayout());

        // Area input
        JTextArea inputArea = new JTextArea(5, 20);
        inputArea.setFont(new Font("SansSerif", Font.BOLD, 18));
        inputArea.setLineWrap(true);
        inputArea.setWrapStyleWord(true);
        inputArea.setBorder(BorderFactory.createTitledBorder("Input Text / Morse Code"));

        // Area output
        JTextArea outputArea = new JTextArea(5, 20);
        outputArea.setFont(new Font("Serif", Font.ITALIC, 18));
        outputArea.setLineWrap(true);
        outputArea.setWrapStyleWord(true);
        outputArea.setEditable(false);
        outputArea.setBorder(BorderFactory.createTitledBorder("Output"));

        // Tombol
        JButton encodeButton = new JButton("Encode");
        JButton decodeButton = new JButton("Decode");
        JButton playButton = new JButton("Play Morse");

        // Pengaturan ukuran tombol
        encodeButton.setPreferredSize(new Dimension(100, 30));
        decodeButton.setPreferredSize(new Dimension(100, 30));
        playButton.setPreferredSize(new Dimension(100, 30));

        // Logika Morse Tree dan Player
        MorseTree morseTree = new MorseTree();
        MorsePlayer morsePlayer = new MorsePlayer();

        encodeButton.addActionListener(_ -> {
            String text = inputArea.getText();
            String morse = morseTree.encode(text);
            outputArea.setText(morse);
        });

        decodeButton.addActionListener(_ -> {
            String morse = inputArea.getText();
            String text = morseTree.decode(morse);
            outputArea.setText(text);
        });

        playButton.addActionListener(_ -> {
            String morse = outputArea.getText();
            morsePlayer.playMorse(morse);
        });

        // Panel tombol
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.add(encodeButton);
        buttonPanel.add(decodeButton);
        buttonPanel.add(playButton);

        // Panel utama
        JPanel textPanel = new JPanel(new GridLayout(2, 1, 10, 10));
        textPanel.add(new JScrollPane(inputArea));
        textPanel.add(new JScrollPane(outputArea));

        frame.add(textPanel, BorderLayout.CENTER);
        frame.add(buttonPanel, BorderLayout.SOUTH);
        frame.setVisible(true);
    }

    // class MorseNode
    private static class MorseNode {
        char letter;
        MorseNode dot;
        MorseNode dash;

        public MorseNode(char letter) {
            this.letter = letter;
        }
    }

    // class MorseTree
    private static class MorseTree {
        private final MorseNode root;

        public MorseTree() {
            root = new MorseNode('\0');
            initializeTree();
        }

        private void initializeTree() {
            addMorseCode('A', ".-");
            addMorseCode('B', "-...");
            addMorseCode('C', "-.-.");
            addMorseCode('D', "-..");
            addMorseCode('E', ".");
            addMorseCode('F', "..-.");
            addMorseCode('G', "--.");
            addMorseCode('H', "....");
            addMorseCode('I', "..");
            addMorseCode('J', ".---");
            addMorseCode('K', "-.-");
            addMorseCode('L', ".-..");
            addMorseCode('M', "--");
            addMorseCode('N', "-.");
            addMorseCode('O', "---");
            addMorseCode('P', ".--.");
            addMorseCode('Q', "--.-");
            addMorseCode('R', ".-.");
            addMorseCode('S', "...");
            addMorseCode('T', "-");
            addMorseCode('U', "..-");
            addMorseCode('V', "...-");
            addMorseCode('W', ".--");
            addMorseCode('X', "-..-");
            addMorseCode('Y', "-.--");
            addMorseCode('Z', "--..");
            addMorseCode('1', ".----");
            addMorseCode('2', "..---");
            addMorseCode('3', "...--");
            addMorseCode('4', "....-");
            addMorseCode('5', ".....");
            addMorseCode('6', "-....");
            addMorseCode('7', "--...");
            addMorseCode('8', "---..");
            addMorseCode('9', "----.");
            addMorseCode('0', "-----");
            addMorseCode('.', ".-.-.-");
            addMorseCode(',', "--..--");
            addMorseCode('?', "..--..");
            addMorseCode('!', "-.-.--");
            addMorseCode(' ', "/");
        }

        private void addMorseCode(char letter, String code) {
            MorseNode current = root;
            for (char c : code.toCharArray()) {
                if (c == '.') {
                    if (current.dot == null) current.dot = new MorseNode('\0');
                    current = current.dot;
                } else if (c == '-') {
                    if (current.dash == null) current.dash = new MorseNode('\0');
                    current = current.dash;
                }
            }
            current.letter = letter;
        }

        public String encode(String text) {
            StringBuilder morse = new StringBuilder();
            for (char c : text.toUpperCase().toCharArray()) {
                if (c == ' ') morse.append("/ ");
                else morse.append(encodeCharacter(c, root, "")).append(" ");
            }
            return morse.toString().trim();
        }

        private String encodeCharacter(char letter, MorseNode node, String path) {
            if (node == null) return null;
            if (node.letter == letter) return path;
            String left = encodeCharacter(letter, node.dot, path + ".");
            return (left != null) ? left : encodeCharacter(letter, node.dash, path + "-");
        }

        public String decode(String morse) {
            StringBuilder text = new StringBuilder();
            for (String code : morse.split(" ")) {
                if (code.equals("/")) text.append(" ");
                else text.append(decodeCharacter(code, root));
            }
            return text.toString();
        }

        private char decodeCharacter(String code, MorseNode node) {
            MorseNode current = node;
            for (char c : code.toCharArray()) {
                if (c == '.') current = current.dot;
                else if (c == '-') current = current.dash;
                if (current == null) return '?';
            }
            return current.letter;
        }
    }

    // class MorsePlayer
    private static class MorsePlayer {
        private final int SAMPLE_RATE = 8000;

        public void playMorse(String morse) {
            for (char c : morse.toCharArray()) {
                switch (c) {
                    case '.' -> playTone(800, 200);
                    case '-' -> playTone(800, 600);
                    case ' ' -> sleep(200);
                    case '/' -> sleep(600);
                    default -> System.err.println("Invalid Morse character: " + c);
                }                
            }
        }

        private void playTone(int hz, int duration) {
            try {
                byte[] buf = new byte[SAMPLE_RATE * duration / 1000];
                for (int i = 0; i < buf.length; i++) {
                    double angle = 2.0 * Math.PI * i / (SAMPLE_RATE / hz);
                    buf[i] = (byte) (Math.sin(angle) * 127);
                }
                AudioFormat af = new AudioFormat(SAMPLE_RATE, 8, 1, true, false);
                try (SourceDataLine sdl = AudioSystem.getSourceDataLine(af)) {
                    sdl.open(af);
                    sdl.start();
                    sdl.write(buf, 0, buf.length);
                    sdl.drain();
                }                
            } catch (LineUnavailableException e) {
                System.err.println("An error occurred: " + e.getMessage());
            }
        }

        private void sleep(int millis) {
            try {
                Thread.sleep(millis);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
