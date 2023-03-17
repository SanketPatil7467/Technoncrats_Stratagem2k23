package com.example.faceheal;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.net.URI;

public class UserRegistration extends AppCompatActivity {
    EditText user_email;
    EditText contact_number;
    EditText user_name;
    private final int GALLERY_REQ_CODE = 1000;
    private Button upload_image;
    private Button submit;
    StorageReference storageReference;
    DatabaseReference databaseReference;

    Uri imageUri;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_registration);

        user_email = findViewById(R.id.user_email);
        user_name = findViewById(R.id.user_name);
        contact_number = findViewById(R.id.contact_number);
        upload_image = findViewById(R.id.upload_image);
        submit = findViewById(R.id.submit);

        storageReference = FirebaseStorage.getInstance().getReference();
        databaseReference = FirebaseDatabase.getInstance("https://faceheal-default-rtdb.firebaseio.com/").getReference("Images");

        upload_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                selectImage();

            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    public void selectImage(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,100);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==100 && data != null && data.getData() != null){
            imageUri = data.getData();
            uploadImage();
        }
    }

    private void uploadImage(){

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Uploading.....");
        progressDialog.show();


        StorageReference reference = storageReference.child("Images/"+contact_number.getText().toString());
        reference.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(UserRegistration.this, "Image Uploaded", Toast.LENGTH_SHORT).show();

            }
        });

    }

}