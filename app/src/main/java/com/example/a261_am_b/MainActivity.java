package com.example.a261_am_b;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class MainActivity extends AppCompatActivity {
    public static final int ACTIVITY_OPTION = 1;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode != ACTIVITY_OPTION) return;
        if(resultCode != RESULT_OK) return;
        if(!data.hasExtra("blog-text")) return;
        Bundle pack = data.getExtras();
        String blogPost = data.getStringExtra("blog-text");
        Log.i("main-log", blogPost);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        FirebaseUser currentUser = mAuth.getCurrentUser();

        if(currentUser != null) {
            String uid = currentUser.getUid();
            Log.i("Firebase login", uid);
            CollectionReference usersRef = db.collection("users");
            usersRef.whereEqualTo("uid", uid).limit(1);

            usersRef.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                @Override
                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                    if(queryDocumentSnapshots.getDocuments().isEmpty()) {
                        //Modal, o mensaje para el usuario
                        return;
                    }
                    DocumentSnapshot document = queryDocumentSnapshots.getDocuments().get(0);
                    //document.getId(); //Si me interesa
                    if(document == null) return;
                    String fullName = document.getData().get("fullName").toString();
                    Log.i("firebase-data", fullName);
                    TextView loginMessage = findViewById(R.id.login_message);

                    loginMessage.setText(fullName);

                }
            });

        } else {
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
        }


        //TODO: download image
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if(networkInfo != null && networkInfo.isConnected()) {
            new ImageDownload().execute(getString(R.string.download_image));
        } else {
            Log.i("main-log", "no se pudo descargar");
        }

        Button viewImage = new Button(this);
        viewImage.setText(R.string.login_cta_text);
        viewImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("main-log", "viewImage.onClick fue ejecutado");
                Intent intent = new Intent(getApplicationContext(), BlogActivity.class);
                final int BLOG_ID = 1;
                intent.putExtra(getString(R.string.blog_id), BLOG_ID);
                startActivityForResult(intent, ACTIVITY_OPTION);
            }
        });

        LinearLayout loginContainer = findViewById(R.id.login_container);
        loginContainer.addView(viewImage);

        (new ApiRequest()).execute(getString(R.string.API_ALL_CHARACTERS));

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }


    public void logoutAccount (View v) {
        mAuth.signOut();
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}