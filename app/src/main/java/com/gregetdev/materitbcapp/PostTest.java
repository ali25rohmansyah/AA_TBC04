package com.gregetdev.materitbcapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.gregetdev.materitbcapp.Common.Common;

import io.paperdb.Paper;

public class PostTest extends AppCompatActivity implements View.OnClickListener {

    int progressValue = 0;
    int index = 0, score = 0, correctAnswer, thisQuestion = 0, totalQuestion;

    ProgressBar progressBar;
    Button btn_jawabanA, btn_jawabanB, btn_jawabanC;
    TextView pertanyaan_text, txt_score, txt_totalQuestion;

    final FirebaseDatabase database = FirebaseDatabase.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_test);

        //Casting
        txt_score = findViewById(R.id.txt_score);
        txt_totalQuestion = findViewById(R.id.txt_totalQuestion);
        btn_jawabanA = findViewById(R.id.btn_jawabanA);
        btn_jawabanB = findViewById(R.id.btn_jawabanB);
        btn_jawabanC = findViewById(R.id.btn_jawabanC);

        progressBar = findViewById(R.id.progressBar);

        pertanyaan_text = findViewById(R.id.pertanyaan_text);

        Paper.init(this);

        btn_jawabanA.setOnClickListener(this);
        btn_jawabanB.setOnClickListener(this);
        btn_jawabanC.setOnClickListener(this);
        //End Casting
    }

    @Override
    public void onClick(View view) {

        Button clickedButten = (Button) view;
        if (clickedButten.getText().equals(Common.questionList.get(index).getJawabanBenar())) {

            //jawaban benar
            score += 10;
            correctAnswer++;
            showQuestion(++index); //jawaban selanjutnya
        } else {

            //jawaban salah
            score += 0;
            correctAnswer += 0;
            showQuestion(++index); //jawaban selanjutnya
        }
        txt_score.setText(String.format("%d", score));

    }

    private void showQuestion(int index) {
        if (index < totalQuestion) {
            thisQuestion++;
            txt_totalQuestion.setText(String.format("%d / %d", thisQuestion, totalQuestion));
            progressValue++;
            progressBar.setProgress(progressValue);

            pertanyaan_text.setText(Common.questionList.get(index).getPertanyaan());

            btn_jawabanA.setText(Common.questionList.get(index).getJawabanA());
            btn_jawabanB.setText(Common.questionList.get(index).getJawabanB());
            btn_jawabanC.setText(Common.questionList.get(index).getJawabanC());

        } else {

            String User = Paper.book().read(Common.User_Key);
            DatabaseReference table_user = database.getReference("User");
            table_user.child(User).child("postTest").setValue(String.valueOf(score));

            String Score_Bagus = "Pengetahuan Anda Luar Biasa!";
            String Score_KurangBagus = "Pengetahuan Anda Kurang";

            AlertDialog.Builder al = new AlertDialog.Builder(PostTest.this);

            if(score > 60) {
                al.setTitle(Score_Bagus)
                        .setMessage("Score Anda : " + score)
                        .setIcon(R.mipmap.ic_launcher)
                        .setCancelable
                                (false)
                        .setNeutralButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent pretest = new Intent(PostTest.this, Home.class);
                                startActivity(pretest);
                                finish();
                            }
                        });
            } else if (score < 60) {
                al.setTitle(Score_KurangBagus)
                        .setMessage("Score Anda : " + score)
                        .setIcon(R.mipmap.ic_launcher)
                        .setCancelable
                                (false)
                        .setNeutralButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent pretest = new Intent(PostTest.this, Home.class);
                                startActivity(pretest);
                                finish();
                            }
                        });
            }
            AlertDialog alertDialog = al.create();

            alertDialog.show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        totalQuestion = Common.questionList.size();
        progressBar.setMax(totalQuestion);

        showQuestion(index);

    }

}
