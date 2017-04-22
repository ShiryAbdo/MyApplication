package com.example.shady.myapplication.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shady.myapplication.activities.LoginActivity;
import com.example.shady.myapplication.activities.MainActivity;
import com.example.shady.myapplication.data.MedicInformation;
import com.example.shady.myapplication.R;
import com.example.shady.myapplication.Interface.UpdateInterface;
import com.example.shady.myapplication.data.UserClass;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class EditFragment extends Fragment implements UpdateInterface {
    //    Firebase
    FirebaseAuth firebaseAuth ;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference mDatabase;

    UserClass user_name;
    private EditText et_userName, et_email;
    private String updatUserName, updatEmail;
    private TextView tv_done ,TextView;
    DataSnapshot dataSnapshot ;
    private FirebaseAuth.AuthStateListener authListener;
    private FirebaseAuth auth;

     @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_edit, container, false);



        tv_done =(TextView) rootView.findViewById(R.id.text_done);
        et_userName = (EditText)rootView.findViewById(R.id.edit_username);
        et_email = (EditText)rootView.findViewById(R.id.edit_email);
        TextView =(TextView) rootView.findViewById(R.id.text_reset_password);

        user_name = new UserClass();

        mDatabase = FirebaseDatabase.getInstance().getReference();
        final FirebaseUser currUser = FirebaseAuth.getInstance().getCurrentUser();

        //get firebase auth instance
        auth = FirebaseAuth.getInstance();

        //get current user



        try {
            mDatabase.child("users").child(currUser.getUid()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    UserClass userClass = dataSnapshot.getValue(UserClass.class);
                    String name = userClass.getUsername();
                    String email = userClass.getEmail();
                    et_userName.setText(name);
                    et_email.setText(email);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    // Failed to read value


                }
            });
        }catch (Exception e){
            Toast.makeText(getContext(), "Selected: " +  "Failed to read value.", Toast.LENGTH_LONG).show();

        }


//      anather salution
//        firebaseAuth = FirebaseAuth.getInstance();
//        String current_user_email = firebaseAuth.getCurrentUser().getEmail();
//        String Current_user_name =  firebaseAuth.getCurrentUser().getDisplayName();


//        et_userName.setText(Current_user_name);
//        et_email.setText(current_user_email);





        tv_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updatUserName = et_userName.getText().toString();
                updatEmail = et_email.getText().toString();






                if (currUser != null && !et_email.getText().toString().trim().equals("") && !TextUtils.isEmpty(et_userName.getText().toString())) {
                    currUser.updateEmail(et_email.getText().toString().trim())
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(getContext(), "Email address is updated. Please sign in with new email id!", Toast.LENGTH_LONG).show();

                                        signOut();
                                    } else {
                                        Toast.makeText(getContext(), "Failed to update email!", Toast.LENGTH_LONG).show();

                                    }
                                }
                            });
                    currUser.updateProfile(new UserProfileChangeRequest.Builder().setDisplayName(updatUserName).build());
                } else if (et_email.getText().toString().trim().equals("")) {
                    et_email.setError("Enter email");
                }





                try {
                    mDatabase.child("users").child(currUser.getUid()).child("username").setValue(updatUserName);
                    mDatabase.child("users").child(currUser.getUid()).child("email").setValue(updatEmail);
//                    FirebaseAuth.getInstance().getCurrentUser().updateEmail(updatEmail);

                }catch (Exception e){
                    Toast.makeText(getContext(),"Failed to write value.", Toast.LENGTH_LONG).show();

                }


                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
//                firebaseAuth.getCurrentUser().updateEmail(updatEmail);
//                firebaseAuth.getCurrentUser().updateProfile(UserProfileChangeRequest)


            }
        });

        TextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ResetPasswordFragment RF=new ResetPasswordFragment();
                FragmentManager FM =getFragmentManager();
                FragmentTransaction FT = FM.beginTransaction();
                FT.replace(R.id.activity_login,RF);
                FT.commit();
//
//                AboutUsFragment fragment = new AboutUsFragment();
//                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
//                ft.replace(R.id.fragment_edit, fragment);
//                ft.commit();

//                ResetPasswordFragment fragment = new ResetPasswordFragment();
//                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
//                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                fragmentTransaction.replace(R.id.fragment_edit, fragment);
//                fragmentTransaction.addToBackStack(null);
//                fragmentTransaction.commit();

            }
        });


        return rootView;
    }
    //sign out method
    public void signOut() {
        auth.signOut();
        startActivity(new Intent(getActivity(),LoginActivity.class));
        getActivity().finish();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        (getActivity()).setTitle("Edit your profile");
    }


    @Override
    public void updateUI(ArrayList<MedicInformation> medicInformations) {

    }
}
