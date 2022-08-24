package com.advira.advirafarm.buyer.ui.guest;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.advira.advirafarm.buyer.R;
import com.advira.advirafarm.buyer.constants.IConsts;
import com.advira.advirafarm.buyer.ui.address.AddressfromMap;
import com.advira.advirafarm.buyer.ui.cart.CartActivity;
import com.advira.advirafarm.buyer.ui.cart.ChooseAddressList;
import com.advira.advirafarm.buyer.ui.category.CategoryFragment;
import com.advira.advirafarm.buyer.ui.navigation.ChooseAddressListNav;
import com.advira.advirafarm.buyer.ui.notification.NotificationListActivity;
import com.advira.advirafarm.buyer.ui.onetaplogin.OneTapLogin;
import com.advira.advirafarm.buyer.ui.product.HomeFragmentB2B;
import com.advira.advirafarm.buyer.ui.product.SearchActivity;
import com.advira.advirafarm.buyer.ui.product.Search_one;
import com.advira.advirafarm.buyer.utility.SharedPrefUtil;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivityGuestNav extends AppCompatActivity implements IConsts, NavigationView.OnNavigationItemSelectedListener{

    public static TextView tv_cartcount, tv_header, tv_deliverto;
    public TextView tv_headernew;
    public static TextView tv_cart_counter, text;
    String profilemode = "B2C";
    AlertDialog.Builder builder;
    private Toolbar toolbar;

    /* private SwitchCompat darkModeSwitch;*/
    private DrawerLayout drawerLayout;
    private FrameLayout frameLayout;
    private NavigationView navigationView;
    private BottomNavigationView bottomnavview;
    private CircleImageView profile_image;
    private Context mContext;
    private RelativeLayout rl_cart, rl_search, rl_header2,rl_mobile, rl_notification, rl_delivpinto, rl_profile;
    private TextView tv_username;
    private TextView tv_mobile;
    private ImageView iv_headerlogo, iv_headerlogo2, img_notification, iv_locationlogo,iv_memimg;

    private TextView tv_accountype;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_nav);

        //String cartcount = SharedPrefUtil.getCartCount(mContext, SHARED_PREF_CARTCOUNT, "");

        initializeViews();
        //text.setText(cartcount);
        toggleDrawer();
        initializeDefaultFragment(savedInstanceState, 0);

        rl_mobile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent();
                //i.setClass(MainActivityGuestNav.this, LoginActivity.class);
                i.setClass(MainActivityGuestNav.this, OneTapLogin.class);
                startActivity(i);

            }
        });

        rl_notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent();
                i.setClass(MainActivityGuestNav.this, NotificationListActivity.class);
                startActivity(i);

            }
        });
    }

    /**
     * Initialize all widgets
     */
    private void initializeViews() {

        mContext = MainActivityGuestNav.this;
        String name = SharedPrefUtil.getUserName(mContext, SHARED_PREF_UserName, "");
        String mobile = SharedPrefUtil.getUserMobile(mContext, SHARED_PREF_UserMobile, "");
        String profilepic = SharedPrefUtil.getUserProfilePic(mContext, SHARED_PREF_ProfilePic, "");
        String cartcount = SharedPrefUtil.getCartCount(mContext, SHARED_PREF_CARTCOUNT, "");

        rl_cart = findViewById(R.id.rl_cart);
        rl_profile= findViewById(R.id.rl_profile);
        rl_search = findViewById(R.id.rl_search);
        rl_header2 = findViewById(R.id.rl_header2);
        tv_header = findViewById(R.id.tv_header);
        tv_headernew = findViewById(R.id.tv_headernew);
        tv_cartcount = findViewById(R.id.tv_cartcount);


        iv_headerlogo = findViewById(R.id.iv_headerlogo);
        iv_headerlogo2 = findViewById(R.id.iv_headerlogo2);

        iv_locationlogo = findViewById(R.id.iv_deliv);

        tv_deliverto = findViewById(R.id.tv_deliverto);

        rl_delivpinto = findViewById(R.id.rl_delivpinto);
        rl_delivpinto.setVisibility(View.VISIBLE);

        img_notification = findViewById(R.id.img_notification);
        rl_notification = findViewById(R.id.rl_notification);
        rl_cart.setVisibility(View.GONE);
        //rl_search.setVisibility(View.GONE);
        tv_deliverto.setVisibility(View.VISIBLE);
        img_notification.setVisibility(View.VISIBLE);
        iv_locationlogo.setVisibility(View.VISIBLE);


        toolbar = findViewById(R.id.toolbar_id);
        toolbar.setTitle(R.string.toolbar_title);
        /*toolbar.setNavigationIcon(R.drawable.menu_icon);*/
        setSupportActionBar(toolbar);
        drawerLayout = findViewById(R.id.drawer_layout_id);
        frameLayout = findViewById(R.id.framelayout_id);
        navigationView = findViewById(R.id.navigationview_id);
        navigationView.setNavigationItemSelectedListener(this);

        bottomnavview=findViewById(R.id.bottom_navigatin_view);
        bottomnavview.setSelectedItemId(R.id.home_btmnav);
        bottomnavview.setItemIconTintList(null);

        BottomNavigationMenuView mbottomNavigationMenuView =
                (BottomNavigationMenuView) bottomnavview.getChildAt(0);

        View view = mbottomNavigationMenuView.getChildAt(4);

        BottomNavigationItemView itemView = (BottomNavigationItemView) view;

        /*View cart_badge = LayoutInflater.from(this)
                .inflate(R.layout.notification_badge,
                        mbottomNavigationMenuView, false);*/

        LayoutInflater inflate=(LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View cart_badge = inflate.inflate(R.layout.notification_badge,mbottomNavigationMenuView, false);
        //cart_badge.setVisibility(View.VISIBLE);
        text=(TextView) cart_badge.findViewById(R.id.notifications_badge);
        text.setText(cartcount);

        /*if(profilemode.equalsIgnoreCase("B2B")){
            String cartcount = SharedPrefUtil.getCartCountB2B(mContext, SHARED_PREF_CARTCOUNTB2B, "");
            text.setText(cartcount); }
        else {
            String cartcount = SharedPrefUtil.getCartCount(mContext, SHARED_PREF_CARTCOUNT, "");
            text.setText(cartcount); }
*/
        //((TextView) cart_badge.findViewById(R.id.notifications_badge)).setText("5");

        itemView.addView(cart_badge);

        bottomnavview.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                profilemode = SharedPrefUtil.getProfileMode(mContext, SHARED_PREF_ProfileMode, "B2C");
                String cartcount = SharedPrefUtil.getCartCount(mContext, SHARED_PREF_CARTCOUNT, "");//remove 0
                switch (item.getItemId()){

                    case R.id.category:
                        cart_badge.setVisibility(View.VISIBLE);
                        text.setText(cartcount);
                        if (profilemode.equalsIgnoreCase("B2C")) {

                            rl_cart.setVisibility(View.GONE);
                            rl_profile.setVisibility(View.VISIBLE);
                            tv_cartcount.setVisibility(View.GONE);

                        } else {
                            rl_cart.setVisibility(View.GONE);
                            rl_profile.setVisibility(View.VISIBLE);
                            tv_cartcount.setVisibility(View.GONE);

                        }


                        getSupportFragmentManager().beginTransaction().replace(R.id.framelayout_id, new CategoryFragment())
                                .commit();

                        rl_search.setVisibility(View.VISIBLE);
                        rl_header2.setVisibility(View.GONE);
                        //text.setText(cartcount);
                        tv_header.setText("");
                        iv_headerlogo2.setVisibility(View.VISIBLE);
                        iv_headerlogo.setVisibility(View.GONE);
                        break;
                    case R.id.home_btmnav:
                        SharedPrefUtil.setProfileMode(mContext, SHARED_PREF_ProfileMode, "B2C");
                        getSupportFragmentManager().beginTransaction().replace(R.id.framelayout_id, new HomeFragmentGuest())
                                .commit();
                        rl_cart.setVisibility(View.GONE);
                        tv_cartcount.setVisibility(View.GONE);
                        text.setText(cartcount);
                        rl_profile.setVisibility(View.VISIBLE);
                        rl_search.setVisibility(View.GONE);
                        rl_header2.setVisibility(View.GONE);
                        tv_header.setText("");
                        iv_headerlogo2.setVisibility(View.VISIBLE);
                        iv_headerlogo.setVisibility(View.GONE);
                        break;
                    case R.id.tokri:
                        String usertype = SharedPrefUtil.getUserActive(mContext, SHARED_PREF_UserActive, "guest");

                       /* if (usertype.equalsIgnoreCase("guest")) {
                            startActivity(new Intent(getApplicationContext(),LoginActivity.class));
                            overridePendingTransition(0,0);
                            return true;
                        } else {*/
                            startActivity(new Intent(getApplicationContext(),OneTapLogin.class));
                            overridePendingTransition(0,0);
                            return true;

                        //}
                    case R.id.home_wallet:
                        startActivity(new Intent(getApplicationContext(), OneTapLogin.class));
                        overridePendingTransition(0,0);
                        break;
                    case R.id.home_subscription:

                        startActivity(new Intent(getApplicationContext(), OneTapLogin.class));
                        overridePendingTransition(0,0);
                        return true;
                }

                //getSupportFragmentManager().beginTransaction().replace(R.id.framelayout_id, new HomeFragmentGuest()).commit();

                return true;
            }
        });

        profile_image = (CircleImageView) navigationView.getHeaderView(0).findViewById(R.id.profile_image);
        tv_username = (TextView) navigationView.getHeaderView(0).findViewById(R.id.tv_username);
        tv_mobile = (TextView) navigationView.getHeaderView(0).findViewById(R.id.tv_mobile);
        rl_mobile = (RelativeLayout) navigationView.getHeaderView(0).findViewById(R.id.rl_mobile);
        tv_accountype = (TextView) navigationView.getHeaderView(0).findViewById(R.id.tv_accountype);
        iv_memimg=(ImageView) navigationView.getHeaderView(0).findViewById(R.id.iv_memimg);
        iv_memimg.setVisibility(View.GONE);


        //text.setText(cartcount);
        tv_header.setVisibility(View.VISIBLE);

        tv_username.setText("Welcome Guest,");
        tv_mobile.setText("Login/Sign Up");

        profilemode = SharedPrefUtil.getProfileMode(mContext, SHARED_PREF_ProfileMode, "B2B");
        //tv_accountype.setText("Mode-Consumer");

        if (profilemode.equalsIgnoreCase("B2B")) {
            tv_accountype.setText("Mode-Business");
            tv_accountype.setVisibility(View.GONE);
        } else {
            tv_accountype.setText("Mode-Consumer");
            tv_accountype.setVisibility(View.GONE);
        }

        if (profilepic.length() > 5) {
            Picasso.with(mContext).load(R.drawable.image_not_available).placeholder(R.drawable.progress_animation).into(profile_image);
        }


        rl_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent();
                i.setClass(MainActivityGuestNav.this, CartActivity.class);
                startActivity(i);

            }
        });
        rl_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getSupportFragmentManager().beginTransaction().replace(R.id.framelayout_id, new MyAccountfragmentGuest())
                        .commit();

            }
        });

        rl_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent();
                //i.setClass(MainActivityGuestNav.this, SearchActivity.class);
                i.setClass(MainActivityGuestNav.this, Search_one.class);
                startActivity(i);

            }
        });

        rl_delivpinto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent i = new Intent();
                i.setClass(mContext, AddressfromMap.class);
                startActivity(i);

               /* getSupportFragmentManager().beginTransaction().replace(R.id.framelayout_id, new AddressListFragment()).addToBackStack(null)
                        .commit();
                rl_search.setVisibility(View.VISIBLE);
                rl_profile.setVisibility(View.VISIBLE);
                rl_header2.setVisibility(View.GONE);
                //tv_header.setText("My Address");
                iv_headerlogo2.setVisibility(View.GONE);
                iv_headerlogo.setVisibility(View.VISIBLE);*/


            }
        });


    }


    /**
     * Checks if the savedInstanceState is null - onCreate() is ran
     * If so, display fragment of navigation drawer menu at position itemIndex and
     * set checked status as true
     *
     * @param savedInstanceState
     * @param itemIndex
     */
    private void initializeDefaultFragment(Bundle savedInstanceState, int itemIndex) {

        //navigationView.getMenu().getItem(0).setVisible(false);
        //navigationView.getMenu().getItem(1).setVisible(false);
        navigationView.getMenu().getItem(2).setVisible(false);
        navigationView.getMenu().getItem(3).setVisible(false);
        navigationView.getMenu().getItem(4).setVisible(false);
        navigationView.getMenu().getItem(5).setVisible(false);
        navigationView.getMenu().getItem(6).setVisible(false);
        navigationView.getMenu().getItem(7).setVisible(false);
        navigationView.getMenu().getItem(8).setVisible(false);
        navigationView.getMenu().getItem(9).setVisible(false);
        //navigationView.getMenu().getItem(10).setVisible(false);
        //navigationView.getMenu().getItem(11).setVisible(false);
        //navigationView.getMenu().getItem(12).setVisible(false);
        //navigationView.getMenu().getItem(13).setVisible(false);
        navigationView.getMenu().getItem(14).setVisible(false);
        //navigationView.getMenu().getItem(10).setTitle("Login");

        /*if(profilemode.equalsIgnoreCase("B2C"))
        {

            navigationView.getMenu().getItem(2).setVisible(false);
        }*/
       /* else
        {

            navigationView.getMenu().getItem(2).setVisible(true);
            navigationView.getMenu().getItem(3).setVisible(false);
        }*/
        if (savedInstanceState == null) {
            MenuItem menuItem = navigationView.getMenu().getItem(itemIndex).setChecked(true);
            onNavigationItemSelected(menuItem);

        }


    }

    /**
     * Creates an instance of the ActionBarDrawerToggle class:
     * 1) Handles opening and closing the navigation drawer
     * 2) Creates a hamburger icon in the toolbar
     * 3) Attaches listener to open/close drawer on icon clicked and rotates the icon
     */
    private void toggleDrawer() {
        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.menu_icon);
    }

    @Override
    public void onBackPressed() {
        //Checks if the navigation drawer is open -- If so, close it
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        // If drawer is already close -- Do not override original functionality

        super.onBackPressed();

    }

    //private BottomNavigationView.OnNavigationItemSelectedListener.navlistner=new View

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        String cartcount = SharedPrefUtil.getCartCount(mContext, SHARED_PREF_CARTCOUNT, "");
        text.setText(cartcount);
        /*if(profilemode.equalsIgnoreCase("B2B")){
            String cartcount = SharedPrefUtil.getCartCountB2B(mContext, SHARED_PREF_CARTCOUNTB2B, "");
            text.setText(cartcount); }
        else {
            String cartcount = SharedPrefUtil.getCartCount(mContext, SHARED_PREF_CARTCOUNT, "");
            text.setText(cartcount); }*/

        switch (menuItem.getItemId()) {
            case R.id.nav_home_id:
                SharedPrefUtil.setProfileMode(mContext, SHARED_PREF_ProfileMode, "B2C");
                //profilemode = SharedPrefUtil.getProfileMode(mContext, SHARED_PREF_ProfileMode, "B2C");
                rl_cart.setVisibility(View.GONE);
                rl_profile.setVisibility(View.VISIBLE);
                tv_cartcount.setVisibility(View.GONE);
                /*if (profilemode.equalsIgnoreCase("B2B")) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.framelayout_id, new HomeFragmentB2B())
                            .commit();
                    tv_accountype.setText("Mode-Business");
                    tv_accountype.setVisibility(View.GONE);

                    //navigationView.getMenu().getItem(2).setVisible(false);
                    //navigationView.getMenu().getItem(3).setVisible(true);

                    rl_cart.setVisibility(View.GONE);
                    rl_profile.setVisibility(View.VISIBLE);
                    tv_cartcount.setVisibility(View.INVISIBLE);

                } else {
*/                    getSupportFragmentManager().beginTransaction().replace(R.id.framelayout_id, new HomeFragmentGuest())
                            .commit();
                    tv_accountype.setText("Mode-Consumer");
                    tv_accountype.setVisibility(View.GONE);
                    //navigationView.getMenu().getItem(2).setVisible(true);
                    //navigationView.getMenu().getItem(3).setVisible(false);
                    rl_cart.setVisibility(View.GONE);
                    rl_profile.setVisibility(View.VISIBLE);
                    tv_cartcount.setVisibility(View.GONE);
                //}



                rl_search.setVisibility(View.GONE);
                rl_header2.setVisibility(View.GONE);
                tv_header.setText("");
                iv_headerlogo2.setVisibility(View.VISIBLE);
                iv_headerlogo.setVisibility(View.GONE);
                closeDrawer();
                break;

            case R.id.nav_shopbybusiness:

                SharedPrefUtil.setProfileMode(mContext, SHARED_PREF_ProfileMode, "B2B");
                getSupportFragmentManager().beginTransaction().replace(R.id.framelayout_id, new HomeFragmentB2B())
                        .commit();
                //String cartcount1 = SharedPrefUtil.getCartCountB2B(mContext, IConsts.SHARED_PREF_CARTCOUNTB2B, "");
                tv_accountype.setText("Mode-Business");
                tv_accountype.setVisibility(View.GONE);
                //navigationView.getMenu().getItem(2).setVisible(false);
                rl_search.setVisibility(View.GONE);
                rl_header2.setVisibility(View.GONE);
                rl_cart.setVisibility(View.INVISIBLE);
                //text.setText(cartcount);
                /*String cartcount = SharedPrefUtil.getCartCount(mContext, SHARED_PREF_CARTCOUNT, "");
                text.setText(cartcount);*/
                tv_header.setText("");
                iv_headerlogo2.setVisibility(View.VISIBLE);
                iv_headerlogo.setVisibility(View.GONE);

                /*if(profilemode.equalsIgnoreCase("B2B")){
                    String cartcount = SharedPrefUtil.getCartCountB2B(mContext, SHARED_PREF_CARTCOUNTB2B, "");
                    text.setText(cartcount); }
                else {
                    String cartcount = SharedPrefUtil.getCartCount(mContext, SHARED_PREF_CARTCOUNT, "");
                    text.setText(cartcount); }
*/

                closeDrawer();
                break;
            case R.id.nav_shopbyconsumer:
                //navigationView.getMenu().getItem(2).setVisible(false);
                SharedPrefUtil.setProfileMode(mContext, SHARED_PREF_ProfileMode, "B2C");
                getSupportFragmentManager().beginTransaction().replace(R.id.framelayout_id, new HomeFragmentGuest())
                        .commit();

                tv_accountype.setText("Mode-Consumer");
                tv_accountype.setVisibility(View.GONE);
                rl_search.setVisibility(View.GONE);
                rl_header2.setVisibility(View.GONE);
                rl_cart.setVisibility(View.VISIBLE);
                tv_cartcount.setVisibility(View.GONE);
                tv_header.setText("");
                iv_headerlogo2.setVisibility(View.VISIBLE);
                iv_headerlogo.setVisibility(View.GONE);

                //navigationView.getMenu().getItem(2).setVisible(true);
                //navigationView.getMenu().getItem(3).setVisible(false);

                closeDrawer();
                break;

            case R.id.nav_category_id:

                profilemode = SharedPrefUtil.getProfileMode(mContext, SHARED_PREF_ProfileMode, "B2B");
                //String cartcount = SharedPrefUtil.getCartCount(mContext, SHARED_PREF_CARTCOUNT, "");
                //text.setText(cartcount);
                if (profilemode.equalsIgnoreCase("B2B")) {

                    rl_cart.setVisibility(View.GONE);
                    tv_cartcount.setVisibility(View.GONE);
                } else {
                    rl_cart.setVisibility(View.GONE);
                    tv_cartcount.setVisibility(View.GONE);
                }


                getSupportFragmentManager().beginTransaction().replace(R.id.framelayout_id, new CategoryFragment())
                        .commit();

                rl_search.setVisibility(View.VISIBLE);
                rl_header2.setVisibility(View.GONE);
                tv_header.setText("");
                iv_headerlogo2.setVisibility(View.VISIBLE);
                iv_headerlogo.setVisibility(View.GONE);
                closeDrawer();
                break;

            case R.id.nav_accountsettings_id:

                getSupportFragmentManager().beginTransaction().replace(R.id.framelayout_id, new MyAccountSettingFragmentGuest())
                        .commit();
                rl_search.setVisibility(View.VISIBLE);
                rl_header2.setVisibility(View.GONE);
                //tv_header.setText("Settings");
                iv_headerlogo2.setVisibility(View.GONE);
                iv_headerlogo.setVisibility(View.VISIBLE);
                closeDrawer();
                break;

            case R.id.nav_customer_id:
                /*getSupportFragmentManager().beginTransaction().replace(R.id.framelayout_id, new CustomerService())
                        .commit();
                rl_search.setVisibility(View.VISIBLE);
                rl_header2.setVisibility(View.GONE);
                //tv_header.setText("Settings");
                iv_headerlogo2.setVisibility(View.GONE);
                iv_headerlogo.setVisibility(View.VISIBLE);
                closeDrawer();
                break;*/
                msgcustomercare();
                closeDrawer();
                break;

            case R.id.nav_share_id:
                rl_search.setVisibility(View.VISIBLE);
                rl_header2.setVisibility(View.GONE);
                iv_headerlogo2.setVisibility(View.GONE);
                iv_headerlogo.setVisibility(View.VISIBLE);

                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT,
                        " Hi, I am using ADVIRA. I like this and I want you to check it out: https://bit.ly/3DRpXJE"/* + BuildConfig.APPLICATION_ID*/);
                sendIntent.setType("text/plain");
                startActivity(Intent.createChooser(sendIntent, "Share via"));
                //startActivity(sendIntent);
                closeDrawer();
                break;

            case R.id.nav_customercall_id:
                callcustomercare();
                closeDrawer();
                break;

            case R.id.nav_logout_id:
                login();
                deSelectCheckedState();
                closeDrawer();
                break;
        }
        return true;
    }

    private void callcustomercare() {
            Intent callIntent = new Intent(Intent.ACTION_DIAL);
            callIntent.setData(Uri.parse("tel:+911292982250"));
            startActivity(callIntent);

    }

    private void msgcustomercare() {
        String digits = "\\d+";
        String mob_num = "9667072941";
        if (mob_num.matches(digits))
        {
            try {
                //linking for whatsapp
                Uri uri = Uri.parse("https://wa.me/+91" + mob_num);
                Intent i = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(i);
            }
            catch (ActivityNotFoundException e){
                e.printStackTrace();
                //if you're in anonymous class pass context like "YourActivity.this"
                Toast.makeText(mContext, "WhatsApp not installed.", Toast.LENGTH_SHORT).show();
            }
        }



    }


    /**
     * Attach setOnCheckedChangeListener to the dark mode switch
     */
    private void login() {

        Intent i = new Intent();
        //i.setClass(MainActivityGuestNav.this, LoginActivity.class);
        i.setClass(MainActivityGuestNav.this, OneTapLogin.class);
        startActivity(i);
    }


    /**
     * Checks if the navigation drawer is open - if so, close it
     */
    private void closeDrawer() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
    }

    /**
     * Iterates through all the items in the navigation menu and deselects them:
     * removes the selection color
     */
    private void deSelectCheckedState() {
        int noOfItems = navigationView.getMenu().size();
        for (int i = 0; i < noOfItems; i++) {
            navigationView.getMenu().getItem(i).setChecked(false);
        }
    }


    @Override
    public void onRestart() {
        super.onRestart();

        String delivaddress = SharedPrefUtil.getHeaderAddress(mContext, SHARED_PREF_HeaderAddress, "Select Address");
        tv_deliverto.setText(delivaddress);

    }
}