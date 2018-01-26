package com.blogspot.mowael.retrofitcore;

import com.blogspot.mowael.retrofitcore.utils.MoConfig;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by moham on 10/6/2017.
 */

/**
 * for more information about converter factory you can visit
 */
public class RetrofitBase {


    private static RetrofitBase instance;

    /**
     * retrofit instance
     */
    private Retrofit retrofit;

    private OkHttpClient.Builder httpClient;


    private RetrofitBase(String baseUrl) {
        this(baseUrl, GsonConverterFactory.create());
    }

    private RetrofitBase(HttpUrl baseUrl) {
        this(baseUrl, GsonConverterFactory.create());
    }

    /**
     * @param baseUrl base url
     * @param factory factory converter @see <a href="https://futurestud.io/tutorials/retrofit-2-introduction-to-multiple-converters">Introduction to (Multiple) Converters</a>
     */
    private RetrofitBase(String baseUrl, Converter.Factory factory) {
        httpClient = new OkHttpClient.Builder();
        setUpLogger();
        Retrofit.Builder builder = new Retrofit.Builder().baseUrl(baseUrl).addConverterFactory(factory);
        retrofit = builder.client(httpClient.build()).build();
    }

    /**
     * @param baseUrl base url
     * @param factory factory converter @see <a href="https://futurestud.io/tutorials/retrofit-2-introduction-to-multiple-converters">Introduction to (Multiple) Converters</a>
     */
    private RetrofitBase(HttpUrl baseUrl, Converter.Factory factory) {
        httpClient = new OkHttpClient.Builder();
        setUpLogger();
        Retrofit.Builder builder = new Retrofit.Builder().baseUrl(baseUrl).addConverterFactory(factory);
        retrofit = builder.client(httpClient.build()).build();
    }

    private RetrofitBase(OkHttpClient.Builder httpClient, String baseUrl, Converter.Factory factory) {
        this.httpClient = httpClient;
        setUpLogger();
        Retrofit.Builder builder = new Retrofit.Builder().baseUrl(baseUrl).addConverterFactory(factory);
        retrofit = builder.client(httpClient.build()).build();
    }

    /**
     * reinitialize retrofitBase with custom OkHttpClient.Builder
     *
     * @param httpClient custom http client
     * @param baseUrl    base url
     * @param factory    custom response converter
     * @return retrofit base instance
     */
    public static RetrofitBase newInstance(OkHttpClient.Builder httpClient, String baseUrl, Converter.Factory factory) {
        if (instance != null) instance = null;
        instance = new RetrofitBase(httpClient, baseUrl, factory);
        return instance;
    }

    /**
     * reinitialize retrofitBase with custom OkHttpClient.Builder and GsonConverterFactory as default converter
     *
     * @param httpClient custom http client
     * @param baseUrl    base url
     * @return retrofit base instance
     */
    public static RetrofitBase newInstance(OkHttpClient.Builder httpClient, String baseUrl) {
        if (instance != null) instance = null;
        instance = new RetrofitBase(httpClient, baseUrl, GsonConverterFactory.create());
        return instance;
    }

    /**
     * @param baseUrl base url
     * @return RetrofitBase instance with GsonConverterFactory converter and disabled debug mode
     */
    public static RetrofitBase initialize(String baseUrl) {
        return initialize(baseUrl, false);
    }

    /**
     * @param baseUrl base url
     * @return RetrofitBase instance with GsonConverterFactory converter and disabled debug mode
     */
    public static RetrofitBase initialize(HttpUrl baseUrl) {
        return initialize(baseUrl, false);
    }

    /**
     * @param baseUrl base url
     * @param debug   true -> enables debug mode
     * @return RetrofitBase instance with GsonConverterFactory converter
     */
    public static RetrofitBase initialize(String baseUrl, boolean debug) {
        return initialize(baseUrl, GsonConverterFactory.create(), debug);
    }

    /**
     * @param baseUrl base url
     * @param debug   true -> enables debug mode
     * @return RetrofitBase instance with GsonConverterFactory converter
     */
    public static RetrofitBase initialize(HttpUrl baseUrl, boolean debug) {
        return initialize(baseUrl, GsonConverterFactory.create(), debug);
    }

    /**
     * @param baseUrl base url
     * @param factory factory converter @see <a href="https://futurestud.io/tutorials/retrofit-2-introduction-to-multiple-converters">Introduction to (Multiple) Converters</a>
     * @return RetrofitBase instance with disabled debug mode
     */
    public static RetrofitBase initialize(String baseUrl, Converter.Factory factory) {
        return initialize(baseUrl, factory, false);
    }

    /**
     * @param baseUrl base url
     * @param factory factory converter @see <a href="https://futurestud.io/tutorials/retrofit-2-introduction-to-multiple-converters">Introduction to (Multiple) Converters</a>
     * @return RetrofitBase instance with disabled debug mode
     */
    public static RetrofitBase initialize(HttpUrl baseUrl, Converter.Factory factory) {
        return initialize(baseUrl, factory, false);
    }

    /**
     * @param baseUrl base url
     * @param factory factory converter @see <a href="https://futurestud.io/tutorials/retrofit-2-introduction-to-multiple-converters">Introduction to (Multiple) Converters</a>
     * @param debug   true -> enables debug mode
     * @return RetrofitBase instance
     */
    public static RetrofitBase initialize(String baseUrl, Converter.Factory factory, boolean debug) {
        if (instance == null) {
            MoConfig.LOG_TOGGLE = debug;
            instance = new RetrofitBase(baseUrl, factory);
        }
        return instance;
    }

    /**
     * @param baseUrl base url
     * @param factory factory converter @see <a href="https://futurestud.io/tutorials/retrofit-2-introduction-to-multiple-converters">Introduction to (Multiple) Converters</a>
     * @param debug   true -> enables debug mode
     * @return RetrofitBase instance
     */
    public static RetrofitBase initialize(HttpUrl baseUrl, Converter.Factory factory, boolean debug) {
        if (instance == null) {
            MoConfig.LOG_TOGGLE = debug;
            instance = new RetrofitBase(baseUrl, factory);
        }
        return instance;
    }

    /**
     * @return instance of RetrofitBase
     * @throws NullPointerException if the RetrofitBase wasn't initialized
     */
    public static RetrofitBase getInstance() {
        if (instance == null) {
            throw new NullPointerException("you have to call RetrofitBase.initialize() first");
        }
        return instance;
    }

    public void setUpLogger() {
        if (httpClient != null)
            if (MoConfig.LOG_TOGGLE) {
                httpClient.addInterceptor(newBodyLogger());
                httpClient.addInterceptor(newHeaderLogger());
            }
    }

    private Interceptor newHeaderLogger() {
        return newLogger(HttpLoggingInterceptor.Level.HEADERS);
    }

    private HttpLoggingInterceptor newBodyLogger() {
        return newLogger(HttpLoggingInterceptor.Level.BODY);
    }

    private HttpLoggingInterceptor newLogger(HttpLoggingInterceptor.Level level) {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(level);
        return loggingInterceptor;
    }

    /**
     * @return base retrofit instance
     */
    public Retrofit getRetrofit() {
        return retrofit;
    }

    public <T> T createClient(Class<T> tClass) {
        return retrofit.create(tClass);
    }

    public void enableDebugMode(boolean debug) {
        MoConfig.LOG_TOGGLE = debug;
    }

    public void stopRetrofit() {
        httpClient = null;
        retrofit = null;
    }
}

