package com.example.gagan.khanavali_main;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import order.Order;
import order.orderDetailsAdapter;

public class orderDetail extends AppCompatActivity {

    orderDetailsAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_order_detail);
        Intent i = getIntent();
        // Order order = (Order)i.getSerializableExtra("order");

        Gson gson = new Gson();
      //  Order order;
        Order order = gson.fromJson(i.getStringExtra("order"), Order.class);
//        JSONObject object = null;
//        try {
//             object = new JSONObject(i.getStringExtra("order"));
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
        adapter = new orderDetailsAdapter(getApplicationContext(), R.layout.activity_customer_order_detail, order);
        TextView txtViewName = (TextView) findViewById(R.id.customer_name_value);
        TextView txtViewPhone = (TextView) findViewById(R.id.customer_contact_value);
        TextView txtViewAddress = (TextView) findViewById(R.id.address_value);
        TextView txtViewStatus = (TextView) findViewById(R.id.current_status_value);
        TextView txtViewMenu = (TextView) findViewById(R.id.items_value);
        TextView txtViewTracker = (TextView) findViewById(R.id.status_tracker_value);
        TextView txtViewid = (TextView) findViewById(R.id.order_id_value);

        txtViewName.setText(order.getCustomer().getName());
        txtViewPhone.setText(order.getCustomer().getPhone());
        txtViewid.setText(order.getId());
        txtViewStatus.setText(order.getCurrent_status());

        String CustomerAddress = order.getCustomer().getAddress().getAddressLine1()
                + "\n" +order.getCustomer().getAddress().getAddressLine2()
                + "\n"+ order.getCustomer().getAddress().getAreaName()
                +"\n"+ order.getCustomer().getAddress().getStreet()
                + "\n" +order.getCustomer().getAddress().getCity()
                + "\n" +order.getCustomer().getAddress().getLandMark();
        txtViewAddress.setText(CustomerAddress);

        ArrayList<order.MenuItem> items = order.getMenuItems();
        String MenuItemStr = "";
        for(int j = 0 ; j < items.size() ; j++)
        {
            MenuItemStr += items.get(j).getName() + " (" + items.get(j).getNo_of_order() + ")" + '\n';
        }
        txtViewMenu.setText(MenuItemStr);

        ArrayList<order.Tracker> trackeritems = order.getTrackerDetail();
        String trackerItemStr = "";
        for(int j = 0 ; j < trackeritems.size() ; j++)
        {
            trackerItemStr += trackeritems.get(j).getStatus()+ " (" + trackeritems.get(j).getTime() + ")" + '\n';
        }
        txtViewTracker.setText(trackerItemStr);


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_order_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
