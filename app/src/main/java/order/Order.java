package order;

import java.util.ArrayList;

/**
 * Created by dganeshappa on 11/10/2015.
 */

public class Order {
    Customer customer;
    String current_status;

    public ArrayList<MenuItem> getMenuItems() {
        return menuItems;
    }

    public void setMenuItems(ArrayList<MenuItem> menuItems) {
        this.menuItems = menuItems;
    }

    ArrayList<MenuItem> menuItems;



    public Tracker getTracker() {
        return tracker;
    }

    public void setTracker(Tracker tracker) {
        this.tracker = tracker;
    }


    Tracker tracker;

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public String getCurrent_status()
    {
        return current_status;
    }
    public void setCurrent_status(String current_status)
    {
        this.current_status = current_status;
    }
}
