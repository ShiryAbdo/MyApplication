package com.example.shady.myapplication.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shady.myapplication.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by ShaDy on 3/24/2017.
 */

public class ResetPasswordFragment  extends Fragment {
    private TextView txt_reset;
    private EditText et_email;
    private String emailAdress;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Activity activity = getActivity();
        //((LoginActivity)activity).setOnBackPressedListener(new BaseBackPressedListener((FragmentActivity) activity));
        View view = inflater.inflate(R.layout.fragment_forgotpass,container,false);

        mAuth = FirebaseAuth.getInstance();
        et_email = (EditText) view.findViewById(R.id.edit_email_reset);
        txt_reset = (TextView) view.findViewById(R.id.text_reset_reset);
        txt_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emailAdress = et_email.getText().toString();
                resetPassword(emailAdress);
                Toast.makeText(getActivity(), "txt_reset password ok", Toast.LENGTH_SHORT).show();
            }
        });
        return  view;
    }
    private void resetPassword(String emailAddress){
        // Method to Reset Password or Forget Password Option
        mAuth.sendPasswordResetEmail(emailAddress).addOnCompleteListener(new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task) {
                if (task.isSuccessful()) {
                    Toast.makeText(getActivity(), "We have sent you instructions to reset your password!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), "Failed to send reset et_email!", Toast.LENGTH_SHORT).show();
                }

            }
        });
//        // Method to change Password Option
//        userClass.updatePassword(modeStr)
//                .addOnCompleteListener(new OnCompleteListener() {
//                    @Override
//                    public void onComplete(@NonNull Task task) {
//                        if (task.isSuccessful()) {
//                            Toast.makeText(ForgetAndChangePasswordActivity.this, "Password is updated!", Toast.LENGTH_SHORT).show();
//                        } else {
//                            Toast.makeText(ForgetAndChangePasswordActivity.this, "Failed to update password!", Toast.LENGTH_SHORT).show();
//                        }
//                        PD.dismiss();
//                    }
//                });
        ////////////////////////////////////////////////////////
//        mAuth.sendPasswordResetEmail(emailAddress)
//                .addOnCompleteListener(new OnCompleteListener<Void>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Void> task) {
//                        if (task.isSuccessful()) {
//                            Toast.makeText(getActivity(), "Email Sent", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                });
    }



}