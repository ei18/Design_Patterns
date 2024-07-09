package org.example.Creational;

public class FactoryMethod {

    // Interface for notification
    public interface Notification {
        void notifyUser(); // Method to be implemented by concrete notification types
    }

    // Implementations of the notification
    public class EmailNotification implements Notification {
        @Override
        public void notifyUser() {
            System.out.println("Sending an Email notification");
        }
    }

    public class SMSNotification implements Notification {
        @Override
        public void notifyUser() {
            System.out.println("Sending an SMS notification");
        }
    }

    // Factory Method
    public abstract class NotificationFactory {
        public abstract Notification createNotification(); // Factory method to be implemented by subclasses

        public void sendNotification() {
            Notification notification = createNotification(); // Create a notification
            notification.notifyUser(); // Send the notification
        }
    }

    public class EmailNotificationFactory extends NotificationFactory {
        @Override
        public Notification createNotification() {
            return new EmailNotification(); // Create an EmailNotification
        }
    }

    public class SMSNotificationFactory extends NotificationFactory {
        @Override
        public Notification createNotification() {
            return new SMSNotification(); // Create an SMSNotification
        }
    }

    // Spring configuration to inject the correct factory
    @Configuration
    public class NotificationConfig {
        @Bean
        @Primary
        public NotificationFactory emailNotificationFactory() {
            return new EmailNotificationFactory(); // Bean for EmailNotificationFactory
        }

        @Bean
        public NotificationFactory smsNotificationFactory() {
            return new SMSNotificationFactory(); // Bean for SMSNotificationFactory
        }
    }

    // Usage in a Spring Boot controller
    @RestController
    @RequestMapping("/notifications")
    public class NotificationController {
        private final NotificationFactory notificationFactory;

        public NotificationController(NotificationFactory notificationFactory) {
            this.notificationFactory = notificationFactory; // Inject the appropriate factory
        }

        @GetMapping("/send")
        public ResponseEntity<String> sendNotification() {
            notificationFactory.sendNotification(); // Use factory to send notification
            return ResponseEntity.ok("Notification sent"); // Return response
        }
    }
}
