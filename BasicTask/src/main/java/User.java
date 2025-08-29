import java.util.UUID;

public class User {
    private UUID id;
    private String name;
    private String email;
    private UserType userType;

    public User(UUID id, String name, String email, UserType userType) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.userType = userType;
    }

    public UserType getUserType() {
        return userType;
    }

    public String getInfo() {
        return name + " | " + email;
    }
    
    public String getInfoWithType() {
        return name + " | " + email + " [" + userType + "]";
    }
}