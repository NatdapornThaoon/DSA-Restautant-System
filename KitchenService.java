package service;

import datastructure.KitchenQueue;
import model.Order;
import java.util.Scanner;
import java.util.Date;

public class KitchenService {
    // เรียกใช้ KitchenQueue ที่เราสร้างไว้ใน package datastructure
    private KitchenQueue kitchenQueue = new KitchenQueue();
    private int orderCounter = 1;

    public void processPayment(Order currentOrder, Scanner sc) {
        // --- กรณี: ตะกร้าว่าง (อ้างอิง image_128a85.png) ---
        if (currentOrder == null || currentOrder.isEmpty()) {
            System.out.println("\nตะกร้าว่าง ไม่สามารถชำระเงินได้");
            return;
        }

        // --- แสดงรายการในตะกร้าก่อนชำระเงิน ---
        System.out.println("\n--- รายการในตะกร้า ---");
        currentOrder.printItems(); // สมมติว่าใน class Order มี method นี้
        System.out.println("รวมทั้งสิ้น: " + currentOrder.getTotalPrice() + " THB");

        // --- ยืนยันการสั่งซื้อ (อ้างอิง image_128d51.png) ---
        System.out.print("\nยืนยันการสั่งซื้อ (y/n): ");
        String confirm = sc.next();

        if (confirm.equalsIgnoreCase("n")) {
            System.out.println("ยกเลิกการชำระเงิน");
            return;
        }

        if (confirm.equalsIgnoreCase("y")) {
            // ตั้งค่า ID และส่งเข้า Queue
            currentOrder.setOrderId(orderCounter++);
            kitchenQueue.enqueue(currentOrder);

            // แสดงสถานะสำเร็จ (อ้างอิง image_128c8.png)
            System.out.println("✅ ออเดอร์ #" + currentOrder.getOrderId() + " ส่งเข้าครัวแล้ว");
            System.out.println("🎉 สั่งซื้อสำเร็จ! ออเดอร์ #" + currentOrder.getOrderId());

            // ส่วนการจัดการคิวครัว
            askToShowQueue(sc);
            askToProcessNextOrder(sc);
        }
    }

    private void askToShowQueue(Scanner sc) {
        System.out.print("\nต้องการแสดงคิวครัวหรือไม่? (y/n): ");
        if (sc.next().equalsIgnoreCase("y")) {
            System.out.println("\n📋 คิวครัว:");
            kitchenQueue.displayQueue();
        }
    }

    private void askToProcessNextOrder(Scanner sc) {
        System.out.print("\nต้องการทำอาหารออเดอร์ถัดไปหรือไม่? (y/n): ");
        if (sc.next().equalsIgnoreCase("y")) {
            Order nextOrder = kitchenQueue.dequeue();
            if (nextOrder != null) {
                // แสดงเวลาปัจจุบันแบบในรูป
                System.out.println("🍳 กำลังทำ: Order #" + nextOrder.getOrderId() + " (" + new Date() + ")");
                nextOrder.printItems();
                System.out.println("Total: " + nextOrder.getTotalPrice() + " THB");
            } else {
                System.out.println("ไม่มีออเดอร์ค้างในคิว");
            }
        }
    }
}