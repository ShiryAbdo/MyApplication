package com.example.shady.myapplication.fireBase;

import android.app.Activity;
import android.support.v4.app.Fragment;

import com.example.shady.myapplication.data.MedicInformation;
import com.example.shady.myapplication.fragment.AfternoonFragment;
import com.example.shady.myapplication.fragment.EveningFragment;
import com.example.shady.myapplication.fragment.MainFragment;
import com.example.shady.myapplication.Interface.UpdateInterface;
import com.example.shady.myapplication.data.UserClass;
import com.example.shady.myapplication.fragment.MorrinngFragment;
import com.example.shady.myapplication.fragment.NightFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

/**
 * Created by Shiry Abdo on 3/31/2017.
 */

public class FirebaseHelper {

    MainFragment context =new MainFragment();
    Activity activity;
    DatabaseReference db;
    FirebaseUser currUser = FirebaseAuth.getInstance().getCurrentUser();
    Boolean saved;
    ArrayList<MedicInformation> medicInformations = new ArrayList<>();
    ArrayList<UserClass> users = new ArrayList<>();
    UpdateInterface updateInterfaceCallback;
    UserClass user = new UserClass();
    private ArrayList<String>keysList;
    EveningFragment eveningFragment= new EveningFragment();
    AfternoonFragment afternoonFragment =new AfternoonFragment();
    MorrinngFragment morrinngFragment = new MorrinngFragment();
    NightFragment nightFragment = new NightFragment();


    /*
 PASS DATABASE REFRENCE
 there  anther fragment
  */
    public FirebaseHelper(DatabaseReference db, Fragment fragment, Activity activity) {
        this.db = db;
        currUser = FirebaseAuth.getInstance().getCurrentUser();
        keysList= new ArrayList<>();

        if (fragment != null) {
            try {
                updateInterfaceCallback = (UpdateInterface) fragment;
//                this.mainFragment = fragment;
            }catch (ClassCastException e){
                throw new ClassCastException((fragment.toString()) + " must implement Update Inteface");
            }
        }
        if (activity != null) {
            try {
                updateInterfaceCallback = (UpdateInterface) activity;
                this.activity = activity;
            }catch (ClassCastException e){
                throw new ClassCastException((activity.toString()) + " must implement Update Inteface");
            }
        }
    }

    /// will not use becouse the class used to read only from class MedicInformation  Medicines
    //WRITE IF NOT NULL
    public Boolean save(MedicInformation medicInformations) {
        if (medicInformations == null) {
            saved = false;
        } else {
            try {
                db.child("users").child(currUser.getUid()).child("Medicines").push().setValue(medicInformations);

//                DatabaseReference databaseReference = db.child("users").child(currUser.getUid()).child("Medicines").push();

//                keysList.add(databaseReference.getKey());
//                databaseReference.setValue(medicInformations);
                saved =true;
            } catch (Exception e) {
                e.printStackTrace();
                saved = false;
            }
        }
        return saved;
    }

    ///
    public Boolean save(UserClass user) {
        if (user == null) {
            saved = false;
        } else {
            try {

                db.child("users").child(user.getUserId()).setValue(user);
                saved = true;
            } catch (DatabaseException e) {
                e.printStackTrace();
                saved = false;
            }
        }
        return saved;
    }

    //IMPLEMENT FETCH DATA AND FILL ARRAYLIST
    private void fetchData(DataSnapshot dataSnapshot) {
        medicInformations.clear();
        for (DataSnapshot ds : dataSnapshot.getChildren()) {
            MedicInformation item = ds.getValue(MedicInformation.class);
            item.set_id(ds.getKey());
            medicInformations.add(item);
        }
    }

    private void fetchDataUser(DataSnapshot dataSnapshot) {
        users.clear();
        for (DataSnapshot ds : dataSnapshot.getChildren()) {
            UserClass user = ds.child("users").child(currUser.getUid()).getValue(UserClass.class);

            users.add(user);
        }
    }

    //RETRIEVE
    public ArrayList<MedicInformation> retrieve( ) {
     try {
         db.child("users").child(currUser.getUid()).addChildEventListener(new ChildEventListener() {
             @Override
             public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                 fetchData(dataSnapshot);
                 if (updateInterfaceCallback != null) {
                     updateInterfaceCallback.updateUI(medicInformations);
                 }
                 context.update();
                 eveningFragment.update();
                 afternoonFragment.update();
                 morrinngFragment.update();
                 nightFragment.update();
             }

             @Override
             public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                 fetchDataUser(dataSnapshot);

                 if (updateInterfaceCallback != null) {
                     updateInterfaceCallback.updateUI(medicInformations);

                 }
                 context.update();
                 eveningFragment.update();
                 afternoonFragment.update();
                 morrinngFragment.update();
                 nightFragment.update();

             }

             @Override
             public void onChildRemoved(DataSnapshot dataSnapshot) {
             }

             @Override
             public void onChildMoved(DataSnapshot dataSnapshot, String s) {
             }

             @Override
             public void onCancelled(DatabaseError databaseError) {
             }
         });
             }catch (Exception e){}
         return medicInformations;



    }

    public ArrayList<UserClass> retrieveUsers() {
        db.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                fetchDataUser(dataSnapshot);
                if (updateInterfaceCallback != null){
                    updateInterfaceCallback.updateUI(medicInformations);
                }
                context.update();
                eveningFragment.update();
                afternoonFragment.update();
                morrinngFragment.update();
                nightFragment.update();
            }


            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                fetchData(dataSnapshot);
                if (updateInterfaceCallback != null){
                    updateInterfaceCallback.updateUI(medicInformations);
                }
                context.update();
                eveningFragment.update();
                afternoonFragment.update();
                morrinngFragment.update();
                nightFragment.update();
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        return users;
    }

    public ArrayList<String> getAddedItemsKeysList() {
        return keysList;
    }
}