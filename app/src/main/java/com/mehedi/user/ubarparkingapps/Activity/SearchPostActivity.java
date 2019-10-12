package com.mehedi.user.ubarparkingapps.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Switch;
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
import com.mehedi.user.ubarparkingapps.PostWrite_Read.PostReadAdaptar;
import com.mehedi.user.ubarparkingapps.R;
import com.mehedi.user.ubarparkingapps.RecyclerSetOnItemClickListener;

import java.util.ArrayList;
import java.util.List;

public class SearchPostActivity extends AppCompatActivity {
    private EditText SearchEdittext;
    private DatabaseReference databaseReference;
    private Pojoclass pojoclass = new Pojoclass();
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private RecyclerView searchPostRecyclerView;
    private PostReadAdaptar postReadAdaptar;
    private List<Pojoclass> list;
    private Query query;
    private SearchView searchView;
    private ArrayAdapter<Pojoclass> arrayAdapter;
    private MainActivity mainActivity;
    public SharedPreferences sharedPreferences;
    public  SharedPreferences.Editor editor;
    private   ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_post);


        Toolbar toolbar = findViewById(R.id.search_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchPostActivity.super.onBackPressed();
            }
        });

        initilizeView();
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading data......");
        progressDialog.show();

        searchPostRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
              for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                  Pojoclass pojoclass = dataSnapshot1.getValue(Pojoclass.class);
                  list.add(pojoclass);
              }
                postReadAdaptar = new PostReadAdaptar(SearchPostActivity.this,list);
                searchPostRecyclerView.setAdapter(postReadAdaptar);
                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(SearchPostActivity.this, "opps Something ...wrong", Toast.LENGTH_SHORT).show();
            }
        });






       searchPostRecyclerView.addOnItemTouchListener(new RecyclerSetOnItemClickListener(this, new RecyclerSetOnItemClickListener.OnItemClickListener() {
           @Override
           public void onItemClick(View view, int position) {
              String postcretorUserID =  list.get(position).getPostcretoruserID();
              String location  = list.get(position).getLocation();
              String slottype  = list.get(position).getSloatType();

               Intent intent = new Intent(SearchPostActivity.this, SearchPostDetailsActivity.class);
               Bundle bundle= new Bundle();
               bundle.putString("id",postcretorUserID);
               bundle.putString("loc",location);
               bundle.putString("slot",slottype);
               // Toast.makeText(SearchTvActivity.this, "Cool => " + TvNumber, Toast.LENGTH_SHORT).show();
               //intent.putExtra(bundle);
               intent.putExtras(bundle);

               ActivityOptionsCompat optionsCompat=ActivityOptionsCompat.makeSceneTransitionAnimation(SearchPostActivity.this,
                       new android.support.v4.util.Pair<View,String>(view.findViewById(R.id.searchlocation),SearchPostDetailsActivity.VIEW_NAME_HEADER_TITLE));
               ActivityCompat.startActivity(SearchPostActivity.this,intent,optionsCompat.toBundle());
           }
       }));

    }

    private void initilizeView() {
        mAuth = FirebaseAuth.getInstance();
        list = new ArrayList<>();
        searchPostRecyclerView = findViewById(R.id.searcepostrecyclerview);
        SearchEdittext = findViewById(R.id.searchbylocation);
        //searchView = findViewById(R.id.searchViewId);
        databaseReference = FirebaseDatabase.getInstance().getReference("parking");
        mainActivity = new MainActivity();
//        query = FirebaseDatabase.getInstance().getReference("parking")
//                .orderByChild("location").startAt("Rup");

        sharedPreferences = getSharedPreferences("alreadylogin",MODE_PRIVATE);
        editor = sharedPreferences.edit();
       // arrayAdapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,list);
    }

    public void SearchResult(View view) {


        String quer = SearchEdittext.getText().toString();
        query = FirebaseDatabase.getInstance().getReference("parking")
                .orderByChild("location").startAt(quer);
        int i = list.size();
        for(int j=i-1;j >=0;j--){
            list.remove(j);
            postReadAdaptar.notifyDataSetChanged();
        }
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                    Pojoclass pojoclass = dataSnapshot1.getValue(Pojoclass.class);
                    list.add(pojoclass);
                }
                postReadAdaptar = new PostReadAdaptar(SearchPostActivity.this,list);
                searchPostRecyclerView.setAdapter(postReadAdaptar);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(SearchPostActivity.this, "opps Something ...wrong", Toast.LENGTH_SHORT).show();
            }
        });
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
            //finish();
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
