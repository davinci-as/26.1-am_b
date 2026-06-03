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

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignupActivity extends AppCompatActivity {

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();

        if(mAuth.getCurrentUser() != null) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }

        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_signup);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private boolean checkEmail (String email) {
        //check por regex
        //verificar manualmente
        return true;
    }

    private boolean checkPassword (String email) {
        //check por regex
        //verificar manualmente
        return true;
    }

    private boolean samePassword (String email, String emailVerification) {
        return true;
    }

    public void createUser(View v) {
        String signupEmail = ((TextView) findViewById(R.id.signupEmail)).getText().toString();
        String signupPassword = ((TextView) findViewById(R.id.signupPassword)).getText().toString();
        String signupPasswordVerification = ((TextView) findViewById(R.id.signupPasswordVerification)).getText().toString();

        if(!checkEmail(signupEmail)) {
            //Modal con mensaje
            return;
        };

        if(!checkPassword(signupEmail)) {
            //Modal con mensaje
            return;
        };

        if(!checkPassword(signupPasswordVerification)) {
            //Modal con mensaje
            return;
        };

        if(!samePassword(signupPassword, signupPasswordVerification)) {
            //Modal con mensaje
            return;
        };


        mAuth.createUserWithEmailAndPassword(signupEmail, signupPassword).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                FirebaseUser user = authResult.getUser();
                if(user == null) return;
                Log.i("create-user", user.getUid());
                //user.sendEmailVerification();
            }
        });
    }

}