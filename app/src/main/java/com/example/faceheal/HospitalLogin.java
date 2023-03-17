package com.example.faceheal;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;

public class HospitalLogin extends AppCompatActivity {

    StorageReference storageReference;
    ProgressDialog progressDialog;
    DatabaseReference reference;
    FirebaseDatabase db;
    ImageView imgCamera;
    EditText phone_number;
    Button take_image;
    Button verify;
    TextView match_status;
    Button end_session;
    boolean sta;


    int counter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital_login);

        counter =1;

        imgCamera = findViewById(R.id.imageView);
        phone_number = findViewById(R.id.phone_number);
        take_image = findViewById(R.id.take_image);
        verify = findViewById(R.id.verify);
        match_status = findViewById(R.id.match_status);
        end_session = findViewById(R.id.end_session);

        end_session.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HospitalLogin.this, MainActivity.class));
            }
        });



        verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sta = false;
                String field = phone_number.getText().toString();
                db =FirebaseDatabase.getInstance("https://faceheal-default-rtdb.firebaseio.com/");
                reference = db.getReference("Users");

                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                            String key = dataSnapshot.child("Phone").getValue().toString();
                            String cand_name = dataSnapshot.child("Name").getValue().toString();
                            String cand_email = dataSnapshot.child("Email").getValue().toString();

                            if (key.equals(field)){
                                progressDialog = new ProgressDialog(HospitalLogin.this);
                                progressDialog.setMessage("Fetching image....");
                                progressDialog.setCancelable(false);
                                progressDialog.show();
                                storageReference = FirebaseStorage.getInstance().getReference("Images/"+field);
                                try {
                                    File localfile = File.createTempFile("tempfile",".jpg");
                                    storageReference.getFile(localfile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                                        @Override
                                        public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                                            if (progressDialog.isShowing()){
                                                progressDialog.dismiss();
                                            }

                                            Bitmap bitmap = BitmapFactory.decodeFile(localfile.getAbsolutePath());
                                            imgCamera.setImageBitmap(bitmap);

                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {

                                            if (progressDialog.isShowing()){
                                                progressDialog.dismiss();
                                            }
                                            Toast.makeText(HospitalLogin.this, "Failed To Retrieve image", Toast.LENGTH_SHORT).show();

                                        }
                                    });
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }


                                String str = "Counter Number : "+String.valueOf(counter)+"\nName: "+cand_name+"\nEmail: "+cand_email;
                                match_status.setText(str);
                                Toast.makeText(HospitalLogin.this, "Candidate Matched..!", Toast.LENGTH_SHORT).show();
                                sta = true;
                                counter = counter+1;
                                break;


                            }

                        }
                        if (sta==false){
                            match_status.setText("Not Matching Candidate");
                        }


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });


        take_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent iCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(iCamera,100);
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK){
            if (requestCode==100){
                Bitmap img = (Bitmap)(data.getExtras().get("data"));
                imgCamera.setImageBitmap(img);
            }
        }
    }
}