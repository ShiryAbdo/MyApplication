package com.example.shady.myapplication.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shady.myapplication.fragment.AboutUsFragment;
import com.example.shady.myapplication.fragment.Add_Medication_Fragment;
import com.example.shady.myapplication.fragment.AfternoonFragment;
import com.example.shady.myapplication.fragment.ContactUsFragment;
import com.example.shady.myapplication.fragment.EveningFragment;
import com.example.shady.myapplication.fragment.MorrinngFragment;
import com.example.shady.myapplication.fragment.EditFragment;
import com.example.shady.myapplication.fragment.MainFragment;
import com.example.shady.myapplication.R;
import com.example.shady.myapplication.fragment.NightFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.yalantis.contextmenu.lib.ContextMenuDialogFragment;
import com.yalantis.contextmenu.lib.MenuObject;
import com.yalantis.contextmenu.lib.MenuParams;
import com.yalantis.contextmenu.lib.interfaces.OnMenuItemClickListener;
import com.yalantis.contextmenu.lib.interfaces.OnMenuItemLongClickListener;

import java.util.ArrayList;
import java.util.List;

public class DrugsActivity extends AppCompatActivity  implements OnMenuItemClickListener, OnMenuItemLongClickListener {
    private ContextMenuDialogFragment mMenuDialogFragment;
    private Toolbar mToolbar;
    private TextView mToolBarTextView;
    private FragmentManager mFragmentManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drugs);


        mFragmentManager = getSupportFragmentManager();
        initToolbar();
        initMenuFragment();

        // Get the ViewPager and set it's PagerAdapter so that it can display items
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        PagerAdapter pagerAdapter =

                new PagerAdapter(getSupportFragmentManager(), DrugsActivity.this);
        viewPager.setAdapter(pagerAdapter);

        // Give the TabLayout the ViewPager
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);

        // Iterate over all tabs and set the custom view
        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            tab.setCustomView(pagerAdapter.getTabView(i));
        }

    }



    private void initMenuFragment() {
        MenuParams menuParams = new MenuParams();
        menuParams.setActionBarSize((int) getResources().getDimension(R.dimen.tool_bar_height));
        menuParams.setMenuObjects(getMenuObjects());
        menuParams.setClosableOutside(false);
        mMenuDialogFragment = ContextMenuDialogFragment.newInstance(menuParams);
        mMenuDialogFragment.setItemClickListener(this);
        mMenuDialogFragment.setItemLongClickListener(this);
    }

    private List<MenuObject> getMenuObjects() {


        List<MenuObject> menuObjects = new ArrayList<>();

        MenuObject close = new MenuObject();
        close.setResource(R.drawable.icn_close);

        MenuObject home = new MenuObject("  Time line");
        home.setResource(R.drawable.home);

        MenuObject send = new MenuObject("   Edit profile");
        send.setResource(R.drawable.user_profil);

        MenuObject like = new MenuObject("  Add Medecines");
        Bitmap b = BitmapFactory.decodeResource(getResources(), R.drawable.add_medecin);
        like.setBitmap(b);

        MenuObject addFr = new MenuObject("   About US");
        BitmapDrawable bd = new BitmapDrawable(getResources(),
                BitmapFactory.decodeResource(getResources(), R.drawable.about_us));
        addFr.setDrawable(bd);

        MenuObject addFav = new MenuObject("  Contact US ");
        addFav.setResource(R.drawable.contactus);

        MenuObject block = new MenuObject("   Log out");
        block.setResource(R.drawable.logout);


        menuObjects.add(close);
        menuObjects.add(home);
        menuObjects.add(send);
        menuObjects.add(like);
        menuObjects.add(addFr);
        menuObjects.add(addFav);
        menuObjects.add(block);
        return menuObjects;
    }

    private void initToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolBarTextView = (TextView) findViewById(R.id.text_toolbar_title);
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        mToolBarTextView.setText("Today");
    }





    @Override
    public void onResume()
    {
        super.onResume();
    }



    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.context_menu:
                if (mFragmentManager.findFragmentByTag(ContextMenuDialogFragment.TAG) == null) {
                    mMenuDialogFragment.show(mFragmentManager, ContextMenuDialogFragment.TAG);
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (mMenuDialogFragment != null && mMenuDialogFragment.isAdded()) {
            mMenuDialogFragment.dismiss();
        } else {
            finish();
        }
    }

    @Override
    public void onMenuItemClick(View clickedView, int position) {
        Toast.makeText(this, "Clicked on position: " + position, Toast.LENGTH_SHORT).show();


        if (position == 1) {
            MainFragment fragment = new MainFragment();

            if (fragment != null) {
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.container, fragment);
                ft.commit();
            }

        }
        if (position == 2) {

            EditFragment fragment = new EditFragment();
            if (fragment != null) {
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.container, fragment);
                ft.commit();
            }

        }
        if (position == 3) {
            Add_Medication_Fragment fragment = new Add_Medication_Fragment();


            if (fragment != null) {
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.container, fragment);
                ft.commit();
            }
        }
        if (position == 4) {
            AboutUsFragment fragment = new AboutUsFragment();

            if (fragment != null) {
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.container, fragment);
                ft.commit();
            }
        }
        if (position == 5) {


            ContactUsFragment fragment = new ContactUsFragment();

            if (fragment != null) {
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.container, fragment);
                ft.commit();
            }

        }
        if (position == 6) {


            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(getApplicationContext(),LoginActivity.class));
            finish();
            Toast.makeText(this, "Log Out", Toast.LENGTH_SHORT).show();
//            Intent intent = new Intent(this, LoginActivity.class);
//            startActivity(intent);


        }


    }

    @Override
    public void onMenuItemLongClick(View clickedView, int position) {
        Toast.makeText(this, "Long clicked on position: " + position, Toast.LENGTH_SHORT).show();

    }

    class PagerAdapter extends FragmentPagerAdapter {

        String tabTitles[] = new String[] { "Morning", "Afternoon", "Evening","Night" };
        Context context;

        public PagerAdapter(FragmentManager fm, Context context) {
            super(fm);
            this.context = context;
        }

        @Override
        public int getCount() {
            return tabTitles.length;
        }

        @Override
        public Fragment getItem(int position) {

            switch (position) {
                case 0:
                    return new MorrinngFragment();
                case 1:
                    return new AfternoonFragment();
                case 2:
                    return new EveningFragment();
                case 3:
                    return new NightFragment();
            }

            return null;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            // Generate title based on item position
            return tabTitles[position];
        }

        public View getTabView(int position) {
            View tab = LayoutInflater.from(DrugsActivity.this).inflate(R.layout.custom_tab, null);
            TextView tv = (TextView) tab.findViewById(R.id.custom_text);
            tv.setText(tabTitles[position]);
            return tab;
        }

    }



}