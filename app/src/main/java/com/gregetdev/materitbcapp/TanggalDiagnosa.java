package com.gregetdev.materitbcapp;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.gregetdev.materitbcapp.Common.Common;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import static android.text.TextUtils.isEmpty;

public class TanggalDiagnosa extends AppCompatActivity {

    private DatePickerDialog datePickerDialog;
    private SimpleDateFormat dateFormatter;
    private TextView txt_datePicker;
    private Button next;
    private int pYear, pMonth, pDay;
    private String KEY_DATE, KEY_MONTH;
    private String selectedDate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tanggal_diagnosa);

        //Views
        txt_datePicker = (TextView)findViewById(R.id.txt_datePicker);
        next = (Button)findViewById(R.id.next) ;

        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);

        txt_datePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDateDialog(view);
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final FirebaseDatabase database = FirebaseDatabase.getInstance();
                final DatabaseReference table_users = database.getReference("User");

                if(isEmpty(txt_datePicker.getText().toString())){
                    Toast.makeText(TanggalDiagnosa.this,"Sepertinya kamu belum terdiaknosa",Toast.LENGTH_SHORT).show();
                }else {
                    table_users.child(Common.currentUser.getNoHandphone()).child("tanggalDiagnosa").setValue(selectedDate);

                    Intent intent = new Intent(TanggalDiagnosa.this, Alarm.class);
                    startActivity(intent);
                    finish();
                }
            }
        });

    }

    private void showDateDialog(View view) {

        final Calendar newCalendar = Calendar.getInstance();
        pYear = newCalendar.get(Calendar.YEAR);
        pMonth = newCalendar.get(Calendar.MONTH);
        pDay = newCalendar.get(Calendar.DAY_OF_MONTH);

        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {


                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);

                selectedDate = dateFormatter.format(newDate.getTime());
                txt_datePicker.setText(selectedDate);

            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.getDatePicker().setMaxDate(newCalendar.getTimeInMillis());// TODO: used to hide future date,month and year

        datePickerDialog.show();
    }
}
