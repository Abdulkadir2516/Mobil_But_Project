package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Firebase;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Map;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Signup_page extends AppCompatActivity {
    private DatabaseReference mReference;
    private FirebaseAuth Mauth;
    private FirebaseUser MUser;
    private  EditText user_name,user_surname,user_email,user_password ;
    private HashMap<String, Object>mData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        Button login = findViewById(R.id.log_in);
        Button sign_up = findViewById(R.id.sign_up);

        user_name = findViewById(R.id.name);
        user_surname = findViewById(R.id.surname);
        user_email = findViewById(R.id.email);
        user_password = findViewById(R.id.password);

        Mauth = FirebaseAuth.getInstance();
        mReference = FirebaseDatabase.getInstance().getReference();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent login_page = new Intent(Signup_page.this, Login_page.class);
                startActivity(login_page);
            }
        });

        sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                kayıt(v);

            }
        });

    }

    public void kayıt(View v)
    {
        String name = user_name.getText().toString();
        String surname = user_surname.getText().toString();
        String mail = user_email.getText().toString();
        String sifre = user_password.getText().toString();

        if(!TextUtils.isEmpty(mail) && !TextUtils.isEmpty(sifre))
        {
            Mauth.createUserWithEmailAndPassword(mail,sifre)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful())
                            {
                                MUser = Mauth.getCurrentUser();

                                mData = new HashMap<>();
                                mData.put("isim", name);
                                mData.put("soyisim", surname);
                                mData.put("mail", mail);
                                mData.put("sifre", sifre);
                                mData.put("Kullanıcı_id", MUser.getUid());


                                mReference.child("Kullanıcılar").child(MUser.getUid())
                                        .setValue(mData).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if(task.isSuccessful())
                                                {
                                                    Toast.makeText(getBaseContext(), "Kayıt Başarılı", Toast.LENGTH_SHORT).show();

                                                    Intent home_page = new Intent(Signup_page.this, Home_page.class);
                                                    startActivity(home_page);
                                                }
                                                else
                                                {
                                                    Toast.makeText(getBaseContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                                                }
                                            }
                                        });

                            }
                            else
                            {
                                Toast.makeText(getBaseContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
        }
        else{
            Toast.makeText(this,"Email veya Şifre boş Girilemez", Toast.LENGTH_SHORT).show();
        }
    }

    public static String md5(String input) {
        try {
            // MD5 için MessageDigest nesnesi oluşturun
            MessageDigest md = MessageDigest.getInstance("MD5");

            // String'i byte dizisine çevirin ve MD5 hash'ini hesaplayın
            byte[] messageDigest = md.digest(input.getBytes());

            // Byte dizisini hexadecimal formatına çevirin
            BigInteger no = new BigInteger(1, messageDigest);

            // Hash değerini hexadecimal olarak alın
            String hashtext = no.toString(16);

            // Eksik başlangıç sıfırlarını ekle
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }

            return hashtext;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }



}