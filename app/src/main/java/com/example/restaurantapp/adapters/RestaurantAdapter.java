package com.example.restaurantapp.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.restaurantapp.R;
import com.example.restaurantapp.pojos.Restaurant;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantAdapter.ViewHolder> {
    Context context;
    List<Restaurant> restaurants;

    public RestaurantAdapter(Context context, List<Restaurant> restaurants){
        this.context = context;
        this.restaurants = restaurants;
    }

    @NonNull
    @Override
    public RestaurantAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list,null,false);
        return new ViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(@NonNull RestaurantAdapter.ViewHolder holder, int position) {

        Restaurant restaurant = restaurants.get(position);
        Glide.with(context).load(restaurant.getIcon())
                .addListener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        return false;
                    }
                })
                .into(holder.imgIcon);
        holder.txtName.setText(restaurant.getName());
        List<String> types = restaurant.getTypes();
        String type = TextUtils.join(" | ",types);
//        for (String string : types){
//            type += " | "+ string;
//        }
        holder.txtType.setText(type);
        holder.txtOffer.setText(restaurant.getReference());

        if (TextUtils.isEmpty(restaurant.getRating())){
            holder.txtRatting.setText("0");
        } else {
            holder.txtRatting.setText(restaurant.getRating());
        }

        try {
            String open = restaurant.getOpening_hours().getOpen_now();
            if (open != null){
                if (restaurant.getOpening_hours().getOpen_now().equalsIgnoreCase("true")){
                    holder.txtTime.setText("Opened");
                    holder.txtTime.setTextColor(context.getColor(R.color.green));
                } else {
                    holder.txtTime.setText("Closed");
                    holder.txtTime.setTextColor(context.getColor(R.color.red));
                }
            } else {
//                holder.txtTime.setVisibility(View.GONE);
                holder.txtTime.setText("Not Given");
                holder.txtTime.setTextColor(context.getColor(R.color.red));
            }
        } catch (Exception e){
            e.printStackTrace();
            holder.txtTime.setText("May be closed/opened soon");
            holder.txtTime.setTextColor(context.getColor(R.color.red));
        }





    }

    @Override
    public int getItemCount() {
        return restaurants.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.txtName)
        TextView txtName;
        @BindView(R.id.txtType)
        TextView txtType;
        @BindView(R.id.txtOffer)
        TextView txtOffer;
        @BindView(R.id.txtTime)
        TextView txtTime;
        @BindView(R.id.txtDescription)
        TextView txtDescription;
        @BindView(R.id.txtRatting)
        TextView txtRatting;
        @BindView(R.id.imgIcon)
        ImageView imgIcon;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
