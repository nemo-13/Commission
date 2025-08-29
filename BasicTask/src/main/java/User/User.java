package User;

import java.util.UUID;

/**
 * Класс, представляющий пользователя системы.
 * Содержит основную информацию о пользователе: идентификатор, имя, email и тип.
 */
public class User {
    private UUID id;
    private String name;
    private String email;
    private UserType userType;

    public User() {
    }

    public User(UUID id, String name, String email, UserType userType) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.userType = userType;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public String getInfo() {
        return name + " | " + email;
    }
    
    public String getInfoWithType() {
        return name + " | " + email + " [" + userType + "]";
    }
}