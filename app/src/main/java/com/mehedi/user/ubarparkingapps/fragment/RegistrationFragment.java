package com.mehedi.user.ubarparkingapps.fragment;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.mehedi.user.ubarparkingapps.R;
import com.mehedi.user.ubarparkingapps.Activity.SearchActivity;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 */
public class RegistrationFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private  String Name = "Develop By mehedi";


    private SharedPreferences sharedPreferences;
    private  SharedPreferences.Editor editor;
    private boolean Contaiatologin ;
    private Button getverificationCodeButton;
    private Button SubmitButton;
    private EditText emailEdittext;
    private EditText passwordEdittext;
    private EditText repasswordEdittext;
    private FirebaseAuth mAuth;
    private String mParam1;
    private String mParam2;
    public RegistrationFragment() {
        // Required empty public constructor


    }
    public static RegistrationFragment newInstance(String param1, String param2) {
        RegistrationFragment fragment = new RegistrationFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
    private  View v;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_registration, container, false);

        initilizeView(v);





        getverificationCodeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

         SubmitButton.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {

                 String Email = emailEdittext.getText().toString();
                 String Password = passwordEdittext.getText().toString();
                 String Re_password = repasswordEdittext.getText().toString();

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
                             .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                         @Override
                         public void onComplete(@NonNull Task<AuthResult> task) {

                            if(task.isSuccessful()){
                                Contaiatologin = true;
                                editor.clear();
                                editor.commit();
                                editor.putBoolean("autologin", Contaiatologin);
                                editor.commit();

                                startActivity(new Intent(getActivity(), SearchActivity.class));
                            }else{

                            }
                         }
                     });
                 }

//                 Contaiatologin = true;
//                 editor.clear();
//                 editor.commit();
//                 editor.putBoolean("autologin",Contaiatologin);
//                 editor.commit();
             }
         });

        return v;
    }

    private void initilizeView(View v) {
        mAuth = FirebaseAuth.getInstance();
        //getverificationCodeButton = v.findViewById(R.id.getvarification);
        SubmitButton = v.findViewById(R.id.signi);

        emailEdittext = v.findViewById(R.id.emailreg);
        passwordEdittext = v.findViewById(R.id.passwordreg);
        repasswordEdittext = v.findViewById(R.id.repasswordreg);


        sharedPreferences = getActivity().getSharedPreferences("alreadylogin",MODE_PRIVATE);
        editor = sharedPreferences.edit();
        Contaiatologin = sharedPreferences.getBoolean("autologin",false);

    }
private void matchpassword(){

}
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(String name);

    }


}
