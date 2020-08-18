package com.winbee.rbclasses.Utils;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.winbee.rbclasses.R;


public class SpinnerAdapter extends BaseAdapter {
    private Context context;
    private String[] list;

    public SpinnerAdapter(Context context , String[] list ) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount ( ) {
        return list.length;
    }
    @Override
    public Object getItem (int i ) {
        return list[ i ];
    }
    @Override
    public long getItemId ( int i ) {
        return i;
    }
    @Override
    public View getView (int i , View view , ViewGroup viewGroup ) {
        if ( view == null ) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService ( Context.LAYOUT_INFLATER_SERVICE );
            view = inflater.inflate ( R.layout.spinner_layout, viewGroup , false );
        }
        TextView tabName =  view.findViewById ( R.id.spinner_item );
        tabName.setText (list[ i ]);
        return view;
    }
}