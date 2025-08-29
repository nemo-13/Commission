package User;

/**
 * Конкретный класс фабрики пользователей.
 * Создает пользователей в определенном порядке используя FakerUserFactory и YamlUserFactory
 */
public class CompositeUserFactory extends UserFactory {
    private YamlUserFactory yamlFactory;
    private FakerUserFactory fakerFactory;
    private boolean useYaml = true;

    public CompositeUserFactory(YamlUserFactory yamlFactory, FakerUserFactory fakerFactory) {
        this.yamlFactory = yamlFactory;
        this.fakerFactory = fakerFactory;
    }

    @Override
    public User createUser() {
        if (useYaml && yamlFactory.hasMoreUsers()) {
            return yamlFactory.createUser();
        } else {
            useYaml = false;
            return fakerFactory.createUser();
        }
    }
}