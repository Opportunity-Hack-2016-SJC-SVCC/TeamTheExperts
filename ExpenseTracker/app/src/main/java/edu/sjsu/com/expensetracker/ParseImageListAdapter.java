package edu.sjsu.com.expensetracker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by patel on 9/24/2016.
 */
public class ParseImageListAdapter extends ArrayAdapter<String> {

    private Context mContext;
    private ArrayList<String> imageList;

    public ParseImageListAdapter(Context context, int resourceLayoutID, ArrayList<String> imageList)
    {
        super(context, resourceLayoutID, imageList);

        mContext = context;
        this.imageList = imageList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.image_list_row_layout, parent, false);
        }

        TextView tvName = (TextView) convertView.findViewById(R.id.product_name_textview);
        TextView tvHome = (TextView) convertView.findViewById(R.id.product_price_textview);

        ImageView deleteImageView = (ImageView) convertView.findViewById(R.id.delete_imageview);
        deleteImageView.setOnClickListener(deleteClickListener);
        deleteImageView.setTag(position);

        String product = imageList.get(position);

        tvHome.setText(product.substring(product.lastIndexOf(" ")));
        tvName.setText(product.substring(0, product.lastIndexOf(" ")));

        return convertView;
    }

    private View.OnClickListener deleteClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int position = (int)v.getTag();

            imageList.remove(position);
//            ((ParseImageActivity)mContext).deleteProductAt(position);
            notifyDataSetChanged();
        }
    };
}
