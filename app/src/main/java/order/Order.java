package order;

import java.util.ArrayList;

/**
 * Created by dganeshappa on 11/10/2015.
 */

public class Order {
    Customer customer;
    String current_status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    String id;

    public ArrayList<MenuItem> getMenuItems() {
        return menuItems;
    }

    public void setMenuItems(ArrayList<MenuItem> menuItems) {
        this.menuItems = menuItems;
    }

    ArrayList<MenuItem> menuItems;

    public ArrayList<Tracker> getTrackerDetail() {
        return trackerDetail;
    }

    public void setTrackerDetail(ArrayList<Tracker> trackerDetail) {
        this.trackerDetail = trackerDetail;
    }

    ArrayList<Tracker> trackerDetail;
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
