package com.example.uberv.recommendations;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.uberv.recommendations.model.ActiveListings;
import com.example.uberv.recommendations.model.Listing;
import com.squareup.picasso.Picasso;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class ListingAdapter extends RecyclerView.Adapter<ListingAdapter.ListingHolder>
        implements Callback<ActiveListings>{
    public static final String LOG_TAG=ListingAdapter.class.getSimpleName();

    private LayoutInflater inflater;
    private ActiveListings activeListings;
    private MainActivity activity;

    public ListingAdapter(MainActivity activity){
        inflater=LayoutInflater.from(activity);
        this.activity=activity;
    }

    @Override
    public ListingHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.layout_listing,parent,false);
        return new ListingHolder(view);
    }

    @Override
    public void onBindViewHolder(ListingHolder holder, int position) {
        final Listing listing = activeListings.results[position];
        holder.setTitle(listing.title);
        holder.setPrice(listing.price);
//        holder.setShopName(listing.shop.shop_name);
//        holder.setImage(listing.images[0].url_570xN);
    }

    @Override
    public int getItemCount() {
        if(activeListings==null)
            return 0;
        else if(activeListings.results==null)
            return 0;
        return activeListings.results.length;
    }

    @Override
    public void success(ActiveListings activeListings, Response response) {
        this.activeListings=activeListings;
        notifyDataSetChanged();
        activity.showList();
    }

    @Override
    public void failure(RetrofitError error) {
        activity.showError(error.getMessage());
    }

    public  ActiveListings getActiveListings(){
        return activeListings;
    }

    public class ListingHolder extends RecyclerView.ViewHolder{
        private ImageView imageView;
        private TextView titleView;
        private TextView priceView;
        private TextView shopNameView;

        public ListingHolder(View itemView) {
            super(itemView);

            imageView= (ImageView) itemView.findViewById(R.id.listing_image);
            titleView= (TextView) itemView.findViewById(R.id.listing_title);
            priceView= (TextView) itemView.findViewById(R.id.listing_price);
            shopNameView= (TextView) itemView.findViewById(R.id.listing_shop_name);
        }

        public void setTitle(String text){
            titleView.setText(text);
        }

        public void setPrice(String text){
            priceView.setText(text);
        }

        public void setShopName(String text){
            shopNameView.setText(text);
        }

        public void setImage(String url){
            Picasso.with(imageView.getContext())
                    .load(url)
                    .into(imageView);
        }
    }

}
