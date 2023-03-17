package com.example.faceheal;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class HospitalLogin extends AppCompatActivity {

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
        sta = false;

        imgCamera = findViewById(R.id.imageView);
        phone_number = findViewById(R.id.phone_number);
        take_image = findViewById(R.id.take_image);
        verify = findViewById(R.id.verify);
        match_status = findViewById(R.id.match_status);
        end_session = findViewById(R.id.end_session);



        verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
                            System.out.println(dataSnapshot.getValue());
                            System.out.println(key);
                            System.out.println("Hello");
                            System.out.println(field);



                            if (key.equals(field)){
                                String str = "Counter Number : "+String.valueOf(counter)+"\nName: "+cand_name+"\nEmail: "+cand_email;
                                match_status.setText(str);
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