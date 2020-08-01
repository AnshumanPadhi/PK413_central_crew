package com.centralcrew.farmaid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Registration extends AppCompatActivity {
    EditText name,phone,username,password;
    ImageView register,login;
    FirebaseAuth fauth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        name=findViewById(R.id.name);
        phone=findViewById(R.id.phone);
        login=findViewById(R.id.login);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Registration.this,MainActivity.class));
            }
        });

        username=findViewById(R.id.username);
        password=findViewById(R.id.password);
        register=findViewById(R.id.register);
        fauth=FirebaseAuth.getInstance();

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });

    }

    private void registerUser() {
        String n=name.getText().toString();
        String user=username.getText().toString();
        String pass=password.getText().toString();
        String phoneno=phone.getText().toString();
        if(n.isEmpty()){
            name.setError("Can't be Empty");
            return;
        }
        if(user.isEmpty()){
            username.setError("Can't be Empty");
            return;
        }

        if(pass.isEmpty()){
            password.setError("Can't be Empty");
            return;
        }
        if(pass.length()<6){
            password.setError("Must be of 6 digits");
            return;
        }


        if(phoneno.length()<10 || phoneno.length()>10)
        {
            phone.setError("Must be of 10 Digits");
            return;
        }

        fauth.createUserWithEmailAndPassword(user,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(Registration.this, "User Created", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(Registration.this,HomePage.class));
                    finish();
                }
                else{
                    Toast.makeText(Registration.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
