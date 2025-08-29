package User;

import DBService.DatabaseService;
import com.github.javafaker.Faker;
import java.util.UUID;


/**
 * Конкретный класс фабрики пользователей.
 * Создает пользователей с помощью Faker
 */
public class FakerUserFactory extends UserFactory {
    private Faker faker = new Faker();
    private DatabaseService dbService;

    public FakerUserFactory(DatabaseService dbService) {
        this.dbService = dbService;
    }

    @Override
    public User createUser() {
        UUID id = UUID.randomUUID();
        String name = faker.name().fullName();
        String email = faker.internet().emailAddress();
        UserType userType = Math.random() > 0.5 ? UserType.PREMIUM : UserType.REGULAR;
        User user = new User(id, name, email, userType);
        
        if (dbService != null) {
            dbService.saveUser(user, "FAKER");
        }
        
        return user;
    }
}