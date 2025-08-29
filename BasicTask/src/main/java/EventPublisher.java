@FunctionalInterface
public interface EventPublisher {
    void subscribe(Runnable handler);
}