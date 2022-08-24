package com.advira.advirafarm.buyer.retrofurlconnection;
import com.advira.advirafarm.buyer.ui.address.api.AddAddressRequest;
import com.advira.advirafarm.buyer.ui.address.api.AddAddressResponse;
import com.advira.advirafarm.buyer.ui.address.api.AddressListRequest;
import com.advira.advirafarm.buyer.ui.address.api.AddressListResponse;
import com.advira.advirafarm.buyer.ui.address.api.DefaultAddressRequest;
import com.advira.advirafarm.buyer.ui.address.api.DeleteAddressResponse;
import com.advira.advirafarm.buyer.ui.address.api.UpdateAddressRequest;
import com.advira.advirafarm.buyer.ui.cart.api.BuyNowRequest;
import com.advira.advirafarm.buyer.ui.cart.api.BuyNowResponse;
import com.advira.advirafarm.buyer.ui.cart.api.CartDeleteRequest;
import com.advira.advirafarm.buyer.ui.cart.api.CartDeleteResponse;
import com.advira.advirafarm.buyer.ui.cart.api.CartListRequest;
import com.advira.advirafarm.buyer.ui.cart.api.CartListResponse;
import com.advira.advirafarm.buyer.ui.cart.api.CartRequest;
import com.advira.advirafarm.buyer.ui.cart.api.CartResponse;
import com.advira.advirafarm.buyer.ui.cart.api.DiscountRequest;
import com.advira.advirafarm.buyer.ui.cart.api.DiscountResponse;
import com.advira.advirafarm.buyer.ui.discount.api.CouponValidationRequest;
import com.advira.advirafarm.buyer.ui.discount.api.CouponValidationResponse;
import com.advira.advirafarm.buyer.ui.discount.api.DiscountListResponse;
import com.advira.advirafarm.buyer.ui.forgotpassword.api.ChangePasswordRequest;
import com.advira.advirafarm.buyer.ui.forgotpassword.api.ChangePasswordResponse;
import com.advira.advirafarm.buyer.ui.forgotpassword.api.ForgotPasswordResponse;
import com.advira.advirafarm.buyer.ui.login.api.LoginRequest;
import com.advira.advirafarm.buyer.ui.login.api.LoginResponse;
import com.advira.advirafarm.buyer.ui.login.api.LogoutResponse;
import com.advira.advirafarm.buyer.ui.masterapi.MasterResponse;
import com.advira.advirafarm.buyer.ui.myaccount.api.CancelmembershipResponse;
import com.advira.advirafarm.buyer.ui.myaccount.api.EmailVerifyRequest;
import com.advira.advirafarm.buyer.ui.myaccount.api.EmailVerifyResponse;
import com.advira.advirafarm.buyer.ui.myaccount.api.IsUserVerifiedResponse;
import com.advira.advirafarm.buyer.ui.myaccount.api.MemberPlanResponse;
import com.advira.advirafarm.buyer.ui.myaccount.api.PinSuggestionRequest;
import com.advira.advirafarm.buyer.ui.myaccount.api.PinSuggestionResponse;
import com.advira.advirafarm.buyer.ui.myaccount.api.ProfilePictureRequest;
import com.advira.advirafarm.buyer.ui.myaccount.api.ProfilePictureResponse;
import com.advira.advirafarm.buyer.ui.notification.api.NotificationDeleteRequest;
import com.advira.advirafarm.buyer.ui.notification.api.NotificationDeleteResponse;
import com.advira.advirafarm.buyer.ui.notification.api.NotificationResponse;
import com.advira.advirafarm.buyer.ui.order.api.OrderCancelRequest;
import com.advira.advirafarm.buyer.ui.order.api.OrderCancelResponse;
import com.advira.advirafarm.buyer.ui.order.api.OrderDetailsRequest;
import com.advira.advirafarm.buyer.ui.order.api.OrderDetailsResponse;
import com.advira.advirafarm.buyer.ui.order.api.OrderListResponse;
import com.advira.advirafarm.buyer.ui.order.api.OrderListResponse_v2;
import com.advira.advirafarm.buyer.ui.order.api.OrderPlacedRequest;
import com.advira.advirafarm.buyer.ui.order.api.OrderPlacedResponse;
import com.advira.advirafarm.buyer.ui.order.api.PaymentRequest;
import com.advira.advirafarm.buyer.ui.order.api.PaymentResponse;
import com.advira.advirafarm.buyer.ui.payment.api.MpaymentreceivedRequest;
import com.advira.advirafarm.buyer.ui.payment.api.MpaymentreceivedResponse;
import com.advira.advirafarm.buyer.ui.payment.api.PGPaymentRequest;
import com.advira.advirafarm.buyer.ui.payment.api.PGPaymentResponse;
import com.advira.advirafarm.buyer.ui.payment.api.RetryOrderRequest;
import com.advira.advirafarm.buyer.ui.payment.api.RzpayMemPayInitRequest;
import com.advira.advirafarm.buyer.ui.payment.api.RzpayOrderInitRequest;
import com.advira.advirafarm.buyer.ui.payment.api.RzpayOrderInitResponse;
import com.advira.advirafarm.buyer.ui.payment.razorpay.api.BankEMIRequest;
import com.advira.advirafarm.buyer.ui.payment.razorpay.api.BankEMIResponse;
import com.advira.advirafarm.buyer.ui.payment.razorpay.api.BankNoCostEMIResponse;
import com.advira.advirafarm.buyer.ui.product.api.CheckPinRequest;
import com.advira.advirafarm.buyer.ui.product.api.CheckPinResponse;
import com.advira.advirafarm.buyer.ui.product.api.DashboardBannerResponse;
import com.advira.advirafarm.buyer.ui.product.api.ProductDetailsRequest;
import com.advira.advirafarm.buyer.ui.product.api.ProductDetailsResponse;
import com.advira.advirafarm.buyer.ui.product.api.ProductListResponse;
import com.advira.advirafarm.buyer.ui.product.api.SearchRequest;
import com.advira.advirafarm.buyer.ui.product.categoryapi.CategoryListResponse;
import com.advira.advirafarm.buyer.ui.product.categoryapi.CategoryResponse;
import com.advira.advirafarm.buyer.ui.product.categoryapi.HomepageResponse;
import com.advira.advirafarm.buyer.ui.product.categoryapi.ProductSearchResponse;
import com.advira.advirafarm.buyer.ui.product.categoryapi.ProductbycategoryListRequest;
import com.advira.advirafarm.buyer.ui.product.categoryapi.ProductbycategoryListResponse;
import com.advira.advirafarm.buyer.ui.registration.mobileotp.api.MobileOTPRequest;
import com.advira.advirafarm.buyer.ui.registration.mobileotp.api.MobileOTPResponse;
import com.advira.advirafarm.buyer.ui.registration.mobileotp.api.OTPVerifyRequest;
import com.advira.advirafarm.buyer.ui.registration.mobileotp.api.OTPVerifyResponse;
import com.advira.advirafarm.buyer.ui.registration.profile.api.BusinessProfileRequest;
import com.advira.advirafarm.buyer.ui.registration.profile.api.BusinessProfileResponse;
import com.advira.advirafarm.buyer.ui.registration.profile.api.KYCDocumentRequest;
import com.advira.advirafarm.buyer.ui.registration.profile.api.KYCDocumentResponse;
import com.advira.advirafarm.buyer.ui.registration.profile.api.UserProfileRequest;
import com.advira.advirafarm.buyer.ui.registration.profile.api.UserProfileResponse;
import com.advira.advirafarm.buyer.ui.registration.profile.api.me.MeResponse;
import com.advira.advirafarm.buyer.ui.splash.api.AppInstallRequest;
import com.advira.advirafarm.buyer.ui.splash.api.AppInstallResponse;
import com.advira.advirafarm.buyer.ui.splash.api.SessionResponse;
import com.advira.advirafarm.buyer.ui.subscrption.api.AddsubscriptionRequest;
import com.advira.advirafarm.buyer.ui.subscrption.api.AddsubscriptionResponse;
import com.advira.advirafarm.buyer.ui.subscrption.api.BasketCartDeleteRequest;
import com.advira.advirafarm.buyer.ui.subscrption.api.ConfirmSubscriptionRequest;
import com.advira.advirafarm.buyer.ui.subscrption.api.ConfirmSubscriptionResponse;
import com.advira.advirafarm.buyer.ui.subscrption.api.DailyBasketCartDeleteRequest;
import com.advira.advirafarm.buyer.ui.subscrption.api.DailyBasketCartDeleteResponse;
import com.advira.advirafarm.buyer.ui.subscrption.api.DailyBasketCartRequest;
import com.advira.advirafarm.buyer.ui.subscrption.api.DailyBasketCartResponse;
import com.advira.advirafarm.buyer.ui.subscrption.api.DailyBasketListRequest;
import com.advira.advirafarm.buyer.ui.subscrption.api.DailyBasketListResponse;
import com.advira.advirafarm.buyer.ui.subscrption.api.DailyBasketResponse;
import com.advira.advirafarm.buyer.ui.subscrption.api.DateSubscriptionDetailResponse;
import com.advira.advirafarm.buyer.ui.subscrption.api.SubscriptionDailyBasketCartResponse;
import com.advira.advirafarm.buyer.ui.subscrption.api.SubscriptionDetailResponse;
import com.advira.advirafarm.buyer.ui.subscrption.api.UpdatesubscriptionRequest;
import com.advira.advirafarm.buyer.ui.subscrption.api.UpdatesubscriptionstatusRequest;
import com.advira.advirafarm.buyer.ui.subscrption.api.UpdatesubscriptionstatusResponse;
import com.advira.advirafarm.buyer.ui.wallet.api.MywalletpassbookResponse;
import com.advira.advirafarm.buyer.ui.wallet.api.WalletOrderInitRequest;
import com.advira.advirafarm.buyer.ui.wallet.api.WalletOrderInitResponse;
import com.advira.advirafarm.buyer.ui.wallet.api.WalletPaymentRequest;
import com.advira.advirafarm.buyer.ui.wallet.api.WalletPaymentResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface IMyApiEndpointInterface {

    @POST("appInstall")
    Call<AppInstallResponse> appInstall(@Body AppInstallRequest user);

    @POST("checksession")
    Call<SessionResponse> checksession();

    @POST("login")
    Call<LoginResponse> login(@Body LoginRequest user);

    @POST("pincodebycity")
    Call<PinSuggestionResponse> pincodebycity(@Body PinSuggestionRequest user);

    @POST("isuserverified")
    Call<IsUserVerifiedResponse> isuserverified();

    @POST("userRegistration")
    Call<MobileOTPResponse> userRegistration(@Body MobileOTPRequest user);

    @POST("verifyMobileNo")
    Call<OTPVerifyResponse> verifyMobileNo(@Body OTPVerifyRequest user);

    @POST("verifyemail")
    Call<EmailVerifyResponse> verifyemail(@Body EmailVerifyRequest user);

    @POST("forgotpassword")
    Call<ForgotPasswordResponse> forgotpassword(@Body MobileOTPRequest user);

    @POST("changepassword")
    Call<ChangePasswordResponse> changepassword(@Body ChangePasswordRequest user);

    @POST("userprofile")
    Call<UserProfileResponse> userprofile(@Body UserProfileRequest user);

    @GET("mastersdata")
    Call<MasterResponse> mastersdata();

    @POST("businessprofile")
    Call<BusinessProfileResponse> businessprofile(@Body BusinessProfileRequest user);

    @POST("kycdocument")
    Call<KYCDocumentResponse> kycdocument(@Body KYCDocumentRequest user);

    @POST("me")
    Call<MeResponse> me();

    @POST("update_profile_picture")
    Call<ProfilePictureResponse> update_profile_picture(@Body ProfilePictureRequest user);

    @POST("logout")
    Call<LogoutResponse> logout();

    @GET("productcategorywiseb2c")
    Call<CategoryListResponse> productcategorywiseb2c();

    @GET("productcategorywiseb2b")
    Call<CategoryListResponse> productcategorywiseb2b();

    @POST("dashboardbanners")
    Call<DashboardBannerResponse> dashboardbanners();

    @GET("dashboardbannersnotoken")
    Call<DashboardBannerResponse> dashboardbannersnotoken();

    @GET("categorynotoken")
    Call<CategoryResponse> categorynotoken();

    @POST("pincode")
    Call<CheckPinResponse> pincode(@Body CheckPinRequest user);

    @POST("productbycategoryidb2c")
    Call<ProductbycategoryListResponse> productbycategoryidb2c(@Body ProductbycategoryListRequest user);

    @POST("productbycategoryidb2b")
    Call<ProductbycategoryListResponse> productbycategoryidb2b(@Body ProductbycategoryListRequest user);

    @POST("product_detail_categorywise_b2c")
    Call<ProductDetailsResponse> product_detail_categorywise_b2c(@Body ProductDetailsRequest user);

    @POST("product_detail_categorywise_b2b")
    Call<ProductDetailsResponse> product_detail_categorywise_b2b(@Body ProductDetailsRequest user);

    @POST("products")
    Call<ProductListResponse> products(@Body SearchRequest user);
    //Call<ProductListResponse> products();

    @POST("deleteaddress")
    Call<DeleteAddressResponse> deleteaddress(@Body DefaultAddressRequest user);

    @POST("addresslist")
    Call<AddressListResponse> addresslist(@Body AddressListRequest user);

    @POST("updateaddress")
    Call<AddAddressResponse> updateaddress(@Body UpdateAddressRequest user);

    @POST("setdefaultaddress")
    Call<AddAddressResponse> setdefaultaddress(@Body DefaultAddressRequest user);

    @POST("addnewaddress")
    Call<AddAddressResponse> addnewaddress(@Body AddAddressRequest user);

    @POST("addtocart")
    Call<CartResponse> addtocart(@Body CartRequest user);

    @POST("getmycart")
    Call<CartListResponse> getmycart(@Body CartListRequest user);

    @POST("updatecart")
    Call<CartResponse> updatecart(@Body CartRequest user);

    @POST("delete_from_cart")
    Call<CartDeleteResponse> delete_from_cart(@Body CartDeleteRequest user);

    @POST("buynow")
    Call<BuyNowResponse> buynow(@Body BuyNowRequest user);

    @POST("discountB2B")
    Call<DiscountResponse> discountB2B(@Body DiscountRequest user);

    @POST("discount")
    Call<DiscountListResponse> discount();

    @POST("discountvalidation")
    Call<CouponValidationResponse> discountvalidation(@Body CouponValidationRequest user);

    @POST("notification")
    Call<NotificationResponse> notification();

    @POST("deletenotification")
    Call<NotificationDeleteResponse> deletenotification(@Body NotificationDeleteRequest user);

    @POST("proceedneworder")
    Call<OrderPlacedResponse> proceedneworder(@Body OrderPlacedRequest user);

    @POST("rzpayOrderInit")
    Call<RzpayOrderInitResponse> rzpayOrderInit(@Body RzpayOrderInitRequest user);

    @POST("addpaymentdetails")
    Call<PGPaymentResponse> addpgpaymentdetails(@Body PGPaymentRequest user);

    @POST("retryorder")
    Call<OrderPlacedResponse> retryorder(@Body RetryOrderRequest user);

    @POST("myorderlist")
    Call<OrderListResponse> myorderlist();

    @POST("myorderdetail")
    Call<OrderDetailsResponse> myorderdetail(@Body OrderDetailsRequest user);

    @POST("addpaymentdetails")
    Call<PaymentResponse> addpaymentdetails(@Body PaymentRequest user);

    @POST("canclemyorder")
    Call<OrderCancelResponse> canclemyorder(@Body OrderCancelRequest user);

    @POST("bankemi")
    Call<BankEMIResponse> bankemi(@Body BankEMIRequest user);

    @POST("nocostemi")
    Call<BankNoCostEMIResponse> nocostemi(@Body BankEMIRequest user);

    //version-2 API's
    @GET("homepage_v2")
    Call<HomepageResponse> homepage_v2();

    @POST("productbycategoryidb2c_v2")
    Call<ProductbycategoryListResponse> productbycategoryidb2c_v2(@Body ProductbycategoryListRequest user, @Query("page") int page, @Query("limit") int limit);

    @GET("productsearch")
    Call<ProductSearchResponse> productsearch();

    @POST("orderlist")
    Call<OrderListResponse_v2> orderlist();

    @GET("memberplan")
    Call<MemberPlanResponse> memberplan();

    @POST("memberpaymentinit")
    Call<RzpayOrderInitResponse> memberpaymentinit(@Body RzpayMemPayInitRequest user);

    @POST("memberpaymentreceived")
    Call<MpaymentreceivedResponse> memberpaymentreceived(@Body MpaymentreceivedRequest user);

    @POST("cancelmembership")
    Call<CancelmembershipResponse> cancelmembership();

    //Subscription API's
    @POST("dailybasketproducts")
    Call<DailyBasketResponse> dailybasketproducts();

    @POST("addsubscription")
    Call<AddsubscriptionResponse> addsubscription(@Body AddsubscriptionRequest user);

    @POST("updatesubscription")
    Call<AddsubscriptionResponse> updatesubscription(@Body UpdatesubscriptionRequest user);

    @POST("addtodailybasket")
    Call<DailyBasketCartResponse> addtodailybasket(@Body DailyBasketCartRequest user);

    @POST("getmydailybasket")
    Call<DailyBasketListResponse> getmydailybasket(@Body DailyBasketListRequest user);

    @POST("updatedailybasket")
    Call<DailyBasketCartResponse> updatedailybasket(@Body DailyBasketCartRequest user);

    @POST("delete_from_dailybasket")
    Call<DailyBasketCartDeleteResponse> delete_from_dailybasket(@Body BasketCartDeleteRequest user);

    @POST("confirmsubscription")
    Call<ConfirmSubscriptionResponse> confirmsubscription(@Body ConfirmSubscriptionRequest user);

    @POST("getmysubscriptiondetail")
    Call<SubscriptionDetailResponse> getmysubscriptiondetail(/*@Body ConfirmSubscriptionRequest user*/);

    @POST("getmysubscriptiondetail_bydate")
    Call<DateSubscriptionDetailResponse> getmysubscriptiondetail_bydate(@Body ConfirmSubscriptionRequest user);

    @POST("addproducttosubscription")
    Call<SubscriptionDailyBasketCartResponse> addproducttosubscription(@Body DailyBasketCartRequest user);

    @POST("updateproducttosubscription")
    Call<SubscriptionDailyBasketCartResponse> updateproducttosubscription(@Body DailyBasketCartRequest user);

    @POST("cancelproductfromsubscription")
    Call<DailyBasketCartDeleteResponse> cancelproductfromsubscription(@Body DailyBasketCartDeleteRequest user);

    @POST("updatesubscriptionstatus")
    Call<UpdatesubscriptionstatusResponse> updatesubscriptionstatus(@Body UpdatesubscriptionstatusRequest user);


    //Wallet Api's
    @POST("initwallet")
    Call<WalletOrderInitResponse> initwallet(@Body WalletOrderInitRequest user);

    @POST("addwalletpayment")
    Call<WalletPaymentResponse> addwalletpayment(@Body WalletPaymentRequest user);


    @POST("mywalletpassbook")
    Call<MywalletpassbookResponse> mywalletpassbook();

}
