package edu.cnm.deepdive.grassrootseasysteps;

import android.app.Application;
import com.facebook.stetho.Stetho;
import edu.cnm.deepdive.grassrootseasysteps.service.GoogleSignInService;
import edu.cnm.deepdive.grassrootseasysteps.service.HouseDatabase;
import io.reactivex.schedulers.Schedulers;

public class GrassrootsApplication extends Application {

  @Override
  public void onCreate() {
    super.onCreate();
    GoogleSignInService.setContext(this);
    Stetho.initializeWithDefaults(this);
    HouseDatabase.setContext(this);
    HouseDatabase.getInstance().getHouseDao().delete()
        .subscribeOn(Schedulers.io())
        .subscribe();
  }

}
