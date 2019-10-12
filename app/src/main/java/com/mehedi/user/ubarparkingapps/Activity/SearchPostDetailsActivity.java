package com.mehedi.user.ubarparkingapps.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.mehedi.user.ubarparkingapps.PostWrite_Read.Pojoclass;
import com.mehedi.user.ubarparkingapps.PostWrite_Read.PostReadAdaptar;
import com.mehedi.user.ubarparkingapps.R;

public class SearchPostDetailsActivity extends AppCompatActivity {
    private TextView locationtextview;
    private TextView slottypetextview;
    private TextView perhourtextview;
    private TextView nametextview;
    private TextView Addresstextview;
    private TextView homenotextview;
    private TextView availableslottextview;
    private Query query;
    private String PostCreatorUserID;
    private String PostcretorUsrIDfronshardprefarance;
    public static final String VIEW_NAME_HEADER_TITLE = "detail:header:title";

    private SharedPreferences sharedPreferences;
    private  SharedPreferences.Editor editor;

    public SharedPreferences PostcretorIDsharedPreferences;
    public  SharedPreferences.Editor PostcretorIDeditor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_post_details);



        if (getIntent().getExtras() != null) {
            Bundle p = getIntent().getExtras();
            PostCreatorUserID = p.getString("id");
        }
       // Toast.makeText(this, ""+PostCreatorUserID, Toast.LENGTH_SHORT).show();



        Toolbar toolbar = findViewById(R.id.searcedetails_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchPostDetailsActivity.super.onBackPressed();
            }
        });
        initilizeView();

        query = FirebaseDatabase.getInstance().getReference("parking").orderByChild("postcretoruserID").equalTo(PostCreatorUserID);;
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                    Pojoclass pojoclass = dataSnapshot1.getValue(Pojoclass.class);
                    initilizeUI(pojoclass);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(SearchPostDetailsActivity.this, "opps Something ...wrong", Toast.LENGTH_SHORT).show();
            }
        });
        PostcretorIDsharedPreferences = this.getSharedPreferences("postcretorid", Context.MODE_PRIVATE);
        PostcretorIDeditor = PostcretorIDsharedPreferences.edit();

        PostcretorUsrIDfronshardprefarance = PostcretorIDsharedPreferences.getString("postcretorid","");
       if(PostcretorUsrIDfronshardprefarance.isEmpty()) {
           PostcretorIDeditor.putString("postcretorid", PostCreatorUserID);
           PostcretorIDeditor.commit();
       }else{
           PostcretorIDeditor.clear();
           PostcretorIDeditor.commit();
           PostcretorIDeditor.putString("postcretorid", PostCreatorUserID);
           PostcretorIDeditor.commit();
       }

        ViewCompat.setTransitionName(locationtextview, VIEW_NAME_HEADER_TITLE);
    }

    private void initilizeUI(Pojoclass pojoclass) {
        locationtextview.setText(pojoclass.getLocation());
        slottypetextview.setText("Type : "+pojoclass.getSloatType());
        perhourtextview.setText("PerHour : "+String.valueOf(pojoclass.getPerhourCost())+" Taka") ;
        nametextview.setText("Owner Name\n"+pojoclass.getName());
        Addresstextview.setText(pojoclass.getAddress());
        homenotextview.setText(pojoclass.getHouseNo());
        availableslottextview.setText("Available Slot "+pojoclass.getSloat());



    }

    private void initilizeView() {

        locationtextview = findViewById(R.id.locationname);
        slottypetextview = findViewById(R.id.slottype);
        perhourtextview = findViewById(R.id.perhour);
        nametextview = findViewById(R.id.ownername);
        Addresstextview = findViewById(R.id.address);
        homenotextview = findViewById(R.id.homeno);
        availableslottextview = findViewById(R.id.availableslot);


        sharedPreferences = getSharedPreferences("alreadylogin",MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }


    public void BokingSection(View view) {

        startActivity(new Intent(getApplicationContext(),BokingActivity.class));
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
            //startActivity(new Intent(getApplicationContext(),SearchActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
}
