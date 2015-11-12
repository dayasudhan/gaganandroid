package com.example.gagan.khanavali_main;

/**
 * Created by dganeshappa on 11/10/2015.
 */


        import android.content.Context;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.ArrayAdapter;
        import android.widget.TextView;

        import java.util.ArrayList;

/**
 * Created by gagan on 10/31/2015.
 */
public class MenuAdapter extends ArrayAdapter<Menu> {

    ArrayList<Menu> customerList;
    LayoutInflater vi;
    int Resource;
    ViewHolder holder;

    public MenuAdapter(Context context, int resource, ArrayList<Menu> objects) {
        super(context, resource, objects);
        vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        Resource = resource;
        customerList = objects;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // convert view = design
        View v = convertView;
        if (v == null) {
            holder = new ViewHolder();
            v = vi.inflate(Resource, null);
            //holder.imageview = (ImageView) v.findViewById(R.id.ivImage);
         //   holder.itemid = (TextView) v.findViewById(R.id.itemid);
            holder.itemavailability = (TextView) v.findViewById(R.id.itemavailability);
            holder.itemname = (TextView) v.findViewById(R.id.itemname);
            holder.itemprice = (TextView) v.findViewById(R.id.itemprice);
            v.setTag(holder);
        } else {
            holder = (ViewHolder) v.getTag();
        }
        //  holder.imageview.setImageResource(R.drawable.ic);
        // new DownloadImageTask(holder.imageview).execute(actorList.get(position).getImage());
        holder.itemname.setText(customerList.get(position).getName());
     //   holder.itemid.setText(customerList.get(position).getid());
        holder.itemavailability.setText(customerList.get(position).getAvailability());
        holder.itemprice.setText(customerList.get(position).getPrice());
        return v;

    }

    static class ViewHolder {
        //  public ImageView imageview;
        public TextView itemname;
        public TextView itemprice;
        public TextView itemavailability;
        public TextView itemid;
    }

   /* private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }

    }*/
}
