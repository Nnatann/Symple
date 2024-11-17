import java.awt.*;
import java.net.URI;
import java.util.Enumeration;
import javax.swing.*;

public class Main {
    private static DefaultListModel<String> friendsModel = new DefaultListModel<>();
    private static DefaultListModel<String> groupsModel = new DefaultListModel<>();
    private static int currentQuestionIndex = 0;
    private static final String[][] data = {
            {"Qual é a fórmula de Bhaskara?", "x = (-b ± √(b² - 4ac)) / 2a", "x = (-b ± √(b² + 4ac)) / 2a", "x = (-b ± √(c² - 4ac)) / 2a", "x = (-b ± √(b² - 2ac)) / 2a"},
            {"Quem foi o primeiro presidente do Brasil?", "Deodoro da Fonseca", "Getúlio Vargas", "Juscelino Kubitschek", "Fernando Henrique Cardoso"},
            {"Qual é a fórmula da água?", "CO2", "O2", "H2O2", "H2O"},
            {"Qual é a capital da França?", "Roma", "Paris", "Berlim", "Londres"},
            {"O que é a fotossíntese?", "Processo de respiração nas plantas", "Processo de produção de energia nas plantas", "Transporte de nutrientes nas plantas", "Reprodução nas plantas"},
            {"Quem pintou a Mona Lisa?", "Vincent van Gogh", "Pablo Picasso", "Claude Monet", "Leonardo da Vinci"},
            {"Qual é a principal fonte de energia da Terra?", "O Sol", "A eletricidade", "O petróleo", "O vento"},
            {"Qual é a capital do Japão?", "Pequim", "Tóquio", "Bangcoc", "Seul"},
            {"Em que ano o homem pisou na Lua?", "1979", "1969", "1959", "1989"},
            {"Quem escreveu 'Dom Casmurro'?", "Monteiro Lobato", "José de Alencar", "Clarice Lispector", "Machado de Assis"}
    };

    private static final String[] correctAnswers = {
        "Alternativa A", 
        "Alternativa A", 
        "Alternativa D", 
        "Alternativa B", 
        "Alternativa B", 
        "Alternativa D", 
        "Alternativa A", 
        "Alternativa B", 
        "Alternativa B", 
        "Alternativa D" 
    };

    public static void main(String[] args) {
        configureLookAndFeel();

        JFrame frame = new JFrame("Symple");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);

        try {
            ImageIcon icon = new ImageIcon("perfil.png");
            frame.setIconImage(icon.getImage());
        } catch (Exception e) {
            System.out.println("Erro ao carregar o ícone: " + e.getMessage());
        }
    
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setBackground(Color.BLACK);
        tabbedPane.setForeground(Color.WHITE);

        tabbedPane.addTab("Matemática", createMatematicaPanel());
        tabbedPane.addTab("Português", createPortuguesPanel());
        tabbedPane.addTab("História", createHistoriaPanel());
        tabbedPane.addTab("Ciências", createCienciasPanel());
        tabbedPane.addTab("Chat", createChatPanel());
        tabbedPane.addTab("Questões", createQuestoesPanel());

        frame.add(tabbedPane);
        frame.setVisible(true);
    }

    private static JPanel createQuestoesPanel() {
    JPanel panel = new JPanel(new BorderLayout(20, 20));
    panel.setBackground(new Color(40, 44, 52));
    panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

    
    JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
    headerPanel.setBackground(new Color(40, 44, 52));
    
    JLabel iconLabel = new JLabel("❓"); 
    iconLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 40));
    
    JLabel titleLabel = new JLabel("Questões");
    titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
    titleLabel.setForeground(Color.WHITE);
    
    headerPanel.add(iconLabel);
    headerPanel.add(Box.createHorizontalStrut(10));
    headerPanel.add(titleLabel);

    
    JPanel contentPanel = new JPanel(new BorderLayout(10, 10));
    contentPanel.setBackground(new Color(50, 54, 62));
    contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

   
    JTextArea questionArea = new JTextArea(data[currentQuestionIndex][0]);
    questionArea.setFont(new Font("Arial", Font.BOLD, 16));
    questionArea.setForeground(Color.WHITE);
    questionArea.setBackground(new Color(60, 64, 72));
    questionArea.setWrapStyleWord(true);
    questionArea.setLineWrap(true);
    questionArea.setEditable(false);
    questionArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

    
    JPanel alternativesPanel = new JPanel(new GridLayout(4, 1, 0, 10));
    alternativesPanel.setBackground(new Color(50, 54, 62));
    ButtonGroup buttonGroup = new ButtonGroup();

    for (int i = 1; i <= 4; i++) {
        JRadioButton radioButton = new JRadioButton(data[currentQuestionIndex][i]);
        radioButton.setFont(new Font("Arial", Font.PLAIN, 14));
        radioButton.setForeground(Color.WHITE);
        radioButton.setBackground(new Color(60, 64, 72));
        radioButton.setFocusPainted(false);
        buttonGroup.add(radioButton);
        alternativesPanel.add(radioButton);
    }

    contentPanel.add(questionArea, BorderLayout.NORTH);
    contentPanel.add(alternativesPanel, BorderLayout.CENTER);

    
    JButton checkAnswerButton = new JButton("Verificar Resposta");
    checkAnswerButton.setFont(new Font("Arial", Font.BOLD, 14));
    checkAnswerButton.setForeground(Color.WHITE);
    checkAnswerButton.setBackground(new Color(98, 0, 238));
    checkAnswerButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
    checkAnswerButton.setFocusPainted(false);
    checkAnswerButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

    checkAnswerButton.addActionListener(e -> {
        String selectedAnswer = getSelectedAnswer(buttonGroup);
        String correctAnswer = correctAnswers[currentQuestionIndex];

        String message = selectedAnswer.equals(correctAnswer) ? "Resposta Correta!" : "Resposta Incorreta. A resposta correta é: " + correctAnswer;
        JOptionPane.showMessageDialog(panel, message, "Resultado", JOptionPane.INFORMATION_MESSAGE);

        if (currentQuestionIndex < data.length - 1) {
            currentQuestionIndex++;
            updateQuestion(questionArea, alternativesPanel);
        } else {
            JOptionPane.showMessageDialog(panel, "Você completou todas as questões!", "Fim", JOptionPane.INFORMATION_MESSAGE);
        }
    });

    panel.add(headerPanel, BorderLayout.NORTH);
    panel.add(contentPanel, BorderLayout.CENTER);
    panel.add(checkAnswerButton, BorderLayout.SOUTH);

    return panel;
}

private static String getSelectedAnswer(ButtonGroup buttonGroup) {
    Enumeration<AbstractButton> buttons = buttonGroup.getElements();
    char letra = 'A';
    while (buttons.hasMoreElements()) {
        AbstractButton button = buttons.nextElement();
        if (button.isSelected()) {
            return "Alternativa " + letra;
        }
        letra++;
    }
    return "Nenhuma Alternativa Selecionada";
}

private static void updateQuestion(JTextArea questionArea, JPanel alternativesPanel) {
    questionArea.setText(data[currentQuestionIndex][0]);
    Component[] components = alternativesPanel.getComponents();
    for (int i = 0; i < components.length; i++) {
        if (components[i] instanceof JRadioButton) {
            ((JRadioButton) components[i]).setText(data[currentQuestionIndex][i + 1]);
            ((JRadioButton) components[i]).setSelected(false);
        }
    }
}

    private static void configureLookAndFeel() {
        UIManager.put("Panel.background", Color.DARK_GRAY);
        UIManager.put("TabbedPane.selectedBackground", Color.BLACK);
        UIManager.put("TabbedPane.selectedForeground", Color.WHITE);
        UIManager.put("Button.background", new Color(50, 50, 50));
        UIManager.put("Button.foreground", Color.WHITE);
        UIManager.put("TextField.background", new Color(80, 80, 80));
        UIManager.put("TextField.foreground", Color.WHITE);
        UIManager.put("Label.foreground", Color.WHITE);
        UIManager.put("TextArea.background", new Color(60, 60, 60));
        UIManager.put("TextArea.foreground", Color.WHITE);
    }

    private static JPanel createMatematicaPanel() {
    JPanel panel = new JPanel();
    panel.setBackground(new Color(40, 44, 52));
    panel.setLayout(new BorderLayout(20, 20));
    panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

    
    JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
    headerPanel.setBackground(new Color(40, 44, 52));
    
    JLabel iconLabel = new JLabel("📐"); 
    iconLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 40));
    
    JLabel titleLabel = new JLabel("Matemática");
    titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
    titleLabel.setForeground(new Color(255, 255, 255));
    
    headerPanel.add(iconLabel);
    headerPanel.add(Box.createHorizontalStrut(10));
    headerPanel.add(titleLabel);

    
    JPanel contentPanel = new JPanel();
    contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
    contentPanel.setBackground(new Color(50, 54, 62));
    contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

    String[] topics = {
        "• Álgebra Básica",
        "• Geometria",
        "• Trigonometria",
        "• Funções",
        "• Estatística"
    };

    for (String topic : topics) {
        JLabel topicLabel = new JLabel(topic);
        topicLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        topicLabel.setForeground(Color.WHITE);
        topicLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        topicLabel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
        contentPanel.add(topicLabel);
    }

    
    JButton watchLessonButton = new JButton("Assistir à Aula");
    watchLessonButton.setFont(new Font("Arial", Font.BOLD, 14));
    watchLessonButton.setForeground(Color.WHITE);
    watchLessonButton.setBackground(new Color(98, 0, 238));
    watchLessonButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
    watchLessonButton.setFocusPainted(false);
    watchLessonButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
    watchLessonButton.addActionListener(e -> openLink("https://www.youtube.com/playlist?list=PLaKmzX_hXWkLc31FnLJ_n6H_Jmmd-6TPi"));

    
    JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
    buttonPanel.setBackground(new Color(40, 44, 52));
    buttonPanel.add(watchLessonButton);

    panel.add(headerPanel, BorderLayout.NORTH);
    panel.add(contentPanel, BorderLayout.CENTER);
    panel.add(buttonPanel, BorderLayout.SOUTH);

    return panel;
}

private static JPanel createPortuguesPanel() {
    JPanel panel = new JPanel();
    panel.setBackground(new Color(40, 44, 52));
    panel.setLayout(new BorderLayout(20, 20));
    panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

    JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
    headerPanel.setBackground(new Color(40, 44, 52));
    
    JLabel iconLabel = new JLabel("📚");
    iconLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 40));
    
    JLabel titleLabel = new JLabel("Português");
    titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
    titleLabel.setForeground(new Color(255, 255, 255));
    
    headerPanel.add(iconLabel);
    headerPanel.add(Box.createHorizontalStrut(10));
    headerPanel.add(titleLabel);

    JPanel contentPanel = new JPanel();
    contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
    contentPanel.setBackground(new Color(50, 54, 62));
    contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

    String[] topics = {
        "• Gramática",
        "• Literatura",
        "• Interpretação de Texto",
        "• Redação",
        "• Figuras de Linguagem"
    };

    for (String topic : topics) {
        JLabel topicLabel = new JLabel(topic);
        topicLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        topicLabel.setForeground(Color.WHITE);
        topicLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        topicLabel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
        contentPanel.add(topicLabel);
    }

    JButton watchLessonButton = new JButton("Assistir à Aula");
    watchLessonButton.setFont(new Font("Arial", Font.BOLD, 14));
    watchLessonButton.setForeground(Color.WHITE);
    watchLessonButton.setBackground(new Color(98, 0, 238));
    watchLessonButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
    watchLessonButton.setFocusPainted(false);
    watchLessonButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
    watchLessonButton.addActionListener(e -> openLink("https://www.youtube.com/playlist?list=PLO3hBdfBc4pH9b8iGXGU0Syzogo0PsHBN"));

    JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
    buttonPanel.setBackground(new Color(40, 44, 52));
    buttonPanel.add(watchLessonButton);

    panel.add(headerPanel, BorderLayout.NORTH);
    panel.add(contentPanel, BorderLayout.CENTER);
    panel.add(buttonPanel, BorderLayout.SOUTH);

    return panel;
}

private static JPanel createHistoriaPanel() {
    JPanel panel = new JPanel();
    panel.setBackground(new Color(40, 44, 52));
    panel.setLayout(new BorderLayout(20, 20));
    panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

    JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
    headerPanel.setBackground(new Color(40, 44, 52));
    
    JLabel iconLabel = new JLabel("🏛️"); 
    iconLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 40));
    
    JLabel titleLabel = new JLabel("História");
    titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
    titleLabel.setForeground(new Color(255, 255, 255));
    
    headerPanel.add(iconLabel);
    headerPanel.add(Box.createHorizontalStrut(10));
    headerPanel.add(titleLabel);

    JPanel contentPanel = new JPanel();
    contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
    contentPanel.setBackground(new Color(50, 54, 62));
    contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

    String[] topics = {
        "• História Antiga",
        "• Idade Média",
        "• História Moderna",
        "• História Contemporânea",
        "• História do Brasil"
    };

    for (String topic : topics) {
        JLabel topicLabel = new JLabel(topic);
        topicLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        topicLabel.setForeground(Color.WHITE);
        topicLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        topicLabel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
        contentPanel.add(topicLabel);
    }

    JButton watchLessonButton = new JButton("Assistir à Aula");
    watchLessonButton.setFont(new Font("Arial", Font.BOLD, 14));
    watchLessonButton.setForeground(Color.WHITE);
    watchLessonButton.setBackground(new Color(98, 0, 238));
    watchLessonButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
    watchLessonButton.setFocusPainted(false);
    watchLessonButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
    watchLessonButton.addActionListener(e -> openLink("https://www.youtube.com/playlist?list=PLMra4G0-Z7pOmDWJfnFQXEG3P17E9erqs"));

    JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
    buttonPanel.setBackground(new Color(40, 44, 52));
    buttonPanel.add(watchLessonButton);

    panel.add(headerPanel, BorderLayout.NORTH);
    panel.add(contentPanel, BorderLayout.CENTER);
    panel.add(buttonPanel, BorderLayout.SOUTH);

    return panel;
}

private static JPanel createCienciasPanel() {
    JPanel panel = new JPanel();
    panel.setBackground(new Color(40, 44, 52));
    panel.setLayout(new BorderLayout(20, 20));
    panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

    JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
    headerPanel.setBackground(new Color(40, 44, 52));
    
    JLabel iconLabel = new JLabel("🧬"); 
    iconLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 40));
    
    JLabel titleLabel = new JLabel("Ciências");
    titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
    titleLabel.setForeground(new Color(255, 255, 255));
    
    headerPanel.add(iconLabel);
    headerPanel.add(Box.createHorizontalStrut(10));
    headerPanel.add(titleLabel);

    JPanel contentPanel = new JPanel();
    contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
    contentPanel.setBackground(new Color(50, 54, 62));
    contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

    String[] topics = {
        "• Biologia",
        "• Química",
        "• Física",
        "• Ecologia",
        "• Astronomia"
    };

    for (String topic : topics) {
        JLabel topicLabel = new JLabel(topic);
        topicLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        topicLabel.setForeground(Color.WHITE);
        topicLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        topicLabel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
        contentPanel.add(topicLabel);
    }

    JButton watchLessonButton = new JButton("Assistir à Aula");
    watchLessonButton.setFont(new Font("Arial", Font.BOLD, 14));
    watchLessonButton.setForeground(Color.WHITE);
    watchLessonButton.setBackground(new Color(98, 0, 238));
    watchLessonButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
    watchLessonButton.setFocusPainted(false);
    watchLessonButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
    watchLessonButton.addActionListener(e -> openLink("https://www.youtube.com/playlist?list=PLXUmiCKnwbRzfhbCPiQGArlUTVFmiMj1Q"));

    JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
    buttonPanel.setBackground(new Color(40, 44, 52));
    buttonPanel.add(watchLessonButton);

    panel.add(headerPanel, BorderLayout.NORTH);
    panel.add(contentPanel, BorderLayout.CENTER);
    panel.add(buttonPanel, BorderLayout.SOUTH);

    return panel;
}

private static JPanel createChatPanel() {
    JPanel chatPanel = new JPanel(new BorderLayout());
    chatPanel.setBackground(new Color(40, 44, 52));

    
    JTextArea chatArea = new JTextArea();
    chatArea.setEditable(false);
    chatArea.setBackground(new Color(60, 64, 72));
    chatArea.setForeground(Color.WHITE);
    chatArea.setFont(new Font("Arial", Font.PLAIN, 14));
    chatArea.setLineWrap(true);
    chatArea.setWrapStyleWord(true);
    JScrollPane chatScrollPane = new JScrollPane(chatArea);
    chatScrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

    
    JPanel inputPanel = new JPanel(new BorderLayout());
    inputPanel.setBackground(new Color(40, 44, 52));

    JTextField messageField = new JTextField();
    messageField.setBackground(new Color(80, 80, 80));
    messageField.setForeground(Color.WHITE);
    messageField.setFont(new Font("Arial", Font.PLAIN, 14));
    messageField.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

    JButton sendButton = createStyledButton("Enviar");
    sendButton.setPreferredSize(new Dimension(80, 40));
    sendButton.addActionListener(e -> {
        String message = messageField.getText().trim();
        if (!message.isEmpty()) {
            chatArea.append("Você: " + message + "\n");
            messageField.setText("");
            chatArea.setCaretPosition(chatArea.getDocument().getLength()); 
        }
    });

    inputPanel.add(messageField, BorderLayout.CENTER);
    inputPanel.add(sendButton, BorderLayout.EAST);

    
    JPanel sidePanel = new JPanel(new GridLayout(2, 1));
    sidePanel.setBackground(new Color(40, 44, 52));
    sidePanel.add(createFriendsPanel());
    sidePanel.add(createGroupsPanel());

    chatPanel.add(chatScrollPane, BorderLayout.CENTER);
    chatPanel.add(inputPanel, BorderLayout.SOUTH);
    chatPanel.add(sidePanel, BorderLayout.EAST);

    return chatPanel;
}

private static JPanel createFriendsPanel() {
    return createSidePanel("Amigos", friendsModel);
}

private static JPanel createGroupsPanel() {
    return createSidePanel("Grupos", groupsModel);
}

private static JPanel createSidePanel(String title, DefaultListModel<String> model) {
    JPanel sidePanel = new JPanel(new BorderLayout());
    sidePanel.setBackground(new Color(40, 44, 52));
    sidePanel.setBorder(BorderFactory.createTitledBorder(title));

    JList<String> list = new JList<>(model);
    list.setBackground(new Color(60, 64, 72));
    list.setForeground(Color.WHITE);
    list.setFont(new Font("Arial", Font.PLAIN, 14));
    JScrollPane scrollPane = new JScrollPane(list);
    scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

    JTextField inputField = new JTextField();
    JButton addButton = createStyledButton("Adicionar");

    addButton.addActionListener(e -> {
        String input = inputField.getText().trim();
        if (!input.isEmpty() && !model.contains(input)) {
            model.addElement(input);
            inputField.setText("");
        } else {
            JOptionPane.showMessageDialog(sidePanel, "Já existe ou nome inválido.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    });

    JPanel inputPanel = new JPanel(new BorderLayout());
    inputPanel.setBackground(new Color(40, 44, 52));
    inputPanel.add(inputField, BorderLayout.CENTER);
    inputPanel.add(addButton, BorderLayout.EAST);

    sidePanel.add(scrollPane, BorderLayout.CENTER);
    sidePanel.add(inputPanel, BorderLayout.SOUTH);

    return sidePanel;
}

private static JButton createStyledButton(String text) {
    JButton button = new JButton(text);
    button.setBackground(new Color(98, 0, 238));
    button.setForeground(Color.WHITE);
    button.setFont(new Font("Arial", Font.BOLD, 14));
    button.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
    button.setFocusPainted(false);
    button.setCursor(new Cursor(Cursor.HAND_CURSOR));
    return button;
}


    private static void openLink(String url) {
        try {
            Desktop desktop = Desktop.getDesktop();
            desktop.browse(new URI(url));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao abrir o link. Verifique se o navegador está disponível.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}
