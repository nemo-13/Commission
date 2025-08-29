// UserFactoryTest.java
import User.User;
import User.UserType;
import User.FakerUserFactory;
import User.YamlUserFactory;
import User.CompositeUserFactory;
import DBService.DatabaseService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;

class MockDatabaseService {
    private List<User> savedUsers = new ArrayList<>();
    private List<String> dataSources = new ArrayList<>();
    private int sessionId = 1;
    
    public void saveUser(User user, String dataSource) {
        savedUsers.add(user);
        dataSources.add(dataSource);
    }
    
    public int getCurrentSessionId() {
        return sessionId;
    }
    
    public List<User> getSavedUsers() {
        return savedUsers;
    }
    
    public List<String> getDataSources() {
        return dataSources;
    }
    
    public void clear() {
        savedUsers.clear();
        dataSources.clear();
    }
}

public class UserFactoryTest {
    private List<User> testYamlUsers;
    private FakerUserFactory fakerFactory;
    private YamlUserFactory yamlFactory;
    private DatabaseService mockDbService;
    private CompositeUserFactory compositeFactory;

    @BeforeEach
    void setUp() {
        testYamlUsers = new ArrayList<>();
        testYamlUsers.add(new User(UUID.randomUUID(), "Test User 1", "test1@example.com", UserType.REGULAR));
        testYamlUsers.add(new User(UUID.randomUUID(), "Test User 2", "test2@example.com", UserType.PREMIUM));
        
        DatabaseService mockDbService = new DatabaseService();
        
        fakerFactory = new FakerUserFactory(mockDbService);
        yamlFactory = new YamlUserFactory(testYamlUsers, mockDbService);
        compositeFactory = new CompositeUserFactory(yamlFactory, fakerFactory);
    }

    @Test
    void testFakerUserFactory_ReturnsNotNull() {
        User user = fakerFactory.createUser();
        assertNotNull(user, "FakerFactory должна возвращать не null пользователя");
    }

    @Test
    void testFakerUserFactory_HasAllFieldsFilled() {
        User user = fakerFactory.createUser();
        
        assertNotNull(user.getId(), "ID не должен быть null");
        assertNotNull(user.getName(), "Name не должен быть null");
        assertNotNull(user.getEmail(), "Email не должен быть null");
        assertNotNull(user.getUserType(), "UserType не должен быть null");
        
        assertFalse(user.getName().isEmpty(), "Name не должен быть пустым");
        assertFalse(user.getEmail().isEmpty(), "Email не должен быть пустым");
    }

    @Test
    void testYamlUserFactory_ReturnsNotNull() {
        User user = yamlFactory.createUser();
        assertNotNull(user, "YamlFactory должна возвращать не null пользователя");
    }

    @Test
    void testYamlUserFactory_HasAllFieldsFilled() {
        User user = yamlFactory.createUser();
        
        assertNotNull(user.getId(), "ID не должен быть null");
        assertNotNull(user.getName(), "Name не должен быть null");
        assertNotNull(user.getEmail(), "Email не должен быть null");
        assertNotNull(user.getUserType(), "UserType не должен быть null");
        
        assertFalse(user.getName().isEmpty(), "Name не должен быть пустым");
        assertFalse(user.getEmail().isEmpty(), "Email не должен быть пустым");
    }
}