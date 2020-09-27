package com.example.metodoshttp.ui.adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.metodoshttp.R;

import java.util.ArrayList;

public class AdapterUser extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    Context context;
    ArrayList<Item> mArray;
    SendData listen;

    public AdapterUser(Context context,SendData listen){
        this.context=context;
        mArray=new ArrayList<>();
        this.listen=listen;
    }

    public AdapterUser(Context context){
        this.context=context;
        mArray=new ArrayList<>();
    }

    public void add(Item item){
        mArray.add(item);
        notifyItemInserted(mArray.indexOf(item));
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if(viewType==1){
            view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_get_user,parent,false);
            return new HolderGet(view);
        }else if(viewType==2){
            view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_patch,parent,false);
            return new HolderPatch(view);
        }else {
            view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_delete,parent,false);
            return new HolderDelete(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder,final int position) {
        final Item it=mArray.get(position);
        if(getItemViewType(position)==1){
            ((HolderGet)holder).name.setText(it.getName());
            ((HolderGet)holder).email.setText(it.getEmail());
        }else if(getItemViewType(position)==2){
            ((HolderPatch)holder).name.setText(it.getName());
            ((HolderPatch)holder).email.setText(it.getEmail());
            ((HolderPatch)holder).view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle bundle=new Bundle();
                    bundle.putString("name",it.getName());
                    bundle.putString("email",it.getEmail());
                    bundle.putString("id",it.getId());
                    listen.dataUser(bundle);
                }
            });
        }else{
            ((HolderDelete)holder).name.setText(it.getName());
            ((HolderDelete)holder).email.setText(it.getEmail());
            ((HolderDelete)holder).btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle bundle=new Bundle();
                    bundle.putString("id",it.getId());
                    listen.dataUser(bundle);
                    delete(position);
                }
            });
        }
    }

    private void delete(int position) {
        mArray.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public int getItemCount() {
        return mArray.size();
    }

    @Override
    public int getItemViewType(int position) {
        //return super.getItemViewType(position);
        if(mArray.get(position).getType()==1){
            return 1;
        }else if(mArray.get(position).getType()==2){
            return 2;
        }else{
            return 3;
        }
    }

    class HolderGet extends RecyclerView.ViewHolder {
        TextView name,email;
        public HolderGet(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.name_home);
            email=itemView.findViewById(R.id.email_home);
        }
    }

    class HolderPatch extends RecyclerView.ViewHolder{
        TextView name,email;
        View view;
        public HolderPatch(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.name_not);
            email=itemView.findViewById(R.id.email_not);
            view=itemView;
        }
    }

    class HolderDelete extends RecyclerView.ViewHolder{
        TextView name,email;
        Button btn;
        public HolderDelete(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.delete_name);
            email=itemView.findViewById(R.id.delete_email);
            btn=itemView.findViewById(R.id.delete_btn);
        }
    }

    public interface SendData{
        void dataUser(Bundle data);
    }
}
