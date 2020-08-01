package com.centralcrew.farmaid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    EditText username,password;
    Button submit;
    ImageView register;
    FirebaseAuth fauth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        username=findViewById(R.id.username);
        password=findViewById(R.id.password);
        submit=findViewById(R.id.submit);
        register=findViewById(R.id.register);
        fauth= FirebaseAuth.getInstance();
        if(fauth.getCurrentUser()!=null){
            startActivity(new Intent(MainActivity.this,HomePage.class));
            this.finish();
        }


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,Registration.class);
                startActivity(intent);
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /* startActivity(new Intent(MainActivity.this,HomePage.class));*/
                validate_signin();
                //Toast.makeText(MainActivity.this,"hello",Toast.LENGTH_LONG).show();
            }
        });






    }

    private void validate_signin() {
        String user=username.getText().toString();
        String pass=password.getText().toString();
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
        fauth.signInWithEmailAndPassword(user,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(MainActivity.this, "Login Succesfull", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(MainActivity.this,HomePage.class));
                    finish();
                }
                else{
                    Toast.makeText(MainActivity.this,"Email Id or Password is Wrong", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}

