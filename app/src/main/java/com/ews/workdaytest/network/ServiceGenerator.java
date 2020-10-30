package com.ews.workdaytest.network;

import androidx.annotation.Nullable;

import com.ews.workdaytest.utils.Constants;
import com.google.gson.GsonBuilder;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory; public class ServiceGenerator {
    private static final String TAG = "SERVICE_GENERATOR";

    private static Retrofit.Builder builder = new Retrofit.Builder()
            .baseUrl(Constants.NETWORK.GIPHY_API_URL)
            .addConverterFactory(new NullOnEmptyConverterFactory())
            .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create()));

    private static HttpLoggingInterceptor loggingInterceptor =
            new HttpLoggingInterceptor()
                    .setLevel(HttpLoggingInterceptor.Level.BASIC);

    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

    static class NullOnEmptyConverterFactory extends Converter.Factory {
        @Nullable
        @Override
        public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
            final Converter<ResponseBody, ?> delegate = retrofit.nextResponseBodyConverter(this, type, annotations);
            return (Converter<ResponseBody, Object>) body -> {
                if (body.contentLength() == 0) {
                    return null;
                }
                return delegate.convert(body);
            };
        }
    }

    public static <S> S createService(Class<S> serviceClass) {
        if (!httpClient.interceptors().contains(loggingInterceptor)) {
            httpClient.connectTimeout(60, TimeUnit.SECONDS);
            httpClient.readTimeout(120, TimeUnit.SECONDS);
            httpClient.writeTimeout(600, TimeUnit.SECONDS);
            httpClient.addInterceptor(loggingInterceptor);
        }

        builder.client(httpClient.build());

        return builder.build().create(serviceClass);
    }
}