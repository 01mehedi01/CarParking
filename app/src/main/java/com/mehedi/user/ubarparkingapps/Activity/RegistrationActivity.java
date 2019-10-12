package com.mehedi.user.ubarparkingapps.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.mehedi.user.ubarparkingapps.R;

public class RegistrationActivity extends AppCompatActivity {
    private MainActivity mainActivity;
    private EditText emailEdittext;
    private EditText passwordEdittext;
    private EditText repasswordEdittext;
    private TextInputLayout emailtextinput;
    private TextInputLayout passwordtextinput;
    private TextInputLayout repasswordtextinput;
    private ProgressDialog progressDialog;


    private FirebaseAuth mAuth;
    private SharedPreferences sharedPreferences;
    private  SharedPreferences.Editor editor;
    private boolean Contaiatologin ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);


        Toolbar toolbar = findViewById(R.id.reg_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegistrationActivity.super.onBackPressed();
            }
        });

        initilizeView();
    }

    private void initilizeView() {
        mainActivity = new MainActivity();
        mAuth = FirebaseAuth.getInstance();
        //getverificationCodeButton = v.findViewById(R.id.getvarification);


//        emailEdittext = findViewById(R.id.emailreg);
//        passwordEdittext = findViewById(R.id.passwordreg);
//        repasswordEdittext = findViewById(R.id.repasswordreg); emailEdittext = findViewById(R.id.emailreg);
//        passwordEdittext = findViewById(R.id.passwordreg);
        emailtextinput = findViewById(R.id.textinputreemail);
        passwordtextinput = findViewById(R.id.textinputpassword);
        repasswordtextinput = findViewById(R.id.textinputrepassword);

        sharedPreferences = getSharedPreferences("alreadylogin",MODE_PRIVATE);
        editor = sharedPreferences.edit();

        //Contaiatologin = sharedPreferences.getBoolean("autologin", false);
    }

    public void Registration(View view) {
        String Email = emailtextinput.getEditText().getText().toString().trim();
        String Password = passwordtextinput.getEditText().getText().toString().trim();
        String Re_password = repasswordtextinput.getEditText().getText().toString().trim();

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading data......");
        progressDialog.show();

        if(Email.isEmpty()){
            emailEdittext.setError("Phone Number is Requird");
            emailEdittext.requestFocus();
            return;
        }
        if(Password.isEmpty() ){
            passwordEdittext.setError("Phone Number is Requird");
            passwordEdittext.requestFocus();
            return;
        }
        if(Re_password.isEmpty() ){
            repasswordEdittext.setError("Phone Number is Requird");
            repasswordEdittext.requestFocus();
            return;
        }
        if(Password.length() <6){
            passwordEdittext.setError("Please Enter at least 6 digit");
            passwordEdittext.requestFocus();
            return;
        }

        if(Password.equals(Re_password)){
            mAuth.createUserWithEmailAndPassword(Email,Password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            try {

                                if (task.isSuccessful()) {
                                    progressDialog.dismiss();
//                                    Contaiatologin = true;
//                                    editor.clear();
//                                    editor.commit();
//                                    editor.putBoolean("autologin", Contaiatologin);
//                                    editor.commit();
                                    Toast.makeText(getApplicationContext(), "success", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                } else {

                                }
                            }catch (Exception e){

//                                editor.commit();
//                                editor.putBoolean("autologin", Contaiatologin);
//                                editor.commit();
                                Toast.makeText(getApplicationContext(), "success", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                            }
                        }
                    });
        }else{
            Toast.makeText(this, "Not match", Toast.LENGTH_SHORT).show();
        }

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        if (id == R.id.action_Logout) {

            FirebaseAuth.getInstance().signOut();
            editor.clear();
            editor.commit();
           // startActivity(new Intent(getApplicationContext(),MainActivity.class));
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            // intent.putExtra("finish", true);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        }
        if (id == R.id.action_Exit) {
            moveTaskToBack(true);
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(1);
        }
        if (id == R.id.home) {
            Intent intent = new Intent(getApplicationContext(), SearchActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
