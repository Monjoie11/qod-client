package edu.cnm.deepdive.qodclient;

import android.app.Application;

public class QodApplication extends Application {

  @Override
  public void onCreate(){
    super.onCreate();
    //This is where we would put stetho, picasso, etc or nontrivial db operations (like a delete) to force create
  }

}
