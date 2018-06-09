package com.tga.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.tga.Activity.DiscountDetails;
import com.tga.Controller.AgentController;
import com.tga.Controller.ProgramController;
import com.tga.Controller.SimpleCallback;
import com.tga.R;

import java.util.List;

/**
 * Created by Mada on 3/1/2018.
 */

public class DiscountsAdapter extends RecyclerView.Adapter<DiscountsAdapter.MyViewHolder> {

    private Context context;
    private List<ProgramController> progControls;




    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView imgDisc;
        TextView title,companyName, discount, txtDiscountEndDate;

        public MyViewHolder(View view) {
            super(view);
            imgDisc = (ImageView)itemView.findViewById(R.id.imgDisc);
            title = (TextView)itemView.findViewById(R.id.title);
            companyName = (TextView)itemView.findViewById(R.id.companyName);
            discount = (TextView) itemView.findViewById(R.id.txtDiscount);
            txtDiscountEndDate = (TextView) itemView.findViewById(R.id.txtEndDate);
        }

    }


    public DiscountsAdapter(Context mainActivityContacts,List<ProgramController> progControls) {
        this.context = mainActivityContacts;
        this.progControls = progControls;
    }


    @Override
    public DiscountsAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_discount, parent, false);



        return new DiscountsAdapter.MyViewHolder(itemView);


    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBindViewHolder(final DiscountsAdapter.MyViewHolder holder, final int position) {

        final ProgramController progControl = progControls.get(position);
        //holder.imgDisc.setImageResource(progControl.getOfferImg()); //TODO: places photos
        AgentController.getByID(new SimpleCallback<AgentController>() {
            @Override
            public void callback(AgentController data) {
                holder.companyName.setText(data.getName());
                holder.title.setText(progControl.getTitle());
                String s = String.valueOf(progControl.getPrice()) +
                        " (" + String.valueOf(progControl.getDiscountPercentage() * 100) + "%" + ")";
                holder.discount.setText(s);
                holder.txtDiscountEndDate.setText(progControl.getDiscountEndDate());

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(context, DiscountDetails.class);
                        intent.putExtra("PROG_ID", progControl.getId());
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                    }
                });
            }
        }, progControl.getOwnerID());
    }

    @Override
    public int getItemCount() {
        return progControls.size();
    }
}