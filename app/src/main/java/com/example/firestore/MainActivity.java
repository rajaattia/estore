package com.example.firestore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    FloatingActionButton upload;
    RecyclerView recyclerView;
    ArrayList<ProjetModel> recyclelist;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference ref;
    SearchView searchView;

    ProjetAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        upload=findViewById(R.id.upload);
        recyclerView=findViewById(R.id.rv);
        searchView=findViewById(R.id.search);

        searchView.clearFocus();

        recyclelist=new ArrayList<>();
        firebaseDatabase=FirebaseDatabase.getInstance();
        adapter  =new ProjetAdapter(recyclelist,getApplicationContext());
        LinearLayoutManager linearLayoutManager =new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(),DividerItemDecoration.VERTICAL));
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setAdapter(adapter);
        ref =FirebaseDatabase.getInstance().getReference().child("product");
        adapter.setOnItemClickListener(new ProjetAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                recyclelist.remove(position);
                adapter.notifyItemRemoved(position);
            }
        });
        firebaseDatabase.getReference().child("product").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                    ProjetModel projetModel=dataSnapshot.getValue(ProjetModel.class);
                    recyclelist.add(projetModel);
                }
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i= new Intent(MainActivity.this,Addproduit.class);
                startActivity(i);
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                txtSearch(query);
                return true;
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        if (ref !=null){
            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    recyclelist =new ArrayList<>();
                    for (DataSnapshot ds :snapshot.getChildren())
                    {
                   recyclelist.add(ds.getValue(ProjetModel.class));
                    }

                }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(MainActivity.this,error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
    }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);

        MenuItem item=menu.findItem(R.id.search);


        return super.onCreateOptionsMenu(menu);

    }

    public void txtSearch (String str){

       ArrayList<ProjetModel>txtSearch =new ArrayList<>();
       for (ProjetModel projetModel :recyclelist){
           if (projetModel.getName().toLowerCase().contains(str.toLowerCase())){
               txtSearch.add(projetModel);

           }
       }

       adapter.searchDatalist(txtSearch);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id=item.getItemId();
        if (id==R.id.add){
            Intent intent =new Intent(MainActivity.this,Addproduit.class);
            startActivity(intent);
          return true ;
        } else if (id==R.id.acc) {
            Intent intent =new Intent(MainActivity.this,MainActivity2.class);
            startActivity(intent);
            return true ;
        }
        return super.onOptionsItemSelected(item);
    }
}