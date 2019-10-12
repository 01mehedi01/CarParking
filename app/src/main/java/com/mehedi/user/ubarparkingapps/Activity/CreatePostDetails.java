package com.mehedi.user.ubarparkingapps.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.mehedi.user.ubarparkingapps.PostWrite_Read.Pojoclass;
import com.mehedi.user.ubarparkingapps.R;

public class CreatePostDetails extends AppCompatActivity {
    private TextView Name,location,address,house,slotTypew,availableslot,perhour;
    private Query query;
    private DatabaseReference databaseReference;
    private DatabaseReference bokingdatabaseRF;
    private Pojoclass pojoclass = new Pojoclass();
    private FirebaseAuth mAuth;
    private FirebaseUser user;

    private SharedPreferences sharedPreferences;
    private  SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_post_details);


        Toolbar toolbar = findViewById(R.id.det_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),SearchActivity.class));
            }
        });

        initilizeView();





    }


    private void initilizeUI(Pojoclass pojoclass) {

        location.setText(pojoclass.getLocation());
        perhour.setText("PerHour : "+String.valueOf(pojoclass.getPerhourCost())+" Taka") ;
        Name.setText(pojoclass.getName());
        address.setText(pojoclass.getAddress());
        house.setText(pojoclass.getHouseNo());
        availableslot.setText("Available Slot "+pojoclass.getSloat());
    }

    private void initilizeView() {

        Name = findViewById(R.id.ownername);
        location = findViewById(R.id.ownerlocation);
        address = findViewById(R.id.owneraddress);
        house = findViewById(R.id.ownerhouseno);
        availableslot = findViewById(R.id.owneravailableslot);
        perhour = findViewById(R.id.ownerperhour);


        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("parking");
        bokingdatabaseRF = FirebaseDatabase.getInstance().getReference("relation");
        user = mAuth.getCurrentUser();

        sharedPreferences = getSharedPreferences("alreadylogin",MODE_PRIVATE);
        editor = sharedPreferences.edit();

    }

    @Override
    protected void onStart() {
        super.onStart();

        query =  FirebaseDatabase.getInstance().getReference("parking").orderByChild("postcretoruserID").equalTo(user.getUid());;
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Toast.makeText(CreatePostDetails.this, "Name "+user.getUid(), Toast.LENGTH_SHORT).show();
                for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){

                   // Toast.makeText(CreatePostDetails.this, "Name "+user.getUid(), Toast.LENGTH_SHORT).show();
                    Pojoclass pojoclass = dataSnapshot1.getValue(Pojoclass.class);
                      location.setText((dataSnapshot1.child("name").getValue(String.class)));
                    Toast.makeText(CreatePostDetails.this, "Name "+dataSnapshot1.child("name").getValue(String.class), Toast.LENGTH_SHORT).show();
                     initilizeUI(pojoclass);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(CreatePostDetails.this, "opps Something ...wrong", Toast.LENGTH_SHORT).show();
            }
        });
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
