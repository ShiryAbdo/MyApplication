package com.example.shady.myapplication.fragment;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shady.myapplication.activities.MainActivity;
import com.example.shady.myapplication.data.MedicInformation;
import com.example.shady.myapplication.data.UserClass;
import com.example.shady.myapplication.fireBase.FirebaseHelper;
import com.example.shady.myapplication.R;
import com.example.shady.myapplication.Interface.UpdateInterface;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

/**
 * Created by ShaDy on 3/23/2017.
 */
public class RegisterFragment extends Fragment implements UpdateInterface {
    private TextView tv_signUp;
    private EditText et_userName, et_email, et_password, et_phone;
    private String username,email,password,phone;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    //protected FirebaseDatabase mDatabase;
    FirebaseHelper helper;
    DatabaseReference db;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Activity activity = getActivity();
        //((LoginActivity)activity).setOnBackPressedListener(new BaseBackPressedListener((FragmentActivity) activity));
        View view = inflater.inflate(R.layout.fragment_register,container,false);

        mAuth = FirebaseAuth.getInstance();
        //INITIALIZE FIREBASE DB
        db = FirebaseDatabase.getInstance().getReference();

        helper = new FirebaseHelper(db,this, null);
//        myRef = mDatabase.getReference("users");
//        myRef.setValue("Hello, World!");


        et_userName = (EditText) view.findViewById(R.id.edit_userName_register);
        et_email = (EditText) view.findViewById(R.id.edit_email_register);
        et_password = (EditText) view.findViewById(R.id.edit_password_register);
        et_phone = (EditText) view.findViewById(R.id.edit_phone_register);
        tv_signUp = (TextView) view.findViewById(R.id.text_signUp_register);

        tv_signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                username = et_userName.getText().toString();
//                email = et_email.getText().toString();
//                password = et_password.getText().toString();
//                phone = et_phone.getText().toString();
                createAccount();
                Toast.makeText(getActivity(),"Sign Up ok", Toast.LENGTH_SHORT).show();
            }
        });
        return  view;
    }

    private void createAccount(){
        // Reset errors.
        et_email.setError(null);
        et_password.setError(null);
        et_userName.setError(null);
        et_phone.setError(null);

        // Store values at the time of the login attempt.
         email = et_email.getText().toString();
         password = et_password.getText().toString();
         username = et_userName.getText().toString();
         phone = et_phone.getText().toString();

        boolean cancel = false;
        View focusView = null;

        if (TextUtils.isEmpty(username)) {
            et_userName.setError(getString(R.string.error_field_required));
            focusView = et_userName;
            cancel = true;
        }

        // Check for a valid password, if the userClass entered one.
        if (!isPasswordValid(password)) {
            et_password.setError(getString(R.string.error_invalid_password));
            focusView = et_password;
            cancel = true;
        }
        if (TextUtils.isEmpty(password)) {
            et_password.setError(getString(R.string.error_field_required));
            focusView = et_password;
            cancel = true;
        }
        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            et_email.setError(getString(R.string.error_field_required));
            focusView = et_email;
            cancel = true;
        } else if (!isEmailValid(email)) {
            et_email.setError(getString(R.string.error_invalid_email));
            focusView = et_email;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the userClass login attempt.
            //showProgress(true);

            /////////////////////////////////////////////
            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    Toast.makeText(getActivity(), "goooooooooo", Toast.LENGTH_SHORT).show();//..............................

                    if (!task.isSuccessful()) {
//                        Toast.makeText(getActivity(), "Signup failed" + task.getResult().toString(), Toast.LENGTH_SHORT).show();
                    } else {
                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                        String uid = user.getUid();
                        Toast.makeText(getActivity(), "ID-- " + uid, Toast.LENGTH_SHORT).show();//...............................
                        addNewUser(uid, username, email, password, phone);
                        Toast.makeText(getActivity(), "Complete SignUp", Toast.LENGTH_SHORT).show();//..............................
                        Intent intent = new Intent(getActivity(), MainActivity.class);
                        intent.putExtra("username", username);
                        intent.putExtra("et_email", user.getEmail());
                        startActivity(intent);
                    }
                }
            });
        }
    }
    private void addNewUser(String userId, String username, String email, String password, String phone) {
        UserClass userClass = new UserClass(userId,username, email,password, phone);
        //mDatabase.child("users").child(userId).child("username").setValue(name);//..........................................
//        mDatabase.child("users").child(userId).setValue(userClass);
        helper.save(userClass);
    }

    private boolean isEmailValid(String email) {
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        return password.length() > 5;
    }

    @Override
    public void updateUI(ArrayList<MedicInformation> medicInformations) {

    }
}
