package com.example.shady.myapplication.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.shady.myapplication.data.HistoryDataHelper;
import com.example.shady.myapplication.data.MedicInformation;

import java.util.ArrayList;


public class HostoryFragment extends Fragment {

     HistoryDataHelper historyDataHelper;
    private ArrayList<MedicInformation> historyList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        historyDataHelper.getHistoryList();

        historyDataHelper.setHistoryList(new ArrayList<MedicInformation>());
        /// add  data to array list string  to  go to the recycle view to show we
        historyList=  historyDataHelper.getHistoryList();

//        historyDataHelper.addToList("nbvnbfgbf");
        // TODO: 4/7/2017 add the list to the recycler ada[ter that will be created here
        return view;
    }
}
