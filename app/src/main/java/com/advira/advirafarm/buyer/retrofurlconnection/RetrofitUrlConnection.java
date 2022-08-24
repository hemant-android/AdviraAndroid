package com.advira.advirafarm.buyer.retrofurlconnection;

import com.advira.advirafarm.buyer.constants.IConsts;
import org.jetbrains.annotations.NotNull;
import java.io.IOException;
import java.security.cert.CertificateException;
import java.util.concurrent.TimeUnit;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitUrlConnection implements IConsts {
    /*private static final String TAG = "ServiceGenerator";
    public static final String HEADER_CACHE_CONTROL = "Cache-Control";
    public static final String HEADER_PRAGMA = "Pragma";*/

    private static RetrofitUrlConnection instance;

    public static RetrofitUrlConnection getInstance(){
        if(instance == null){
            instance = new RetrofitUrlConnection();
        }
        return instance;
    }
    //private static final long cacheSize = 10 * 1024 * 1024; // 5 MB
    //development url
    //private static final String BASE_URL = "https://devapi.advira.in/api/auth/";
    //PreProduction url
    //private static final String BASE_URL = "https://preprodbuyer.advira.in/api/auth/";
    //production url
    private static final String BASE_URL = "https://buyer.advira.in/api/auth/";

    public static IMyApiEndpointInterface loadJSON(String token) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(getUnsafeOkHttpClient(token))
                .build();

        IMyApiEndpointInterface request = retrofit.create(IMyApiEndpointInterface.class);
        return request;

    }

    private static OkHttpClient getUnsafeOkHttpClient(String token) {

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        try {
            final TrustManager[] trustAllCerts = new TrustManager[] {
                    new X509TrustManager() {
                        @Override
                        public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                        }
                        @Override
                        public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                        }
                        @Override
                        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                            return new java.security.cert.X509Certificate[]{};
                        }
                    }
            };

            final SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
            // Create an ssl socket factory with our all-trusting manager
            final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

            OkHttpClient.Builder builder = new OkHttpClient.Builder().addInterceptor(logging);
            builder.sslSocketFactory(sslSocketFactory, (X509TrustManager)trustAllCerts[0]);
            builder.addInterceptor(new Interceptor() {
                @NotNull
                @Override
                public Response intercept(@NotNull Chain chain) throws IOException {
                    Request newRequest = chain.request().newBuilder()
                            .addHeader("Authorization", "Bearer " + token)
                            .build();
                    return chain.proceed(newRequest);
                }
            });
            builder.hostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });

            builder.connectTimeout(30, TimeUnit.SECONDS);
            builder.readTimeout(30, TimeUnit.SECONDS);
            builder.writeTimeout(30, TimeUnit.SECONDS);
            OkHttpClient okHttpClient = builder.build();
            return okHttpClient;

            //OkHttpClient okHttpClient = builder.build();
            //OkHttpClient OClient = new OkHttpClient();
            /*builder.connectTimeout(5, TimeUnit.MINUTES)
                    .writeTimeout(5, TimeUnit.MINUTES)
                    .readTimeout(5, TimeUnit.MINUTES);
            return OkHttpClient;*/

        }  catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
