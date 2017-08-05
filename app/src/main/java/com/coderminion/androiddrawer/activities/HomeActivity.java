package com.coderminion.androiddrawer.activities;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.coderminion.androiddrawer.R;
import com.coderminion.androiddrawer.fragments.GalleryFragment;
import com.coderminion.androiddrawer.fragments.ImportFragment;
import com.coderminion.androiddrawer.fragments.SlideShowFragment;
import com.coderminion.androiddrawer.fragments.ToolsFragment;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    private static final String IMPORT_FRAGMENT = "Import";
    private static final String GALLERY_FRAGMENT = "Gallery" ;
    private static final String SLIDESHOW_FRAGMENT = "Slide Show";
    private static final String TOOLS_FRAGMENT = "Tools";
    private static  String CURRENT_FRAGMENT = IMPORT_FRAGMENT;
    int navigationIndex = 0;

    Toolbar toolbar;
    Handler mHandler;

    String profilePicUrl="http://www.coderminion.com/wp-content/uploads/bb-plugin/cache/vaibhav-233x300-square.jpg";
    String background_img_url="http://api.coderminion.com/blue_background.png";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        toolbar = (Toolbar) findViewById(R.id.toolbar);
        mHandler = new Handler();


        loadTrivialViews();

        loadandRefreshFragment();
    }

    private void loadTrivialViews() {

        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View header = navigationView.getHeaderView(0);


        ImageView profilePic = (ImageView)header.findViewById(R.id.profilepic);
        TextView name = (TextView) header.findViewById(R.id.name);
        TextView email = (TextView) header.findViewById(R.id.emailid);


        Glide.with(this).load(profilePicUrl).apply(RequestOptions.circleCropTransform()).into(profilePic);
        name.setText("Vaibhav Kadam");
        email.setText("vaibhav.kadam45@live.com");

//        Glide is so cool that it can load background Image too.
        final LinearLayout nav_header_home = (LinearLayout)header.findViewById(R.id.nav_header_home);
        Glide.with(getApplicationContext()).load(background_img_url).into(new SimpleTarget<Drawable>() {
            @Override
            public void onResourceReady(Drawable resource, Transition<? super Drawable> transition) {
                nav_header_home.setBackground(resource);
            }
        });


        navigationView.setNavigationItemSelectedListener(this);
        //set 1st item selected
        navigationView.getMenu().getItem(0).setChecked(true);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        switch (id)
        {
            case R.id.nav_camera:
                CURRENT_FRAGMENT = IMPORT_FRAGMENT;
                navigationIndex = 0;
                break;
             case R.id.nav_gallery:
                CURRENT_FRAGMENT = GALLERY_FRAGMENT;
                navigationIndex = 1;
                break;
             case R.id.nav_slideshow:
                CURRENT_FRAGMENT = SLIDESHOW_FRAGMENT;
                navigationIndex = 2;
                break;
             case R.id.nav_manage:
                CURRENT_FRAGMENT = TOOLS_FRAGMENT;
                navigationIndex = 3;
                break;
             case R.id.nav_share:
                 Toast.makeText(getApplicationContext(),"Share selected",Toast.LENGTH_SHORT).show();
                break;
             case R.id.nav_send:
                 Toast.makeText(getApplicationContext(),"Send selected",Toast.LENGTH_SHORT).show();
                break;
             default: {
             }
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        loadandRefreshFragment();
        return true;
    }


    /***
     * Returns respected fragment that user
     * selected from navigation menu
     */
    private void loadandRefreshFragment() {

        Runnable mPendingRunnable = new Runnable() {
            @Override
            public void run() {
                Fragment fragment = getselectedFragment();
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
                fragmentTransaction.replace(R.id.contet_frame, fragment, CURRENT_FRAGMENT);
                fragmentTransaction.commitAllowingStateLoss();
            }
        };

        // If mPendingRunnable is not null, then add to the message queue
        mHandler.post(mPendingRunnable);
    }

    private Fragment getselectedFragment() {

        switch (navigationIndex) {
            case 0:
                // IMPORT
                ImportFragment importFragment = new ImportFragment();
                toolbar.setTitle(IMPORT_FRAGMENT);
                return importFragment;
            case 1:
                // GALLERY
                GalleryFragment galleryFragment = new GalleryFragment();
                toolbar.setTitle(GALLERY_FRAGMENT);
                return galleryFragment;
            case 2:
                // SLIDESHOW
                SlideShowFragment slideShowFragment = new SlideShowFragment();
                toolbar.setTitle(SLIDESHOW_FRAGMENT);
                return slideShowFragment;
            case 3:
                // TOOLS
                ToolsFragment toolsFragment = new ToolsFragment();
                toolbar.setTitle(TOOLS_FRAGMENT);
                return toolsFragment;

            default:
                return new ImportFragment();
        }
    }

}
