package com.example.shady.myapplication.activities;

import android.support.v4.app.FragmentTransaction;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shady.myapplication.fragment.LoginFragment;
import com.example.shady.myapplication.Interface.OnBackPressedListener;
import com.example.shady.myapplication.R;
import com.example.shady.myapplication.data.UserClass;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    private Button btn_signUp;
    private TextView txt_signIn;
    private EditText et_email, et_password;
    private UserClass userClass;
     private FragmentTransaction mFragmentTransaction;
    private LoginFragment mLoginFragment;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    protected OnBackPressedListener onBackPressedListener;
    public void setOnBackPressedListener(OnBackPressedListener onBackPressedListener) {
        this.onBackPressedListener = onBackPressedListener;
    }
    @Override
    public void onBackPressed() {
        if (onBackPressedListener != null) {
            onBackPressedListener.doBack();
            LoginFragment mLoginFragment=new LoginFragment();
            FragmentTransaction ft =  getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.activity_login, mLoginFragment);
            ft.commit();
//            FragmentManager mFragmentManager =getFragmentManager();
//            FragmentTransaction mFragmentTransaction = mFragmentManager.beginTransaction();
//            mFragmentTransaction.replace(R.id.activity_login,mLoginFragment);
//            mFragmentTransaction.commit();
            Toast.makeText(getApplicationContext(), "onBackPressed", Toast.LENGTH_SHORT).show();

//            ResetPasswordFragment fragment = new ResetPasswordFragment();
//            FragmentTransaction ft = getFragmentManager().beginTransaction();
//            ft.replace(R.id.activity_login, fragment);
//            ft.commit();
//            getActivity().finish();
        }
        else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        userClass =new UserClass();
        final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                final FirebaseUser currUser = firebaseAuth.getCurrentUser();
                if (currUser != null) {
                    // UserClass is signed in
                    ValueEventListener postListener = new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (currUser != null) {
                                String uid = currUser.getUid();
                                //userClass
                                // see that is the root
                                userClass = dataSnapshot.child("users").child(uid).getValue(UserClass.class);
                                Intent intent = new Intent(getApplication(),MainActivity.class);
//                                intent.putExtra("et_email",userClass.getEmail());
//                                intent.putExtra("username", userClass.username);
                                startActivity(intent);
                                finish();
//                                Toast.makeText(getApplicationContext(), "Complete Login : " + userClass.username, Toast.LENGTH_SHORT).show();
                            }
                        }
                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                        }
                    };
                    mDatabase.addValueEventListener(postListener);
                    Toast.makeText(LoginActivity.this,"signed_in: " + currUser.getEmail(), Toast.LENGTH_SHORT).show();
                } else {
                    // UserClass is signed out

                    LoginFragment mLoginFragment=new LoginFragment();
                    FragmentTransaction ft =  getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.activity_login, mLoginFragment);
                    ft.commit();
                    Toast.makeText(LoginActivity.this,"onAuthStateChanged:signed_out", Toast.LENGTH_SHORT).show();
                }
            }
        };
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mAuth.removeAuthStateListener(mAuthListener);
    }
}