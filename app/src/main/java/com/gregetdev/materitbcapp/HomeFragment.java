package com.gregetdev.materitbcapp;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.gregetdev.materitbcapp.Common.Common;
import com.gregetdev.materitbcapp.Model.Users;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import io.paperdb.Paper;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    TextView txt_nama, txt_kota, txt_tanggalLahir, txt_jkel, txt_jtb, nama, txt_minumObat;
    SimpleDateFormat dateFormatter;
    String minumObat="lol";
    DatabaseReference table_user = FirebaseDatabase.getInstance().getReference("User");


    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Views
        txt_nama = view.findViewById(R.id.txt_nama);
        txt_kota = view.findViewById(R.id.txt_kota);
        txt_tanggalLahir = view.findViewById(R.id.txt_tanggaLahir);
        txt_jkel = view.findViewById(R.id.txt_jkel);
        txt_jtb = view.findViewById(R.id.txt_jtb);
        txt_minumObat = view.findViewById(R.id.txt_minumObat);

        Paper.init(getActivity());
        String User = Paper.book().read(Common.User_Key);

        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        final Calendar newCalendar = Calendar.getInstance();

        table_user.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Users user = dataSnapshot.child(User).getValue(Users.class);
                String mObat = user.getMinumObat();
                if(mObat.equals(dateFormatter.format(newCalendar.getTime()))){
                    minumObat = "Sudah";
                }
                else{
                    minumObat = "Belum";
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        table_user.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Users user = dataSnapshot.child(User).getValue(Users.class);
                txt_nama.setText("Nama : "+user.getNama());
                txt_kota.setText("Kota :"+user.getKota());
                txt_tanggalLahir.setText("Tanggal Lahir :"+user.getTanggalLahir());
                txt_jkel.setText("Jenis Kelamin : "+user.getJkel());
                txt_jtb.setText("Jenit TB : "+user.getJenisTb());
                txt_minumObat.setText(minumObat);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }



}
