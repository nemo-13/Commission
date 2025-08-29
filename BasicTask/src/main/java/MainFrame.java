import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    private PublisherButton showPremiumButton;
    private JTextArea textArea;
    private UserFactory userFactory;

    public MainFrame() {
        setTitle("User Management");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        userFactory = new UserFactory();
        initializeComponents();
    }

    private void initializeComponents() {
        JPanel buttonPanel = new JPanel();
        JButton createUsersButton = new JButton("Создать пользователей");
        showPremiumButton = new PublisherButton("Показать PREMIUM");

        createUsersButton.addActionListener(e -> createUsers());
        buttonPanel.add(createUsersButton);
        buttonPanel.add(showPremiumButton);

        textArea = new JTextArea();
        textArea.setEditable(false);

        add(buttonPanel, BorderLayout.NORTH);
        add(new JScrollPane(textArea), BorderLayout.CENTER);
    }

    private void createUsers() {
        textArea.setText("");
        for (int i = 0; i < 5; i++) {
            User user = userFactory.createUser();
            textArea.append(user.getInfoWithType() + "\n");
            if (user.getUserType() == UserType.PREMIUM) {
                showPremiumButton.subscribe(() -> 
                    textArea.append("PREMIUM: " + user.getInfo() + "\n")
                );
            }
        }
    }
}