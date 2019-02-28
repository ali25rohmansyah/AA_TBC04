package com.gregetdev.materitbcapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.gregetdev.materitbcapp.Model.Admin;

public class AdminLogin extends AppCompatActivity {

    private EditText pswd_admin;
    private Button btn_admin;
    private ImageView img_admin;
    private String Password_bener;
    private Intent intent;
    private DatabaseReference adminreference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);

        pswd_admin = findViewById(R.id.password_admin);
        btn_admin = findViewById(R.id.button_admin);
        img_admin = findViewById(R.id.gambar_admin);
        adminreference = FirebaseDatabase.getInstance().getReference("Admin");
        adminreference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Admin modelAdmin = dataSnapshot.child("01").getValue(Admin.class);
                Password_bener = modelAdmin.getPassword();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        intent = new Intent(AdminLogin.this, HalamanUser.class);
        btn_admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String password = pswd_admin.getText().toString();

                if (password.isEmpty()) {
                    showMessage("Password tidak boleh kosong");
                } else {
                    if (password.equals(Password_bener)) {
                        PindahLayout();
                    } else {
                        showMessage("Password salah");
                    }
                }
            }
        });
    }

    private void PindahLayout() {
        startActivity(intent);
        finish();
    }

    private void showMessage(String text) {
        Toast.makeText(getApplicationContext(), text, Toast.LENGTH_LONG).show();
    }
}
