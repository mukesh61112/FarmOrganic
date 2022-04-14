package com.example.farmorganic.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.farmorganic.R;
import com.example.farmorganic.SearchActivity;
import com.example.farmorganic.models.OrderByCateModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class OrderByCateAdapter extends RecyclerView.Adapter<OrderByCateAdapter.OBCViewHolder> {

    ArrayList<OrderByCateModel> orderByCateModelArrayList;
    Context context;

    public OrderByCateAdapter(ArrayList<OrderByCateModel> orderByCateModelArrayList, Context context) {
        this.orderByCateModelArrayList = orderByCateModelArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public OrderByCateAdapter.OBCViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.order_by_cate_recyclerview_cardv,parent,false);

       context = parent.getContext();
       return new OBCViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderByCateAdapter.OBCViewHolder holder, int position) {

           OrderByCateModel orderByCateModel=orderByCateModelArrayList.get(position);
        Picasso.get().load(orderByCateModel.getImgUrl()).into(holder.imageView);
        holder.textView.setText(orderByCateModel.getCateName());

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//              AppCompatActivity appCompatActivity=(AppCompatActivity) view.getContext();
//               SearchFragment searchFragment=new SearchFragment();
//               appCompatActivity.getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerView,searchFragment).commit();
//                FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
//                fragmentTransaction.replace(R.id.fragmentContainerView,new SearchFragment());
//                fragmentTransaction.commit();
                Intent intent = new Intent(context, SearchActivity.class);

                 context.startActivity(intent);

            }
        });


    }

    @Override
    public int getItemCount() {
        return orderByCateModelArrayList.size();
    }

    public class OBCViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;
        public OBCViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.order_by_cate_image);
            textView=itemView.findViewById(R.id.order_by_cate_name);
        }
    }
}
