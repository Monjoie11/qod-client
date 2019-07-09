package edu.cnm.deepdive.qodclient.service;

import android.text.method.SingleLineTransformationMethod;
import edu.cnm.deepdive.qodclient.BuildConfig;
import edu.cnm.deepdive.qodclient.model.Quote;
import io.reactivex.Observable;
import io.reactivex.Single;
import java.util.List;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import okhttp3.logging.HttpLoggingInterceptor.Level;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface QodService {

  @GET("quotes/random")
  Single<Quote> random();

  @GET("quotes/search")
  Observable<List<Quote>> search(@Query("q") String filter);

  static QodService getInstance(){
    return InstanceHolder.INSTANCE;
  }

  class InstanceHolder {

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
