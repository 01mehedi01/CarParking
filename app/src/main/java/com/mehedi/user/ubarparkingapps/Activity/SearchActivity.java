package com.mehedi.user.ubarparkingapps.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.mehedi.user.ubarparkingapps.Activity.MainActivity;
import com.mehedi.user.ubarparkingapps.PostWrite_Read.Pojoclass;
import com.mehedi.user.ubarparkingapps.R;
import com.mehedi.user.ubarparkingapps.SliderAdapterExample;
import com.mehedi.user.ubarparkingapps.Utils;


import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import me.relex.circleindicator.CircleIndicator;


public class SearchActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,View.OnClickListener {
     private MainActivity mainActivity;
     private Utils utils = null;
     private CardView CreatePostcardview,findPostCardView,DirectionCardview;
    private SharedPreferences sharedPreferences;
    private  SharedPreferences.Editor editor;
    private SliderAdapterExample adapter;
    private  ViewPager viewpager;
    private   CircleIndicator indicator;

    private Query query;
    private DatabaseReference databaseReference;
    private Pojoclass pojoclass = new Pojoclass();
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private DatabaseReference bokingdatabaseRF;
    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        initilizeView();

        viewpager.setAdapter(adapter);
        indicator.setViewPager(viewpager);

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new MyTimerTask(),2000,4000);






    }






    private void initilizeView() {
        AdView adView = new AdView(this);
        adView.setAdSize(AdSize.BANNER);
        adView.setAdUnitId("ca-app-pub-9177620541638751/4045046868");


        mainActivity = new MainActivity();

        CreatePostcardview = findViewById(R.id.createapost);
        findPostCardView = findViewById(R.id.searceapost);
        DirectionCardview = findViewById(R.id.direction);




        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        sharedPreferences = getSharedPreferences("alreadylogin",MODE_PRIVATE);
        editor = sharedPreferences.edit();
        CreatePostcardview.setOnClickListener(this);
        findPostCardView.setOnClickListener(this);
       // DirectionCardview.setOnClickListener(this);

        adapter = new SliderAdapterExample(this);

        viewpager =  findViewById(R.id.viewpagerid);


       indicator = findViewById(R.id.indicator);



        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("parking");
        bokingdatabaseRF = FirebaseDatabase.getInstance().getReference("relation");
        user = mAuth.getCurrentUser();

// optional
//        adapter.registerDataSetObserver(indicator.getDataSetObserver());
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
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
        if (id == R.id.home) {
            Intent intent = new Intent(getApplicationContext(), SearchActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onClick(View v) {
        //Toast.makeText(this, "Inside", Toast.LENGTH_SHORT).show();
        String categoryName = "";

           // Toast.makeText(this, "Internet", Toast.LENGTH_SHORT).show();
            if(v.getId()== R.id.createapost){



                try{
                    if(pojoclass.getPostcretoruserID().isEmpty()){
                        startActivity(new Intent(getApplicationContext(),CreatePostActivity.class));
                    }else {
                        startActivity(new Intent(getApplicationContext(),CreatePostDetails.class));
                    }

                }catch (Exception ex){
                    startActivity(new Intent(getApplicationContext(),CreatePostActivity.class));
                }
              //  Toast.makeText(getApplicationContext(), "createapost", Toast.LENGTH_SHORT).show();

            }else if(v.getId()==R.id.searceapost){
                //Toast.makeText(getApplicationContext(), "searceapost", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(),SearchPostActivity.class));
            }

//        if(v.getId() == R.id.direction){
//            Toast.makeText(getApplicationContext(), "direction", Toast.LENGTH_SHORT).show();
//            startActivity(new Intent(SearchActivity.this,DirectionActivity.class));
//        }



    }

    public class MyTimerTask extends TimerTask{

        @Override
        public void run() {
            SearchActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if(viewpager.getCurrentItem() == 0){
                        viewpager.setCurrentItem(1);
                    }else if(viewpager.getCurrentItem() == 1){
                        viewpager.setCurrentItem(2);
                    }else if(viewpager.getCurrentItem() == 2){
                        viewpager.setCurrentItem(3);
                    }
                    else if(viewpager.getCurrentItem() == 3){
                        viewpager.setCurrentItem(0);
                    }
                }
            });
        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();

        moveTaskToBack(true);
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(1);
    }

    @Override
    protected void onStart() {
        super.onStart();
        query =  FirebaseDatabase.getInstance().getReference("parking").orderByChild("postcretoruserID").equalTo(user.getUid());;
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
               // Toast.makeText(SearchActivity.this, "Name "+user.getUid(), Toast.LENGTH_SHORT).show();
                for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){

                    // Toast.makeText(CreatePostDetails.this, "Name "+user.getUid(), Toast.LENGTH_SHORT).show();
                    pojoclass = dataSnapshot1.getValue(Pojoclass.class);
                 //   Toast.makeText(SearchActivity.this, "Name "+dataSnapshot1.child("name").getValue(String.class), Toast.LENGTH_SHORT).show();

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(SearchActivity.this, "opps Something ...wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
