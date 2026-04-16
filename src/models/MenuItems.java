package models;


//public class MenuItems {
    
    //private final int id;
    //private final String name;
    //private final double price;

    //public MenuItems(int id, String name, double price) {
      //  this.id = id;
        //this.name = name;
        //this.price = price;
    //}


   // public int getId() {
        //return id;
    //}

    //public String getName() {
      //  return name;
    //}

    //public double getPrice() {
      //  return price;
    //}

    //@Override
    //public String toString() {
      //  return id + ". " + name + " (Price: " + price + " THB)";
    //}
 //}

//สร้าง interface ในไฟล์เดียวกัน (ไม่ต้องแยกไฟล์)
 interface Identifiable {
    int getId();
    String getName();
    String getDisplayName();
 }

 // Abstract class ในไฟล์เดียวกัน
 abstract class BaseModels {
    protected java.util.Date createdAt;
    protected java.util.Date updatedAt;

    public BaseModels(){
        this.createdAt = new java.util.Date();
        this.updatedAt = new java.util.Date();

    }

    public java.util.Date getCreatedAt(){ return createdAt;}
    public java.util.Date getUpdatedAt(){ return updatedAt;}
    public void setUpdatedAt(java.util.Date updatedAt){
        this.updatedAt = updatedAt;
    }

    public abstract String getDisplayName();

 }
 // MenuItem implements interface และ extends abstract class
  public class MenuItems extends BaseModels implements Identifiable {
    private final int id;
    private final String name;
    private final double price;

    public MenuItems(int id, String name, double price) {
        super(); // เรียก constructor ของ BaseModels
        this.id = id;
        this.name = name;
        this.price = price;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    public double getPrice() {
       return price;
    }

    @Override
    public String getDisplayName() {
        return id + ". " + name + " (Price: " + price + " THB)";
    }

    @Override
    public String toString() {
        return id + ". " + name + " (Price: " + price + " THB)";
    }
}

    





    

