package com.gregetdev.materitbcapp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.gregetdev.materitbcapp.Model.MateriModel;
import com.squareup.picasso.Picasso;

public class HalamanMateri extends AppCompatActivity {

    FirebaseDatabase database;
    DatabaseReference table_materi;
    StorageReference mStorageReference;

    String materiID = "";
    ImageView IMG;

    TextView Judul, materi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_halaman_materi);



        Judul = (TextView)findViewById(R.id.judul_text);
        materi = (TextView)findViewById(R.id.materi_text);
        IMG = findViewById(R.id.materi_image);
        IMG.setVisibility(View.INVISIBLE);
        materi.setVisibility(View.INVISIBLE);

        database = FirebaseDatabase.getInstance();
        table_materi = database.getReference("Materi");
        //mStorageReference = FirebaseStorage.getInstance().getReference();


        //get materiID from intent
        if(getIntent() != null){
            materiID = getIntent().getStringExtra("materiID");
        }
        if (!materiID.isEmpty()){
            getMateri(materiID);
        }



    }



    private void getMateri(final String materiID) {
        table_materi.child(materiID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                MateriModel materiModel = dataSnapshot.getValue(MateriModel.class);

                Judul.setText(materiModel.getJudul());
                if (materiModel.getType().equals("text")) {
                    materi.setVisibility(View.VISIBLE);
                    materi.setText(materiModel.getMateri());
                    IMG.setPadding(0,0,0,0);
                }else if(materiModel.getType().equals("picture")){
                    IMG.setVisibility(View.VISIBLE);
                    Picasso.get().load(materiModel.getMateri()).into(IMG);
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }



}
