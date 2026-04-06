package main;

import models.*;
import structures.*;
import java.util.*;

public class FoodOrderingSystem {
    private MenuSLL fullMenu;
    private MenuCLL recommendMenu;
    private List<OrderItem> cart;
    private UndoStack undoStack;
    private KitchenQueue kitchenQueue;
    private HistoryDLL history;
    private Scanner sc;

    public FoodOrderingSystem() {
        fullMenu = new MenuSLL();
        recommendMenu = new MenuCLL();
        cart = new ArrayList<>();
        undoStack  = new UndoStack();
        kitchenQueue = new KitchenQueue();
        history = new HistoryDLL();
        sc = new Scanner(System.in);
        
        initMenu();
    }

    public static void main(String[] args) {
        FoodOrderingSystem system = new FoodOrderingSystem();
        system.showMainMenu();
    } 
}