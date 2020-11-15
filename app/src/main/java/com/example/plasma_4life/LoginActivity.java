package com.example.plasma_4life;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private EditText edit_name;
    private EditText edit_password;
    private Button login_button;
    private FirebaseAuth mAuth;
    private TextView register;
    ProgressDialog pd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        edit_name=(EditText)findViewById(R.id.etName);
        edit_password=(EditText)findViewById(R.id.etPassword);
        login_button=(Button)findViewById(R.id.loginB);
        pd=new ProgressDialog(this);
        register=(TextView)findViewById(R.id.tvRegister);
        mAuth=FirebaseAuth.getInstance();
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,RegistrationActivity.class));
            }
        });
        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name=edit_name.getText().toString();
                String password=edit_password.getText().toString();
                if(name.isEmpty()||password.isEmpty())
                {
                    Toast.makeText(LoginActivity.this,"Field is Empty",Toast.LENGTH_SHORT).show();
                    edit_name.setError("Email Required");
                    edit_name.requestFocus();
                    return;
                }
                else
                {
                 userLogin(edit_name.getText().toString(),edit_password.getText().toString());
                }
            }
        });
    }
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser!=null)
        {
            Intent intent=new Intent(LoginActivity.this,HomeActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); //to avoid going to same login page
            startActivity(intent);
        }
    }
    private void userLogin(String mail,String pass)
    {
        pd.setMessage("Please wait");
        pd.show();
        mAuth.signInWithEmailAndPassword(mail,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                pd.dismiss();
                if(task.isSuccessful())
                {
                 Intent intent=new Intent(LoginActivity.this,HomeActivity.class);
                 intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); //to avoid going to same login page
                    startActivity(intent);
                }
                else{
                    Toast.makeText(getApplicationContext(),task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}