package com.example.firestore;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class detailActivity3 extends AppCompatActivity {
    TextView detailname,detaildescritpion,detailprix,detaillibelle;
    ImageView detailimage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail3);
        detailname=findViewById(R.id.detailname);
        detaillibelle=findViewById(R.id.detaillibelle);
        detailprix=findViewById(R.id.detailprix);
        detaildescritpion=findViewById(R.id.detaildes);
        detailimage=findViewById(R.id.detailimg);
        Bundle bundle=getIntent().getExtras();
        if(bundle!=null){

            detailname.setText(bundle.getString("name"));
            detaildescritpion.setText(bundle.getString("description"));
            detailprix.setText(bundle.getString("prix"));
            detaillibelle.setText(bundle.getString("libelle"));
            Glide.with(this).load(bundle.getString("image")).into(detailimage);
        }
    }
}