package src.main;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import models.MenuItem;
import models.Order;
import models.OrderItem;
import structures.MenuSLL;
import structures.MenuCLL;
import structures.UndoStack;
import structures.KitchenQueue;
import structures.OrderHistory;
import util.InputHelper;


public class FoodOrderingSystem {
    private final Scanner scanner;
    private final InputHelper inputHelper;
    
    private final MenuSLL fullMenu;
    private final MenuCLL recommendMenu;
    private final List<OrderItem> cart;
    private final UndoStack undoStack;
    private final KitchenQueue kitchenQueue;
    private final OrderHistory history;
    
    public FoodOrderingSystem() {
        this.scanner = new Scanner(System.in);
        this.inputHelper = new InputHelper(scanner);
        
        this.fullMenu = new MenuSLL();
        this.recommendMenu = new MenuCLL();
        this.cart = new ArrayList<>();
        this.undoStack = new UndoStack();
        this.kitchenQueue = new KitchenQueue();
        this.history = new OrderHistory();
        
        loadMenuData();
    }
    
    private void loadMenuData() {
    
        fullMenu.add(new MenuItem(1, "Fried Rice", 50));
        fullMenu.add(new MenuItem(2, "Tom Yum Goong", 120));
        fullMenu.add(new MenuItem(3, "Green Curry", 80));
        fullMenu.add(new MenuItem(4, "Pad Thai", 60));
        fullMenu.add(new MenuItem(5, "Som Tam", 45));
        
        
        recommendMenu.add(new MenuItem(3, "Green Curry", 80));
        recommendMenu.add(new MenuItem(1, "Fried Rice", 50));
        recommendMenu.add(new MenuItem(4, "Pad Thai", 60));
    }
    
    public void start() {
        while (true) {
            displayMainMenu();
            int choice = inputHelper.getIntInput("Choose: ");
            
            switch (choice) {
                case 1 -> showMenuWithRecommendations();
                case 2 -> addToCart();
                case 3 -> checkout();
                case 4 -> showOrderHistory();
                case 5 -> {
                    System.out.println("\nThank you for using our service!");
                    scanner.close();
                    return;
                }
                default -> System.out.println("Invalid choice");
            }
        }
    }
    
    private void displayMainMenu() {
        System.out.println("\n=== Food Ordering System ===");
        System.out.println("1. Show all menu + rotating recommendations");
        System.out.println("2. Add food to cart");
        System.out.println("3. Checkout and send to kitchen");
        System.out.println("4. Show order history");
        System.out.println("5. Exit");
    }
    
    private void showMenuWithRecommendations() {
        System.out.println();
        fullMenu.display();
        System.out.println();
        recommendMenu.displayCurrent();
        
        String answer = inputHelper.getStringInput("\nDo you want to see next recommendation? (y/n): ");
        if (answer.equalsIgnoreCase("y")) {
            recommendMenu.rotate();
            System.out.println();
            recommendMenu.displayCurrent();
        }
        inputHelper.waitForEnter();
    }
    
    private void addToCart() {
        System.out.println();
        fullMenu.display();
        
        int itemId = inputHelper.getIntInput("\nEnter food ID (0=cancel): ");
        if (itemId == 0) return;
        
        MenuItem selectedItem = fullMenu.findById(itemId);
        if (selectedItem == null) {
            System.out.println("Food ID not found");
            return;
        }
        
        int quantity = inputHelper.getIntInput("Quantity: ");
        if (quantity <= 0) {
            System.out.println("Quantity must be greater than 0");
            return;
        }
        
        OrderItem newItem = new OrderItem(selectedItem, quantity);
        cart.add(newItem);
        
        String action = "Added " + selectedItem.name + " x" + quantity;
        undoStack.push(action);
        System.out.println(  action + " added to cart");
        
        String undoAnswer = inputHelper.getStringInput("\nDo you want to undo the last item? (y/n): ");
        if (undoAnswer.equalsIgnoreCase("y")) {
            performUndo();
        }
    }
    
    private void performUndo() {
        String lastAction = undoStack.pop();
        if (lastAction != null && !cart.isEmpty()) {
            cart.remove(cart.size() - 1);
            System.out.println(" Undo: " + lastAction);
        } else {
            System.out.println(" Nothing to undo");
        }
    }
    
    private void displayCartSummary() {
        System.out.println("\n--- Cart Summary ---");
        double total = 0;
        for (OrderItem item : cart) {
            System.out.println(item);
            total += item.getTotal();
        }
        System.out.println("-----------------------------------");
        System.out.printf("Total: %.2f THB\n", total);
    }
    
    private void askToShowKitchenQueue() {
        String answer = inputHelper.getStringInput("\nDo you want to see kitchen queue? (y/n): ");
        if (answer.equalsIgnoreCase("y")) {
            kitchenQueue.displayQueue();
            String cookAnswer = inputHelper.getStringInput("\nDo you want to cook the next order? (y/n): ");
            if (cookAnswer.equalsIgnoreCase("y")) {
                kitchenQueue.processNextOrder();
            }
        }
    }
    
    private void checkout() {
        if (cart.isEmpty()) {
            System.out.println("\nCart is empty. Cannot checkout");
            return;
        }
        
        displayCartSummary();
        
        String confirm = inputHelper.getStringInput("\nConfirm order? (y/n): ");
        if (!confirm.equalsIgnoreCase("y")) {
            System.out.println("Checkout cancelled");
            return;
        }
        
        Order order = new Order(new ArrayList<>(cart));
        history.addOrder(order);
        kitchenQueue.addOrder(order);
        
        System.out.println("Order successful! Order #" + order.orderId);
        
        cart.clear();
        undoStack.clear();
        
        askToShowKitchenQueue();
    }
    
    private void showOrderHistory() {
        if (history.isEmpty()) {
            System.out.println("\nNo order history");
            inputHelper.waitForEnter();
            return;
        }
        
        System.out.println("\n1. Show history in order (Iterative)");
        System.out.println("2. Show history in reverse (Recursive)");
        int choice = inputHelper.getIntInput("Choose: ");
        
        System.out.println();
        if (choice == 1) {
            history.displayForward();
        } else if (choice == 2) {
            history.displayBackwardRecursive();
        } else {
            System.out.println("Invalid choice");
        }
        inputHelper.waitForEnter();
    }
    
    public static void main(String[] args) {
        FoodOrderingSystem system = new FoodOrderingSystem();
        system.start();
    }
}