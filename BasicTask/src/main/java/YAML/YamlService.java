package YAML;

import User.User;
import User.UserType;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.DumperOptions;
import com.github.javafaker.Faker;
import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class YamlService {
    
    public void generateYamlFile(String filePath) {
        try {
            File configDir = new File("config");
            if (!configDir.exists()) {
                configDir.mkdirs();
            }
            
            List<Map<String, Object>> usersData = new ArrayList<>();
            Faker faker = new Faker();
            
            for (int i = 0; i < 10; i++) {
                Map<String, Object> userData = Map.of(
                    "id", UUID.randomUUID().toString(),
                    "name", faker.name().fullName(),
                    "email", faker.internet().emailAddress(),
                    "userType", Math.random() > 0.5 ? "PREMIUM" : "REGULAR"
                );
                usersData.add(userData);
            }
            
            DumperOptions options = new DumperOptions();
            options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
            options.setPrettyFlow(true);
            
            Yaml yaml = new Yaml(options);
            try (FileWriter writer = new FileWriter(filePath)) {
                yaml.dump(usersData, writer);
            }
            
        } catch (IOException e) {
            System.err.println("Error generating YAML file: " + e.getMessage());
        }
    }
    
    public List<User> loadUsersFromYaml(String filePath) {
        try {
            Yaml yaml = new Yaml();
            try (FileInputStream inputStream = new FileInputStream(filePath)) {
                List<Map<String, Object>> data = yaml.load(inputStream);
                
                return data.stream().map(userData -> {
                    UUID id = UUID.fromString((String) userData.get("id"));
                    String name = (String) userData.get("name");
                    String email = (String) userData.get("email");
                    UserType userType = UserType.valueOf((String) userData.get("userType"));
                    return new User(id, name, email, userType);
                }).collect(Collectors.toList());
            }
        } catch (FileNotFoundException e) {
            System.out.println("YAML file not found: " + filePath);
        } catch (IOException e) {
            System.err.println("Error reading YAML file: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error parsing YAML data: " + e.getMessage());
        }
        
        return null;
    }
}