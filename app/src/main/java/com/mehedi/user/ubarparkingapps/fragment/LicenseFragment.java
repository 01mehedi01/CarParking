package com.mehedi.user.ubarparkingapps.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mehedi.user.ubarparkingapps.Activity.BokingActivity;
import com.mehedi.user.ubarparkingapps.Activity.MainActivity;
import com.mehedi.user.ubarparkingapps.PostWrite_Read.DrivierinformationPojoClass;
import com.mehedi.user.ubarparkingapps.PostWrite_Read.Pojoclass;
import com.mehedi.user.ubarparkingapps.R;

import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class LicenseFragment extends Fragment {

    private TextInputLayout DrivingLicense;
    private TextInputLayout DriverPhoneNo;
    private Button SubmitBtn;
    private DrivierinformationPojoClass pojoclass = new DrivierinformationPojoClass();
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private View v;
    private DatabaseReference databaseReference;
    public LicenseFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_license, container, false);
        initilizeView();



        SubmitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String license = DrivingLicense.getEditText().getText().toString().trim();
                String phone = DriverPhoneNo.getEditText().getText().toString().trim();


                    if(license.length() < 15){
                        DrivingLicense.setError("At least 15 digit");
                        //DrivingLicense.requestFocus();
                        return;
                    }
                if(phone.isEmpty() ){
                    DriverPhoneNo.setError("phone Number is Requird");
                   // DriverPhoneNo.requestFocus();
                    return;
                }


                if(!(license.isEmpty()) && !(phone.isEmpty()) ){


                    pojoclass = new DrivierinformationPojoClass(license,phone,user.getUid());

                    databaseReference.child(user.getUid()).setValue(pojoclass);


                    Toast.makeText(getActivity(), "Save Successfullll", Toast.LENGTH_SHORT).show();


                    DrivingLicense.getEditText().setText("");
                    DriverPhoneNo.getEditText().setText("");

                    Intent intent = new Intent(getActivity(), BokingActivity.class);
                    startActivity(intent);


                }
               // Toast.makeText(getActivity(), ""+license, Toast.LENGTH_SHORT).show();
            }
        });



        return v;
    }

    private void initilizeView() {
        DrivingLicense = v.findViewById(R.id.textinputlicense);
        DriverPhoneNo = v.findViewById(R.id.textinputcontac);
        SubmitBtn = v.findViewById(R.id.submitbtn);
        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("driverinformation");
        user = mAuth.getCurrentUser();
    }

}
