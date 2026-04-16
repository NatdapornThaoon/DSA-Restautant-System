package structures;

import java.util.Stack;

public class UndoStack {
    private final Stack<String> stack = new Stack<>();  // เก็บข้อความการกระทำ
    
    // เพิ่มการกระทำลง stack
    public void push(String action) {
        stack.push(action);  // push = วางบนสุด
    }
    
    // ดึงการกระทำล่าสุดออก (LIFO)
    public String pop() {
        return stack.isEmpty() ? null : stack.pop();  // pop = ดึงออก
    }
    
    // ล้าง stack ทั้งหมด
    public void clear() {
        stack.clear();
    }
    
    public boolean isEmpty() {
        return stack.isEmpty();
    }
}






