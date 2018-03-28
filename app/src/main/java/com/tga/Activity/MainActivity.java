package com.tga.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.IdRes;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.Query;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;
import com.tga.R;
import com.tga.fragment.AboutUs;
import com.tga.fragment.ContactUs;
import com.tga.fragment.Favourites;
import com.tga.fragment.Home;
import com.tga.fragment.Plans;
import com.tga.fragment.Privacy;
import com.tga.fragment.Profile;
import com.tga.fragment.Settings;
import com.tga.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    BottomBar bottomBar;
    FrameLayout frameLayout;
    private NavigationView navigationView;
    private DrawerLayout drawer;
    private View navHeader;
    // flag to load home fragment when user presses back key
    private boolean shouldLoadHomeFragOnBackPress = true;
    private Handler mHandler;
    private static final String TAG_HOME = "TGA";
    private static final String TAG_FAV = "favourites";
    private static final String TAG_SETTINGS = "settings";
    private static final String TAG_PRIVACY = "privacy";
    private static final String TAG_CONTACTUS = "contact us";
    private static final String TAG_ABOUTUS = "about us";
    public static String CURRENT_TAG = TAG_HOME;
    public static int navItemIndex = 0;
//Ahmed Elewa
    private ImageView imgMenu;
    private int selectedTabId;
    private TextView txtSearch;
    private ImageView imgSearch;
    private Firebase myFirebaseRef;
    private TextView txtUserName;
//    private Toolbar toolbar;
//    private String[] activityTitles;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
        mAuth = FirebaseAuth.getInstance();
        drawer = (DrawerLayout)findViewById(R.id.drawer_layout);
        navigationView = (NavigationView)findViewById(R.id.nav_view);
        myFirebaseRef = new Firebase("https://tgassistant-437b6.firebaseio.com/users/");


        // Navigation view header
//        activityTitles = getResources().getStringArray(R.array.nav_item_activity_titles);
        mHandler = new Handler();
        // load nav menu header data
        navHeader = navigationView.getHeaderView(0);
        txtUserName = (TextView)navHeader.findViewById(R.id.txtName);
        loadNavHeader();

        // initializing navigation menu
        setUpNavigationView();

        if (savedInstanceState == null) {
            navItemIndex = 0;
            CURRENT_TAG = TAG_HOME;
            loadHomeFragment();
        }

        frameLayout = (FrameLayout) findViewById(R.id.framelayout);


         bottomBar = (BottomBar) findViewById(R.id.bottombar);
        for (int i = 0; i < bottomBar.getTabCount(); i++) {
            bottomBar.getTabAtPosition(i).setGravity(Gravity.CENTER_VERTICAL);
        }
        frameLayout = (FrameLayout) findViewById(R.id.framelayout);
        bottomBar.setDefaultTab(R.id.home);
        imgMenu = (ImageView)findViewById(R.id.imgMenu) ;
        txtSearch = (TextView)findViewById(R.id.etxtSearch);
        imgSearch = (ImageView)findViewById(R.id.imgSearch);

        imgMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawer.openDrawer(Gravity.START);
            }
        });

        txtSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,Search.class);
                startActivity(intent);
            }
        });
        imgSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,Search.class);
                startActivity(intent);
            }
        });
        /*roughike bottombar library code is here*/

        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {

            @Override
            public void onTabSelected(@IdRes int tabId) {
             //   Fragment fragment = null;

//                if (getIntent() != null && getIntent().hasExtra("check")) {
//                    tabId = getIntent().getIntExtra("check", -1);
//                }
                switch (tabId) {
                    case R.id.home:
                        selectedTabId =R.id.home;
                        replace_fragment(new Home());

                        break;
                    case R.id.plans:
                        selectedTabId =R.id.plans;
                        replace_fragment(new Plans());

                        break;
                    case R.id.profile:
                        selectedTabId =R.id.profile;
                        replace_fragment(new Profile());

                        break;

//                    case R.id.favorite:
//                        selectedTabId =R.id.favorite;
//                       replace_fragment(new Home());
//                        break;
//                    case R.id.more:
//                        selectedTabId =R.id.more;
//                        replace_fragment(new Home());

//                        break;

                }


            }
        });


    }

    private void loadHomeFragment() {
        // selecting appropriate nav menu item
        selectNavMenu();


        // if user select the current navigation menu again, don't do anything
        // just close the navigation drawer
        if (getSupportFragmentManager().findFragmentByTag(CURRENT_TAG) != null) {
            drawer.closeDrawers();

            // show or hide the fab button
            return;
        }

        // Sometimes, when fragment has huge data, screen seems hanging
        // when switching between navigation menus
        // So using runnable, the fragment is loaded with cross fade effect
        // This effect can be seen in GMail app
        Runnable mPendingRunnable = new Runnable() {
            @Override
            public void run() {
                // update the main content by replacing fragments
                Fragment fragment = getHomeFragment();
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                        android.R.anim.fade_out);
                fragmentTransaction.replace(R.id.framelayout, fragment, CURRENT_TAG);
                fragmentTransaction.commitAllowingStateLoss();
            }
        };

        // If mPendingRunnable is not null, then add to the message queue
        if (mPendingRunnable != null) {
            mHandler.post(mPendingRunnable);
        }

        //Closing drawer on item click
        drawer.closeDrawers();

        // refresh toolbar menu
        invalidateOptionsMenu();
    }

    private void loadNavHeader() {

        final String uid = mAuth.getCurrentUser().getUid();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        Query query2 = database.getReference("users").orderByChild("id").equalTo(uid);
        query2.addListenerForSingleValueEvent(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(com.google.firebase.database.DataSnapshot snapshot) {
                // do some stuff once

                HashMap<String, User> results = snapshot.getValue(new GenericTypeIndicator<HashMap<String, User>>() {
                });

                List<User> posts = new ArrayList<>(results.values());

                for (User post : posts) {
                    txtUserName.setText(post.getName());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private Fragment getHomeFragment() {
        switch (navItemIndex) {
            case 0:
                // home
                Home home = new Home();
                return home;
            case 1:
                Favourites favourites = new Favourites();
                return favourites;
            case 2:
                Settings settings = new Settings();
                return settings;
            case 3:
                Privacy privacy = new Privacy();
                return privacy;
            case 4:
                ContactUs contactUs =new ContactUs();
                return contactUs;
            case 5:
                AboutUs aboutUs = new AboutUs();
                return aboutUs;


            default:
                return new Home();
        }
    }

//    private void setToolbarTitle() {
//        getSupportActionBar().setTitle(activityTitles[navItemIndex]);
//    }

    private void selectNavMenu() {
        navigationView.getMenu().getItem(navItemIndex).setChecked(true);
    }

    private void setUpNavigationView() {
        //Profile Navigation View Item Selected Listener to handle the item click of the navigation menu
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            // This method will trigger on item Click of navigation menu
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {

                //Check to see which item was being clicked and perform appropriate action
                switch (menuItem.getItemId()) {
                    //Replacing the main content with ContentFragment Which is our Inbox View;
                    case R.id.home:
                        navItemIndex = 0;
                        CURRENT_TAG = TAG_HOME;
                        break;
                    case R.id.fav:
                        navItemIndex = 1;
                        CURRENT_TAG = TAG_FAV;
                        break;
                    case R.id.settings:
                        navItemIndex = 2;
                        CURRENT_TAG = TAG_SETTINGS;
                        break;
                    case R.id.privacy:
                        navItemIndex = 3;
                        CURRENT_TAG = TAG_PRIVACY;
                        break;
                    case R.id.contact:
                        navItemIndex = 4;
                        CURRENT_TAG = TAG_CONTACTUS;
                        break;
                    case R.id.about:
                        navItemIndex = 5;
                        CURRENT_TAG = TAG_ABOUTUS;
                        break;
                    case R.id.logout:
                        navItemIndex = 6;
                        mAuth.signOut();
                        Intent intent = new Intent(getApplicationContext(), Login.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();
                        drawer.closeDrawers();
                        return true;

                    default:
                        navItemIndex = 0;
                }

                //Checking if the item is in checked state or not, if not make it in checked state
                if (menuItem.isChecked()) {
                    menuItem.setChecked(false);
                } else {
                    menuItem.setChecked(true);
                }
                menuItem.setChecked(true);

                loadHomeFragment();

                return true;
            }
        });

//        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.openDrawer, R.string.closeDrawer) {
//
//            @Override
//            public void onDrawerClosed(View drawerView) {
//                // Code here will be triggered once the drawer closes as we dont want anything to happen so we leave this blank
//                super.onDrawerClosed(drawerView);
//            }
//
//            @Override
//            public void onDrawerOpened(View drawerView) {
//                // Code here will be triggered once the drawer open as we dont want anything to happen so we leave this blank
//                super.onDrawerOpened(drawerView);
//            }
//        };
//
//        //Profile the actionbarToggle to drawer layout
//        drawer.setDrawerListener(actionBarDrawerToggle);
//
//        //calling sync state is necessary or else your hamburger icon wont show up
//        actionBarDrawerToggle.syncState();
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawers();
            return;
        }

        // This code loads home fragment when back key is pressed
        // when user is in other fragment than home
        if (shouldLoadHomeFragOnBackPress) {
            // checking if user is on other navigation menu
            // rather than home
            if (navItemIndex != 0) {
                navItemIndex = 0;
                CURRENT_TAG = TAG_HOME;
                loadHomeFragment();
                return;
            }
            if(navItemIndex == 0){
                finish();
            }
        }

        super.onBackPressed();
    }


    public void replace_fragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.framelayout, fragment);
        transaction.commit();
    }



    //////////////////////////////////////////////////////////




}
