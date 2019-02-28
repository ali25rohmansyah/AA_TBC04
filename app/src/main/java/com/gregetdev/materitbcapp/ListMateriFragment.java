package com.gregetdev.materitbcapp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.gregetdev.materitbcapp.Common.Common;
import com.gregetdev.materitbcapp.Interface.ItemClickListener;
import com.gregetdev.materitbcapp.Model.MateriModel;
import com.gregetdev.materitbcapp.Model.Question;
import com.gregetdev.materitbcapp.ViewHolder.ListMateriHolder;

import java.util.Collections;

public class ListMateriFragment extends Fragment {

    FirebaseDatabase database;
    DatabaseReference materi;
    Button btn_posttest;

    RecyclerView recycler_materi;
    RecyclerView.LayoutManager layoutManager;
    FirebaseRecyclerAdapter<MateriModel,ListMateriHolder> adapter;
    ProgressBar progressBar;


    Boolean[] readStatus;



    public ListMateriFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list_materi, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        database = FirebaseDatabase.getInstance();
        materi = database.getReference("Materi");
        progressBar = view.findViewById(R.id.progressBar);

        loadQuestion();

        btn_posttest = view.findViewById(R.id.btn_posttest);
        btn_posttest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), PostTest.class));
            }
        });

        //load List Materi
        recycler_materi = view.findViewById(R.id.Recycle_materi);
        recycler_materi.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this.getActivity());
        recycler_materi.setLayoutManager(layoutManager);
        loadMateri();
    }

    private void loadMateri() {

        adapter = new FirebaseRecyclerAdapter<MateriModel, ListMateriHolder>
                (MateriModel.class, R.layout.materi_cardview,ListMateriHolder.class,materi) {
            @Override
            protected void populateViewHolder(ListMateriHolder viewHolder, final MateriModel model, int position) {


                viewHolder.TxtJudul.setText(model.getJudul());
                final MateriModel clickItem = model;
                final Intent intent1 = new Intent(ListMateriFragment.this.getActivity(),HalamanMateri.class);
                final Intent intent2 = new Intent(ListMateriFragment.this.getActivity(),WebMainActivity.class);

                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {

                        if(clickItem.getType().equals("text") || clickItem.getType().equals("picture")){
                            intent1.putExtra("materiID",adapter.getRef(position).getKey());
                            startActivity(intent1);

                        }
                        else if(clickItem.getType().equals("video")){
                            startActivity(intent2);
                        }
                    }
                });
            }
        };

        recycler_materi.setAdapter(adapter);

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
