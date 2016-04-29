package com.example.gagan.khanavali_main;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ParseException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import order.*;
/**
 * Created by gagan on 11/6/2015.
 */
public class order_list_layouthome extends Fragment {

    // JSON Node names
    private static final String TAG_CUSTOMER = "customer";
    private static final String TAG_ID = "_id";
    private static final String TAG_NAME = "name";
    private static final String TAG_EMAIL = "email";
    private static final String TAG_ADDRESS = "address";
    private static final String TAG_PHONE = "phone";
    private static final String TAG_TRACKER = "tracker";
    private static final String TAG_CURRENT_STATUS = "current_status";

    SharedPreferences pref;
    ArrayList<Order> orderList;
    String vendor_email;
    OrderAdapter adapter;

    View rootview;
    ListView listView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootview=inflater.inflate(R.layout.new_order_list,container,false);
        listView = (ListView) rootview.findViewById(R.id.listView_vendor);
        orderList = new ArrayList<Order>();
        pref = getActivity().getSharedPreferences("Khaanavali", 0);
        vendor_email = pref.getString("email","name");
        bindView();
        adapter = new OrderAdapter(getActivity().getApplicationContext(), R.layout.new_order_list_item, orderList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position,
                                    long id) {
                // TODO Auto-generated method stub
                //          Toast.makeText(getActivity().getApplicationContext(), orderList.get(position).getCustomer().getName(), Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getActivity(), orderDetail.class);
                Gson gson = new Gson();
                String order = gson.toJson(orderList.get(position));
                intent.putExtra("order", order);

                startActivity(intent);
            }
        });
        return rootview;
    }
    public void bindView() {

        String order_url = "http://oota.herokuapp.com/v1/vendor/order/";
        order_url= order_url.concat(vendor_email);
        new JSONAsyncTask(getActivity(),listView).execute(order_url);
    }
    public  class JSONAsyncTask extends AsyncTask<String, Void, Boolean> {

        ProgressDialog dialog;

        ListView mListView;
        Activity mContex;
        public  JSONAsyncTask(Activity contex,ListView gview)
        {
            this.mListView=gview;
            this.mContex=contex;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(getActivity());
            dialog.setMessage("Loading, please wait");
            dialog.setTitle("Connecting server");
            dialog.show();
            dialog.setCancelable(false);
        }

        @Override
        protected Boolean doInBackground(String... urls) {
            try {

                //------------------>>
                HttpGet httppost = new HttpGet(urls[0]);
                HttpClient httpclient = new DefaultHttpClient();
                HttpResponse response = httpclient.execute(httppost);

                // StatusLine stat = response.getStatusLine();
                int status = response.getStatusLine().getStatusCode();

                if (status == 200) {
                    HttpEntity entity = response.getEntity();

                    String data = EntityUtils.toString(entity);
                    JSONArray jarray = new JSONArray(data);

                    for (int i = 0; i < jarray.length(); i++) {
                        JSONObject object = jarray.getJSONObject(i);

                        Order ordr = new Order();
                        Customer cus = new Customer();
                        if(object.has(TAG_CUSTOMER)) {
                            JSONObject custObj = object.getJSONObject(TAG_CUSTOMER);
                            if (custObj.has(TAG_PHONE)) {
                                cus.setName(custObj.getString(TAG_NAME));
                            }
                            if (custObj.has(TAG_PHONE)) {
                                int phone = custObj.getInt(TAG_PHONE);
                                cus.setPhone(Integer.toString(phone));
                            }
                            if (custObj.has(TAG_ADDRESS)) {
                                JSONObject addrObj = custObj.getJSONObject(TAG_ADDRESS);
                                Address address = new Address();
                                address.setAddressLine1(addrObj.getString("addressLine1"));
                                address.setAddressLine2(addrObj.getString("addressLine2"));
                                address.setAreaName(addrObj.getString("areaName"));
                                address.setCity(addrObj.getString("city"));
                                address.setLandMark(addrObj.getString("LandMark"));
                                address.setStreet(addrObj.getString("street"));
                                address.setZip(addrObj.getString("zip"));
                                cus.setAddress(address);
                            }


                        }
                        if(object.has(TAG_CURRENT_STATUS))
                        {
                            ordr.setCurrent_status(object.getString(TAG_CURRENT_STATUS));
                        }
                        ordr.setCustomer(cus);
                        orderList.add(ordr);
                    }
                    return true;
                }
             } catch (ParseException e1) {
                e1.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return false;
        }

        protected void onPostExecute(Boolean result) {
            dialog.cancel();
            adapter.notifyDataSetChanged();
            if (result == false)
                Toast.makeText(getActivity().getApplicationContext(), "Unable to fetch data from server", Toast.LENGTH_LONG).show();

        }
    }

}
