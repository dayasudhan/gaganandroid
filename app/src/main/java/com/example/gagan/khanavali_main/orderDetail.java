package com.example.gagan.khanavali_main;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import com.google.gson.Gson;
import android.widget.AdapterView.OnItemSelectedListener;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import order.Order;
//import order.orderDetailsAdapter;

public class orderDetail extends AppCompatActivity implements OnItemSelectedListener{
    SharedPreferences pref;
    String vendor_email;
    Order order;
 //   orderDetailsAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_order_detail);
        Intent i = getIntent();
        // Order order = (Order)i.getSerializableExtra("order");
        pref = getSharedPreferences("Khaanavali", 0);
        vendor_email = pref.getString("email", "name");
        Gson gson = new Gson();
      //  Order order;
        order = gson.fromJson(i.getStringExtra("order"), Order.class);
//        JSONObject object = null;
//        try {
//             object = new JSONObject(i.getStringExtra("order"));
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
      //  adapter = new orderDetailsAdapter(getApplicationContext(), R.layout.activity_customer_order_detail, order);
        TextView txtViewName = (TextView) findViewById(R.id.customer_name_value);
        TextView txtViewPhone = (TextView) findViewById(R.id.customer_contact_value);
        TextView txtViewAddress = (TextView) findViewById(R.id.address_value);
        TextView txtViewStatus = (TextView) findViewById(R.id.current_status_value);
        TextView txtViewMenu = (TextView) findViewById(R.id.items_value);
        TextView txtViewTracker = (TextView) findViewById(R.id.status_tracker_value);
        TextView txtViewid = (TextView) findViewById(R.id.order_id_value);
        Spinner spinner = (Spinner) findViewById(R.id.spinner);
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
            SimpleDateFormat existingUTCFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            SimpleDateFormat requiredFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date getDate = null;
            try {
                getDate = existingUTCFormat.parse(trackeritems.get(j).getTime());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Calendar cal = Calendar.getInstance();
            cal.setTime(getDate);
            cal.add(Calendar.HOUR, 5);
            cal.add(Calendar.MINUTE, 30);
            String newTime = requiredFormat.format(cal.getTime());
            trackerItemStr += trackeritems.get(j).getStatus()+ " (" + newTime + ")" + '\n';
        }
        txtViewTracker.setText(trackerItemStr);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.status_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);  // Spinner click listener
        spinner.setOnItemSelectedListener(this);


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_order_detail, menu);
        return true;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String item = parent.getItemAtPosition(position).toString();
        if(position !=0) {
            updateStatusTracker(item, "ok");
        }
    }
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }
    public void updateStatusTracker(String status,String reason)
    {
      //  String order_url = "http://10.239.54.58:3000/v1/vendor/order/status/";
        String order_url = "http://oota.herokuapp.com/v1/vendor/order/status/";
        order_url= order_url.concat(order.getId());
        new AddJSONAsyncTask().execute(order_url,status,reason);
    }
    public  class AddJSONAsyncTask extends AsyncTask<String, Void, Boolean> {

        ProgressDialog dialog;

        public  AddJSONAsyncTask()
        {

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(orderDetail.this);
            dialog.setMessage("Adding Item, please wait");
            dialog.setTitle("Connecting server");
            dialog.show();
            dialog.setCancelable(false);
        }

        @Override
        protected Boolean doInBackground(String... urls) {
            try {
                String json = "";
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("status", urls[1]);
                    jsonObject.put("reason", urls[2]);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                json = jsonObject.toString();
                StringEntity se = new StringEntity(json);

                HttpPut request = new HttpPut(urls[0]);
                HttpClient httpclient = new DefaultHttpClient();
                        request.setEntity(se);
                request.setHeader("Accept", "application/json");
                request.setHeader("Content-type", "application/json");
                HttpResponse response = httpclient.execute(request);

                int status = response.getStatusLine().getStatusCode();

                if (status == 200) {
                    HttpEntity entity = response.getEntity();
                    String data = EntityUtils.toString(entity);
                    return true;
                }

                //------------------>>

            } catch (android.net.ParseException e1) {
                e1.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return false;
        }

        protected void onPostExecute(Boolean result) {
            dialog.cancel();
            // adapter.notifyDataSetChanged();

            if (result == false)
                Toast.makeText(getApplicationContext(), "Unable to fetch data from server", Toast.LENGTH_LONG).show();

        }
    }
}
