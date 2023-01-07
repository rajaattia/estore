package com.example.firestore;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Process;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

public class Addproduit extends AppCompatActivity {
TextView name,description,reference1,prix,libelle;
ImageView uploadbtn,imgproduit;
Button submit;
Uri imageUri;
RelativeLayout relativeLayout;
private FirebaseDatabase database;
private FirebaseStorage firebaseStorage;
ProgressDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addproduit);

        database=FirebaseDatabase.getInstance();
        firebaseStorage=FirebaseStorage.getInstance();
        dialog =new ProgressDialog(this);
        dialog.setProgress(ProgressDialog.STYLE_SPINNER);
        dialog.setMessage("please wait...");
        dialog.setCancelable(false);
        dialog.setTitle("product uploading");
        dialog.setCanceledOnTouchOutside(false);

        name=findViewById(R.id.name);
        description=findViewById(R.id.description);
        reference1=findViewById(R.id.reference1);
        prix=findViewById(R.id.prix);
        libelle=findViewById(R.id.libelle);
        uploadbtn=findViewById(R.id.uploadbtn);
        imgproduit=findViewById(R.id.imgproduit);
        submit=findViewById(R.id.submit);
         relativeLayout=findViewById(R.id.relative);
        uploadbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UploadImage ();
                relativeLayout.setVisibility(View.VISIBLE);
                uploadbtn.setVisibility(View.GONE);
            }
        });
submit.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        dialog.show();
    final StorageReference reference=firebaseStorage.getReference().child("product").child(System.currentTimeMillis()+"");
    reference.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
        @Override
        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
           reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
               @Override
               public void onSuccess(Uri uri) {
                 ProjetModel model=new ProjetModel();
                 model.setImgproduit(uri.toString());
                 model.setName(name.getText().toString());
                   model.setDescription(description.getText().toString());
                   model.setReference(reference1.getText().toString());
                   model.setPrix(prix.getText().toString());
                   model.setLibelle(libelle.getText().toString());

                   database.getReference().child("product").push().setValue(model).addOnSuccessListener(new OnSuccessListener<Void>() {
                       @Override
                       public void onSuccess(Void unused) {
                           Toast.makeText(Addproduit.this,"product upload sucessfully",Toast.LENGTH_SHORT).show();
                           dialog.dismiss();
                       }
                   }).addOnFailureListener(new OnFailureListener() {
                       @Override
                       public void onFailure(@NonNull Exception e) {
                           Toast.makeText(Addproduit.this,"error",Toast.LENGTH_SHORT).show();
                           dialog.dismiss();
                       }
                   });

               }
           });
        }
    });
    }
});

    }

    private void UploadImage() {
        Dexter.withContext(this)
                .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                        Intent intent=new Intent();
                        intent.setType("image/*");
                        intent.setAction(intent.ACTION_GET_CONTENT);
                        startActivityForResult(intent,101);
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
                        Toast.makeText(Addproduit.this, "permission denied", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                     permissionToken.continuePermissionRequest();
                    }
                }).check();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==101&& resultCode==RESULT_OK){
            imageUri = data.getData();
            imgproduit.setImageURI(imageUri);
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);

        MenuItem item=menu.findItem(R.id.search);


        return super.onCreateOptionsMenu(menu);

    }
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id=item.getItemId();
        if (id==R.id.acc) {
            Intent intent =new Intent(Addproduit.this,MainActivity.class);
            startActivity(intent);
            return true ;
        }
        return super.onOptionsItemSelected(item);
    }
}