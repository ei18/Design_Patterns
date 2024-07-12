# Design Patterns
Apply the different types of design patterns

# 1. Individual Research:
Research and select one design pattern from each category (Creative, Structural, Behavioral).
For each pattern selected, answer the following questions:
    What is the purpose of the pattern?
    In what situations is this pattern most useful to apply?

# Solution:

# Creational: Factory Method
## Purpose of the pattern:
The Factory Method pattern is used to define an interface for creating an object but allows subclasses to decide which class to instantiate. The Factory Method allows a class to delegate the responsibility of creation to its subclasses.
## Usage situations:
It is useful when the code should not depend on the concrete class of the objects it needs to create. It is especially useful in applications where you need to handle multiple types of related or derived objects.
### Example:
```
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
```
# Structural: Decorator
## Purpose of the pattern:
The Decorator pattern allows additional responsibilities to be added to an object dynamically. Decorators provide a flexible alternative to inheritance for extending functionality.
## Usage situations:
It is useful when you want to add additional functionalities to individual objects in a flexible way without altering the code of the original class.
### Example:
```
// Interface for the message service
public interface MessageService {
    void sendMessage(String message); // Method to send a message
}

// Basic implementation of the message service
@Service
public class BasicMessageService implements MessageService {
    @Override
    public void sendMessage(String message) {
        System.out.println("Sending message: " + message); // Print the message
    }
}

// Abstract decorator for the message service
public abstract class MessageServiceDecorator implements MessageService {
    protected MessageService wrapped;

    public MessageServiceDecorator(MessageService wrapped) {
        this.wrapped = wrapped; // Initialize the wrapped service
    }

    @Override
    public void sendMessage(String message) {
        wrapped.sendMessage(message); // Delegate the sendMessage call
    }
}

// Concrete decorator adding additional functionality
@Service
public class EncryptedMessageService extends MessageServiceDecorator {
    public EncryptedMessageService(MessageService wrapped) {
        super(wrapped); // Initialize the decorator with the wrapped service
    }

    @Override
    public void sendMessage(String message) {
        message = encrypt(message); // Encrypt the message
        super.sendMessage(message); // Call sendMessage on the wrapped service
    }

    private String encrypt(String message) {
        return "Encrypted(" + message + ")"; // Simple encryption logic
    }
}

// Spring configuration to inject the decorator
@Configuration
public class MessageServiceConfig {
    @Bean
    public MessageService messageService() {
        return new EncryptedMessageService(new BasicMessageService()); // Wrap BasicMessageService with EncryptedMessageService
    }
}

// Usage in a Spring Boot controller
@RestController
@RequestMapping("/messages")
public class MessageController {
    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService; // Inject the message service
    }

    @PostMapping("/send")
    public ResponseEntity<String> sendMessage(@RequestBody String message) {
        messageService.sendMessage(message); // Use the service to send the message
        return ResponseEntity.ok("Message sent"); // Return response
    }
}

```
# Behavioral: Observer
## Purpose of the pattern:
The Observer pattern defines a one-to-many dependency between objects so that when one object changes state, all its dependents are notified and updated automatically.
## Usage situations:
It is useful when there is an object that changes its state frequently and other objects need to be informed of these changes.
### Example:
```
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

```
-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
# 2. Case Analysis:
Imagine you are working on a project to develop a task management application. The application should allow users to create, edit and delete tasks, assign tasks to different users, and generate progress reports.
Prepare a document where you explain how you would apply the design patterns you researched to this application. Consider the following:
        What creative patterns could help you manage the creation of objects in the application?
        How could you use structural patterns to better organize classes and objects?
        What behavioral patterns would be useful for managing interaction and accountability between objects?

# Solution:

# Task Management Application

## 1. Creative Patterns:
For the task management application, the Factory Method pattern could be useful for creating different types of tasks. For example, we could have normal tasks, priority tasks and recurring tasks. Each type of task could have its own implementation and specific logic.
```
// Interface for Task
public interface Task {
    void performTask(); // Method to be implemented by concrete task types
}

// Implementations of Task
public class NormalTask implements Task {
    @Override
    public void performTask() {
        System.out.println("Performing a normal task"); // Perform normal task
    }
}

public class PriorityTask implements Task {
    @Override
    public void performTask() {
        System.out.println("Performing a priority task"); // Perform priority task
    }
}

public class RecurringTask implements Task {
    @Override
    public void performTask() {
        System.out.println("Performing a recurring task"); // Perform recurring task
    }
}

// Factory Method
public abstract class TaskFactory {
    public abstract Task createTask(); // Factory method to be implemented by subclasses

    public void executeTask() {
        Task task = createTask(); // Create a task
        task.performTask(); // Execute the task
    }
}

public class NormalTaskFactory extends TaskFactory {
    @Override
    public Task createTask() {
        return new NormalTask(); // Create a NormalTask
    }
}

public class PriorityTaskFactory extends TaskFactory {
    @Override
    public Task createTask() {
        return new PriorityTask(); // Create a PriorityTask
    }
}

public class RecurringTaskFactory extends TaskFactory {
    @Override
    public Task createTask() {
        return new RecurringTask(); // Create a RecurringTask
    }
}
```
## 2. Structural Patterns:
To better organize classes and objects, we could use the Decorator pattern to add additional functionality to tasks. For example, we could add "notification" functionality to any task without changing the basic implementation of the task.
```
// Abstract decorator for Task
public abstract class TaskDecorator implements Task {
    protected Task wrapped; // Task to be decorated

    public TaskDecorator(Task wrapped) {
        this.wrapped = wrapped; // Initialize with a Task instance
    }

    @Override
    public void performTask() {
        wrapped.performTask(); // Delegate task execution to the wrapped task
    }
}

// Concrete decorator that adds notification
public class NotifiedTask extends TaskDecorator {
    public NotifiedTask(Task wrapped) {
        super(wrapped); // Pass the task to the parent decorator
    }

    @Override
    public void performTask() {
        super.performTask(); // Perform the original task
        sendNotification(); // Add additional functionality
    }

    private void sendNotification() {
        System.out.println("Sending notification for task completion"); // Send notification
    }
}

```
## 3. Behavioral Patterns:
To handle interaction and accountability between objects, we could use the Observer pattern to notify users about the progress of tasks. Whenever a task changes state, observers (users) will be notified.
```
// Observer interface
public interface Observer {
    void update(String message); // Method to be implemented by concrete observers
}

// Subject class
public class TaskManager {
    private List<Observer> observers = new ArrayList<>(); // List to hold observers

    public void addObserver(Observer observer) {
        observers.add(observer); // Add an observer to the list
    }

    public void removeObserver(Observer observer) {
        observers.remove(observer); // Remove an observer from the list
    }

    public void notifyObservers(String message) {
        for (Observer observer : observers) {
            observer.update(message); // Notify all observers with the message
        }
    }

    public void updateTaskStatus(String status) {
        // Logic to update task status
        notifyObservers("Task status updated to: " + status); // Notify observers about status update
    }
}

// Concrete observers
public class UserObserver implements Observer {
    @Override
    public void update(String message) {
        System.out.println("UserObserver received: " + message); // Handle update message
    }
}

public class AdminObserver implements Observer {
    @Override
    public void update(String message) {
        System.out.println("AdminObserver received: " + message); // Handle update message
    }
}

```
