package com.example.xuchen.vision1;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder>{
    private List<Cloth> mClothList;
    public String file_id = "c1";
    static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView cImage;
        public ViewHolder(View view){
            super(view);
            cImage=(ImageView)view.findViewById(R.id.cloth_image);

        }
    }
    public MyAdapter(List<Cloth>clothList){
        mClothList=clothList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.cloth_item,parent,false);
        final ViewHolder holder=new ViewHolder(view);
        holder.cImage.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                int position=holder.getAdapterPosition();
                Cloth cloth=mClothList.get(position);
                file_id=cloth.getName();
                Toast.makeText(v.getContext(),cloth.getName(),Toast.LENGTH_SHORT).show();
                //TODO:cloth id
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Cloth cloth = mClothList.get(position);
        holder.cImage.setImageResource(cloth.getImageId());
    }

    @Override
    public int getItemCount(){
        return mClothList.size();
    }
}
