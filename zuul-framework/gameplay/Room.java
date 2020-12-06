package gameplay;

import item.PurchasableItem;
import item.Key;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashMap;


public class Room {
    private String name;
    private String description;
    private HashMap<String, Room> exits;
    private ArrayList<PurchasableItem> stock;
    private boolean locked;

    public Room(String name, String description, boolean locked) {
        this.setName(name);
        this.description = description;
        this.locked = locked;
        exits = new HashMap<>();
        stock = new ArrayList<>();
    }

    public void setItem(PurchasableItem i) {
        stock.add(i);
    }

    public PurchasableItem getItem(String name) {
        for (PurchasableItem i : stock) {
            if (name.equals(i.getName())) {
                return i;
            }
        }
        System.out.println("Item not in shop");
        return null;
    }
    public void removeItem(PurchasableItem i) {
        stock.remove(i);
    }

    public ArrayList<PurchasableItem> getItems() {
        return stock;
    }

    public void printStock() {
        System.out.println("These objects are for sale:");
        if (stock.isEmpty()) {
            System.out.println("\tnothing");
        } else {
            for (PurchasableItem i : stock) {
                System.out.println("\t" + i.getName() + "\t|\t" + i.getPrice() + " gold");
            }
        }
    }

    public void setExit(String direction, Room neighbor) {
        exits.put(direction, neighbor);
    }

    public String getLongDescription() {
        return "You are " + description + ".\n" + getExitString();
    }

    private String getExitString() {
        StringBuilder returnString = new StringBuilder("Exits:");
        Set<String> keys = exits.keySet();
        for (String exit : keys) {
            returnString.append(" ").append(exit);
        }
        return returnString.toString();
    }

    public Room getExit(String direction) {
        return exits.get(direction);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isLocked() { return this.locked; }

    public void unlock(Key key) {
        if (key.canUnlock(this.getName())) {
            this.locked = false;
            System.out.println("You have unlocked " + this.getName());
        }
        else
            System.out.println("You can't do that here.");
    }

    public void lock() {
        this.locked = true;
    }
}
