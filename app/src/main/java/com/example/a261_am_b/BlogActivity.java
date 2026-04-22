package com.example.a261_am_b;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class BlogActivity extends AppCompatActivity {
    public void sendMessage(View view) {
        Log.i("main-log", "sendMessage fue ejecutado");
        TextView loginMessage = findViewById(R.id.blog_message);
        loginMessage.setText(R.string.login_text_alternative);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_blog);

        Intent intent = getIntent();
        if(intent.hasExtra(getString(R.string.blog_id))) {
            int blogId = intent.getIntExtra(getString(R.string.blog_id), 0);
            Log.i("main-log", String.format("El id del blog es: %d", blogId));
        } else {
            Log.i("main-log", "No se envió extra");
        }


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}