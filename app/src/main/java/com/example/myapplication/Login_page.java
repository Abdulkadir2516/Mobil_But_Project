package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Login_page extends AppCompatActivity {

    private FirebaseUser MUser;
    private DatabaseReference mReference;
    private EditText user_email,user_password;
    private Button sign_up,log_in;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sign_up = findViewById(R.id.sign_up2);
        log_in = findViewById(R.id.log_in2);
        user_email = findViewById(R.id.userEmail);
        user_password = findViewById(R.id.userPassword);

        sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent signup_page = new Intent(Login_page.this, Signup_page.class);
                startActivity(signup_page);

            }
        });

        log_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                giris(user_email.getText().toString(),user_password.getText().toString());


            }
        });




    }
    public void giris(String user_email, String user_password)
    {

        String mail = user_email;
        String sifre = user_password;

        FirebaseAuth Mauth = FirebaseAuth.getInstance();

        if(!TextUtils.isEmpty(mail) && !TextUtils.isEmpty(sifre))
        {
            Mauth.signInWithEmailAndPassword(mail, sifre).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {

                        Toast.makeText(getBaseContext(),"giriş Başarılı", Toast.LENGTH_SHORT).show();

                        Intent home_page = new Intent(Login_page.this, Home_page.class);
                        startActivity(home_page);

                    } else {
                        Toast.makeText(getBaseContext(),"giriş başarısız", Toast.LENGTH_SHORT).show();
                    }
                }
            });


        }
        else{
            Toast.makeText(this,"Email veya Şifre boş Girilemez", Toast.LENGTH_SHORT).show();
        }
    }

}