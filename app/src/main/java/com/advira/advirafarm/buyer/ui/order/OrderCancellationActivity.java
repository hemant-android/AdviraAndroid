package com.advira.advirafarm.buyer.ui.order;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.advira.advirafarm.buyer.R;
import com.advira.advirafarm.buyer.constants.IConsts;
import com.advira.advirafarm.buyer.retrofurlconnection.RetrofitUrlConnection;
import com.advira.advirafarm.buyer.ui.cart.CartActivity;
import com.advira.advirafarm.buyer.ui.category.CategoryActivity;
import com.advira.advirafarm.buyer.ui.masterapi.DocumentList;
import com.advira.advirafarm.buyer.ui.masterapi.MasterResponse;
import com.advira.advirafarm.buyer.ui.masterapi.OrderCanceleQuestion;
import com.advira.advirafarm.buyer.ui.myaccount.SubscriptionActivity;
import com.advira.advirafarm.buyer.ui.myaccount.WalletActivity;
import com.advira.advirafarm.buyer.ui.navigation.MainActivityNav;
import com.advira.advirafarm.buyer.ui.onetaplogin.OneTapLogin;
import com.advira.advirafarm.buyer.ui.order.adapter.OrderDetailsImageAdapter;
import com.advira.advirafarm.buyer.ui.order.api.OrderCancelRequest;
import com.advira.advirafarm.buyer.ui.order.api.OrderCancelResponse;
import com.advira.advirafarm.buyer.ui.order.api.OrderDetailsRequest;
import com.advira.advirafarm.buyer.ui.order.api.OrderDetailsResponse;
import com.advira.advirafarm.buyer.ui.order.api.OrderProductList;
import com.advira.advirafarm.buyer.ui.product.SearchActivity;
import com.advira.advirafarm.buyer.ui.product.Search_one;
import com.advira.advirafarm.buyer.ui.registration.adapter.DocumentAdapter;
import com.advira.advirafarm.buyer.utility.SharedPrefUtil;
import com.advira.advirafarm.buyer.utility.Singleton;
import com.advira.advirafarm.buyer.utility.Utilities;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.advira.advirafarm.buyer.ui.subscrption.MySubscription;
import com.google.gson.Gson;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

public class OrderCancellationActivity extends AppCompatActivity implements IConsts {

    private RelativeLayout rl_back ,rl_search,rl_cart;
    private TextView tv_cartcount;

    private static RecyclerView recyclerView2;
    private Button btn_cancelorder;
    private static TextView  tv_pd_header2, tv_deliv;
    private static EditText et_reason;



    private Spinner spn_reason;
    private List<DocumentList> arrayListDoctype;
    DocumentAdapter arrayAdapterDoc;

    public static RelativeLayout  rl_recycler2;
    OrderDetailsImageAdapter orderDetailsImageAdapter;

    private List<OrderProductList> orderproductList;
    private Context mContext;
    private RelativeLayout rl_cancel;
    private String orderid = "";
    private String orderno = "";

    public static BottomNavigationView bottomnavview;
    public static TextView tv_cart_counter, text;


    AlertDialog.Builder builder;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ordercancellation);

        initUI();

        rl_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                OrderCancellationActivity.this.finish();


            }
        });



        rl_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent();
                i.setClass(OrderCancellationActivity.this, CartActivity.class);
                startActivity(i);

            }
        });

        rl_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent();
                //i.setClass(OrderCancellationActivity.this, SearchActivity.class);
                i.setClass(OrderCancellationActivity.this, Search_one.class);
                startActivity(i);

            }
        });

        spn_reason.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (position == 0) {

                    et_reason.setHint("Comments (Optional)");
                    et_reason.clearFocus();

                } else if (spn_reason.getSelectedItem().toString().equalsIgnoreCase("My reason is not listed")) {

                    et_reason.setHint("Cancellation Reason");
                    et_reason.requestFocus();

                }
                else
                {
                    et_reason.setHint("Comments (Optional)");
                    et_reason.clearFocus();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });



        btn_cancelorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (spn_reason.getSelectedItem().toString().equalsIgnoreCase("Select Cancellation Reason ")) {
                    Singleton.getInstance().showLongToast(mContext, "Please select Cancellation Reason ");

                }
                else if (spn_reason.getSelectedItem().toString().equalsIgnoreCase("My reason is not listed"))
                {

                    if (et_reason.getText().toString().trim().length()<1)
                    {
                        et_reason.setError("Please enter reason");
                        et_reason.requestFocus();
                    }
                    else
                    {
                        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                        builder.setTitle("Request Cancellation");

                        //Setting message manually and performing action on button click
                        builder.setMessage("Are you sure you want to cancel this order ?")
                                .setCancelable(false)
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {

                                        cancelOrder();

                                    }
                                })
                                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {

                                        dialog.cancel();

                                    }
                                });
                        //Creating dialog box
                        AlertDialog alert = builder.create();
                        //Setting the title manually
                        // alert.setTitle("Logout");
                        alert.show();

                        alert.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(mContext.getResources().getColor(R.color.colorThemeDark));
                        alert.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(mContext.getResources().getColor(R.color.colorThemeDark));

                    }
                }
                else
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                    builder.setTitle("Request Cancellation");

                    //Setting message manually and performing action on button click
                    builder.setMessage("Are you sure you want to cancel this order ?")
                            .setCancelable(false)
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {

                                    cancelOrder();

                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {

                                    dialog.cancel();

                                }
                            });
                    //Creating dialog box
                    AlertDialog alert = builder.create();
                    //Setting the title manually
                    // alert.setTitle("Logout");
                    alert.show();

                    alert.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(mContext.getResources().getColor(R.color.colorThemeDark));
                    alert.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(mContext.getResources().getColor(R.color.colorThemeDark));

                }

                /*Intent i=new Intent();
                i.setClass(mContext,OrderDetailsActivity.class);
                startActivity(i);*/

            }
        });


    }

    private void initUI() {

        mContext = OrderCancellationActivity.this;

        rl_back = findViewById(R.id.rl_back);
        rl_search  = findViewById(R.id.rl_search);
        rl_cart = findViewById(R.id.rl_cart);
        rl_cart.setVisibility(View.GONE);
        tv_cartcount = findViewById(R.id.tv_cartcount);
        tv_cartcount.setVisibility(View.GONE);
        String cartcount = SharedPrefUtil.getCartCount(mContext, SHARED_PREF_CARTCOUNT, "");
        //tv_cartcount.setText(cartcount);

        spn_reason = findViewById(R.id.spn_reason);
        recyclerView2 = findViewById(R.id.recyclerView2);
        tv_pd_header2 = findViewById(R.id.tv_pd_header2);
        tv_deliv = findViewById(R.id.tv_deliv);
        btn_cancelorder = findViewById(R.id.btn_cancelorder);
        rl_cancel = findViewById(R.id.rl_cancel);
        rl_recycler2 = findViewById(R.id.rl_recycler2);
        et_reason = findViewById(R.id.et_reason);

        recyclerView2.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));


        bottomnavview=findViewById(R.id.bottom_navigatin_view);
        //bottomnavview.setVisibility(View.GONE);
        bottomnavview.setSelectedItemId(R.id.home_btmnav);
        //bottomnavview.setSelectedItemId(R.id.home_btmnav);
        bottomnavview.setItemIconTintList(null);

        BottomNavigationMenuView mbottomNavigationMenuView =
                (BottomNavigationMenuView) bottomnavview.getChildAt(0);

        View view = mbottomNavigationMenuView.getChildAt(4);

        BottomNavigationItemView itemView = (BottomNavigationItemView) view;

        /*View cart_badge = LayoutInflater.from(this)
                .inflate(R.layout.notification_badge,
                        mbottomNavigationMenuView, false);
        //((TextView) cart_badge.findViewById(R.id.notifications_badge)).setText();*/

        LayoutInflater inflate=(LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View cart_badge = inflate.inflate(R.layout.notification_badge,mbottomNavigationMenuView, false);

        text=(TextView) cart_badge.findViewById(R.id.notifications_badge);
        //String cartcount = SharedPrefUtil.getCartCount(mContext, SHARED_PREF_CARTCOUNT, "0");
        text.setText(cartcount);
        itemView.addView(cart_badge);

        bottomnavview.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                String usertype = SharedPrefUtil.getUserActive(mContext, SHARED_PREF_UserActive, "guest");
                //profilemode = SharedPrefUtil.getProfileMode(mContext, SHARED_PREF_ProfileMode, "B2C");
                switch (item.getItemId()){

                    case R.id.category:
                        //tv_cartcount.setVisibility(View.GONE);
                        /*getSupportFragmentManager().beginTransaction().replace(R.id.framelayout_id, new CategoryFragment())
                                .commit();

                        rl_search.setVisibility(View.VISIBLE);
                        break;*/
                        startActivity(new Intent(getApplicationContext(), CategoryActivity.class));
                        overridePendingTransition(0,0);
                        break;
                    case R.id.home_btmnav:
                        startActivity(new Intent(getApplicationContext(), MainActivityNav.class));
                        overridePendingTransition(0,0);
                        break;
                    case R.id.home_subscription:
                        if (usertype.equalsIgnoreCase("guest")) {
                            startActivity(new Intent(getApplicationContext(), OneTapLogin.class));
                            overridePendingTransition(0,0);
                        } else {
                            startActivity(new Intent(getApplicationContext(), MySubscription.class));
                            overridePendingTransition(0,0);
                        }
                        break;
                    case R.id.home_wallet:
                        if (usertype.equalsIgnoreCase("guest")) {
                            startActivity(new Intent(getApplicationContext(), OneTapLogin.class));
                            overridePendingTransition(0,0);
                        } else {
                            startActivity(new Intent(getApplicationContext(), WalletActivity.class));
                            overridePendingTransition(0,0);
                        }
                        break;
                    case R.id.tokri:
                        startActivity(new Intent(getApplicationContext(),CartActivity.class));
                        overridePendingTransition(0,0);
                        return true;


                }

                //getSupportFragmentManager().beginTransaction().replace(R.id.framelayout_id, new HomeFragmentGuest()).commit();

                return true;
            }
        });


        Bundle extras = getIntent().getExtras();


        if (extras != null) {
            orderid = extras.getString("orderid");
        }

        OrderDetailsRequest(orderid);
        binddocumentType();

    }


    public void OrderDetailsRequest(String orderid) {

        //Utilities.showLoading(mContext);

        String token = SharedPrefUtil.getUniversalSharedPrefToken(mContext, SHARED_PREF_TOKEN, "");

        OrderDetailsRequest orderDetailsRequest = new OrderDetailsRequest();
        orderDetailsRequest.setOrderId(orderid);

        try {

            Call<OrderDetailsResponse> call = RetrofitUrlConnection.loadJSON(token).myorderdetail(orderDetailsRequest);

            call.enqueue(new Callback<OrderDetailsResponse>() {
                @Override
                public void onResponse(Call<OrderDetailsResponse> call, Response<OrderDetailsResponse> response) {

                    if (response.body().getStatus().toString().equalsIgnoreCase("100")) {

                        orderproductList = new ArrayList<>();
                        orderDetailsImageAdapter = new OrderDetailsImageAdapter(mContext, orderproductList);


                        tv_deliv.setText(formatDate(response.body().getOrderDetails().get(0).getCreatedAt()));
                        tv_pd_header2.setText("Order No : " + response.body().getOrderDetails().get(0).getOrderNo());
                        orderno = response.body().getOrderDetails().get(0).getOrderNo();


                        List<OrderProductList> mListData = response.body().getOrderDetails().get(0).getOrderProductList();

                        if (mListData != null && mListData.size() > 0) {
                            orderproductList.addAll(mListData);


                        }

                        recyclerView2.setAdapter(orderDetailsImageAdapter);
                        recyclerView2.setNestedScrollingEnabled(false);

                        Utilities.dismissDialog();


                    } else {
                        //Singleton.getInstance().showLongToast(mContext, response.body().getMessage());
                        Utilities.dismissDialog();
                    }

                }

                @Override
                public void onFailure(Call<OrderDetailsResponse> call, Throwable t) {

                    Utilities.dismissDialog();
                }
            });
        } catch (Exception e) {
            Toast.makeText(mContext, "Invalid request, Please try again.", Toast.LENGTH_SHORT).show();
        }

    }

    private String formatDate(String dateString) {

        SimpleDateFormat input = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat output = new SimpleDateFormat("EE, MMM dd yyyy");

        Date d = null;
        try {
            d = input.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        String formatted = output.format(d);
        Log.i("DATE", "" + formatted);

        return formatted;
    }

    private void cancelOrder() {

        Utilities.showLoading(mContext);

        int documentid = spn_reason.getSelectedItemPosition();
        String reasonid = String.valueOf(arrayListDoctype.get(documentid).getId());

        OrderCancelRequest orderCancelRequest=new OrderCancelRequest();
        orderCancelRequest.setOrderId(orderid);
        orderCancelRequest.setCancelReasonId(reasonid);
        orderCancelRequest.setCancelReasonOther(et_reason.getText().toString());

        String token = SharedPrefUtil.getUniversalSharedPrefToken(mContext, SHARED_PREF_TOKEN, "");

        try {
            Call<OrderCancelResponse> call = RetrofitUrlConnection.loadJSON(token).canclemyorder(orderCancelRequest);

            call.enqueue(new Callback<OrderCancelResponse>() {
                @Override
                public void onResponse(Call<OrderCancelResponse> call, Response<OrderCancelResponse> response) {
                   /* Gson gson = new Gson();
                    String jsondata = gson.toJson(response.body());
                    //String orderstatus = response.body().getOrderDetails().get(0).getDeliveryStatus();
                    Log.e(String.valueOf(OrderDetailsActivity.class), "onResponse: sapna_orderstatus"+jsondata+"\n");
*/
                    if (response.body().getStatus().toString().equalsIgnoreCase("100")) {

                        //Singleton.getInstance().showLongToast(mContext, response.body().getMessage());
                        Utilities.dismissDialog();

                        //OrderCancellationActivity.this.finish();

                        Intent i=new Intent();
                        i.setClass(mContext,OrderDetailsActivity.class);
                        i.putExtra("orderid", orderid);
                        i.putExtra("from", "");
                        startActivity(i);


                    }

                    Utilities.dismissDialog();

                }


                @Override
                public void onFailure(Call<OrderCancelResponse> call, Throwable t) {
                    Utilities.dismissDialog();

                    Toast.makeText(mContext, "Something went wrong, Please try again.", Toast.LENGTH_SHORT).show();
                }
            });

        } catch (Exception e) {
            Utilities.dismissDialog();

            Toast.makeText(mContext, "Something went wrong, Please try again.", Toast.LENGTH_SHORT).show();
        }
    }



    private void binddocumentType() {
        Utilities.showLoading(mContext);

        String token = SharedPrefUtil.getUniversalSharedPrefToken(mContext, SHARED_PREF_TOKEN, null);


        try {
            Call<MasterResponse> call = RetrofitUrlConnection.loadJSON(token).mastersdata();

            call.enqueue(new Callback<MasterResponse>() {
                @Override
                public void onResponse(Call<MasterResponse> call, Response<MasterResponse> response) {

                    if (response.body().getStatus().toString().equalsIgnoreCase("100")) {

                        List<OrderCanceleQuestion> otherdocumentList = new ArrayList<>();
                        otherdocumentList = response.body().getMasterData().getOrderCanceleQuestions();


                        arrayListDoctype = new ArrayList<>();
                        arrayListDoctype.add(new DocumentList(0, "Select Cancellation Reason "));

                        for (int i = 0; i < otherdocumentList.size(); i++) {

                            int docid = Integer.valueOf(otherdocumentList.get(i).getId());
                            String docname = otherdocumentList.get(i).getQuestion();
                            arrayListDoctype.add(new DocumentList(docid, docname));

                        }

                        arrayAdapterDoc = new DocumentAdapter(OrderCancellationActivity.this, R.layout.layout_profile, R.id.profile_name, arrayListDoctype);
                        spn_reason.setAdapter(arrayAdapterDoc);

                    } else {
                        Singleton.getInstance().showLongToast(mContext, response.body().getMessage());
                    }
                    Utilities.dismissDialog();
                    // PopulateKYCDetails();

                }

                @Override
                public void onFailure(Call<MasterResponse> call, Throwable t) {
                    Utilities.dismissDialog();
                    Toast.makeText(mContext, "Something went wrong, Please try again.", Toast.LENGTH_SHORT).show();
                }
            });

        } catch (Exception e) {
            Utilities.dismissDialog();
            Toast.makeText(mContext, "Something went wrong, Please try again.", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onRestart() {
        super.onRestart();
        initUI();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        /*Intent i=new Intent();
        i.setClass(mContext,OrderDetailsActivity.class);
        startActivity(i);*/

    }
}
