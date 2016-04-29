package com.example.gagan.khanavali_main;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.gson.Gson;

import order.Order;
import order.orderDetailsAdapter;

public class orderDetail extends AppCompatActivity {

    orderDetailsAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);
        Intent i = getIntent();
        // Order order = (Order)i.getSerializableExtra("order");

        Gson gson = new Gson();
        Order order = gson.fromJson(i.getStringExtra("order"), Order.class);
        adapter = new orderDetailsAdapter(getApplicationContext(), R.layout.activity_order_detail, order);
        TextView txtViewName = (TextView) findViewById(R.id.ord_customer_name);
        TextView txtViewPhone = (TextView) findViewById(R.id.ord_customer_phone);
        TextView txtViewAddress = (TextView) findViewById(R.id.customer_address);
        TextView txtViewStatus = (TextView) findViewById(R.id.order_status);
        TextView txtViewMenu = (TextView) findViewById(R.id.menu_order);

        txtViewName.setText(order.getCustomer().getName());
        txtViewPhone.setText(order.getCustomer().getPhone());

        String CustomerAddress = order.getCustomer().getAddress().getAddressLine1()
                + "\n" +order.getCustomer().getAddress().getAddressLine2()
                + "\n"+ order.getCustomer().getAddress().getAreaName()
                +"\n"+ order.getCustomer().getAddress().getStreet()
                + "\n" +order.getCustomer().getAddress().getCity()
                + "\n" +order.getCustomer().getAddress().getLandMark();
        txtViewAddress.setText(CustomerAddress);
        txtViewStatus.setText(order.getCurrent_status());
//        txtViewMenu.setText(order.getCustomer().getName());


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
