package util;

import java.util.Scanner;

public class InputHelper {
    private final Scanner scanner;

    public InputHelper(Scanner scanner) {
        this.scanner = scanner;
    }

    public int getIntInput(String prompt) {
        System.out.print(prompt);
        int input = 0;
        try {
            input = Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a number.");
        }
        return input;
    }

    public String getStringInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    public void waitForEnter() {
        System.out.println("Press Enter to continue...");
        scanner.nextLine();
    }
}
