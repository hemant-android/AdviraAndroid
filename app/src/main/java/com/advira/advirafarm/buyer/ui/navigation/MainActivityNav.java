package com.advira.advirafarm.buyer.ui.navigation;

import android.Manifest;
import android.app.Activity;
import android.app.SearchManager;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Looper;
import android.os.ResultReceiver;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.advira.advirafarm.buyer.BuildConfig;
import com.advira.advirafarm.buyer.R;
import com.advira.advirafarm.buyer.constants.Constants;
import com.advira.advirafarm.buyer.constants.IConsts;
import com.advira.advirafarm.buyer.retrofurlconnection.RetrofitUrlConnection;
import com.advira.advirafarm.buyer.service.FetchAddressIntentService;
import com.advira.advirafarm.buyer.service.FetchAddressIntentServices;
import com.advira.advirafarm.buyer.ui.address.AddressListFragment;
import com.advira.advirafarm.buyer.ui.address.AddressfromMap;
import com.advira.advirafarm.buyer.ui.address.AutoDetectAddressList;
import com.advira.advirafarm.buyer.ui.cart.CartActivity;
import com.advira.advirafarm.buyer.ui.cart.ChooseAddressList;
import com.advira.advirafarm.buyer.ui.cart.OrderPreviewActivity;
import com.advira.advirafarm.buyer.ui.category.CategoryFragment;
import com.advira.advirafarm.buyer.ui.deliverySlot.DeliverySlotActivity;
import com.advira.advirafarm.buyer.ui.deliverySlot.MyDeliverySlot;
import com.advira.advirafarm.buyer.ui.guest.HomeFragmentGuest;
import com.advira.advirafarm.buyer.ui.guest.MainActivityGuestNav;
import com.advira.advirafarm.buyer.ui.guest.MyAccountfragmentGuest;
import com.advira.advirafarm.buyer.ui.login.api.LogoutResponse;
import com.advira.advirafarm.buyer.ui.myaccount.MembershipActivity;
import com.advira.advirafarm.buyer.ui.myaccount.MembershipFragment;
import com.advira.advirafarm.buyer.ui.myaccount.MyAccountSettingFragment;
import com.advira.advirafarm.buyer.ui.myaccount.MyAccountfragment;
import com.advira.advirafarm.buyer.ui.myaccount.MyMembershipFragment;
import com.advira.advirafarm.buyer.ui.myaccount.PersonalDetailsEditActivity;
import com.advira.advirafarm.buyer.ui.myaccount.SubscriptionActivity;
import com.advira.advirafarm.buyer.ui.myaccount.SubscriptionFragment;
import com.advira.advirafarm.buyer.ui.myaccount.WalletActivity;
import com.advira.advirafarm.buyer.ui.myaccount.WalletFragment;
import com.advira.advirafarm.buyer.ui.notification.NotificationListActivity;
import com.advira.advirafarm.buyer.ui.onetaplogin.OneTapLogin;
import com.advira.advirafarm.buyer.ui.order.MyOrdersFragment;
import com.advira.advirafarm.buyer.ui.product.HomeFragment;
import com.advira.advirafarm.buyer.ui.product.HomeFragmentB2B;
import com.advira.advirafarm.buyer.ui.product.api.CheckPinRequest;
import com.advira.advirafarm.buyer.ui.product.api.CheckPinResponse;

import com.advira.advirafarm.buyer.ui.search.SActivity;

import com.advira.advirafarm.buyer.ui.splash.Splash;
import com.advira.advirafarm.buyer.ui.subscrption.MySubscription;
import com.advira.advirafarm.buyer.utility.SharedPrefUtil;
import com.advira.advirafarm.buyer.utility.Singleton;
import com.advira.advirafarm.buyer.utility.Utilities;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivityNav extends AppCompatActivity implements IConsts, NavigationView.OnNavigationItemSelectedListener {

    public static TextView tv_cartcount, tv_header, tv_deliverto, tv_notificationcount;
    public static BottomNavigationView bottomnavview;
    RelativeLayout rl_delivpinto;
    public static int cart_count_number=0;
    public static TextView tv_cart_counter, text;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    ResultReceiver resultReceiver;
    String city="", pincode="";
    String from="";
    String membershipName="";

    public BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent != null) {
                String str = intent.getStringExtra("firebasemessage");
                String firebasemessagecount = intent.getStringExtra("firebasemessagecount");
                tv_notificationcount = findViewById(R.id.tv_notificationcount);
                tv_notificationcount.setText(firebasemessagecount);

                if (firebasemessagecount.equalsIgnoreCase("0")) {
                    tv_notificationcount.setVisibility(View.GONE);
                } else {
                    tv_notificationcount.setVisibility(View.VISIBLE);
                }

                try {
                    if (str.contains("Profile Activated")) {
                        Intent i = new Intent();
                        i.setClass(MainActivityNav.this, MainActivityNav.class);
                        finishAffinity();
                        startActivity(i);
                    }


                } catch (Exception ex) {

                }

                try {

                    if (str.contains("Profile Deactivated")) {

                        Intent i = new Intent();
                        i.setClass(MainActivityNav.this, MainActivityNav.class);
                        finishAffinity();
                        startActivity(i);
                    }


                } catch (Exception ex) {

                }
            }
        }
    };

    String profilemode = "B2C";
    String profilestatus="Inactive";
    AlertDialog.Builder builder;
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private FrameLayout frameLayout;
    private NavigationView navigationView;
    public static CircleImageView profile_image;
    private Context mContext;
    private RelativeLayout rl_cart, rl_search, rl_header2, rl_notification,rl_profile;
    public static TextView tv_username;
    private TextView tv_mobile;
    private TextView tv_accountype;
    private ImageView iv_headerlogo, iv_headerlogo2;
    public static ImageView iv_memimg;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_nav);

        initializeViews();
        toggleDrawer();
        initializeDefaultFragment(savedInstanceState, 0);

        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        /*searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));*/
        //setDarkModeSwitchListener();
        LocalBroadcastManager lbm = LocalBroadcastManager.getInstance(this);
        lbm.registerReceiver(receiver, new IntentFilter("filter_string"));

        resultReceiver = new AddressResultReceiver(new Handler());

        rl_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.setClass(MainActivityNav.this, CartActivity.class);
                startActivity(i);
            }
        });

        rl_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction().replace(R.id.framelayout_id, new MyAccountfragment())
                        .commit();
            }
        });

        rl_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent i = new Intent();
                i.setClass(MainActivityNav.this, SActivity.class);
                startActivity(i);
            }
        });

        rl_header2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Intent i = new Intent();
                    //i.setClass(MainActivityNav.this, AutoDetectAddressList.class);
                    //i.setClass(mContext, AddressfromMap.class);
                    i.setClass(mContext, ChooseAddressListNav.class);
                    i.putExtra("from","Home");
                    startActivity(i);
            }
        });

        rl_notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.setClass(MainActivityNav.this, NotificationListActivity.class);
                startActivity(i);
            }
        });

    }

    private void initializeViews() {

        mContext = MainActivityNav.this;
        rl_cart = findViewById(R.id.rl_cart);
        rl_search = findViewById(R.id.rl_search);
        rl_header2 = findViewById(R.id.rl_header2);
        tv_header = findViewById(R.id.tv_header);
        iv_headerlogo = findViewById(R.id.iv_headerlogo);
        iv_headerlogo2 = findViewById(R.id.iv_headerlogo2);
        tv_deliverto = findViewById(R.id.tv_deliverto);
        rl_notification = findViewById(R.id.rl_notification);
        rl_profile=findViewById(R.id.rl_profile);
        rl_delivpinto= findViewById(R.id.rl_delivpinto);
        rl_cart.setVisibility(View.GONE);

        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            from=extras.getString("from");
            city=extras.getString("street");
            pincode=extras.getString("pincode");

        }

        String delivaddress = SharedPrefUtil.getHeaderAddress(mContext, SHARED_PREF_HeaderAddress, "Select Address");
        if(from.equalsIgnoreCase("currentadd")){
            tv_deliverto.setText(city+" "+pincode);
        }else {
            tv_deliverto.setText(delivaddress);
        }
        tv_cartcount = findViewById(R.id.tv_cartcount);
        profilemode = SharedPrefUtil.getProfileMode(mContext, SHARED_PREF_ProfileMode, "B2C");
        tv_notificationcount = findViewById(R.id.tv_notificationcount);
        String notificationcount = SharedPrefUtil.getFCMMessageCount(mContext, SHARED_PREF_FCMMessageCount, "0");
        tv_notificationcount.setText(notificationcount);

        if (notificationcount.equalsIgnoreCase("0")) {
            tv_notificationcount.setVisibility(View.GONE);
        } else {
            tv_notificationcount.setVisibility(View.VISIBLE);
        }
        String fcmtoken = SharedPrefUtil.getFCMToken(mContext, SHARED_PREF_FCMToken, "");
        membershipName=SharedPrefUtil.getMembership(mContext,SHARED_PREF_MemberShip,"");

        toolbar = findViewById(R.id.toolbar_id);
        toolbar.setTitle(R.string.toolbar_title);
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
        LayoutInflater inflate=(LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View cart_badge = inflate.inflate(R.layout.notification_badge,mbottomNavigationMenuView, false);
        text = cart_badge.findViewById(R.id.notifications_badge);

        if(profilemode.equalsIgnoreCase("B2B")){
            String cartcount = SharedPrefUtil.getCartCountB2B(mContext, SHARED_PREF_CARTCOUNTB2B, "");
            text.setText(cartcount);
            rl_profile.setVisibility(View.VISIBLE);
        }
        else
        {
            String cartcount = SharedPrefUtil.getCartCount(mContext, SHARED_PREF_CARTCOUNT, "");
            text.setText(cartcount);
            rl_profile.setVisibility(View.VISIBLE);
        }

        itemView.addView(cart_badge);

        bottomnavview.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                String usertype = SharedPrefUtil.getUserActive(mContext, SHARED_PREF_UserActive, "guest");
                profilemode = SharedPrefUtil.getProfileMode(mContext, SHARED_PREF_ProfileMode, "B2C");
                switch (item.getItemId()){

                    case R.id.category:
                        if (profilemode.equalsIgnoreCase("B2C")) {
                            rl_cart.setVisibility(View.GONE);
                            tv_cartcount.setVisibility(View.GONE);
                        }
                        else {
                            rl_cart.setVisibility(View.GONE);
                            tv_cartcount.setVisibility(View.GONE);
                        }

                        getSupportFragmentManager().beginTransaction().replace(R.id.framelayout_id, new CategoryFragment())
                                .commit();
                        rl_search.setVisibility(View.VISIBLE);
                        rl_profile.setVisibility(View.VISIBLE);
                        rl_header2.setVisibility(View.GONE);
                        tv_header.setText("");
                        iv_headerlogo2.setVisibility(View.VISIBLE);
                        iv_headerlogo.setVisibility(View.GONE);
                        break;

                    case R.id.home_btmnav:

                        String profilemode = SharedPrefUtil.getProfileMode(mContext, SHARED_PREF_ProfileMode, "B2C");
                        if (profilemode.equalsIgnoreCase("B2B")) {
                            getSupportFragmentManager().beginTransaction().replace(R.id.framelayout_id, new HomeFragmentB2B()).addToBackStack(null)
                                    .commit();
                            tv_accountype.setText("Account Type-Business");
                            String cartcount = SharedPrefUtil.getCartCountB2B(mContext, SHARED_PREF_CARTCOUNTB2B, "");
                            SharedPrefUtil.setCartCount(mContext,SHARED_PREF_CARTCOUNT,cartcount);

                            text.setText(cartcount);
                            rl_profile.setVisibility(View.VISIBLE);
                            rl_cart.setVisibility(View.GONE);
                            rl_profile.setVisibility(View.VISIBLE);
                            tv_cartcount.setVisibility(View.GONE);
                            rl_search.setVisibility(View.GONE);
                            rl_header2.setVisibility(View.GONE);
                            tv_header.setText("");
                            iv_headerlogo2.setVisibility(View.VISIBLE);
                            iv_headerlogo.setVisibility(View.GONE);

                        } else {
                            getSupportFragmentManager().beginTransaction().replace(R.id.framelayout_id, new HomeFragment())
                                    .commit();
                            tv_accountype.setText("Account Type-Consumer");

                            String cartcount = SharedPrefUtil.getCartCountB2C(mContext, SHARED_PREF_CARTCOUNTB2C, "");
                            SharedPrefUtil.setCartCount(mContext,SHARED_PREF_CARTCOUNT,cartcount);
                            tv_cartcount.setVisibility(View.GONE);
                            text.setText(cartcount);
                            rl_profile.setVisibility(View.VISIBLE);
                        }
                        rl_search.setVisibility(View.GONE);
                        rl_header2.setVisibility(View.GONE);
                        tv_header.setText("");
                        iv_headerlogo2.setVisibility(View.VISIBLE);
                        iv_headerlogo.setVisibility(View.GONE);
                        rl_cart.setVisibility(View.GONE);
                        rl_profile.setVisibility(View.VISIBLE);
                        tv_cartcount.setVisibility(View.GONE);
                        break;
                    case R.id.tokri:
                        startActivity(new Intent(getApplicationContext(), CartActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.home_wallet:
                        if (usertype.equalsIgnoreCase("guest")) {
                            startActivity(new Intent(getApplicationContext(), OneTapLogin.class));
                            overridePendingTransition(0,0);
                        } else {
                            startActivity(new Intent(getApplicationContext(), WalletActivity.class));
                            overridePendingTransition(0,0);
                        }
                        break;
                    case R.id.home_subscription:
                        if (usertype.equalsIgnoreCase("guest")) {
                            startActivity(new Intent(getApplicationContext(), OneTapLogin.class));
                            overridePendingTransition(0,0);
                        } else {
                            startActivity(new Intent(getApplicationContext(), MySubscription.class));
                            overridePendingTransition(0,0);
                        }
                        return true;
               }
                return true;
            }
        });
        //getCurrentLocation();
        
        profile_image = (CircleImageView) navigationView.getHeaderView(0).findViewById(R.id.profile_image);
        iv_memimg=(ImageView) navigationView.getHeaderView(0).findViewById(R.id.iv_memimg);
        tv_accountype = (TextView) navigationView.getHeaderView(0).findViewById(R.id.tv_accountype);
        tv_accountype.setVisibility(View.GONE);
        tv_username = (TextView) navigationView.getHeaderView(0).findViewById(R.id.tv_username);
        tv_mobile = (TextView) navigationView.getHeaderView(0).findViewById(R.id.tv_mobile);
        String name = SharedPrefUtil.getUserName(mContext, SHARED_PREF_UserName, "");
        String mobile = SharedPrefUtil.getUserMobile(mContext, SHARED_PREF_UserMobile, "");
        String profilepic = SharedPrefUtil.getUserProfilePic(mContext, SHARED_PREF_ProfilePic, "");

        if(name.equalsIgnoreCase("")){
            AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
            builder.setTitle("Profile Update!");
            builder.setMessage("Please update your profile.")
                    .setCancelable(false)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                            Intent i = new Intent();
                            i.setClass(MainActivityNav.this, PersonalDetailsEditActivity.class);
                            startActivity(i);


                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {


                        }
                    });

            AlertDialog alert = builder.create();
            alert.show();

            alert.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(mContext.getResources().getColor(R.color.colorThemeDark));
            alert.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(mContext.getResources().getColor(R.color.colorThemeDark));

        }
        tv_username.setText(name);
        tv_mobile.setText(mobile);

        if(membershipName!=null && membershipName.length()>0 ){
            navigationView.getMenu().getItem(8).setTitle("My Membership");
        }
        else{
            iv_memimg.setVisibility(View.GONE);
        }

        if (profilemode.equalsIgnoreCase("B2B")) {
            tv_accountype.setText("Account Type-Business");
        } else {
            tv_accountype.setText("Account Type-Consumer");
        }

        if (profilepic.length() > 5) {
            Picasso.with(mContext).load(profilepic).placeholder(R.drawable.progress_animation).into(profile_image);
        }

        rl_delivpinto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.setClass(mContext, AddressfromMap.class);
                i.putExtra("from","Home");
                startActivity(i);
            }
        });

        if (ContextCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivityNav.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_PERMISSION_REQUEST_CODE);
        } else {
            getCurrentLocation();
        }

    }

    private void initializeDefaultFragment(Bundle savedInstanceState, int itemIndex) {
        String userstatus = SharedPrefUtil.getUserActive(mContext, SHARED_PREF_UserActive, "inactive");

        navigationView.getMenu().getItem(9).setVisible(false);
        navigationView.getMenu().getItem(7).setVisible(false);
        navigationView.getMenu().getItem(2).setVisible(false);
        navigationView.getMenu().getItem(3).setVisible(false);

        if (savedInstanceState == null) {
            MenuItem menuItem = navigationView.getMenu().getItem(itemIndex).setChecked(true);
            onNavigationItemSelected(menuItem);
        }
    }

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
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        }

        super.onBackPressed();


    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.nav_home_id:

                String profilemode = SharedPrefUtil.getProfileMode(mContext, SHARED_PREF_ProfileMode, "B2C");

                if (profilemode.equalsIgnoreCase("B2B")) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.framelayout_id, new HomeFragmentB2B()).addToBackStack(null)
                            .commit();
                    tv_accountype.setText("Account Type-Business");
                    String cartcount = SharedPrefUtil.getCartCountB2B(mContext, SHARED_PREF_CARTCOUNTB2B, "");
                    SharedPrefUtil.setCartCount(mContext,SHARED_PREF_CARTCOUNT,cartcount);
                    tv_cartcount.setVisibility(View.GONE);
                    text.setText(cartcount);
                    rl_profile.setVisibility(View.VISIBLE);

                } else {
                    getSupportFragmentManager().beginTransaction().replace(R.id.framelayout_id, new HomeFragment())
                            .commit();
                    tv_accountype.setText("Account Type-Consumer");
                    String cartcount = SharedPrefUtil.getCartCountB2C(mContext, SHARED_PREF_CARTCOUNTB2C, "");
                    SharedPrefUtil.setCartCount(mContext,SHARED_PREF_CARTCOUNT,cartcount);
                    tv_cartcount.setVisibility(View.GONE);
                    text.setText(cartcount);
                    rl_profile.setVisibility(View.VISIBLE);
                }
                rl_search.setVisibility(View.GONE);
                rl_header2.setVisibility(View.GONE);
                tv_header.setText("");
                iv_headerlogo2.setVisibility(View.VISIBLE);
                iv_headerlogo.setVisibility(View.GONE);
                closeDrawer();
                break;
            case R.id.nav_category_id:
                getSupportFragmentManager().beginTransaction().replace(R.id.framelayout_id, new CategoryFragment()).addToBackStack(null)
                        .commit();
                rl_search.setVisibility(View.VISIBLE);
                rl_profile.setVisibility(View.VISIBLE);
                rl_header2.setVisibility(View.GONE);
                tv_header.setText("Category");
                iv_headerlogo2.setVisibility(View.GONE);
                iv_headerlogo.setVisibility(View.VISIBLE);
                closeDrawer();
                break;

            case R.id.nav_shopbybusiness:

                SharedPrefUtil.setProfileMode(mContext, SHARED_PREF_ProfileMode, "B2B");
                getSupportFragmentManager().beginTransaction().replace(R.id.framelayout_id, new HomeFragmentB2B())
                            .commit();

                String cartcount = SharedPrefUtil.getCartCountB2B(mContext, SHARED_PREF_CARTCOUNTB2B, "");
                SharedPrefUtil.setCartCount(mContext,SHARED_PREF_CARTCOUNT,cartcount);

                text.setText(cartcount);
                tv_accountype.setText("Account Type-Business");
                rl_search.setVisibility(View.GONE);
                rl_profile.setVisibility(View.VISIBLE);
                rl_header2.setVisibility(View.GONE);
                tv_header.setText("");
                iv_headerlogo2.setVisibility(View.VISIBLE);
                iv_headerlogo.setVisibility(View.GONE);

                closeDrawer();
                break;

            case R.id.nav_shopbyconsumer:
                SharedPrefUtil.setProfileMode(mContext, SHARED_PREF_ProfileMode, "B2C");
                getSupportFragmentManager().beginTransaction().replace(R.id.framelayout_id, new HomeFragment()).addToBackStack(null)
                        .commit();

                String cartcount1 = SharedPrefUtil.getCartCountB2C(mContext, SHARED_PREF_CARTCOUNTB2C, "");
                SharedPrefUtil.setCartCount(mContext,SHARED_PREF_CARTCOUNT,cartcount1);
                tv_cartcount.setVisibility(View.GONE);
                text.setText(cartcount1);
                rl_profile.setVisibility(View.VISIBLE);
                tv_accountype.setText("Account Type-Consumer");
                rl_search.setVisibility(View.GONE);
                rl_header2.setVisibility(View.GONE);
                tv_header.setText("");
                iv_headerlogo2.setVisibility(View.VISIBLE);
                iv_headerlogo.setVisibility(View.GONE);

                closeDrawer();
                break;

            case R.id.nav_myaddress_id:
                getSupportFragmentManager().beginTransaction().replace(R.id.framelayout_id, new AddressListFragment()).addToBackStack(null)
                        .commit();
                rl_search.setVisibility(View.VISIBLE);
                rl_profile.setVisibility(View.VISIBLE);
                rl_header2.setVisibility(View.GONE);
                tv_header.setText("My Address");
                iv_headerlogo2.setVisibility(View.GONE);
                iv_headerlogo.setVisibility(View.VISIBLE);
                closeDrawer();
                break;

            case R.id.nav_myorders_id:
                getSupportFragmentManager().beginTransaction().replace(R.id.framelayout_id, new MyOrdersFragment()).addToBackStack(null)
                        .commit();
                rl_search.setVisibility(View.VISIBLE);
                rl_header2.setVisibility(View.GONE);
                tv_header.setText("My Orders");
                rl_profile.setVisibility(View.VISIBLE);
                iv_headerlogo2.setVisibility(View.GONE);
                iv_headerlogo.setVisibility(View.VISIBLE);
                closeDrawer();
                break;

            case R.id.nav_profile_id:
                getSupportFragmentManager().beginTransaction().replace(R.id.framelayout_id, new MyAccountfragment()).addToBackStack(null)
                        .commit();
                rl_search.setVisibility(View.VISIBLE);
                rl_profile.setVisibility(View.VISIBLE);
                rl_header2.setVisibility(View.GONE);
                tv_header.setText("My Account");
                iv_headerlogo2.setVisibility(View.GONE);
                iv_headerlogo.setVisibility(View.VISIBLE);
                closeDrawer();
                break;

                case R.id.nav_wallet_id:
                getSupportFragmentManager().beginTransaction().replace(R.id.framelayout_id, new WalletFragment())
                        .commit();
                rl_search.setVisibility(View.VISIBLE);
                rl_header2.setVisibility(View.GONE);
                tv_header.setText("Wallet Balance");
                iv_headerlogo2.setVisibility(View.GONE);
                iv_headerlogo.setVisibility(View.VISIBLE);
                closeDrawer();
                break;
            case R.id.nav_subscription:
                if(membershipName!=null && membershipName.length()>0){
                    menuItem.setTitle("My Membership");
                } else {
                    menuItem.setTitle("Advira Elite Green");
                }
                startActivity(new Intent(getApplicationContext(), MembershipActivity.class));
                overridePendingTransition(0,0);
                rl_search.setVisibility(View.VISIBLE);
                rl_header2.setVisibility(View.GONE);
                tv_header.setText("Membership");
                iv_headerlogo2.setVisibility(View.GONE);
                iv_headerlogo.setVisibility(View.VISIBLE);
                //return true;
                closeDrawer();
                break;
            /*case R.id.update_profile_id:
                getSupportFragmentManager().beginTransaction().replace(R.id.framelayout_id, new MyAccountfragment())
                        .commit();
                rl_search.setVisibility(View.VISIBLE);
                rl_header2.setVisibility(View.GONE);
                tv_header.setText("Update Your Account");
                iv_headerlogo2.setVisibility(View.GONE);
                iv_headerlogo.setVisibility(View.VISIBLE);
                closeDrawer();
                break;*/
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

                closeDrawer();
                break;

            case R.id.nav_accountsettings_id:
                getSupportFragmentManager().beginTransaction().replace(R.id.framelayout_id, new MyAccountSettingFragment()).addToBackStack(null)
                        .commit();
                rl_search.setVisibility(View.VISIBLE);
                rl_header2.setVisibility(View.GONE);
                tv_header.setText("Settings");
                iv_headerlogo2.setVisibility(View.GONE);
                iv_headerlogo.setVisibility(View.VISIBLE);
                closeDrawer();
                break;

            case R.id.nav_customer_id:
                msgcustomercare();
                closeDrawer();
                break;
            case R.id.nav_customercall_id:
                callcustomercare();
                closeDrawer();
                break;

            case R.id.nav_logout_id:
                logout();
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
                Toast.makeText(mContext, "WhatsApp not installed.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void logout() {

        builder = new AlertDialog.Builder(mContext);
        builder.setMessage("Logout");

        builder.setMessage("Do you want to Logout ?").setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        logoutRequest();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert = builder.create();
        alert.setTitle("Logout");
        alert.show();

        alert.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(mContext.getResources().getColor(R.color.colorThemeDark));
        alert.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(mContext.getResources().getColor(R.color.colorThemeDark));


    }

    private void logoutRequest() {

        Utilities.showLoading(mContext);

        String token = SharedPrefUtil.getUniversalSharedPrefToken(mContext, SHARED_PREF_TOKEN, "");

        try {
            Call<LogoutResponse> call = RetrofitUrlConnection.loadJSON(token).logout();

            call.enqueue(new Callback<LogoutResponse>() {
                @Override
                public void onResponse(Call<LogoutResponse> call, Response<LogoutResponse> response) {

                    if (response.body().getStatus().toString().equalsIgnoreCase("100")) {
                        SharedPrefUtil.setUniversalSharedPrefToken(mContext, SHARED_PREF_TOKEN, "");

                        SharedPrefUtil.setUserName(mContext, SHARED_PREF_UserName, "Guest");
                        SharedPrefUtil.setUserMobile(mContext, SHARED_PREF_UserMobile, "");
                        SharedPrefUtil.setUserEmail(mContext, SHARED_PREF_UserEmailID, "");
                        SharedPrefUtil.setUserProfilePic(mContext, SHARED_PREF_ProfilePic, "");
                        SharedPrefUtil.setProfilePercent(mContext, SHARED_PREF_PROFILEPERCENTAGE, "");
                        SharedPrefUtil.setCartCount(mContext, SHARED_PREF_CARTCOUNT, "0");
                        SharedPrefUtil.setUserActive(mContext, SHARED_PREF_UserActive, "guest");
                        SharedPrefUtil.setOrderCount(mContext, SHARED_PREF_OrderCount, "0");
                        SharedPrefUtil.setHeaderAddress(mContext, SHARED_PREF_HeaderAddress, "Select Address");
                        SharedPrefUtil.setAvailableCredit(mContext, SHARED_PREF_AvailableCredit, "0");
                        SharedPrefUtil.setmembershipStartTime(mContext,SHARED_PREF_MemberShip_StartTime,"");
                        SharedPrefUtil.setSubscriptionID(mContext,SHARED_PREF_SubscriptionID,"");

                        Intent intent = new Intent();
                        intent.setClass(mContext, Splash.class);//slideActivity
                        finishAffinity();
                        startActivity(intent);
                    }
                    Utilities.dismissDialog();
                }
                @Override
                public void onFailure(Call<LogoutResponse> call, Throwable t) {
                    Utilities.dismissDialog();

                    Toast.makeText(mContext, "Something went wrong, Please try again.", Toast.LENGTH_SHORT).show();
                }
            });

        } catch (Exception e) {
            Utilities.dismissDialog();

            Toast.makeText(mContext, "Something went wrong, Please try again.", Toast.LENGTH_SHORT).show();
        }
    }

    private void closeDrawer() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
    }

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

        String notificationcount = SharedPrefUtil.getFCMMessageCount(mContext, SHARED_PREF_FCMMessageCount, "0");
        tv_notificationcount.setText(notificationcount);

        if (notificationcount.equalsIgnoreCase("0")) {
            tv_notificationcount.setVisibility(View.GONE);
        } else {
            tv_notificationcount.setVisibility(View.VISIBLE);
        }
        if(profilemode.equalsIgnoreCase("B2B")){
            String cartcount = SharedPrefUtil.getCartCountB2B(mContext, SHARED_PREF_CARTCOUNTB2B, "");
            text.setText(cartcount);
            rl_profile.setVisibility(View.VISIBLE);
        }
        else
        {
            String cartcount = SharedPrefUtil.getCartCount(mContext, SHARED_PREF_CARTCOUNT, "");
            text.setText(cartcount);
            rl_profile.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE && grantResults.length > 0) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getCurrentLocation();
            } else {
                Toast.makeText(this, "Permission is denied!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void getCurrentLocation() {

        //Utilities.showLoading(mContext);
        LocationRequest locationRequest = new LocationRequest();
        locationRequest.setInterval(5000);
        locationRequest.setFastestInterval(3000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        LocationServices.getFusedLocationProviderClient(MainActivityNav.this)
                .requestLocationUpdates(locationRequest, new LocationCallback() {

                    @Override
                    public void onLocationResult(LocationResult locationResult) {
                        super.onLocationResult(locationResult);
                        LocationServices.getFusedLocationProviderClient(getApplicationContext())
                                .removeLocationUpdates(this);
                        if (locationResult != null && locationResult.getLocations().size() > 0) {
                            int latestlocIndex = locationResult.getLocations().size() - 1;
                            double lati = locationResult.getLocations().get(latestlocIndex).getLatitude();
                            double longi = locationResult.getLocations().get(latestlocIndex).getLongitude();

                            Location location = new Location("providerNA");
                            location.setLongitude(longi);
                            location.setLatitude(lati);
                            fetchaddressfromlocation(location);
                            //Utilities.dismissDialog();
                        } else {
                            Utilities.dismissDialog();
                        }
                    }
                }, Looper.getMainLooper());
    }


    private class AddressResultReceiver extends ResultReceiver {
        public AddressResultReceiver(Handler handler) {
            super(handler);
        }

        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData) {
            super.onReceiveResult(resultCode, resultData);
            if (resultCode == Constants.SUCCESS_RESULT) {
                city=resultData.getString(Constants.DISTRICT);
                pincode=resultData.getString(Constants.POST_CODE);
                //SharedPrefUtil.setHeaderAddress(mContext,SHARED_PREF_HeaderAddress,resultData.getString(Constants.LOCAITY)+","+resultData.getString(Constants.POST_CODE));
                //tv_deliverto.setText(resultData.getString(Constants.LOCAITY)+","+resultData.getString(Constants.POST_CODE));
                CheckPin(resultData.getString(Constants.POST_CODE));
            } else {
                Toast.makeText(MainActivityNav.this, resultData.getString(Constants.RESULT_DATA_KEY), Toast.LENGTH_SHORT).show();
            }
            Utilities.dismissDialog();
        }
    }

    private void CheckPin(String pincode) {
        Utilities.showLoading(mContext);
        String token = SharedPrefUtil.getUniversalSharedPrefToken(mContext, SHARED_PREF_TOKEN, "");
        CheckPinRequest checkPinRequest = new CheckPinRequest();
        checkPinRequest.setPincode(pincode);

        try {

            Call<CheckPinResponse> call = RetrofitUrlConnection.loadJSON(token).pincode(checkPinRequest);

            call.enqueue(new Callback<CheckPinResponse>() {
                @Override
                public void onResponse(Call<CheckPinResponse> call, Response<CheckPinResponse> response) {

                    if (response.body().getStatus().toString().equalsIgnoreCase("100")) {

                        Utilities.dismissDialog();
                        if (response.body().getPincodeDetails().size() > 0) {
                            SharedPrefUtil.setHeaderAddress(mContext,SHARED_PREF_HeaderAddress,city+","+pincode);
                            tv_deliverto.setText(city+","+pincode);
                        }
                        else {
                            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivityNav.this);
                            builder.setMessage("We are Coming Soon in your location. We will notify you when we available in you site. ")
                                    .setCancelable(false)
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            /*Intent a = new Intent(Intent.ACTION_MAIN);
                                            a.addCategory(Intent.CATEGORY_HOME);
                                            a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                            startActivity(a);*/
                                        }
                                    });
                            AlertDialog alert = builder.create();
                            alert.show();

                            String delivaddress = SharedPrefUtil.getHeaderAddress(mContext, SHARED_PREF_HeaderAddress, "Select Address");
                            tv_deliverto.setText(delivaddress);
                        }
                    }
                    else {
                        Utilities.dismissDialog();
                        Singleton.getInstance().showLongToast(mContext, response.body().getMessage());//remove toast
                    }
                }

                @Override
                public void onFailure(Call<CheckPinResponse> call, Throwable t) {

                    Utilities.dismissDialog();
                }
            });
        } catch (Exception e) {
            Toast.makeText(mContext, "Invalid request, Please try again.", Toast.LENGTH_SHORT).show();
        }
    }

    private void fetchaddressfromlocation(Location location) {
        Intent intent = new Intent(this, FetchAddressIntentService.class);
        intent.putExtra(Constants.RECEVIER, resultReceiver);
        intent.putExtra(Constants.LOCATION_DATA_EXTRA, location);
        startService(intent);
    }
}
