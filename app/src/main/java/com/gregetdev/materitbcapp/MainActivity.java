package com.gregetdev.materitbcapp;

import android.content.Intent;
import android.os.Bundle;
import android.renderscript.ScriptGroup;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.gregetdev.materitbcapp.Common.Common;
import com.gregetdev.materitbcapp.Interface.ItemClickListener;
import com.gregetdev.materitbcapp.Model.MateriModel;
import com.gregetdev.materitbcapp.ViewHolder.ListMateriHolder;

import io.paperdb.Paper;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_activity);

        //Init Paper
        Paper.init(this);

        //Check Remember
        String User = Paper.book().read(Common.User_Key);
        if(User !=null){
            startActivity(new Intent(MainActivity.this,Home.class));
        }else{
            startActivity(new Intent(MainActivity.this,InputBiodata.class));
        }
    }
}
