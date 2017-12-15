package com.joshpayne1.androidartist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import java.util.ArrayList;

/**
 * Created by joshpayne1 on 12/15/17.
 */

public class ImageAdapter extends BaseAdapter {
    private Context mContext;
    private EditorGameState ed;

    public ImageAdapter(Context c) {
        mContext = c;
        ed = EditorGameState.getInstance();
    }

    public int getCount() {
        return mThumbIds().size();
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {
            // if it's not recycled, initialize some attributes
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(85, 85));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(8, 8, 8, 8);
            LayoutInflater inflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);



            View gridView;
            if (convertView == null) {

                gridView = new View(mContext);

                // get layout from mobile.xml
                gridView = inflater.inflate(R.layout.item, null);


            } else {
                gridView = (View) convertView;
            }
        } else {
            imageView = (ImageView) convertView;
        }

        imageView.setImageBitmap(ed.getBitmapImage(mThumbIds().get(position)));
        return imageView;
    }

    // references to our images
    private ArrayList<String> mThumbIds () {
        return ed.getFilesWithExt("image");
    }
}
