package com.mehedi.user.ubarparkingapps.Activity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.mehedi.user.ubarparkingapps.PostWrite_Read.BokingPoJo;
import com.mehedi.user.ubarparkingapps.PostWrite_Read.DrivierinformationPojoClass;
import com.mehedi.user.ubarparkingapps.PostWrite_Read.Pojoclass;
import com.mehedi.user.ubarparkingapps.PostWrite_Read.bokingPojoClass;
import com.mehedi.user.ubarparkingapps.R;
import com.mehedi.user.ubarparkingapps.fragment.LicenseFragment;
import com.mehedi.user.ubarparkingapps.fragment.PaymentFragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class BokingActivity extends AppCompatActivity {

    private EditText timersetEdittext,DatePickerEdittext,timepickerEdittext,timepickerlastEdittext;
    private DrivierinformationPojoClass pojoclass = new DrivierinformationPojoClass();
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private DatabaseReference databaseReference;
    private DatabaseReference bokingDatabaseRf;
    private Query query;
    private List<DrivierinformationPojoClass> list;

    private String PostcretorUsrID;
    public SharedPreferences sharedPreferences;
    public  SharedPreferences.Editor editor;
    private ProgressDialog progressDialog;
    private SharedPreferences PostcretorIDsharedPreferences;
    private   SharedPreferences.Editor PostcretorIDeditor;
    private BokingPoJo bokingpojoclass = new BokingPoJo();

    private Calendar calendar;
    private  int year,month,day;
    private String enentReturntDataeOnDevice;
    int currentHour;
    int currentMinute;
    String amPm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boking);


        Toolbar toolbar = findViewById(R.id.searcedetails_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BokingActivity.super.onBackPressed();
            }
        });


        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);


        currentHour = calendar.get(Calendar.HOUR_OF_DAY);
        currentMinute = calendar.get(Calendar.MINUTE);


        initilizeView();

        query = FirebaseDatabase.getInstance().getReference("driverinformation").orderByChild("driverUserID").equalTo(user.getUid());;

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                    pojoclass = dataSnapshot1.getValue(DrivierinformationPojoClass.class);

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(BokingActivity.this, "opps Something ...wrong", Toast.LENGTH_SHORT).show();
            }
        });


       // Toast.makeText(this, "PostcretorUsrID    "+PostcretorUsrID, Toast.LENGTH_SHORT).show();
    }

    private void initilizeView() {

        timersetEdittext = findViewById(R.id.timerset);
        DatePickerEdittext= findViewById(R.id.datepicker);
        timepickerEdittext = findViewById(R.id.timepicker);
        timepickerlastEdittext = findViewById(R.id.timepickerlast);


        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("driverinformation");
        bokingDatabaseRf = FirebaseDatabase.getInstance().getReference("relation");
        user = mAuth.getCurrentUser();

        PostcretorIDsharedPreferences = getSharedPreferences("postcretorid",MODE_PRIVATE);
        PostcretorIDeditor = PostcretorIDsharedPreferences.edit();

        PostcretorUsrID = PostcretorIDsharedPreferences.getString("postcretorid","");



        sharedPreferences = getSharedPreferences("alreadylogin",MODE_PRIVATE);
        editor = sharedPreferences.edit();



    }

    public void Conform(View view) {


        String boking = timersetEdittext.getText().toString();
        String date = DatePickerEdittext.getText().toString();
        String fast = timepickerEdittext.getText().toString();
        String last = timepickerlastEdittext.getText().toString();

        String lenth =  pojoclass.getDrivingLicense();

       // Toast.makeText(this, "cool"+pojoclass.getDrivingLicense(), Toast.LENGTH_SHORT).show();




        try {
            if (lenth.isEmpty()) {
                LicenseFragment fragment = new LicenseFragment();
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction ft = fragmentManager.beginTransaction();
                ft.setCustomAnimations(R.anim.from_right, R.anim.form_left, R.anim.from_right, R.anim.form_left);
                ft.addToBackStack(null);
                ft.add(R.id.fragmentContainer, fragment, "license").commit();
            } else {


                if(boking.isEmpty() ){
                    timersetEdittext.setError("location is Requird");
                    timersetEdittext.requestFocus();
                    return;
                }
                if(date.isEmpty() ){
                    DatePickerEdittext.setError("location is Requird");
                    DatePickerEdittext.requestFocus();
                    return;
                }
                if(fast.isEmpty() ){
                    timepickerEdittext.setError("location is Requird");
                    timepickerEdittext.requestFocus();
                    return;
                }
                if(last.isEmpty() ){
                    timepickerlastEdittext.setError("location is Requird");
                    timepickerlastEdittext.requestFocus();
                    return;
                }
                if(!(boking.isEmpty()) &&  !(date.isEmpty()) && !(fast.isEmpty()) && !(last.isEmpty())){


                    bokingpojoclass = new BokingPoJo(boking,user.getUid(),date,fast,last);

                    bokingDatabaseRf.child(PostcretorUsrID).setValue(bokingpojoclass);
                    Toast.makeText(this, "Save Successfullll", Toast.LENGTH_SHORT).show();


                    timersetEdittext.setText("");
                    DatePickerEdittext.setText("");
                    timepickerEdittext.setText("");
                    timepickerlastEdittext.setText("");

                    PaymentFragment fragment = new PaymentFragment();
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction ft = fragmentManager.beginTransaction();
                    ft.setCustomAnimations(R.anim.from_right, R.anim.form_left, R.anim.from_right, R.anim.form_left);
                    ft.addToBackStack(null);
                    ft.add(R.id.fragmentContainer, fragment, "payment").commit();



                }




//
            }
        }catch (Exception c){
            LicenseFragment fragment = new LicenseFragment();
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction ft = fragmentManager.beginTransaction();
            ft.setCustomAnimations(R.anim.from_right, R.anim.form_left, R.anim.from_right, R.anim.form_left);
            ft.addToBackStack(null);
            ft.add(R.id.fragmentContainer, fragment, "license").commit();
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

    @Override
    protected void onStart() {
        super.onStart();

        final DatePickerDialog.OnDateSetListener Returndatedatelistenerrr = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(year,month,dayOfMonth);
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                enentReturntDataeOnDevice = sdf.format(calendar.getTime());
                DatePickerEdittext.setText(enentReturntDataeOnDevice);

            }
        };

        DatePickerEdittext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog ReturndatedatePickerDialogg = new DatePickerDialog(BokingActivity.this,
                        Returndatedatelistenerrr,year,month,day);
                ReturndatedatePickerDialogg.show();
            }

        });



        final TimePickerDialog timePickerDialog = new TimePickerDialog(BokingActivity.this, new TimePickerDialog.OnTimeSetListener() {

            @Override
            public void onTimeSet(TimePicker timePicker, int hourOfDay, int minutes) {




                timepickerEdittext.setText(String.format("%02d:%02d", hourOfDay, minutes));
            }
        }, currentHour, currentMinute, false);

        timepickerEdittext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timePickerDialog.show();
            }
        });

        final TimePickerDialog timePickerDialoglast = new TimePickerDialog(BokingActivity.this, new TimePickerDialog.OnTimeSetListener() {

            @Override
            public void onTimeSet(TimePicker timePicker, int hourOfDay, int minutes) {




                timepickerlastEdittext.setText(String.format("%02d:%02d", hourOfDay, minutes));
            }
        }, currentHour, currentMinute, false);

        timepickerlastEdittext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timePickerDialoglast.show();
            }
        });
    }
}
