package com.example.faceheal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserPortal extends AppCompatActivity {
    EditText editTextTextPersonName6;
    Button check_button;

    DatabaseReference reference;
    FirebaseDatabase db;

    TextView counter_number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_portal);

        editTextTextPersonName6 = findViewById(R.id.editTextTextPersonName6);
        counter_number = findViewById(R.id.counter_number);
        check_button = findViewById(R.id.check_button);

        check_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String num = editTextTextPersonName6.getText().toString();
                if (num.equals("7276707467")){
                    counter_number.setText("\tCounter number : 1");
                } else if (num.equals("7822927505")) {
                    counter_number.setText("\tCounter number : 2");
                }


            }
        });

    }
}