package View;

import User.User;
import DBService.DatabaseService;
import User.UserType;
import User.CompositeUserFactory;
import User.FakerUserFactory;
import User.YamlUserFactory;
import Listener.EventPublisher;
import Listener.PublisherButton;
import YAML.YamlService;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

public class MainFrame extends JFrame {
    private PublisherButton showPremiumButton;
    private JButton clearDbButton;
    private JTextArea textArea;
    private CompositeUserFactory userFactory;
    private List<User> yamlUsers;
    private DatabaseService dbService;

    public MainFrame(List<User> yamlUsers, DatabaseService dbService) {
        this.yamlUsers = yamlUsers;
        this.dbService = dbService;
        initializeUI();
    }

    private void initializeUI() {
        setTitle("User Management");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);

        YamlUserFactory yamlFactory = new YamlUserFactory(yamlUsers, dbService);
        FakerUserFactory fakerFactory = new FakerUserFactory(dbService);
        
        userFactory = new CompositeUserFactory(yamlFactory, fakerFactory);
        
        createComponents();
    }

    private void createComponents() {
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton createUsersButton = new JButton("Создать пользователей");
        showPremiumButton = new PublisherButton("Показать PREMIUM");
        clearDbButton = new JButton("Очистить БД");

        createUsersButton.addActionListener(e -> createUsers());
        clearDbButton.addActionListener(e -> clearDatabase());
        
        buttonPanel.add(createUsersButton);
        buttonPanel.add(showPremiumButton);
        buttonPanel.add(clearDbButton);

        textArea = new JTextArea();
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);

        add(buttonPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }

    private void createUsers() {
        textArea.setText("");
        textArea.append("Текущий сеанс: " + dbService.getCurrentSessionId() + "\n");
        textArea.append("\n");

        for (int i = 0; i < 5; i++) {
            User user = userFactory.createUser();
            
            textArea.append(user.getInfoWithType() + "\n");
            
            if (user.getUserType() == UserType.PREMIUM) {
                showPremiumButton.subscribe(() -> 
                    textArea.append("PREMIUM: " + user.getInfo() + "\n")
                );
            }
        }
        textArea.append("\n");
    }
    
    private void clearDatabase() {
        dbService.clearDatabase();
        textArea.setText("База данных очищена. Начат новый сеанс: " + dbService.getCurrentSessionId() + "\n");
    }
}