package edu.cnm.deepdive.qodclient.service;

import android.text.method.SingleLineTransformationMethod;
import edu.cnm.deepdive.qodclient.BuildConfig;
import edu.cnm.deepdive.qodclient.model.Quote;
import io.reactivex.Single;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import okhttp3.logging.HttpLoggingInterceptor.Level;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

public interface QodService {

  @GET("quotes/random")
  Single<Quote> random();

  static QodService getInstance(){
    return InstaceHolder.INSTANCE;
  }

  class InstaceHolder {

    private static final QodService INSTANCE;

    static{
      //remove these lines for production release
      HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
      interceptor.setLevel(Level.BODY);
      OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();
      Retrofit retrofit = new Retrofit.Builder().baseUrl(BuildConfig.BASE_URL).client(client)//for production use standard http client without logging
      .addCallAdapterFactory(RxJava2CallAdapterFactory.create()).addConverterFactory(
              GsonConverterFactory.create()).build(); //Todo check/change
      INSTANCE = retrofit.create(QodService.class);
    }
  }


}
