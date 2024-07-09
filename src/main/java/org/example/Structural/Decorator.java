package org.example.Structural;

public class Decorator {
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

}
