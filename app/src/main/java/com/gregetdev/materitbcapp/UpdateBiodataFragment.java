package com.gregetdev.materitbcapp;


import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.gregetdev.materitbcapp.Model.Users;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import io.paperdb.Paper;

import static android.text.TextUtils.isEmpty;


/**
 * A simple {@link Fragment} subclass.
 */
public class UpdateBiodataFragment extends Fragment {

    EditText edt_nama, edt_tanggalLahir, edt_kota, edt_noHandphone;
    RadioButton jenisTb, gender;
    RadioGroup G_jenisTb, G_gender;
    Button btn_submit;
    DatePickerDialog datePickerDialog;
    private SimpleDateFormat dateFormatter;
    int pYear,  pMonth, pDay;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    final DatabaseReference table_users = database.getReference("User");

    public UpdateBiodataFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_update_biodata, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        //Views
        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        edt_nama = view.findViewById(R.id.edt_nama);
        edt_tanggalLahir = view.findViewById(R.id.edt_tanggalLahir);
        edt_kota = view.findViewById(R.id.edt_kota);
        edt_noHandphone = view.findViewById(R.id.edt_noHandphone);

        G_gender = view.findViewById(R.id.gender);
        G_jenisTb = view.findViewById(R.id.jenistb);
        int id = G_gender.getCheckedRadioButtonId();
        int id2 = G_jenisTb.getCheckedRadioButtonId();
        gender = view.findViewById(id);
        jenisTb = view.findViewById(id2);

        Paper.init(getActivity());

        table_users.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String User = Paper.book().read(Common.User_Key);
                Users user = dataSnapshot.child(User).getValue(Users.class);
                edt_nama.setText(user.getNama());
                edt_kota.setText(user.getKota());
                edt_tanggalLahir.setText(user.getTanggalLahir());
                edt_noHandphone.setText(user.getNoHandphone());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        edt_tanggalLahir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar newCalendar = Calendar.getInstance();
                pYear = newCalendar.get(Calendar.YEAR);
                pMonth = newCalendar.get(Calendar.MONTH);
                pDay = newCalendar.get(Calendar.DAY_OF_MONTH);

                datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
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
        });
        btn_submit = view.findViewById(R.id.btn_submit);
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Add Bio
                if (isEmpty(edt_kota.getText().toString()) || isEmpty(edt_nama.getText().toString()) || isEmpty(edt_tanggalLahir.getText().toString()) || isEmpty(edt_noHandphone.getText().toString())) {

                    Toast.makeText(getActivity(), "Field tidak boleh kosong", Toast.LENGTH_SHORT).show();

                } else {

                    table_users.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            table_users.child(edt_noHandphone.getText().toString()).child("nama").setValue(edt_nama.getText().toString());
                            table_users.child(edt_noHandphone.getText().toString()).child("kota").setValue(edt_kota.getText().toString());
                            table_users.child(edt_noHandphone.getText().toString()).child("tanggalLahir").child(edt_tanggalLahir.getText().toString());
//                            table_users.child(edt_noHandphone.getText().toString()).child("jkel").child(gender.getText().toString());
//                            table_users.child(edt_noHandphone.getText().toString()).child("jenisTb").child(jenisTb.getText().toString());
                            table_users.child(edt_noHandphone.getText().toString()).child("noHandphone").setValue(edt_noHandphone.getText().toString());
                            Toast.makeText(getActivity(), "Update Berhasil", Toast.LENGTH_SHORT).show();

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                }
            }
        });
    }
}
