package com.mehedi.user.ubarparkingapps.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
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

import com.firebase.client.Config;
import com.firebase.client.Firebase;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mehedi.user.ubarparkingapps.PostWrite_Read.Pojoclass;
import com.mehedi.user.ubarparkingapps.R;

public class CreatePostActivity extends AppCompatActivity {



     private TextInputLayout NameEdittext,LocEdittext,AddEdittext,SlottypeEdittext,SlotEdittext,perhourCostEdittext,houseEdittext;
     private DatabaseReference databaseReference;
     private Pojoclass pojoclass = new Pojoclass();
     private FirebaseAuth mAuth;
     private FirebaseUser user;
    public SharedPreferences sharedPreferences;
    public  SharedPreferences.Editor editor;
    private MainActivity mainActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_post);


        Toolbar toolbar = findViewById(R.id.reg_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreatePostActivity.super.onBackPressed();
            }
        });
        initilizeview();
    }

    private void initilizeview() {
        MobileAds.initialize(this,"ca-app-pub-3940256099942544~3347511713");
        AdRequest adRequest = new AdRequest.Builder().build();
        NameEdittext = findViewById(R.id.namecreator);
        LocEdittext = findViewById(R.id.loccreator);
        AddEdittext = findViewById(R.id.addcreator);
        SlotEdittext = findViewById(R.id.slotcreator);
        SlottypeEdittext = findViewById(R.id.slottypecreator);
        perhourCostEdittext = findViewById(R.id.perhour);
        houseEdittext = findViewById(R.id.houseno);
        mAuth = FirebaseAuth.getInstance();
        mainActivity = new MainActivity();
        sharedPreferences = getSharedPreferences("alreadylogin",MODE_PRIVATE);
        editor = sharedPreferences.edit();
        databaseReference = FirebaseDatabase.getInstance().getReference("parking");
    }

    public void SaveCreatePost(View view) {

        user = mAuth.getCurrentUser();
        String Name = NameEdittext.getEditText().getText().toString().trim();
        String location = LocEdittext.getEditText().getText().toString().trim();
        String address = AddEdittext.getEditText().getText().toString().trim();
        String slot = SlotEdittext.getEditText().getText().toString().trim();
        String slotype = SlottypeEdittext.getEditText().getText().toString().trim();
        String postuID = user.getUid();
        String houseNo = houseEdittext.getEditText().getText().toString().trim();


        if(Name.isEmpty()){
            NameEdittext.setError("Name is Requird");
            NameEdittext.requestFocus();
            return;
        }
        if(location.isEmpty() ){
            LocEdittext.setError("location is Requird");
            LocEdittext.requestFocus();
            return;
        }
        if(slotype.isEmpty() ){
            SlottypeEdittext.setError("slotype is Requird");
            SlottypeEdittext.requestFocus();
            return;
        }
        if(address.isEmpty() ){
            AddEdittext.setError("address is Requird");
            AddEdittext.requestFocus();
            return;
        }
        if(slot.isEmpty() ){
            SlotEdittext.setError("slot is Requird");
            SlotEdittext.requestFocus();
            return;
        }
//        if(perhourcost == 0  ){
//            perhourCostEdittext.setError("perhourcost is Requird");
//            perhourCostEdittext.requestFocus();
//            return;
//        }

        if(!(Name.isEmpty()) && !(location.isEmpty()) && !(address.isEmpty()) && !(slotype.isEmpty()) && !(slot.isEmpty())){

            int perhourcost = Integer.parseInt(perhourCostEdittext.getEditText().getText().toString().trim());
            pojoclass = new Pojoclass(Name,location,address,slot,postuID,slotype,perhourcost,houseNo,false);

            databaseReference.child(user.getUid()).setValue(pojoclass);


//
//
//
// String userId = user.getUid();
//            Firebase rootRef = new Firebase(Config.U);
//            Firebase userRef = rootRef.child("Users");
//
//            userRef.child(userId).setValue(pojoclass);


            Toast.makeText(this, "Save Successfullll", Toast.LENGTH_SHORT).show();

            NameEdittext.getEditText().setText("");
            LocEdittext.getEditText().setText("");
            SlottypeEdittext.getEditText().setText("");
            AddEdittext.getEditText().setText("");
            SlotEdittext.getEditText().setText("");
            perhourCostEdittext.getEditText().setText("");
            houseEdittext.getEditText().setText("");

            startActivity(new Intent(CreatePostActivity.this,CreatePostDetails.class));
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
            //startActivity(new Intent(getApplicationContext(),MainActivity.class));
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
