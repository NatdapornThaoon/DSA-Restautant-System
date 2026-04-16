package DSA;

import DSA.FoodOrderingSystem.EmptyCartException;
import DSA.FoodOrderingSystem.FoodOrderException;
import DSA.FoodOrderingSystem.InvalidMenuItemException;
import DSA.FoodOrderingSystem.InvalidQuantityException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.function.Predicate;
import models.MenuItems;
import models.Order;
import models.OrderItem;
import structures.KitchenQueue;
import structures.MenuCLL;
import structures.MenuSLL;
import structures.OrderHistory;
import structures.UndoStack;
import util.InputHelper;



public class FoodOrderingSystem {
    private final Scanner scanner;
    private final InputHelper inputHelper;
    private final MenuSLL<MenuItems> fullMenu;
    private final MenuCLL recommendMenu;
    private final List<OrderItem> cart;
    private final UndoStack undoStack;
    private final KitchenQueue kitchenQueue;
    private final OrderHistory history;
    private int orderCounter = 1000;

     public FoodOrderingSystem() {
        this.scanner = new Scanner(System.in);
        this.inputHelper = new InputHelper(scanner);
        this.fullMenu = new MenuSLL<>();
        this.recommendMenu = new MenuCLL();
        this.cart = new ArrayList<>();
        this.undoStack = new UndoStack();
        this.kitchenQueue = new KitchenQueue();
        this.history = new OrderHistory();
        
        loadMenuData();
    }

    public static void main(String[] args) {
        FoodOrderingSystem system = new FoodOrderingSystem();
        system.start();
    }
     private void loadMenuData() {
    
        fullMenu.add(new MenuItems(1, "Fried Rice", 50));
        fullMenu.add(new MenuItems(2, "Tom Yum Goong", 120));
        fullMenu.add(new MenuItems(3, "Green Curry", 80));
        fullMenu.add(new MenuItems(4, "Pad Thai", 60));
        fullMenu.add(new MenuItems(5, "Som Tam", 45));
        
        
        recommendMenu.add(new MenuItems(3, "Green Curry", 80));
        recommendMenu.add(new MenuItems(1, "Fried Rice", 50));
        recommendMenu.add(new MenuItems(4, "Pad Thai", 60));

        
    }

     public void start() {
        while (true) {
            displayMainMenu();
            int choice = inputHelper.getIntInput("Choose: ");
            
            switch (choice) {
                case 1 : showMenuWithRecommendations(); break;
                case 2 : addToCart(); break;
                case 3 : checkout(); break;
                case 4 : showOrderHistory(); break; 
                case 5 : saveOrderHistoryToFile(); break;
                case 6 : loadOrderHistoryFromFile(); break;
                case 7 : {
                    System.out.println("\nThank you for using our service!");
                    scanner.close();
                    return;
                }
                default : System.out.println("Invalid choice"); break;
            }
        }
    }

     private void displayMainMenu() {
        System.out.println("\n=== Food Ordering System ===");
        System.out.println("1. Show all menu + rotating recommendations");
        System.out.println("2. Add food to cart");
        System.out.println("3. Checkout and send to kitchen");
        System.out.println("4. Show order history");
        System.out.println("5. Save order history");
        System.out.println("6. Load order history");
        System.out.println("7. Exit");
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
    //ใช้ exception
    private void addToCart() {
        System.out.println();
        fullMenu.display();

        try {
            int itemId = inputHelper.getIntInput("\nEnter food ID (0=cancel): ");
            if (itemId == 0) return;

            MenuItems selectedItem = fullMenu.findById(itemId);
            if (selectedItem == null) {
                throw new InvalidMenuItemException(itemId);
            }

            int quantity = inputHelper.getIntInput("Quantity: ");
            if (quantity <= 0) {
                throw new InvalidQuantityException(quantity);
            }

            OrderItem newItem = new OrderItem(selectedItem, quantity);
            cart.add(newItem);

            String action = "Added " + selectedItem.getName() + " x" + quantity;
            undoStack.push(action);
            System.out.println(action + " added to cart");

            String undoAnswer = inputHelper.getStringInput("\nDo you want to undo the last item? (y/n): ");
            if (undoAnswer.equalsIgnoreCase("y")) {
                performUndo();
            }

        } catch (InvalidMenuItemException | InvalidQuantityException e) {
            System.out.println("An error occurred while adding item to cart. " + e.getMessage());
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
        try {
            if (cart.isEmpty()) {
                throw new EmptyCartException();
            }
        
        displayCartSummary();
        
        String confirm = inputHelper.getStringInput("\nConfirm order? (y/n): ");
        if (!confirm.equalsIgnoreCase("y")) {
            System.out.println("Checkout cancelled");
            return;
        }

        Order order = new Order(orderCounter++);
        for (OrderItem item : cart) {
            order.addItem(item);
        }
        
        history.addOrder(order);
        kitchenQueue.addOrder(order);
        
        System.out.println("Order successful! Order #" + order.getId());
        
        cart.clear();
        undoStack.clear();
        
        askToShowKitchenQueue();
        
        } catch (EmptyCartException e) {
            System.out.println("Checkout failed: " + e.getMessage());
        }
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
        switch (choice) {
            case 1 : history.displayForward(); break;
            case 2 : history.displayBackwardRecursive(); break;
            default : System.out.println("Invalid choice"); break;
        }
        inputHelper.waitForEnter();
    }

    

    private void performUndo() {
        if (cart.isEmpty()) {
            System.out.println("Nothing to undo");
            return;
        }
        
        cart.remove(cart.size() - 1);
        String action = undoStack.pop();
        
        if (action != null) {
            System.out.println("Undo: " + action);
        }
    }

    // ========== GENERIC METHODS ==========
    
    /**
     * Generic method to filter items from a collection based on a predicate
     * @param <T> Type of items in the collection
     * @param items List of items to filter
     * @param predicate Condition to match items
     * @return List of items matching the predicate
     */
    private <T> List<T> filterItems(List<T> items, Predicate<T> predicate) {
        List<T> result = new ArrayList<>();
        for (T item : items) {
            if (predicate.test(item)) {
                result.add(item);
            }
        }
        return result;
    }

    /**
     * Generic method to find the first item matching a predicate
     * @param <T> Type of items in the collection
     * @param items List of items to search
     * @param predicate Condition to match
     * @return The first item matching the predicate, or null if not found
     */
    private <T> T findFirst(List<T> items, Predicate<T> predicate) {
        for (T item : items) {
            if (predicate.test(item)) {
                return item;
            }
        }
        return null;
    }

    /**
     * Generic method to display items with a custom title
     * @param <T> Type of items to display
     * @param items List of items to display
     * @param title Title to show before displaying items
     */
    private <T> void displayItems(List<T> items, String title) {
        if (items.isEmpty()) {
            System.out.println(title + " is empty.");
            return;
        }
        System.out.println("\n--- " + title + " ---");
        for (int i = 0; i < items.size(); i++) {
            System.out.println((i + 1) + ". " + items.get(i));
        }
    }

    /**
     * Generic method to count items matching a predicate
     * @param <T> Type of items in the collection
     * @param items List of items to count
     * @param predicate Condition to match
     * @return Count of items matching the predicate
     */
    private <T> int countItems(List<T> items, Predicate<T> predicate) {
        int count = 0;
        for (T item : items) {
            if (predicate.test(item)) {
                count++;
            }
        }
        return count;
    }

    // ========== END GENERIC METHODS ==========
    private static final String HISTORY_FILE = "order_history.txt";

    private void saveOrderHistoryToFile() {
        if (history.isEmpty()){
            System.out.println("No order history to save");
            return;
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(HISTORY_FILE))){

             // ดึงข้อมูลจาก OrderHistory (ต้องเพิ่ม method toFileLines)
            List<String> lines = (List<String>) history.toFileLines();
            for (String line : lines) {
                writer.write(line);
                writer.newLine();
            }
            System.out.println("Order history saved to " + HISTORY_FILE + " successfully!");

        } catch (IOException e) {
            System.out.println("Error saving order history: " + e.getMessage());
        }
    
    }

    private void loadOrderHistoryFromFile() {
        File file = new File(HISTORY_FILE);
        if (!file.exists()){
            System.out.println("No saved order history found");
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(HISTORY_FILE))){

            String line;
            System.out.println("--- Loaded Order History ---");
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }

        } catch (IOException e) {
            System.out.println("Error loading order history: " + e.getMessage());
        }
    }

    static class FoodOrderException extends Exception {
        public FoodOrderException(String message) {
            super(message);
        }
    }

    static class InvalidMenuItemException extends FoodOrderException {
        public InvalidMenuItemException(int id) {
            super("Invalid menu item ID: " + id);
        }
    }

    static class EmptyCartException extends FoodOrderException {
        public EmptyCartException() {
            super("Cart is empty. Cannot checkout.");
        }
    }

    static class InvalidQuantityException extends FoodOrderException {
        public InvalidQuantityException(int quantity) {
            super("Invalid quantity: " + quantity + ". Must be greater than 0.");
        }
    }
     





}
