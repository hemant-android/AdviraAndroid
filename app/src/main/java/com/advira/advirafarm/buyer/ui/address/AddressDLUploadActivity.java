package com.advira.advirafarm.buyer.ui.address;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.advira.advirafarm.buyer.R;
import com.advira.advirafarm.buyer.constants.IConsts;
import com.advira.advirafarm.buyer.imageupload.ImagePickerActivity;
import com.advira.advirafarm.buyer.ui.address.api.AddAddressKYCRequest;
import com.advira.advirafarm.buyer.ui.myaccount.PersonalDetailsEditActivity;
import com.advira.advirafarm.buyer.utility.SharedPrefUtil;
import com.advira.advirafarm.buyer.utility.Singleton;
import com.advira.advirafarm.buyer.utility.Utilities;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddressDLUploadActivity extends AppCompatActivity implements IConsts {

    private RelativeLayout rl_back;
    private Context mContext;
    private Button btn_submit;
    String chk = "";
    private RelativeLayout rl_1;
    private RelativeLayout rl_2;
    private RelativeLayout rl_3;
    private RelativeLayout rl_4;
    private RelativeLayout rl_5;
    private RelativeLayout rl_6;
    private Button btn_add1;
    private Button btn_add2;
    private Button btn_add3;
    private Button btn_add4;
    private Button btn_add5;
    private Button btn_upload1;
    private Button btn_upload2;
    private Button btn_upload3;
    private Button btn_upload4;
    private Button btn_upload5;
    private Button btn_upload6;
    private ImageView img_upload1;
    private ImageView img_upload2;
    private ImageView img_upload3;
    private ImageView img_upload4;
    private ImageView img_upload5;
    private ImageView img_upload6;

    public static final int REQUEST_IMAGE_1 = 100;
    public static final int REQUEST_IMAGE_2 = 200;
    public static final int REQUEST_IMAGE_3 = 300;
    public static final int REQUEST_IMAGE_4 = 400;
    public static final int REQUEST_IMAGE_5 = 500;
    public static final int REQUEST_IMAGE_6 = 600;

    private String base64String1 = "";
    private String base64String2 = "";
    private String base64String3 = "";
    private String base64String4 = "";
    private String base64String5 = "";
    private String base64String6 = "";

    AlertDialog.Builder builder;
    private Uri uri1, uri2, uri3, uri4, uri5, uri6;

    String addressid = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addressdlupload);
        ButterKnife.bind(this);

        initUI();

        rl_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent();
                intent.putExtra("status", "no");
                setResult(RESULT_OK, intent);
                finish();


            }
        });


        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (chk.length() > 0) {
                    builder = new AlertDialog.Builder(mContext);
                    builder.setMessage("New Address").setTitle("Document Uploaded Successfully");

                    builder.setMessage("Your Address document (Drug License) uploaded successfully. Your address will be activated soon")
                            .setCancelable(true)
                            .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {

                                    dialog.cancel();
                                    Intent intent = new Intent();
                                    intent.putExtra("status", "edit");
                                    setResult(RESULT_OK, intent);
                                    AddressDLUploadActivity.this.finish();

                                }
                            });

                    AlertDialog alert = builder.create();
                    alert.show();
                    alert.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(mContext.getResources().getColor(R.color.colorThemeDark));
                    alert.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(mContext.getResources().getColor(R.color.colorThemeDark));
                } else {
                    AlertDialog.Builder alert = new AlertDialog.Builder(AddressDLUploadActivity.this);
                    alert.setTitle("Alert!!");
                    alert.setMessage("Please upload required document.");
                    alert.setPositiveButton("OK",null);
                    alert.show();

                }
            }
        });


        btn_upload1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (base64String1.length() > 10) {
                    SaveKYCDetails(base64String1,1);
                } else {
                    Singleton.getInstance().showLongToast(mContext, "Please select image");
                }
            }
        });


        btn_upload2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (base64String2.length() > 10) {
                    SaveKYCDetails(base64String2,2);
                } else {
                    Singleton.getInstance().showLongToast(mContext, "Please select image");

                }

            }
        });

        btn_upload3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (base64String3.length() > 10) {
                    SaveKYCDetails(base64String3,3);
                } else {
                    Singleton.getInstance().showLongToast(mContext, "Please select image");

                }


            }
        });

        btn_upload4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (base64String4.length() > 10) {
                    SaveKYCDetails(base64String4,4);
                } else {
                    Singleton.getInstance().showLongToast(mContext, "Please select image");

                }


            }
        });

        btn_upload5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (base64String5.length() > 10) {
                    SaveKYCDetails(base64String5,5);
                } else {
                    Singleton.getInstance().showLongToast(mContext, "Please select image");

                }


            }
        });

        btn_upload6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (base64String6.length() > 10) {
                    SaveKYCDetails(base64String6,6);
                } else {
                    Singleton.getInstance().showLongToast(mContext, "Please select image");

                }


            }
        });

        btn_add1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                rl_2.setVisibility(View.VISIBLE);
                btn_add1.setVisibility(View.GONE);
                btn_add2.setVisibility(View.VISIBLE);

            }
        });


        btn_add2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                rl_3.setVisibility(View.VISIBLE);
                btn_add2.setVisibility(View.GONE);
                btn_add3.setVisibility(View.VISIBLE);

            }
        });

        btn_add3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                rl_4.setVisibility(View.VISIBLE);
                btn_add3.setVisibility(View.GONE);
                btn_add4.setVisibility(View.VISIBLE);

            }
        });

        btn_add4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                rl_5.setVisibility(View.VISIBLE);
                btn_add4.setVisibility(View.GONE);
                btn_add5.setVisibility(View.VISIBLE);

            }
        });

        btn_add5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                rl_6.setVisibility(View.VISIBLE);
                btn_add5.setVisibility(View.GONE);

            }
        });


    }


    private void initUI() {

        mContext = AddressDLUploadActivity.this;

        rl_back = findViewById(R.id.rl_back);

        btn_submit = findViewById(R.id.btn_submit);
        btn_upload1 = findViewById(R.id.btn_upload1);
        btn_upload2 = findViewById(R.id.btn_upload2);
        btn_upload3 = findViewById(R.id.btn_upload3);
        btn_upload4 = findViewById(R.id.btn_upload4);
        btn_upload5 = findViewById(R.id.btn_upload5);
        btn_upload6 = findViewById(R.id.btn_upload6);

        img_upload1 = findViewById(R.id.img_upload1);
        img_upload2 = findViewById(R.id.img_upload2);
        img_upload3 = findViewById(R.id.img_upload3);
        img_upload4 = findViewById(R.id.img_upload4);
        img_upload5 = findViewById(R.id.img_upload5);
        img_upload6 = findViewById(R.id.img_upload6);

        rl_1 = findViewById(R.id.rl_1);
        rl_2 = findViewById(R.id.rl_2);
        rl_3 = findViewById(R.id.rl_3);
        rl_4 = findViewById(R.id.rl_4);
        rl_5 = findViewById(R.id.rl_5);
        rl_6 = findViewById(R.id.rl_6);

        btn_add1 = findViewById(R.id.btn_add1);
        btn_add2 = findViewById(R.id.btn_add2);
        btn_add3 = findViewById(R.id.btn_add3);
        btn_add4 = findViewById(R.id.btn_add4);
        btn_add5 = findViewById(R.id.btn_add5);

        Bundle extras = getIntent().getExtras();
        addressid = "";

        if (extras != null) {
            addressid = extras.getString("addressid");

        }

    }


    @OnClick({R.id.img_upload1})
    void onProfileImageClick1() {
        Dexter.withActivity(this)
                .withPermissions(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        if (report.areAllPermissionsGranted()) {
                            showImagePickerOptions("image1");
                        }

                        if (report.isAnyPermissionPermanentlyDenied()) {
                            showSettingsDialog();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check();
    }

    @OnClick({R.id.img_upload2})
    void onProfileImageClick2() {
        Dexter.withActivity(this)
                .withPermissions(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        if (report.areAllPermissionsGranted()) {
                            showImagePickerOptions("image2");
                        }

                        if (report.isAnyPermissionPermanentlyDenied()) {
                            showSettingsDialog();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check();
    }

    @OnClick({R.id.img_upload3})
    void onProfileImageClick3() {
        Dexter.withActivity(this)
                .withPermissions(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        if (report.areAllPermissionsGranted()) {
                            showImagePickerOptions("image3");
                        }

                        if (report.isAnyPermissionPermanentlyDenied()) {
                            showSettingsDialog();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check();
    }


    @OnClick({R.id.img_upload4})
    void onProfileImageClick5() {
        Dexter.withActivity(this)
                .withPermissions(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        if (report.areAllPermissionsGranted()) {
                            showImagePickerOptions("image4");
                        }

                        if (report.isAnyPermissionPermanentlyDenied()) {
                            showSettingsDialog();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check();
    }


    @OnClick({R.id.img_upload5})
    void onProfileImageClick6() {
        Dexter.withActivity(this)
                .withPermissions(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        if (report.areAllPermissionsGranted()) {
                            showImagePickerOptions("image5");
                        }

                        if (report.isAnyPermissionPermanentlyDenied()) {
                            showSettingsDialog();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check();
    }

    @OnClick({R.id.img_upload6})
    void onProfileImageClick7() {
        Dexter.withActivity(this)
                .withPermissions(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        if (report.areAllPermissionsGranted()) {
                            showImagePickerOptions("image6");
                        }

                        if (report.isAnyPermissionPermanentlyDenied()) {
                            showSettingsDialog();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check();
    }


    private void showImagePickerOptions(String pic) {
        ImagePickerActivity.showImagePickerOptions1(this, new ImagePickerActivity.PickerOptionListener() {
            @Override
            public void onTakeCameraSelected() {
                launchCameraIntent(pic);
            }

            @Override
            public void onChooseGallerySelected() {
                launchGalleryIntent(pic);
            }
        });
    }

    private void launchCameraIntent(String picFor) {
        Intent intent = new Intent(mContext, ImagePickerActivity.class);
        intent.putExtra(ImagePickerActivity.INTENT_IMAGE_PICKER_OPTION, ImagePickerActivity.REQUEST_IMAGE_CAPTURE);

        // setting aspect ratio
        intent.putExtra(ImagePickerActivity.INTENT_LOCK_ASPECT_RATIO, false);
        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_X, 1); // 16x9, 1x1, 3:4, 3:2
        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_Y, 1);

        // setting maximum bitmap width and height
        intent.putExtra(ImagePickerActivity.INTENT_SET_BITMAP_MAX_WIDTH_HEIGHT, true);
        intent.putExtra(ImagePickerActivity.INTENT_BITMAP_MAX_WIDTH, 1000);
        intent.putExtra(ImagePickerActivity.INTENT_BITMAP_MAX_HEIGHT, 1000);

        if (picFor.equals("image1")) {
            startActivityForResult(intent, REQUEST_IMAGE_1);
        } else if (picFor.equals("image2")) {
            startActivityForResult(intent, REQUEST_IMAGE_2);
        } else if (picFor.equals("image3")) {
            startActivityForResult(intent, REQUEST_IMAGE_3);
        } else if (picFor.equals("image4")) {
            startActivityForResult(intent, REQUEST_IMAGE_4);
        } else if (picFor.equals("image5")) {
            startActivityForResult(intent, REQUEST_IMAGE_5);
        } else if (picFor.equals("image6")) {
            startActivityForResult(intent, REQUEST_IMAGE_6);
        }

    }

    private void launchGalleryIntent(String picFor) {
        Intent intent = new Intent(mContext, ImagePickerActivity.class);
        intent.putExtra(ImagePickerActivity.INTENT_IMAGE_PICKER_OPTION, ImagePickerActivity.REQUEST_GALLERY_IMAGE);

        // setting aspect ratio
        intent.putExtra(ImagePickerActivity.INTENT_LOCK_ASPECT_RATIO, false);
        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_X, 1); // 16x9, 1x1, 3:4, 3:2
        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_Y, 1);


        if (picFor.equals("image1")) {
            startActivityForResult(intent, REQUEST_IMAGE_1);
        } else if (picFor.equals("image2")) {
            startActivityForResult(intent, REQUEST_IMAGE_2);
        } else if (picFor.equals("image3")) {
            startActivityForResult(intent, REQUEST_IMAGE_3);
        } else if (picFor.equals("image4")) {
            startActivityForResult(intent, REQUEST_IMAGE_4);
        } else if (picFor.equals("image5")) {
            startActivityForResult(intent, REQUEST_IMAGE_5);
        } else if (picFor.equals("image6")) {
            startActivityForResult(intent, REQUEST_IMAGE_6);
        }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_1) {
            if (resultCode == Activity.RESULT_OK) {
                uri1 = data.getParcelableExtra("path");


                try {
                    // You can update this bitmap to your server
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri1);
                    base64String1 = convert(bitmap);
                    base64String1 = base64String1.replaceAll("\n", "");

                    Picasso.with(this).load(uri1.toString())
                            .into(img_upload1);
                    img_upload1.setColorFilter(ContextCompat.getColor(this, android.R.color.transparent));

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        if (requestCode == REQUEST_IMAGE_2) {
            if (resultCode == Activity.RESULT_OK) {
                uri2 = data.getParcelableExtra("path");


                try {
                    // You can update this bitmap to your server
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri2);
                    base64String2 = convert(bitmap);
                    base64String2 = base64String2.replaceAll("\n", "");

                    Picasso.with(this).load(uri2.toString())
                            .into(img_upload2);
                    img_upload2.setColorFilter(ContextCompat.getColor(this, android.R.color.transparent));

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        if (requestCode == REQUEST_IMAGE_3) {
            if (resultCode == Activity.RESULT_OK) {
                uri3 = data.getParcelableExtra("path");


                try {
                    // You can update this bitmap to your server
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri3);
                    base64String3 = convert(bitmap);
                    base64String3 = base64String3.replaceAll("\n", "");

                    Picasso.with(this).load(uri3.toString())
                            .into(img_upload3);
                    img_upload3.setColorFilter(ContextCompat.getColor(this, android.R.color.transparent));

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        if (requestCode == REQUEST_IMAGE_4) {
            if (resultCode == Activity.RESULT_OK) {
                uri4 = data.getParcelableExtra("path");


                try {
                    // You can update this bitmap to your server
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri4);
                    base64String4 = convert(bitmap);
                    base64String4 = base64String4.replaceAll("\n", "");

                    Picasso.with(this).load(uri4.toString())
                            .into(img_upload4);
                    img_upload4.setColorFilter(ContextCompat.getColor(this, android.R.color.transparent));

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        if (requestCode == REQUEST_IMAGE_5) {
            if (resultCode == Activity.RESULT_OK) {
                uri5 = data.getParcelableExtra("path");


                try {
                    // You can update this bitmap to your server
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri5);
                    base64String5 = convert(bitmap);
                    base64String5 = base64String5.replaceAll("\n", "");

                    Picasso.with(this).load(uri5.toString())
                            .into(img_upload5);
                    img_upload5.setColorFilter(ContextCompat.getColor(this, android.R.color.transparent));

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        if (requestCode == REQUEST_IMAGE_6) {
            if (resultCode == Activity.RESULT_OK) {
                uri6 = data.getParcelableExtra("path");


                try {
                    // You can update this bitmap to your server
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri6);
                    base64String6 = convert(bitmap);
                    base64String6 = base64String6.replaceAll("\n", "");

                    Picasso.with(this).load(uri6.toString())
                            .into(img_upload6);
                    img_upload6.setColorFilter(ContextCompat.getColor(this, android.R.color.transparent));

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }


    private void showSettingsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle(getString(R.string.dialog_permission_title));
        builder.setMessage(getString(R.string.dialog_permission_message));
        builder.setPositiveButton(getString(R.string.go_to_settings), (dialog, which) -> {
            dialog.cancel();
            openSettings();
        });
        builder.setNegativeButton(getString(android.R.string.cancel), (dialog, which) -> dialog.cancel());
        builder.show();

    }

    // navigating user to app settings
    private void openSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getPackageName(), null);
        intent.setData(uri);
        startActivityForResult(intent, 101);
    }

    public static Bitmap convert(String base64Str) throws IllegalArgumentException {
        byte[] decodedBytes = Base64.decode(
                base64Str.substring(base64Str.indexOf(",") + 1),
                Base64.DEFAULT
        );

        return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
    }

    public static String convert(Bitmap bitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 10, outputStream);

        return Base64.encodeToString(outputStream.toByteArray(), Base64.DEFAULT);
    }


    private void SaveKYCDetails(String basestring,int step) {

        Utilities.showLoading(mContext);


        AddAddressKYCRequest kycDocumentRequest = new AddAddressKYCRequest();
        kycDocumentRequest.setApiType("add");
        kycDocumentRequest.setAddressId(addressid);
        kycDocumentRequest.setDocumentImagename(basestring);

        String token = SharedPrefUtil.getUniversalSharedPrefToken(mContext, SHARED_PREF_TOKEN, null);

       /* Call<AddAddressKYCResponse> call = RetrofitUrlConnection.loadJSON(token).newaddresskyc(kycDocumentRequest);

        call.enqueue(new Callback<AddAddressKYCResponse>() {
            @Override
            public void onResponse(Call<AddAddressKYCResponse> call, Response<AddAddressKYCResponse> response) {

                if (response.body().getStatus().toString().equalsIgnoreCase("100")) {

                    Utilities.dismissDialog();
                    Singleton.getInstance().showShortToast(mContext, response.body().getMessage());
                    chk = "done";

                    try
                    {
                        switch(step) {

                            case 1:
                                btn_upload1.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_check_black_24dp, 0);
                                btn_upload1.setEnabled(false);
                                img_upload1.setEnabled(false);
                                btn_upload1.setText("Done");
                                break;

                            case 2:
                                btn_upload2.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_check_black_24dp, 0);
                                btn_upload2.setEnabled(false);
                                img_upload2.setEnabled(false);
                                btn_upload2.setText("Done");
                                break;

                            case 3:
                                btn_upload3.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_check_black_24dp, 0);
                                btn_upload3.setEnabled(false);
                                img_upload3.setEnabled(false);
                                btn_upload3.setText("Done");
                                break;

                            case 4:
                                btn_upload4.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_check_black_24dp, 0);
                                btn_upload4.setEnabled(false);
                                img_upload4.setEnabled(false);
                                btn_upload4.setText("Done");
                                break;

                            case 5:
                                btn_upload5.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_check_black_24dp, 0);
                                btn_upload5.setEnabled(false);
                                img_upload5.setEnabled(false);
                                btn_upload5.setText("Done");
                                break;

                            case 6:
                                btn_upload6.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_check_black_24dp, 0);
                                btn_upload6.setEnabled(false);
                                img_upload6.setEnabled(false);
                                btn_upload6.setText("Done");
                                break;


                            default:
                                // setContentView(R.layout.default);
                                break;
                        }
                    }
                    catch (Exception ex)
                    {

                    }


                } else {
                    Utilities.dismissDialog();
                    Singleton.getInstance().showShortToast(mContext, response.body().getMessage());
                }

                Utilities.dismissDialog();
            }

            @Override
            public void onFailure(Call<AddAddressKYCResponse> call, Throwable t) {

                Utilities.dismissDialog();
            }
        });*/


    }


    public void onBackPressed() {

        super.onBackPressed();
        actionOnBackPress();

    }

    private void actionOnBackPress() {

        AddressDLUploadActivity.this.finish();
    }

}