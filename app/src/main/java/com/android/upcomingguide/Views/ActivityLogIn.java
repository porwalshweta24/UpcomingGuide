package com.android.upcomingguide.Views;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.upcomingguide.Adapter.DrawerItemCustomAdapter;
import com.android.upcomingguide.Pojo.DataModel;
import com.android.upcomingguide.Pojo.UpcomingGuides;
import com.android.upcomingguide.R;

import com.android.upcomingguide.Utility.JsonDataParser;
import com.android.upcomingguide.Utility.Utils;
import com.android.upcomingguide.database.DBCartAdapter;
import com.android.upcomingguide.fragments.BaseFragment;
import com.android.upcomingguide.fragments.SettingsFragment;
import com.android.upcomingguide.fragments.UpcomingGuidesFragment;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.android.upcomingguide.Utility.Utils.showAlertBox;
import static com.android.upcomingguide.Utility.Utils.showLog;

/**
 * Created by shweta on 2/10/2017.
 */
public class ActivityLogIn extends AppCompatActivity implements BaseFragment.BackHandlerInterface {


    private String[] mNavigationDrawerItemTitles;
    @Bind(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;
    @Bind(R.id.left_drawer)
    ListView mDrawerList;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.layout_banner)
    public LinearLayout llCart;
    @Bind(R.id.cartCount)
    public TextView cartCount;
    @Bind(R.id.header_title)
    TextView headerTitle;

    CharSequence mDrawerTitle;
    CharSequence mTitle;
    public boolean isLoggedin=false;
    android.support.v7.app.ActionBarDrawerToggle mDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        setupToolbar();
        mTitle = mDrawerTitle = getTitle();
        mNavigationDrawerItemTitles= getResources().getStringArray(R.array.navigation_drawer_items_array);
        setupToolbar();

        DataModel[] drawerItem = new DataModel[2];
        drawerItem[0] = new DataModel(R.drawable.connect, "Settings");
        drawerItem[1] = new DataModel(R.drawable.fixtures, "Upcoming Guide");

        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setHomeButtonEnabled(true);

        DrawerItemCustomAdapter adapter = new DrawerItemCustomAdapter(this, R.layout.list_view_item_row, drawerItem);
        mDrawerList.setAdapter(adapter);
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
        mDrawerList.setItemChecked(0, true);
        getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, new SettingsFragment()).commit();

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        setupDrawerToggle();


    }
    private class DrawerItemClickListener implements ListView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
        }
    }

    public void deleteValuesAtLogOut()
    {
        DBCartAdapter dbCartAdapter = new DBCartAdapter(this);
        dbCartAdapter.deleteFromCart();
    }

    public void setValuesInCart()
    {
        DBCartAdapter dbCartAdapter = new DBCartAdapter(this);
        int count= dbCartAdapter.getCartValueCount();
        cartCount.setText(""+count);
    }

    private void selectItem(int position) {

        Fragment fragment = null;

        switch (position) {
            case 0:
                fragment = new SettingsFragment();
                headerTitle.setText(getString(R.string.action_settings));
                break;
            case 1:
                if(isLoggedin) {
                    UpcomingGuidesFragment upcomingGuidesFragment = new UpcomingGuidesFragment();
                    upcomingGuidesFragment.setArgumentUI(this);
                    fragment = upcomingGuidesFragment;
                    headerTitle.setText(getString(R.string.app_name));
                }else
                {
                    showAlertBox(this,getString(R.string.msg_loggin_required),null);
                }
                break;
            default:
                break;
        }
        if(isLoggedin)
            llCart.setVisibility(View.VISIBLE);
        else
            llCart.setVisibility(View.INVISIBLE);

        if (fragment != null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, fragment).commit();
            mDrawerList.setItemChecked(position, true);
            mDrawerList.setSelection(position);
            setTitle(mNavigationDrawerItemTitles[position]);
            mDrawerLayout.closeDrawer(mDrawerList);

        } else {
            showLog("MainActivity", "Error in creating fragment");
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getSupportActionBar().setTitle(mTitle);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    void setupToolbar(){
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    void setupDrawerToggle(){
        mDrawerToggle = new android.support.v7.app.ActionBarDrawerToggle(this,mDrawerLayout,toolbar,R.string.app_name, R.string.app_name);
        //This is necessary to change the icon of the Drawer Toggle upon state change.
        mDrawerToggle.syncState();
    }

    @Override
    public void setSelectedFragment(BaseFragment backHandledFragment) {

    }
@OnClick(R.id.layout_banner)
    void onCartProceedToCheckOut(View view)
{
    Utils.showToast(this,"checkout deal(s).");
}
}
