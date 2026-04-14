# **Food Ordering System** (Group 5 OODP Project)

A robust Food Ordering System developed using Object-Oriented Design & Programming (OODP) principles. This project focuses on implementing custom data structures such as Linked Lists, Stacks, and Queues to manage menus, orders, and kitchen workflows efficiently.

## Key Features
- Menu Management: Uses a Singly Linked List (SLL) for the full menu and a Circular Linked List (CLL) for a rotating "Recommended Menu" system.

- Order System: Automated shopping cart management with real-time price calculation.

- Undo Action: Supports undoing the last action using a Stack data structure.

- Kitchen Workflow: Manages food preparation order using a Queue (First-In, First-Out).

- Order History: Tracks past orders using a Doubly Linked List (DLL), allowing users to navigate through history both forward and backward.

- Data Persistence: Features a FileManager to save and load order history and menu data via File I/O.

## System Architecture
The system is organized into four distinct modules:

1. Core Entities: Fundamental data models including MenuItem, OrderItem, and Order, all inheriting from a common BaseEntity and implementing the Identifiable interface.

2. Custom Data Structures: Custom-built logic for MenuSLL, MenuCLL, and OrderHistory (DLL) to demonstrate deep understanding of memory linking.

3. Management Modules: Specialized structures including UndoStack and KitchenQueue to handle system state and workflow.

4. Utilities: Helper classes like InputHelper for robust console input handling and FileManager for external data storage.

## Core Technologies
- Language: Java

- Design Principles: SOLID, Inheritance, Polymorphism, Encapsulation, and Abstraction.

- Algorithms & Structures: * Stack (LIFO) for Undo functionality.
      - Queue (FIFO) for Kitchen management.
      - Linked Lists for dynamic data storage and navigation.

# Group 5 Members :

6831503056 Pradisaru Jaroenthua

6531503200 Patthamaporn Sertluecha

6831503043 Natdaporn Thao-on

6831503033 Kanyaphorn Saengwong
