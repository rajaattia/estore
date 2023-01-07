package com.example.firestore;

import android.content.ClipData;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class ProjetAdapter extends RecyclerView.Adapter<ProjetAdapter.ViewHolder> {
    ArrayList<ProjetModel> list;
    Context context;
    private OnItemClickListener listener;
    public ProjetAdapter(ArrayList<ProjetModel> list, Context context) {
        this.list = list;
        this.context = context;
    }
    public class ViewHolder extends RecyclerView.ViewHolder  {
        TextView tvname,tvdescription,tvreference1,tvprix,tvlibelle;
        ImageView imgproduit;
        Button delete;
        CardView recCard;

        public ViewHolder(@NonNull View itemView,OnItemClickListener listener) {
            super(itemView);
            tvname=itemView.findViewById(R.id.tvname);
            tvdescription=itemView.findViewById(R.id.tvdescription);
            tvreference1=itemView.findViewById(R.id.tvreference);
            tvprix=itemView.findViewById(R.id.tvprix);
            tvlibelle=itemView.findViewById(R.id.tvlibelle);
            imgproduit=itemView.findViewById(R.id.tvimg);
            delete=(Button)itemView.findViewById(R.id.delete);
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                 listener.onItemClick(getAdapterPosition());
                }
            });
        }

    }
    public interface OnItemClickListener{
        void onItemClick(int position);
    }
public void setOnItemClickListener(OnItemClickListener clickListener){
    listener=clickListener;
}
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.item,parent,false);

        return new ViewHolder(view,listener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
     ProjetModel model =list.get(position);
        Picasso.get().load(model.getImgproduit()).placeholder(R.drawable.gamer).into(holder.imgproduit);
      holder.tvname.setText(model.getName());
      holder.tvdescription.setText(model.getDescription());
      holder.tvreference1.setText(model.getReference());
      holder.tvlibelle.setText(model.getLibelle());
      holder.tvprix.setText(model.getPrix());

      //holder.recCard.setOnClickListener(new View.OnClickListener() {
          //@Override
          //public void onClick(View v) {
              //Intent intent =new Intent(context,detailActivity3.class);
             // intent.putExtra("image",list.get(holder.getAdapterPosition()).getImgproduit());
            //  intent.putExtra("name",list.get(holder.getAdapterPosition()).getName());
             // intent.putExtra("description",list.get(holder.getAdapterPosition()).getDescription());
             // intent.putExtra("prix",list.get(holder.getAdapterPosition()).getPrix());
             // intent.putExtra("libelle",list.get(holder.getAdapterPosition()).getLibelle());
            // context.startActivity(intent);
          }
      //});

    //}

public void searchDatalist(ArrayList<ProjetModel> searchlist) {
       list=searchlist;
       notifyDataSetChanged();
}


    @Override
    public int getItemCount() {
        return list.size();
    }



}
