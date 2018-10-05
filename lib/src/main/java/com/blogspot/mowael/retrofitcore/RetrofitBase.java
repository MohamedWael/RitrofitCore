package com.blogspot.mowael.retrofitcore;

import com.blogspot.mowael.retrofitcore.utils.RetrofitBaseConfig;

import java.io.IOException;

import okhttp3.Credentials;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
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


    private final String CONTENT_TYPE = "Content-Type";
    private final String AUTHORIZATION = "Authorization";
    private final String APPLICATION_JSON = "application/json";
    private final String APPLICATION_FORM_URL = "application/x-www-form-urlencoded";

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
            RetrofitBaseConfig.LOG_TOGGLE = debug;
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
            RetrofitBaseConfig.LOG_TOGGLE = debug;
            instance = new RetrofitBase(baseUrl, factory);
        }
        return instance;
    }

    /**
     * @param baseUrl   base url
     * @param authToken OAuth2 token
     * @return retrofit base instance
     */
    public RetrofitBase initializeWithOAuthToken(String baseUrl, String authToken) {
        httpClient = null;
        httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(getAuthInterceptor(authToken));
        return newInstance(httpClient, baseUrl);
    }

    public RetrofitBase initializeWithBasicToken(String baseUrl, String username, String password) {
        httpClient = null;
        httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(getAuthInterceptor(username, password));
        return newInstance(httpClient, baseUrl);
    }


    public Interceptor getAuthInterceptor(final String authToken) {
        return new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request original = chain.request();
                Request.Builder builder = original.newBuilder().
                        header(CONTENT_TYPE, APPLICATION_JSON).
                        header(AUTHORIZATION, "Bearer " + authToken);
                Request request = builder.build();
                return chain.proceed(request);
            }
        };
    }


    public Interceptor getAuthInterceptor(String username, String password) {
        final String credentials = Credentials.basic(username, password);
        return new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request original = chain.request();
                Request.Builder builder = original.newBuilder()
                        .header(AUTHORIZATION, credentials).
                                header(CONTENT_TYPE, APPLICATION_FORM_URL);

                okhttp3.Response response = chain.proceed(builder.build());
                return response;
            }
        };
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
            if (RetrofitBaseConfig.LOG_TOGGLE) {
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
        RetrofitBaseConfig.LOG_TOGGLE = debug;
    }

    public void stopRetrofit() {
        httpClient = null;
        retrofit = null;
    }
}


