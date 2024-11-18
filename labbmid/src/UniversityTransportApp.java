import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// Input Layer
class TransportInput {
    public String getPaymentStatus() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Have you already paid? (yes/no):");
        return scanner.nextLine();
    }

    public String getTimingStatus() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Are you within timing restrictions? (yes/no):");
        return scanner.nextLine();
    }
}

// Processing Layer
class PipeAndFilter {
    private boolean alreadyPaid;
    private boolean timingRestrictions;

    public void setPaymentStatus(String status) {
        this.alreadyPaid = "yes".equalsIgnoreCase(status);
    }

    public void setTimingStatus(String status) {
        this.timingRestrictions = "yes".equalsIgnoreCase(status);
    }

    public String processUniversityTransport() {
        if (alreadyPaid && timingRestrictions) {
            return "Access granted: You can use the university transport.";
        } else if (!alreadyPaid) {
            return "Access denied: Please pay the transport fee first.";
        } else if (!timingRestrictions) {
            return "Access denied: You are outside the allowed transport timings.";
        } else {
            return "Invalid status.";
        }
    }
}

// Output Layer
class TransportOutput {
    public void displayMessage(String message) {
        System.out.println("Message: " + message);
    }
}

// Observer Pattern
interface Observer {
    void update(String message);
}

// Subject interface: For managing observers
interface Subject {
    void addObserver(Observer observer);
    void removeObserver(Observer observer);
    void notifyObservers(String message);
}

// Notification Layer: Manages observers and notifications
class TransportNotifier implements Subject {
    private final List<Observer> observers = new ArrayList<>();

    @Override
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers(String message) {
        for (Observer observer : observers) {
            observer.update(message);
        }
    }

    // User class: Implements the Observer interface
    class User implements Observer {
        private final String name;

        public User(String name) {
            this.name = name;
        }

        @Override
        public void update(String message) {
            System.out.println(name + " received notification: " + message);
        }
    }
}

// Main Application: Combines all layers and runs the workflow
public class UniversityTransportApp {
    public static void main(String[] args) {
        // Input Layer
        TransportInput input = new TransportInput();

        // Processing Layer
        PipeAndFilter processor = new PipeAndFilter();

        // Output Layer
        TransportOutput output = new TransportOutput();

        // Notification Layer
        TransportNotifier notifier = new TransportNotifier();
        TransportNotifier.User user1 = notifier.new User("Student1");
        TransportNotifier.User user2 = notifier.new User("Student2");

        notifier.addObserver(user1);
        notifier.addObserver(user2);

        // Workflow
        String paymentStatus = input.getPaymentStatus();       // Step 1: Get payment status
        processor.setPaymentStatus(paymentStatus);

        String timingStatus = input.getTimingStatus();         // Step 2: Get timing restrictions
        processor.setTimingStatus(timingStatus);

        String result = processor.processUniversityTransport(); // Step 3: Process transport
        output.displayMessage(result);                         // Step 4: Display result

        // Notify observers
        notifier.notifyObservers("Transport processing complete. Result: " + result);
    }
}
