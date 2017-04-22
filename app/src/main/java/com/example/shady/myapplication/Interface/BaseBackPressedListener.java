package com.example.shady.myapplication.Interface;

import android.app.FragmentManager;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

/**
 * Created by ShaDy on 3/23/2017.
 */

public class BaseBackPressedListener  implements OnBackPressedListener {
    private final FragmentActivity ACTIVITY;

    public BaseBackPressedListener(FragmentActivity activity) {
        this.ACTIVITY = activity;
    }
    @Override
    public void doBack() {
        ACTIVITY.getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);

        Toast.makeText(ACTIVITY, "BaseBackPressedListener", Toast.LENGTH_SHORT).show();
    }
}
