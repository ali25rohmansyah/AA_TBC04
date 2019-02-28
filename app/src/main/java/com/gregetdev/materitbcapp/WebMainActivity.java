package com.gregetdev.materitbcapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class WebMainActivity extends AppCompatActivity {

    WebView webView;
    FirebaseDatabase database;
    DatabaseReference table_materi;
    TextView Judul;
    ImageButton backIMG;
    String weblink;
    String materiID = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_main);

        //get materiID from intent
        if(getIntent() != null){
            materiID = getIntent().getStringExtra("materiID");
        }


        database = FirebaseDatabase.getInstance();
        table_materi = database.getReference("Materi");
        backIMG = findViewById(R.id.backImage);


        webView = findViewById(R.id.WebView);





        webView.setWebViewClient(new WebViewClient());
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);



        webView.loadData(loadLink(),"text/html", "utf-8");

    }

    private String loadLink() {

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;


        String weblink = "<iframe "
                +"width=\"100%\" height=\""+width+"\" "
                +"src=\"https://www.youtube.com/embed/5gQYzPlzVbw\" "
                +"frameborder=\"0\" allow=\"accelerometer; autoplay; encrypted-media; gyroscope; "
                +"picture-in-picture\" allowfullscreen=\"true\"></iframe>";

        return weblink;
    }


}
