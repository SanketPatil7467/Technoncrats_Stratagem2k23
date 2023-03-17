package com.example.faceheal;

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

public class HospitalLogin extends AppCompatActivity {
    ImageView imgCamera;
    EditText phone_number;
    Button take_image;
    Button verify;
    TextView match_status;
    Button end_session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital_login);

        imgCamera = findViewById(R.id.imageView);
        phone_number = findViewById(R.id.phone_number);
        take_image = findViewById(R.id.take_image);
        verify = findViewById(R.id.verify);
        match_status = findViewById(R.id.match_status);
        end_session = findViewById(R.id.end_session);


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