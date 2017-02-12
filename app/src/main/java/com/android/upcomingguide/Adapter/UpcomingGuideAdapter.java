package com.android.upcomingguide.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.upcomingguide.Pojo.UpcomingGuides;
import com.android.upcomingguide.R;
import com.android.upcomingguide.Views.ActivityLogIn;
import com.android.upcomingguide.database.DBCartAdapter;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by shweta on 2/11/2017.
 */

public class UpcomingGuideAdapter extends RecyclerView.Adapter<UpcomingGuideAdapter.ViewHolder>  {
    private Context context;
    ArrayList<UpcomingGuides.Datas> upcomingGuidesDataList;
    UpcomingGuides.Datas deletingItem, editItem;

    public UpcomingGuideAdapter(Context context, ArrayList<UpcomingGuides.Datas> upcomingGuidesList) {
        this.upcomingGuidesDataList = upcomingGuidesList;
        this.context = context;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_upcoming_guide, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
    try {

        final UpcomingGuides.Datas datas = this.upcomingGuidesDataList.get(position);

        holder.textEndDate.setText("" + datas.getEndDate());
        holder.textName.setText("" + datas.getName());
        holder.textQuantity.setText("" + datas.getQuantity());
        Picasso.with(context.getApplicationContext()).load(datas.getIcon())
                .into(holder.imageItem);

        holder.textEndDate.setTag(datas);
        holder.textName.setTag(datas);
        holder.imageItem.setTag(datas);
//        holder.llItem.setTag(position);
        holder.imageMinus.setTag(datas);
        holder.imagePlus.setTag(datas);

        holder.imagePlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editItem = (UpcomingGuides.Datas) v.getTag();
                onUpdateData(editItem);
                holder.textQuantity.setText(""+getQuantityVal(editItem));
            }

        });

        holder.imageMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deletingItem = (UpcomingGuides.Datas) v.getTag();
                if(deletingItem.getQuantity()>0) {
                    DBCartAdapter dbCartAdapter = new DBCartAdapter(context);
                    dbCartAdapter.deleteMenuItemDetailInCart(deletingItem);
                    holder.textQuantity.setText("" + getQuantityVal(deletingItem));
                }
            }

        });

     /*   holder.llItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = (int) v.getTag();
                pickedMenu(position);
            }

        });*/
    }catch (Exception e)
    {
        e.printStackTrace();
    }
    }
    int getQuantityVal(UpcomingGuides.Datas datas)
    {
        DBCartAdapter dbCartAdapter = new DBCartAdapter(context);
        UpcomingGuides.Datas datas1=dbCartAdapter.getSingleData(datas);
        datas.setQuantity(datas1.getQuantity());// set value in quantity text
        ((ActivityLogIn)context).setValuesInCart();//set Value in cart
        return  datas1.getQuantity();
    }

    void onUpdateData(UpcomingGuides.Datas datas)
    {
        DBCartAdapter dbCartAdapter = new DBCartAdapter(context);
        dbCartAdapter.updateMenuItemDetailInCart(datas);
    }

    public void pickedMenu(final int position) {
        try {
            final CharSequence[] options = {"Add To Cart", "Cancel"};
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("Menu!");
            builder.setItems(options, new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int item) {
                    if (options[item].equals("Add To Cart")) {
                        if( upcomingGuidesDataList.get(position).getQuantity()==0){
                            onUpdateData(upcomingGuidesDataList.get(position));

                        }

                    } else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
                }
            }); builder.show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

    @Override
    public int getItemCount() {
        return (upcomingGuidesDataList.size());
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.txtEndDate)
        TextView textEndDate;
        @Bind(R.id.txtHeaderName)
        TextView textName;
        @Bind(R.id.imgItem)
        ImageView imageItem;
        @Bind(R.id.image_minus_item)
        ImageView imageMinus;
        @Bind(R.id.image_plus_item)
        ImageView imagePlus;
        @Bind(R.id.text_quantity)
        TextView textQuantity;
        @Bind(R.id.llItem)
        LinearLayout llItem;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);

        }
    }
}
