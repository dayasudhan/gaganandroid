package order;

import java.util.ArrayList;

/**
 * Created by dganeshappa on 11/10/2015.
 */

public class Order {
    Customer customer;
    String current_status;
    String id;
    ArrayList<MenuItem> menuItems;
    ArrayList<Tracker> trackerDetail;
    int bill_value;

    public Order()
    {
        this.bill_value =0;
        this.deliveryCharge = 0;
        this.totalCost = 0;
    }
    public int getDeliveryCharge() {
        return deliveryCharge;
    }

    public void setDeliveryCharge(int deliveryCharge) {
        this.deliveryCharge = deliveryCharge;
    }

    public int getBill_value() {
        return bill_value;
    }

    public void setBill_value(int bill_value) {
        this.bill_value = bill_value;
    }

    public int getTotalCost() {
        return this.totalCost;
    }

    public void setTotalCost(int totalCost) {
        this.totalCost = totalCost;
    }

    int deliveryCharge;
    int totalCost;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }



    public ArrayList<MenuItem> getMenuItems() {
        return menuItems;
    }

    public void setMenuItems(ArrayList<MenuItem> menuItems) {
        this.menuItems = menuItems;
    }



    public ArrayList<Tracker> getTrackerDetail() {
        return trackerDetail;
    }

    public void setTrackerDetail(ArrayList<Tracker> trackerDetail) {
        this.trackerDetail = trackerDetail;
    }


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
