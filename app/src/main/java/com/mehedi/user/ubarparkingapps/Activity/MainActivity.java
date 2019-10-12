package com.mehedi.user.ubarparkingapps.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.mehedi.user.ubarparkingapps.R;

public class MainActivity extends AppCompatActivity {



    private FrameLayout frameLayout;
    private RelativeLayout relativeLayout;
    private AnimationDrawable animationDrawable;
    private EditText emailEdittext;
    private EditText passwordEdittext;
    private EditText repasswordEdittext;

    public SharedPreferences sharedPreferences;
    public  SharedPreferences.Editor editor;
    private boolean Contaiatologin ;
    private FirebaseAuth  mAuth;
    private String Codessent;
    private LinearLayout linearLayout;
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        initilizeView();
        mAuth = FirebaseAuth.getInstance();

        Contaiatologin = sharedPreferences.getBoolean("autologin",false);
        if(Contaiatologin){
            Toast.makeText(this, "Already Loged in", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(MainActivity.this,SearchActivity.class));
        }else{
            Toast.makeText(this, "Please Sign In", Toast.LENGTH_SHORT).show();
           //startActivity(new Intent(MainActivity.this,SearchActivity.class));
        }
    }

    private void initilizeView() {

        sharedPreferences = getSharedPreferences("alreadylogin",MODE_PRIVATE);
        editor = sharedPreferences.edit();



        emailEdittext = findViewById(R.id.email);
       // linearLayout = findViewById(R.id.liniarlayoutmain);
        passwordEdittext=findViewById(R.id.password);


//        AnimationDrawable animationDrawable = (AnimationDrawable) linearLayout.getBackground();
//        animationDrawable.setEnterFadeDuration(4500);
//        animationDrawable.setExitFadeDuration(4500);
//        animationDrawable.start();


    }





    //****************************** Sign IN Method *******************************************************
    public void SignIn(View view) {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Login.....");
        progressDialog.show();
        VerificatoinSignin();

    }

    private void VerificatoinSignin() {

        String Email = emailEdittext.getText().toString();
        String Password = passwordEdittext.getText().toString();


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

      mAuth.signInWithEmailAndPassword(Email,Password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
          @Override
          public void onComplete(@NonNull Task<AuthResult> task) {
              if(task.isSuccessful()){

                  Contaiatologin = true;
                  editor.clear();
                  editor.commit();
                  editor.putBoolean("autologin",Contaiatologin);
                  editor.commit();
                  progressDialog.dismiss();
                  startActivity(new Intent(getApplicationContext(),SearchActivity.class));
                 // Toast.makeText(MainActivity.this, "Login", Toast.LENGTH_SHORT).show();
              }

          }
      });





    }

    public void Registration(View view) {

          startActivity(new Intent(getApplicationContext(),RegistrationActivity.class));
    }


//    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
//        @Override
//        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
//            Toast.makeText(MainActivity.this, "onVerificationCompleted", Toast.LENGTH_SHORT).show();
//        }
//
//        @Override
//        public void onVerificationFailed(FirebaseException e) {
//
//        }
//
//        @Override
//        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
//            super.onCodeSent(s, forceResendingToken);
//            Codessent = s;
//            Toast.makeText(MainActivity.this, "Code sent to the phone     "+Codessent, Toast.LENGTH_SHORT).show();
//        }
//    };
//    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
//        mAuth.signInWithCredential(credential)
//                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        if (task.isSuccessful()) {
//
//                            Toast.makeText(MainActivity.this, " login success", Toast.LENGTH_SHORT).show();
//                            FirebaseUser user = task.getResult().getUser();
//                            // ...
//                        } else {
//                            // Sign in failed, display a message and update the UI
//                            //Log.w(TAG, "signInWithCredential:failure", task.getException());
//                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
//                                // The verification code entered was invalid
//                                Toast.makeText(MainActivity.this, "The verification code entered was invalid", Toast.LENGTH_SHORT).show();
//                            }
//                        }
//                    }
//                });
//    }

}
