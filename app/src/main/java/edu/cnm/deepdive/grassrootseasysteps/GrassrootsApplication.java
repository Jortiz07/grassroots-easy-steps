package edu.cnm.deepdive.grassrootseasysteps;

import android.app.Application;
import com.facebook.stetho.Stetho;

public class GrassrootsApplication extends Application {

  @Override
  public void onCreate() {
    super.onCreate();
    Stetho.initializeWithDefaults(this);
  }

}
