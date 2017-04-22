package com.example.shady.myapplication.fragment;



 import android.support.v4.app.FragmentTransaction;
 import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
 import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

 import com.example.shady.myapplication.R;
 import com.example.shady.myapplication.data.UserClass;
 import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
 import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginFragment extends Fragment {
    private ImageView img_google;
    private TextView txt_signIn, txt_register, txt_forgot;
    private EditText et_email, et_password; 
    private String email,password;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference mDatabase;
    UserClass userClass;
    
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login,container,false);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        userClass =new UserClass();

        img_google = (ImageView) view.findViewById(R.id.image_social_google);
        et_email = (EditText) view.findViewById(R.id.edit_email_login);
        et_password = (EditText) view.findViewById(R.id.edit_password_login);
        txt_signIn = (TextView) view.findViewById(R.id.text_signIn_login);
        txt_register = (TextView) view.findViewById(R.id.text_create);
        txt_forgot = (TextView) view.findViewById(R.id.text_forgot);

        //Sign in with google account
        img_google.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = et_email.getText().toString();
                password = et_password.getText().toString();
                Toast.makeText(getActivity(), "ok Forgot", Toast.LENGTH_SHORT).show();
            }
        });

        //Sign in with FireBase Authentication  (et_email and password)
        txt_signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                loginAccount();
                Toast.makeText(getActivity(),et_email.getText().toString() + "Login" + et_password.getText().toString(), Toast.LENGTH_SHORT).show();

//                FirebaseUser userClass = FirebaseAuth.getInstance().getCurrentUser();
//                if (userClass != null) {
//                    // Name, et_email address, and profile photo Url
//                    String name = userClass.getDisplayName();
//                    String et_email = userClass.getEmail();
//                    Uri photoUrl = userClass.getPhotoUrl();
//
//                    // Check if userClass's et_email is verified
//                    boolean emailVerified = userClass.isEmailVerified();
//                    String uid = userClass.getUid();
//                    Toast.makeText(getActivity(), "name = "+ name, Toast.LENGTH_SHORT).show();
//                    Toast.makeText(getActivity(), "et_email = "+ et_email, Toast.LENGTH_SHORT).show();
//                    Toast.makeText(getActivity(), "uid = "+ uid, Toast.LENGTH_SHORT).show();
//                    Toast.makeText(getActivity(), "isVerified = "+ emailVerified, Toast.LENGTH_SHORT).show();
//                }

            }
        });
        txt_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 RegisterFragment fragment = new RegisterFragment();
                 FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.activity_login, fragment);
                ft.commit();

//                  FT.replace(R.id.activity_login,RF);
//                FT.commit();

                Toast.makeText(getActivity(), "ok Register", Toast.LENGTH_SHORT).show();
            }
        });
       // txt_forgot.setOnClickListener(new View.OnClickListener() {
         //   @Override
          //  public void onClick(View v) {
            //    ResetPasswordFragment RF=new ResetPasswordFragment();
             //   FragmentManager FM =getFragmentManager();
              //  FragmentTransaction FT = FM.beginTransaction();
              //   FT.replace(R.id.activity_login,RF);
               // FT.commit();
               // Toast.makeText(getActivity(), "ok Forgot", Toast.LENGTH_SHORT).show();
      //      }
      //  });


        return view;
    }
    private void loginAccount(){
        // Reset errors.
        et_email.setError(null);
        et_password.setError(null);

        // Store values at the time of the login attempt.
        String email = et_email.getText().toString();
        String password = et_password.getText().toString();

        boolean cancel = false;
        View focusView = null;

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
            // There was an error; don't  login and focus the first form field with an error.
            focusView.requestFocus();
        } else {
            Log.d("TAG", "signInWithEmail:onnnnnnnnnnnnnnnnnnnnnnnnn:" );

            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            Log.d("TAG", "signInWithEmail:onComplete:" + task.isSuccessful());

                            // If sign in fails, display a message to the user. If sign in succeeds
                            // the auth state listener will be notified and logic to handle the
                            // signed in user can be handled in the listener.
                            if (!task.isSuccessful()) {
                                Log.w("TAG", "signInWithEmail:failed", task.getException());
                                Toast.makeText(getActivity(),"R.string.auth_failed", Toast.LENGTH_SHORT).show();
                            }

                            // ...
                        }
                    });
            // Show a progress spinner, and kick off a background task to
            // perform the userClass login attempt.
            //showProgress(true);
//            Toast.makeText(getActivity(), "gooooooooooooo", Toast.LENGTH_SHORT).show();
//            mAuth.signInWithEmailAndPassword(email, password)
//                    .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
//                        @Override
//                        public void onComplete(@NonNull Task<AuthResult> task) {
//                            if (!task.isSuccessful()) {
//                                Toast.makeText(getActivity(), "Wrong email or password!!", Toast.LENGTH_SHORT).show();
//                            } else {
//                                Toast.makeText(getActivity(), "Complete okkkkk : " + userClass.username, Toast.LENGTH_SHORT).show();
//
//                                final FirebaseUser currUser = FirebaseAuth.getInstance().getCurrentUser();
//                                ValueEventListener postListener = new ValueEventListener() {
//                                    @RequiresApi(api = Build.VERSION_CODES.M)
//                                    @Override
//                                    public void onDataChange(DataSnapshot dataSnapshot) {
//                                        if (currUser != null) {
//                                            String uid = currUser.getUid();
//                                            //userClass
//                                            userClass = dataSnapshot.child("users").child(uid).getValue(UserClass.class);
//                                            Toast.makeText(getActivity(), "Complete Login : " + userClass.username, Toast.LENGTH_SHORT).show();
//                                        }
//                                    }
//
//                                    @Override
//                                    public void onCancelled(DatabaseError databaseError) {
//                                    }
//                                };
//                                mDatabase.addValueEventListener(postListener);
//                                Intent intent = new Intent(getActivity(), MainActivity.class);
//                                intent.putExtra("username", userClass.username);
//                                intent.putExtra("et_email", userClass.email);
////                            getActivity().finish();
//
//                            }
//                        }
//                    });
        }
    }
    private boolean isEmailValid(String email) {
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        return password.length() > 5;
    }


    private void loginGoogle(String email,String password){

    }
}