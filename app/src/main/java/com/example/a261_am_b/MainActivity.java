package com.example.a261_am_b;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        Button viewImage = new Button(this);
        viewImage.setText(R.string.login_cta_text);
        viewImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("main-log", "viewImage.onClick fue ejecutado");
                Intent intent = new Intent(getApplicationContext(), BlogActivity.class);
                final int BLOG_ID = 1;
                intent.putExtra(getString(R.string.blog_id), BLOG_ID);
                startActivity(intent);
            }
        });

        LinearLayout loginContainer = findViewById(R.id.login_container);
        loginContainer.addView(viewImage);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}