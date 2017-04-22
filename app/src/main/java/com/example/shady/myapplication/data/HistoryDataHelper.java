package com.example.shady.myapplication.data;

import java.util.ArrayList;

/**
 * Created by Shiry Abdo on 4/7/2017.
 */

public class HistoryDataHelper {

    private static HistoryDataHelper instance;

    private ArrayList<MedicInformation> historyList;

    private HistoryDataHelper() {
    }



    public static HistoryDataHelper getInstance() {
        if (instance == null)
            instance = new HistoryDataHelper();
        return instance;
    }


    public ArrayList<MedicInformation> getHistoryList() {
        if(historyList == null)
            historyList = new ArrayList<>();
        return historyList;
    }

    public void setHistoryList(ArrayList<MedicInformation> historyList) {
        this.historyList = historyList;
    }

    public void addToList(MedicInformation newItem){
        if(historyList == null)
            historyList = new ArrayList<>();

        historyList.add(newItem);
    }
}
