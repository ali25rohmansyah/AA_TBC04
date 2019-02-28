package com.gregetdev.materitbcapp;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.gregetdev.materitbcapp.Common.Common;
import com.gregetdev.materitbcapp.Model.Question;
import com.gregetdev.materitbcapp.Model.Users;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Locale;

import io.paperdb.Paper;

import static android.text.TextUtils.isEmpty;

public class InputBiodata extends AppCompatActivity {

    EditText edt_nama, edt_tanggalLahir, edt_kota, edt_noHandphone;
    RadioButton jenisTb, gender;
    RadioGroup G_jenisTb, G_gender;
    Button btn_submit;
    DatePickerDialog datePickerDialog;
    private SimpleDateFormat dateFormatter;
    int pYear,  pMonth, pDay;
    FirebaseDatabase database = FirebaseDatabase.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_biodata);

        //Views
        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        edt_nama = findViewById(R.id.edt_nama);
        edt_tanggalLahir = findViewById(R.id.edt_tanggalLahir);
        edt_kota = findViewById(R.id.edt_kota);
        edt_noHandphone = findViewById(R.id.edt_noHandphone);

        G_gender = findViewById(R.id.gender);
        G_jenisTb = findViewById(R.id.jenistb);
        Paper.init(this);

        loadQuestion();

        btn_submit = findViewById(R.id.btn_submit);
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final DatabaseReference table_users = database.getReference("User");

                int id = G_gender.getCheckedRadioButtonId();
                int id2 = G_jenisTb.getCheckedRadioButtonId();

                gender = (RadioButton) findViewById(id);
                jenisTb = (RadioButton) findViewById(id2);

                //Save Biodata
                Paper.book().write(Common.User_Key, edt_noHandphone.getText().toString());

                //Add Bio
                if (isEmpty(edt_kota.getText().toString()) || isEmpty(edt_nama.getText().toString()) || isEmpty(edt_tanggalLahir.getText().toString()) || isEmpty(edt_noHandphone.getText().toString())) {

                    Toast.makeText(InputBiodata.this, "Field tidak boleh kosong", Toast.LENGTH_SHORT).show();

                } else {

                    Users users = new Users(jenisTb.getText().toString(), gender.getText().toString(), edt_kota.getText().toString(),"00-00-0000","00-00-0000", edt_nama.getText().toString(), edt_noHandphone.getText().toString(), "0", "0","00-00-0000", edt_tanggalLahir.getText().toString());
                    table_users.child(edt_noHandphone.getText().toString()).setValue(users);
                    Common.currentUser = users;
                    showDialog();
                }
            }
        });
    }
    public void showDateDialog(View view) {

        final Calendar newCalendar = Calendar.getInstance();
        pYear = newCalendar.get(Calendar.YEAR);
        pMonth = newCalendar.get(Calendar.MONTH);
        pDay = newCalendar.get(Calendar.DAY_OF_MONTH);

        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {


                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);

                edt_tanggalLahir.setText(dateFormatter.format(newDate.getTime()));

            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.getDatePicker().setMaxDate(newCalendar.getTimeInMillis());// TODO: used to hide future date,month and year

        datePickerDialog.show();
    }
    private void showDialog(){
        AlertDialog.Builder al = new AlertDialog.Builder(this);

        al.setTitle("Ayo Pretest!")
                .setMessage("Ayo Ikuti pretest Untuk melihat seberapa jauh Kamu tahu tentang penyakit TB ")
                .setIcon(R.mipmap.ic_launcher)
                .setCancelable(false)
                .setNeutralButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent pretest = new Intent(InputBiodata.this, Pretest.class);
                        startActivity(pretest);
                        finish();

                    }
                });
        AlertDialog alertDialog = al.create();

        alertDialog.show();
    }

    private void loadQuestion() {

        if (Common.questionList.size() > 0) {
            Common.questionList.clear();
        }

        final DatabaseReference table_pertanyaan = database.getReference("Question");
        table_pertanyaan.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {

                    Question ques = postSnapshot.getValue(Question.class);
                    Common.questionList.add(ques);
                }

                Collections.shuffle(Common.questionList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
