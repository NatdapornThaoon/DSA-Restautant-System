package structures;

import models.MenuItems;

public class MenuCLL {
    private Node current; // ชี้ไป node ปัจจุบันในวงกลม

    private static class Node {
        MenuItems data;
        Node next; // ใน  CLL, node สุดท้าย next จะชี้กลับไป node แรก

        Node(MenuItems data) {
            this.data = data;
        }
    }

    // เพิ่ม node (ไม่มีการเรียงลำดับ)
    public void add(MenuItems item) {
        Node newNode = new Node(item);

        if (current == null) { // กรณี list ว่าง
            current = newNode;
            current.next = current; // ชี้กลับไปตัวเอง
            return;
        } 
        
        // เพิ่ม node ใหมต่อจาก current
        newNode.next = current.next; // node ใหม่ชี้ไป node ถัดไปของ current
        current.next = newNode; // current ชี้ไป node ใหม่
       
    }

    // แสดงเมนูทั้งหมด (Iterative)
    public void displayCurrent(){
        if (current == null){
            System.out.println("Menu is empty.");
            return;
        }
        System.out.println("--- Recommended Menu TODAY ---" + current.data);
    }

    //หมุนไปเมนูแนะนำถัดไป
    public void rotate() {
        if (current != null){
            current = current.next; // หมุนไป node ถัดไป
        }
    }
    

    
}
