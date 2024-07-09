package org.example.Behavioral;

public class Observer {
    // Observer interface
    public interface Observer {
        void update(String message); // Method to be called when the subject updates
    }

    // Subject class
    public class Subject {
        private List<Observer> observers = new ArrayList<>(); // List of observers

        public void addObserver(Observer observer) {
            observers.add(observer); // Add an observer
        }

        public void removeObserver(Observer observer) {
            observers.remove(observer); // Remove an observer
        }

        public void notifyObservers(String message) {
            for (Observer observer : observers) {
                observer.update(message); // Notify all observers with the message
            }
        }
    }

    // Concrete observers
    @Service
    public class EmailObserver implements Observer {
        @Override
        public void update(String message) {
            System.out.println("EmailObserver: " + message); // Handle the update for email
        }
    }

    @Service
    public class SmsObserver implements Observer {
        @Override
        public void update(String message) {
            System.out.println("SmsObserver: " + message); // Handle the update for SMS
        }
    }

    // Usage in a Spring Boot controller
    @RestController
    @RequestMapping("/notifications")
    public class NotificationController {
        private final Subject subject;

        @Autowired
        public NotificationController(EmailObserver emailObserver, SmsObserver smsObserver) {
            this.subject = new Subject(); // Initialize the subject
            this.subject.addObserver(emailObserver); // Add the EmailObserver
            this.subject.addObserver(smsObserver); // Add the SmsObserver
        }

        @PostMapping("/notify")
        public ResponseEntity<String> notifyObservers(@RequestBody String message) {
            subject.notifyObservers(message); // Notify all observers
            return ResponseEntity.ok("Observers notified"); // Return response
        }
    }

}
