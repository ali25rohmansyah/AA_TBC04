package com.gregetdev.materitbcapp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;

import com.applandeo.materialcalendarview.EventDay;
import com.applandeo.materialcalendarview.listeners.OnDayClickListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.gregetdev.materitbcapp.Common.Common;
import com.gregetdev.materitbcapp.Model.Users;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;


/**
 * A simple {@link Fragment} subclass.
 */
public class CalenderFragment extends Fragment {

    CalendarView calendarView;
    TextView text, txt_today;
    Button btn_minumObat, btn_kunjungan, btn_ok, btn_simpan, btn_batal;
    private SimpleDateFormat dateFormatter;
    private String KEY_DATE, KEY_MONTH;
    private int date;
    private int month;
    private String selectedDate, mObat;

    AlertDialog.Builder dialog;
    LayoutInflater inflater;
    View dialogView;

    List<EventDay> events = new ArrayList<>();

    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    final DatabaseReference table_user = database.getReference("User");
    final DatabaseReference table_kunjunganDokter = database.getReference("KunjunganDokter");

    public CalenderFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_calender, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        text = view.findViewById(R.id.text);
        txt_today = view.findViewById(R.id.txt_today);

        Calendar calendar = Calendar.getInstance();

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference table_users = database.getReference("User");

        for (int i = 0; i < 360; i++) {
            Calendar calendars = Calendar.getInstance();
            calendars.add(Calendar.DAY_OF_WEEK, i);
            events.add(new EventDay(calendars, R.drawable.medicine));
        }

        com.applandeo.materialcalendarview.CalendarView calendarView = view.findViewById(R.id.calendarView);

        final Calendar newCalendar = Calendar.getInstance();
        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);

        txt_today.setText("Hari ini : "+android.text.format.DateFormat.format("EEEE", newCalendar.getTime()) +", " +dateFormatter.format(newCalendar.getTime()));

        calendarView.setOnDayClickListener(new OnDayClickListener() {
            @Override
            public void onDayClick(EventDay eventDay) {
                Calendar clickedDayCalendar = eventDay.getCalendar();

                selectedDate = dateFormatter.format(eventDay.getCalendar().getTime());

                DialogForm(selectedDate);
                Calendar calendars = Calendar.getInstance();
                events.add(new EventDay(clickedDayCalendar, R.drawable.medicine));
            }
        });
        calendarView.setEvents(events);
    }

    private void DialogForm(String selectedDate){

        android.app.AlertDialog.Builder al = new android.app.AlertDialog.Builder(getActivity());
        inflater = getLayoutInflater();
        dialogView = inflater.inflate(R.layout.minum_obat, null);

        final Calendar newCalendar = Calendar.getInstance();
        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);

        al.setTitle(dateFormatter.format(newCalendar.getTime()))
                .setView(dialogView)
                .setIcon(R.mipmap.ic_launcher)
                .setCancelable(true);

        android.app.AlertDialog alertDialog = al.create();

        btn_minumObat = dialogView.findViewById(R.id.btn_minumObat);
        btn_kunjungan = dialogView.findViewById(R.id.btn_Kunjungan);

        alertDialog.show();

        btn_minumObat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(selectedDate.equals(dateFormatter.format(newCalendar.getTime()))){
                    alertDialog.dismiss();
                    informasi("Anda telah selesai minum obat");
                    table_user.child(Common.currentUser.getNoHandphone()).child("minumObat").setValue(selectedDate);
                }

                else{
                    alertDialog.dismiss();
                    informasi("Anda tidak bisa minum obat selain hari ini");
                }
            }
        });

        btn_kunjungan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
                kunjunganDokterDialog(selectedDate);
            }
        });
    }

    public void informasi(String s) {

        android.app.AlertDialog.Builder al = new android.app.AlertDialog.Builder(getActivity());
        inflater = getLayoutInflater();
        dialogView = inflater.inflate(R.layout.informasi, null);

        al.setTitle("Informasi")
                .setView(dialogView)
                .setMessage(s)
                .setCancelable(true);

        android.app.AlertDialog alertDialog = al.create();
        alertDialog.show();

        btn_ok = dialogView.findViewById(R.id.btn_ok);
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
    }

    public void kunjunganDokterDialog(String selectedDate) {

        android.app.AlertDialog.Builder al = new android.app.AlertDialog.Builder(getActivity());
        inflater = getLayoutInflater();
        dialogView = inflater.inflate(R.layout.kunjungandokter, null);

        al.setTitle(selectedDate)
                .setView(dialogView)
                .setCancelable(true);

        android.app.AlertDialog alertDialog = al.create();
        alertDialog.show();

        btn_simpan = dialogView.findViewById(R.id.btn_simpan);
        btn_batal = dialogView.findViewById(R.id.btn_batal);
        table_user.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Users user = dataSnapshot.child(Common.currentUser.getNoHandphone()).getValue(Users.class);
                mObat = user.getKunjunganDokter();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        btn_simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mObat.equals("00-00-0000")){
                    table_user.child(Common.currentUser.getNoHandphone()).child("kunjunganDokter").setValue(selectedDate);
                    alertDialog.dismiss();
                    informasi("Kunjungan Berhasil dibuat");
                }else{
                    informasi("Anda harus menyelesaikan kunjungan pada tanggal "+selectedDate+" atau membatalkanya");
                }
                alertDialog.dismiss();
            }
        });
        btn_batal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mObat.equals(selectedDate)) {

                    table_user.child(Common.currentUser.getNoHandphone()).child("kunjunganDokter").setValue("00-00-0000");
                    informasi("Kunjungan Berhasil dibatalkan");

                }else{

                    informasi("Maaf anda tidak bisa membatalkan kunjungan yang anda tidak jadwalkan");
                }
                alertDialog.dismiss();
            }
        });

    }
}
