package com.example.project.location_advetise;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class ShopListAdaptor extends ArrayAdapter<Shop_List> {
    private final int resourceLayout;
    private final Context mContext;
    public ShopListAdaptor(@NonNull Context context, int resource, List<Shop_List> shop_lists) {
        super(context, resource);
        resourceLayout = resource;
        mContext = context;
    }
    @SuppressLint({"SetTextI18n", "DefaultLocale"})
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if (view == null) view = LayoutInflater.from(mContext).inflate(resourceLayout, null);
        Shop_List item = getItem(position);
        ((TextView) view.findViewById(R.id.title)).setText(item.name);
        ((TextView) view.findViewById(R.id.subtitle)).setText(String.format("%.2f km", item.distance));
        ((TextView) view.findViewById(R.id.offer)).setText(item.shops);
//        ((CardView) view.findViewById(R.id.trailing_avatar))
//                .setCardBackgroundColor(item.plugsAvailable > 1 ? Colors.green400 : item.plugsAvailable > 0 ? Colors.orange400 : Colors.red400);
        return view;
    }
}
