import com.github.javafaker.Faker;
import java.util.UUID;

public class UserFactory {
    private Faker faker = new Faker();

    public User createUser() {
        UUID id = UUID.randomUUID();
        String name = faker.name().fullName();
        String email = faker.internet().emailAddress();
        double counter =  Math.random();
        if (counter > 0.5) {
            return new User(id, name, email, UserType.PREMIUM);
        }
        else {
            return new User(id, name, email, UserType.REGULAR);
        }
    }
}