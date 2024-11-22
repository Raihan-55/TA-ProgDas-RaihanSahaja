package TypingAPP;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TypingAPP extends JFrame implements ActionListener {
    private JPanel mainPanel;
    private JLabel promptLabel, timerLabel, resultLabel, headerLabel;
    private JTextArea typingArea;
    private JButton startButton, historyButton, exitButton;

    private String[] sentences = {
            "Belajar cepat, menulis lebih tangkas.",
            "Latihan rutin, hasil tak tertandingi.",
            "Kecepatan mengetik adalah kunci kemenangan.",
            "Menguasai keyboard, menjadi juara.",
            "Jari-jari menari, pikiran tetap fokus.",
            "Ketepatan mengetik membawa hasil terbaik.",
            "Semakin sering mengetik, semakin cepat.",
            "Mengetik adalah seni dalam teknologi.",
            "Tantang dirimu, raih kecepatan baru.",
            "Keyboard adalah senjata, ketepatan adalah kekuatan.",
            "Melatih jari, melatih pikiran.",
            "Mengetik dengan akurasi adalah kebiasaan terbaik.",
            "Kecepatan mengetikmu mengalahkan batas waktu.",
            "Menghadapi tantangan dengan konsistensi.",
            "Jadilah raja kecepatan mengetik hari ini.",
            "Mengetik cepat, berpikir lebih jernih.",
            "Setiap kata adalah langkah menuju kemenangan.",
            "Jadikan mengetik sebagai kebiasaan sehari-hari.",
            "Keyboard modern, keahlian klasik.",
            "Taklukkan setiap kalimat dengan presisi.",
            "Tingkatkan kecepatan, raih hasil luar biasa.",
            "Kecepatan mengetik mencerminkan kecepatan berpikir.",
            "Tulis lebih cepat, tanpa melihat keyboard.",
            "Tantang dirimu untuk mengetik tanpa kesalahan.",
            "Jadilah ahli mengetik dengan latihan teratur.",
            "Ketepatan mengetik menunjukkan profesionalisme.",
            "Semakin banyak latihan, semakin percaya diri.",
            "Jari cepat, akurasi tajam.",
            "Ketepatan mengetik adalah seni yang perlu diasah.",
            "Tantangan mengetik ini untuk para juara.",
            "Semangat mengetik, semangat belajar.",
            "Melatih kecepatan, meningkatkan keterampilan.",
            "Setiap kata adalah langkah menuju kesempurnaan.",
            "Mengetik cepat membawa pengalaman baru.",
            "Kuasai keyboard, kuasai dunia digital.",
            "Ketepatan mengetik membawa hasil luar biasa.",
            "Semua orang bisa mengetik, tapi tidak semua cepat.",
            "Jadilah legenda di dunia mengetik.",
            "Kecepatan tanpa ketepatan adalah sia-sia.",
            "Mengetik adalah perjalanan, bukan tujuan.",
            "Teknik mengetik menentukan produktivitas.",
            "Latihan rutin, hasil tak terhingga.",
            "Setiap huruf adalah langkah menuju tujuan.",
            "Kuasai teknik mengetik dan kalahkan batasmu.",
            "Mengetik cepat adalah investasi jangka panjang.",
            "Setiap hari adalah kesempatan untuk lebih cepat.",
            "Mengetik cepat adalah keterampilan yang tak ternilai.",
            "Menguasai mengetik menghemat waktu.",
            "Mengetik dengan presisi adalah seni digital.",
            "Kecepatan mengetikmu adalah identitasmu.",

    };

    private String currentSentence;
    private long startTime;
    private boolean isStarted;

    private Timer timer;
    private int elapsedTime;
    private int correctWordCount;

    private List<String> gameHistory;

    public TypingAPP() {
        setTitle("Typing Practice Application");
        setSize(800, 600);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setIconImage(new ImageIcon("src/TypingAPP/img/icon.png").getImage());

        gameHistory = new ArrayList<>();

        // Pixel font
        Font pixelFont = loadPixelFont();

        // Main Panel
        mainPanel = new JPanel(new BorderLayout(20, 20));
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(Color.BLACK);

        // Header Label
        headerLabel = createLabel("TYPING HERO", 30, null, Color.CYAN);
        headerLabel.setFont(pixelFont.deriveFont(36f));
        headerLabel.setBorder(new EmptyBorder(10, 0, 20, 0));

        // Prompt Label
        promptLabel = createLabel("Press Start to Begin!", 18, Color.BLACK, Color.WHITE);
        promptLabel.setBorder(BorderFactory.createLineBorder(Color.MAGENTA, 3));

        // Typing Area
        typingArea = new JTextArea(5, 20);
        setupTypingArea(typingArea, new Font("Poppins", Font.PLAIN, 18));

        // Default message in Typing Area
        typingArea.setText("WELCOME TO TYPING HERO\nType here....");
        typingArea.setEnabled(false);


        JScrollPane scrollPane = new JScrollPane(typingArea);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        // Start Button
        startButton = new JButton("Start");
        setupButton(startButton, "Click to start or check typing!", pixelFont);

        // History Button
        historyButton = new JButton("History");
        setupButton(historyButton, "View past game results", pixelFont);

        exitButton = new JButton("Exit");
        setupButton(exitButton, "Click to exit the application", pixelFont);
        exitButton.addActionListener(e -> System.exit(0)); // Keluar dari aplikasi

        // Timer and Score Labels
        timerLabel = createLabel("Time: 0s", 16, null, Color.GREEN);
        resultLabel = createLabel("", 16, null, Color.RED);


        // Add action listener
        startButton.addActionListener(this);
        historyButton.addActionListener(this);

        // Key listener for Enter key
        typingArea.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    e.consume(); // Prevent newline

                    // Trigger button click based on button text
                    if (startButton.getText().equals("Start")) {
                        startButton.doClick(); // Simulate "Start" button click
                    } else if (startButton.getText().equals("Check")) {
                        startButton.doClick(); // Simulate "Check" button click
                    }
                }
            }
        });

        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
                .put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "startButtonClick");
        getRootPane().getActionMap().put("startButtonClick", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startButton.doClick(); // Simulate button click
            }
        });

        // Menambahkan KeyListener pada mainPanel agar bisa menangani event enter dari seluruh UI
        mainPanel.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    // Jika tombol "Start" atau "Check" aktif, simulasikan klik tombol
                    startButton.doClick();
                }
            }
        });
        mainPanel.setFocusable(true); // Agar mainPanel dapat menerima fokus untuk mendengarkan key events



        // Layout Top Panel
        JPanel topPanel = createPanel(new BorderLayout(), Color.BLACK);
        topPanel.add(headerLabel, BorderLayout.NORTH);
        topPanel.add(promptLabel, BorderLayout.CENTER);

        // Layout Bottom Panel
        JPanel bottomPanel = createPanel(new GridLayout(5, 1), Color.BLACK);
        bottomPanel.add(timerLabel);
        bottomPanel.add(resultLabel);
        bottomPanel.add(historyButton);
        bottomPanel.add(exitButton);

        // Main Panel Assembly
        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(bottomPanel, BorderLayout.EAST);
        mainPanel.add(startButton, BorderLayout.SOUTH);

        add(mainPanel);
        setVisible(true);
    }

    private Font loadPixelFont() {
        try {
            return Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("font/PressStart2P-Regular.ttf"));
        } catch (Exception e) {
            e.printStackTrace();
            return new Font("Monospaced", Font.BOLD, 20);
        }
    }

    private JLabel createLabel(String text, int fontSize, Color bgColor, Color fgColor) {
        JLabel label = new JLabel(text, JLabel.CENTER);
        if (bgColor != null) label.setOpaque(true);
        label.setBackground(bgColor);
        label.setForeground(fgColor);
        label.setFont(new Font("Monospaced", Font.BOLD, fontSize));
        return label;
    }

    private JPanel createPanel(LayoutManager layout, Color bgColor) {
        JPanel panel = new JPanel(layout);
        panel.setBackground(bgColor);
        return panel;
    }

    private void setupTypingArea(JTextArea area, Font font) {
        area.setEnabled(false);
        area.setBackground(Color.BLACK);
        area.setForeground(Color.GREEN);
        area.setLineWrap(true);
        area.setWrapStyleWord(true);
        area.setMargin(new Insets(10, 10, 10, 10));
        area.setBorder(BorderFactory.createLineBorder(Color.CYAN, 3));
        area.setFont(font.deriveFont(18f));
    }

    private void setupButton(JButton button, String toolTip, Font font) {
        button.setBackground(Color.BLACK);
        button.setForeground(Color.MAGENTA);
        button.setFont(font.deriveFont(20f));
        button.setFocusPainted(false);
        button.setToolTipText(toolTip);
        button.setBorder(BorderFactory.createLineBorder(Color.CYAN, 2));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    public void startPractice() {
        Random random = new Random();
        currentSentence = sentences[random.nextInt(sentences.length)];

        promptLabel.setText(currentSentence);
        typingArea.setText(""); // Clear default text
        typingArea.setEnabled(true);
        typingArea.requestFocus();

        startTime = System.currentTimeMillis();
        elapsedTime = 0;
        correctWordCount = 0;
        isStarted = true;
        resultLabel.setText("");

        if (timer != null) timer.stop();

        timer = new Timer(1000, e -> {
            elapsedTime++;
            timerLabel.setText("Time: " + elapsedTime + "s");
        });
        timer.start();
    }

    public void checkTyping() {
        if (timer != null) timer.stop();

        String typedText = typingArea.getText().trim();
        String[] typedWords = typedText.split("\\s+");
        String[] targetWords = currentSentence.split("\\s+");

        int correctWords = 0;
        for (int i = 0; i < Math.min(typedWords.length, targetWords.length); i++) {
            if (typedWords[i].equals(targetWords[i])) {
                correctWords++;
            }
        }

        correctWordCount = correctWords;
        double timeInSeconds = (System.currentTimeMillis() - startTime) / 1000.0;
        double wpm = (correctWordCount / timeInSeconds) * 60;
        double accuracy = ((double) correctWordCount / targetWords.length) * 100;

        resultLabel.setText(String.format("WPM: %.2f | Accuracy: %.2f%%", wpm, accuracy));
        gameHistory.add(String.format("WPM: %.2f | Accuracy: %.2f%% | Time: %.2fs", wpm, accuracy, timeInSeconds));

        typingArea.setEnabled(false);
        isStarted = false;
    }

    public void showHistory() {
        StringBuilder historyText = new StringBuilder("Game History:\n");
        for (String entry : gameHistory) {
            historyText.append(entry).append("\n");
        }

        JOptionPane.showMessageDialog(this, historyText.toString(), "Game History", JOptionPane.INFORMATION_MESSAGE);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == startButton) {
            if (!isStarted) {
                startPractice();
                startButton.setText("Check");
            } else {
                checkTyping();
                startButton.setText("Start");
            }
        } else if (e.getSource() == historyButton) {
            showHistory(); // Show history when History button is clicked
        }
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(TypingAPP::new);
    }
}
