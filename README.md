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

# Structural: Decorator
## Purpose of the pattern:
The Decorator pattern allows additional responsibilities to be added to an object dynamically. Decorators provide a flexible alternative to inheritance for extending functionality.
## Usage situations:
It is useful when you want to add additional functionalities to individual objects in a flexible way without altering the code of the original class.

# Behavioral: Observer
## Purpose of the pattern:
The Observer pattern defines a one-to-many dependency between objects so that when one object changes state, all its dependents are notified and updated automatically.
## Usage situations:
It is useful when there is an object that changes its state frequently and other objects need to be informed of these changes.

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

## 2. Structural Patterns:
To better organize classes and objects, we could use the Decorator pattern to add additional functionality to tasks. For example, we could add "notification" functionality to any task without changing the basic implementation of the task.

## 3. Behavioral Patterns:
To handle interaction and accountability between objects, we could use the Observer pattern to notify users about the progress of tasks. Whenever a task changes state, observers (users) will be notified.
