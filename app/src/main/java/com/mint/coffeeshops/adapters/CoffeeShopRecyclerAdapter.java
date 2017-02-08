package com.mint.coffeeshops.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mint.coffeeshops.R;
import com.mint.coffeeshops.activities.WebViewActivity;
import com.mint.coffeeshops.model.CoffeeShop;

import java.util.List;

public class CoffeeShopRecyclerAdapter extends RecyclerView.Adapter<CoffeeShopRecyclerAdapter.ViewHolder> {
    private List<CoffeeShop> mVenues;
    private Context mContext;


    public CoffeeShopRecyclerAdapter(List<CoffeeShop> VenueList, Context context) {
        mVenues = VenueList;
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View rowView = LayoutInflater.from(parent.getContext()).inflate(R.layout.venue_recycler_row, parent, false);
        return new ViewHolder(rowView);
    }

    @Override
    public int getItemCount() {
        return mVenues.size();
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        final CoffeeShop selectedVenue = mVenues.get(position);
        final ViewHolder finalHolder = holder;

        holder.CoffeeShopName.setText("Coffee Shop : " + selectedVenue.getCoffeeShopName());
        holder.CoffeeShopRating.setText("Rating :" + selectedVenue.getCoffeeShopRating());
        holder.CoffeeShopAddress.setText("Address :" + selectedVenue.getAddress());
        holder.CoffeeShopDistance.setText("Distance :" + selectedVenue.getDistance());
        holder.CoffeeShopUrl.setText(selectedVenue.getCoffeeShopUrl());


    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView CoffeeShopName, CoffeeShopRating, CoffeeShopAddress, CoffeeShopUrl, CoffeeShopDistance;

        Context context;

        public ViewHolder(View itemView) {
            super(itemView);

            context = itemView.getContext();
            itemView.setOnClickListener(this);

            CoffeeShopName = (TextView) itemView.findViewById(R.id.coffee_shop_name);
            CoffeeShopRating = (TextView) itemView.findViewById(R.id.venue_rating);
            CoffeeShopAddress = (TextView) itemView.findViewById(R.id.coffee_shop_address);
            CoffeeShopDistance = (TextView) itemView.findViewById(R.id.coffee_shop_distance);
            CoffeeShopUrl = (TextView) itemView.findViewById(R.id.coffee_shop_url);

        }

        @Override
        public void onClick(View view) {
            if (CoffeeShopUrl.getText().length() > 0) {
                Intent intent = new Intent(context, WebViewActivity.class);
                intent.putExtra("coffee_shop_url", CoffeeShopUrl.getText());
                context.startActivity(intent);
            } else {
                Toast.makeText(context, R.string.msg_no_website, Toast.LENGTH_LONG).show();
            }

        }
    }
}