import View.MainFrame;
import YAML.YamlService;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.swing.SwingUtilities;
import DBService.DatabaseService;
import User.*;

public class App {
    public static void main(String[] args) {
        DatabaseService dbService = new DatabaseService();
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH-mm-ss");
        String fileName = "config/users-data-" + LocalDateTime.now().format(formatter) + ".yaml";
        
        YamlService yamlService = new YamlService();
        yamlService.generateYamlFile(fileName);
        
        List<User> yamlUsers = yamlService.loadUsersFromYaml(fileName);
        
        SwingUtilities.invokeLater(() -> {
            MainFrame frame = new MainFrame(yamlUsers, dbService);
            frame.setVisible(true);
        });
        
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            dbService.close();
        }));
    }
}