package User;

import java.util.List;
import DBService.DatabaseService;


public class YamlUserFactory extends UserFactory {
    private List<User> yamlUsers;
    private int yamlUserIndex = 0;
    private DatabaseService dbService;

    public YamlUserFactory(List<User> yamlUsers, DatabaseService dbService) {
        this.yamlUsers = yamlUsers;
        this.dbService = dbService;
    }

    @Override
    public User createUser() {
        if (yamlUsers != null && yamlUserIndex < yamlUsers.size()) {
            User user = yamlUsers.get(yamlUserIndex);
            yamlUserIndex++;
            
            // Сохраняем пользователя в базу данных
            if (dbService != null) {
                dbService.saveUser(user, "YAML");
            }
            
            return user;
        }
        return null;
    }
    
    public boolean hasMoreUsers() {
        return yamlUsers != null && yamlUserIndex < yamlUsers.size();
    }
}