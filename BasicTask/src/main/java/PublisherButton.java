import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class PublisherButton extends JButton implements EventPublisher {
    private List<Runnable> subscribers = new ArrayList<>();

    public PublisherButton(String text) {
        super(text);
        addActionListener(e -> notifySubscribers());
    }

    private void notifySubscribers() {
        subscribers.forEach(Runnable::run);
        subscribers.clear();
    }

    @Override
    public void subscribe(Runnable handler) {
        subscribers.add(handler);
    }
}